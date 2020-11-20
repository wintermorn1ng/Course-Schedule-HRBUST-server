package com.example.demo.controller;

import com.example.demo.bean.Captcha;
import com.example.demo.bean.Courses;
import com.example.demo.domain.CourseRep;
import com.example.demo.utils.GetClassInfoUtil;
import com.example.demo.utils.HTMLParserUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MainController {
    @RequestMapping("/index")
    public String index(){
        return "hello Test";
    }

    //返回图片
    //produces = MediaType.IMAGE_JPEG_VALUE
    @RequestMapping(value = "/getCaptcha")
    public Captcha getCaptcha(){
        CourseRep.sum++;
        System.out.println(CourseRep.sum);
        return GetClassInfoUtil.getCaptcha();
    }

    @RequestMapping(value = "/getCaptchaImg",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getCaptchaImg(@RequestParam String fileName){
        return GetClassInfoUtil.getCaptchaImg(fileName);
    }

    @RequestMapping(value = "/checkCaptcha")
    public String checkCaptcha(@RequestParam String captcha,@RequestParam String cookie){
        if(GetClassInfoUtil.checkCaptcha(captcha,cookie)){
            return "true";
        }
        else{
            return "false";
        }
    }

    @RequestMapping(value = "/login")
    public Captcha login(@RequestParam String username,@RequestParam String password,@RequestParam String captcha,@RequestParam String cookie){
        //System.out.println("login");
        return new Captcha(GetClassInfoUtil.login(username,password,captcha,cookie));
        /*
        返回cookie
         */
    }

    @RequestMapping(value = "/gotoClassTableLocation")
    public String gotoClassTableLocation(){
        return null; //TODO
    }

    @RequestMapping(value = "/getNowWeekTable")
    public Courses getNowWeekTable(@RequestParam int year, @RequestParam int term, @RequestParam String cookie){
        GetClassInfoUtil.gotoClassTableLocation(cookie);
        return HTMLParserUtil.parserClassTable(GetClassInfoUtil.getNowWeekTable(year,term,cookie));
    }

    @RequestMapping(value = "/getWeekTableByWeek")
    public Courses getWeekTableByWeek(@RequestParam int year, @RequestParam int term,@RequestParam int whichWeek ,@RequestParam String cookie){
        //GetClassInfoUtil.gotoClassTableLocation(cookie);
        //System.out.println(whichWeek);
        return HTMLParserUtil.parserClassTable(GetClassInfoUtil.getWeekTableByWeek(year,term,whichWeek,cookie));
        /*TODO
        不确定返回的表格是否和上一个方法一致
         */
    }

    //TODO logout
    @RequestMapping(value = "/loginOut")
    public void loginOut(@RequestParam String cookie){
        GetClassInfoUtil.loginOut(cookie);
    }
}
