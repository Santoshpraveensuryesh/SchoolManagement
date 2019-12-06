package com.app.schoolerpapp.ui.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.app.schoolerpapp.MyApplication;
import com.app.schoolerpapp.R;
import com.app.schoolerpapp.language.LocaleManager;
import com.app.schoolerpapp.language.Utility;

import java.util.Locale;


public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(MyApplication.localeManager.setLocale(base));
        Log.d(TAG, "attachBaseContext");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        Utility.resetActivityTitle(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showResourcesInfo();

    }

    private void showResourcesInfo() {
    }
}