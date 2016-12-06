package com.example.linxj.xmlpull;

import com.example.linxj.Model.BookData;

import java.util.ArrayList;

/**
 * Created by lvjia on 2015/8/25.
 */
public class BookLab {
    private static BookLab bookLab;
    private ArrayList<BookData> books = new ArrayList<BookData>();
    private BookLab(){
    }
    public static BookLab newInstance(){
        if(bookLab == null){
            synchronized (BookLab.class){
                if(bookLab == null){
                    bookLab = new BookLab();
                }
            }
        }
        return bookLab;
    }
    public void add(BookData book){
        this.books.add(book);
    }
    public ArrayList<BookData> getBooks(){
        return books;
    }
}
