package com.example.photoshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity //implements View.OnClickListener{
{
    private EditText etPwd;
    private EditText etAccount;
    private CheckBox cbRememberPwd;
    private String password ;
    private String account;
    private String spFileName;
    private String accountKey;


    private String passwordKey;
    private String rememberPasswordKey;
    private SharedPreferences spFile;
    private Boolean rememberPassword;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bmob.initialize(this, "f964d85568eec03d9551a9d29a53bfe3");

        etPwd = findViewById(R.id.et_pwd);
        etAccount = findViewById(R.id.et_account);
        cbRememberPwd = (CheckBox)findViewById(R.id.cb_remember_pwd);
        Button btLogin = findViewById(R.id.bt_login);
        Button btRegiser=findViewById(R.id.bt_register);

        btRegiser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //获取用户输入的账号和密码
                String new_username = etAccount.getText().toString();
                String new_password = etPwd.getText().toString();
                BmobUser user = new BmobUser();
                user.setUsername(new_username);
                user.setPassword(new_password);
                //注册
                user.signUp(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser user, BmobException e) {
                        if (e == null) {
                            Toast.makeText(MainActivity.this, "注册成功" + "用户名:" + new_username + "密码:" + new_password, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "注册失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
               }
             });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_username = etAccount.getText().toString();
                String new_password = etPwd.getText().toString();
                BmobUser user = new BmobUser();
                user.setUsername(new_username);
                user.setPassword(new_password);
                //登录
                user.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser user, BmobException e) {
                        if(e == null){
                           Intent intent = new Intent(MainActivity.this,Index.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this,"登录失败"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });




       // btLogin.setOnClickListener(this);

       /* spFileName = getResources()
                .getString(R.string.shared_preferences_file_name);
        accountKey = getResources()
                .getString(R.string.login_account_name);
        passwordKey =  getResources()
                .getString(R.string.login_password);
        rememberPasswordKey = getResources()
                .getString(R.string.login_remember_password);*/

        /*spFile = getSharedPreferences(
                spFileName,
                MODE_PRIVATE);
        editor = spFile.edit();
        account = spFile.getString(accountKey, null);
        password = spFile.getString(passwordKey, null);
        rememberPassword = spFile.getBoolean(
                rememberPasswordKey,
                false);

        if (account != null && !TextUtils.isEmpty(account)) {
            etAccount.setText(account);
        }

        if (password != null && !TextUtils.isEmpty(password)) {
            etPwd.setText(password);
        }

        cbRememberPwd.setChecked(rememberPassword);
    }
    @Override
    public void onClick(View view) {if (cbRememberPwd.isChecked()) {
        String password1 = etPwd.getText().toString();
        String account1 = etAccount.getText().toString();

        editor.putString(accountKey, account1);
        editor.putString(passwordKey, password1);
        editor.putBoolean(rememberPasswordKey, true);
        editor.apply();
    }
    else {
        editor.remove(accountKey);
        editor.remove(passwordKey);
        editor.remove(rememberPasswordKey);
        editor.apply();
    }*/
    }
}