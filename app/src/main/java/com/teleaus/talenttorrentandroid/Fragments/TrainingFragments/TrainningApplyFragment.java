package com.teleaus.talenttorrentandroid.Fragments.TrainingFragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
/*import com.stripe.android.model.StripeIntent;*/
import com.stripe.android.SetupIntentResult;
import com.stripe.android.Stripe;

import com.stripe.android.model.Card;
import com.stripe.android.model.ConfirmSetupIntentParams;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.model.SetupIntent;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.Fragments.JobsFragment.JobTypeModel;
import com.teleaus.talenttorrentandroid.R;

/*import org.jetbrains.annotations.NotNull;*/

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.stripe.android.view.CardInputWidget;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainningApplyFragment<REQUEST_CODE> extends Fragment{
    View v;
    View fragmentView;
    private ProgressDialog progressDialog;
    EditText locationEditText,phoneNumberEditText,addressEditText;
    TextView trainngingTittle,locationShow, durationShow,tentativeDateText,
            snacksTxt,priceShow, contentTxt, dateShow,checkCoupon,paymentMethodButton;
    Toolbar toolbarId;
    String profileEmail, password, type,cardType;
    Button registerButton;
    Integer userId,lunch,assessment,snacks,fee,trainingID;
    String title,location,duration,date,certificate,details,postDate;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Spinner cardTypeSpinner;
    String radioButtonValue,phone,address,cardBrandName;
    private String setupIntentClientSecret;

    public String card,cardtype;

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    String paymentMethodId;
    CardInputWidget cardInputWidget;
    Button save_payment,cancel_payment;
    String strip_Token;

    private Stripe stripe;
    EditText emailInput;
    private String token = "";
    String publishedKey = "pk_test_2gr4dBnIiTzKsnOzTTbQQ9lv";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_trainning_apply, container, false);
        v= getActivity().findViewById(android.R.id.content);
        initialise(view);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        toolbarId.setNavigationIcon(R.drawable.ic_backbutton);
        toolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        cardTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TrainningCardModel trainningCardModel =(TrainningCardModel) adapterView.getSelectedItem();
                /*  displayTypeData(jobTypeModel);*/

                cardType = trainningCardModel.getCardPostName();
                if (cardType.equals("card")){
                    paymentMethodButton.setVisibility(View.VISIBLE);
                }else{
                    paymentMethodButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        paymentMethodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUpDialog();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(radioGroup.getCheckedRadioButtonId()==-1){
                    /*Toast.makeText(getActivity(), "Please select any Participate", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Please select any Participate",Snackbar.LENGTH_SHORT).show();
                }else{
                    checkAnotherValidation();
                }
            }
        });

        tentativeDateText.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        month = month+1;
                        postDate = year+"-"+month+"-"+day;
                        tentativeDateText.setText(postDate);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        return view;
    }

    private void checkAnotherValidation() {
        String radioValue = String.valueOf(radioButton.getText());
        phone = phoneNumberEditText.getText().toString();
        address = addressEditText.getText().toString();
        if (radioValue.equals("Individual")){
            radioButtonValue = "individual";
            if(tentativeDateText.getText()==null) {
                tentativeDateText.setError("Please Provide Date");
                tentativeDateText.requestFocus();

            }
            else if (phone.isEmpty()){
                phoneNumberEditText.setError("Please Provide Phone Number");
                phoneNumberEditText.requestFocus();

            }
            else if (address.isEmpty()){
                addressEditText.setError("Please Provide Address");
                addressEditText.requestFocus();

            }
            else if(paymentMethodId.isEmpty()){
                /*Toast.makeText(getActivity(), "Add your Card", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Add your Card",Snackbar.LENGTH_SHORT).show();
            }
            else if(postDate!=null&&phone!=null&&address!=null&&paymentMethodId!=null){


                 postRegisterTraining();
            }
        }else if(radioValue.equals("Group")){
            radioButtonValue = "group";
            if(postDate.isEmpty()) {
                tentativeDateText.setError("Please Provide Date");
                tentativeDateText.requestFocus();
            }else if (phone.isEmpty()){
                phoneNumberEditText.setError("Please Provide Phone Number");
                phoneNumberEditText.requestFocus();
            }else if (address.isEmpty()){
                addressEditText.setError("Please Provide Address");
                addressEditText.requestFocus();
            }else if(paymentMethodId.isEmpty()){
               /* Toast.makeText(getActivity(), "Add your PayCard", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Add your Card",Snackbar.LENGTH_SHORT).show();
            }
            else{
                /*Toast.makeText(getActivity(), "True", Toast.LENGTH_SHORT).show();*/
                 postRegisterTraining();
            }
        }else if (radioValue.equals("Business")){
            radioButtonValue = "business";
            if(postDate.isEmpty()) {
                tentativeDateText.setError("Please Provide Date");
                tentativeDateText.requestFocus();
            }else if (phone.isEmpty()){
                phoneNumberEditText.setError("Please Provide Phone Number");
                phoneNumberEditText.requestFocus();
            }else if (address.isEmpty()){
                addressEditText.setError("Please Provide Address");
                addressEditText.requestFocus();
            }else if(paymentMethodId.isEmpty()){
                /*Toast.makeText(getActivity(), "Add your PayCard", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Add your Card",Snackbar.LENGTH_SHORT).show();
            }
            else{
               /* Toast.makeText(getActivity(), "True", Toast.LENGTH_SHORT).show();*/
                 postRegisterTraining();
            }
        }
    }

    private void initialise(View view) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

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
        type = bundle.getString("typetype");

        toolbarId = view.findViewById(R.id.toolbarId);
        trainngingTittle = view.findViewById(R.id.trainngingTittle);
        locationShow = view.findViewById(R.id.locationShow);
        durationShow = view.findViewById(R.id.durationShow);
        dateShow = view.findViewById(R.id.dateShow);
        priceShow = view.findViewById(R.id.priceShow);
        paymentMethodButton = view.findViewById(R.id.paymentMethodButton);

        locationEditText = view.findViewById(R.id.locationEditText);
        tentativeDateText = view.findViewById(R.id.tentativeDateText);
        phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText);
        addressEditText = view.findViewById(R.id.addressEditText);
        cardTypeSpinner = view.findViewById(R.id.cardTypeSpinner);

        radioGroup = view.findViewById(R.id.radioGroup);
        registerButton = view.findViewById(R.id.registerButton);
        checkCoupon = view.findViewById(R.id.checkCoupon);


        trainngingTittle.setText(title);
        locationShow.setText(location);
        durationShow.setText(duration);
        dateShow.setText(date);
        priceShow.setText(String.valueOf(fee));

        List<TrainningCardModel> trainningCardModels = new ArrayList<>();
        TrainningCardModel trainningCardModel1 =new TrainningCardModel("invoice","Email me an invoice");
        trainningCardModels.add(trainningCardModel1);
        TrainningCardModel TrainningCardModel2 =new TrainningCardModel("card","Secure Credit/Debit Card");
        trainningCardModels.add(TrainningCardModel2);

        ArrayAdapter<TrainningCardModel> adapter = new ArrayAdapter<TrainningCardModel>(getActivity(),
                R.layout.spinner_textview, trainningCardModels);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        cardTypeSpinner.setPrompt("Select Your Type");
        cardTypeSpinner.setAdapter(adapter);
        createIntent();
    }

    private void postRegisterTraining() {

            Service service = Client.retrofit.create(Service.class);
            Call<JSONObject> call = service.applyTrainingCard("Bearer"+" "+token,trainingID,postDate,
                    paymentMethodId, cardType,radioButtonValue,address,phone);
            call.enqueue(new Callback<JSONObject>() {
                @Override
                public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {

                    if (response.isSuccessful()){
                        /*Toast.makeText(getActivity(),"Your Payment is Successful", Toast.LENGTH_SHORT).show();*/
                        Snackbar.make(v,"Your Payment is Successful",Snackbar.LENGTH_SHORT).show();

                    }else{
                        /*Toast.makeText(getActivity(),response.message(), Toast.LENGTH_SHORT).show();*/
                        /*paymentMethodButton.setText(demoClass.getData().toString());*/
                        Snackbar.make(v,response.message(),Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JSONObject> call, Throwable t) {

                }
            });

    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.fragmentView = view;


        for (int iRadioButton = 0;iRadioButton<3;iRadioButton++){
            int idView = getResources().getIdentifier("radioButton"+iRadioButton, "id", getContext().getPackageName());
            View eventView = view.findViewById(idView);
            eventView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRadioButtonClicked(view);
                }
            });
        }
    }

    public void onRadioButtonClicked(View view){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = view.findViewById(radioId);

    }

    private void createIntent(){

        Service service = Client.retrofit.create(Service.class);
        retrofit2.Call<TokenPassResponseModel> call = service.tokenPass("Bearer"+" "+token);
        call.enqueue(new retrofit2.Callback<TokenPassResponseModel>() {
            @Override
            public void onResponse(retrofit2.Call<TokenPassResponseModel> call, retrofit2.Response<TokenPassResponseModel> response) {
                TokenPassResponseModel mainModel = response.body();
                if (response.isSuccessful()){
                    setupIntentClientSecret = mainModel.getData().getIntent().getClient_secret();

                    stripe = new Stripe(getContext(), "pk_test_2gr4dBnIiTzKsnOzTTbQQ9lv");
                }else{
                    /*Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Error",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<TokenPassResponseModel> call, Throwable t) {
               /* Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Failed",Snackbar.LENGTH_SHORT).show();
            }
        });
    }


    private void createPopUpDialog() {
        dialogBuilder = new AlertDialog.Builder(getActivity());


        final View popUp_card = getLayoutInflater().inflate(R.layout.popup_card_view,null);

        save_payment = popUp_card.findViewById(R.id.save_payment);
        cancel_payment = popUp_card.findViewById(R.id.cancel_payment);
        dialogBuilder.setView(popUp_card);
        dialogBuilder.setCancelable(false);
        dialog = dialogBuilder.create();
        dialog.show();

        save_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*progressDialog.show();*/
                /*saveCard();*/
                cardInputWidget = popUp_card.findViewById(R.id.card_input_widget);
                PaymentMethodCreateParams.Card card = cardInputWidget.getPaymentMethodCard();
                emailInput = popUp_card.findViewById(R.id.emailInput);
                PaymentMethod.BillingDetails billingDetails = (new PaymentMethod.BillingDetails.Builder())
                        .setEmail(emailInput.getText().toString())
                        .build();
                if (card != null) {
                    // Create SetupIntent confirm parameters with the above
                    PaymentMethodCreateParams paymentMethodParams = PaymentMethodCreateParams
                            .create(card, billingDetails);
                    ConfirmSetupIntentParams confirmParams = ConfirmSetupIntentParams
                            .create(paymentMethodParams, setupIntentClientSecret);
                    stripe.confirmSetupIntent(TrainningApplyFragment.this, confirmParams);
                    progressDialog.show();
                }

            }
        });
        cancel_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        WeakReference<Activity> weakActivity = new WeakReference<>(getActivity());

        // Handle the result of stripe.confirmSetupIntent
        stripe.onSetupResult(requestCode, data, new ApiResultCallback<SetupIntentResult>() {
            @Override
            public void onSuccess(@NonNull SetupIntentResult result) {
                progressDialog.dismiss();
                SetupIntent setupIntent = result.getIntent();
                SetupIntent.Status status = setupIntent.getStatus();
                if (status == SetupIntent.Status.Succeeded) {
                    // Setup completed successfully
                    dialog.dismiss();
                    getActivity().runOnUiThread(() -> {
                        if (weakActivity.get() != null) {
                            Activity activity = weakActivity.get();


                           /* AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setTitle("Setup completed");*/
                            /*Gson gson = new GsonBuilder().setPrettyPrinting().create();*/
                           /* builder.setMessage(gson.toJson(setupIntent));*/
                            /*Toast.makeText(activity, setupIntent.getPaymentMethodId(), Toast.LENGTH_SHORT).show();*/
                             paymentMethodId = setupIntent.getPaymentMethodId();
                            paymentMethodButton.setText("Your "+paymentMethodId+" card has been added");
                            /*builder.setPositiveButton("Restart demo", (DialogInterface dialog, int index) -> {
                                CardInputWidget cardInputWidget = getActivity().findViewById(R.id.card_input_widget);
                                cardInputWidget.clear();
                                EditText emailInput = getActivity().findViewById(R.id.emailInput);
                                emailInput.setText(null);
                                createIntent();
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();*/
                        }
                    });
                } else if (status == SetupIntent.Status.RequiresPaymentMethod) {
                    // Setup failed – allow retrying using a different payment method
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            progressDialog.dismiss();
                            Activity activity = weakActivity.get();
                            if (activity != null) {
                                /*AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                builder.setTitle("Setup failed");
                                builder.setMessage(setupIntent.getLastSetupError().getMessage());
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int index) {
                                        CardInputWidget cardInputWidget = getActivity().findViewById(R.id.card_input_widget);
                                        cardInputWidget.clear();
                                        EditText emailInput = getActivity().findViewById(R.id.emailInput);
                                        emailInput.setText(null);
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();*/
                                /*Toast.makeText(activity, "Payment Failed", Toast.LENGTH_SHORT).show();*/
                                Snackbar.make(v,"Payment Failed",Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(@NonNull Exception e) {
                dialog.dismiss();
                // Setup request failed – allow retrying using the same payment method
                getActivity().runOnUiThread(() -> {
                    Activity activity = weakActivity.get();
                    if (activity != null) {
                        /*AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setMessage(e.toString());
                        builder.setPositiveButton("Ok", null);
                        AlertDialog dialog = builder.create();
                        dialog.show();*/
                        /*Toast.makeText(activity, "Payment Failed", Toast.LENGTH_SHORT).show();*/
                        Snackbar.make(v,"Payment Failed",Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }





    /*private void saveCard() {

        Card card = card_input_widget.getCard();
        if(card == null){
            Toast.makeText(getActivity(),"Invalid card",Toast.LENGTH_SHORT).show();
        }else {
            if (!card.validateCard()) {
                // Do not continue token creation.
                Toast.makeText(getActivity(), "Invalid card", Toast.LENGTH_SHORT).show();
            } else {
                 stripeCardPost(card);
                *//*Toast.makeText(getActivity(),stripeCardID, Toast.LENGTH_SHORT).show();*//*

            }
        }
    }
    private void stripeCardPost(Card card) {
        progressDialog.show();
        Stripe stripe = new Stripe(getActivity(), "pk_test_2gr4dBnIiTzKsnOzTTbQQ9lv");
        stripe.createToken(
                card,
                new TokenCallback() {
                    public void onSuccess(Token token) {

                        // Send token to your server
                       *//* Log.e("Stripe Token", token.getId());
                        Intent intent = new Intent();
                        intent.putExtra("card",token.getCard().getLast4());
                        intent.putExtra("stripe_token",token.getId());
                        intent.putExtra("cardtype",token.getCard().getBrand());*//*
                        strip_Token = token.getId();
                        cardBrandName = token.getCard().getBrand();
                        stripe_token = token.getCard().getId();
                        paymentMethodButton.setText("Your "+cardBrandName+" card has been added");
                        dialog.dismiss();
                        progressDialog.dismiss();

                        Toast.makeText(getActivity(), "Token: "+strip_Token+"\n"+"card: "+ stripe_token, Toast.LENGTH_LONG).show();
                    *//*    Toast.makeText(getActivity(), stripe_token, Toast.LENGTH_LONG).show();*//*


                    }
                    public void onError(Exception error) {
                        progressDialog.dismiss();
                        // Show localized error message
                        Toast.makeText(getActivity(),
                                error.getLocalizedMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }*/
}