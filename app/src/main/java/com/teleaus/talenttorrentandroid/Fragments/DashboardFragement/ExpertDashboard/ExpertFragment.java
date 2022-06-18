package com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ExpertDashboard;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.teleaus.talenttorrentandroid.Api.ButtonPressCloseWindow;
import com.teleaus.talenttorrentandroid.Api.Client;
import com.teleaus.talenttorrentandroid.Api.Service;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ProfileInfoFragment;
import com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.RealPathUtil;
import com.teleaus.talenttorrentandroid.Model.Adapter.EducationsAdapter.EducationAdapter;
import com.teleaus.talenttorrentandroid.Model.Adapter.EmploymentsAdapter.EmployementAdapter;
import com.teleaus.talenttorrentandroid.Model.EducationModel.EducationData;
import com.teleaus.talenttorrentandroid.Model.EducationModel.EducationsModel;
import com.teleaus.talenttorrentandroid.Model.EmployementModel.EmployementData;
import com.teleaus.talenttorrentandroid.Model.EmployementModel.EmployementModel;
import com.teleaus.talenttorrentandroid.Model.Profile.ProfileMainModel;
import com.teleaus.talenttorrentandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExpertFragment extends Fragment implements ButtonPressCloseWindow {

    View v;
    public static ButtonPressCloseWindow backlistener;
    Button profileInfo;
    Button updateUserNameButton, editButton, updatePersonalInfoButton, updateWorkExperienceButton ,educationButton,
            employeeHistoryButton, changePassButton;

    EditText usernameEditText,firstNameEditText, lastNameEditText, emailEditText, phoneEditText,locationEditText
            ,businessNumEditText,sklillEditText,coverLetterEditText;
    Spinner countrySpinner,qualificationSpinner,experienceSpinner;
    ImageButton addButton;
    ChipGroup chipGroup;
    TextView firstNameTxtView, lastNameTxtView,typeName, locationName, countryNameShow;
    RecyclerView educationRecyclerView,employmentRecyclerView;
    int i;
    String token, email, type, password,profilePicture,coverPicture,userName,firstName,lastName,emailData,phone,
            location,countryName, qualification,experience,businessNumber, coverLetter;


    String URL = "https://talenttorrent.com/api/v1/countries";
    ArrayList<String> CategoryName;
    ArrayList<String> ShortName;
    ArrayAdapter<String> adapter;
    String shortNamePost,postQualificationName,postExperienceName,skillPost;

    ImageButton expandButton;
    LinearLayout expandLayout,hiddenLayout,connections, appliedJob, activeJob, draftedJob, awardedJob, ongoingJob, submittedJob,completedJob;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ArrayList<String> arr=new ArrayList<>();
    List<String> arraySkillShow;

    FloatingActionButton fabProfile;
    FloatingActionButton fabCover;
    ImageView coverPic;
    CircleImageView profile_pic;
    private String cameraPermission[];
    private  static final int COVER_CAMERA_REQUEST_CODE = 200;
    private  static final int AVATAR_CAMERA_REQUEST_CODE = 201;
    ////Camera View
    LinearLayout cameraButton, galleryButton;
    Button cancelButton;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    public final String APP_TAG = "crop";
    public String intermediateName = "1.jpg";
    Uri intermediateProvider;
    Uri resultProvider;
    ActivityResultLauncher<Intent> cameraActivityResultLauncher;
    ActivityResultLauncher<Intent> galleryActivityResultLauncher;
    ActivityResultLauncher<Intent> cropActivityResultLauncher;

    ActivityResultLauncher<Intent> cameraProfileActivityResultLauncher;
    ActivityResultLauncher<Intent> galleryProfileActivityResultLauncher;
    ActivityResultLauncher<Intent> cropProfileActivityResultLauncher;

    Bitmap cropImage;
    public String resultName = "2.jpg";

    @Override
    public void onAttach(@NonNull Context context) {
        sharedPreferences = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expert, container, false);
        v= getActivity().findViewById(android.R.id.content);
        defineViews(view);

        token = sharedPreferences.getString("token",null);
        String email= sharedPreferences.getString("email",null);
        String password= sharedPreferences.getString("password",null);
        String types= sharedPreferences.getString("type",null);
        String firstNames= sharedPreferences.getString("firstName",null);
        String lastNames= sharedPreferences.getString("lastName",null);
        String profilePhotos= sharedPreferences.getString("profilePhoto",null);

        fieldList(URL);
        educationDataLoad();
        employementDataLoad();
        profileRequest();


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///Enable EditText
                usernameEditText.setFocusableInTouchMode(true);
                usernameEditText.setTextColor(Color.parseColor("#FF000000"));
                updateUserNameButton.setVisibility(View.VISIBLE);
            }
        });
        updateUserNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameChange();
            }
        });
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String countryName = countrySpinner.getItemAtPosition(countrySpinner.getSelectedItemPosition()).toString();

                shortNamePost = ShortName.get(i);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /////qualificationSpinner
        qualificationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                /*QualificationModel qualificationModel =(QualificationModel) adapterView.getSelectedItem();
                postQualificationName = qualificationModel.getPostQualification();*/

                postQualificationName = qualificationSpinner.getItemAtPosition(qualificationSpinner.getSelectedItemPosition()).toString();
               /* Toast.makeText(getActivity(), postQualificationName, Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ////ExperienceSpinner
        experienceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                /*ExperienceModel experienceModel = (ExperienceModel) adapterView.getSelectedItem();*/

                postExperienceName = experienceSpinner.getItemAtPosition(experienceSpinner.getSelectedItemPosition()).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        expandLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread() {
                    public void run() {
                            try {
                               getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (hiddenLayout.getVisibility() == View.VISIBLE) {

                                            TransitionManager.beginDelayedTransition(hiddenLayout,
                                                    new AutoTransition());
                                            hiddenLayout.setVisibility(View.GONE);
                                            expandButton.setImageResource(R.drawable.ic_baseline_expand_less_24);
                                        }

                                        else {

                                            TransitionManager.beginDelayedTransition(hiddenLayout,
                                                    new AutoTransition());
                                            hiddenLayout.setVisibility(View.VISIBLE);
                                            expandButton.setImageResource(R.drawable.ic_baseline_expand_more_24);
                                        }
                                    }
                                });
                                Thread.sleep(4000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                    }
                }.start();

            }
        });
        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    public void run() {
                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (hiddenLayout.getVisibility() == View.VISIBLE) {

                                        TransitionManager.beginDelayedTransition(hiddenLayout,
                                                new AutoTransition());
                                        hiddenLayout.setVisibility(View.GONE);
                                        expandButton.setImageResource(R.drawable.ic_baseline_expand_less_24);
                                    }

                                    else {

                                        TransitionManager.beginDelayedTransition(hiddenLayout,
                                                new AutoTransition());
                                        hiddenLayout.setVisibility(View.VISIBLE);
                                        expandButton.setImageResource(R.drawable.ic_baseline_expand_more_24);
                                    }
                                }
                            });
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
            }
        });
        connections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        updatePersonalInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPersonalInfoChange();
            }
        });
        updateWorkExperienceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateWorkExperience();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arr.add(sklillEditText.getText()+"");
                final Chip chip = new Chip(getActivity());

                ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getActivity(),
                        null,
                        0,
                        R.style.Widget_MaterialComponents_Chip_Entry);
                chip.setChipDrawable(chipDrawable);

                chip.setText(sklillEditText.getText()+"");
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chipGroup.removeView(chip);
                        arr.remove(chip.getText());
                        /*Toast.makeText(getActivity(),arr.size()+"",Toast.LENGTH_LONG).show();*/
                    }
                });
                chipGroup.addView(chip);
                sklillEditText.setText("");
            }
        });

        educationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                CreateEducationFragment createEducationFragment = new CreateEducationFragment();
                bundle.putString("token", token);
                bundle.putString("email", email);
                bundle.putString("password", password);
                bundle.putString("type", type);
                createEducationFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, createEducationFragment).addToBackStack(null).commit();
            }
        });


        fabCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected()) {

                    if (!checkCameraPermission()){

                        requestCameraPermission();

                    }else{
                        createPopUpDialog();
                    }

                }else{
                    new iOSDialogBuilder(getActivity())
                            .setTitle("Alert!")
                            .setSubtitle("No Internet Connected from your device")
                            .setBoldPositiveLabel(true)
                            .setCancelable(false)
                            .setPositiveListener("Ok",new iOSDialogClickListener() {
                                @Override
                                public void onClick(iOSDialog dialog) {
                                    dialog.dismiss();

                                }
                            })
                            .build().show();
                }
            }
        });

        fabProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected()) {

                    if (!checkCameraPermission()){
                        requestProfileCameraPermission();
                    }else{
                        createProfilePopUpDialog();
                    }

                }else{
                    new iOSDialogBuilder(getActivity())
                            .setTitle("Alert!")
                            .setSubtitle("No Internet Connected from your device")
                            .setBoldPositiveLabel(true)
                            .setCancelable(false)
                            .setPositiveListener("Ok",new iOSDialogClickListener() {
                                @Override
                                public void onClick(iOSDialog dialog) {
                                    dialog.dismiss();

                                }
                            })
                            .build().show();
                }
            }
        });

        cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Bitmap takenImage = loadFromUri(intermediateProvider);

                            onCropImage();
                        }
                    }
                });

        galleryActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            saveBitmapFileToIntermediate(result.getData().getData());
                            Bitmap selectedImage = loadFromUri(intermediateProvider);
                            onCropImage();
                        }
                    }
                });

        cropActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {

                            cropImage =loadFromUri(resultProvider);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            cropImage.compress(Bitmap.CompressFormat.JPEG,100,bytes);



                            String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),cropImage,"val",null);
                            Uri uri = Uri.parse(path);
                            Context context = getActivity();
                            String realPath = RealPathUtil.getRealPath(context,uri);


                            File file = new File (realPath);

                            File newFile = saveBitmapToFile(file);

                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), newFile);
                            MultipartBody.Part part = MultipartBody.Part.createFormData("cover_photo",newFile.getName(),requestBody);


                            Service service = Client.retrofit.create(Service.class);
                            Call<ProfileMainModel> call = service.getProfileCover("Bearer"+" "+token,part);
                            call.enqueue(new Callback<ProfileMainModel>() {
                                @Override
                                public void onResponse(Call<ProfileMainModel> call, Response<ProfileMainModel> response) {
                                    if (response.isSuccessful()){
                                        ProfileMainModel profileMainModel = response.body();
                                        profilePicture = profileMainModel.getData().getProfile().getAvatar_url();
                                        coverPicture = profileMainModel.getData().getProfile().getCover_photo_url();
                                        userName = profileMainModel.getData().getUsername();
                                        firstName = profileMainModel.getData().getFirst_name();
                                        lastName = profileMainModel.getData().getLast_name();
                                        type = profileMainModel.getData().getType();
                                        emailData = profileMainModel.getData().getEmail();
                                        phone = profileMainModel.getData().getProfile().getPhone();
                                        location = profileMainModel.getData().getProfile().getLocation();
                                        countryName = profileMainModel.getData().getProfile().getCountry();
                                        qualification = profileMainModel.getData().getProfile().getQualification();
                                        experience = profileMainModel.getData().getProfile().getExperience();
                                        businessNumber = profileMainModel.getData().getProfile().getBusiness_number();
                                        ArrayList<String> skills = profileMainModel.getData().getProfile().getSkills();
                                        coverLetter = profileMainModel.getData().getProfile().getCover_letter();

                                        showData();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ProfileMainModel> call, Throwable t) {
                                    Snackbar.make(v,"Network Connection is Error",Snackbar.LENGTH_SHORT).show();
                                }
                            });



                        }
                    }
                });


        cameraProfileActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Bitmap takenImage = loadFromUri(intermediateProvider);

                            onProfileCropImage();
                        }
                    }
                });

        galleryProfileActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            saveBitmapFileToIntermediate(result.getData().getData());
                            Bitmap selectedImage = loadFromUri(intermediateProvider);
                            onProfileCropImage();
                        }
                    }
                });

        cropProfileActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {

                            cropImage =loadFromUri(resultProvider);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            cropImage.compress(Bitmap.CompressFormat.JPEG,100,bytes);

                            Toast.makeText(getContext(), "Profile Picture", Toast.LENGTH_SHORT).show();
                            String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),cropImage,"val",null);
                            Uri uri = Uri.parse(path);
                            Context context = getActivity();
                            String realPath = RealPathUtil.getRealPath(context,uri);

                            File file = new File (realPath);

                            File newFile = saveBitmapToFile(file);

                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), newFile);
                            MultipartBody.Part part = MultipartBody.Part.createFormData("avatar",newFile.getName(),requestBody);


                            Service service = Client.retrofit.create(Service.class);
                            Call<ProfileMainModel> call = service.getProfileAvatar("Bearer"+" "+token,part);
                            call.enqueue(new Callback<ProfileMainModel>() {
                                @Override
                                public void onResponse(Call<ProfileMainModel> call, Response<ProfileMainModel> response) {
                                    if (response.isSuccessful()){
                                        ProfileMainModel profileMainModel = response.body();
                                        profilePicture = profileMainModel.getData().getProfile().getAvatar_url();
                                        coverPicture = profileMainModel.getData().getProfile().getCover_photo_url();
                                        userName = profileMainModel.getData().getUsername();
                                        firstName = profileMainModel.getData().getFirst_name();
                                        lastName = profileMainModel.getData().getLast_name();
                                        type = profileMainModel.getData().getType();
                                        emailData = profileMainModel.getData().getEmail();
                                        phone = profileMainModel.getData().getProfile().getPhone();
                                        location = profileMainModel.getData().getProfile().getLocation();
                                        countryName = profileMainModel.getData().getProfile().getCountry();
                                        qualification = profileMainModel.getData().getProfile().getQualification();
                                        experience = profileMainModel.getData().getProfile().getExperience();
                                        businessNumber = profileMainModel.getData().getProfile().getBusiness_number();
                                        ArrayList<String> skills = profileMainModel.getData().getProfile().getSkills();
                                        coverLetter = profileMainModel.getData().getProfile().getCover_letter();
                                        showData();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ProfileMainModel> call, Throwable t) {
                                    Snackbar.make(v,"Network Connection is Error",Snackbar.LENGTH_SHORT).show();
                                }
                            });



                        }
                    }
                });

        profileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("userName",userName);
                ProfileInfoFragment profileInfoFragment = new ProfileInfoFragment();
                profileInfoFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, profileInfoFragment).commit();
            }
        });

        return view;

    }
    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else
            return false;

    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(getActivity(),cameraPermission,COVER_CAMERA_REQUEST_CODE);

    }
    private void requestProfileCameraPermission() {
        ActivityCompat.requestPermissions(getActivity(),cameraPermission,AVATAR_CAMERA_REQUEST_CODE);

    }

    private void createPopUpDialog() {
        dialogBuilder = new AlertDialog.Builder(getActivity());

        final View popUp_card = getLayoutInflater().inflate(R.layout.camera_choice_view,null);

        cameraButton = popUp_card.findViewById(R.id.cameraButton);
        galleryButton = popUp_card.findViewById(R.id.galleryButton);
        cancelButton = popUp_card.findViewById(R.id.cancelButton);

        dialogBuilder.setView(popUp_card);
        dialogBuilder.setCancelable(false);
        dialog = dialogBuilder.create();
        dialog.show();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onLaunchCamera();
                dialog.dismiss();
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onPickPhoto();
                dialog.dismiss();
            }
        });
    }


    private void createProfilePopUpDialog() {
        dialogBuilder = new AlertDialog.Builder(getActivity());

        final View popUp_card = getLayoutInflater().inflate(R.layout.camera_choice_view,null);

        cameraButton = popUp_card.findViewById(R.id.cameraButton);
        galleryButton = popUp_card.findViewById(R.id.galleryButton);
        cancelButton = popUp_card.findViewById(R.id.cancelButton);

        dialogBuilder.setView(popUp_card);
        dialogBuilder.setCancelable(false);
        dialog = dialogBuilder.create();
        dialog.show();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onLaunchProfileCamera();
                dialog.dismiss();
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onProfilePickPhoto();
                dialog.dismiss();
            }
        });

    }
    /// camera capture
    public void onLaunchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = getPhotoFileUri(intermediateName);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            intermediateProvider = FileProvider.getUriForFile(getActivity(), "com.teleaus.talenttorrentandroid.fileprovider", photoFile);

        else
            intermediateProvider = Uri.fromFile(photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, intermediateProvider);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            cameraActivityResultLauncher.launch(intent);
        }
    }

    // Trigger gallery selection for a photo
    public void onPickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            galleryActivityResultLauncher.launch(intent);
        }
    }
    private void onCropImage() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getActivity().grantUriPermission("com.android.camera", intermediateProvider, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(intermediateProvider, "image/*");

            List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(intent, 0);

            int size = 0;

            if(list != null) {
                getActivity(). grantUriPermission(list.get(0).activityInfo.packageName, intermediateProvider, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                size = list.size();
            }

            if (size == 0) {
                Toast.makeText(getActivity(), "Error, wasn't taken image!", Toast.LENGTH_SHORT).show();
            } else {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.putExtra("crop", "true");

                intent.putExtra("scale", true);

                File photoFile = getPhotoFileUri(resultName);
                // wrap File object into a content provider
                // required for API >= 24
                // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
                resultProvider = FileProvider.getUriForFile(getActivity(), "com.teleaus.talenttorrentandroid.fileprovider", photoFile);

                intent.putExtra("return-data", false);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, resultProvider);
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

                Intent cropIntent = new Intent(intent);
                ResolveInfo res = list.get(0);
                cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                getActivity().grantUriPermission(res.activityInfo.packageName, resultProvider, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                cropIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                /* Toast.makeText(this, "1st Crop", Toast.LENGTH_SHORT).show();*/
                cropActivityResultLauncher.launch(cropIntent);

            }
        } else {
            File photoFile = getPhotoFileUri(resultName);
            resultProvider = Uri.fromFile(photoFile);

            Intent intentCrop = new Intent("com.android.camera.action.CROP");
            intentCrop.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intentCrop.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intentCrop.setDataAndType(intermediateProvider, "image/*");
            intentCrop.putExtra("crop", "true");
            intentCrop.putExtra("scale", true);
            intentCrop.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intentCrop.putExtra("noFaceDetection", true);
            intentCrop.putExtra("return-data", false);
            intentCrop.putExtra(MediaStore.EXTRA_OUTPUT, resultProvider);
            cropActivityResultLauncher.launch(intentCrop);

        }
    }
    // Returns the File for a photo stored on disk given the fileName


    public void onLaunchProfileCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = getPhotoFileUri(intermediateName);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            intermediateProvider = FileProvider.getUriForFile(getActivity(), "com.teleaus.talenttorrentandroid.fileprovider", photoFile);

        else
            intermediateProvider = Uri.fromFile(photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, intermediateProvider);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            cameraProfileActivityResultLauncher.launch(intent);
        }
    }

    // Trigger gallery selection for a photo
    public void onProfilePickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            galleryProfileActivityResultLauncher.launch(intent);
        }
    }

    private void onProfileCropImage() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getActivity().grantUriPermission("com.android.camera", intermediateProvider, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(intermediateProvider, "image/*");

            List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(intent, 0);

            int size = 0;

            if(list != null) {
                getActivity(). grantUriPermission(list.get(0).activityInfo.packageName, intermediateProvider, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                size = list.size();
            }

            if (size == 0) {
                Toast.makeText(getActivity(), "Error, wasn't taken image!", Toast.LENGTH_SHORT).show();
            } else {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.putExtra("crop", "true");

                intent.putExtra("scale", true);

                File photoFile = getPhotoFileUri(resultName);
                // wrap File object into a content provider
                // required for API >= 24
                // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
                resultProvider = FileProvider.getUriForFile(getActivity(), "com.teleaus.talenttorrentandroid.fileprovider", photoFile);

                intent.putExtra("return-data", false);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, resultProvider);
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

                Intent cropIntent = new Intent(intent);
                ResolveInfo res = list.get(0);
                cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                getActivity().grantUriPermission(res.activityInfo.packageName, resultProvider, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                cropIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                /* Toast.makeText(this, "1st Crop", Toast.LENGTH_SHORT).show();*/
                cropProfileActivityResultLauncher.launch(cropIntent);

            }
        } else {
            File photoFile = getPhotoFileUri(resultName);
            resultProvider = Uri.fromFile(photoFile);

            Intent intentCrop = new Intent("com.android.camera.action.CROP");
            intentCrop.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intentCrop.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intentCrop.setDataAndType(intermediateProvider, "image/*");
            intentCrop.putExtra("crop", "true");
            intentCrop.putExtra("scale", true);
            intentCrop.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intentCrop.putExtra("noFaceDetection", true);
            intentCrop.putExtra("return-data", false);
            intentCrop.putExtra(MediaStore.EXTRA_OUTPUT, resultProvider);
            cropProfileActivityResultLauncher.launch(intentCrop);

        }
    }

    public Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1){
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    private void saveBitmapFileToIntermediate(Uri sourceUri) {
        try {
            Bitmap bitmap =  loadFromUri(sourceUri);

            File imageFile = getPhotoFileUri(intermediateName);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                intermediateProvider = FileProvider.getUriForFile(getActivity(), "com.teleaus.talenttorrentandroid.fileprovider", imageFile);
            else
                intermediateProvider = Uri.fromFile(imageFile);

            OutputStream out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(getActivity().getExternalFilesDir(""), APP_TAG);
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return file;
    }
    public File saveBitmapToFile(File file){
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE=75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    /////////////////////


    ////////////////////

    private void defineViews(View view) {
        usernameEditText = view.findViewById(R.id.usernameEditText);
        firstNameEditText = view.findViewById(R.id.firstNameEditText);
        lastNameEditText = view.findViewById(R.id.lastNameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        locationEditText = view.findViewById(R.id.locationEditText);
        qualificationSpinner = view.findViewById(R.id.qualificationSpinner);
        experienceSpinner = view.findViewById(R.id.experienceSpinner);
        businessNumEditText = view.findViewById(R.id.businessNumEditText);
        sklillEditText = view.findViewById(R.id.sklillEditText);
        coverLetterEditText = view.findViewById(R.id.coverLetterEditText);
        countryNameShow = view.findViewById(R.id.countryNameShow);
        firstNameTxtView = view.findViewById(R.id.firstNameTxtView);
        lastNameTxtView = view.findViewById(R.id.lastNameTxtView);
        typeName = view.findViewById(R.id.typeName);
        locationName = view.findViewById(R.id.locationName);
        chipGroup = view.findViewById(R.id.chipGroup);

        expandButton = view.findViewById(R.id.expandButton);
        expandLayout = view.findViewById(R.id.expandLayout);
        hiddenLayout = view.findViewById(R.id.hiddenLayout);
        connections = view.findViewById(R.id.connections);
        appliedJob = view.findViewById(R.id.appliedJob);
        activeJob = view.findViewById(R.id.activeJob);
        draftedJob = view.findViewById(R.id.draftedJob);
        awardedJob = view.findViewById(R.id.awardedJob);
        ongoingJob = view.findViewById(R.id.ongoingJob);
        submittedJob = view.findViewById(R.id.submittedJob);
        completedJob = view.findViewById(R.id.completedJob);

        //Spinner
        countrySpinner = (Spinner)view.findViewById(R.id.countrySpinner);

        //Button
        editButton = view.findViewById(R.id.editButton);
        updateUserNameButton = view.findViewById(R.id.updateUserNameButton);
        updatePersonalInfoButton = view.findViewById(R.id.updatePersonalInfoButton);
        updateWorkExperienceButton = view.findViewById(R.id.updateWorkExperienceButton);
        educationButton = view.findViewById(R.id.educationButton);
        addButton = view.findViewById(R.id.addButton);
        employeeHistoryButton = view.findViewById(R.id.employeeHistoryButton);
        changePassButton = view.findViewById(R.id.changePassButton);
        //RecyclerView
        educationRecyclerView = view.findViewById(R.id.educationRecyclerView);
        employmentRecyclerView = view.findViewById(R.id.employmentRecyclerView);

        educationRecyclerView.setHasFixedSize(true);
        educationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        employmentRecyclerView.setHasFixedSize(true);
        employmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        CategoryName =new ArrayList<>();
        ShortName =new ArrayList<>();

        profileInfo = view.findViewById(R.id.profileInfo);


        profile_pic = view.findViewById(R.id.profile_pic);
        coverPic = view.findViewById(R.id.coverPic);
        fabProfile = view.findViewById(R.id.fabProfile);
        fabCover = view.findViewById(R.id.fabCover);
        cameraPermission= new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    private void profileRequest() {
        Service service = Client.retrofit.create(Service.class);
        Call<ProfileMainModel> call = service.getProfileData("Bearer"+" "+token);
        call.enqueue(new Callback<ProfileMainModel>() {
            @Override
            public void onResponse(Call<ProfileMainModel> call, Response<ProfileMainModel> response) {
                if (response.isSuccessful()){
                    ProfileMainModel profileMainModel = response.body();
                    profilePicture = profileMainModel.getData().getProfile().getAvatar_url();
                    coverPicture = profileMainModel.getData().getProfile().getCover_photo_url();
                    userName = profileMainModel.getData().getUsername();
                    firstName = profileMainModel.getData().getFirst_name();
                    lastName = profileMainModel.getData().getLast_name();
                    type = profileMainModel.getData().getType();
                    emailData = profileMainModel.getData().getEmail();
                    phone = profileMainModel.getData().getProfile().getPhone();
                    location = profileMainModel.getData().getProfile().getLocation();
                    countryName = profileMainModel.getData().getProfile().getCountry();
                    qualification = profileMainModel.getData().getProfile().getQualification();
                    experience = profileMainModel.getData().getProfile().getExperience();
                    businessNumber = profileMainModel.getData().getProfile().getBusiness_number();
                    ArrayList<String> skills = profileMainModel.getData().getProfile().getSkills();
                    coverLetter = profileMainModel.getData().getProfile().getCover_letter();
                    coverLetter = profileMainModel.getData().getProfile().getCover_letter();
                    showData();
                }
            }

            @Override
            public void onFailure(Call<ProfileMainModel> call, Throwable t) {
                /*Toast.makeText(getActivity(), "Network Connection is Error", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Network Connection is Error",Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void showData() {
        usernameEditText.setText(userName);
        firstNameEditText.setText(firstName);
        firstNameTxtView.setText(firstName);
        lastNameEditText.setText(lastName);
        lastNameTxtView.setText(lastName);
        typeName.setText(type);
        emailEditText.setText(emailData);
        phoneEditText.setText(phone);
        locationEditText.setText(location);
        locationName.setText(location);

        businessNumEditText.setText(businessNumber);
        coverLetterEditText.setText(coverLetter);
        countryNameShow.setText(countryName);
       /* countrySpinner.setVisibility(View.GONE);*/

        Picasso.get().load(profilePicture).into(profile_pic);
        Picasso.get().load(coverPicture).into(coverPic);

        ///Disable EditText
        usernameEditText.setFocusable(false);
        usernameEditText.setTextColor(Color.parseColor("#868686"));

        emailEditText.setFocusable(false);
        emailEditText.setTextColor(Color.parseColor("#868686"));

        List<String> qualificationModelList = new ArrayList<>();
        qualificationModelList.add("Diploma");
        qualificationModelList.add("Bachelor");
        qualificationModelList.add("Masters");
        qualificationModelList.add("Ph.D");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_textview, qualificationModelList);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        qualificationSpinner.setPrompt("Select Your Type");
        qualificationSpinner.setAdapter(adapter);

        String qualification = sharedPreferences.getString("postQualificationName", "");
        if(!qualification.equalsIgnoreCase(""))
        {
            int spinnerPosition = adapter.getPosition(qualification);

            qualificationSpinner.setSelection(spinnerPosition);

        }


        List<String> experienceModelList = new ArrayList<>();
        experienceModelList.add("0-1 Year");
        experienceModelList.add("1-2 Years");
        experienceModelList.add("3-4 Years");
        experienceModelList.add("5+ Years");

        ArrayAdapter<String> experienceAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_textview, experienceModelList);
        experienceAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        experienceSpinner.setPrompt("Select Your Type");
        experienceSpinner.setAdapter(experienceAdapter);
        String experience = sharedPreferences.getString("postExperienceName", "");
        if(!experience.equalsIgnoreCase(""))
        {
            int spinnerPosition = experienceAdapter.getPosition(experience);

            experienceSpinner.setSelection(spinnerPosition);

        }

        String skill = sharedPreferences.getString("skillPost", "");
        String str[] = skill.split(",");
        arraySkillShow = new ArrayList<String>();
        arraySkillShow = Arrays.asList(str);

        for(String s: arraySkillShow){
       /*     Toast.makeText(getActivity(),"List: "+ s, Toast.LENGTH_SHORT).show();*/
            arr.add(s);
            final Chip chip = new Chip(getActivity());

            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getActivity(),
                    null,
                    0,
                    R.style.Widget_MaterialComponents_Chip_Entry);
            chip.setChipDrawable(chipDrawable);
            chip.setText(s);
            chip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chipGroup.removeView(chip);
                    arr.remove(chip.getText());

                }
            });
            chipGroup.addView(chip);

        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("country",countryName);
        editor.apply();

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

                        String short_name = jsonObject1.getString("short_name");
                        ShortName.add(short_name);

                    }

                    if (getActivity()!=null){
                        adapter = new ArrayAdapter<String>(getActivity(),
                                R.layout.spinner_textview, CategoryName);
                        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
                        countrySpinner.setPrompt("Select Your Category");
                        countrySpinner.setAdapter(adapter);

                        String country = sharedPreferences.getString("country", "");
                        if(!country.equalsIgnoreCase(""))
                        {
                            int spinnerPosition = adapter.getPosition(country);

                            countrySpinner.setSelection(spinnerPosition);

                        }

                    }

                }catch (JSONException e){e.printStackTrace();}
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                /*Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Failed",Snackbar.LENGTH_SHORT).show();
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

    private void educationDataLoad() {
        Service service = Client.retrofit.create(Service.class);

        Call<EducationsModel> call = service.getEducationData("Bearer"+" "+token);
        call.enqueue(new Callback<EducationsModel>() {
            @Override
            public void onResponse(Call<EducationsModel> call, Response<EducationsModel> response) {
                if (response.isSuccessful()){
                    List<EducationData> data = response.body().getData();
                    educationAdapterView(data);
                    /*   Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();*/

                }else{
                    /*Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Error",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EducationsModel> call, Throwable t) {
                Snackbar.make(v,"Failed",Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void educationAdapterView(List<EducationData> list) {
        EducationAdapter educationAdapter = new EducationAdapter(list,getContext());
        educationRecyclerView.setAdapter(educationAdapter);
    }

    private void employementDataLoad() {
        Service service = Client.retrofit.create(Service.class);
        Call<EmployementModel> call = service.getEmployement("Bearer"+" "+token);
        call.enqueue(new Callback<EmployementModel>() {
            @Override
            public void onResponse(Call<EmployementModel> call, Response<EmployementModel> response) {
                if (response.isSuccessful()){
                    List<EmployementData> data = response.body().getData();
                    employementAdapterView(data);
                    /*   Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();*/

                }else{
                    /*Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();*/
                    Snackbar.make(v,"Error",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EmployementModel> call, Throwable t) {

            }
        });

    }

    private void employementAdapterView(List<EmployementData> list) {
        EmployementAdapter employementAdapter = new EmployementAdapter(list,getContext());
        employmentRecyclerView.setAdapter(employementAdapter);
    }

    private void userNameChange() {
        String userName = usernameEditText.getText().toString();
        Service service = Client.retrofit.create(Service.class);
        Call<JSONObject> call = service.userNamePost("Bearer"+" "+token,userName);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.isSuccessful()){
                    new iOSDialogBuilder(getContext())
                            .setTitle("Thank you")
                            .setSubtitle("Your Update is Successful")
                            .setBoldPositiveLabel(true)
                            .setCancelable(false)
                            .setPositiveListener("Ok",new iOSDialogClickListener() {
                                @Override
                                public void onClick(iOSDialog dialog) {
                                    /*HomeFragment homeFragment = new HomeFragment();
                                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).addToBackStack(null).commit();
                                    */

                                    dialog.dismiss();
                                    profileRequest();

                                }
                            })
                            .build().show();
                }
                else{
                    usernameEditText.setError("The username has already been taken.");
                    usernameEditText.requestFocus();
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                /*Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();*/
                Snackbar.make(v,"Failed",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void userPersonalInfoChange() {
        String userFirstName = firstNameEditText.getText().toString();
        String userLastName = lastNameEditText.getText().toString();
        String userPhone = phoneEditText.getText().toString();
        String userLocation = locationEditText.getText().toString();

        Service service = Client.retrofit.create(Service.class);
        Call<JSONObject> call = service.userInfoUpdatePost("Bearer"+" "+token,userFirstName,userLastName,userPhone,shortNamePost,userLocation);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.isSuccessful()){
                    new iOSDialogBuilder(getContext())
                            .setTitle("Thank you")
                            .setSubtitle("Your Update is successfull")
                            .setBoldPositiveLabel(true)
                            .setCancelable(false)
                            .setPositiveListener("Ok",new iOSDialogClickListener() {
                                @Override
                                public void onClick(iOSDialog dialog) {
                                    /*HomeFragment homeFragment = new HomeFragment();
                                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).addToBackStack(null).commit();
                                    */

                                    dialog.dismiss();
                                    profileRequest();

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
                Snackbar.make(v,"Failed",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void updateWorkExperience() {

        ArrayList<String> arrayList = new ArrayList<>();
        skillPost="";
        for(int i=0;i<arr.size();i++){
            skillPost+= arr.get(i)+",";

            arrayList.add(arr.get(i));
        }
        if(arr.size()>0){
            skillPost=skillPost.substring(0,skillPost.length()-1);
        }

        String businessNum = businessNumEditText.getText().toString();

        String coverLetter = coverLetterEditText.getText().toString();


        if (coverLetter.isEmpty()){
            coverLetterEditText.setError("Please Provide your Cover Letter");
            coverLetterEditText.requestFocus();
        }else if(skillPost.isEmpty()){
            sklillEditText.setError("Please Provide Your Skills");
            sklillEditText.requestFocus();
        }else{

            Service service = Client.retrofit.create(Service.class);
            Call<JSONObject> call = service.updateJobExperience("Bearer"+" "+token,postQualificationName,postExperienceName,skillPost,coverLetter);
            call.enqueue(new Callback<JSONObject>() {
                @Override
                public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                    if (response.isSuccessful()){
                        /*Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();*/
                        new iOSDialogBuilder(getContext())
                                .setTitle("Thank you")
                                .setSubtitle("Your Update is Successful")
                                .setBoldPositiveLabel(true)
                                .setCancelable(false)
                                .setPositiveListener("Ok",new iOSDialogClickListener() {
                                    @Override
                                    public void onClick(iOSDialog dialog) {

                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("postQualificationName",postQualificationName);
                                        editor.putString("postExperienceName",postExperienceName);
                                        editor.putString("skillPost",skillPost);


                                        editor.apply();
                                        dialog.dismiss();
                                        /*profileRequest();*/
                                        /*getActivity().recreate();*/

                                        getFragmentManager()
                                                .beginTransaction()
                                                .detach(ExpertFragment.this)
                                                .attach(ExpertFragment.this)
                                                .addToBackStack(null)
                                                .commit();
                                    }
                                })
                                .build().show();
                    }else{
                        Snackbar.make(v,"Error",Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<JSONObject> call, Throwable t) {
                    Snackbar.make(v,"Response Failed",Snackbar.LENGTH_LONG).show();
                }
            });
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
                            getActivity().finish();

                        }
                    })
                    .build().show();

        }
    }


}
