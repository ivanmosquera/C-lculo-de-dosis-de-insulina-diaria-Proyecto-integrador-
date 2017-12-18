package com.example.kleberstevendiazcoello.ui.Otros;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.fragments.PerfilFragment;
import com.example.kleberstevendiazcoello.ui.fragments.cameraFragment;
import com.example.kleberstevendiazcoello.ui.fragments.homeFragment;

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

