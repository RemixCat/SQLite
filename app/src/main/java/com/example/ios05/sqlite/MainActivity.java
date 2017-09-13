package com.example.ios05.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity{

    private Context mContext;
    private Button createBtn;
    private Button btn_insert;
    private Button btn_query;
    private Button btn_update;
    private Button btn_delete;
    private MyDBOpenHelper myDBHelper;
    private StringBuilder sb;
    private int i = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

        createBtn = (Button)findViewById(R.id.createBtn);
        btn_insert = (Button) findViewById(R.id.btn_insert);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_delete = (Button) findViewById(R.id.btn_delete);


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createn();
            }
        });

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });

        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updata();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });


    }

    public void createn(){
        MyDBOpenHelper dbHelper = new MyDBOpenHelper(mContext,"my_db",null,1);
//得到一个可读的SQLiteDatabase对象
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        Toast.makeText(mContext, "创建完毕~", Toast.LENGTH_SHORT).show();
    }


    private void insert(){
        MyDBOpenHelper dbHelper = new MyDBOpenHelper(mContext,"my_db",null,1);
//得到一个可写的数据库
        SQLiteDatabase db =dbHelper.getWritableDatabase();

//生成ContentValues对象 //key:列名，value:想插入的值
        ContentValues cv = new ContentValues();
//往ContentValues对象存放数据，键-值对模式
        cv.put("id", 1);
        cv.put("sname", "精灵王");
        cv.put("sage", 21);
        cv.put("ssex", "女");
//调用insert方法，将数据插入数据库
        db.insert("stu_table", null, cv);
//关闭数据库
        Toast.makeText(mContext, "插入完毕~", Toast.LENGTH_SHORT).show();
        db.close();
    }


    private void query() {
        sb = new StringBuilder();
        MyDBOpenHelper dbHelper = new MyDBOpenHelper(mContext,"my_db",null,1);
//得到一个可写的数据库
        SQLiteDatabase db =dbHelper.getReadableDatabase();
//参数1：表名
//参数2：要想显示的列
//参数3：where子句
//参数4：where子句对应的条件值
//参数5：分组方式
//参数6：having条件
//参数7：排序方式
        Cursor cursor = db.query("stu_table", new String[]{"id","sname","sage","ssex"}, "id=?", new String[]{"1"}, null, null, null);
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("sname"));
            String age = cursor.getString(cursor.getColumnIndex("sage"));
            String sex = cursor.getString(cursor.getColumnIndex("ssex"));
            System.out.println("query------->" + "姓名："+name+" "+"年龄："+age+" "+"性别："+sex);
            sb.append("姓名：" + name + "年龄：" + age + "性别："+ sex +"\n");

        }
        Toast.makeText(mContext, sb.toString(), Toast.LENGTH_SHORT).show();
//关闭数据库
        db.close();
    }


    private void updata(){
        MyDBOpenHelper dbHelper = new MyDBOpenHelper(mContext,"my_db",null,1);
//得到一个可写的数据库
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("sage", "23");
//where 子句 "?"是占位符号，对应后面的"1",
        String whereClause="id=?";
        String [] whereArgs = {String.valueOf(1)};
//参数1 是要更新的表名
//参数2 是一个ContentValeus对象
//参数3 是where子句
        db.update("stu_table", cv, whereClause, whereArgs);
        Toast.makeText(mContext, "修改完毕~", Toast.LENGTH_SHORT).show();
    }


    private void delete(){
        MyDBOpenHelper dbHelper = new MyDBOpenHelper(mContext,"my_db",null,1);
//得到一个可写的数据库
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        String whereClauses = "id=?";
        String [] whereArgs = {String.valueOf(1)};
//调用delete方法，删除数据
        db.delete("stu_table", whereClauses, whereArgs);
        Toast.makeText(mContext, "删除完毕~", Toast.LENGTH_SHORT).show();
    }



}
