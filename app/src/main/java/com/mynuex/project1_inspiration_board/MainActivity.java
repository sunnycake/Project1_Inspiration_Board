package com.mynuex.project1_inspiration_board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements
        InspirationFragment.InspirationFragmentListener,
        AddInspirationFragment.NewInspirationListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Adding fragments to hosting activity
        InspirationFragment inspirationFragment = InspirationFragment.newInstance();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container, inspirationFragment);
        ft.commit();
    }

    // interface listener from Inspiration Fragment
    @Override
    public void newInspiration() {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        AddInspirationFragment addInspirationFragment = AddInspirationFragment.newInstance();
        ft.replace(R.id.fragment_container, addInspirationFragment);
        ft.addToBackStack(null);
        ft.commit();

    }


    @Override
    public void addedInspiration(Inspiration inspiration) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        InspirationFragment fragment = InspirationFragment.newInstance();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();

    }
}
