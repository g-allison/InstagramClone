package com.codepath.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.instagramclone.databinding.ActivitySignUpBinding;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import static android.content.ContentValues.TAG;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPass;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignUpBinding binding = ActivitySignUpBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        etUsername = binding.etUsername;
        etPassword = binding.etPassword;
        etConfirmPass = binding.etConfirmPass;
        btnSignUp = binding.btnSignUp;
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String passwordConfirm = etConfirmPass.getText().toString();

                if (!password.equals(passwordConfirm)) {
                    Toast.makeText(SignUpActivity.this, "passwords must match", Toast.LENGTH_SHORT).show();
                } else {
                    ParseUser user = new ParseUser();
                    user.setUsername(username);
                    user.setPassword(password);

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                // Let use app now, redirect to log in page
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                intent.putExtra("new account", "true");
                                startActivity(intent);
                                finish();

                            } else {
                                // Sign up didn't exceed
                                Log.e(TAG, "done: ", e);
                            }
                        }
                    });

                }


            }
        });

    }
}