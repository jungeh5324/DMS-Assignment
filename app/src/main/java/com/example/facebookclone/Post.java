package com.example.facebookclone;

import com.google.gson.annotations.SerializedName;

public class Post {
    public Post(Integer id, String title, String content, String create){
        this.id=id;
        this.title=title;
        this.content=content;
        this.createdAt=create;
    }
    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("createdAt")
    private String createdAt;
    public Integer getId(){return id;}
    public String getTitle(){return title;}
    public String getContent(){return content;}
    public String getCreatedAt(){return createdAt;}

}
