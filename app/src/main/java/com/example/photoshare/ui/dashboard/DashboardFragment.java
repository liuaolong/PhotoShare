package com.example.photoshare.ui.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import cn.bmob.v3.listener.UploadFileListener;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private Button uploadButton;
    private BmobFile img;

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
                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intent, 1);
            }
        });


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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}