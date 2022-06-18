package com.teleaus.talenttorrentandroid.Fragments.DashboardFragement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.teleaus.talenttorrentandroid.Activities.MainActivity;
import com.teleaus.talenttorrentandroid.Api.ButtonPressCloseWindow;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.Fragments.HomeFragment;
import com.teleaus.talenttorrentandroid.Fragments.RegistrationFragments.RegistrationFragment;
import com.teleaus.talenttorrentandroid.Model.Login.LoginModel;
import com.teleaus.talenttorrentandroid.R;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class LoginFragment extends Fragment implements ButtonPressCloseWindow {

    View v;
    public static ButtonPressCloseWindow backlistener;
    EditText emailTxt,passwordTxt;
    TextView signUpTxt,incorrectPasswordShow,forgetPasswordTxt,textId;
    Button loginButton,linkedinButton;
    String email,password;
    SharedPreferences sharedPreferences;
    ///facebook
    SharedPreferences.Editor editor;
    /*LoginButton facebookButton;*/
    Button facebookButton;
    CallbackManager callbackManager;
    String socialLoginFirst_name,socialLoginLast_name,socialLoginEmail,socialLoginId;
    ///Google
    Button googleButton;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int SIGN_IN = 1;
    String googlePersonName,googleFirstName,googleLastName,googlePersonEmail,googlePersonId;
    Uri personPhoto;

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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        v= getActivity().findViewById(android.R.id.content);
        defineView(view);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginValidation(v);
            }
        });

        signUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegistrationFragment registrationFragment = new RegistrationFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, registrationFragment)
                        .addToBackStack(null).commit();
            }
        });

        forgetPasswordTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgetPasswordFragment forgetPasswordFragment = new ForgetPasswordFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, forgetPasswordFragment)
                        .addToBackStack(null).commit();
            }
        });

        callbackManager = CallbackManager.Factory.create();

        /*AccessToken token = AccessToken.getCurrentAccessToken();
        if (token!=null){
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }else {
            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
        }*/

        checkLoginStatus(v);


        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fblogin(v);

            }
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(),googleSignInOptions);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        return view;
    }

    private void Fblogin(View v) {
        callbackManager = CallbackManager.Factory.create();
        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","user_photos","public_profile"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {


                    }

                    @Override
                    public void onCancel() {
                        /*Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();*/
                        Snackbar.make(v,"Cancel",Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        /*Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();*/
                        Snackbar.make(v,"Failed",Snackbar.LENGTH_LONG).show();
                    }
                });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,SIGN_IN);
    }
    private void defineView(View view) {
        textId = view.findViewById(R.id.textId);
        emailTxt = view.findViewById(R.id.emailTxt);
        passwordTxt = view.findViewById(R.id.passwordTxt);
        incorrectPasswordShow = view.findViewById(R.id.incorrectPasswordShow);
        forgetPasswordTxt = view.findViewById(R.id.forgetPasswordTxt);
        signUpTxt = view.findViewById(R.id.signUpTxt);
        loginButton = view.findViewById(R.id.loginButton);

        facebookButton= view.findViewById(R.id.facebookButton);
        googleButton= view.findViewById(R.id.googleButton);
        /*linkedinButton= view.findViewById(R.id.linkedinButton);*/
        FacebookSdk.sdkInitialize(getActivity());

    }

    private void loginValidation(View v) {
        email= emailTxt.getText().toString().trim();;
        password= passwordTxt.getText().toString().trim();;

        if (email.isEmpty()) {
            emailTxt.setError("Please provide email");
            emailTxt.requestFocus();
        }else if(password.isEmpty()){
            passwordTxt.setError("Please provide password");
            passwordTxt.requestFocus();
        }else{
            loginPost(v);
        }
    }

    private void loginPost(View v) {
        Service service = Client.retrofit.create(Service.class);
        Call<LoginModel> call = service.getLoginData(email,password);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                LoginModel loginModel = response.body();
                if (response.isSuccessful()){

                    incorrectPasswordShow.setVisibility(View.GONE);
                    forgetPasswordTxt.setVisibility(View.GONE);
                   String token=  loginModel.getAccess_token();
                   String type =  loginModel.getData().getType();
                   String firstName =  loginModel.getData().getFirst_name();
                   String lastName =  loginModel.getData().getLast_name();
                   String profilePhoto =  loginModel.getData().getProfile().getAvatar_url();

                   editor.putString("token",token);
                   editor.putString("email",email);
                   editor.putString("password",password);
                   editor.putString("type",type);
                   editor.putString("firstName",firstName);
                   editor.putString("lastName",lastName);
                   editor.putString("profilePhoto",profilePhoto);
                   editor.apply();

                    /*HomeFragment homeFragment = new HomeFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, homeFragment)
                            .addToBackStack(null).commit();*/
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);


                }else{
                   /* loginModel.isSuccess();*/
                    incorrectPasswordShow.setVisibility(View.VISIBLE);
                    forgetPasswordTxt.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                /*Toast.makeText(getActivity(), "Response Failed", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Response Failed",Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void facebookLoginPost() {
        Service service = Client.retrofit.create(Service.class);
        Call<LoginModel> call = service.getSocialLoginData(socialLoginFirst_name,socialLoginLast_name,socialLoginEmail,"facebook",socialLoginId);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                LoginModel loginModel = response.body();
                if (response.isSuccessful()){

                    incorrectPasswordShow.setVisibility(View.GONE);
                    forgetPasswordTxt.setVisibility(View.GONE);
                    String token=  loginModel.getAccess_token();
                    String email=  loginModel.getData().getEmail();
                    String type =  loginModel.getData().getType();
                    String firstName =  loginModel.getData().getFirst_name();
                    String lastName =  loginModel.getData().getLast_name();
                    String profilePhoto =  loginModel.getData().getProfile().getAvatar_url();

                    editor.putString("token",token);
                    editor.putString("email",email);
                    editor.putString("password",password);
                    editor.putString("type",type);
                    editor.putString("firstName",firstName);
                    editor.putString("lastName",lastName);
                    editor.putString("profilePhoto",profilePhoto);
                    editor.apply();

                    /*HomeFragment homeFragment = new HomeFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, homeFragment)
                            .addToBackStack(null).commit();*/
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);


                }else{
                    /* loginModel.isSuccess();*/
                    incorrectPasswordShow.setVisibility(View.VISIBLE);
                    forgetPasswordTxt.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                /*Toast.makeText(getActivity(), "Response Failed", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Response Failed",Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
            if (acct != null) {
                 googleFirstName = acct.getGivenName();
                 googleLastName = acct.getFamilyName();
                 googlePersonEmail = acct.getEmail();
                 googlePersonId = acct.getId();
                 personPhoto = acct.getPhotoUrl();
            }
            Service service = Client.retrofit.create(Service.class);
            Call<LoginModel> call = service.getGoogleLoginData(googleFirstName,googleLastName,googlePersonEmail,"google",googlePersonId);
            call.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    LoginModel loginModel = response.body();
                    if (response.isSuccessful()){

                        incorrectPasswordShow.setVisibility(View.GONE);
                        forgetPasswordTxt.setVisibility(View.GONE);
                        String token= loginModel.getAccess_token();
                        String email= loginModel.getData().getEmail();
                        String type = loginModel.getData().getType();
                        String firstName = loginModel.getData().getFirst_name();
                        String lastName = loginModel.getData().getLast_name();
                        String profilePhoto = loginModel.getData().getProfile().getAvatar_url();

                        editor.putString("token",token);
                        editor.putString("email",email);
                        editor.putString("password",password);
                        editor.putString("type",type);
                        editor.putString("firstName",firstName);
                        editor.putString("lastName",lastName);
                        editor.putString("profilePhoto",profilePhoto);
                        editor.apply();

                    /*HomeFragment homeFragment = new HomeFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, homeFragment)
                            .addToBackStack(null).commit();*/

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);


                    }else{
                        /* loginModel.isSuccess();*/
                        incorrectPasswordShow.setVisibility(View.VISIBLE);
                        forgetPasswordTxt.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    /*Toast.makeText(getActivity(), "Response Failed", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Response Failed",Snackbar.LENGTH_LONG).show();
                }
            });

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }

    private void googleLoginPost() {
        Service service = Client.retrofit.create(Service.class);
        Call<LoginModel> call = service.getGoogleLoginData(socialLoginFirst_name,socialLoginLast_name,socialLoginEmail,"google",socialLoginId);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                LoginModel loginModel = response.body();
                if (response.isSuccessful()){

                    incorrectPasswordShow.setVisibility(View.GONE);
                    forgetPasswordTxt.setVisibility(View.GONE);
                    String token= loginModel.getAccess_token();
                    String email= loginModel.getData().getEmail();
                    String type = loginModel.getData().getType();
                    String firstName = loginModel.getData().getFirst_name();
                    String lastName = loginModel.getData().getLast_name();
                    String profilePhoto = loginModel.getData().getProfile().getAvatar_url();

                    editor.putString("token",token);
                    editor.putString("email",email);
                    editor.putString("password",password);
                    editor.putString("type",type);
                    editor.putString("firstName",firstName);
                    editor.putString("lastName",lastName);
                    editor.putString("profilePhoto",profilePhoto);
                    editor.apply();

                    /*HomeFragment homeFragment = new HomeFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, homeFragment)
                            .addToBackStack(null).commit();*/

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);


                }else{
                    /* loginModel.isSuccess();*/
                    incorrectPasswordShow.setVisibility(View.VISIBLE);
                    forgetPasswordTxt.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                /*Toast.makeText(getActivity(), "Response Failed", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Response Failed",Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
           /* facebookLoginPost();*/
        }
    }
    private void loadUserProfile(AccessToken newAccessToken){
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(@Nullable JSONObject object, @Nullable GraphResponse graphResponse) {

                if (object !=null){
                    try {
                        socialLoginFirst_name = object.getString("first_name");
                        socialLoginLast_name = object.getString("last_name");
                        socialLoginEmail = object.getString("email");
                        socialLoginId = object.getString("id");

                        String image_url = "https://graph.facebook.com/"+socialLoginId+"/picture?type=normal";
                        facebookLoginPost();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    /*Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Not found",Snackbar.LENGTH_LONG).show();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }
    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken ==null){
               /* Toast.makeText(getActivity(), "Not login", Toast.LENGTH_SHORT).show();*/
                Log.e("msg","Not Login");
            }else{
                loadUserProfile(currentAccessToken);
            }
        }
    };
    private void checkLoginStatus(View v){
        if (AccessToken.getCurrentAccessToken()!=null){
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }else{
            LoginManager.getInstance().logOut();
        }
    }
    @Override
    public void onPause() {
        backlistener = null;
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        backlistener = this;
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 1 ) {
            // If there are back-stack entries, leave the FragmentActivity
            // implementation take care of them.
            manager.popBackStack();

        } else {

            // Otherwise, ask user if he wants to leave :)
            new iOSDialogBuilder(getContext())
                    .setTitle("Really Exit?")
                    .setSubtitle("Are you sure you want to exit?")
                    .setBoldPositiveLabel(true)
                    .setCancelable(false)
                    .setNegativeListener("Cancel", new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveListener("Ok",new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {

                            dialog.dismiss();
                            getActivity().moveTaskToBack(true);
                            getActivity().moveTaskToBack(true);
                            getActivity().finish();

                        }
                    })
                    .build().show();

        }
    }
}