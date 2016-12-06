package com.example.linxj.xmlpull;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linxj.Model.BookData;
import com.example.linxj.Model.BookInfo;
import com.example.linxj.com.example.linxj.ui.ProgressHUD;
import com.example.linxj.net.NetAssistant;
import com.example.linxj.tool.MyJsonParse;
import com.example.linxj.tool.NetworkConnectStatus;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by linxj on 2015/9/19.
 */
public class BookDetailActivity extends AppCompatActivity {

    @Bind(R.id.book_title)
    TextView bookTitle;
    @Bind(R.id.book_pic)
    ImageView bookPic;
    @Bind(R.id.book_Author)
    TextView bookAuthor;
    @Bind(R.id.book_abstract)
    TextView bookAbstract;
    String url;
    View view;
    @Bind(R.id.txv)
    LinearLayout txv;
    @Bind(R.id.tv_emptyview)
    TextView tvEmptyview;

    @Bind(R.id.fabDetail)
    FloatingActionButton mFab;
    Typeface mRobotoBold;
    Typeface mRobotoThin;

    public NetworkConnectStatus mNetworkStatus;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    private ProgressHUD mProgressHUD;
    private boolean isAdd = false;
    private String isbn;
    private BookInfo info;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            //Toast.makeText(BookDetailActivity.this,msg.obj.toString(),Toast.LENGTH_LONG).show();
            //  Log.e("MyInfo", new MyJsonParse().parse(msg.obj.toString()));
            if (msg.obj.toString().equals(" ")) {
                Toast.makeText(BookDetailActivity.this, "请扫描书本", Toast.LENGTH_SHORT).show();
                tvEmptyview.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                txv.setVisibility(View.GONE);
            } else {

                txv.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.VISIBLE);
                tvEmptyview.setVisibility(View.GONE);
                mRobotoBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
                mRobotoThin = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
                bookTitle.setTypeface(mRobotoBold);
                bookAuthor.setTypeface(mRobotoThin);
                info = new MyJsonParse().parseInfoBook(msg.obj.toString());
                bookTitle.setText(info.getTitle().getTitle());
                bookAuthor.setText(info.getAuthor().get(0).getName().getAuthorName());
                bookAbstract.setText(info.getContent().getContent());
                Picasso.with(BookDetailActivity.this).load(info.getLink().get(2).getLhref()).placeholder(R.drawable.book_head).error(R.drawable.book_head).into(bookPic);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookdetail);
        ButterKnife.bind(this);

        if (this.getIntent().hasExtra("addBook")) {//添加book
            mFab.setVisibility(View.VISIBLE);
            isAdd = true;
        } else {
            mFab.setVisibility(View.GONE);
        }
        mNetworkStatus = new NetworkConnectStatus(BookDetailActivity.this);
        isbn = this.getIntent().getStringExtra("isbn");
        url = "http://api.douban.com/book/subject/isbn/" + isbn + "?alt=json";
        ExecutorService executor = Executors.newCachedThreadPool();
        if (mNetworkStatus.isConnectInternet()) {
            mProgressHUD = ProgressHUD.show(BookDetailActivity.this, "正在加载", true, true, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mProgressHUD.dismiss();
                }
            });
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    String s = new NetAssistant(url).getBookInfoFromNet();
                    Log.e("info", s);
                    Message message = handler.obtainMessage();
                    message.obj = s;
                    message.sendToTarget();
                    mProgressHUD.dismiss();

                }
            });
        } else {
            tvEmptyview.setVisibility(View.VISIBLE);
            txv.setVisibility(View.GONE);
            scrollView.setVisibility(View.GONE);
            Toast.makeText(BookDetailActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();

            /*Snackbar snackbar = Snackbar.make(view,"请检查网络",Snackbar.LENGTH_SHORT);
            snackbar.show();
            snackbar.setAction("取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(BookDetailActivity.this, "SnackBar action", Toast.LENGTH_SHORT).show();
                }
            });*/

        }

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long currentTmie = SystemClock.currentThreadTimeMillis();
                BookData book = new BookData();
                //book.number = b.getIndex();
                book.isbn = isbn;
                book.name = info.getTitle().getTitle();
                book.time = currentTmie;
                book.save();
                setResult(Activity.RESULT_OK);
                Toast.makeText(BookDetailActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


}
