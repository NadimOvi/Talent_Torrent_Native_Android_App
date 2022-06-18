package com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientJobStatusModels;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.stripe.android.model.ConfirmSetupIntentParams;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.Fragments.HomeFragment;
import com.teleaus.talenttorrentandroid.Fragments.TrainingFragments.TrainningApplyFragment;
import com.teleaus.talenttorrentandroid.R;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppliedExpertProfileFragment extends Fragment {

    View v;
    Integer dataId;
    String uuid,token,budget,message,status,user_id,first_name,last_name,email,type,avatar_url,cover_photo_url,phone,location,country,created_at;
    Toolbar toolbarId;
    TextView expertName,locationTxt,countryTxt,statusTxt,budgetTxt,createdTxt,messageTxt;
    Button hireButton,rejectButton,hire_button,closeButton;
    LinearLayout statusLayout;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    EditText budgetEditTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_applied_expert_profile, container, false);
        v= getActivity().findViewById(android.R.id.content);
        initialise(view);
        toolbarId.setNavigationIcon(R.drawable.ic_backbutton);
        toolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        hireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUpDialog();
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectRequest();
            }
        });


        return view;
    }


    private void initialise(View view) {
        Bundle bundle = this.getArguments();
        dataId = bundle.getInt("dataId");
        token = bundle.getString("token");
        uuid = bundle.getString("uuid");
        budget = bundle.getString("budget");
        message = bundle.getString("message");
        status = bundle.getString("status");
        user_id = bundle.getString("user_id");
        first_name = bundle.getString("first_name");
        last_name = bundle.getString("last_name");
        email = bundle.getString("email");
        type = bundle.getString("type");
        avatar_url = bundle.getString("avatar_url");
        cover_photo_url = bundle.getString("cover_photo_url");
        phone = bundle.getString("phone");
        location = bundle.getString("location");
        country = bundle.getString("country");
        created_at = bundle.getString("created_at");

        //initialise
        toolbarId = view.findViewById(R.id.toolbarId);
        expertName = view.findViewById(R.id.expertName);
        locationTxt = view.findViewById(R.id.locationTxt);
        countryTxt = view.findViewById(R.id.countryTxt);
        statusTxt = view.findViewById(R.id.statusTxt);
        budgetTxt = view.findViewById(R.id.budgetTxt);
        createdTxt = view.findViewById(R.id.createdTxt);
        messageTxt = view.findViewById(R.id.messageTxt);
        hireButton = view.findViewById(R.id.hireButton);
        rejectButton = view.findViewById(R.id.rejectButton);
        statusLayout = view.findViewById(R.id.statusLayout);

        ///Show Data
        String fullName = first_name+" "+last_name;
        expertName.setText(fullName);
        locationTxt.setText(location);
        countryTxt.setText(country);
        budgetTxt.setText(budget);
        createdTxt.setText(created_at);
        messageTxt.setText(Html.fromHtml(message));
        statusLayout.setVisibility(View.GONE);

    }

    private void createPopUpDialog() {
        dialogBuilder = new AlertDialog.Builder(getActivity());


        final View popUp_card = getLayoutInflater().inflate(R.layout.hire_expert_card_view,null);

        hire_button = popUp_card.findViewById(R.id.hire_button);
        closeButton = popUp_card.findViewById(R.id.closeButton);
        dialogBuilder.setView(popUp_card);
        dialogBuilder.setCancelable(false);
        dialog = dialogBuilder.create();
        dialog.show();

        hire_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*progressDialog.show();*/
                /*saveCard();*/
                budgetEditTxt = popUp_card.findViewById(R.id.budgetEditTxt);
                String budget = budgetEditTxt.getText().toString();
                try
                {
                    int budgetNum = Integer.parseInt(budget);

                    if (budget.isEmpty()){
                        budgetEditTxt.setError("Please Enter Amount");
                        budgetEditTxt.requestFocus();
                    }else if(budgetNum<500){
                        budgetEditTxt.setError("Minimum Amount is $500");
                        budgetEditTxt.requestFocus();
                    }else{


                        Service service = Client.retrofit.create(Service.class);
                        Call<JSONObject> call = service.expertHire("Bearer"+" "+token,uuid,budgetNum,dataId);
                        call.enqueue(new Callback<JSONObject>() {
                            @Override
                            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                                if (response.isSuccessful()){

                                    new iOSDialogBuilder(getContext())
                                            .setTitle("Thank you")
                                            .setSubtitle("Your request is successful")
                                            .setBoldPositiveLabel(true)
                                            .setCancelable(false)
                                            .setPositiveListener("Ok",new iOSDialogClickListener() {
                                                @Override
                                                public void onClick(iOSDialog dialogs) {
                                                    dialogs.dismiss();
                                                    dialog.dismiss();
                                                    HomeFragment homeFragment = new HomeFragment();
                                                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).addToBackStack(null).commit();



                                                }
                                            })
                                            .build().show();


                                }else{
                                    /*Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();*/
                                    Snackbar.make(v,"Error",Snackbar.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<JSONObject> call, Throwable t) {
                                Snackbar.make(v,"Failed",Snackbar.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
                catch (NumberFormatException e)
                {
                    // handle the exception
                    budgetEditTxt.setError("Please Enter Amount");
                    budgetEditTxt.requestFocus();
                }


            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void rejectRequest() {
        Service service = Client.retrofit.create(Service.class);
        Call<JSONObject> call = service.expertReject("Bearer"+" "+token,uuid,dataId);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.isSuccessful()){
                    new iOSDialogBuilder(getContext())
                            .setTitle("Thank you")
                            .setSubtitle("Your request is successful")
                            .setBoldPositiveLabel(true)
                            .setCancelable(false)
                            .setPositiveListener("Ok",new iOSDialogClickListener() {
                                @Override
                                public void onClick(iOSDialog dialog) {
                                    dialog.dismiss();
                                    HomeFragment homeFragment = new HomeFragment();
                                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).addToBackStack(null).commit();

                                }
                            })
                            .build().show();
                }else{
                    /*Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Error",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                /*Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Error",Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}