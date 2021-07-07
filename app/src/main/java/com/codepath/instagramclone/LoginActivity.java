package com.codepath.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.instagramclone.databinding.ActivityLoginBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.parceler.Parcels;


public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignUp;

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
                Toast.makeText(this, "account created!", Toast.LENGTH_LONG).show();
            }
            Log.i(TAG, "onCreate try: recentlyCreated status " + name);

        } catch (Exception e){
            Log.i(TAG, "onCreate catch: recentlyCreated status " + name);
        }


        if (ParseUser.getCurrentUser() != null && name == null) {
            goMainActivity();
        }

        etUsername = binding.etUsername;
        etPassword = binding.etPassword;
        btnLogin = binding.btnLogin;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });
        btnSignUp = binding.btnSignUp;
        btnSignUp.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(LoginActivity.this, "Issue with login", Toast.LENGTH_SHORT).show();
                    return;
                }
                // navigate to main activity if user has properly signed in
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}