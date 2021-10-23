package com.example.photoshare;

/*
* 用于初步开发用
* 现已更换为imagedapter类
* android大作业
*
*/
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

import static androidx.core.content.ContextCompat.getSystemService;

public class ImgAdapter extends BaseAdapter {
    private List<UpLoadImg> lists;
    private ImageLoader imageLoader;

    public ImgAdapter(List<UpLoadImg> list) {
        this.lists=list;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            BmobFile image;
                image = lists.get(position).getFile();

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, null);

            viewHolder.icon = (ImageView) convertView.findViewById(R.id.title_pic);
            convertView.setTag(viewHolder);

            ViewHolder finalViewHolder = viewHolder;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //根据表中图片的url地址来得到图片（Bitmap类型）
                    final Bitmap bitmap=getPicture(lists.get(position).getFile().getFileUrl());
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finalViewHolder.icon.post(new Runnable() {
                        @Override
                        public void run() {
                            finalViewHolder.icon.setImageBitmap(bitmap);//将图片放到视图中去
                        }
                    });
                }
            }).start();


        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return  convertView;
    }

    public Bitmap getPicture(String path){
        Bitmap bm=null;
        try{
            URL url=new URL(path);
            URLConnection connection=url.openConnection();
            connection.connect();
            InputStream inputStream=connection.getInputStream();
            bm= BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  bm;
    }


    public class  ViewHolder{
        TextView titleContent;
        ImageView icon;
    }
}
