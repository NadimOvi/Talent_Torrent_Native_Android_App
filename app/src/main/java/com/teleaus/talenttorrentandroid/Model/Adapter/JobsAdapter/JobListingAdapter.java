package com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertAdapter;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels.JobData;
import com.teleaus.talenttorrentandroid.R;

import java.util.ArrayList;
import java.util.List;

public class JobListingAdapter extends RecyclerView.Adapter<JobListingAdapter.ViewHolder> {
    private List<JobData> list;
    private Context context;
    private RecyclerViewClickListener listener;


    public JobListingAdapter(List<JobData> list, Context context, RecyclerViewClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;

    }

    @NonNull
    @Override
    public JobListingAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_listing_holder,viewGroup,false);

        return new JobListingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobListingAdapter.ViewHolder holder, int position) {
        final JobData data = list.get(position);
        if (data.getTags()==null){
            holder.jobTitle.setText(data.getTitle());
            holder.budgetTextShow.setText(data.getBudget());
            holder.dayRemainingShow.setText(data.getUpdated_at());
            holder.dayRemainingShow.setText(data.getUpdated_at());
            holder.jobDetailsShow.setText(Html.fromHtml(data.getDescription()));

        } else{
            ArrayList<String> tag = data.getTags();
            TagAdapter tagAdapter = new TagAdapter(context,tag);
            holder.jobTitle.setText(data.getTitle());
            holder.budgetTextShow.setText(data.getBudget());
            holder.dayRemainingShow.setText(data.getUpdated_at());
            holder.dayRemainingShow.setText(data.getUpdated_at());
            holder.jobDetailsShow.setText(Html.fromHtml(data.getDescription()));
            holder.tagsRecyclerView.setHasFixedSize(true);
            holder.tagsRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
            holder.tagsRecyclerView.setAdapter(tagAdapter);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView jobTitle,budgetTextShow,dayRemainingShow,jobDetailsShow;
        RecyclerView tagsRecyclerView;
        Button applyButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.jobTitle);
            budgetTextShow = itemView.findViewById(R.id.budgetTextShow);
            dayRemainingShow = itemView.findViewById(R.id.dayRemainingShow);
            jobDetailsShow = itemView.findViewById(R.id.jobDetailsShow);
            tagsRecyclerView = itemView.findViewById(R.id.tagsRecyclerView);
            applyButton = itemView.findViewById(R.id.applyButton);
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            listener.onClick(itemView,getAdapterPosition());
        }
    }
}
