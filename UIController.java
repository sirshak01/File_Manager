package com.ApkEditor.pro.ui;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.ApkEditor.pro.navigation.NavigationManager;

public class UIController {

    Activity act;
    OnNavigate navigator;

    public interface OnNavigate {
        void onNavigate(Uri uri);
    }

    public UIController(Activity activity, OnNavigate navigator) {
        this.act = activity;
        this.navigator = navigator;
    }

    public void setupButtons(View btnBack, View btnHome) {

        // BACK BUTTON
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NavigationManager.canGoBack()) {
                    if (navigator != null) {
                        navigator.onNavigate(NavigationManager.back());
                    }
                } else {
                    Toast.makeText(act, "No back history", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // HOME BUTTON
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navigator != null) {
                    navigator.onNavigate(NavigationManager.home());
                }
            }
        });
    }
}