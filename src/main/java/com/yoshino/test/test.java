package com.yoshino.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class test {

    public static void main(String[] args) throws IOException {
        String str = "";
        InputStream inputStream = new FileInputStream(Paths.get("/Users/xiaoyi/IdeaProjects/comyoshinoutil/src/main/java/com/yoshino/test/mockconfig2.txt").toString());
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String mockConfigstr = in.readLine();
        JSONObject jsonObj = JSON.parseObject(mockConfigstr);
        String mockConfig = (String) jsonObj.get("mockConfig");
//        mockConfig = mockConfig.replace("\\\"", "\"");
        mockConfig = mockConfig.replaceAll("[\\\\]+\"", "\"");
        System.out.println(mockConfig);
        jsonObj.put("mockConfig", mockConfig);
        PrintWriter out = new PrintWriter("/Users/xiaoyi/IdeaProjects/comyoshinoutil/src/main/java/com/yoshino/test/mockconfig2.txt", "UTF-8");
        out.print(jsonObj);
        out.flush();


//        DataOutputStream out = new DataOutputStream(new FileOutputStream("**"));
//        System.out.println(Charset.defaultCharset());
//        System.out.println(Charset.availableCharsets());
//        StandardCharsets.UTF_8
//        new PrintWriter("", "U");
//        DataInputStream dataInputStream = new DataInputStream(
//                new BufferedInputStream(
//                        new FileInputStream("filename.txt")
//                ));
//        dataInputStream.readDouble();
//        long star = System.currentTimeMillis();
//        BloomFilter<Integer> filter = BloomFilter.create(
//                Funnels.integerFunnel(),
//                10000000,
//                0.01);
//
//        for (int i = 0; i < 10000000; i++) {
//            filter.put(i);
//        }
//
////        Assert.assertTrue(filter.mightContain(1));
////        Assert.assertTrue(filter.mightContain(2));
////        Assert.assertTrue(filter.mightContain(3));
////        System.out.println(filter.mightContain(327394294));
//        Assert.check(filter.mightContain(327394294));
//        long end = System.currentTimeMillis();
//        System.out.println("执行时间：" + (end - star) + "ms");
    }

}
