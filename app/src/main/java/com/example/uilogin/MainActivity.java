package com.example.uilogin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";
    private EditText nameEditText, emailEditText, passwordEditText;
    private Button pickProfile, register;
    private TextView textWarnName, textWarnPassword, textWarnEmail, textPassRegister;
    private Spinner countriesSpinner;
    private RadioGroup rgGender;
    private CheckBox agreementCheckBox;
    private RelativeLayout parent;
    ImageView imageView;
    Uri imageUri;
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image_profile);
        pickProfile = findViewById(R.id.pick_image);
        pickProfile.setOnClickListener(view -> {
            openGallery();
        });
        initViews();

        register.setOnClickListener(view -> initRegister());
    }
    /**
     * Snackbar after end from register appear when insert the data correctly
     */
    private void initRegister() {
        if (validateData()) {
            if (agreementCheckBox.isChecked()) {
                showSnackBar();
            } else {
                Toast.makeText(this, "You Need to agree th licence agreement", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showSnackBar() {
        textWarnName.setVisibility(View.GONE);
        textWarnEmail.setVisibility(View.GONE);
        textWarnPassword.setVisibility(View.GONE);
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String country = countriesSpinner.getSelectedItem().toString();
        String gender = "";
        switch (rgGender.getCheckedRadioButtonId()) {
            case R.id.gender_male:
                gender = "Male";
                break;
            case R.id.gender_female:
                gender = "Female";
                break;
            case R.id.gender_other:
                gender = "Other";
                break;
            default:
                gender = "UNKNOWN";
                break;
        }
        String snackText = "Name: " + name + '\n' +
                "Email: " + email + '\n' +
                "Gender: " + gender + '\n' +
                "Country: " + country;
        Snackbar.make(parent, snackText, Snackbar.LENGTH_INDEFINITE).setAction("Dismiss", view -> {
            nameEditText.setText("");
            emailEditText.setText("");
            passwordEditText.setText("");
        }).show();
    }
    /**
     * To check the data that had been inserted is validate data
     */
    private boolean validateData() {
        if (nameEditText.getText().toString().equals("")) {
            textWarnName.setVisibility(View.VISIBLE);
            textWarnName.setText("Enter your Name");
            return false;
        }
        if (emailEditText.getText().toString().equals("")) {
            textWarnEmail.setVisibility(View.VISIBLE);
            textWarnEmail.setText("Enter your Email");
            return false;
        }
        if (passwordEditText.getText().toString().equals("")) {
            textWarnPassword.setVisibility(View.VISIBLE);
            textWarnPassword.setText("Enter your Password");
            return false;
        }
        return true;
    }
    /**
     *initViews Method to Initial all the ID's
     */
    private void initViews() {
        nameEditText = findViewById(R.id.edit_text_name);
        emailEditText = findViewById(R.id.edit_text_email);
        passwordEditText = findViewById(R.id.edit_text_password);
        pickProfile = findViewById(R.id.pick_image);
        register = findViewById(R.id.button_register);
        textWarnName = findViewById(R.id.warn_name);
        textWarnEmail = findViewById(R.id.warn_email);
        textWarnPassword = findViewById(R.id.warn_password);
        countriesSpinner = findViewById(R.id.spinner_country);
        rgGender = findViewById(R.id.rgGender);
        agreementCheckBox = findViewById(R.id.agreement);
        parent = findViewById(R.id.parent_layout);
    }
    /**
     * Method to open the Gallery
     */
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode ==PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }

    }
}
