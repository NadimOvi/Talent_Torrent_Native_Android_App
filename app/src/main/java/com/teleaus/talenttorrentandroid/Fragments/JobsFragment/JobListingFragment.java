package com.teleaus.talenttorrentandroid.Fragments.JobsFragment;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.Fragments.HomeFragment;
import com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertAdapter;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobAdapter;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobListingAdapter;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels.JobData;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels.JobFilterModel;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels.JobMainModel;
import com.teleaus.talenttorrentandroid.R;

import java.util.List;

import it.mirko.rangeseekbar.OnRangeSeekBarListener;
import it.mirko.rangeseekbar.RangeSeekBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobListingFragment extends Fragment {
    View v;
    RecyclerView JobAdapterRecycler;
    private JobListingAdapter.RecyclerViewClickListener listener;
    Toolbar toolbarId;
    SearchView searchView;
    RangeSeekBar seekBar;
    TextView seekBarTextMin, seekBarTextMax;
    int startRange ;
    int endRange ;
    String range;
    Button applyFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job_listing, container, false);
        v= getActivity().findViewById(android.R.id.content);
        Bundle bundle = this.getArguments();

        /*    String data = bundle.getString("key");
        Toast.makeText(getActivity(),data, Toast.LENGTH_SHORT).show();
        showDes.setText(data);*/

        defineViews(view);
        dataJobLoad();

        toolbarId.setNavigationIcon(R.drawable.ic_backbutton);
        toolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        SearchManager searchManager =
                (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo =
                searchManager.getSearchableInfo(getActivity().getComponentName());
        searchView.setSearchableInfo( searchableInfo);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                jobSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                jobSearch(s);
                return true;
            }
        });

        seekBar.setOnRangeSeekBarListener(new OnRangeSeekBarListener() {
            @Override
            public void onRangeValues(RangeSeekBar rangeSeekBar, int start, int end) {
                seekBarTextMin.setText(String.valueOf(start+" $")); // example using start value
                seekBarTextMax.setText(String.valueOf(end+" $"));
                startRange = start;
                endRange = end;

                range = String.valueOf(startRange)+", "+String.valueOf(endRange);
             /*   Toast.makeText(getActivity(), range, Toast.LENGTH_SHORT).show();*/
                filterPost();

            }
        });

        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*range = String.valueOf(startRange)+", "+String.valueOf(endRange);
                Toast.makeText(getActivity(), range, Toast.LENGTH_SHORT).show();
                filterPost();*/
            }
        });

       return view;
    }



    private void defineViews(View view) {

        //Initialise

        seekBar = view.findViewById(R.id.seekBar);
        seekBar.setStartProgress(2000); // default is 0
        seekBar.setEndProgress(50000);
        seekBar.setMinDifference(5000);
        seekBar.setRangeColor(Color.RED);
        seekBar.setTrackColor(Color.LTGRAY);
        seekBar.setMax(199783);
        seekBarTextMin = view.findViewById(R.id.seekBarTextMin);
        seekBarTextMax = view.findViewById(R.id.seekBarTextMax);
        applyFilter = view.findViewById(R.id.applyFilter);
        searchView = view.findViewById(R.id.searchView);
        toolbarId = view.findViewById(R.id.toolbarId);
        JobAdapterRecycler = view.findViewById(R.id.jobListAdapter);
        JobAdapterRecycler.setHasFixedSize(true);
        JobAdapterRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

    }
    private void dataJobLoad(){
        Service service = Client.retrofit.create(Service.class);
        Call<JobMainModel> call = service.getJobData();
        call.enqueue(new Callback<JobMainModel>() {
            @Override
            public void onResponse(Call<JobMainModel> call, Response<JobMainModel> response) {
                if (response.isSuccessful()){
                    List<JobData> jobData = response.body().getData();
                    JobAdapterView(jobData);
                }else{
                    /*Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Error",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JobMainModel> call, Throwable t) {
                /*Toast.makeText(getActivity(), "Job Listing Failed", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Failed",Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void jobSearch(String s){

        Service service = Client.retrofit.create(Service.class);
        Call<JobMainModel> call = service.getJobSearch(s);
        call.enqueue(new Callback<JobMainModel>() {
            @Override
            public void onResponse(Call<JobMainModel> call, Response<JobMainModel> response) {
                if (response.isSuccessful()){
                    List<JobData> jobData = response.body().getData();
                    JobAdapterView(jobData);
                }
            }

            @Override
            public void onFailure(Call<JobMainModel> call, Throwable t) {
                /*Toast.makeText(getActivity(), "Job Listing Failed", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Failed",Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void JobAdapterView(List<JobData> jobData) {
        setOnClickListener(jobData);
        JobListingAdapter jobAdapter = new JobListingAdapter(jobData,getContext(),listener);
        JobAdapterRecycler.setAdapter(jobAdapter);
    }

    private void setOnClickListener(List<JobData> jobData) {
        listener = new JobListingAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                JobDetailsFragment jobDetailsFragment = new JobDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.get(String.valueOf(position));
                bundle.putString("jobTitle",jobData.get(position).getTitle());
                bundle.putString("createdDay",jobData.get(position).getCreated_at());
                bundle.putString("budget",jobData.get(position).getBudget());
                bundle.putString("getUpdated",jobData.get(position).getUpdated_at());
                bundle.putString("description",jobData.get(position).getDescription());
                bundle.putSerializable("tag",jobData.get(position).getTags());
                jobDetailsFragment.setArguments(bundle);

                searchView.onActionViewCollapsed();
                searchView.setQuery("", false);
                searchView.clearFocus();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,jobDetailsFragment).addToBackStack(null).commit();

            }
        };
    }
    private void filterPost() {
        String active = "test";
        Service service = Client.retrofit.create(Service.class);
        Call<JobFilterModel> call = service.getJobFilter(active,range);
        call.enqueue(new Callback<JobFilterModel>() {
            @Override
            public void onResponse(Call<JobFilterModel> call, Response<JobFilterModel> response) {
                if (response.isSuccessful()){
                    List<JobData> jobData = response.body().getData();
                    JobAdapterView(jobData);
                }
            }

            @Override
            public void onFailure(Call<JobFilterModel> call, Throwable t) {
                /*Toast.makeText(getActivity(), "Job Listing Failed", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Failed",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}