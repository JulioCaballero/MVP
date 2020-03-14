package com.example.practica.presents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;

import com.example.practica.Login.Login;
import com.example.practica.R;
import com.example.practica.view.FirstFragment;
import com.example.practica.view.SecondFragment;

public class MainActivity extends AppCompatActivity implements FirstFragment.OnFragmentInteractionListener, SecondFragment.OnFragmentInteractionListener {

    private Fragment first;
    private Fragment second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        first = new FirstFragment();
        second = new SecondFragment();

        if(Login.getUsername().equals("admin"))
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragment,first).commit();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragment,second).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
