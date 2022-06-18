package com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientJobStatusModels.AppliedExpertModel;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientJobStatusModels.AppliedExpertProfileFragment;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientJobStatusModels.AppliedProfileData;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientJobStatusModels.JobStatusData;
import com.teleaus.talenttorrentandroid.Fragments.JobsFragment.JobDetailsFragment;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobAdapter;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels.JobData;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.TagAdapter;
import com.teleaus.talenttorrentandroid.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActiveJobDetailsFragment extends Fragment {

    View v;
    TextView jobsTitleText, budgetShow, jobPostShow, jobDescription;
    RecyclerView jobTagRecyclerView;
    Toolbar toolbarId;
    int id;
    String token,uuid ,email, password, type;
    Button editJobButton;
    TextView appliedList;
    RecyclerView jobAppliedRecyclerView;
    private ExpertAppliedAdapter.RecyclerViewClickListener expertAppliedAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activite_job_details, container, false);
        v= getActivity().findViewById(android.R.id.content);
        initialise(view);
        toolbarId.setNavigationIcon(R.drawable.ic_backbutton);
        toolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        jobApplied();
        return view;
    }

    private void initialise(View view) {
        toolbarId = view.findViewById(R.id.toolbarId);
        appliedList = view.findViewById(R.id.appliedList);

        Bundle bundle = this.getArguments();
        id = bundle.getInt("id");

        token = bundle.getString("token");
        uuid = bundle.getString("uuid");

        String jobTitle = bundle.getString("jobTitle");
        String createdDay = bundle.getString("createdDay");
        String budget = bundle.getString("budget");
        String getUpdated = bundle.getString("getUpdated");
        String description = bundle.getString("description");
        /*Toast.makeText(getActivity(),String.valueOf(id), Toast.LENGTH_SHORT).show();*/
        ArrayList<String> list= (ArrayList<String>)getArguments().getSerializable("tag");

        jobsTitleText = view.findViewById(R.id.JobsTitleText);
        budgetShow = view.findViewById(R.id.budgetShow);
        jobPostShow = view.findViewById(R.id.jobPostShow);
        jobDescription = view.findViewById(R.id.jobDescription);
        editJobButton = view.findViewById(R.id.editJobButton);

        jobsTitleText.setText(jobTitle);
        budgetShow.setText(budget);
        jobPostShow.setText(getUpdated);
        jobDescription.setText(Html.fromHtml(description));

        jobAppliedRecyclerView = view.findViewById(R.id.jobAppliedRecyclerView);


        jobTagRecyclerView = view.findViewById(R.id.jobTagRecyclerView);
        TagAdapter tagAdapter = new TagAdapter(getActivity(),list);
        jobTagRecyclerView.setHasFixedSize(true);
        jobTagRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        jobTagRecyclerView.setAdapter(tagAdapter);
    }

    private void jobApplied() {
        Service service = Client.retrofit.create(Service.class);
        Call<AppliedExpertModel> call = service.jobAppliedCheck("Bearer"+" "+token,uuid);
        call.enqueue(new Callback<AppliedExpertModel>() {
            @Override
            public void onResponse(Call<AppliedExpertModel> call, Response<AppliedExpertModel> response) {
                if (response.isSuccessful()){
                    List<AppliedProfileData> appliedProfileDataArrayList= response.body().getData();

                    adapter(appliedProfileDataArrayList,token);

                }else{
                    /*Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Error",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AppliedExpertModel> call, Throwable t) {
                /*Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Error",Snackbar.LENGTH_SHORT).show();
            }
        });
    }
    private void adapter(List<AppliedProfileData> list, String token) {

        expertAppliedAdaptersetOnClickListener(list,token);
        ExpertAppliedAdapter appliedAdapter = new ExpertAppliedAdapter(list,getContext(),token,expertAppliedAdapter);

        jobAppliedRecyclerView.setHasFixedSize(true);
        jobAppliedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        jobAppliedRecyclerView.setAdapter(appliedAdapter);
        int i = jobAppliedRecyclerView.getAdapter().getItemCount();
        appliedList.setText("Expert Applied: "+i);
    }

    private void expertAppliedAdaptersetOnClickListener(List<AppliedProfileData> list, String token) {
        expertAppliedAdapter = new ExpertAppliedAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                AppliedExpertProfileFragment appliedExpertProfileFragment = new AppliedExpertProfileFragment();
                Bundle bundle = new Bundle();
                bundle.get(String.valueOf(position));
                bundle.putInt("dataId",list.get(position).getId());
                bundle.putString("token",token);
                bundle.putString("uuid",uuid);
                bundle.putString("budget",list.get(position).getBudget());
                bundle.putString("message",list.get(position).getMessage());
                bundle.putString("status",list.get(position).getStatus());
                bundle.putInt("user_id",list.get(position).getUser_id());
                bundle.putString("first_name",list.get(position).getUser().getFirst_name());
                bundle.putString("last_name",list.get(position).getUser().getLast_name());
                bundle.putString("email",list.get(position).getUser().getEmail());
                bundle.putString("type",list.get(position).getUser().getType());
                bundle.putString("avatar_url",list.get(position).getUser().getProfile().getAvatar_url());
                bundle.putString("cover_photo_url",list.get(position).getUser().getProfile().getCover_photo_url());
                bundle.putString("phone",list.get(position).getUser().getProfile().getPhone());
                bundle.putString("location",list.get(position).getUser().getProfile().getLocation());
                bundle.putString("country",list.get(position).getUser().getProfile().getCountry());
                bundle.putString("created_at",list.get(position).getUser().getCreated_at());

                appliedExpertProfileFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,appliedExpertProfileFragment).addToBackStack(null).commit();

            }
        };
    }

}