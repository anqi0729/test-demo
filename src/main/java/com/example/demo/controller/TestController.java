package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @Value("${server.port}")
    private int id;

    @RequestMapping("/hello")
    public String index(Model model) {
        return "hello1" + id;
    }

    /**
     * 获取图片的EXIF信息
     * @throws ImageProcessingException
     * @throws IOException
     */
    @RequestMapping("/getPicExifInfo")
    public String getPicExifInfo(String fileName) throws ImageProcessingException, IOException {
//        String fileName = "C:\\Users\\86132\\Desktop\\迪士尼\\2021_07_22_18_51_IMG_1589.JPG";
//        String fileName = "C:\\Users\\86132\\Pictures\\微信图片_20200615190835.jpg";
//        String fileName = "C:\\Users\\86132\\Pictures\\事务测试.png";
        File file = new File(fileName);
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        Map<String, String> map = new HashMap();
        Map<String, Object> map1 = new HashMap();

        for (Directory directory : metadata.getDirectories()) {
            map1.put(directory.getName(), "");
            for (Tag tag : directory.getTags()) {
                map.put(tag.getDirectoryName() + ";" + tag.getTagName(), tag.getDescription());
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
        return JSON.toJSONString(map1);
    }

    /**
     * 获取图片的EXIF信息
     * @throws ImageProcessingException
     * @throws IOException
     */
    @RequestMapping("/getPicExifInfo2")
    public String getPicExifInfo2(@RequestParam("file") MultipartFile multipartFile) throws ImageProcessingException, IOException {
//        String fileName = "C:\\Users\\86132\\Desktop\\迪士尼\\2021_07_22_18_51_IMG_1589.JPG";
//        String fileName = "C:\\Users\\86132\\Pictures\\微信图片_20200615190835.jpg";
//        String fileName = "C:\\Users\\86132\\Pictures\\事务测试.png";
        File file = transferToFile(multipartFile);
//        File file = new File(fileName);
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        Map<String, String> map = new HashMap();
        Map<String, Object> map1 = new HashMap();

        for (Directory directory : metadata.getDirectories()) {
            map1.put(directory.getName(), "");
            for (Tag tag : directory.getTags()) {
                map.put(tag.getDirectoryName() + ";" + tag.getTagName(), tag.getDescription());
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
        return JSON.toJSONString(map1);
    }
    public File transferToFile(MultipartFile multipartFile) {
//        选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            file = File.createTempFile(filename[0], filename[1]);
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void main(String[] args) throws Exception {
        invokeStaticMethod("com.example.demo.service.TestService","sayHello",null);
    }

    public static Object invokeStaticMethod(String className, String methodName,
                                            Object[] args) throws Exception {
        Class ownerClass = Class.forName(className);

        Class[] argsClass = new Class[args.length];

        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }

        Method method = ownerClass.getMethod(methodName,argsClass);

        return method.invoke(null, args);
    }
}
