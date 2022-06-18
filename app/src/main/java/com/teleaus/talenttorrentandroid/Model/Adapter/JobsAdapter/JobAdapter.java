package com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teleaus.talenttorrentandroid.Fragments.JobsFragment.JobDetailsFragment;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels.JobData;
import com.teleaus.talenttorrentandroid.R;

import java.util.ArrayList;
import java.util.List;

import ru.embersoft.expandabletextview.ExpandableTextView;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {

    private List<JobData> list;
    private Context context;
    private JobAdapter.RecyclerViewClickListener jobAdapterListener;
    JobData data;
    Bundle bundle;
    private boolean expendable = false;

    public JobAdapter(List<JobData> list, Context context, JobAdapter.RecyclerViewClickListener jobListener) {
        this.list = list;
        this.context = context;
        this.jobAdapterListener = jobListener;
    }

    @NonNull
    @Override
    public JobAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_view_holder,viewGroup,false);
        return new JobAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final JobAdapter.ViewHolder holder, int position) {
       data = list.get(position);
       if (data.getTags().isEmpty()){
           holder.jobTitle.setText(data.getTitle());
           holder.budgetTextShow.setText(data.getBudget());
           holder.dayRemainingShow.setText(data.getUpdated_at());

           holder.jobDetailsShow.setText(Html.fromHtml(data.getDescription()));
       }
       else{
           holder.jobTitle.setText(data.getTitle());
           holder.budgetTextShow.setText(data.getBudget());
           holder.dayRemainingShow.setText(data.getUpdated_at());
           holder.jobDetailsShow.setText(Html.fromHtml(data.getDescription()));

           ArrayList<String> tag = data.getTags();
           TagAdapter tagAdapter = new TagAdapter(context,tag);
           holder.tagsRecyclerView.setHasFixedSize(true);
           holder.tagsRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
           holder.tagsRecyclerView.setAdapter(tagAdapter);
       }


        
        holder.descTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                bundle.get(String.valueOf(position));
                bundle.putString("jobTitle",list.get(position).getTitle());
                bundle.putString("createdDay",list.get(position).getCreated_at());
                bundle.putString("budget",list.get(position).getBudget());
                bundle.putString("getUpdated",list.get(position).getUpdated_at());
                bundle.putString("description",list.get(position).getDescription());
                bundle.putSerializable("tag",list.get(position).getTags());

                fragmentCall(view);
            }
        });

        /*holder.descTextView.setText(Html.fromHtml(data.getDescription()));*/

        holder.applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                bundle.get(String.valueOf(position));
                bundle.putString("jobTitle",list.get(position).getTitle());
                bundle.putString("createdDay",list.get(position).getCreated_at());
                bundle.putString("budget",list.get(position).getBudget());
                bundle.putString("getUpdated",list.get(position).getUpdated_at());
                bundle.putString("description",list.get(position).getDescription());
                bundle.putSerializable("tag",list.get(position).getTags());

                fragmentCall(view);

            }
        });
    }

    private void fragmentCall(View view) {
        AppCompatActivity activity = (AppCompatActivity)view.getContext();
        JobDetailsFragment jobDetailsFragment = new JobDetailsFragment();
        jobDetailsFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,jobDetailsFragment).addToBackStack(null).commit();

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
        Button applyButton;
        RecyclerView tagsRecyclerView;
        TextView descTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.jobTitle);
            budgetTextShow = itemView.findViewById(R.id.budgetTextShow);
            dayRemainingShow = itemView.findViewById(R.id.dayRemainingShow);
            jobDetailsShow = itemView.findViewById(R.id.jobDetailsShow);
            tagsRecyclerView = itemView.findViewById(R.id.tagsRecyclerView);
            applyButton = itemView.findViewById(R.id.applyButton);
            descTextView = itemView.findViewById(R.id.descTextView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            jobAdapterListener.onClick(itemView,getAdapterPosition());
        }
    }
}
