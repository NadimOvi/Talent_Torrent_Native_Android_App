package com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobAdapter;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels.JobData;
import com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter.TrainningAdapterModel.TrainningData;
import com.teleaus.talenttorrentandroid.R;

import java.util.List;

public class TrainningAdapter extends RecyclerView.Adapter<TrainningAdapter.ViewHolder>{

    private List<TrainningData> list;
    private Context context;
    private TrainningAdapter.RecyclerViewClickListener listener;

    public TrainningAdapter(List<TrainningData> list, Context context, RecyclerViewClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TrainningAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trainning_view_adpater,viewGroup,false);
        return new TrainningAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainningAdapter.ViewHolder holder, int position) {
        final TrainningData data = list.get(position);
        holder.trainngingTittle.setText(data.getTitle());
        holder.locationShow.setText(data.getLocation());
        holder.durationShow.setText(data.getDuration());
        holder.dateShow.setText(data.getUpdated_at());
        holder.priceShow.setText(String.valueOf(data.getFee()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView trainngingTittle,locationShow,durationShow,dateShow,priceShow;
        Button detailsButton,registerButton;
        public ViewHolder( View itemView) {
            super(itemView);
            trainngingTittle= itemView.findViewById(R.id.trainngingTittle);
            locationShow= itemView.findViewById(R.id.locationShow);
            durationShow= itemView.findViewById(R.id.durationShow);
            dateShow= itemView.findViewById(R.id.dateShow);
            priceShow= itemView.findViewById(R.id.priceShow);
            detailsButton= itemView.findViewById(R.id.detailsButton);
            registerButton= itemView.findViewById(R.id.registerButton);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(itemView,getAdapterPosition());
        }
    }
}
