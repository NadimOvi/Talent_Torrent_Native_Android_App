package com.teleaus.talenttorrentandroid.Fragments.TrainingFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.teleaus.talenttorrentandroid.Fragments.ExpertsFragment.ExpertDetailsFragment;
import com.teleaus.talenttorrentandroid.R;


public class TrainningDetailsFragment extends Fragment {

    TextView trainngingTittle,locationShow, durationShow, lunchTxt, assessmentTxt, certificateTxt, snacksTxt,priceShow, contentTxt, dateShow;
    Toolbar toolbarId;
    String token, profileEmail, password, type;
    Button registerButton;
    Integer userId,lunch,assessment,snacks,fee,trainingID;
    String title,location,duration,date,certificate,details;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trainning_details, container, false);


        initialise(view);

        toolbarId.setNavigationIcon(R.drawable.ic_backbutton);
        toolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Bundle bundle = new Bundle();
                bundle = new Bundle();
                bundle.putInt("id", userId);
                bundle.putInt("trainingID", trainingID);
                bundle.putString("title", title);
                bundle.putString("location", location);
                bundle.putString("duration", duration);
                bundle.putString("date", date);
                bundle.putInt("lunch", lunch);
                bundle.putInt("assessment", assessment);
                bundle.putInt("snacks", snacks);
                bundle.putInt("fee", fee);
                bundle.putString("certificate", certificate);
                bundle.putString("details", details);
                bundle.putString("token", token);
                bundle.putString("profileEmail", profileEmail);
                bundle.putString("type", type);

                TrainningApplyFragment trainningApplyFragment = new TrainningApplyFragment();
                trainningApplyFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, trainningApplyFragment)
                        .addToBackStack(null).commit();
            }
        });
        return view;
    }

    private void initialise(View view) {
        trainngingTittle = view.findViewById(R.id.trainngingTittle);
        locationShow = view.findViewById(R.id.locationShow);
        durationShow = view.findViewById(R.id.durationShow);
        lunchTxt = view.findViewById(R.id.lunchTxt);
        assessmentTxt = view.findViewById(R.id.assessmentTxt);
        certificateTxt = view.findViewById(R.id.certificateTxt);
        snacksTxt = view.findViewById(R.id.snacksTxt);
        priceShow = view.findViewById(R.id.priceShow);
        contentTxt = view.findViewById(R.id.contentTxt);
        dateShow = view.findViewById(R.id.dateShow);
        registerButton = view.findViewById(R.id.registerButton);

        Bundle bundle = this.getArguments();
         userId = bundle.getInt("id");
         trainingID = bundle.getInt("trainingID");
         title = bundle.getString("title");
         location = bundle.getString("location");
         duration = bundle.getString("duration");
         date = bundle.getString("date");
         lunch = bundle.getInt("lunch");
         assessment = bundle.getInt("assessment");
         snacks = bundle.getInt("snacks");
         fee = bundle.getInt("fee");
         certificate = bundle.getString("certificate");
         details = bundle.getString("details");


        token = bundle.getString("token");
        profileEmail = bundle.getString("email");
        password = bundle.getString("password");
        type = bundle.getString("typetype");

        contentTxt.setText(Html.fromHtml(details));
        trainngingTittle.setText(title);
        dateShow.setText(date);
        durationShow.setText(duration);
        certificateTxt.setText(certificate);
        locationShow.setText(location);
        priceShow.setText(String.valueOf(fee));

        if (lunch==0){
            lunchTxt.setText("No");
        }else{
            lunchTxt.setText("Yes");
        }

        if (assessment==0){
            assessmentTxt.setText("No");
        }else{
            assessmentTxt.setText("Yes");
        }

        if (snacks==0){
            snacksTxt.setText("No");
        }else{
            snacksTxt.setText("Yes");
        }
        toolbarId = view.findViewById(R.id.toolbarId);
    }
}