package com.example.kqnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private Button clickBtn,weather_button;

    TextView[] note_all=new TextView[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //为当前用户创建note表
        DBHelper helper=new DBHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        sql_query(db);

        //返回按钮
        clickBtn=(Button)findViewById(R.id.clickBtn);
        clickBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Login.class) ;
                startActivity(intent) ;
                finish();
            }
        });
        //点击天气去往天气界面
        weather_button=(Button)findViewById(R.id.weather_button);
        weather_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, weather.class) ;
                startActivity(intent) ;
                finish();
            }
        });
        //监听添加按钮 让后将便签写入数据库中
        Button add_note = (Button) findViewById(R.id.add_note);
        EditText input_note=(EditText)findViewById(R.id.input_note);
        add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note_content = input_note.getText().toString();
                //insert data
                if (note_content.equals("")){
                    input_note.setHint("内容不能为空");

                }
                else{
                ContentValues values = new ContentValues();
                values.put("name", "admin");
                values.put("note", note_content);
                db.insert("UserTbl", null, values);
                sql_query(db);
                input_note.setText("");
                }
            }

        });


    }
    //用于查询并显示便签内容
    public void sql_query(SQLiteDatabase db){
        Cursor c = db.query("UserTbl" , null, null, null, null, null, null);
        //关联布局中的显示
        String[] note={"note1","note2","note3","note4"};
        int[] to={R.id.note1,R.id.note2,R.id.note3,R.id.note4};
        for(int i=0;i<4;i++){
            note_all[i]=(TextView)findViewById(to[i]);
        }
        c.moveToLast();
        if(c != null){
            int i=0;
            do{
                String note_content=c.getString(c.getColumnIndex("note"));
                Log.d("geshu","i");
                note_all[i].setText(note_content);
                note_all[i].setVisibility(View.VISIBLE);
                i++;
            }while(c.moveToPrevious()&&i<4);
        }
    }
}
