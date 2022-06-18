package com.teleaus.talenttorrentandroid.Fragments.JobsFragment;

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

import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobAdapter;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.TagAdapter;
import com.teleaus.talenttorrentandroid.R;

import java.util.ArrayList;

public class JobDetailsFragment extends Fragment {

    View v;
    TextView jobsTitleText, budgetShow, jobPostShow, jobDescription;
    RecyclerView jobTagRecyclerView;
    Toolbar toolbarId;
    int id;
    String token,uuid ,email, password, type;
    Button applyJob;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job_details, container, false);
        v= getActivity().findViewById(android.R.id.content);
        initialise(view);

        toolbarId.setNavigationIcon(R.drawable.ic_backbutton);
        toolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        /*if (type.equals("expert")){
            applyJob.setVisibility(View.VISIBLE);
        }else{
            applyJob.setVisibility(View.GONE);
        }*/

        applyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putInt("id",id);
                bundle.putString("token",token);
                bundle.putString("uuid",uuid);

                JobApplyFragment jobApplyFragment = new JobApplyFragment();
                jobApplyFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,jobApplyFragment).addToBackStack(null).commit();

            }
        });

        return view;
    }

    private void initialise(View view) {
        toolbarId = view.findViewById(R.id.toolbarId);

        Bundle bundle = this.getArguments();
        id = bundle.getInt("id");
        token = bundle.getString("token");
        email = bundle.getString("email");
        password = bundle.getString("password");
        type = bundle.getString("type");
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
        jobTagRecyclerView = view.findViewById(R.id.jobTagRecyclerView);
        applyJob = view.findViewById(R.id.applyJob);

        jobsTitleText.setText(jobTitle);
        budgetShow.setText(budget);
        jobPostShow.setText(getUpdated);
        jobDescription.setText(Html.fromHtml(description));

        jobTagRecyclerView = view.findViewById(R.id.jobTagRecyclerView);

        TagAdapter tagAdapter = new TagAdapter(getActivity(),list);
        jobTagRecyclerView.setHasFixedSize(true);
        jobTagRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        jobTagRecyclerView.setAdapter(tagAdapter);
    }
}