package com.example.portal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {
    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!SharedPrefer.getInstance(this).isLogged()){
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }

        navigation = findViewById(R.id.bottomNavigation);

        navigation.setOnItemSelectedListener(this);
        navigation.setSelectedItemId(R.id.profile);
    }
    Profile profile = new Profile();
    Services service= new Services();
    History hist = new History();
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.profile :
                getSupportFragmentManager().beginTransaction().replace(R.id.container, profile).commit();
                return true;
            case R.id.service :
                getSupportFragmentManager().beginTransaction().replace(R.id.container, service).commit();
                return true;
            case R.id.history :
                getSupportFragmentManager().beginTransaction().replace(R.id.container, hist).commit();
                return true;
        }
        return false;
    }
}
