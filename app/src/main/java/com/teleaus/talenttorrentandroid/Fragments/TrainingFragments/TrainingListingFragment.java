package com.teleaus.talenttorrentandroid.Fragments.TrainingFragments;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter.TrainningAdapter;
import com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter.TrainningAdapterModel.TrainningData;
import com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter.TrainningAdapterModel.TrainningMainModel;
import com.teleaus.talenttorrentandroid.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrainingListingFragment extends Fragment {

    View v;
    RecyclerView TrainingAdapterRecycler;
    private TrainningAdapter.RecyclerViewClickListener trainningListener;
    Toolbar toolbarId;
    SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_training_listing, container, false);
        v= getActivity().findViewById(android.R.id.content);
        defineViews(view);
        dataTrainningLoad();
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
                dataTrainningSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                dataTrainningSearch(s);
                return true;
            }
        });


        return view;
    }

    private void defineViews(View view){
        searchView = view.findViewById(R.id.searchView);
        toolbarId = view.findViewById(R.id.toolbarId);
        TrainingAdapterRecycler = view.findViewById(R.id.TrainingListingRecyclerView);
        TrainingAdapterRecycler.setHasFixedSize(true);
        TrainingAdapterRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
    }

    private void dataTrainningLoad(){
        Service service = Client.retrofit.create(Service.class);
        Call<TrainningMainModel> call = service.getTrainningData();
        call.enqueue(new Callback<TrainningMainModel>() {
            @Override
            public void onResponse(Call<TrainningMainModel> call, Response<TrainningMainModel> response) {

                if (response.isSuccessful()){
                    List<TrainningData> trainningData = response.body().getData();
                    trainningAdapterView(trainningData);

                }else{
                    /*Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Error",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TrainningMainModel> call, Throwable t) {
                /*Toast.makeText(getActivity(), "Failed To access", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Failed To access",Snackbar.LENGTH_SHORT).show();
            }
        });
    }
    private void dataTrainningSearch(String s){
        Service service = Client.retrofit.create(Service.class);
        Call<TrainningMainModel> call = service.getTrainningSearch(s);
        call.enqueue(new Callback<TrainningMainModel>() {
            @Override
            public void onResponse(Call<TrainningMainModel> call, Response<TrainningMainModel> response) {


                if (response.isSuccessful()){

                    List<TrainningData> trainningData = response.body().getData();
                    trainningAdapterView(trainningData);

                }
            }

            @Override
            public void onFailure(Call<TrainningMainModel> call, Throwable t) {

                /*Toast.makeText(getActivity(), "Failed To access", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Failed To access",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void trainningAdapterView(List<TrainningData> trainningData) {
        TrainningsetOnClickListener(trainningData);
        TrainningAdapter expertAdapter = new TrainningAdapter(trainningData,getContext(),trainningListener);
        TrainingAdapterRecycler.setAdapter(expertAdapter);
    }

    private void TrainningsetOnClickListener(List<TrainningData> list) {
        trainningListener = new TrainningAdapter.RecyclerViewClickListener(){
            @Override
            public void onClick(View v, int position) {
                Bundle bundle;
                bundle = new Bundle();
                bundle.putInt("trainingID", list.get(position).getId());
                bundle.putString("title", list.get(position).getTitle());
                bundle.putString("location", list.get(position).getLocation());
                bundle.putString("duration", list.get(position).getDuration());
                bundle.putString("date", list.get(position).getStart_date());
                bundle.putInt("lunch", list.get(position).getLunch());
                bundle.putInt("assessment", list.get(position).getAssessment());
                bundle.putInt("snacks", list.get(position).getSnacks());
                bundle.putString("certificate", list.get(position).getCertificate());
                bundle.putInt("fee", list.get(position).getFee());
                bundle.putString("details", list.get(position).getDetails());

                searchView.onActionViewCollapsed();
                searchView.setQuery("", false);
                searchView.clearFocus();

                TrainningDetailsFragment trainningDetailsFragment = new TrainningDetailsFragment();
                trainningDetailsFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, trainningDetailsFragment)
                        .addToBackStack(null).commit();
            }
        };
    }
}