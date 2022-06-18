package com.teleaus.talenttorrentandroid.Fragments.RegistrationFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.LoginFragment;
import com.teleaus.talenttorrentandroid.Fragments.JobsFragment.JobTypeModel;
import com.teleaus.talenttorrentandroid.Model.Login.LoginModel;
import com.teleaus.talenttorrentandroid.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationFragment extends Fragment {

    View v;
    EditText firstNameTxt,lastNameTxt, emailTxt, passwordTxt, confirmTxt;
    TextView signInTxt;
    Spinner userTypeSpinner;
    Button registerButton,facebookButton,googleButton, linkedinButton;

    String userType;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        v= getActivity().findViewById(android.R.id.content);
        defineView(view);

        signInTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginFragment loginFragment = new LoginFragment();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, loginFragment)
                        .addToBackStack(null).commit();
            }
        });
        userTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                UserTypeModel userTypeModel =(UserTypeModel) adapterView.getSelectedItem();
                userType = userTypeModel.getTypePostName();
                /*Toast.makeText(getActivity(),userType, Toast.LENGTH_SHORT).show();*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postValidation();
            }
        });
        return view;
    }

    private void defineView(View view) {
        firstNameTxt = view.findViewById(R.id.firstNameTxt);
        lastNameTxt = view.findViewById(R.id.lastNameTxt);
        emailTxt = view.findViewById(R.id.emailTxt);
        passwordTxt = view.findViewById(R.id.passwordTxt);
        confirmTxt = view.findViewById(R.id.confirmTxt);
        signInTxt = view.findViewById(R.id.signInTxt);
        userTypeSpinner = view.findViewById(R.id.userTypeSpinner);
        registerButton = view.findViewById(R.id.registerButton);

        List<UserTypeModel> userTypeModels = new ArrayList<>();
        UserTypeModel userTypeModel1 =new UserTypeModel("expert","I want to work");
        userTypeModels.add(userTypeModel1);
        UserTypeModel userTypeModel2 =new UserTypeModel("client","I want to hire");
        userTypeModels.add(userTypeModel2);
        UserTypeModel userTypeModel3 =new UserTypeModel("user","I want to learn");
        userTypeModels.add(userTypeModel3);

        ArrayAdapter<UserTypeModel> adapter = new ArrayAdapter<UserTypeModel>(getActivity(),
                R.layout.spinner_textview, userTypeModels);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        userTypeSpinner.setPrompt("Select Your Type");
        userTypeSpinner.setAdapter(adapter);
    }


    private void postValidation() {
        String firstName = firstNameTxt.getText().toString();
        String lastName = lastNameTxt.getText().toString();
        String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();
        String confirmPassword = confirmTxt.getText().toString();
        
        if (firstName.isEmpty()){
            firstNameTxt.setError("Please provide your First Name");
            firstNameTxt.requestFocus();
        }else if (lastName.isEmpty()){
            lastNameTxt.setError("Please provide your Last Name");
            lastNameTxt.requestFocus();
        }else if (email.isEmpty()){
            emailTxt.setError("Please provide your Email");
            emailTxt.requestFocus();
        }else if (password.isEmpty()){
            passwordTxt.setError("Please provide your Password");
            passwordTxt.requestFocus();
        }else if (confirmPassword.isEmpty()){
            confirmTxt.setError("Please provide your Confirm Password");
            confirmTxt.requestFocus();
        }else if (!password.equals(confirmPassword)){
            /*Toast.makeText(getActivity(), "The password confirmation does not match", Toast.LENGTH_SHORT).show();*/
            Snackbar.make(v,"The password confirmation does not match",Snackbar.LENGTH_SHORT).show();
        }else{
            Service service = Client.retrofit.create(Service.class);
            Call<LoginModel> call = service.getRegistrationData(firstName,lastName,email,password,confirmPassword,userType);

            call.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    LoginModel loginModel = response.body();
                    if (response.isSuccessful()){

                        RegistrationVerificationFragment registrationVerificationFragment = new RegistrationVerificationFragment();
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, registrationVerificationFragment)
                                .addToBackStack(null).commit();
                    }else{
                        /*Toast.makeText(getActivity(),"The email has already been taken", Toast.LENGTH_SHORT).show();*/
                        Snackbar.make(v,"The email has already been taken",Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                   /* Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Failed",Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }

}