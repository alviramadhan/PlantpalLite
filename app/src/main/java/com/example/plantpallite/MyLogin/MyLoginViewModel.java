package com.example.plantpallite.MyLogin;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.plantpallite.Database.MyPlantpalRepository;
import com.example.plantpallite.Database.MyRoomDatabase;
import com.example.plantpallite.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyLoginViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel

    private MyPlantpalRepository repository;
    private ExecutorService executorService;
    private MutableLiveData<User> loginResult = new MutableLiveData<>();

    public MyLoginViewModel(@NonNull Application application) {
        super(application);
        repository = new MyPlantpalRepository(application);
        executorService = Executors.newSingleThreadExecutor();
    }


    public LiveData<User> login(String email, String password) {
        MutableLiveData<User> loginResult = new MutableLiveData<>();

        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            User user = repository.login(email, password); // Call repository method
            loginResult.postValue(user); // Post the result to LiveData
        });

        return loginResult;
    }
}

