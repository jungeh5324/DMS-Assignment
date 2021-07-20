package com.example.facebookclone;

import android.graphics.drawable.Drawable;

public class RecyclerViewItem {
    private String titleText;
    private String contentText;
    private String dateText;
    private String idText;
    public void setTitle(String title){
        titleText = title;
    }
    public void setContent(String content){
        contentText = content;
    }
    public void setDate(String date){dateText = date;}
    public void setId(String id){idText = id;}
    public String getTitleText(){
        return this.titleText;
    }
    public String getContentText(){
        return this.contentText;
    }
    public String getDateText(){return this.dateText;}
    public String getIdText(){return this.idText;}
}
