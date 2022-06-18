package com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientJobStatusModels.JobStatusData;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientJobStatusModels.JobStatusMainModel;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobAdapter;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels.JobData;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels.JobMainModel;
import com.teleaus.talenttorrentandroid.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientActiveJobFragment extends Fragment {

    View v;
    Toolbar toolbarId;
    RecyclerView activeJobRecyclerView;
    String token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_active_job, container, false);
        v= getActivity().findViewById(android.R.id.content);
        initialise(view);
        activeGetRequest();
        toolbarId.setNavigationIcon(R.drawable.ic_backbutton);
        toolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }
    private void initialise(View view) {
        Bundle bundle = this.getArguments();
        token = bundle.getString("token");
        toolbarId = view.findViewById(R.id.toolbarId);
        activeJobRecyclerView = view.findViewById(R.id.activeJobRecyclerView);
        activeJobRecyclerView.setHasFixedSize(true);
        activeJobRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
    }


    private void activeGetRequest() {
        Service service = Client.retrofit.create(Service.class);
        Call<JobStatusMainModel> call = service.jobStatus("Bearer"+" "+token,"active");
        call.enqueue(new Callback<JobStatusMainModel>() {
            @Override
            public void onResponse(Call<JobStatusMainModel> call, Response<JobStatusMainModel> response) {

                if (response.isSuccessful()){
                    List<JobStatusData> jobStatusData= response.body().getData();
                    ActiveJobAdapter(jobStatusData,token);
                }
            }

            @Override
            public void onFailure(Call<JobStatusMainModel> call, Throwable t) {
                Snackbar.make(v,"Failed",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void ActiveJobAdapter(List<JobStatusData> list,String token) {
        ActiveJobAdapter activeJobAdapter = new ActiveJobAdapter(list,getContext(),token);
        activeJobRecyclerView.setAdapter(activeJobAdapter);
    }

}