package com.example.demo.utils;


import okhttp3.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HttpUtil {
    protected static String USERAGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0";
    private static final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    protected static OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
    protected  static OkHttpClient okHttpClientWithCookie = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                        cookieStore.put(httpUrl.host(), list);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                        List<Cookie> cookies = cookieStore.get(httpUrl.host());
                        cookieStore.clear();
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                }).build();
    protected void printHttpHeader(Response response){
        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }
    }
}
