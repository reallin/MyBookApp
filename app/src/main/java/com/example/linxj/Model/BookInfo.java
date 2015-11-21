package com.example.linxj.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by linxj on 2015/9/26.
 */
public class BookInfo {
    private categories category;
    @SerializedName(" db:tag")
    private List<tag> db;
    private Title title;
    private List<Author> author;
    private Content summary;
    private List<link>  link;
    @SerializedName(" db:attribute")
    public List<attribute>  attributes;
    public Id id;
    @SerializedName(" db:rating")
    public rating rating;

    public void setCategory(categories category) {
        this.category = category;
    }

    public void setDb(List<tag> db) {
        this.db = db;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setAuthor(List<Author> author) {
        this.author = author;
    }

    public void setContent(Content content) {
        this.summary = content;
    }

    public void setLink(List<BookInfo.link> link) {
        this.link = link;
    }

    public void setAttributes(List<attribute> attributes) {
        this.attributes = attributes;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public void setRating(BookInfo.rating rating) {
        this.rating = rating;
    }

    public categories getCategory() {

        return category;
    }

    public List<tag> getDb() {
        return db;
    }

    public Title getTitle() {
        return title;
    }

    public List<Author> getAuthor() {
        return author;
    }

    public Content getContent() {
        return summary;
    }

    public List<BookInfo.link> getLink() {
        return link;
    }

    public List<attribute> getAttributes() {
        return attributes;
    }

    public Id getId() {
        return id;
    }

    public BookInfo.rating getRating() {
        return rating;
    }

    public class categories{
        @SerializedName("@scheme")
        public String scheme;
        @SerializedName("@term")
        public String term;

        public void setScheme(String scheme) {
            this.scheme = scheme;
        }

        public void setTerm(String term) {
            this.term = term;
        }

        public String getScheme() {

            return scheme;
        }

        public String getTerm() {
            return term;
        }
    }

    public class tag{

        @SerializedName("@count")
        public String count;
        @SerializedName("@name")
        public String name;

        public void setCount(String count) {
            this.count = count;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCount() {

            return count;
        }

        public String getName() {
            return name;
        }
    }
    public class Title{

        @SerializedName("$t")
        private String title;

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {

            return title;
        }
    }
    public class Author{

        private Name name;

        public void setName(Name name) {
            this.name = name;
        }

        public Name getName() {

            return name;
        }
    }
    public class Name{
        @SerializedName("$t")
        public String authorName;

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getAuthorName() {

            return authorName;
        }
    }
    public class Content{

        @SerializedName("$t")
        public String content;

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {

            return content;
        }
    }
    public class link{
        @SerializedName("@rel")
        public String lrel;
        @SerializedName("@href")
        public String lhref;

        public void setLrel(String lrel) {
            this.lrel = lrel;
        }

        public String getLrel() {

            return lrel;
        }

        public void setLhref(String lhref) {
            this.lhref = lhref;
        }

        public String getLhref() {

            return lhref;
        }
        /*public List<MyLink> MyLink;

        public void setMyLink(List<BookInfo.MyLink> myLink) {
            MyLink = myLink;
        }

        public List<BookInfo.MyLink> getMyLink() {

            return MyLink;
        }*/
    }
   /* public class MyLink{
        @SerializedName("@rel")
        public String lrel;
        @SerializedName("@href")
        public String lhref;

        public void setLrel(String lrel) {
            this.lrel = lrel;
        }

        public String getLrel() {

            return lrel;
        }

        public void setLhref(String lhref) {
            this.lhref = lhref;
        }

        public String getLhref() {

            return lhref;
        }
    }*/
    public class attribute{
        public List<myAttribute> myAttribute;

        public void setMyAttribute(List<BookInfo.myAttribute> myAttribute) {
            this.myAttribute = myAttribute;
        }

        public List<BookInfo.myAttribute> getMyAttribute() {

            return myAttribute;
        }
    }
    public class myAttribute{
        @SerializedName("$t")
        public String aRel;
        @SerializedName("@name")
        public String aNname;

        public void setaRel(String aRel) {
            this.aRel = aRel;
        }

        public void setaNname(String aNname) {
            this.aNname = aNname;
        }

        public String getaRel() {
            return aRel;
        }

        public String getaNname() {
            return aNname;
        }
    }
    public class Id{

        @SerializedName("$t")
        public String id;

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
    public class rating{

        @SerializedName("@min")
        public int min;
        @SerializedName("@numRaters")
        public int numRaters;
        @SerializedName("@average")
        public String average;

        public int getMax() {
            return max;
        }

        public int getMin() {
            return min;
        }

        public int getNumRaters() {
            return numRaters;
        }

        public String getAverage() {
            return average;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public void setNumRaters(int numRaters) {
            this.numRaters = numRaters;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public void setMax(int max) {
            this.max = max;
        }

        @SerializedName("@max")

        public int max;
    }
}
