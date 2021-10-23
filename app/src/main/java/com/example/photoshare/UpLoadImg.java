package com.example.photoshare;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class UpLoadImg extends BmobObject {

    private BmobFile file;
    private Integer like_num;

    public BmobFile getFile() {

        return file;
    }

    public Integer getLike_num(){
        return like_num;
    }

    public void setLike_num(Integer like_num){
        this.like_num=like_num;
    }

    public void setFile(BmobFile file) {

        this.file = file;
    }


    public String getfileurl(){
        return file.getFileUrl();}

}
