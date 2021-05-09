package com.example.kqnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity{
    EditText user_accout;
    EditText user_password;
    Button login;
    TextView tips;
    Button register;
    //DBHelper helpter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        //启动当前activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 设置输入内容两个文本
        user_accout =(EditText)findViewById(R.id.user_accout);
        user_password=(EditText)findViewById(R.id.user_password);
        // 显示提示
        tips=(TextView)findViewById(R.id.tips);
        // 登录按钮
        login=(Button)findViewById(R.id.regesit);
        // 注册按钮
        register=(Button)findViewById(R.id.regesiter);
        // 新建两个activity，一个是登录成功后的，一个是当前的
        Intent note_activity = new Intent(Login.this,MainActivity.class);
        Intent login_activity = new Intent(Login.this,Login.class);
        // 登录按键监听
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取当前输入的账号密码
                String user_name = user_accout.getText().toString();
                String user_pass = user_password.getText().toString();

                // 根据账号密码查询当前数据库是否有匹配对象，有则直接登陆，没有则提示账号或密码错误
                DBHelper helpter = new DBHelper(getApplicationContext());
                SQLiteDatabase db = helpter.getWritableDatabase();
                Cursor c = db.query("AccountTbl" , null, "name=\""+user_name+"\""+" and password=\""+user_pass+"\"", null, null, null, null);
                if(c!=null && c.getCount() >= 1){
                    c.close();
                    db.close();
                    Login.this.finish();
                    startActivity(note_activity);
                }
                tips.setText("账户或密码不正确");
                tips.setVisibility(View.VISIBLE);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name= user_accout.getText().toString();
                String user_pass= user_password.getText().toString();

                DBHelper helper = new DBHelper(getApplicationContext());
                SQLiteDatabase db = helper.getWritableDatabase();

                //根据画面上输入的账号去数据库中进行查询
                Cursor c = db.query("AccountTbl",null,"name=\""+user_name+"\"",null,null,null,null);
                //如果有查询到数据，则说明账号已存在
                if(c!=null && c.getCount() >= 1){
                    tips.setText("该用户已存在");
                    tips.setVisibility(View.VISIBLE);

                    String[] cols = c.getColumnNames();

                    c.close();
                }
                //如果没有查询到数据，则往数据库中insert一笔数据
                else{
                    //insert data
                    ContentValues values= new ContentValues();
                    values.put("name",user_name);
                    values.put("password",user_pass);
                    long rowid = db.insert("AccountTbl",null,values);
                    tips.setText("注册成功");
                    tips.setVisibility(View.VISIBLE);
                    //this.finish();
                }
                db.close();

            }
        });
    }
}