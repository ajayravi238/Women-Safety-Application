package com.example.womensafetyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton appCompatButtonLogin;
    private AppCompatButton appCompatButtonRegister;
    private DatabaseHelper databaseHelper;
    private InputValidation inputValidation;
    private NestedScrollView nestedScrollView;
    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextPassword;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutPassword;
    private AppCompatTextView textViewLinkForgetPassword;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initListeners();
        initObjects();

    }

    private void initViews() {
        this.nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        this.textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        this.textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        this.textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        this.textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        this.appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);
        this.appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);
    }

    private void initListeners() {
        this.appCompatButtonLogin.setOnClickListener(this);
        this.appCompatButtonRegister.setOnClickListener(this);
    }

    private void initObjects() {
        this.databaseHelper = new DatabaseHelper(this);
        this.inputValidation = new InputValidation(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin :
                verifyFromSQLite();
                return;
            case R.id.appCompatButtonRegister :
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                return;
            default:
                return;
        }
    }

    private void verifyFromSQLite() {
        if (this.databaseHelper.checkUser(this.textInputEditTextName.getText().toString().trim(), this.textInputEditTextPassword.getText().toString().trim())) {

            String mobileno = "";

            Cursor cursor = databaseHelper.getdata("SELECT * FROM user WHERE user_name='"+ this.textInputEditTextName.getText().toString() +"'");
            while (cursor.moveToNext())
            {
                mobileno = cursor.getString(2);
            }

            Intent accountsIntent = new Intent(this, MapsActivity.class);
            accountsIntent.putExtra("Name", this.textInputEditTextName.getText().toString().trim());
            accountsIntent.putExtra("mobile", mobileno);
            emptyInputEditText();
            startActivity(accountsIntent);
        } else {
            Snackbar.make((View) this.nestedScrollView, (CharSequence) getString(R.string.error_valid_email_password), 0).show();
        }
    }

    private void emptyInputEditText() {
        this.textInputEditTextName.setText(null);
        this.textInputEditTextPassword.setText(null);
    }
}
