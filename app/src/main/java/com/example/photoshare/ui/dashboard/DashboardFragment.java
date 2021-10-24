package com.example.photoshare.ui.dashboard;


import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.photoshare.R;
import com.example.photoshare.UpLoadImg;
import com.example.photoshare.databinding.FragmentDashboardBinding;
import java.io.File;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

        public class DashboardFragment extends Fragment{

            private DashboardViewModel dashboardViewModel;
            private FragmentDashboardBinding binding;
            private Button uploadButton;
            private ImageView imageview;

            private  String URI_A ;//= "/sdcard/Pictures/flower.png";
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
                        Intent data = new Intent(Intent.ACTION_PICK, null);
                        data.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//打开相册
                        startActivityForResult(data, 1);//选择图片
                        Uri originalUri = data.getData(); //  获取相对路径
                        URI_A = getRealPathFromUriAboveApi19(getContext(),originalUri);//  获取绝对路径
                        Toast.makeText(getActivity(), ""+URI_A , Toast.LENGTH_LONG).show();

                        // 显示最新shang图片
                        Bitmap bm = BitmapFactory.decodeFile(URI_A);
                        imageview.setImageBitmap(bm);

                        //将获取绝对路径的图片发送到Bomb后端
                        BmobFile bmobFile = new BmobFile(new File(URI_A));
                        bmobFile.upload(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    UpLoadImg uploadimge = new UpLoadImg();
                                    uploadimge.setFile(bmobFile);
                                    uploadimge.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String objectId, BmobException e) {
                                            if (e == null) {
                                                Toast.makeText(getActivity(), "图片上传成功" + objectId, Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getActivity(), "图片上传失败", Toast.LENGTH_SHORT).show();
                                            }

                                            //更新UpLoadImg表里面id为6b6c11c537的数据，address内容更新为“0”
                                            UpLoadImg p2 = new UpLoadImg();
                                            p2.setLike_num(0);
                                            p2.update(objectId, new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if(e==null){
                                                        Toast.makeText(getActivity(), "图片更新成功" + objectId, Toast.LENGTH_SHORT).show();
                                                    }else{
                                                        // toast("更新失败：" + e.getMessage());
                                                        Toast.makeText(getActivity(), "图片更新失败" + objectId, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                } else {
                                    Toast.makeText(getActivity(), "图片上传失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    /**
                     * 适配api19及以上,根据uri获取图片的绝对路径
                     * @param context 上下文对象
                     * @param uri  图片的Uri
                     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
                     */
                    @SuppressLint("NewApi")
                    private  String getRealPathFromUriAboveApi19(Context context, Uri uri) {
                        String filePath = null;
                        if (DocumentsContract.isDocumentUri(context, uri)) {
                            // 如果是document类型的 uri, 则通过document id来进行处理
                            String documentId = DocumentsContract.getDocumentId(uri);
                            if (isMediaDocument(uri)) { // MediaProvider
                                // 使用':'分割
                                String id = documentId.split(":")[1];

                                String selection = MediaStore.Images.Media._ID + "=?";
                                String[] selectionArgs = {id};
                                filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
                            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                                filePath = getDataColumn(context, contentUri, null, null);
                            }
                        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                            // 如果是 content 类型的 Uri
                            filePath = getDataColumn(context, uri, null, null);
                        } else if ("file".equals(uri.getScheme())) {
                            // 如果是 file 类型的 Uri,直接获取图片对应的路径
                            filePath = uri.getPath();
                        }
                        return filePath;
                    }

                    /**
                     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
                     * @return
                     */
                    private  String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
                        String path = null;

                        String[] projection = new String[]{MediaStore.Images.Media.DATA};
                        Cursor cursor = null;
                        try {
                            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                                path = cursor.getString(columnIndex);
                            }
                        } catch (Exception e) {
                            if (cursor != null) {
                                cursor.close();
                            }
                        }
                        return path;
                    }

                    /**
                     * @param uri the Uri to check
                     * @return Whether the Uri authority is MediaProvider
                     */
                    private  boolean isMediaDocument(Uri uri) {
                        return "com.android.providers.media.documents".equals(uri.getAuthority());
                    }

                    /**
                     * @param uri the Uri to check
                     * @return Whether the Uri authority is DownloadsProvider
                     */
                    private  boolean isDownloadsDocument(Uri uri) {
                        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
                    }
                });

                return root;
            }

            @Override
            public void onDestroyView() {
                super.onDestroyView();
                binding = null;
            }
        }
