package com.example.photoshare;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.myviewholder> {
    private List<UpLoadImg> list;
    private View inflater;
    private  String bitmapuri;

    public ImageAdapter(List<UpLoadImg> UpLoadImgList) {
        this.list = UpLoadImgList;
    }

    @Override
    public ImageAdapter.myviewholder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        inflater = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_item,viewGroup,false);
        myviewholder myviewholder = new myviewholder(inflater);
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.myviewholder holder, int position) {
//        String bitmapuri;
        UpLoadImg uploadimg = list.get(position);
        BmobFile bmobfile = uploadimg.getFile();
        if(bmobfile!= null){
            bmobfile.download(new DownloadFileListener() {
                @Override
                public void onProgress(Integer integer, long l) {

                }
                @Override
                public void done(String s, BmobException e) {
                    if(e == null){
                        //imageview.setImageBitmap(BitmapFactory.decodeFile(s));
                        //Toast.makeText(getActivity(),"查询成功"+s,Toast.LENGTH_SHORT).show();
                         bitmapuri=s;
                        //bigImageLoader(BitmapFactory.decodeFile(s));

                    }
                    //else {Toast.makeText(getActivity(),"cant find it"+e.getMessage(),Toast.LENGTH_SHORT).show();}
                }
            });
            //调用bmobfile.download方法
        }

        //holder.UpImage.setImageBitmap(BitmapFactory.decodeFile(bitmapuri));

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class myviewholder extends RecyclerView.ViewHolder{
        View ImgView;
        ImageView UpImage;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            ImgView = itemView;
            UpImage = itemView.findViewById(R.id.imageView);
        }
    }

}
