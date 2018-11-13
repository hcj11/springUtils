package com.example.demo;

import com.sun.crypto.provider.HmacMD5;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class test1 {
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        // byte -> int  转成16进制。
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        String ss = "dsadw'dsad撒大声'地";
////            String replace = ss.replace("'", "\\'").replace("\n","");
////        String replace = ss.replace("'", "");
////        System.out.println(replace);
        // md5 加密 生成32bit的校验码， 提供摘要信息   16字节数组 生成32位
        String phone = "17317298371";
        String s = DigestUtils.md5DigestAsHex(phone.getBytes());
        System.out.println(s);
        byte[] bytes = DigestUtils.md5Digest(phone.getBytes(StandardCharsets.UTF_8));
        System.out.println(bytesToHexString(bytes));

        byte[] bytes1 = DigestUtils.md5Digest(phone.getBytes(StandardCharsets.UTF_8));
        System.out.println(bytesToHexString(bytes1));

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] data;
        data = md5.digest(phone.getBytes(StandardCharsets.UTF_8));// 转换为MD5码
        // bytes -> hex
        System.out.println(data);
        System.out.println(bytesToHexString(data));
        // SHA-256 的编码生成16进制
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] digest = sha.digest(phone.getBytes(StandardCharsets.UTF_8));
        System.out.println(digest);
        System.out.println(bytesToHexString(digest));
//    40
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        byte[] digest1 = sha1.digest(phone.getBytes(StandardCharsets.UTF_8));
        System.out.println(digest1);
        System.out.println(bytesToHexString(digest1));
        // 计算文件的md5值， 和提供的值进行对比

//        System.out.println(bytes);
//        byte[] bytes = DigestUtils.md5Digest(s.getBytes());
//        System.out.println(new String(bytes, StandardCharsets.UTF_8));
        // md5 加密 不可逆。 只能破解


//            String ss="哈哈";
//            int i = ss.hashCode();
//            System.out.println(i);
//            int hash = Objects.hash(ss);
//            System.out.println(hash);
//            int i1 = Objects.hashCode(ss);
//            System.out.println(i1);
        //  102%100  0-99 1,2,3,4,5..   对%100取模，分100个文件
        //  12%10 0-9 1..0    对%10取模，分10个文件
//        int i = Objects.hashCode(ss);
//        // &    1555092336
//        int i1 = i & Integer.MAX_VALUE;
//        System.out.println(i1);
//        System.out.println(Integer.MAX_VALUE);
//        //    1010
//        int i2 = i & 10;
//        System.out.println(i2);
//        int i3 = 10 & Integer.MAX_VALUE;
//        System.out.println(i3);
//        int i4 = i3 % 10;
//        System.out.println(i4);
//
////            String content="你,好,啊,的";
////            String[] split = content.split(",");
////            List<String> collect = Arrays.stream(split).map(String::trim).collect(Collectors.toList());
////            System.out.println(collect);
//
//        String ss = "1,2";
//        String ss1 = "1";
//        String[] split = ss.split(",");
//        String[] split1 = ss1.split(",");
//        System.out.println(Arrays.asList(split));
//        System.out.println(Arrays.asList(split1));
        // 编码and 解码
//        String gbk = URLEncoder.encode("<>Av9x^finBox4B1L8", "gbk");
//        String gbk1 = URLDecoder.decode(gbk, "gbk");
//        System.out.println(gbk);
//        System.out.println(gbk1);
//        double v = Double.parseDouble("1.0");
//        int v2 = (int)v ;
//        System.out.println(v2);

//        double v1 = Double.parseDouble("0.0");
//
//        long l = Long.parseLong("1.2");
//
//        System.out.println(l);
//        long a=10L;
//        //
//        long l = a << 32L;
//        System.out.println(l);


    }
}
