package com.example.facebookclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyActivity extends AppCompatActivity implements onBackPressedListener{
    Intent intentModify;
    public RecyclerAdapter adapter = MainActivity.adapter;
    static Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        EditText titleModify = (EditText)findViewById(R.id.titleModify);
        EditText contentModify = (EditText)findViewById(R.id.contentModify);
        intentModify = getIntent();
        titleModify.setText(intentModify.getStringExtra("제목"));
        contentModify.setText(intentModify.getStringExtra("내용"));
        Integer id = intentModify.getIntExtra("id",1);
        TextView countText = (TextView)findViewById(R.id.countTextMod);
        TextView contentCount = (TextView)findViewById(R.id.contentCountMod);
        countText.setText(titleModify.getText().toString().length()+" / 20");
        contentCount.setText(contentModify.getText().toString().length()+" / 1000");
        calendar = Calendar.getInstance();
        getApi api = RetrofitAsd.getRetrofit().create(getApi.class);
        Date day = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",java.util.Locale.getDefault());
        ImageButton back = (ImageButton)findViewById(R.id.backButtonMod);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Button modifyBtn = (Button)findViewById(R.id.modifyButton);
        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titleModify.length()!=0&&contentModify.length()!=0){
                    String title = titleModify.getText().toString();
                    String content = contentModify.getText().toString();
                    String toDay = dateFormat.format(day);
                    Post post = new Post(id,title,content,toDay);
                    Call<Post> call = api.patchData(id,post);
                    call.enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {
                        }
                    });
                    Toast.makeText(getApplicationContext(),"수정 되었습니다.",Toast.LENGTH_SHORT);
                    adapter.notifyDataSetChanged();
                    finish();
                }
                else if(titleModify.length()==0&&contentModify.length()==0){
                    Toast.makeText(getApplicationContext(),"제목과 내용을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
                else if(titleModify.length()==0){
                    Toast.makeText(getApplicationContext(),"제목을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
                else if(contentModify.length()==0){
                    Toast.makeText(getApplicationContext(),"내용을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }

            }
        });
        titleModify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = titleModify.getText().toString();
                countText.setText(input.length()+" / 20");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        contentModify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = contentModify.getText().toString();
                contentCount.setText(string.length()+" / 1000");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ModifyActivity.this);
        builder.setMessage("수정을 취소하시겠습니까?").setPositiveButton("예",yesClickListener).setNegativeButton("아니오",null);
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