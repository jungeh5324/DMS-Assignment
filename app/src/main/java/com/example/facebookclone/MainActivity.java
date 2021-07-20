package com.example.facebookclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Boolean fapOpen = false;
    private FloatingActionButton plus, post;
    private Animation fab_close, fab_open, rotate, rotateClose;
    public static RecyclerView recyclerView = null;
    public static RecyclerAdapter adapter = null;
    public static Data data;
    public static List<Post> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        list = new ArrayList<>();
        getApi api = RetrofitAsd.getRetrofit().create(getApi.class);
        Call<Data> call = api.getData();
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.isSuccessful()){
                    data = response.body();
                    list = data.posts;
                    adapter = new RecyclerAdapter(getApplicationContext(), list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anim);
        rotateClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anim_close);
        plus = (FloatingActionButton) findViewById(R.id.plusButton);
        post = (FloatingActionButton) findViewById(R.id.postButton);
        plus.setOnClickListener(this);
        post.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, PostActivity.class);
        int id = v.getId();
        switch (id) {
            case R.id.plusButton:
                anim();
                break;
            case R.id.postButton:
                startActivity(intent);
                break;
        }
    }

    public void anim() {
        if (fapOpen) {
            post.startAnimation(fab_close);
            post.setClickable(false);
            plus.startAnimation(rotateClose);
            fapOpen = false;
        } else {
            post.startAnimation(fab_open);
            post.setClickable(true);
            plus.startAnimation(rotate);
            fapOpen = true;
        }
    }
    @Override
    public void onRestart(){
        super.onRestart();
        getApi api = RetrofitAsd.getRetrofit().create(getApi.class);
        Call<Data> call = api.getData();
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.isSuccessful()){
                    data = response.body();
                    list = data.posts;
                    adapter = new RecyclerAdapter(getApplicationContext(), list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
            }
        });
    }

}