package com.diandiancar.demo.controller;

import com.diandiancar.demo.entity.CarCategory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/test")
    public String test() {
        System.out.println("testtestsetts");
        return "/customer/car-list";
    }
    @RequestMapping("/index")
    public String carIndex() {
        System.out.println("testtestsetts");
        return "/customer/index";
    }
    @RequestMapping("/testAbout")
    public String testAbout() {
        System.out.println("testAbout");
        return "/about";
    }
    @RequestMapping("/my")
    public String my() {
        System.out.println("testtestsetts");
        return "/customer/my";
    }


    @RequestMapping("/json")
    @ResponseBody
    public List<CarCategory> json() {
        System.out.println("jsonjson");

        List<CarCategory> list = new ArrayList<>();
        CarCategory carCategory = new CarCategory();
        carCategory.setCategoryId(0);
        carCategory.setCategoryName("my0name");
        carCategory.setCategoryType(0);
        CarCategory carCategory1 = new CarCategory();
        carCategory1.setCategoryId(1);
        carCategory1.setCategoryName("my1name");
        carCategory1.setCategoryType(1);

        list.add(carCategory);
        list.add(carCategory1);
        return list;
    }

    public static String getPwd(String pwd) throws NoSuchAlgorithmException {
        try {
            // 创建加密对象
            MessageDigest digest = MessageDigest.getInstance("md5");

            // 调用加密对象的方法，加密的动作已经完成
            byte[] bs = digest.digest(pwd.getBytes());

            // 接下来，我们要对加密后的结果，进行优化，按照Oracle的优化思路走
            // Oracle的优化思路：
            // 第一步，将数据全部转换成正数：
            String hexString = "";
            for (byte b : bs) {
                // 第一步，将数据全部转换成正数：
                int temp = b & 255;
                // 第二步，将所有的数据转换成16进制的形式
                // 注意：转换的时候注意if正数>=0&&<16，那么如果使用Integer.toHexString()，可能会造成缺少位数
                // 因此，需要对temp进行判断
                if (temp >= 0&&temp < 16 ) {
                    // 符合条件，手动补上一个“0”
                    hexString = hexString + "0" + Integer.toHexString(temp);
                } else {
                    hexString = hexString + Integer.toHexString(temp);
                }
            }
            return hexString;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "失败";
    }
}
