package com.fy.TransferAnalysis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fy on 17-9-4.
 */
public class test {
    public static void main(String args[]) throws IOException{


        File file  = new File("hupu.html");
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        String html = "";
        BufferedReader bufferedReader = new BufferedReader(reader);
        String tmp;
        while((tmp = bufferedReader.readLine()) != null){
            html += tmp+"\n";
        }
        System.out.println(getConent(html));
    }

    public static String getConent(String html){
        Document document = Jsoup.parse(html);
        Elements contents = document.select("div.artical-main-content");
        for(Element e : contents){
            System.out.println(e.text());
        }
        String res = "";
        return res;
    }
}
