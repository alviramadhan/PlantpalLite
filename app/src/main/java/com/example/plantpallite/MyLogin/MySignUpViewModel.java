package com.example.plantpallite.MyLogin;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.plantpallite.Database.MyPlantpalRepository;
import com.example.plantpallite.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MySignUpViewModel extends AndroidViewModel {

    private final MyPlantpalRepository repository;
    private final ExecutorService executorService;

    public MySignUpViewModel(@NonNull Application application) {
        super(application);
        repository = new MyPlantpalRepository(application);
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<Boolean> createUser(User user) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        executorService.execute(() -> {
            boolean emailExists = repository.isEmailExists(user.getEmail());
            if (emailExists) {
                result.postValue(false); // Email already exists
            } else {
                repository.insertUser(user);
                result.postValue(true); // User creation successful
            }
        });

        return result;
    }
}
