package com.example.kleberstevendiazcoello.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

public class botton_menu extends AppCompatActivity  {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.support.v4.app.FragmentManager fragmentManager= getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.content2,new homeFragment()).commit();

                    return true;
                case R.id.navigation_camera:
                    transaction.replace(R.id.content2,new cameraFragment()).commit();

                    return true;
                case R.id.navigation_user:
                    transaction.replace(R.id.content2,new PerfilFragment()).commit();

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botton_menu);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}