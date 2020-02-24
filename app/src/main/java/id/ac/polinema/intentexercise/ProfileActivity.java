package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    private TextView fullnameText;
    private TextView emailText;
    private TextView homepageText;
    private TextView aboutText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        aboutText=findViewById(R.id.label_about);
        fullnameText=findViewById(R.id.label_fullname);
        emailText=findViewById(R.id.label_email);
        homepageText=findViewById(R.id.label_homepage);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String fullname=extras.getString(RegisterActivity.FULLNAME_KEY);
            String email=extras.getString(RegisterActivity.EMAIL_KEY);
            String homepage=extras.getString(RegisterActivity.HOMEPAGE_KEY);
            String about=extras.getString(RegisterActivity.ABOUT_KEY);
            fullnameText.setText(fullname);
            emailText.setText(email);
            homepageText.setText(homepage);
            aboutText.setText(about);
        }
    }
}
