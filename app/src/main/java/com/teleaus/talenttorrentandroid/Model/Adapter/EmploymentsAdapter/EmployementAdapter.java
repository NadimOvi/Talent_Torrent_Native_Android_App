package com.teleaus.talenttorrentandroid.Model.Adapter.EmploymentsAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertAdapter;
import com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertModels.Data;
import com.teleaus.talenttorrentandroid.Model.EducationModel.EducationData;
import com.teleaus.talenttorrentandroid.Model.EmployementModel.EmployementData;
import com.teleaus.talenttorrentandroid.R;

import java.util.List;

public class EmployementAdapter extends RecyclerView.Adapter<EmployementAdapter.ViewHolder> {

    private List<EmployementData> list;
    private Context context;

    public EmployementAdapter(List<EmployementData> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public EmployementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.employment_recycler_viewholder,viewGroup,false);
        return new EmployementAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployementAdapter.ViewHolder holder, int position) {
        final EmployementData employementData = list.get(position);

        holder.companyName.setText(employementData.getCompany_name());
        holder.jobTitle.setText(employementData.getJob_title());
        holder.startDate.setText(employementData.getStart_date());
        holder.endDate.setText(employementData.getEnd_date());
        holder.workType.setText(employementData.getWork_type());
        holder.description.setText(employementData.getDescription());
    }

    @Override
    public int getItemCount() {
       return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView companyName,jobTitle,startDate,endDate,workType,locationName,description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            companyName = itemView.findViewById(R.id.companyName);
            jobTitle = itemView.findViewById(R.id.jobTitle);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
            workType = itemView.findViewById(R.id.workType);
            locationName = itemView.findViewById(R.id.locationName);
            description = itemView.findViewById(R.id.description);
        }
    }
}
