package com.teleaus.talenttorrentandroid.Fragments.ExpertsFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.Fragments.JobsFragment.draftedJobPostModel;
import com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertModels.Data;
import com.teleaus.talenttorrentandroid.Model.ConnectionModel.StatusModel;
import com.teleaus.talenttorrentandroid.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpertDetailsFragment extends Fragment {

    View v;
    TextView expertName, countryName,aboutExpert,memberDate, locationBased, jobCompleted;
    ImageView expertPhoto;
    RecyclerView expertSkillRecyclerview;
    Button contactButton;
    Toolbar toolbarId;
    String profileImageShow;
    Integer userId;
    String token,profileEmail,password,type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expert_details, container, false);
        v= getActivity().findViewById(android.R.id.content);
        defineView(view);

        if (token !=null){
            statusPost();
        }else {
            contactButton.setVisibility(View.GONE);
        }

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPost();
            }
        });

        toolbarId.setNavigationIcon(R.drawable.ic_backbutton);

        toolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    private void defineView(View view) {
        toolbarId = view.findViewById(R.id.toolbarId);
        expertName = view.findViewById(R.id.expertName);
        countryName = view.findViewById(R.id.countryName);
        aboutExpert = view.findViewById(R.id.aboutExpert);
        memberDate = view.findViewById(R.id.memberDate);
        locationBased = view.findViewById(R.id.locationBased);
        jobCompleted = view.findViewById(R.id.jobCompleted);
        expertPhoto = view.findViewById(R.id.expertPhoto);
        expertSkillRecyclerview = view.findViewById(R.id.expertSkillRecyclerview);
        contactButton = view.findViewById(R.id.contactButton);

        expertSkillRecyclerview = view.findViewById(R.id.expertSkillRecyclerview);
        expertSkillRecyclerview.setHasFixedSize(true);
        expertSkillRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        Bundle bundle = this.getArguments();
        userId = bundle.getInt("id");
        String firstName = bundle.getString("firstName");
        String lastName = bundle.getString("lastName");
        String fullName = firstName +" "+lastName;
        String companyName = bundle.getString("companyName");
        String countyName = bundle.getString("countyName");
        profileImageShow = bundle.getString("profileImageShow");
        String experience = bundle.getString("experience");
        String coverLetter = bundle.getString("coverLetter");
        String qualification = bundle.getString("qualification");
        String phoneNumber = bundle.getString("phoneNumber");
        String email = bundle.getString("email");
        String location = bundle.getString("location");
        String created = bundle.getString("created");

        token = bundle.getString("token");
        profileEmail = bundle.getString("email");
        password = bundle.getString("password");
        type = bundle.getString("typetype");

        ArrayList<String> skill = (ArrayList<String>)getArguments().getSerializable("skill");


        expertName.setText(fullName);

        if (countyName == null){
            countryName.setText(" ");
        }else if(coverLetter == null){
            aboutExpert.setText(" ");
        }else if(locationBased == null){
            locationBased.setText(" ");
        }else{
            countryName.setText(countyName);
            aboutExpert.setText(Html.fromHtml(coverLetter));
            locationBased.setText(location);
        }

        memberDate.setText(created);

        /*jobCompleted.setText(location);*/

        Picasso.get()
                .load(profileImageShow)
                .into(expertPhoto);

    }

    private void requestPost() {
        Service service = Client.retrofit.create(Service.class);
        Call<String> call = service.connectionRequest("Bearer"+" "+token,userId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    contactButton.setBackgroundResource(R.drawable.ic_pending_button);
                    contactButton.setText("Pending");

                }else{
                    /*Toast.makeText(getActivity(), "You already connected with this user", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"You already connected with this user",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                /*Toast.makeText(getActivity(), "Failed to Request", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Failed to Request",Snackbar.LENGTH_SHORT).show();

            }
        });
    }

    private void statusPost() {
        Service service = Client.retrofit.create(Service.class);
        Call<StatusModel> call = service.connectionStatus("Bearer"+" "+token,userId);
        call.enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                StatusModel statusModel = response.body();
                if (response.isSuccessful()){

                    String message = statusModel.getMessage();
                    String status = statusModel.getData().getStatus();
                    if (status.equals("block")){
                        contactButton.setBackgroundResource(R.drawable.ic_block_button);
                        contactButton.setText("Blocked");
                        contactButton.setEnabled(false);
                        /*Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();*/
                        Snackbar.make(v,message,Snackbar.LENGTH_SHORT).show();
                    }else if (status.equals("pending")){
                        contactButton.setBackgroundResource(R.drawable.ic_pending_button);
                        contactButton.setText("Pending");
                        contactButton.setEnabled(false);
                        /*Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();*/
                        Snackbar.make(v,message,Snackbar.LENGTH_SHORT).show();
                    }else if (status.equals("active")) {
                        contactButton.setBackgroundResource(R.drawable.ic_active_button);
                        contactButton.setText("Actived");
                        contactButton.setEnabled(false);
                        /*Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();*/
                        Snackbar.make(v,message,Snackbar.LENGTH_SHORT).show();
                    }

                }else{
                    /*Toast.makeText(getActivity(),"User is not connected with this expert.", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"User is not connected with this expert.",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
               /* Toast.makeText(getActivity(), "Connection Failed", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Connection Failed",Snackbar.LENGTH_SHORT).show();
            }
        });
    }


}