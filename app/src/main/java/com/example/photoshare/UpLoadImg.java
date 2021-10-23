package com.example.photoshare;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class UpLoadImg extends BmobObject {
//    private String Name;
//    private String password;
    private BmobFile file;
    private Integer like_num;
   //private  String objectId;

//    public UpLoadImg(String Name,String password,BmobFile file){
//        this.Name = Name;
//        this.password = password;
//        this.file = file;
//    }

//    public String getName() {
//        return Name;
//    }
//    public void setName(String name) {
//        Name = name;
//    }
//    public String getPassword() {
//        return password;
//    }
//    public void setPassword(String password) {
//        this.password = password;
//    }
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

//    public String getObjectid(){
//        return objectId;
//    }

    public String getfileurl(){
        return file.getFileUrl();}

}
