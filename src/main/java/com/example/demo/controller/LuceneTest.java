package com.example.demo.controller;

import com.example.demo.mapper.LocalTestMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class LuceneTest {
    @Autowired
    private LocalTestMapper localTestMapper;

//    public void testCreateIndex() throws IOException {
//        //数据采集
//        SkuDao skuDao = new SkuDaoImpl();
//        List<Sku> list = skuDao.querySkuAll();
//        //文档集合
//        List<Document> documents = new ArrayList<>();
//        //往集合中添加文档
//        for(Sku sku:list) {
//            //创建文档对象
//            Document doc = new Document();
//            //为文档对象创建域对象
//            doc.add(new TextField("id", sku.getId(),Field.Store.YES));
//            doc.add(new TextField("name", sku.getName(),Field.Store.YES));
//            doc.add(new TextField("price", String.valueOf(sku.getPrice()),Field.Store.YES));
//            doc.add(new TextField("image", sku.getImage(),Field.Store.YES));
//            doc.add(new TextField("categoryName", sku.getCategoryName(),Field.Store.YES));
//            doc.add(new TextField("brandName", sku.getBrandName(),Field.Store.YES));
//            documents.add(doc);
//        }
//        //创建分词器
//        Analyzer analyzer = new StandardAnalyzer();
//        //创建Directory对象,声明索引库的位置
//        Directory directory = FSDirectory.open(Paths.get("e:/dir"));
//        //创建索引写入配置对象
//        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
//        //创建索引写入对象
//        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
//        //通过写对象写入到索引库
//        for(Document doc:documents) {
//            indexWriter.addDocument(doc);
//        }
//        //关闭流
//        indexWriter.close();
//        System.out.println("索引库创建完成！");
//    }
}
