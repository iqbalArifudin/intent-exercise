package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {
    private TextView fullnameText;
    private TextView emailText;
    private TextView homepageText;
    private TextView aboutText;
    private ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        aboutText=findViewById(R.id.label_about);
        fullnameText=findViewById(R.id.label_fullname);
        emailText=findViewById(R.id.label_email);
        homepageText=findViewById(R.id.label_homepage);
        avatar=findViewById(R.id.image_profile);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String fullname=extras.getString(RegisterActivity.FULLNAME_KEY);
            String email=extras.getString(RegisterActivity.EMAIL_KEY);
            String homepage=extras.getString(RegisterActivity.HOMEPAGE_KEY);
            String about=extras.getString(RegisterActivity.ABOUT_KEY);
            String avatarURL=extras.getString(RegisterActivity.AVATAR_KEY);
            fullnameText.setText(fullname);
            emailText.setText(email);
            homepageText.setText(homepage);
            aboutText.setText(about);
            try {
                Bitmap homebitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(avatarURL));
                avatar.setImageBitmap(homebitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
