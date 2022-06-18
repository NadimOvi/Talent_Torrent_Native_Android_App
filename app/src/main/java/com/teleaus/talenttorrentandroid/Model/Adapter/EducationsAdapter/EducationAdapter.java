package com.teleaus.talenttorrentandroid.Model.Adapter.EducationsAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teleaus.talenttorrentandroid.Model.Adapter.EmploymentsAdapter.EmployementAdapter;
import com.teleaus.talenttorrentandroid.Model.EducationModel.EducationData;
import com.teleaus.talenttorrentandroid.Model.EmployementModel.EmployementData;
import com.teleaus.talenttorrentandroid.R;

import org.w3c.dom.Text;

import java.util.List;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.ViewHolder> {

    private List<EducationData> list;
    private Context context;

    public EducationAdapter(List<EducationData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public EducationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.education_recycler_viewholder,viewGroup,false);
        return new EducationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EducationAdapter.ViewHolder holder, int position) {
        final EducationData educationData = list.get(position);
        holder.instituteName.setText(educationData.getInstitute());
        holder.degreeShow.setText(educationData.getDegree());
        holder.fromDate.setText(educationData.getStart_year());
        holder.toDateShow.setText(educationData.getEnd_year());
        holder.description.setText(educationData.getDescription());
        holder.activities.setText(educationData.getActivities());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView instituteName,degreeShow, fromDate, toDateShow,description, activities;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            instituteName = itemView.findViewById(R.id.instituteName);
            degreeShow = itemView.findViewById(R.id.degreeShow);
            fromDate = itemView.findViewById(R.id.fromDate);
            toDateShow = itemView.findViewById(R.id.toDateShow);
            description = itemView.findViewById(R.id.description);
            activities = itemView.findViewById(R.id.activities);
        }
    }
}
