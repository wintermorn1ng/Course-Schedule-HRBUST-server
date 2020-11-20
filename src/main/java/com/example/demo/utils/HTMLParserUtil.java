package com.example.demo.utils;

import com.example.demo.bean.Course;
import com.example.demo.bean.Courses;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParserUtil {
    public static Courses parserClassTable(String html){
        //System.out.println(html);
        Courses courses = new Courses();

        Document document = Jsoup.parse(html);

        Element option = document.getElementsByAttributeValue("name","whichWeek").get(0);
        Element select = option.getElementsByAttribute("selected").get(0);
        courses.setWeek(select.text());

        Element table = document.getElementsByClass("datalist").get(0);
        Elements trs = table.getElementsByTag("tr");
        for(int i=1;i<trs.size();i++){
            Element tr = trs.get(i);
            Elements tds = tr.getElementsByTag("td");
            Course course = new Course();
            for(Element td:tds){
                String mName = td.attr("name");
                if(mName.equals("td0")){
                    if(td.text().equals(""))continue;
                    course.setDate(td.text());
                }
                if(mName.equals("td1")){
                    course.setName(td.text());
                }
                if(mName.equals("td5")){
                    course.setWeekday(td.text());
                }
                if(mName.equals("td6")){
                    course.setSection(td.text());
                }
                if(mName.equals("td10")){
                    course.setClassroom(td.text());
                }
            }
            if(course.getDate()!=null)
                courses.data.add(course);
        }
        return courses;
    }
}
