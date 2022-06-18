package com.teleaus.talenttorrentandroid.Fragments.NotificationFragments;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    private List<NotificationData> list;
    private Context context;
    private NotificationAdapter.RecyclerViewClickListener notificationListener;
    String token;
    NotificationAdapter notificationAdapter;

    public NotificationAdapter(List<NotificationData> list, Context context, RecyclerViewClickListener notificationListener, String token) {
        this.list = list;
        this.context = context;
        this.notificationListener = notificationListener;
        this.token = token;
    }

    @NotNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_view_holder,viewGroup,false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotificationAdapter.ViewHolder holder, int position) {
        NotificationData notificationData = list.get(position);
        holder.messageTxt.setText(notificationData.getMessage());
        if (notificationData.getStatus().equals("unread")){
            holder.messageTxt.setTextColor(Color.parseColor("#000000"));
        }
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Service service = Client.retrofit.create(Service.class);
                Call<JSONObject> call = service.getArchivedNotification("Bearer"+" "+token, notificationData.getId());
                call.enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.isSuccessful()){
                            Snackbar.make(view,"Archived Done",Snackbar.LENGTH_SHORT).show();
                            list.remove(position);
                            notifyItemRemoved(position);

                        }else{
                            Snackbar.make(view,"Error",Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        Snackbar.make(view,"Failed",Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView messageTxt;
        ImageButton deleteButton;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            messageTxt = itemView.findViewById(R.id.messageTxt);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            notificationListener.onClick(itemView,getAdapterPosition());
        }
    }


}
