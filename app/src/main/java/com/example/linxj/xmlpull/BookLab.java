package com.example.linxj.xmlpull;

import com.example.linxj.Model.Book;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lvjia on 2015/8/25.
 */
public class BookLab {
    private static BookLab bookLab;
    private ArrayList<Book> books = new ArrayList<Book>();
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
    public void add(Book book){
        this.books.add(book);
    }
    public ArrayList<Book> getBooks(){
        return books;
    }
}
