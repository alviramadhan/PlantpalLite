package com.example.plantpallite.MyAccount;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.plantpallite.Database.MyPlantpalRepository;
import com.example.plantpallite.User;

public class MyAccountViewModel extends AndroidViewModel {
    private final MyPlantpalRepository repository;

    public MyAccountViewModel(@NonNull Application application) {
        super(application);
        repository = new MyPlantpalRepository(application);
    }

    public void deleteAccount(int userId) {
        repository.deleteUser(userId);
    }

}
