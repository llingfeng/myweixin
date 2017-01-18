package com.util;

import com.entity.News;
import com.entity.NewsMessage;
import com.entity.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by admin on 2016/11/17.
 */
public class MessageUtil {
    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_NEWS = "news";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVENT = "event";
    public static final String MESSAGE_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    public static final String MESSAGE_CLICK = "CLICK";
    public static final String MESSAGE_VIEW = "VIEW";


    /**
     * xml文件转Map集合
     *
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();
        //从request中获取输入流
        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);
        //得到根元素
        Element element = doc.getRootElement();
        List<Element> list = element.elements();
        for (Element ele : list) {
            map.put(ele.getName(), ele.getText());
        }
        ins.close();
        return map;
    }

    /**
     * 文本对象转xml
     * @param textMessage
     * @return
     */
    public static String textMessageToXml(TextMessage textMessage) {
        XStream xStream = new XStream(new StaxDriver());
        xStream.alias("xml", TextMessage.class);
        return xStream.toXML(textMessage);
    }

    /**
     * 图文对象转化为xml
     * @param newsMessage
     * @return
     */
    public static String newsMessageToXml(NewsMessage newsMessage){
        XStream xStream = new XStream(new StaxDriver());
        xStream.alias("xml",newsMessage.getClass());
        xStream.alias("item",new News().getClass());
        return xStream.toXML(newsMessage);
    }

    /**
     * 初始化文本回复内容
     * @param fromPerson
     * @param toPerson
     * @param content
     * @return
     */
    public static String initMessage(String fromPerson, String toPerson, String content) {
        TextMessage textMessage = new TextMessage();
        textMessage.setFromUserName(toPerson);
        textMessage.setToUserName(fromPerson);
        textMessage.setContent(content);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.MESSAGE_TEXT);
        return textMessageToXml(textMessage);
    }

    /**
     * 初始化图文回复
     * @param fromUserName
     * @param toUserName
     * @return
     */
    public static String initNewsMessage(String fromUserName,String toUserName){
        List<News> newsList = new ArrayList<>();
        for(int i=0;i<2;i++){
            News news = new News();
            news.setTitle("杨幂当兵了");
            news.setDescription("杨幂参加湖南卫视真正男子汉，体验了一回军人");
            news.setPicUrl("http://myapp.tunnel.qydev.com/myweixin/image/damimi.jpg");
            news.setUrl("www.baidu.com");
            newsList.add(news);
        }
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setFromUserName(toUserName);
        newsMessage.setToUserName(fromUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MESSAGE_NEWS);
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());
        return newsMessageToXml(newsMessage);
    }


    /**
     * 创建主菜单
     * @return
     */
    public static String MenuText() {
        StringBuilder sb = new StringBuilder();
        sb.append("你好，我是刷子李大嘴，请按提示进行操作：\n\n");
        sb.append("1.介绍\n");
        sb.append("2.功能\n\n");
        sb.append("回复'?'查询主菜单");
        return sb.toString();
    }

    public static String firstMenu() {
        return "这是一个介绍";
    }

    public static String secondMenu() {
        return "这是一个功能";
    }


}
