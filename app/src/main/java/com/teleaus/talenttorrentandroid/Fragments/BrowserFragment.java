package com.teleaus.talenttorrentandroid.Fragments;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.teleaus.talenttorrentandroid.Api.ButtonPressCloseWindow;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.Fragments.ExpertsFragment.ExpertDetailsFragment;
import com.teleaus.talenttorrentandroid.Fragments.JobsFragment.JobDetailsFragment;
import com.teleaus.talenttorrentandroid.Fragments.TrainingFragments.TrainningDetailsFragment;
import com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertAdapter;
import com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertModels.Data;
import com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertModels.MainModel;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobAdapter;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels.JobData;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels.JobMainModel;
import com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter.TrainningAdapter;
import com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter.TrainningAdapterModel.TrainningData;
import com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter.TrainningAdapterModel.TrainningMainModel;
import com.teleaus.talenttorrentandroid.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BrowserFragment extends Fragment implements ButtonPressCloseWindow {

    View v;
    public static ButtonPressCloseWindow backlistener;
    Bundle bundle;
    RecyclerView ExpertAdapterRecycler;
    RecyclerView JobAdapterRecycler;
    RecyclerView TrainingAdapterRecycler;
    private ExpertAdapter.RecyclerViewClickListener listener;
    private JobAdapter.RecyclerViewClickListener jobListener;
    private TrainningAdapter.RecyclerViewClickListener trainningListener;

    TextView jobTextView,expertTextView,trainningTextView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    SearchView searchView;
    boolean jobSearchClicked;
    boolean expertSearchClicked;
    boolean trainingSearchClicked;

    ArrayList<JobData> jobData ;
    ArrayList<Data> dataExpert;
    ArrayList<TrainningData> trainningData;

    @Override
    public void onAttach(@NonNull Context context) {
        sharedPreferences = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browser, container, false);
        v= getActivity().findViewById(android.R.id.content);

        Bundle extras = getArguments();
        jobData  = extras.getParcelableArrayList("jobData");
        dataExpert  = extras.getParcelableArrayList("dataExpert");
        trainningData  = extras.getParcelableArrayList("trainningData");


        String token = sharedPreferences.getString("token",null);
        String email= sharedPreferences.getString("email",null);
        String password= sharedPreferences.getString("password",null);
        String type= sharedPreferences.getString("type",null);
        String firstName= sharedPreferences.getString("firstName",null);
        String lastName= sharedPreferences.getString("lastName",null);
        String profilePhoto= sharedPreferences.getString("profilePhoto",null);

        defineViews(view);
        JobAdapterView(jobData);
        adapterView(dataExpert);
        trainningAdapterView(trainningData);

        /*dataJobLoad();
        dataExpertLoad();
        dataTrainningLoad();*/

        ExpertAdapterRecycler.setVisibility(View.GONE);
        TrainingAdapterRecycler.setVisibility(View.GONE);
        JobAdapterRecycler.setVisibility(View.VISIBLE);

        jobTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobSearchClicked=true;
                expertSearchClicked=false;
                trainingSearchClicked=false;
                jobTextView.setTextColor(getResources().getColor(R.color.colorRed));
                trainningTextView.setTextColor(getResources().getColor(R.color.defaultColor));
                expertTextView.setTextColor(getResources().getColor(R.color.defaultColor));
                ExpertAdapterRecycler.setVisibility(View.GONE);
                TrainingAdapterRecycler.setVisibility(View.GONE);
                JobAdapterRecycler.setVisibility(View.VISIBLE);

            }
        });

        expertTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobSearchClicked=false;
                expertSearchClicked=true;
                trainingSearchClicked=false;
                jobTextView.setTextColor(getResources().getColor(R.color.defaultColor));
                trainningTextView.setTextColor(getResources().getColor(R.color.defaultColor));
                expertTextView.setTextColor(getResources().getColor(R.color.colorRed));
                JobAdapterRecycler.setVisibility(View.GONE);
                ExpertAdapterRecycler.setVisibility(View.VISIBLE);
                TrainingAdapterRecycler.setVisibility(View.GONE);

            }
        });

        trainningTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobSearchClicked=false;
                expertSearchClicked=false;
                trainingSearchClicked=true;
                jobTextView.setTextColor(getResources().getColor(R.color.defaultColor));
                trainningTextView.setTextColor(getResources().getColor(R.color.colorRed));
                expertTextView.setTextColor(getResources().getColor(R.color.defaultColor));
                ExpertAdapterRecycler.setVisibility(View.GONE);
                JobAdapterRecycler.setVisibility(View.GONE);
                TrainingAdapterRecycler.setVisibility(View.VISIBLE);

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
                if (jobSearchClicked){
                    jobSearch(s);
                }else if(expertSearchClicked) {
                    dataExpertSearch(s);
                }else if(trainingSearchClicked){
                    dataTrainningSearch(s);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if (jobSearchClicked){
                    jobSearch(s);
                }else if(expertSearchClicked) {
                    dataExpertSearch(s);
                }else if(trainingSearchClicked){
                    dataTrainningSearch(s);
                }
                return true;
            }
        });

        return view;
    }

    private void defineViews(View view) {

        //Initialise
        ExpertAdapterRecycler = view.findViewById(R.id.expertAdapterRecycler);
        ExpertAdapterRecycler.setHasFixedSize(true);
        ExpertAdapterRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        JobAdapterRecycler = view.findViewById(R.id.jobAdapterRecycler);
        JobAdapterRecycler.setHasFixedSize(true);
        JobAdapterRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        TrainingAdapterRecycler = view.findViewById(R.id.trainningAdapterRecycler);
        TrainingAdapterRecycler.setHasFixedSize(true);
        TrainingAdapterRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));



        jobTextView = view.findViewById(R.id.jobTextView);
        expertTextView = view.findViewById(R.id.expertTextView);
        trainningTextView = view.findViewById(R.id.trainningTextView);
        searchView = view.findViewById(R.id.searchView);

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
                /*Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();*/
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
                /*Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Failed",Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void JobAdapterView(List<JobData> jobData) {
        jobAdaptersetOnClickListener(jobData);
        JobAdapter jobAdapter = new JobAdapter(jobData,getContext(),jobListener);
        JobAdapterRecycler.setAdapter(jobAdapter);
    }

    private void jobAdaptersetOnClickListener(List<JobData> jobData) {
        jobListener = new JobAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                JobDetailsFragment jobDetailsFragment = new JobDetailsFragment();
                bundle = new Bundle();
                bundle.get(String.valueOf(position));
                bundle.putInt("id",jobData.get(position).getId());
                bundle.putString("uuid",jobData.get(position).getUuid());
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

    private void dataExpertLoad() {
        jobSearchClicked=true;
        Service service = Client.retrofit.create(Service.class);

        Call<MainModel> call = service.getData();
        call.enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                if (response.isSuccessful()){

                    List<Data> data = response.body().getData();
                    adapterView(data);

                }else{

                    /*Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Error",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {

               /* Toast.makeText(getActivity(), "Failed To access", Toast.LENGTH_SHORT).show();*/
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
                }else{
                    Snackbar.make(v,"Error",Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {

                /*Toast.makeText(getActivity(), "Failed To access", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Failed To access",Snackbar.LENGTH_LONG).show();
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

                ExpertDetailsFragment expertDetailsFragment = new ExpertDetailsFragment();
                expertDetailsFragment.setArguments(bundle);

                searchView.onActionViewCollapsed();
                searchView.setQuery("", false);
                searchView.clearFocus();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, expertDetailsFragment)
                        .addToBackStack(null).commit();


            }
        };
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
                Snackbar.make(v,"Failed To access",Snackbar.LENGTH_LONG).show();
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
                bundle = new Bundle();
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

                TrainningDetailsFragment trainningDetailsFragment = new TrainningDetailsFragment();
                trainningDetailsFragment.setArguments(bundle);

                searchView.onActionViewCollapsed();
                searchView.setQuery("", false);
                searchView.clearFocus();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, trainningDetailsFragment)
                        .addToBackStack(null).commit();

            }
        };
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
