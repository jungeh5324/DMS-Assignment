package com.example.facebookclone;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data {
    @SerializedName("message")
    private String message;
    @SerializedName("posts")
    public List<Post> posts;

}


