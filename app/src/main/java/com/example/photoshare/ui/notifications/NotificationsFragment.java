package com.example.photoshare.ui.notifications;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.photoshare.MainActivity;
import com.example.photoshare.R;
import com.example.photoshare.databinding.FragmentNotificationsBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private ImageView ima;

    private Bitmap head;// 头像Bitmap
    //private static String path = "/sdcard/Pictures/";// sd路径

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        ima = root.findViewById(R.id.touxiang);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final AlertDialog dialog = builder.create();
        View view = View.inflate(getContext(), R.layout.fragment_notifications, null);

        //打开相册
        ima.setOnClickListener(new View.OnClickListener() {// 在相册中选取

            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                /* 开启Pictures画面Type设定为image */
//                intent.setType("image/*");
//                /* 使用Intent.ACTION_GET_CONTENT这个Action */
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                /* 取得相片后返回本画面 */
//                startActivityForResult(intent, 1);
                //intent.addCategory(Intent.CATEGORY_OPENABLE);
//                Toast.makeText(getActivity(),"success",Toast.LENGTH_SHORT).show();
//
                //initChoosePhoto();

                // 相册选择图片
                Intent data = new Intent(Intent.ACTION_PICK, null);
                data.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(data, 1);
                //dialog.dismiss();
                // Toast.makeText(getActivity(), "注册失败" , Toast.LENGTH_LONG).show();
                onActivityResult(1, 1, data);
                Uri originalUri = data.getData();
                String URI_B = getRealPathFromUriAboveApi19(getContext(),originalUri);
                //URI_A = getRealPathFromUriAboveApi19(getContext(),originalUri);
                Bitmap bt = BitmapFactory.decodeFile(URI_B);
                if (bt != null) {
                    Toast.makeText(getActivity(), "注", Toast.LENGTH_LONG).show();
                    ima.setImageBitmap(bt);

                } else {
                    Toast.makeText(getActivity(), "0000", Toast.LENGTH_LONG).show();

                }

            }

                private String getRealPathFromUriBelowAPI19 (Context context, Uri uri){
                    return getDataColumn(context, uri, null, null);
                }


            /**
             * 适配api19及以上,根据uri获取图片的绝对路径
             *
             * @param context 上下文对象
             * @param uri     图片的Uri
             * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
             */
            //@SuppressLint("NewApi")
            private String getRealPathFromUriAboveApi19(Context context, Uri uri) {
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
             *
             * @return
             */
            private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
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
            private boolean isMediaDocument(Uri uri) {
                return "com.android.providers.media.documents".equals(uri.getAuthority());
            }

            /**
             * @param uri the Uri to check
             * @return Whether the Uri authority is DownloadsProvider
             */
            private boolean isDownloadsDocument(Uri uri) {
                return "com.android.providers.downloads.documents".equals(uri.getAuthority());
            }



//        String URI_B = originalUri.toString();
//        //URI_A = getRealPathFromUriAboveApi19(getContext(),originalUri);
//        Bitmap bt = BitmapFactory.decodeFile(URI_B);
//        if (bt != null) {
//            Toast.makeText(getActivity(), "注", Toast.LENGTH_LONG).show();
//            ima.setImageBitmap(bt);
//
//        } else {
//            Toast.makeText(getActivity(), "0000", Toast.LENGTH_LONG).show();
//
//        }



        protected void onActivityResult ( int requestCode, int resultCode, Intent data) {
            if (resultCode == 1) {
                Log.e("TAG->onresult", "ActivityResult resultCode error");
                return;
            }}
        });
   // });
//          Bitmap bt = BitmapFactory.decodeFile(path);// 从SD卡中找头像，转换成Bitmap
//            if (bt != null) {
//                ima.setImageBitmap(bt);
//            }

/*
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();

                //Toast.makeText(getActivity(), "注册失败" , Toast.LENGTH_LONG).show();

            }
        });
dialog.setView(view);
 dialog.show();
*/
        return root;
}


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


