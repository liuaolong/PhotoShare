package com.example.photoshare;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.myviewholder> {
    private List<UpLoadImg> list;
    private View inflater;
    private  String bitmapuri;
    private Context mContext;

    public ImageAdapter(List<UpLoadImg> UpLoadImgList,Context context) {
        this.list = UpLoadImgList;
        this.mContext=context;
    }

    @Override
    public ImageAdapter.myviewholder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        inflater = LayoutInflater.from(mContext).inflate(R.layout.home_item,viewGroup,false);
        myviewholder myviewholder = new myviewholder(inflater);
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.myviewholder holder, int position) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String url = list.get(position).getFile().getFileUrl();
//                Glide.with(mContext).load(url).into(holder.UpImage);
//            }});
        holder.Textview.setText(""+list.get(position).getLike_num());

        new Thread(new Runnable() {
            @Override
            public void run() {
                //holder.Textview.setText(list.get(position).getLike_num());
                Bitmap bitmap=getPicture(list.get(position).getFile().getFileUrl());

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                    holder.UpImage.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.UpImage.setImageBitmap(bitmap);//将图片放到视图中去
                        }
                    });}
            }).start();
            //do nothing


//        String bitmapuri;
//        UpLoadImg uploadimg = list.get(position);
//        BmobFile bmobfile = uploadimg.getFile();
//        if(bmobfile!= null){
//            bmobfile.download(new DownloadFileListener() {
//                @Override
//                public void onProgress(Integer integer, long l) {
//
//                }
//                @Override
//                public void done(String s, BmobException e) {
//                    if(e == null){
//                        //imageview.setImageBitmap(BitmapFactory.decodeFile(s));
//                        //Toast.makeText(getActivity(),"查询成功"+s,Toast.LENGTH_SHORT).show();
//                         bitmapuri=s;
//                        //bigImageLoader(BitmapFactory.decodeFile(s));
//
//                    }
//                    //else {Toast.makeText(getActivity(),"cant find it"+e.getMessage(),Toast.LENGTH_SHORT).show();}
//                }
//            });
//            //调用bmobfile.download方法
//        }

        //holder.UpImage.setImageBitmap(BitmapFactory.decodeFile(bitmapuri));

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

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        CardView ImgView;
        ImageView UpImage;
        TextView Textview;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            ImgView = (CardView)itemView;
            UpImage = itemView.findViewById(R.id.title_pic);
            Textview = itemView.findViewById(R.id.like_num);
        }
    }

}
