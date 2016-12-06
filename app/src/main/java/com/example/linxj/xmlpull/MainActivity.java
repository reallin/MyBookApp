package com.example.linxj.xmlpull;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.linxj.Model.BookData;
import com.example.linxj.adapter.MyAdapter;
import com.example.linxj.adapter.RecommendAdapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import qr_codescan.MipcaActivityCapture;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    //private HashMap<String, String> map = new HashMap<String, String>();
    private String url = "http://api.douban.com/book/subject/isbn/9787543639133?alt=json";
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ViewPager mRecommendPager;
    private RecommendAdapter mRecommendAdapter;
    private CollapsingToolbarLayout collapsingToolbar;
    private FloatingActionButton fab;
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private final static int SCANNIN_DETAIL = 2;
    private BookLab lab;
    private BookData b;
    private BookData book;
    List<BookData> books;
    StringBuilder sb;
    SharedPreferences sp;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         sp = getSharedPreferences("firstEnter", Context.MODE_PRIVATE);
         edit = sp.edit();
        initToolbar();
        initNavigationView();
        initData();
        initViewPage();
        initFloationButton();
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("CSDC");
        try {
          /*  InputStream is = getResources().getAssets().open("book.xml");
            lab = new BookXmlClass().parse(is);
            books = lab.getBooks();
            Iterator<Book> iterator = books.iterator();*/
            //map.clear();
            recyclerView = (RecyclerView) super.findViewById(R.id.list);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            myAdapter = new MyAdapter(this, books);
            recyclerView.setAdapter(myAdapter);
            myAdapter.setOnItemClickListener(new MyAdapter.onItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    //Toast.makeText(MainActivity.this,"wwww",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, BookDetailActivity.class);
                    Bundle b = new Bundle();
                    b.putString("isbn", books.get(position).isbn);
                    startActivity(intent.putExtras(b));

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
    *初始化数据，第一次进入从xml中读取存数据库
    *@author linxj
    */
    private void initData() {
        try {
            books = null;
            InputStream is = getResources().getAssets().open("book.xml");
            lab = new BookXmlClass().parse(is);
            books = lab.getBooks();
            if(sp.getBoolean("first",true)) {//确保只读一次
                saveAllInfo(books);
                edit.putBoolean("first",false);
                edit.commit();
            }else{
                books = getAllBooks();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAllInfo(List<BookData> books) {
        try {
            long currentTmie = SystemClock.currentThreadTimeMillis();
            ActiveAndroid.beginTransaction();
            for (int i = 0; i < books.size(); i++) {
                book = new BookData();
                b = books.get(i);
                //book.number = b.getIndex();
                book.isbn = (b == null ? "" : b.isbn);
                book.name = (b == null ? "" : b.name);
                book.time = currentTmie;
                //book.index = b.getIndex();
                book.save();
            }

            ActiveAndroid.setTransactionSuccessful();
            //Toast.makeText(MainActivity.this,"aa",Toast.LENGTH_LONG).show();

        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    public List<BookData> getAllBooks(){
        List<BookData> sb = new Select()
                .from(BookData.class)
                .orderBy("Time ASC")
                //.orderBy("Name ASC")
                .execute();
        return sb;
    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("图书管理系统");

        // toolbar.setSubtitle("CSDC");
        toolbar.setNavigationIcon(R.drawable.ic_list_black_24dp);
        toolbar.setOnMenuItemClickListener(this);
    }

    public void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_blog:
                        //startActivity(new Intent(MainActivity.this, BlogActivity.class));
                        break;
                    case R.id.nav_ver:
                        break;
                    case R.id.nav_about:
                        break;
                }
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void initViewPage() {

        mRecommendPager = (ViewPager) findViewById(R.id.pager);
        mRecommendPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                PointF downP = new PointF();
                PointF curP = new PointF();
                int act = event.getAction();
                if (act == MotionEvent.ACTION_DOWN
                        || act == MotionEvent.ACTION_MOVE
                        || act == MotionEvent.ACTION_UP) {
                    ((ViewGroup) v).requestDisallowInterceptTouchEvent(true);
                    if (downP.x == curP.x && downP.y == curP.y) {
                        return false;
                    }
                }
                return false;
            }
        });
        mRecommendPager.setAdapter(mRecommendAdapter);
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        View view1 = mLayoutInflater.inflate(R.layout.guide_one, null);
        View view2 = mLayoutInflater.inflate(R.layout.guide_two, null);
        View view3 = mLayoutInflater.inflate(R.layout.guide_end, null);

        final ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        mRecommendAdapter = new RecommendAdapter(views, this);
        mRecommendPager.setAdapter(mRecommendAdapter);
    }

    public void initFloationButton() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    //显示扫描到的内容
                    String isbn = bundle.getString("result");
                    // Toast.makeText(MainActivity.this,isbn,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, BookDetailActivity.class);
                    Bundle b = new Bundle();
                    b.putString("isbn", isbn);
                    b.putString("addBook","add");
                    startActivityForResult(intent.putExtras(b),SCANNIN_DETAIL);

                }
                break;
            case SCANNIN_DETAIL:
                if(resultCode == RESULT_OK){
                    getAllBooks();
                    myAdapter.notifyDataSetChanged();
                }
            break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                books = simpleSearch(query);
                myAdapter.setList(books);
                myAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                books = simpleSearch(newText);
                myAdapter.setList(books);
                myAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }



    @Override
    public boolean onMenuItemClick(MenuItem item) {

    /*    switch (item.getItemId()) {
           *//* case R.id.action_search:
                break;*//*
            case R.id.action_share:
                //Toast.makeText(this, "���?ť", Toast.LENGTH_SHORT).show();
                List<BookData> sb = new Select()
                        .from(BookData.class)
                        .orderBy("Time ASC")
                        .execute();
                break;
        }*/

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
     /*   if (id == R.id.action_settings) {
            return true;
        }*/
        if(id == R.id.action_search){

            SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    books = simpleSearch(query);
                    myAdapter.setList(books);
                    myAdapter.notifyDataSetChanged();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    books = simpleSearch(newText);
                    myAdapter.setList(books);
                    myAdapter.notifyDataSetChanged();
                    return false;
                }
            });
        }
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public List<BookData> simpleSearch(String str){
        List<BookData> sb = new Select()
                .from(BookData.class)
                .where("Name like ?","%"+str+"%")
                .orderBy("Time ASC")
                .execute();
        return sb;

    }

}
