package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Sku;
import com.example.demo.mapper.LocalTestMapper;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class LuceneTestTest {

    @Autowired
    private LocalTestMapper localTestMapper;

    @Test
    public void testCreateIndex() throws IOException {
        //数据采集
        QueryWrapper queryWrapper = new QueryWrapper();
        List<Sku> list = localTestMapper.selectList(queryWrapper);
        System.out.println(list);
        //文档集合
        List<Document> documents = new ArrayList<>();
        //往集合中添加文档
        for(Sku sku:list) {
            //创建文档对象
            Document doc = new Document();
            //为文档对象创建域对象
            doc.add(new TextField("id", sku.getId(), Field.Store.YES));
            doc.add(new TextField("name", sku.getName(),Field.Store.YES));
            doc.add(new TextField("price", String.valueOf(sku.getPrice()),Field.Store.YES));
            doc.add(new TextField("image", sku.getImage(),Field.Store.YES));
            doc.add(new TextField("categoryName", sku.getCategoryName(),Field.Store.YES));
            doc.add(new TextField("brandName", sku.getBrandName(),Field.Store.YES));
            documents.add(doc);
        }
        //创建分词器
        Analyzer analyzer = new StandardAnalyzer();
        //创建Directory对象,声明索引库的位置
        Directory directory = FSDirectory.open(Paths.get("e:/lucenedir"));
        //创建索引写入配置对象
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        //创建索引写入对象
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        //通过写对象写入到索引库
        for(Document doc:documents) {
            indexWriter.addDocument(doc);
        }
        //关闭流
        indexWriter.close();
        System.out.println("索引库创建完成！");
    }

    //测试文本搜索
    @Test
    public void testIndexSearch() throws Exception {
        //创建分词器
        Analyzer analyzer = new StandardAnalyzer();
        //创建搜索解析器，第一个参数是默认的域，第二个参数是分词器
        QueryParser queryParser = new QueryParser("name", analyzer);
        //基于搜索表达式创建搜索对象
        Query query = queryParser.parse("苹果");
        //创建Directory流对象,声明索引库位置
        Directory directory = FSDirectory.open(Paths.get("e:/lucenedir"));
        //创建索引读取对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建索引搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //执行搜索，返回TopDocs结果集，第一个参数是查询对象，第二个参数是分页时返回的记录条数
        TopDocs topDocs = indexSearcher.search(query, 10);
        System.out.println("查询到的数据总条数：" + topDocs.totalHits + "条！");
        //获取查询结果集，返回ScoreDoc类型数组
        ScoreDoc[]scoreDocs = topDocs.scoreDocs;
        //遍历结果集
        for(ScoreDoc doc:scoreDocs) {
            //获取文档ID
            int docId = doc.doc;
            //获取文档
            Document document = indexSearcher.doc(docId);
            System.out.println("----------------------------------");
            //通过域名获取域值
            System.out.println("id:" + document.get("id"));
            System.out.println("name：" + document.get("name"));
            System.out.println("price：" + document.get("price"));
            System.out.println("image：" + document.get("image"));
            System.out.println("brandName：" + document.get("brandName"));
            System.out.println("categoryName：" + document.get("categoryName"));
        }
        //关闭读取对象
        indexReader.close();
    }

}