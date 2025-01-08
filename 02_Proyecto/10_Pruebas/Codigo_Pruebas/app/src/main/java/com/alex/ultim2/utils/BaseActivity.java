package com.alex.ultim2.utils;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private static final long INACTIVITY_TIMEOUT = 5 * 60 * 1000; // 5 minutos en milisegundos
    private Handler inactivityHandler = new Handler();
    private Runnable inactivityRunnable = this::finishApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inactivityHandler.postDelayed(inactivityRunnable, INACTIVITY_TIMEOUT);
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        inactivityHandler.removeCallbacks(inactivityRunnable);
        inactivityHandler.postDelayed(inactivityRunnable, INACTIVITY_TIMEOUT);
    }

    private void finishApp() {
        finish();
    }
}

