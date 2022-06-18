package com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ExpertDashboard;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.Fragments.HomeFragment;
import com.teleaus.talenttorrentandroid.Fragments.JobsFragment.createJobPostModel;
import com.teleaus.talenttorrentandroid.R;

import org.json.JSONObject;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEducationFragment extends Fragment {
    View v;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String token,startDate,endDate;
    EditText instituteEditText,degreeEditText,fieldStudyEditText,gradeEditText,detailsEditText,activitiesEditText;
    TextView startYearTxt,endYearTxt;
    Button createButton;
    @Override
    public void onAttach(@NonNull Context context) {
        sharedPreferences = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_education, container, false);
        v= getActivity().findViewById(android.R.id.content);
        initialise(view);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        startYearTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        month = month+1;

                        startDate = year+"-"+month+"-"+day;
                        startYearTxt.setText(startDate);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        endYearTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        month = month+1;

                        endDate = year+"-"+month+"-"+day;
                        endYearTxt.setText(endDate);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });

        return view;
    }

    private void initialise(View view) {
        token = sharedPreferences.getString("token",null);
        String email= sharedPreferences.getString("email",null);
        String password= sharedPreferences.getString("password",null);
        String type= sharedPreferences.getString("type",null);

        instituteEditText = view.findViewById(R.id.instituteEditText);
        degreeEditText = view.findViewById(R.id.degreeEditText);
        fieldStudyEditText = view.findViewById(R.id.fieldStudyEditText);
        gradeEditText = view.findViewById(R.id.gradeEditText);
        detailsEditText = view.findViewById(R.id.detailsEditText);
        activitiesEditText = view.findViewById(R.id.activitiesEditText);
        startYearTxt = view.findViewById(R.id.startYearTxt);
        endYearTxt = view.findViewById(R.id.endYearTxt);
        createButton = view.findViewById(R.id.createButton);

    }

    private void checkValidation() {
        String institute = instituteEditText.getText().toString().trim();
        String degree = degreeEditText.getText().toString().trim();
        String fieldStudy = fieldStudyEditText.getText().toString().trim();
        String grade = gradeEditText.getText().toString().trim();
        String details = detailsEditText.getText().toString().trim();
        String activities = activitiesEditText.getText().toString().trim();

        if (institute.isEmpty()){
            instituteEditText.setError("Please Insert your Institute Name");
            instituteEditText.requestFocus();
        }else if (degree.isEmpty()){
            degreeEditText.setError("Please Insert your Degree");
            degreeEditText.requestFocus();
        }else if (fieldStudy.isEmpty()){
            fieldStudyEditText.setError("Please Insert your Field of Study");
            fieldStudyEditText.requestFocus();
        }else if (grade.isEmpty()){
            gradeEditText.setError("Please Insert your Grade");
            gradeEditText.requestFocus();
        }else if (details.isEmpty()){
            detailsEditText.setError("Please provide your Details");
            detailsEditText.requestFocus();
        }else if (startDate==null){
            startYearTxt.setError("provide your Study Start Date");
            startYearTxt.requestFocus();
        }else if (endDate==null){
            endYearTxt.setError("provide your Study End Date");
            endYearTxt.requestFocus();
        }else{

            if (activities.isEmpty()){
                activitiesEditText.setError("Please provide your Activities");
                activitiesEditText.requestFocus();
            }else{
                Service service = Client.retrofit.create(Service.class);
                Call<JSONObject> call = service.createEducationWithActivities("Bearer"+" "+token,institute,degree,fieldStudy,startDate,endDate,grade,activities,details);
                call.enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.isSuccessful()){
                            new iOSDialogBuilder(getContext())
                                    .setTitle("Thank you")
                                    .setSubtitle("Create Successful")
                                    .setBoldPositiveLabel(true)
                                    .setCancelable(false)
                                    .setPositiveListener("Ok",new iOSDialogClickListener() {
                                        @Override
                                        public void onClick(iOSDialog dialog) {
                                            ExpertFragment expertFragment = new ExpertFragment();
                                            getFragmentManager().beginTransaction().replace(R.id.fragment_container,expertFragment).addToBackStack(null).commit();
                                            dialog.dismiss();
                                        }
                                    })
                                    .build().show();
                        }else{
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
    }

}