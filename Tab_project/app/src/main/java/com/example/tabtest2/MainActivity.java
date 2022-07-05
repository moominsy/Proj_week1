package com.example.tabtest2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tabs;
    FragmentA fragmentA;
    FragmentB fragmentB;
    FragmentC fragmentC;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("onCreateMain", "");

        getSupportActionBar().setTitle("Contacts");
        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Contacts"));
        tabs.addTab(tabs.newTab().setText("Album"));
        tabs.addTab(tabs.newTab().setText("MyVoca"));
        tabs.setTabTextColors(Color.rgb(0,0,0), Color.rgb(0,0,150));

        fragmentA = new FragmentA();
        fragmentB = new FragmentB();
        fragmentC = new FragmentC();

        fragmentC.Initwords(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, fragmentA).commit();
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();

                Fragment selected = null;

                if(position==0){
                    selected = fragmentA;
                    getSupportActionBar().setTitle("Contacts");
                }else if(position==1){
                    selected = fragmentB;
                    getSupportActionBar().setTitle("Album");
                }else if(position==2){
                    selected = fragmentC;
                    getSupportActionBar().setTitle("MyVoca");
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.containers, selected).commit();
            }
        });
    }
}