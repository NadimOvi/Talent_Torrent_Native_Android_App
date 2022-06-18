package com.teleaus.talenttorrentandroid.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.Fragments.BrowserFragment;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientFragment;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ExpertDashboard.ExpertFragment;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.UsersFragment.UserFragment;
import com.teleaus.talenttorrentandroid.Fragments.HomeFragment;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.LoginFragment;
import com.teleaus.talenttorrentandroid.Fragments.NotificationFragments.NotificationFragment;
import com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertModels.Data;
import com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertModels.MainModel;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels.JobData;
import com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels.JobMainModel;
import com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter.TrainningAdapterModel.TrainningData;
import com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter.TrainningAdapterModel.TrainningMainModel;
import com.teleaus.talenttorrentandroid.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import eu.dkaratzas.android.inapp.update.Constants;
import eu.dkaratzas.android.inapp.update.InAppUpdateManager;
import eu.dkaratzas.android.inapp.update.InAppUpdateStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements InAppUpdateManager.InAppUpdateHandler {

    Bundle bundle = new Bundle();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String token,email,password,type;

    ////play-store
    private static final int REQ_CODE_VERSION_UPDATE = 530;
    private static final String TAG = "Sample";
    private InAppUpdateManager inAppUpdateManager;

    ArrayList<JobData> jobData ;
    ArrayList<Data> dataExpert;
    ArrayList<TrainningData> trainningData;
    private ProgressDialog progressDialog;

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();



        if (isConnected()){
            initialise();
            progressDialog.show();

            dataExpertLoad();
            dataTrainningLoad();
            dataJobLoad();

            inAppUpdateManager = InAppUpdateManager.Builder(this, REQ_CODE_VERSION_UPDATE)
                    .resumeUpdates(true) // Resume the update, if the update was stalled. Default is true
                    .mode(Constants.UpdateMode.IMMEDIATE)
                    // default is false. If is set to true you,

                    .useCustomNotification(true)
                    .handler(this);

            inAppUpdateManager.checkForAppUpdate();

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    Bundle bundle = new Bundle();
                    switch (item.getItemId()){
                        case R.id.navigation_home:
                            progressDialog.dismiss();
                            selectedFragment = new HomeFragment();
                            bundle.putString("token", token);
                            bundle.putString("email", email);
                            bundle.putString("data", password);
                            bundle.putString("type", type);
                            bundle.putParcelableArrayList("jobData", jobData);
                            bundle.putParcelableArrayList("dataExpert", dataExpert);
                            bundle.putParcelableArrayList("trainningData", trainningData);
                            selectedFragment.setArguments(bundle);

                            break;
                        case R.id.navigation_browser:
                            progressDialog.dismiss();
                            selectedFragment = new BrowserFragment();
                            bundle.putString("token", token);
                            bundle.putString("email", email);
                            bundle.putString("password", password);
                            bundle.putString("type", type);
                            bundle.putParcelableArrayList("jobData", jobData);
                            bundle.putParcelableArrayList("dataExpert", dataExpert);
                            bundle.putParcelableArrayList("trainningData", trainningData);
                            selectedFragment.setArguments(bundle);

                            break;
                        case R.id.navigation_settings:
                            progressDialog.dismiss();
                            selectedFragment = new NotificationFragment();
                            bundle.putString("token", token);
                            bundle.putString("email", email);
                            bundle.putString("password", password);
                            bundle.putString("type", type);
                            selectedFragment.setArguments(bundle);
                            break;
                        case R.id.navigation_dashboard:
                            progressDialog.dismiss();
                            if(token!=null&&email!=null){
                                if (type.equals("expert")){
                                    selectedFragment = new ExpertFragment();
                                    bundle.putString("token", token);
                                    bundle.putString("email", email);
                                    bundle.putString("password", password);
                                    bundle.putString("type", type);
                                    selectedFragment.setArguments(bundle);
                                }else if(type.equals("client")){
                                    selectedFragment = new ClientFragment();
                                    bundle.putString("token", token);
                                    bundle.putString("email", email);
                                    bundle.putString("password", password);
                                    bundle.putString("type", type);
                                    selectedFragment.setArguments(bundle);
                                }else if(type.equals("user")){
                                    selectedFragment = new UserFragment();
                                    bundle.putString("token", token);
                                    bundle.putString("email", email);
                                    bundle.putString("password", password);
                                    bundle.putString("type", type);
                                    selectedFragment.setArguments(bundle);
                                }

                            }else{
                                selectedFragment = new LoginFragment();
                                bundle.putString("token", token);
                                bundle.putString("email", email);
                                bundle.putString("password", password);
                                bundle.putString("type", type);
                                selectedFragment.setArguments(bundle);
                            }



                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            });




            progressDialog.dismiss();
        }
        else{
            new iOSDialogBuilder(MainActivity.this)
                    .setTitle("Alert!")
                    .setSubtitle("No Internet Connection from your device")
                    .setBoldPositiveLabel(true)
                    .setCancelable(false)
                    .setPositiveListener("Ok",new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {
                            dialog.dismiss();
                            recreate();

                        }
                    })
                    .build().show();
        }
    }

    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else
            return false;

    }

    @Override
    public void onInAppUpdateError(int code, Throwable error) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInAppUpdateStatus(InAppUpdateStatus status) {
        /*
         * If the update downloaded, ask user confirmation and complete the update
         */

        if (status.isDownloaded()) {

            View rootView = getWindow().getDecorView().findViewById(android.R.id.content);

            Snackbar snackbar = Snackbar.make(rootView,
                    "An update has just been downloaded.",
                    Snackbar.LENGTH_INDEFINITE);

            snackbar.setAction("RESTART", view -> {

                // Triggers the completion of the update of the app for the flexible flow.
                inAppUpdateManager.completeUpdate();

            });

            snackbar.show();

        }else{
            initialise();
            sharedPreferences = getSharedPreferences("loginInfo",MODE_PRIVATE);
            token = sharedPreferences.getString("token",null);
            email= sharedPreferences.getString("email",null);
            password= sharedPreferences.getString("password",null);
            type= sharedPreferences.getString("type",null);
            String firstName= sharedPreferences.getString("firstName",null);
            String lastName= sharedPreferences.getString("lastName",null);
            String profilePhoto= sharedPreferences.getString("profilePhoto",null);
            /*dataJobLoad();*/
            /*dataExpertLoad();*/



        }
    }


    private void dataJobLoad(){

        Service service = Client.retrofit.create(Service.class);
        Call<JobMainModel> call = service.getJobData();
        call.enqueue(new Callback<JobMainModel>() {
            @Override
            public void onResponse(Call<JobMainModel> call, Response<JobMainModel> response) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000);

                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    jobData = response.body().getData();

                    HomeFragment homeFragment = new HomeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("jobData", jobData);
                    bundle.putParcelableArrayList("dataExpert", dataExpert);
                    bundle.putParcelableArrayList("trainningData", trainningData);
                    homeFragment.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).commit();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JobMainModel> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Home Fragment Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void dataExpertLoad() {
        Service service = Client.retrofit.create(Service.class);
        Call<MainModel> call = service.getData();
        call.enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    dataExpert = response.body().getData();


                }else{
                    progressDialog.dismiss();
                    /*Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();*/

                }
            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {
                progressDialog.dismiss();
                /*Toast.makeText(getActivity(), "Failed To access", Toast.LENGTH_SHORT).show();*/

            }
        });
    }

    private void dataTrainningLoad(){
        Service service = Client.retrofit.create(Service.class);
        Call<TrainningMainModel> call = service.getTrainningData();
        call.enqueue(new Callback<TrainningMainModel>() {
            @Override
            public void onResponse(Call<TrainningMainModel> call, Response<TrainningMainModel> response) {

                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    trainningData = response.body().getData();
                }
            }

            @Override
            public void onFailure(Call<TrainningMainModel> call, Throwable t) {
                progressDialog.dismiss();
                /*Toast.makeText(getActivity(), "Failed To access", Toast.LENGTH_SHORT).show();*/

            }
        });
    }

    /*private void breedHistory(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://talenttorrent.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        *//*Service service = Client.retrofit.create(Service.class);*//*
        Service service = retrofit.create(Service.class);
        Call<JobMainModel> call = service.getJobData();

        call.enqueue(new Callback<JobMainModel>() {
            @Override
            public void onResponse(Call<JobMainModel> call, Response<JobMainModel> response) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000);
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    jobData = response.body().getData();

                }else{
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JobMainModel> call, Throwable t) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000);

                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void initialise() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);

        sharedPreferences = getSharedPreferences("loginInfo",MODE_PRIVATE);
        token = sharedPreferences.getString("token",null);
        email= sharedPreferences.getString("email",null);
        password= sharedPreferences.getString("password",null);
        type= sharedPreferences.getString("type",null);
        String firstName= sharedPreferences.getString("firstName",null);
        String lastName= sharedPreferences.getString("lastName",null);
        String profilePhoto= sharedPreferences.getString("profilePhoto",null);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQ_CODE_VERSION_UPDATE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                // If the update is cancelled by the user,
                // you can request to start the update again.
                inAppUpdateManager.checkForAppUpdate();

                Log.d(TAG, "Update flow failed! Result code: " + resultCode);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        /*if (HomeFragment.backlistener!=null){
            HomeFragment.backlistener.onBackPressed();
        }else if (BrowserFragment.backlistener!=null){
            BrowserFragment.backlistener.onBackPressed();
        }else if (NotificationFragment.backlistener!=null){
            NotificationFragment.backlistener.onBackPressed();
        }else if (LoginFragment.backlistener!=null){
            LoginFragment.backlistener.onBackPressed();
        }else if(type.equals("user")){
            if (UserFragment.backlistener!=null){
                UserFragment.backlistener.onBackPressed();
            } else{
                super.onBackPressed();
            }
        }else if(type.equals("client")){
            if (ClientFragment.backlistener!=null){
                ClientFragment.backlistener.onBackPressed();
            } else{
                super.onBackPressed();
            }
        }else if(type.equals("expert")){
            if (ExpertFragment.backlistener!=null){
                ExpertFragment.backlistener.onBackPressed();
            } else{
                super.onBackPressed();
            }
        }else{
            super.onBackPressed();
        }*/

        super.onBackPressed();
    }
}