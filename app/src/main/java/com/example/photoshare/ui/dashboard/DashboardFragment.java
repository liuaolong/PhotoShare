package com.example.photoshare.ui.dashboard;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.photoshare.MainActivity;
import com.example.photoshare.R;
import com.example.photoshare.UpLoadImg;
import com.example.photoshare.databinding.FragmentDashboardBinding;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private Button uploadButton;
    private BmobFile img;
    private Intent data;
    private ImageView imageview;
    private static final String IMAGE_NAME = " ";
    private static final String URI_A = "/sdcard/Pictures/flower.png";
    private  String URI_B;
    private String bitmapuri;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imageview=root.findViewById(R.id.imageView);

        uploadButton= root.findViewById(R.id.button_upload);
        uploadButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                BmobFile bmobFile = new BmobFile(new File(URI_A));
                bmobFile.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            UpLoadImg uploadimge = new UpLoadImg();
                            uploadimge.setFile(bmobFile);
                            uploadimge.save(new SaveListener<String>() {
                                @Override
                                public void done(String objectId, BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(getActivity(), "图片上传成功" + objectId, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), "图片上传falure", Toast.LENGTH_SHORT).show();
                                    }
                                } });

                        }else{
                            Toast.makeText(getActivity(),"图片上传失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onProgress(Integer value) {

                    }
                });

            }
        });
        Button downbutton=root.findViewById(R.id.button_download);
        downbutton.setOnClickListener(new View.OnClickListener(){
            BmobQuery bmobQuery=new BmobQuery();
            @Override
            public void onClick(View v) {
                bmobQuery.findObjects(new FindListener<UpLoadImg>() {
                    @Override
                    public void done(List<UpLoadImg> object, BmobException e) {
                        if(e==null){
                            for (UpLoadImg uploadimge : object) {
                                BmobFile bmobfile = uploadimge.getFile();
                                if(bmobfile!= null){
                                    bmobfile.download(new DownloadFileListener() {
                                        @Override
                                        public void onProgress(Integer integer, long l) {

                                        }
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if(e == null){
                                                imageview.setImageBitmap(BitmapFactory.decodeFile(s));
                                                Toast.makeText(getActivity(),"查询成功"+s,Toast.LENGTH_SHORT).show();
                                                bitmapuri=s;
                                                //bigImageLoader(BitmapFactory.decodeFile(s));

                                            }
                                            else {Toast.makeText(getActivity(),"cant find it"+e.getMessage(),Toast.LENGTH_SHORT).show();}
                                        }
                                    });

                                }
                            }

                        }else{
                            Toast.makeText(getActivity(),"查询失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });bigImageLoader(BitmapFactory.decodeFile(bitmapuri));

            }

        });








        return root;
    }


    private void bigImageLoader(Bitmap bitmap){
        final Dialog dialog = new Dialog(getActivity());
        ImageView image = new ImageView(getContext());
        image.setImageBitmap(bitmap);
        dialog.setContentView(image);
        //将dialog周围的白块设置为透明
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //显示
        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.show();
            }
        });
        dialog.show();
       // 点击图片取消
        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.cancel();
            }
        });
    }


    public void resizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");// 可以裁剪
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2);// 跳转，传递调整大小请求码
    }

    private Uri getImageUri() {
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                IMAGE_NAME));
    }

    private void upload(String imgpath) {
        final BmobFile icon = new BmobFile(new File(imgpath));
        icon.upload (new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    UpLoadImg uploadimge = new UpLoadImg();
                    uploadimge.setFile(icon);
                    uploadimge.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                Toast.makeText(getActivity(),"图片上传成功"+objectId,Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(),"图片上传falure",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {

                    Toast.makeText(getActivity(),"图片上传失败",Toast.LENGTH_SHORT).show();
                }

            }  });}





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }}
