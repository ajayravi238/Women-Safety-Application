package com.example.womensafetyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MobileNoAddActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText MobileNo1EditText;
    private EditText MobileNo2EditText;
    private EditText MobileNo3EditText;
    private EditText MobileNo4EditText;
    private EditText MobileNo5EditText;
    private Button Register;
    private DatabaseHelper databaseHelper;
    private InputValidation inputValidation;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_no_add);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {
        this.MobileNo1EditText = (EditText) findViewById(R.id.MobileNo1EditText);
        this.MobileNo2EditText = (EditText) findViewById(R.id.MobileNo2EditText);
        this.MobileNo3EditText = (EditText) findViewById(R.id.MobileNo3EditText);
        this.MobileNo4EditText = (EditText) findViewById(R.id.MobileNo4EditText);
        this.MobileNo5EditText = (EditText) findViewById(R.id.MobileNo5EditText);
        this.Register = (Button) findViewById(R.id.Register);
    }

    private void initListeners() {
        this.Register.setOnClickListener(this);
    }

    private void initObjects() {
        this.inputValidation = new InputValidation(this);
        this.databaseHelper = new DatabaseHelper(this);
        this.user = new User();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Register /*2131230730*/:
                postDataToSQLite();
                return;
            default:
                return;
        }
    }

    private void postDataToSQLite() {
        /*if (this.databaseHelper.getAllMobileNo().getCount() != 0) {
            Toast.makeText(this, "Already Mobile number addeded", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);
            emptyInputEditText();
            startActivity(intent);
        } else
        {*/
            if (this.MobileNo1EditText.getText().toString().equals("")
                    || this.MobileNo2EditText.getText().toString().equals("")
                    || this.MobileNo3EditText.getText().toString().equals("")
                    || this.MobileNo4EditText.getText().toString().equals("")
                    || this.MobileNo5EditText.getText().toString().equals("")) {
                Toast.makeText(this, "Please Fill All the Fields", Toast.LENGTH_LONG).show();
            }
            else if(MobileNo1EditText.getText().toString().trim().length() == 10 &&
                    MobileNo2EditText.getText().toString().trim().length() == 10 &&
                    MobileNo3EditText.getText().toString().trim().length() == 10 &&
                    MobileNo4EditText.getText().toString().trim().length() == 10 &&
                    MobileNo5EditText.getText().toString().trim().length() == 10){
                this.user.setMobile_1(this.MobileNo1EditText.getText().toString().trim());
                this.user.setMobile_2(this.MobileNo2EditText.getText().toString().trim());
                this.user.setMobile_3(this.MobileNo3EditText.getText().toString().trim());
                this.user.setMobile_4(this.MobileNo4EditText.getText().toString().trim());
                this.user.setMobile_5(this.MobileNo5EditText.getText().toString().trim());
                this.databaseHelper.addMobile(this.user);
                Toast.makeText(this, "Register Sucess", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(this, LoginActivity.class);
                emptyInputEditText();
                startActivity(intent2);
            } else {
                Toast.makeText(this, "Please Fill All the Fields", Toast.LENGTH_LONG).show();
            }
        }
  //  }

    private void emptyInputEditText() {
        this.MobileNo1EditText.setText(null);
        this.MobileNo2EditText.setText(null);
        this.MobileNo3EditText.setText(null);
        this.MobileNo4EditText.setText(null);
        this.MobileNo5EditText.setText(null);
    }
}
