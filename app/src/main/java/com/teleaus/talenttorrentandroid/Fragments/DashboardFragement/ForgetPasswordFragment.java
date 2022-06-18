package com.teleaus.talenttorrentandroid.Fragments.DashboardFragement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.Model.Login.LoginModel;
import com.teleaus.talenttorrentandroid.R;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordFragment extends Fragment {

    View v;
    EditText emailEditText,codeEditText,passwordEditText,confirmPassEditText;
    TextView resendEmail,emailTxtView;
    Button sendLink,checkButton;
    LinearLayout hiddenLayout;
    String email,password,confirmPassword,code;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        v= getActivity().findViewById(android.R.id.content);
        initialise(view);

        sendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });

        return view;
    }

    private void initialise(View view) {
        emailEditText = view.findViewById(R.id.emailEditText);
        codeEditText = view.findViewById(R.id.codeEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        confirmPassEditText = view.findViewById(R.id.confirmPassEditText);
        emailTxtView = view.findViewById(R.id.emailTxtView);
        resendEmail = view.findViewById(R.id.resendEmail);
        sendLink = view.findViewById(R.id.sendLink);
        checkButton = view.findViewById(R.id.checkButton);
        hiddenLayout = view.findViewById(R.id.hiddenLayout);
    }

    private void validation() {
         email = emailEditText.getText().toString();
        if (email.isEmpty()){
            emailEditText.setError("Please provide email");
            emailEditText.requestFocus();
        }else{
            Service service = Client.retrofit.create(Service.class);
            Call<JSONObject> call = service.resetEmail(email);
            call.enqueue(new Callback<JSONObject>() {
                @Override
                public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                    if (response.isSuccessful()){
                        /*Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();*/
                        Snackbar.make(v,"Success",Snackbar.LENGTH_SHORT).show();
                        hiddenLayout.setVisibility(View.VISIBLE);
                        resendEmail.setVisibility(View.VISIBLE);

                    }else{
                        /*Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();*/
                        Snackbar.make(v,"Error",Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JSONObject> call, Throwable t) {
                    /*Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Failed",Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void checkValidation() {
        emailTxtView.setText(email);
        password = passwordEditText.getText().toString();
        confirmPassword = confirmPassEditText.getText().toString();
        code = codeEditText.getText().toString();

        if (code.isEmpty()){
            codeEditText.setError("Please Insert your Code");
            codeEditText.requestFocus();
        }else if (password.isEmpty()){
            passwordEditText.setError("Please Insert your Password");
            passwordEditText.requestFocus();
        }else if(confirmPassword.isEmpty()){
            confirmPassEditText.setError("Please Insert your Confirm Password");
            confirmPassEditText.requestFocus();
        }else{
            Service service = Client.retrofit.create(Service.class);
            Call<JSONObject> call = service.checkReset(email,password,confirmPassword,code);
            call.enqueue(new Callback<JSONObject>() {
                @Override
                public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                    if (response.isSuccessful()){
                        /*Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();*/
                        Snackbar.make(v,"Success",Snackbar.LENGTH_SHORT).show();
                        LoginFragment loginFragment = new LoginFragment();
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, loginFragment)
                                .addToBackStack(null).commit();
                    }else{
                        codeEditText.setError("Please Provide Correct Code");
                        codeEditText.requestFocus();
                       /* Toast.makeText(getActivity(), "Code is", Toast.LENGTH_SHORT).show();*/
                    }
                }

                @Override
                public void onFailure(Call<JSONObject> call, Throwable t) {
                    /*Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Failed",Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }
}