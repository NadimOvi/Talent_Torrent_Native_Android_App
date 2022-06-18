package com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.SkillAdapter;
import com.teleaus.talenttorrentandroid.R;

import java.util.ArrayList;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder>{
    private Context context;
    ArrayList<String> list;
    public TagAdapter(Context context, ArrayList<String> list) {
       this.context = context;
       this.list = list;
    }

    @NonNull
    @Override
    public TagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tag_view_adapter,viewGroup,false);
        return new TagAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagAdapter.ViewHolder holder, int position) {
        final String tags = list.get(position);
        holder.tagText.setText(tags);
        if (position %3 ==0 ){
            holder.tagText.setTextColor(Color.parseColor("#62D9A2"));
        }else if(position %3 ==1){
            holder.tagText.setTextColor(Color.parseColor("#48C7EC"));
        }else if(position %3 == 2){
            holder.tagText.setTextColor(Color.parseColor("#3C587B"));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tagText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagText = itemView.findViewById(R.id.tagText);
        }
    }
}
