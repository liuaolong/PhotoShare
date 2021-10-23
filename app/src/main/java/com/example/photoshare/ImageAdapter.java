package com.example.photoshare;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.myviewholder> {
    private List<UpLoadImg> list;
    private View inflater;
    private  String bitmapuri;
    private Context mContext;
    Bitmap bm;

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

//        myviewholder.UpImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

//        myviewholder.Like.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

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

        holder.Like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer like_number=list.get(position).getLike_num();
                //Toast.makeText(mContext, "like succeed", Toast.LENGTH_SHORT).show();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
                UpLoadImg p2 = new UpLoadImg();
                p2.setLike_num(like_number+1);
                p2.update(list.get(position).getObjectId(), new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(mContext, "like succeed", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(mContext, "like f", Toast.LENGTH_SHORT).show();
                        }
                    }

                });//}});
                list.get(position).setLike_num(like_number+1);
                holder.Textview.setText(""+list.get(position).getLike_num());
            }
        });

        holder.Down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(mContext, "download succeed", Toast.LENGTH_SHORT).show();

                BmobQuery<UpLoadImg> bmobQuery = new BmobQuery<UpLoadImg>();
                bmobQuery.getObject(list.get(position).getObjectId(), new QueryListener<UpLoadImg>() {
                    @Override
                    public void done(UpLoadImg object,BmobException e) {
                        if(e==null){
                            BmobFile bmobfile = object.getFile();
                            if(bmobfile!= null){
                                //调用bmobfile.download方法
                                downloadFile(bmobfile);
                                //Toast.makeText(mContext, "download succeed", Toast.LENGTH_SHORT).show();
                            }
                            //Toast.makeText(mContext, "download succeed", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(mContext, "download f", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//                UpLoadImg p2 = new UpLoadImg();
//                //p2.setLike_num(like_number+1);
//                p2.update(list.get(position).getObjectId(), new UpdateListener() {
//
//                    @Override
//                    public void done(BmobException e) {
//                        if(e==null){
//                            Toast.makeText(mContext, "like succeed", Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(mContext, "like f", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                });


                //Integer like_number=list.get(position).getLike_num();
                //Toast.makeText(mContext, "like succeed", Toast.LENGTH_SHORT).show();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                UpLoadImg p2 = new UpLoadImg();
//                p2.setLike_num(like_number+1);
//                p2.update(list.get(position).getObjectId(), new UpdateListener() {
//
//                    @Override
//                    public void done(BmobException e) {
//                        if(e==null){
//                            Toast.makeText(mContext, "like succeed", Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(mContext, "like f", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                });//}});
//                list.get(position).setLike_num(like_number+1);
//                holder.Textview.setText(""+list.get(position).getLike_num());
            }
        });

        holder.UpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int like_number=list.get(position).getLike_num();
//                list.get(position).setLike_num(like_number+1);
   //             Bitmap bitmap0 = ((BitmapDrawable)bm.getDrawable()).getBitmap();
    //            bigImageLoader(bitmap0);
//                v.getContext().startActivity(
//                        new Intent(v.getContext(), OtherActivity.class),
//                        // 注意这里的sharedView
//                        // Content，View（动画作用view），String（和XML一样）
//                        ActivityOptions.makeSceneTransitionAnimation((Activity) view.getContext(), view, "sharedView").toBundle());



            }
        });






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

    private void downloadFile(BmobFile file){
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"

        String res=mContext.getFilesDir().toString();
        File saveFile = new File(//Environment.getExternalStorageDirectory(),
               //"/data/data/com.example.photoshare/databases"
                res
                ,file.getFilename());
        file.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
                //Toast.makeText(mContext, "download begin", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void done(String savePath,BmobException e) {
                if(e==null){
                    try {
                    MediaStore.Images.Media.insertImage(mContext.getContentResolver(),savePath,file.getFilename(),null);

                    Toast.makeText(mContext, "download succeed!!!", Toast.LENGTH_SHORT).show();}
                    catch (Exception e1) {

                            e.printStackTrace();}
                }else{
                    Toast.makeText(mContext, "download f"+e.getErrorCode()+","+e.getMessage(), Toast.LENGTH_SHORT).show();
                    //toast("下载失败："+e.getErrorCode()+","+e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value, long newworkSpeed) {
                Log.i("bmob","下载进度："+value+","+newworkSpeed);
            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        CardView ImgView;
        ImageView UpImage;
        TextView Textview;
        ImageView Like;
        ImageView Down;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            ImgView = (CardView)itemView;
            UpImage = itemView.findViewById(R.id.title_pic);
            Textview = itemView.findViewById(R.id.like_num);
            Like=itemView.findViewById(R.id.like);
            Down=itemView.findViewById(R.id.download);

        }
    }

//    private void bigImageLoader(Bitmap bitmap,Dialog dialog1,ImageView image1){
//        final Dialog dialog=dialog1 ;//= new Dialog(getActivity());
//        ImageView image=image1;// = new ImageView(getContext());
//        image.setImageBitmap(bitmap);
//        dialog.setContentView(image);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.show();
//        image.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                dialog.cancel();
//            }
//        });
//    }




}
