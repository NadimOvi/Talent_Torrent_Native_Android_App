package com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientJobStatusModels.AppliedProfileData;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientJobStatusModels.JobStatusData;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobAdapter;
import com.teleaus.talenttorrentandroid.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ExpertAppliedAdapter extends RecyclerView.Adapter<ExpertAppliedAdapter.ViewHolder>{
    private List<AppliedProfileData> list;
    private Context context;
    String token;
    private ExpertAppliedAdapter.RecyclerViewClickListener expertAdapterListener;


    public ExpertAppliedAdapter(List<AppliedProfileData> list, Context context, String token, RecyclerViewClickListener expertAppliedAdapter) {
        this.list = list;
        this.context = context;
        this.token = token;
        this.expertAdapterListener = expertAppliedAdapter;
    }

    @NotNull
    @Override
    public ExpertAppliedAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.applied_expert_viewholder,viewGroup,false);
        return new ExpertAppliedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ExpertAppliedAdapter.ViewHolder holder, int position) {

        AppliedProfileData appliedProfileData = list.get(position);

        String firstName = appliedProfileData.getUser().getFirst_name();
        String lastName = appliedProfileData.getUser().getLast_name();

        holder.expertName.setText(firstName+" "+lastName);
        holder.locationTxt.setText(appliedProfileData.getUser().getProfile().getLocation());
        holder.countryTxt.setText(appliedProfileData.getUser().getProfile().getCountry());
        holder.budgetTxt.setText(appliedProfileData.getBudget());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView expertName,locationTxt,countryTxt,budgetTxt;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            expertName = itemView.findViewById(R.id.expertName);
            locationTxt = itemView.findViewById(R.id.locationTxt);
            countryTxt = itemView.findViewById(R.id.countryTxt);
            budgetTxt = itemView.findViewById(R.id.budgetTxt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            expertAdapterListener.onClick(itemView,getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
