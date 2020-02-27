package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.io.IOException;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements Validator.ValidationListener {
    public static final String FULLNAME_KEY="fullname";
    public static final String EMAIL_KEY="email";
    public static final String PASSWORD_KEY="password";
    public static final String CONFIRMPASSWORD_KEY="confirmpassword";
    public static final String HOMEPAGE_KEY="homepage";
    public static final String ABOUT_KEY="about";
    public static final String AVATAR_KEY="avatarURL";
    public static final int IMAGE_REQUEST_CODE=1;

    private ImageView avatarImage;
    private String avatarURL;

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
        avatarImage = findViewById(R.id.image_profile);
    }

    public void handleProfile(View view) { validator.validate();
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
        intent.putExtra(AVATAR_KEY, avatarURL);
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

    public void handleImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == IMAGE_REQUEST_CODE) {
            if (data != null) {
                try {
                    avatarURL = data.getDataString();
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    avatarImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
