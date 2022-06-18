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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.teleaus.talenttorrentandroid.Api.ButtonPressCloseWindow;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientFragment;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ExpertDashboard.ExpertFragment;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.LoginFragment;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.UsersFragment.UserFragment;
import com.teleaus.talenttorrentandroid.Fragments.ExpertsFragment.ExpertDetailsFragment;
import com.teleaus.talenttorrentandroid.Fragments.ExpertsFragment.ExpertListingFragment;
import com.teleaus.talenttorrentandroid.Fragments.JobsFragment.JobCreateFragment;
import com.teleaus.talenttorrentandroid.Fragments.JobsFragment.JobDetailsFragment;
import com.teleaus.talenttorrentandroid.Fragments.JobsFragment.JobListingFragment;
import com.teleaus.talenttorrentandroid.Fragments.TrainingFragments.TrainingListingFragment;
import com.teleaus.talenttorrentandroid.Fragments.TrainingFragments.TrainningDetailsFragment;
import com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertAdapter;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobAdapter;
import com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertModels.Data;
import com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertModels.MainModel;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels.JobData;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels.JobMainModel;
import com.teleaus.talenttorrentandroid.Model.Adapter.StaticModel;
import com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter.TrainningAdapter;
import com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter.TrainningAdapterModel.TrainningData;
import com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter.TrainningAdapterModel.TrainningMainModel;
import com.teleaus.talenttorrentandroid.R;

import org.jetbrains.annotations.NotNull;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeFragment extends Fragment implements ButtonPressCloseWindow {

   public static ButtonPressCloseWindow backlistener;

    View view;
    Bundle bundle;
    RecyclerView ExpertAdapterRecycler;
    RecyclerView JobAdapterRecycler;
    RecyclerView TrainingAdapterRecycler;
    private ExpertAdapter.RecyclerViewClickListener listener;
    private JobAdapter.RecyclerViewClickListener jobListener;
    private TrainningAdapter.RecyclerViewClickListener trainningListener;

    SearchView searchView;
    boolean jobSearchClicked;
    boolean expertSearchClicked;
    boolean trainingSearchClicked;

    TextView jobTextView,expertTextView,trainningTextView,businessConnected,expertConnected,trainningConnected,jobPosted,jobConnected;
    LinearLayout hire_Button,work_button,training_button;

    private ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    DrawerLayout drawerLayout;
    ImageView ClickMenu,ClickClose,ClickNotification;
    TextView ClickHome,ClickBrowse,ClickJob,ClickExpert,ClickTraining,nameShow,ClickMyView,ClickLogout;
    String fullName,token,email,password,type;
    Button createJob;
    private GoogleSignInClient mGoogleSignInClient;

    ArrayList<JobData> jobData ;
    ArrayList<Data> dataExpert;
    ArrayList<TrainningData> trainningData;


    @Override
    public void onAttach(@NonNull Context context) {
        sharedPreferences = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        View v= getActivity().findViewById(android.R.id.content);

        Bundle extras = getArguments();
        jobData  = extras.getParcelableArrayList("jobData");
     /*   dataExpert  = extras.getParcelableArrayList("dataExpert");*/
        /*trainningData  = extras.getParcelableArrayList("trainningData");*/


        token = sharedPreferences.getString("token",null);
        String email= sharedPreferences.getString("email",null);
        String password= sharedPreferences.getString("password",null);
        String type= sharedPreferences.getString("type",null);
        String firstName= sharedPreferences.getString("firstName",null);
        String lastName= sharedPreferences.getString("lastName",null);
        fullName = firstName+" "+lastName;
        String profilePhoto= sharedPreferences.getString("profilePhoto",null);

        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient=GoogleSignIn.getClient(getContext(),gso);
       /* Toast.makeText(getActivity(), token+"\n"+email+"\n"+password+"\n"+type+"\n"+firstName+
                "\n"+lastName+"\n"+profilePhoto, Toast.LENGTH_LONG).show();*/

        defineViews(view);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(),googleSignInOptions);

        dataJobLoad(v);
        dataExpertLoad(v);
        statisticApi(v);
        dataTrainningLoad(v);


        if (type == null){
            createJob.setVisibility(View.GONE);
        }else{
            if (type.equals("client")){
                createJob.setVisibility(View.VISIBLE);
            }else{
                createJob.setVisibility(View.GONE);
            }
        }

        if (fullName.equals("null null")){
            nameShow.setText("Your Name");
            ClickMyView.setText("Sign In");
            ClickLogout.setVisibility(View.GONE);
        }else{
            nameShow.setText(fullName);
            ClickLogout.setVisibility(View.VISIBLE);
        }

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
        hire_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpertListingFragment expertListingFragment = new ExpertListingFragment();

                getFragmentManager().beginTransaction().replace(R.id.fragment_container,expertListingFragment).addToBackStack(null).commit();

            }
        });
        work_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobListingFragment jobFragment = new JobListingFragment();

               /* JobMainModel jobMainModel = new JobMainModel();
                Bundle bundle = new Bundle();
                bundle.putParcelable("key", jobMainModel);
                jobFragment.setArguments(bundle);*/

                getFragmentManager().beginTransaction().replace(R.id.fragment_container,jobFragment).addToBackStack(null).commit();
            }
        });

        training_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TrainingListingFragment trainingListingFragment = new TrainingListingFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,trainingListingFragment).addToBackStack(null).commit();
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
                    jobSearch(s,v);
                }else if(expertSearchClicked) {
                    dataExpertSearch(s,v);
                }else if(trainingSearchClicked){
                    dataTrainningSearch(s,v);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if (jobSearchClicked){
                    jobSearch(s, v);
                }else if(expertSearchClicked) {
                    dataExpertSearch(s, v);
                }else if(trainingSearchClicked){
                    dataTrainningSearch(s, v);
                }
                return true;
            }
        });

        ClickMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);

            }
        });
        ClickClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer(drawerLayout);
            }
        });
        ClickHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().recreate();
            }
        });
        ClickBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BrowserFragment browserFragment = new BrowserFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,browserFragment).addToBackStack(null).commit();

            }
        });
        ClickJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobListingFragment jobFragment = new JobListingFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,jobFragment).addToBackStack(null).commit();
            }
        });
        ClickExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpertListingFragment expertListingFragment = new ExpertListingFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,expertListingFragment).addToBackStack(null).commit();

            }
        });
        ClickTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrainingListingFragment trainingListingFragment = new TrainingListingFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,trainingListingFragment).addToBackStack(null).commit();
            }
        });
        ClickMyView.setOnClickListener(new View.OnClickListener() {
            Bundle bundle = new Bundle();
            @Override
            public void onClick(View view) {
                if(token!=null&&email!=null){
                    if (type.equals("expert")){
                        ExpertFragment expertFragment = new ExpertFragment();
                        bundle.putString("token", token);
                        bundle.putString("email", email);
                        bundle.putString("password", password);
                        bundle.putString("type", type);
                        expertFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, expertFragment).addToBackStack(null).commit();
                    }else if(type.equals("client")){
                        ClientFragment clientFragment = new ClientFragment();
                        bundle.putString("token", token);
                        bundle.putString("email", email);
                        bundle.putString("password", password);
                        bundle.putString("type", type);
                        clientFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,clientFragment).addToBackStack(null).commit();
                    }else if(type.equals("user")){
                        UserFragment userFragment = new UserFragment();
                        bundle.putString("token", token);
                        bundle.putString("email", email);
                        bundle.putString("password", password);
                        bundle.putString("type", type);
                        userFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,userFragment).addToBackStack(null).commit();
                    }

                }else{
                    LoginFragment loginFragment = new LoginFragment();
                    bundle.putString("token", token);
                    bundle.putString("email", email);
                    bundle.putString("password", password);
                    bundle.putString("type", type);
                    loginFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,loginFragment).addToBackStack(null).commit();

                }
            }
        });
        ClickLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AccessToken.getCurrentAccessToken() != null) {
                    new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                            .Callback() {
                        @Override
                        public void onCompleted(GraphResponse graphResponse) {
                            LoginManager.getInstance().logOut();
                            AccessToken.setCurrentAccessToken(null);
                            editor.clear();
                            editor.apply();
                           /* Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);*/
                            getActivity().recreate();
                        }
                    }).executeAsync();

                } else if (googleSignInClient!=null){
                    googleSignInClient.signOut().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            editor.clear();
                            editor.apply();
                            /*Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);*/
                            getActivity().recreate();
                        }
                    });
                }else {
                    editor.clear();
                    editor.apply();
                    getActivity().recreate();
                }

            }
        });

        createJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobCreateFragment jobCreateFragment = new JobCreateFragment();
                Bundle bundle = new Bundle();
                bundle.putString("token", token);
                bundle.putString("email", email);
                bundle.putString("password", password);
                bundle.putString("type", type);
                jobCreateFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,jobCreateFragment).addToBackStack(null).commit();
            }
        });
        return view;

    }

    private void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
        view.setClickable(false);
    }

    private void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
            view.setClickable(true);
        }
    }

    private void defineViews(View view) {


        //Initialise
        ExpertAdapterRecycler = view.findViewById(R.id.expertAdapterRecycler);
        ExpertAdapterRecycler.setHasFixedSize(true);
        ExpertAdapterRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        JobAdapterRecycler = view.findViewById(R.id.jobAdapterRecycler);
        JobAdapterRecycler.setHasFixedSize(true);
        JobAdapterRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        TrainingAdapterRecycler = view.findViewById(R.id.trainningAdapterRecycler);
        TrainingAdapterRecycler.setHasFixedSize(true);
        TrainingAdapterRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);


        jobTextView = view.findViewById(R.id.jobTextView);
        expertTextView = view.findViewById(R.id.expertTextView);
        trainningTextView = view.findViewById(R.id.trainningTextView);

        businessConnected = view.findViewById(R.id.businessConnected);
        expertConnected = view.findViewById(R.id.expertConnected);
        trainningConnected = view.findViewById(R.id.trainningConnected);
        jobPosted = view.findViewById(R.id.jobPosted);
        jobConnected = view.findViewById(R.id.jobConnected);
        hire_Button = view.findViewById(R.id.hire_Button);
        work_button = view.findViewById(R.id.work_button);
        training_button = view.findViewById(R.id.training_button);
        createJob = view.findViewById(R.id.createJob);
        searchView = view.findViewById(R.id.searchView);

        drawerLayout = view.findViewById(R.id.drawer_layout);
        ClickMenu = view.findViewById(R.id.ClickMenu);
        ClickClose = view.findViewById(R.id.ClickClose);
        nameShow = view.findViewById(R.id.nameShow);
        ClickNotification = view.findViewById(R.id.CLickNotification);
        ClickHome = view.findViewById(R.id.ClickHome);
        ClickBrowse = view.findViewById(R.id.ClickBrowse);
        ClickJob = view.findViewById(R.id.ClickJob);
        ClickExpert = view.findViewById(R.id.ClickExpert);
        ClickTraining = view.findViewById(R.id.CLickTraining);
        ClickMyView = view.findViewById(R.id.ClickMyView);
        ClickLogout = view.findViewById(R.id.ClickLogout);

        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    private void dataJobLoad(View v){
        progressDialog.dismiss();
        jobSearchClicked=true;
        JobAdapterView(jobData);

       /* Service service = Client.retrofit.create(Service.class);
        Call<JobMainModel> call = service.getJobData();
        call.enqueue(new Callback<JobMainModel>() {
            @Override
            public void onResponse(Call<JobMainModel> call, Response<JobMainModel> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    ArrayList<JobData> jobData = response.body().getData();
                    JobAdapterView(jobData);

                }else{

                    Snackbar.make(v,"Error",Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JobMainModel> call, Throwable t) {

                Snackbar.make(v,"Home Fragment Failed",Snackbar.LENGTH_LONG).show();
            }
        });*/

    }

    private void jobSearch(String s, View v){

        Service service = Client.retrofit.create(Service.class);
        Call<JobMainModel> call = service.getJobSearch(s);
        call.enqueue(new Callback<JobMainModel>() {
            @Override
            public void onResponse(Call<JobMainModel> call, Response<JobMainModel> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
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

                bundle.putString("token", token);
                bundle.putString("email", email);
                bundle.putString("password", password);
                bundle.putString("type", type);

                jobDetailsFragment.setArguments(bundle);

                searchView.onActionViewCollapsed();
                searchView.setQuery("", false);
                searchView.clearFocus();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,jobDetailsFragment).addToBackStack(null).commit();

            }
        };
    }

    private void dataExpertLoad(View v) {
        Service service = Client.retrofit.create(Service.class);

        Call<MainModel> call = service.getData();
        call.enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    List<Data> data = response.body().getData();
                    adapterView(data);

                }else{
                    progressDialog.dismiss();
                    /*Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Error",Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {
                progressDialog.dismiss();
                /*Toast.makeText(getActivity(), "Failed To access", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Failed",Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void dataExpertSearch(String s, View v) {
        Service service = Client.retrofit.create(Service.class);

        Call<MainModel> call = service.getExpertSearch(s);
        call.enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    List<Data> data = response.body().getData();
                    adapterView(data);
                }
            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {
                progressDialog.dismiss();
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

                bundle.putString("token", token);
                bundle.putString("email", email);
                bundle.putString("password", password);
                bundle.putString("type", type);

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

            }
        };
    }


    private void dataTrainningLoad(View v){
        Service service = Client.retrofit.create(Service.class);
        Call<TrainningMainModel> call = service.getTrainningData();
        call.enqueue(new Callback<TrainningMainModel>() {
            @Override
            public void onResponse(Call<TrainningMainModel> call, Response<TrainningMainModel> response) {

                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    List<TrainningData> trainningData = response.body().getData();
                    trainningAdapterView(trainningData);
                }
            }

            @Override
            public void onFailure(Call<TrainningMainModel> call, Throwable t) {
                progressDialog.dismiss();
                /*Toast.makeText(getActivity(), "Failed To access", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Failed To access",Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void dataTrainningSearch(String s, View v){
        Service service = Client.retrofit.create(Service.class);
        Call<TrainningMainModel> call = service.getTrainningSearch(s);
        call.enqueue(new Callback<TrainningMainModel>() {
            @Override
            public void onResponse(Call<TrainningMainModel> call, Response<TrainningMainModel> response) {


                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    List<TrainningData> trainningData = response.body().getData();
                    trainningAdapterView(trainningData);
                }
            }

            @Override
            public void onFailure(Call<TrainningMainModel> call, Throwable t) {
                progressDialog.dismiss();
                /*Toast.makeText(getActivity(), "Failed To access", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Failed To access",Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void statisticApi(View v){
        Service service = Client.retrofit.create(Service.class);
        Call<StaticModel> call = service.getStaticData();

        call.enqueue(new Callback<StaticModel>() {
            @Override
            public void onResponse(Call<StaticModel> call, Response<StaticModel> response) {
                if (response.isSuccessful()){
                    StaticModel staticModel = response.body();
                       businessConnected.setText(String.valueOf(staticModel.getData().getBusiness_connected()));
                       expertConnected.setText(String.valueOf(staticModel.getData().getExpert_connected()));
                       jobPosted.setText(String.valueOf(staticModel.getData().getJob_posted()));
                       jobConnected.setText(String.valueOf(staticModel.getData().getJob_completed()));
                       trainningConnected.setText(String.valueOf(staticModel.getData().getTraining_completed()));
                }else{
                    /*Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Error",Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StaticModel> call, Throwable t) {
                /*Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Failed",Snackbar.LENGTH_LONG).show();
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
                bundle.putInt("id", list.get(position).getId());
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

                bundle.putString("token", token);
                bundle.putString("email", email);
                bundle.putString("password", password);
                bundle.putString("type", type);

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

    public static class UnsafeOkHttpClient {
        public static OkHttpClient getUnsafeOkHttpClient() {
            try {
                // Create a trust manager that does not validate certificate chains
                final TrustManager[] trustAllCerts = new TrustManager[] {
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });

                OkHttpClient okHttpClient = builder.build();
                return okHttpClient;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
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
           /* new AlertDialog.Builder(getActivity())
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            getActivity().finish();
                        }
                    }).create().show();*/

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
                                    /*HomeFragment homeFragment = new HomeFragment();
                                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).addToBackStack(null).commit();
                                    */

                            dialog.dismiss();
                            getActivity().moveTaskToBack(true);
                            getActivity().finish();

                        }
                    })
                    .build().show();

        }
    }

}
