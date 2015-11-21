package com.example.linxj.Model;

import android.provider.BaseColumns;

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
public BookData(){

}
    }

