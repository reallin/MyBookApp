package com.example.linxj.Model;

import android.os.Parcelable;



import java.io.Serializable;

/**
 * Created by lvjia on 2015/8/25.
 */


public class Book  {

    private String name;

    private String index;

    private String ibsn;

    public void setName(String name) {
        this.name = name;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setIbsn(String ibsn) {
        this.ibsn = ibsn;
    }

    public String getName() {

        return name;
    }

    public String getIndex() {
        return index;
    }

    public String getIbsn() {
        return ibsn;
    }

    @Override
    public String toString() {
        return name+""+ibsn;
    }
}
