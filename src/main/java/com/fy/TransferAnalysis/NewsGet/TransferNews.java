package com.fy.TransferAnalysis.NewsGet;

import com.fy.TransferAnalysis.Config.Config;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.RequestLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.DefaultCookieSpec;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.URIException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.fy.TransferAnalysis.Config.Config.transferSite;

/**
 * Created by fy on 17-9-4.
 */
public class TransferNews {
    private Logger logger = LoggerFactory.getLogger(TransferNews.class);
    public static String newsWebsite = transferSite;
    public static TransferNews instance;
    public static HttpClient client;
    public static CookieStore cookieStore;
    /*获取转会新闻标题正则表达式*/
    private static String titleRegex = "(<h3>[^<]*)?(<a target=\"_blank\" href=)(.*)?(>)([^<]*)(</a>)([^<]*)?</h3>";
    /*获取转会新闻主体正则表达式*/
    private static String contentRegex = "<div class=\"artical-main-content\">(.|\n)*</div>";
    /*转会新闻标题*/
    private static List<String> titles;
    /*新闻主体*/
    private static List<String> newsContent;
    /*转会新闻html*/
    private String content;

    public static TransferNews getInstance() {
        if (instance == null) {
            instance = new TransferNews();
        }
        return instance;
    }

    public void getTransferNews() throws ClientProtocolException, IOException, URISyntaxException {

        cookieStore = new BasicCookieStore();
        CookieSpecProvider myCookie = new CookieSpecProvider() {
            public CookieSpec create(HttpContext context) {
                return new DefaultCookieSpec();
            }
        };
        Registry<CookieSpecProvider> registry = RegistryBuilder.<CookieSpecProvider>create().register("myCookie", myCookie)
                .build();
        client = HttpClients.custom().setDefaultCookieStore(cookieStore).setDefaultCookieSpecRegistry(registry).build();
        HttpGet get = new HttpGet();
        get.setURI(new URI(transferSite));
        get.setHeader("Host", Config.transferHost);
        get.setHeader("Accept", Config.Accept);
        get.setHeader("Accept-Language", Config.Accept_language);
        get.setHeader("Accept-Encoding", Config.Accept_encoding);
//        get.setHeader(new BasicHeader("Cookie",Config.Cookie));
        get.setHeader("Connection", Config.Connection);
        get.setHeader("Upgrade-Insecure-Request", Config.Upgrade_Insecure_Request);
        RequestLine requestLine = get.getRequestLine();

        HttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        this.content = EntityUtils.toString(entity);

    }

    public void splitNewsTitles() {
        this.titles = new ArrayList<String>();
        Pattern titlePattern = Pattern.compile(titleRegex);
        Matcher matcher = titlePattern.matcher(this.content);
        while (matcher.find()) {
            String result = matcher.group(5);
            titles.add(result);
        }
    }

    public void splitNewsContent(){
        this.newsContent = new ArrayList<String>();
        /*抓取新闻主体*/
        Document document = Jsoup.parse(content);
        Elements elements = document.select("div.artical-main-content");
        for(Element e : elements){
            newsContent.add(e.text());
            System.out.println(e.text());
        }
    }

}
