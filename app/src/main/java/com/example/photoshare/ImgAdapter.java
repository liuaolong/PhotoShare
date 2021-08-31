package com.example.photoshare;

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
            //LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, null);
//            viewHolder.titleContent = (TextView) convertView.findViewById(R.id.title_content);
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
//            viewHolder.tv_name.setText(name);
//            viewHolder.tv_price.setText(String.valueOf(price));   //将double数据放到textView中
//            viewHolder.tv_desc.setText(desc);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 创建DisplayImageOptions对象并进行相关选项配置
//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_launcher_background)// 设置图片下载期间显示的图片
//                .showImageForEmptyUri(R.drawable.ic_launcher_background)// 设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail(R.drawable.ic_launcher_background)// 设置图片加载或解码过程中发生错误显示的图片
//                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
//                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
//                .displayer(new RoundedBitmapDisplayer(20))// 设置成圆角图片
//                .build();// 创建DisplayImageOptions对象
        // 使用ImageLoader加载图片
        //imageLoader.displayImage(lists.get(position).getFile().getFileUrl(),viewHolder.icon);
       // viewHolder.titleContent.setText(lists.get(position).getName());
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
