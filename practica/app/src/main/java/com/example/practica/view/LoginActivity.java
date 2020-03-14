package com.example.practica.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;

import com.example.practica.R;
import com.example.practica.presents.LoginPresent;
import android.view.View;
import android.content.Intent;

public class LoginActivity extends AppCompatActivity implements LoginPresent.View {
    private EditText txtUser;
    private EditText txtPwd;
    private LoginPresent present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnlogin = findViewById(R.id.login);
        txtUser = findViewById(R.id.user);
        txtPwd = findViewById(R.id.password);
        present = new LoginPresent(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                present.postLogin(String.valueOf(txtUser.getText()),String.valueOf(txtPwd.getText()));
                txtPwd.setText("");
                txtUser.setText("");
            }
        });
    }

    public void goMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void accerderLogin() {
        goMain();
    }
}
