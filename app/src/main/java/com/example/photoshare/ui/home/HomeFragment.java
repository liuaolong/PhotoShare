package com.example.photoshare.ui.home;

import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.photoshare.ImageAdapter;
import com.example.photoshare.ImgAdapter;
import com.example.photoshare.R;
import com.example.photoshare.UpLoadImg;
import com.example.photoshare.databinding.FragmentHomeBinding;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private List<UpLoadImg> list;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ListView lvimg;
    private  ImageAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //private ImageLoader imageLoader = ImageLoader.getInstance();
//
        //imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

        //queryPage();
        recyclerView=root.findViewById(R.id.rl);

       // lvimg=root.findViewById(R.id.main_list_view);

        //queryPage();

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(layoutManager);
        //FruitAdapter adapter = new FruitAdapter(fruitList);

        queryPage();


        //final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

//        recyclerView = root.findViewById(R.id.recycler_view);
//        //设置RecyclerView保持固定大小,这样可以优化RecyclerView的性能
//        recyclerView.setHasFixedSize(true);
//
//        //initData();
//
//        //设置瀑布流布局为3列，垂直方向滑动
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
//
//        ImageAdapter imageAdapter = new ImageAdapter(list);
//
//        //创建分割线对象
//        //recyclerView.addItemDecoration(new MyDecoration());
//
//        BmobQuery<UpLoadImg> bmobQuery=new BmobQuery<UpLoadImg>();
//        bmobQuery.findObjects(new FindListener<UpLoadImg>(){
//            @Override
//            public void done(List<UpLoadImg> object, BmobException e) {
//                if(e==null){
//                    for (UpLoadImg uploadimge : object) {
//                        BmobFile bmobfile = uploadimge.getFile();
//                        if(bmobfile!= null){
//                            bmobfile.download(new DownloadFileListener() {
//                                @Override
//                                public void onProgress(Integer integer, long l) {
//
//                                }
//                                @Override
//                                public void done(String s, BmobException e) {
//                                    if(e == null){
//                                        //holder.UpImage.setImageBitmap(BitmapFactory.decodeFile(s));
//                                        //Toast.makeText(getActivity(),"查询成功"+s,Toast.LENGTH_SHORT).show();
//                                        // bitmapuri=s;
//                                        //bigImageLoader(BitmapFactory.decodeFile(s));
//                                    }
//                                    // else {Toast.makeText(getActivity(),"cant find it"+e.getMessage(),Toast.LENGTH_SHORT).show();}
//                                }
//                            });
//                            //调用bmobfile.download方法
//                        }
//                    }
//                    // Toast.makeText(getActivity(),"查询成功"+e.getMessage(),Toast.LENGTH_SHORT).show();
//                }else{
//                    //Toast.makeText(getActivity(),"查询失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        // holder.UpImage.setImageResource(uploadimg.getImageid());
//
//                recyclerView.setAdapter(imageAdapter);



        return root;
    }

    //数据源
//    private void initData() {
//        String[] names = new String[]{"车位查询","车位预定","与车间距","防盗警报","停车时长","车位查询","车位预定","与车间距","防盗警报","停车时长"};
//        int[] ImageId = new int[]{R.drawable.car1,R.drawable.car2,R.drawable.car3,R.drawable.car4,R.drawable.car5,R.drawable.car1,R.drawable.car2,R.drawable.car3,R.drawable.car4,R.drawable.car5};
//        for(int i = 0 ;i < names.length ; i++){
//            this.homeList.add(new Home(names[i],ImageId[i]));
//        }
//    }

    //分割线的类
//    class MyDecoration extends RecyclerView.ItemDecoration{
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
//            super.getItemOffsets(outRect,view ,parent,state);
//            int gap = getResources().getDimensionPixelSize(R.dimen.dividerHeight);//5dp
//            outRect.set(gap,gap,gap,gap);
//        }

    //查询所有数据
    public void queryPage(){
        BmobQuery<UpLoadImg> query = new BmobQuery<>();
        //查询存在“objectId”字段的数据。
        query.addWhereExists("objectId");
        //获取查询数据
        query.findObjects(new FindListener<UpLoadImg>() {
            @Override
            public void done(List<UpLoadImg> list, BmobException e) {
                if (e == null) {
                    try {
                       //vimg.setAdapter(new ImgAdapter(list));
                        //adapter=new ImageAdapter(list,getContext());
                        recyclerView.setAdapter(new ImageAdapter(list,getContext()));
                        //Toast.makeText(getActivity(), "更新列为" + list.get(2).getFile().getFileUrl(), Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(), "更新列为" + list.size()+"条", Toast.LENGTH_LONG).show();
                    }catch (Exception es){
                        es.printStackTrace();
                    }
                }else {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}