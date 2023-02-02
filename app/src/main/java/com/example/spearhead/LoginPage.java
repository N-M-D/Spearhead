package com.example.spearhead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    TextView btnGuest, textForgotPassword;
    Intent i;
    EditText textUsername,textPassword;
    Button btnLogin, btnRegister;
    SharedPreferences prefs;

    public static final String UserPREFRENCE = "UserPref";
    public static final String UID = "UserID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        btnGuest = (TextView) findViewById(R.id.btnGuest);
        textUsername = (EditText) findViewById(R.id.textEmail);
        textPassword = (EditText) findViewById(R.id.textPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(LoginPage.this, MainActivity.class);
                startActivity(i);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = textUsername.getText().toString();
                String password = textPassword.getText().toString();

                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                try {
                    int uid = db.userLogin(email, password);
                    if(uid > -1){
                        prefs = getSharedPreferences(UserPREFRENCE, MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt(UID, uid);
                        editor.commit();
                        i = new Intent(LoginPage.this, MainActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(), "Email or Password incorrect!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }
}