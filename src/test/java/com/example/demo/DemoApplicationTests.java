package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.sun.org.apache.xpath.internal.objects.XObject;
import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.*;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }


    /**
     * 测试用于读取图片的EXIF信息
     * @author Winter Lau
     */
    @Test
    void ExifTester() throws ImageProcessingException, IOException {
        File file = new File("C:\\Users\\86132\\Desktop\\迪士尼\\2021_07_22_18_51_IMG_1589.JPG");
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        StringBuffer stringBuffer = new StringBuffer();
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.format("[%s] - %s = %s",
                        directory.getName(), tag.getTagName(), tag.getDescription());
                System.out.println();
            }

            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    System.err.format("ERROR: %s", error);
                }
            }
        }
    }

    /**
     * 获取图片的EXIF信息
     * @throws ImageProcessingException
     * @throws IOException
     */
    @Test
    void getPicExifInfo() throws ImageProcessingException, IOException {
//        String fileName = "C:\\Users\\86132\\Desktop\\迪士尼\\2021_07_22_18_51_IMG_1589.JPG";
//        String fileName = "C:\\Users\\86132\\Pictures\\微信图片_20200615190835.jpg";
        String fileName = "C:\\Users\\86132\\Pictures\\事务测试.png";
        File file = new File(fileName);
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        Map<String, String> map = new HashMap();
        Map<String, Object> map1 = new HashMap();

        for (Directory directory : metadata.getDirectories()) {
            map1.put(directory.getName(),"");
            for (Tag tag : directory.getTags()) {
                map.put(tag.getDirectoryName()+";"+tag.getTagName(),tag.getDescription());
            }
        }
        for (String key1 : map1.keySet()) {
            Map<String, String> map2 = new HashMap();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String[] split = entry.getKey().split(";");//Exif SubIFD   ;    White Balance Mode
                if (key1.equals(split[0])) {
                    map2.put(split[1], entry.getValue());
                }
            }
            map1.put(key1, map2);
        }
        System.out.println(JSON.toJSONString(map1));
        //存储数据库json格式
    }

}
