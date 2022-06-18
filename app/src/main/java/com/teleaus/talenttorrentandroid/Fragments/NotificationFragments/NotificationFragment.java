package com.teleaus.talenttorrentandroid.Fragments.NotificationFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.teleaus.talenttorrentandroid.Api.ButtonPressCloseWindow;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationFragment extends Fragment implements ButtonPressCloseWindow {

    View v;
    public static ButtonPressCloseWindow backlistener;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private TextView notificationStatus;
    private RecyclerView notificationRecyclerView;
    private NotificationAdapter.RecyclerViewClickListener notificationListener;
    String token;
    List<NotificationData> notificationData;
    NotificationAdapter notificationAdapter;
    int notificationID;
    View view;
    NotificationData deleteMove;
    int id;
    @Override
    public void onAttach(@NonNull Context context) {
        sharedPreferences = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        super.onAttach(context);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_notification, container, false);
        v= getActivity().findViewById(android.R.id.content);
        initialise(view);
        notificationShow(v);

        return view;
    }

    private void initialise(View view) {
        token = sharedPreferences.getString("token",null);
        String email= sharedPreferences.getString("email",null);
        String password= sharedPreferences.getString("password",null);
        String type= sharedPreferences.getString("type",null);
        String firstName= sharedPreferences.getString("firstName",null);
        String lastName= sharedPreferences.getString("lastName",null);
        String profilePhoto= sharedPreferences.getString("profilePhoto",null);

        notificationStatus = view.findViewById(R.id.notificationStatus);

        notificationRecyclerView = view.findViewById(R.id.notificationRecyclerView);
        notificationRecyclerView.setHasFixedSize(true);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
    }


    private void notificationShow(View v){
        Service service = Client.retrofit.create(Service.class);
        Call<NotificationMainModel> call = service.getNotification("Bearer"+" "+token);
        call.enqueue(new Callback<NotificationMainModel>() {
            @Override
            public void onResponse(Call<NotificationMainModel> call, Response<NotificationMainModel> response) {
                if (response.isSuccessful()){
                    notificationStatus.setVisibility(View.GONE);
                    notificationData = response.body().getData();
                    for (int i =0;i<notificationData.size();i++){
                       id= notificationData.get(i).getId();
                    }
                    notificationAdapter(notificationData);
                }else{
                    /*Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Error",Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationMainModel> call, Throwable t) {
                /*Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Failed",Snackbar.LENGTH_LONG).show();
            }
        });
    }


    private void notificationAdapter(List<NotificationData> notificationData) {
        notificationSetOnClickListener(notificationData);
        notificationAdapter = new NotificationAdapter(notificationData,getContext(),notificationListener,token);
        notificationRecyclerView.setAdapter(notificationAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(notificationRecyclerView);
    }

    private void notificationSetOnClickListener(List<NotificationData> notificationData) {
        notificationListener = new NotificationAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                Service service = Client.retrofit.create(Service.class);
                Integer postId = notificationData.get(position).getId();
                Call<JSONObject> call = service.getReadNotification("Bearer"+" "+token, postId);
                call.enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.isSuccessful()){
                            Snackbar.make(view,"Read Done",Snackbar.LENGTH_SHORT).show();
                            notificationAdapter.notifyDataSetChanged();
                            notificationAdapter.notifyItemChanged(position);
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
        };
    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT /*| ItemTouchHelper.RIGHT*/) {
        @Override
        public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            switch (direction){
                case ItemTouchHelper.LEFT:

                    deleteMove = notificationData.get(position);
                    notificationData.remove(position);
                    notificationAdapter.notifyItemRemoved(position);

                    notificationID = deleteMove.getId();

                    setArchive();
                    Snackbar.make(notificationRecyclerView,deleteMove.getMessage(),Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    notificationData.add(position,deleteMove);
                                    notificationAdapter.notifyItemInserted(position);
                                }
                            }).show();

                    break;
                case ItemTouchHelper.RIGHT:
                    deleteMove = notificationData.get(position);
                    notificationData.remove(position);
                    notificationAdapter.notifyItemRemoved(position);
                    Snackbar.make(notificationRecyclerView,deleteMove.getMessage()+", Archived",Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(getActivity(), "Context", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull @NotNull Canvas c, @NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_archive_24)
                    /*.addSwipeRightBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_archive_24)*/
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    private void setArchive(){
        Service service = Client.retrofit.create(Service.class);
        Call<JSONObject> call = service.getArchivedNotification("Bearer"+" "+token, notificationID);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.isSuccessful()){
                    Snackbar.make(view,"Archive Successful",Snackbar.LENGTH_SHORT).show();
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

    @Override
    public void onPause() {
        backlistener = null;
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        backlistener = this;
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 1 ) {
            // If there are back-stack entries, leave the FragmentActivity
            // implementation take care of them.
            manager.popBackStack();

        } else {

            // Otherwise, ask user if he wants to leave :)
            new iOSDialogBuilder(getContext())
                    .setTitle("Really Exit?")
                    .setSubtitle("Are you sure you want to exit?")
                    .setBoldPositiveLabel(true)
                    .setCancelable(false)
                    .setNegativeListener("Cancel", new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveListener("Ok",new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {

                            dialog.dismiss();
                            getActivity().moveTaskToBack(true);
                            getActivity().finish();

                        }
                    })
                    .build().show();

        }
    }
}
