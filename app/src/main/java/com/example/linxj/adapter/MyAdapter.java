package com.example.linxj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.linxj.Model.BookData;
import com.example.linxj.xmlpull.R;

import java.util.List;

public class MyAdapter
    extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
public interface onItemClickListener{
    void onItemClick(View view, int position);
}
    public onItemClickListener monItemClickListener;
    public void setOnItemClickListener(onItemClickListener onItemClickListener){
    this.monItemClickListener = onItemClickListener;
}

    private List<BookData> books;

    private Context mContext;

    public MyAdapter( Context context , List<BookData> books)
    {
        this.mContext = context;
        this.books = books;
        //this.actors = actors;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i )
    {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listviewitem, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( final ViewHolder viewHolder,final int i )
    {
        // 给ViewHolder设置元素
        BookData p = books.get(i);
        viewHolder.mTextView.setText(p.name);
        if(monItemClickListener != null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monItemClickListener.onItemClick(viewHolder.itemView,i);
                }
            });

        }
        //viewHolder.mImageView.setImageDrawable(mContext.getDrawable(p.getImageResourceId(mContext)));
    }

    @Override
    public int getItemCount()
    {
        // 返回数据总数
       // return actors == null ? 0 : actors.size();
        return  books == null ? 0:books.size();
    }

    public void setList(List<BookData> bs){
        this.books = bs;
    }

    // 重写的自定义ViewHolder
    public static class ViewHolder
        extends RecyclerView.ViewHolder
    {
        public TextView mTextView;

        //public ImageView mImageView;

        public ViewHolder( View v )
        {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.name);
            //mImageView = (ImageView) v.findViewById(R.id.pic);
        }
    }
}
