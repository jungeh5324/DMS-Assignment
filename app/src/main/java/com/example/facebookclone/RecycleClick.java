package com.example.facebookclone;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.app.AlertDialog;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecycleClick extends AppCompatActivity {
    public List<Post> list = MainActivity.list;
    public RecyclerAdapter adapter = MainActivity.adapter;
    ImageButton menuBtn;
    public Intent intent;
    public int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_click);
        intent = getIntent();
        pos = intent.getIntExtra("position",1);
        TextView title = (TextView)findViewById(R.id.titleText);
        TextView content = (TextView)findViewById(R.id.contentText);
        TextView date = (TextView)findViewById(R.id.dateText);
        menuBtn = (ImageButton)findViewById(R.id.menuButton);
        title.setText(intent.getStringExtra("제목"));
        content.setText(intent.getStringExtra("내용"));
        date.setText(intent.getStringExtra("날짜"));
        Integer id = intent.getIntExtra("id",1);
        ImageButton back = (ImageButton)findViewById(R.id.backButtonClick);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(RecycleClick.this,menuBtn);
                MenuInflater menuInf = popupMenu.getMenuInflater();
                menuInf.inflate(R.menu.post_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.modify:
                                Intent intentModify = new Intent(getApplicationContext(),ModifyActivity.class);
                                intentModify.putExtra("제목", title.getText());
                                intentModify.putExtra("내용",content.getText());
                                intentModify.putExtra("날짜",date.getText());
                                intentModify.putExtra("id",id);
                                intentModify.putExtra("pos",pos);
                                finish();
                                startActivity(intentModify);
                                break;
                            case R.id.delete:
                                AlertDialog.Builder builder = new AlertDialog.Builder(RecycleClick.this);
                                builder.setTitle("삭제").setMessage("삭제 하시겠습니까?").setPositiveButton("예",yesCilckListener ).setNegativeButton("아니오",null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }
    private final DialogInterface.OnClickListener yesCilckListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            intent = getIntent();
            pos = intent.getIntExtra("position",1);
            Integer id = intent.getIntExtra("id",1);
            getApi api = RetrofitAsd.getRetrofit().create(getApi.class);
            Call<Post> call = api.deleteData(id);
            call.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    System.out.println("삭제됨");
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    System.out.println("이잉?"+t);
                }
            });
            list.remove(pos);
            adapter.notifyItemRemoved(pos);
            adapter.notifyItemRangeChanged(pos,list.size());
            finish();
        }
    };
}