package com.zaaibo.tolet.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.crashlytics.android.Crashlytics;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.zaaibo.tolet.R;
import com.zaaibo.tolet.models.User;
import com.zaaibo.tolet.services.MyNetworkReceiver;
import com.zaaibo.tolet.session.SharedPrefManager;
import com.zaaibo.tolet.utils.ConstantKey;
import com.zaaibo.tolet.utils.PermissionUtility;
import com.zaaibo.tolet.utils.Utility;
import com.zaaibo.tolet.utils.language.LocaleHelper;
import com.zaaibo.tolet.utils.network.Network;
import com.zaaibo.tolet.viewmodels.UserViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.fabric.sdk.android.Fabric;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    private static final int RESULT_LOAD_IMAGE = 91;
    private static final int REQUEST_TAKE_PHOTO = 92;

    //Runtime Permissions
    private String[] PERMISSIONS = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA};
    private PermissionUtility mPermissions;

    private MyNetworkReceiver mNetworkReceiver;
    private ProgressDialog mProgress = null;

    private String currentPhotoPath;

    private String mImageUrl = null;
    private String mPhone = null;
    private String mAuthId = null;
    private String mToken = null;

    private ArrayAdapter adapter;
    private ImageView userImageUrl;
    private EditText userFullName, userPhoneNumber, userOccupation, userEmail, userBirthDate, userAddress;
    private TextInputLayout layoutName, layoutPhone;
    private RadioGroup userGroup;
    private RadioButton userRender, userOwner;
    private Spinner userRelation;

    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_profile);

        //mProgress = new ProgressDialog(this);
        mNetworkReceiver = new MyNetworkReceiver(this);
        mPermissions = new PermissionUtility(this, PERMISSIONS); //Runtime permissions

        //===============================================| findViewById
        userImageUrl = (ImageView) findViewById(R.id.userImageUrl);

        userGroup = (RadioGroup) findViewById(R.id.userGroup);
        userRender = (RadioButton) findViewById(R.id.userRender);
        userOwner = (RadioButton) findViewById(R.id.userOwner);

        userFullName = (EditText) findViewById(R.id.userFullName);
        //userFullName.addTextChangedListener(new MyTextWatcher(userFullName));
        layoutName = (TextInputLayout) findViewById(R.id.layoutUserFullName);

        userRelation = (Spinner) findViewById(R.id.userRelation);
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.relation_array));
        userRelation.setAdapter(adapter);

        userPhoneNumber = (EditText) findViewById(R.id.userPhoneNumber);
        //userPhoneNumber.addTextChangedListener(new MyTextWatcher(userPhoneNumber));
        layoutPhone = (TextInputLayout) findViewById(R.id.layoutUserPhoneNumber);

        userOccupation = (EditText) findViewById(R.id.userOccupation);
        userEmail = (EditText) findViewById(R.id.userEmail);
        userBirthDate = (EditText) findViewById(R.id.userBirthDate);
        userAddress = (EditText) findViewById(R.id.userAddress);
        Button saveButton = (Button) findViewById(R.id.saveButton);

        userImageUrl.setOnClickListener(new ActionHandler());
        userBirthDate.setOnClickListener(new ActionHandler());
        saveButton.setOnClickListener(new ActionHandler());



    }

    //===============================================| Language Change
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    //===============================================| onStart(), onPause(), onResume(), onStop()
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*@Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgress != null) {
            mProgress.dismiss();
            mProgress = null;
        }
    }*/

    //===============================================| Click Events
    private class ActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.userImageUrl:

                    break;
                case R.id.userBirthDate:

                    break;
                case R.id.saveButton:
                    Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    }


    //===============================================| Birthday date-picker
    private void birthDate() {
        DatePicker picker = new DatePicker(this);
        int curYear = picker.getYear();
        int curMonth = picker.getMonth() + 1;
        int curDayOfMonth = picker.getDayOfMonth();
        DatePickerDialog pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                userBirthDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, curYear, curMonth, curDayOfMonth);
        pickerDialog.show();
    }

}
