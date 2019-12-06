package com.app.schoolerpapp.dagger;

import com.app.schoolerpapp.firebase.FireBaseMessageServiceUse;
import com.app.schoolerpapp.ui.activity.DashBoardActivity;
import com.app.schoolerpapp.ui.activity.LoginActivity;
import com.app.schoolerpapp.ui.activity.OtpActivity;
import com.app.schoolerpapp.ui.activity.SplashActivity;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created on : Feb 09, 2019
 * Author     : AndroidWave
 */
@Singleton
@Component(modules = {SharedPreferencesModule.class})
public interface AppComponent {

    void inject(DashBoardActivity target);
    void inject(LoginActivity target);
    void inject(SplashActivity target);
    void inject(OtpActivity target);

    void inject(@NotNull FireBaseMessageServiceUse fireBaseMessageServiceUse);
}
