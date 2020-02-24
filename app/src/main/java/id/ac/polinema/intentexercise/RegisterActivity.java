package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

public class RegisterActivity extends AppCompatActivity implements Validator.ValidationListener {
    public static final String FULLNAME_KEY="fullname";
    public static final String EMAIL_KEY="email";
    public static final String PASSWORD_KEY="password";
    public static final String CONFIRMPASSWORD_KEY="confirmpassword";
    public static final String HOMEPAGE_KEY="homepage";
    public static final String ABOUT_KEY="about";

    @NotEmpty
    private EditText fullnameInput;

    @NotEmpty
    @Email
    private EditText emailInput;

    @NotEmpty
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)
    private EditText passwordInput;

    @NotEmpty
    @ConfirmPassword
    private EditText confirmpasswordInput;

    @NotEmpty
    private EditText homepageInput;

    @NotEmpty
    private EditText aboutInput;

    private Validator validator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        validator = new Validator(this);
        validator.setValidationListener(this);
        fullnameInput=findViewById(R.id.text_fullname);
        emailInput=findViewById(R.id.text_email);
        passwordInput=findViewById(R.id.text_password);
        confirmpasswordInput=findViewById(R.id.text_confirm_password);
        homepageInput=findViewById(R.id.text_homepage);
        aboutInput=findViewById(R.id.text_about);
    }

    public void handleProfile(View view) {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        String fullname=fullnameInput.getText().toString();
        String email=emailInput.getText().toString();
        String password=passwordInput.getText().toString();
        String confirmpassword=confirmpasswordInput.getText().toString();
        String homepage=homepageInput.getText().toString();
        String about=aboutInput.getText().toString();
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(FULLNAME_KEY, fullname);
        intent.putExtra(EMAIL_KEY, email);
        intent.putExtra(PASSWORD_KEY, password);
        intent.putExtra(CONFIRMPASSWORD_KEY, confirmpassword);
        intent.putExtra(HOMEPAGE_KEY, homepage);
        intent.putExtra(ABOUT_KEY, about);
        startActivity(intent);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
