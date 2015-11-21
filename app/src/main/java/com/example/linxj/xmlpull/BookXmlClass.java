package com.example.linxj.xmlpull;

import com.example.linxj.Model.Book;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;

/**
 * Created by lvjia on 2015/8/25.
 */
public class BookXmlClass implements XmlPull {
    BookLab bookLab = null ;
    Book book = null;
    @Override
    public BookLab parse(InputStream is) throws Exception {
       // List<Book> book;
        try {
            XmlPullParserFactory factory =  XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(is,"utf-8"); //设置输入流并设置编码
            int eventType = parser.getEventType();
            int i = 0;
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                       bookLab = BookLab.newInstance();
                        break;
                    case XmlPullParser.START_TAG:

                        if(parser.getName().equals("array")) {
                            book = new Book();
                           // i = 0;
                        }else if(parser.getName().equals("string")){
                            if(i == 0) {
                                book.setName(parser.nextText());
                                i++;
                            }else if(i == 1){
                                book.setIbsn(parser.nextText());
                                i++;
                            }else if(i == 2) {
                                book.setIndex(parser.nextText());
                                i = 0;
                            }

                        }break;
                    case  XmlPullParser.END_TAG:
                        if(parser.getName().equals("array")){
                            bookLab.add(book);
                            book = null;
                        }break;

                }
                eventType = parser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookLab;
    }
}
