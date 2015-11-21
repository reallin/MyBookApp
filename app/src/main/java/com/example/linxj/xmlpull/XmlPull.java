package com.example.linxj.xmlpull;

import java.io.InputStream;
import java.util.List;

/**
 * Created by lvjia on 2015/8/25.
 */
public interface XmlPull {
    public BookLab parse(InputStream is) throws Exception;

}
