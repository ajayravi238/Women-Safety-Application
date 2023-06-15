package com.example.womensafetyapplication;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class InputValidation {

    private Context context;

    public InputValidation(Context context2) {
        this.context = context2;
    }

    public boolean isInputEditTextFilled(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        if (textInputEditText.getText().toString().trim().isEmpty()) {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        }
        textInputLayout.setErrorEnabled(false);
        return true;
    }

    public boolean isInputEditTextMatches(TextInputEditText textInputEditText1, TextInputEditText textInputEditText2, TextInputLayout textInputLayout, String message) {
        if (!textInputEditText1.getText().toString().trim().contentEquals(textInputEditText2.getText().toString().trim())) {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText2);
            return false;
        }
        textInputLayout.setErrorEnabled(false);
        return true;
    }

    private void hideKeyboardFrom(View view) {
        ((InputMethodManager) this.context.getSystemService("INPUT_METHOD")).hideSoftInputFromWindow(view.getWindowToken(), 3);
    }
}
