package com.example.photoshare.ui.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private Button uploadButton;
    private BmobFile img;
    private Intent data;
    private static final String IMAGE_NAME = " ";
    private static final String URI_A = "/sdcard/Pictures/iu1.jpg";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        uploadButton= root.findViewById(R.id.button_upload);
        uploadButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                /* 开启Pictures画面Type设定为image */
//                intent.setType("image/*");
//                /* 使用Intent.ACTION_GET_CONTENT这个Action */
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                /* 取得相片后返回本画面 */
//                startActivityForResult(intent, 1);
//                //intent.addCategory(Intent.CATEGORY_OPENABLE);
//                Toast.makeText(getActivity(),"success",Toast.LENGTH_SHORT).show();
                BmobFile bmobFile = new BmobFile(new File(URI_A));
                bmobFile.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            UpLoadImg uploadimge = new UpLoadImg();
                            uploadimge.setFile(bmobFile);
                            //uploadimge.save();
                            //bmobFile.getFileUrl()--返回的上传文件的完整地址
                            Toast.makeText(getActivity(),"图片上传成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(),"图片上传失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onProgress(Integer value) {
                        // 返回的上传进度（百分比）
                    }
                });
//                Uri uri = data.getData();
//                resizeImage(uri);
//                // 将获取到的uri转换为String型
//                String[] images = { MediaStore.Images.Media.DATA };// 将图片URI转换成存储路径
//                Cursor cursor = getActivity().managedQuery(uri, images, null, null, null);
//                int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                cursor.moveToFirst();
//                String img_url = cursor.getString(index);
//                upload(img_url);
            }
        });
//        Button downbutton=root.findViewById(R.id.button_download);
//        downbutton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                bmobQuery.findObjects(new FindListener<GameScore>() {
//                    @Override
//                    public void done(List<GameScore> object,BmobException e) {
//                        if(e==null){
//                            for (GameScore gameScore : object) {
//                                BmobFile bmobfile = gameScore.getPic();
//                                if(file!= null){
//                                    //调用bmobfile.download方法
//                                }
//                            }
//                        }else{
//                            toast("查询失败："+e.getMessage());
//                        }
//                    }
//                });
//
//            }

//        });




//            @SuppressWarnings("unused")
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                File file = new File("/mnt/sdcard/"+Path);
//                if (file != null) {
//                    final BmobFile bmobFile = new BmobFile(file);
//                    final ProgressDialog progressDialog = new ProgressDialog(DashboardFragment.this);
//                    progressDialog.setMessage("正在上传。。。");
//                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                    progressDialog.show();
//                    bmobFile.upload(DashboardFragment.this, new UploadFileListener() {
//
//                        @Override
//                        public void onSuccess() {
//                            // TODO Auto-generated method stub
//                            String url = bmobFile.getUrl();
//                            insertObject(new UpLoadImg("绝地反击","123456",bmobFile));
//                            Toast.makeText(DashboardFragment.this, "上传成功", Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
//                        }
//
//                        @Override
//                        public void onFailure(int arg0, String arg1) {
//                            // TODO Auto-generated method stub
//                            Toast.makeText(DashboardFragment.this, "上传失败"+arg1, Toast.LENGTH_SHORT).show();
//                        }
//
//                    });
//                }else {
//                    Toast.makeText(DashboardFragment.this, "文件为空", Toast.LENGTH_SHORT).show();
//                }
//
//            }



      //  });
        /*final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }


//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        uploadButton = getActivity().findViewById(R.id.button_upload);
//        //Button button = (Button) getActivity().findViewById(R.id.button);
//        //button.setOnClickListener(new OnClickListener() {
//        //uploadButton= root.findViewById(R.id.button_upload);
//        uploadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                /* 开启Pictures画面Type设定为image */
//                intent.setType("image/*");
//                /* 使用Intent.ACTION_GET_CONTENT这个Action */
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                /* 取得相片后返回本画面 */
//                //startActivityForResult(intent, 1);
//                //intent.addCategory(Intent.CATEGORY_OPENABLE);
//            }
//        });
//        Uri uri = data.getData();
//        resizeImage(uri);
//        // 将获取到的uri转换为String型
//        String[] images = { MediaStore.Images.Media.DATA };// 将图片URI转换成存储路径
//        Cursor cursor = this.managedQuery(uri, images, null, null, null);
//        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        String img_url = cursor.getString(index);
//        upload(img_url);
//
//    }

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
//                    UpLoadImg uploadimge = new UpLoadImg();
//                    uploadimge.setFile(icon);
//                    uploadimge.save(getActivity());
                    Toast.makeText(getActivity(),"图片上传成功",Toast.LENGTH_SHORT).show();

                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    //toast("上传文件成功:" + icon.getFileUrl());
                } else {
                    //toast("上传文件失败：" + e.getMessage());
                    Toast.makeText(getActivity(),"图片上传失败",Toast.LENGTH_SHORT).show();
                }

            }  });}





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }}
