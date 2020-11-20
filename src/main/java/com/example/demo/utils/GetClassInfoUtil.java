package com.example.demo.utils;

import com.example.demo.bean.Captcha;
import com.example.demo.domain.ImgRep;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class GetClassInfoUtil extends HttpUtil{
    private static final String getCaptchaUrl = "*";
    private static final String checkCaptchaUrl = "*";
    private static final String loginUrl = "*";
    private static final String gotoClassTableLocationUrl = "*";
    private static final String getNowWeekTableUrl = "*";
    private static final String getWeekTableByWeekUrl = "*";
    private static final String mainPageUrl = "*";
    private static final String loginOutUrl = "*";


    public static Captcha getCaptcha(){
        String url = getCaptchaUrl+Math.random();
        //Request request = new Request.Builder().url(url).removeHeader("User-Agent").addHeader("User-Agent",USERAGENT).build();
        Request request = new Request.Builder().url(url).header("User-Agent",USERAGENT).build();
        try (Response response = okHttpClient.newCall(request).execute()){
            Captcha mCaptcha = new Captcha("");
            mCaptcha.cookie = Objects.requireNonNull(response.header("Set-Cookie")).split(" ")[0];
            ImgRep.put(mCaptcha.cookie,response.body().bytes());
            return mCaptcha;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static  byte[] getCaptchaImg(String fileName){
        return ImgRep.get(fileName);
    }

    public static boolean checkCaptcha(String captchaCode,String cookie){
        //System.out.println(captchaCode+" - "+cookie);
        RequestBody requestBody = new FormBody.Builder().add("captchaCode",captchaCode).build();
        Request request = new Request.Builder().post(requestBody).url(checkCaptchaUrl)
                .header("User-Agent",USERAGENT)
                .header("cookie",cookie)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            if(Objects.requireNonNull(response.body()).string().equals("true"))return true;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    /*
    html div=message中存放错误信息

    成功 http://jwzx.hrbust.edu.cn/academic/index_new.jsp
    失败 http://jwzx.hrbust.edu.cn/academic/common/security/login.jsp?login_error=1
     */

    /**
     *
     * @param username
     * @param password
     * @param captchaCode
     * @param cookie
     * @return cookie
     */
    public static String login(String username,String password,String captchaCode,String cookie){
        //System.out.println(username+" - "+password+" - "+captchaCode+" - "+cookie);
        RequestBody requestBody = new FormBody.Builder()
                .add("j_username",username)
                .add("j_password",password)
                .add("j_captcha",captchaCode)
                .add("login","")
                .build();
        Request request = new Request.Builder().post(requestBody).url(loginUrl)
                .header("User-Agent",USERAGENT)
                .header("cookie",cookie)
                .build();

        try (Response response = HttpUtil.okHttpClientWithCookie.newCall(request).execute()) {
            String str = response.request().url().toString();
            //System.out.println(str);
            if(str.equals(mainPageUrl))
                return  Objects.requireNonNull(response.networkResponse()).request().headers("Cookie").get(0);;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /*
    TODO
    set cookie
     */

    public static void gotoClassTableLocation(String cookie){
        //System.out.println("来到课表");
        String url = gotoClassTableLocationUrl;
        url = url + getTime() + getRandomString(6);
        Request request = new Request.Builder().url(url)
                .header("User-Agent",USERAGENT)
                .header("cookie",cookie)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()){
            //System.out.println(response);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * @param year example2020
     * @param term 1 or 2
     * @return String
     */
    public static String getNowWeekTable(int year,int term,String cookie){
        //System.out.println(year+" - "+term+" - "+cookie);
        String url = getNowWeekTableUrl;
        int yearId = year-1980;
        url = url + "yearid="+yearId+"&termid="+term;
        Request request = new Request.Builder().url(url)
                .header("User-Agent",USERAGENT)
                .header("cookie",cookie)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()){
            return Objects.requireNonNull(response.body()).string();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getWeekTableByWeek(int year,int term,int whichWeek,String cookie){
        //System.out.println("得到课表");
        int yearId = year-1980;
        RequestBody requestBody = new FormBody.Builder()
                .add("yearid",yearId+"")
                .add("termid",term+"")
                .add("whichWeek",whichWeek+"")
                .build();
        Request request = new Request.Builder().post(requestBody).url(getWeekTableByWeekUrl)
                .header("User-Agent",USERAGENT)
                .header("cookie",cookie)
                .header("Referer",getNowWeekTableUrl+yearId+"&termid="+term)
                .header("Origin","http://jwzx.hrbust.edu.cn")
                .header("Upgrade-Insecure-Requests","1")
                .build();
        try (Response response = okHttpClient.newCall(request).execute()){
            return Objects.requireNonNull(response.body()).string();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    //退出
    public static void loginOut(String cookie){
        Request request = new Request.Builder().url(loginOutUrl)
                .header("User-Agent",USERAGENT)
                .header("cookie",cookie)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()){
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyyMMddHHmmss");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        return sdf.format(date).toString();
    }
    private static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
