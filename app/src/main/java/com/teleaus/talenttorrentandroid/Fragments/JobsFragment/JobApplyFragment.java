package com.teleaus.talenttorrentandroid.Fragments.JobsFragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.Fragments.BrowserFragment;
import com.teleaus.talenttorrentandroid.Fragments.HomeFragment;
import com.teleaus.talenttorrentandroid.Model.Login.LoginModel;
import com.teleaus.talenttorrentandroid.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobApplyFragment extends Fragment {

    View v;
    Integer id;
    String token, uuid;
    Toolbar toolbarId;
    EditText budgetEditText, proposalEditText;
    Button applyJob;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job_apply, container, false);
        v= getActivity().findViewById(android.R.id.content);
        initialise(view);
        toolbarId.setNavigationIcon(R.drawable.ic_backbutton);
        toolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        applyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRequest();
            }
        });


        return view;
    }


    private void initialise(View view) {
        Bundle bundle = this.getArguments();
        id = bundle.getInt("id");
        token = bundle.getString("token");
        uuid = bundle.getString("uuid");

        toolbarId = view.findViewById(R.id.toolbarId);
        budgetEditText = view.findViewById(R.id.budgetEditText);
        proposalEditText = view.findViewById(R.id.proposalEditText);
        applyJob = view.findViewById(R.id.applyJob);

    }
    private void postRequest() {
        int budget = Integer.parseInt(budgetEditText.getText().toString());
        String message = proposalEditText.getText().toString();

        Service service = Client.retrofit.create(Service.class);
        Call<JsonObject> call = service.applyJob("Bearer"+" "+token,uuid,budget,message);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    FancyAlertDialog.Builder
                            .with(getActivity())
                            .setTitle("Thank You")
                            .setBackgroundColor(Color.parseColor("#303F9F"))  // for @ColorRes use setBackgroundColorRes(R.color.colorvalue)
                            .setMessage("You have successfully done")
                            .setNegativeBtnText("Others Job")
                            .setPositiveBtnBackground(Color.parseColor("#10243F"))  // for @ColorRes use setPositiveBtnBackgroundRes(R.color.colorvalue)
                            .setPositiveBtnText("Home")
                            .setNegativeBtnBackground(Color.parseColor("#10243F"))
                            .setAnimation(Animation.POP)
                            .isCancellable(false)
                            .setIcon(R.drawable.ic_baseline_check_circle_outline_24, View.VISIBLE)
                            .onPositiveClicked(new FancyAlertDialogListener() {
                                @Override
                                public void onClick(Dialog dialog) {
                                    HomeFragment homeFragment = new HomeFragment();
                                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).addToBackStack(null).commit();
                                }
                            })
                            .onNegativeClicked(new FancyAlertDialogListener() {
                                @Override
                                public void onClick(Dialog dialog) {
                                    BrowserFragment browserFragment = new BrowserFragment();
                                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,browserFragment).addToBackStack(null).commit();
                                }
                            })

                            .build()
                            .show();

                }else{
                    /*Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Error",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                /*Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Failed",Snackbar.LENGTH_SHORT).show();
            }
        });

    }
}