package com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertModels.Data;
import com.teleaus.talenttorrentandroid.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ExpertAdapter extends RecyclerView.Adapter<ExpertAdapter.ViewHolder> {
    private List<Data> list;
    private Context context;
    private RecyclerViewClickListener listener;

    public ExpertAdapter(List<Data> list, Context context, RecyclerViewClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.expert_adapter_viewholder,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Data data = list.get(position);
        String url = data.getProfile().getAvatar_url();
        String firstName = data.getFirst_name();
        String lastName = data.getLast_name();
        String countryName = data.getProfile().getCountry();
        String type =data.getProfile().getExperience();
        String coverLetter = data.getProfile().getCover_letter();

        holder.profileFirstName.setText(firstName);
        holder.profileLastName.setText(lastName);
        holder.countryName.setText(countryName);
        holder.aboutExpert.setText(type);
        holder.designation.setText(coverLetter);
        /*Glide.with(context).load(url).into(holder.profileImage);*/

        ArrayList<String> skill = data.getProfile().getSkills();
        SkillAdapter skillAdapter = new SkillAdapter(context,skill);
        holder.skillRecyclerView.setHasFixedSize(true);
        holder.skillRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
        holder.skillRecyclerView.setAdapter(skillAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView profileImage;
        TextView profileFirstName,profileLastName,countryName,aboutExpert,designation;
        RecyclerView skillRecyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Initialise Home adapter viewHolder
            profileImage = itemView.findViewById(R.id.profileImage);
            profileFirstName = itemView.findViewById(R.id.profileFirstName);
            profileLastName = itemView.findViewById(R.id.profileLastName);
            countryName = itemView.findViewById(R.id.countryName);
            aboutExpert = itemView.findViewById(R.id.aboutExpert);
            designation = itemView.findViewById(R.id.designation);
            skillRecyclerView = itemView.findViewById(R.id.skillRecyclerView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(itemView,getAdapterPosition());
        }
    }
}
