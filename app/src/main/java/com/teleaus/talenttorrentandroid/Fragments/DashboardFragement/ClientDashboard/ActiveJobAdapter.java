package com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientJobStatusModels.JobStatusData;
import com.teleaus.talenttorrentandroid.Fragments.JobsFragment.JobDetailsFragment;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.TagAdapter;
import com.teleaus.talenttorrentandroid.R;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class ActiveJobAdapter extends RecyclerView.Adapter<ActiveJobAdapter.ViewHolder> {

    private List<JobStatusData> list;
    private Context context;
    String token;

    public ActiveJobAdapter(List<JobStatusData> list, Context context, String token) {
        this.list = list;
        this.context = context;
        this.token = token;
    }


    @NotNull
    @Override
    public ActiveJobAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.active_job_list_view,viewGroup,false);
        return new ActiveJobAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ActiveJobAdapter.ViewHolder holder, int position) {
        JobStatusData jobStatusData = list.get(position);
        holder.jobTitle.setText(jobStatusData.getTitle());
        holder.budgetTextShow.setText(jobStatusData.getBudget());
        holder.dayRemainingShow.setText(jobStatusData.getUpdated_at());
        holder.jobDetailsShow.setText((Html.fromHtml(jobStatusData.getDescription())));

        ArrayList<String> tag = jobStatusData.getTags();
        TagAdapter tagAdapter = new TagAdapter(context,tag);
        holder.tagsRecyclerView.setHasFixedSize(true);
        holder.tagsRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
        holder.tagsRecyclerView.setAdapter(tagAdapter);

        holder.detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActiveJobDetailsFragment activeJobDetailsFragment = new ActiveJobDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.get(String.valueOf(position));
                bundle.putInt("id",list.get(position).getId());
                bundle.putString("uuid",list.get(position).getUuid());
                bundle.putString("jobTitle",list.get(position).getTitle());
                bundle.putString("createdDay",list.get(position).getCreated_at());
                bundle.putString("budget",list.get(position).getBudget());
                bundle.putString("getUpdated",list.get(position).getUpdated_at());
                bundle.putString("description",list.get(position).getDescription());
                bundle.putSerializable("tag",list.get(position).getTags());
                bundle.putString("token", token);

                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activeJobDetailsFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,activeJobDetailsFragment).addToBackStack(null).commit();

            }
        });

        holder.editJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditJobPostFragment editJobPostFragment = new EditJobPostFragment();
                Bundle bundle = new Bundle();
                bundle.get(String.valueOf(position));
                bundle.putInt("id",list.get(position).getId());
                bundle.putString("uuid",list.get(position).getUuid());
                bundle.putString("jobTitle",list.get(position).getTitle());
                bundle.putString("createdDay",list.get(position).getCreated_at());
                bundle.putString("budget",list.get(position).getBudget());
                bundle.putString("getUpdated",list.get(position).getUpdated_at());
                bundle.putString("description",list.get(position).getDescription());
                bundle.putSerializable("tag",list.get(position).getTags());
                bundle.putString("token", token);

                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                editJobPostFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,editJobPostFragment).addToBackStack(null).commit();

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitle,budgetTextShow,dayRemainingShow,jobDetailsShow;
        RecyclerView tagsRecyclerView;
        Button detailsButton,editJobButton;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            jobTitle =itemView.findViewById(R.id.jobTitle);
            budgetTextShow =itemView.findViewById(R.id.budgetTextShow);
            dayRemainingShow =itemView.findViewById(R.id.dayRemainingShow);
            jobDetailsShow =itemView.findViewById(R.id.jobDetailsShow);
            tagsRecyclerView =itemView.findViewById(R.id.tagsRecyclerView);
            detailsButton =itemView.findViewById(R.id.detailsButton);
            editJobButton =itemView.findViewById(R.id.editJobButton);

        }
    }
}
