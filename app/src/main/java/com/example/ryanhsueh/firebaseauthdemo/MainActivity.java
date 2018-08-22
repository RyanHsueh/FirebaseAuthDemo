package com.example.ryanhsueh.firebaseauthdemo;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

    }

    public void onSignOut(View view) {
        mAuth.signOut();

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void getUserProfile(View view) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("display name:");
            sb.append(user.getDisplayName());
            sb.append("\n");
            sb.append("email:");
            sb.append(user.getEmail());
            sb.append("\n");
            sb.append("photo url:");
            sb.append(user.getPhotoUrl());
            sb.append("\n");
            sb.append("is email verified:");
            sb.append(user.isEmailVerified());
            sb.append("\n");
            sb.append("uid:");
            sb.append(user.getUid());

            TextView textUserInfo = findViewById(R.id.text_user_info);
            textUserInfo.setText(sb.toString());
        }
    }

    public void sendVerifyEmail(View view) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "驗證Email已寄出", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "驗證Email寄送失敗", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void updateUserInfo(View view) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName("Ryan Hsueh")
                    .setPhotoUri(Uri.parse("https://avatars1.githubusercontent.com/u/10694648?s=460&v=4"))
                    .build();

            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "用戶資訊更新成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "用戶資訊更新失敗", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
