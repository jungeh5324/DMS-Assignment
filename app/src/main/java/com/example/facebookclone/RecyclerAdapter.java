package com.example.facebookclone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Post> posts;

    public RecyclerAdapter(List<Post> list){
        posts = list;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.recycler_item,parent,false);
        RecyclerAdapter.ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post item = posts.get(position);
        holder.title.setText(item.getTitle());
        holder.content.setText(item.getContent());
        holder.date.setText(item.getCreatedAt());
        holder.id.setText("id : "+item.getId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(v.getContext(),RecycleClick.class);
                intent.putExtra("제목",posts.get(position).getTitle());
                intent.putExtra("내용",posts.get(position).getContent());
                intent.putExtra("날짜",posts.get(position).getCreatedAt());
                intent.putExtra("id",posts.get(position).getId());
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView content;
        TextView date;
        TextView id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleText);
            content = itemView.findViewById(R.id.contentText);
            date = itemView.findViewById(R.id.dayView);
            id = itemView.findViewById(R.id.idView);
        }
    }

}
