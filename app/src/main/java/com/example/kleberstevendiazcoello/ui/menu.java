package com.example.kleberstevendiazcoello.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

/**
 * Created by kleberstevendiazcoello on 1/12/17.
 */

public class menu extends AppCompatActivity {
    BottomNavigationView bnv;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu);

        android.support.v4.app.FragmentManager fragmentManager= getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content,new homeFragment()).commit();

        bnv = (BottomNavigationView)findViewById(R.id.navigationview);
        bnv.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

                android.support.v4.app.FragmentManager fragmentManager= getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (item.getItemId() == R.id.iniciomenu){
                    transaction.replace(R.id.content,new homeFragment()).commit();
                    Toast.makeText(getApplicationContext(),"entre", Toast.LENGTH_LONG).show();
                }else if (item.getItemId() == R.id.camaramenu){
                    transaction.replace(R.id.content,new cameraFragment()).commit();
                    Toast.makeText(getApplicationContext(),"entre", Toast.LENGTH_LONG).show();
                }else if(item.getItemId() == R.id.perfilmenu){
                    transaction.replace(R.id.content,new PerfilFragment()).commit();
                    Toast.makeText(getApplicationContext(),"entre", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}

