package com.example.facebookclone;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class PostActivity extends AppCompatActivity implements onBackPressedListener{
    public RecyclerAdapter adapter = MainActivity.adapter;
    RecyclerView recyclerView = MainActivity.recyclerView;
    String title;
    String content;
    String toDay;
    public static Integer id= 1;
    public List<Post> list = MainActivity.list;
    static Calendar calendar;
    public static Post data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        EditText titleEdit = (EditText)findViewById(R.id.titleEdit);
        EditText contentEdit = (EditText)findViewById(R.id.contentEdit);
        TextView countText = (TextView)findViewById(R.id.countText);
        TextView contentCount = (TextView)findViewById(R.id.contentCount);
        TextView idView = (TextView)findViewById(R.id.idView);
        calendar = Calendar.getInstance();
        Date day = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",java.util.Locale.getDefault());
        getApi api = RetrofitAsd.getRetrofit().create(getApi.class);
        ImageButton backButton = (ImageButton)findViewById(R.id.backButton);
        Button postingButton = (Button)findViewById(R.id.postingButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        postingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titleEdit.length()!=0&&contentEdit.length()!=0){
                    title = titleEdit.getText().toString();
                    content = contentEdit.getText().toString();
                    toDay = dateFormat.format(day);
                    Post post = new Post(id,title,content,toDay);
                    Call<Post> call = api.postData(post);
                    call.enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {

                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {
                        }
                    });
                    id++;
                    finish();

                }
                else if(titleEdit.length()==0&&contentEdit.length()==0){
                    Toast.makeText(getApplicationContext(),"제목과 내용을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
                else if(titleEdit.length()==0){
                    Toast.makeText(getApplicationContext(),"제목을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
                else if(contentEdit.length()==0){
                    Toast.makeText(getApplicationContext(),"내용을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }

            }
        });
        titleEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = titleEdit.getText().toString();
                countText.setText(input.length()+" / 20");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        contentEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = contentEdit.getText().toString();
                contentCount.setText(string.length()+" / 1000");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PostActivity.this);
        builder.setMessage("글쓰기를 취소하시겠습니까?").setPositiveButton("예",yesClickListener).setNegativeButton("아니오",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private final DialogInterface.OnClickListener yesClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            finish();
        }
    };
}