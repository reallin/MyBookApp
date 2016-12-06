package com.example.linxj.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by linxj on 2015/9/26.
 */
@Table(name = "BookDate")
public class BookData extends Model {
    /*    @Column(name = "number")
        public String number;*/
    @Column(name = "Name")
    public String name;
    @Column(name = "isbn")
    public String isbn;

    @Column(name = "Time")
    public long time;

    public BookData() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}

