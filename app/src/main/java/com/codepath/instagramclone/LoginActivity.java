package com.codepath.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.instagramclone.databinding.ActivityLoginBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private Button mBtnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        // if recently created user, doesn't skip to main activity
        String name = "";
        try {
            name = getIntent().getStringExtra("new account");
            if (name != null) {
                // tells user that an account has been made
                Toast.makeText(this, getResources().getString(R.string.new_account_message), Toast.LENGTH_LONG).show();
            }
            Log.i(TAG, "onCreate try: recentlyCreated status " + name);

        } catch (Exception e){
            Log.i(TAG, "onCreate catch: recentlyCreated status " + name);
        }

        if (ParseUser.getCurrentUser() != null && name == null) {
            goMainActivity();
        }

        mEtUsername = binding.etUsername;
        mEtPassword = binding.etPassword;
        mBtnLogin = binding.btnLogin;
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                loginUser(username, password);
            }
        });
        mBtnSignUp = binding.btnSignUp;
        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick sign up button");
                signUpUser();
            }
        });

    }

    private void signUpUser() {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
        finish();
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to log in user " + username);

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                    return;
                }
                // navigates to main activity if user has properly signed in
                goMainActivity();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}