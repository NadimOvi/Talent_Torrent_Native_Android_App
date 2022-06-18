package com.teleaus.talenttorrentandroid.Fragments.JobsFragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientActiveJobFragment;
import com.teleaus.talenttorrentandroid.Fragments.HomeFragment;
import com.teleaus.talenttorrentandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JobCreateFragment extends Fragment {

    View v;
    EditText jobTitleEditText,budgetEditText,descriptionEditText;
    Button applyJob,draftedJob;
    TextView endDateEditText;
    Spinner jobTypeSpinner,categorySpinner;
    String token,email,password,profileType;
    ChipGroup chipGroup;
    ImageButton addButton;
    EditText tagEditText;
    String date, jobtype;

    Integer field_Id;
    Integer fieldId;
    ArrayList<String> CategoryName;
    ArrayList<Integer> CategoryId;
    ArrayList<String> arrayTags;
    String[] tags;
    String URL = "https://talenttorrent.com/api/v1/job/categories";
    String testBody;


    ArrayList<String> arr=new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job_create, container, false);
        v= getActivity().findViewById(android.R.id.content);
        initialise(view);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        /////jobTypeSpinner
        jobTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                JobTypeModel jobTypeModel =(JobTypeModel) adapterView.getSelectedItem();
              /*  displayTypeData(jobTypeModel);*/

                jobtype = jobTypeModel.getTypePostName();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ///jobCategorySpinner
        fieldList(URL);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String categoryName = categorySpinner.getItemAtPosition(categorySpinner.getSelectedItemPosition()).toString();

                fieldId = CategoryId.get(i);
                /*Toast.makeText(getActivity(), categoryName +"\n"+fieldId, Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                arr.add(tagEditText.getText()+"");
                final Chip chip = new Chip(getActivity());

                ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getActivity(),
                        null,
                        0,
                        R.style.Widget_MaterialComponents_Chip_Entry);
                chip.setChipDrawable(chipDrawable);
                chip.setText(tagEditText.getText()+"");
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chipGroup.removeView(chip);
                        arr.remove(chip.getText());
                        /*Toast.makeText(getActivity(),arr.size()+"",Toast.LENGTH_LONG).show();*/
                    }
                });
                chipGroup.addView(chip);
                tagEditText.setText("");


            }
        });


        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        month = month+1;

                        date = year+"-"+month+"-"+day;
                        endDateEditText.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        
        applyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validation();

            }
        });

        draftedJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                draftedValidation();
            }
        });

        return view;
    }

    private void initialise(View view) {
        Bundle bundle = this.getArguments();
        token = bundle.getString("token");
        email = bundle.getString("email");
        password = bundle.getString("password");
        profileType = bundle.getString("profileType");

        jobTypeSpinner = view.findViewById(R.id.jobTypeSpinner);
        categorySpinner = view.findViewById(R.id.categorySpinner);

        chipGroup = view.findViewById(R.id.chipGroup);
        addButton = view.findViewById(R.id.addButton);
        tagEditText = view.findViewById(R.id.tagEditText);

        jobTitleEditText = view.findViewById(R.id.jobTitleEditText);
        endDateEditText = view.findViewById(R.id.endDateEditText);
        budgetEditText = view.findViewById(R.id.budgetEditText);
        descriptionEditText = view.findViewById(R.id.descriptionEditText);
        draftedJob = view.findViewById(R.id.draftedJob);
        applyJob = view.findViewById(R.id.applyJob);

        CategoryName =new ArrayList<>();
        CategoryId =new ArrayList<>();
        arrayTags = new ArrayList<>();

        List<JobTypeModel> jobTypeModelList = new ArrayList<>();
        JobTypeModel jobTypeModel1 =new JobTypeModel("project","Per Project");
        jobTypeModelList.add(jobTypeModel1);
        JobTypeModel jobTypeModel2 =new JobTypeModel("month","Monthly");
        jobTypeModelList.add(jobTypeModel2);
        JobTypeModel jobTypeModel3 =new JobTypeModel("week","weekly");
        jobTypeModelList.add(jobTypeModel3);
        JobTypeModel jobTypeModel4 =new JobTypeModel("hour","Hourly");
        jobTypeModelList.add(jobTypeModel4);

        ArrayAdapter<JobTypeModel> adapter = new ArrayAdapter<JobTypeModel>(getActivity(),
                R.layout.spinner_textview, jobTypeModelList);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        jobTypeSpinner.setPrompt("Select Your Type");
        jobTypeSpinner.setAdapter(adapter);

    }

    private void fieldList(String url) {
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("data");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String categoryName=jsonObject1.getString("name");
                        CategoryName.add(categoryName);

                        field_Id = jsonObject1.getInt("id");
                        CategoryId.add(field_Id);

                    }

                    if (getActivity()!=null){
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                R.layout.spinner_textview, CategoryName);
                        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
                        categorySpinner.setPrompt("Select Your Category");
                        categorySpinner.setAdapter(adapter);
                    }

                }catch (JSONException e){e.printStackTrace();}
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){

            /*//This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> params = new HashMap<String, String>();
                *//*HashMap<String, String> headers = new HashMap<String, String>();*//*
                params.put("Authorization", token);
                return params;
            }*/
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void setJobTypeSpinnerError(Spinner spinner, String error){
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }
    }

    private void setJobCategorySpinnerError(Spinner spinner, String error){
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }
    }

    private void validation() {

        int budget = 0;
        String title = jobTitleEditText.getText().toString().trim();
        String num = budgetEditText.getText().toString();


        ArrayList<String> arrayList = new ArrayList<>();
        String result="";
        for(int i=0;i<arr.size();i++){
            result+= arr.get(i)+",";

            arrayList.add(arr.get(i));
        }
        if(arr.size()>0){
            result=result.substring(0,result.length()-1);
        }


        try {
            budget = Integer.parseInt(num);

        } catch (NumberFormatException e) {
        }

        String description = descriptionEditText.getText().toString().trim();

        if (title.isEmpty()){
            jobTitleEditText.setError("Please Provide Job Title");
            jobTitleEditText.requestFocus();
        }else if(result.isEmpty()){
            tagEditText.setError("Please Provide Tag");
            tagEditText.requestFocus();
        }else if(date==null){
            endDateEditText.setError("Please Provide Date");
            endDateEditText.requestFocus();
        }else if(budget==0){
            budgetEditText.setError("Please Insert your Budget");
            budgetEditText.requestFocus();
        }else if(description.isEmpty()){
            descriptionEditText.setError("Please Provide your Description");
            descriptionEditText.requestFocus();
        }else{

            Service service = Client.retrofit.create(Service.class);
            Call<createJobPostModel> call = service.createJob("Bearer"+" "+token,title,jobtype,fieldId,result,date,budget,description);
            call.enqueue(new Callback<createJobPostModel>() {
                @Override
                public void onResponse(Call<createJobPostModel> call, Response<createJobPostModel> response) {
                    if (response.isSuccessful()){

                        new iOSDialogBuilder(getContext())
                                .setTitle("Thank you")
                                .setSubtitle("Your job has been created")
                                .setBoldPositiveLabel(true)
                                .setCancelable(false)
                                .setPositiveListener("Ok",new iOSDialogClickListener() {
                                    @Override
                                    public void onClick(iOSDialog dialog) {
                                        HomeFragment homeFragment = new HomeFragment();
                                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).addToBackStack(null).commit();
                                        dialog.dismiss();

                                    }
                                })
                                .build().show();

                    }else{
                        /*Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();*/
                        Snackbar.make(v,"Error",Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<createJobPostModel> call, Throwable t) {
                    /*Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Failed",Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static RequestBody createFromString(String string) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text"), string);
        return requestBody;
    }

    private void draftedValidation() {
        int budget = 0;
        String title = jobTitleEditText.getText().toString().trim();
        String num = budgetEditText.getText().toString();

        ArrayList<String> arrayList = new ArrayList<>();
        String result="";
        for(int i=0;i<arr.size();i++){
            result+= arr.get(i)+",";

            arrayList.add(arr.get(i));
        }
        if(arr.size()>0){
            result=result.substring(0,result.length()-1);
        }

        try {
            budget = Integer.parseInt(num);

        } catch (NumberFormatException e) {
        }

        String description = descriptionEditText.getText().toString().trim();

        if (title.isEmpty()){
            jobTitleEditText.setError("Please Provide Job Title");
            jobTitleEditText.requestFocus();
        }else if(result.isEmpty()){
            tagEditText.setError("Please Provide Tag");
            tagEditText.requestFocus();
        }else if(date==null){
            endDateEditText.setError("Please Provide Date");
            endDateEditText.requestFocus();
        }else if(budget==0){
            budgetEditText.setError("Please Insert your Budget");
            budgetEditText.requestFocus();
        }else if(description.isEmpty()){
            descriptionEditText.setError("Please Provide your Description");
            descriptionEditText.requestFocus();
        }else{

            /*for (int i=0;i<chipGroup.getChildCount();i++)
            {
                Chip chip = (Chip) chipGroup.getChildAt(i);
                arrayTags.add(chip.getText()+"");

                arrayTags.toString();

            }*/

            Service service = Client.retrofit.create(Service.class);
            Call<draftedJobPostModel> call = service.draftedJob("Bearer"+" "+token,title,jobtype,fieldId,result,date,budget,description,"drafted");

            call.enqueue(new Callback<draftedJobPostModel>() {
                @Override
                public void onResponse(Call<draftedJobPostModel> call, Response<draftedJobPostModel> response) {
                    if (response.isSuccessful()){

                        new iOSDialogBuilder(getContext())
                                .setTitle("Thank you")
                                .setSubtitle("Your job has been drafted")
                                .setBoldPositiveLabel(true)
                                .setCancelable(false)
                                .setPositiveListener("Ok",new iOSDialogClickListener() {
                                    @Override
                                    public void onClick(iOSDialog dialog) {
                                        HomeFragment homeFragment = new HomeFragment();
                                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).addToBackStack(null).commit();
                                        dialog.dismiss();

                                    }
                                })
                                .build().show();
                    }else{
                        /*Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();*/
                        Snackbar.make(v,"Failed",Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<draftedJobPostModel> call, Throwable t) {
                    /*Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Failed",Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }

}