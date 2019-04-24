package com.diandiancar.demo;

import com.diandiancar.demo.controller.TestController;
import com.diandiancar.demo.util.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Encoder;
import sun.security.provider.MD5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void test() {

    Integer a = 1;
    Integer b = 1;
        System.out.println(a==b);
        System.out.println(a.equals(b));
    }

    @Test
    public void testMD5() {

//        MD5CryptoServiceProvider
    }

    @Test
    public void EncoderByMd5() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        String str = "lin";
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        System.out.println(newstr);
    }

    @Test
    public void testMd5() throws NoSuchAlgorithmException {

        String lin = "test";
        String setLin = MD5Util.getMD5(lin);

        System.out.println(setLin+"setLin");


    }

    @Test
    public void testList(){
        List<Integer> list = Arrays.asList(0, 2, 4, 1, 5);
        Integer integer = 0;
        for(int i=0;i<list.size();i++){
            System.out.println(i+"----"+i+"-----"+list.get(i));
            if(i==list.get(i)){
                System.out.println(i+"---");
                list.set(i,list.get(i)+1);
            }
        }
        for (Integer i: list) {
            System.out.println(i);
        }
    }
}
