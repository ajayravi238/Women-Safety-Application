package com.example.womensafetyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = this;
    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;
    private DatabaseHelper databaseHelper;
    private InputValidation inputValidation;
    private NestedScrollView nestedScrollView;
    private TextInputEditText textInputEditTextConfirmPassword;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextPassword;
    private TextInputLayout textInputLayoutConfirmPassword;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutPassword;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {
        this.nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        this.textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        this.textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        this.textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        this.textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);
        this.textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        this.textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        this.textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        this.textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.textInputEditTextConfirmPassword);
        this.appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);
        this.appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);
    }

    private void initListeners() {
        this.appCompatButtonRegister.setOnClickListener(this);
        this.appCompatTextViewLoginLink.setOnClickListener(this);
    }

    private void initObjects() {
        this.inputValidation = new InputValidation(this.activity);
        this.databaseHelper = new DatabaseHelper(this.activity);
        this.user = new User();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonRegister /*2131230761*/:
                postDataToSQLite();
                return;
            case R.id.appCompatTextViewLoginLink /*2131230762*/:
                finish();
                return;
            default:
                return;
        }
    }

    private void postDataToSQLite() {
        if (textInputEditTextName.equals("") || textInputEditTextEmail.equals("") || textInputEditTextPassword.equals("")) {
            Toast.makeText(this, "Please Fill All the Fields", Toast.LENGTH_LONG).show();
        } else {
            if(textInputEditTextEmail.getText().toString().trim().length() == 10) {
                if (!this.databaseHelper.checkUser(this.textInputEditTextEmail.getText().toString().trim())) {
                    this.user.setName(this.textInputEditTextName.getText().toString().trim());
                    this.user.setEmail(this.textInputEditTextEmail.getText().toString().trim());
                    this.user.setPassword(this.textInputEditTextPassword.getText().toString().trim());
                    this.databaseHelper.addUser(this.user);
                    //Snackbar.make((View) this.nestedScrollView, (CharSequence) getString(R.string.success_message), 0).show();
                    Toast.makeText(this, "Register Sucessfull", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, MobileNoAddActivity.class);
                    emptyInputEditText();
                    startActivity(intent);
                    return;
                }
                //Snackbar.make((View) this.nestedScrollView, (CharSequence) getString(R.string.error_email_exists), 0).show();
                Toast.makeText(this, "Mobile No Already Exists", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this, "Please enter valid mobile no", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void emptyInputEditText() {
        this.textInputEditTextName.setText(null);
        this.textInputEditTextEmail.setText(null);
        this.textInputEditTextPassword.setText(null);
        this.textInputEditTextConfirmPassword.setText(null);
    }
}
