package com.teleaus.talenttorrentandroid.Fragments.ExpertsFragment;

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
import com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertAdapter;
import com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertModels.Data;
import com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertModels.MainModel;
import com.teleaus.talenttorrentandroid.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExpertListingFragment extends Fragment {
    View v;
    RecyclerView ExpertAdapterRecycler;
    private ExpertAdapter.RecyclerViewClickListener listener;
    Toolbar toolbarId;
    SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expert_listing, container, false);
        v= getActivity().findViewById(android.R.id.content);
        defineViews(view);
        dataExpertLoad();

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
                dataExpertSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                dataExpertSearch(s);
                return true;
            }
        });


        return view;
    }
    private void defineViews(View view){
        searchView = view.findViewById(R.id.searchView);
        toolbarId = view.findViewById(R.id.toolbarId);
        ExpertAdapterRecycler = view.findViewById(R.id.expertListingRecyclerView);
        ExpertAdapterRecycler.setHasFixedSize(true);
        ExpertAdapterRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
    }

    private void dataExpertLoad() {
        Service service = Client.retrofit.create(Service.class);

        Call<MainModel> call = service.getData();
        call.enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                if (response.isSuccessful()){
                    List<Data> data = response.body().getData();
                    adapterView(data);
                    /*Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();*/

                }else{
                    /*Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Error",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {
                /*Toast.makeText(getActivity(), "Failed To access", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Failed To access",Snackbar.LENGTH_SHORT).show();
            }
        });
    }
    private void dataExpertSearch(String s) {
        Service service = Client.retrofit.create(Service.class);

        Call<MainModel> call = service.getExpertSearch(s);
        call.enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                if (response.isSuccessful()){
                    List<Data> data = response.body().getData();
                    adapterView(data);

                }
            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {

                /*Toast.makeText(getActivity(), "Failed To access", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Failed To access",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void adapterView(List<Data> list) {
        setOnClickListener(list);
        ExpertAdapter expertAdapter = new ExpertAdapter(list,getContext(),listener);

        ExpertAdapterRecycler.setAdapter(expertAdapter);
    }

    private void setOnClickListener(final List<Data> list) {
        listener = new ExpertAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {


                Bundle bundle;
                bundle = new Bundle();
                bundle.putInt("id", list.get(position).getId());
                bundle.putString("firstName", list.get(position).getFirst_name());
                bundle.putString("lastName", list.get(position).getLast_name());
                bundle.putString("companyName", list.get(position).getProfile().getCompany_name());
                bundle.putString("profileImageShow", list.get(position).getProfile().getAvatar_url());
                bundle.putString("experience", list.get(position).getProfile().getExperience());
                bundle.putString("coverLetter", list.get(position).getProfile().getCover_letter());
                bundle.putString("qualification", list.get(position).getProfile().getQualification());
                bundle.putString("phoneNumber", list.get(position).getProfile().getPhone());
                bundle.putString("email", list.get(position).getEmail());
                bundle.putString("location", list.get(position).getProfile().getLocation());
                bundle.putString("countyName", list.get(position).getProfile().getCountry());
                bundle.putSerializable("skill",list.get(position).getProfile().getSkills());
                bundle.putSerializable("created",list.get(position).getCreated_at());

                searchView.onActionViewCollapsed();
                searchView.setQuery("", false);
                searchView.clearFocus();

                ExpertDetailsFragment expertDetailsFragment = new ExpertDetailsFragment();
                expertDetailsFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, expertDetailsFragment)
                        .addToBackStack(null).commit();

                /*Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();*/
            }
        };
    }
}