package com.teleaus.talenttorrentandroid.Fragments.RegistrationFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.LoginFragment;
import com.teleaus.talenttorrentandroid.Model.Login.LoginModel;
import com.teleaus.talenttorrentandroid.R;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationVerificationFragment extends Fragment {

    View v;
    EditText codeTxt;
    TextView resendEmail;
    Button verifyButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration_verification, container, false);
        v= getActivity().findViewById(android.R.id.content);
        initialise(view);

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPost();
            }
        });
        return view;
    }

    private void initialise(View view) {
        codeTxt = view.findViewById(R.id.codeEditText);
        resendEmail = view.findViewById(R.id.resendEmail);
        verifyButton = view.findViewById(R.id.verifyButton);
    }

    private void sendPost() {
        String code = codeTxt.getText().toString();
        if (code.isEmpty()){
            codeTxt.setError("Insert your code");
            codeTxt.requestFocus();
        }else{
            Service service = Client.retrofit.create(Service.class);
            Call<JSONObject> call = service.postToken(code);
            call.enqueue(new Callback<JSONObject>() {
                @Override
                public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                    if (response.isSuccessful()){
                        /*Toast.makeText(getActivity(), "Registration Successful", Toast.LENGTH_SHORT).show();*/
                        Snackbar.make(v,"Registration Successful",Snackbar.LENGTH_SHORT).show();
                        LoginFragment loginFragment = new LoginFragment();
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, loginFragment)
                                .addToBackStack(null).commit();
                    }else{
                       /* Toast.makeText(getActivity(), "Verification failed", Toast.LENGTH_SHORT).show();*/
                        Snackbar.make(v,"Verification failed",Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JSONObject> call, Throwable t) {
                    /*Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Failed",Snackbar.LENGTH_SHORT).show();
                }
            });
        }

    }
}