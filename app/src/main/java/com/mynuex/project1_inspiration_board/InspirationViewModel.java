package com.mynuex.project1_inspiration_board;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class InspirationViewModel extends AndroidViewModel {

    private InspirationRepository mInspirationRepository;
    private LiveData<List<Inspiration>> allInspirations;

    public InspirationViewModel(@NonNull Application application) {
        super(application);
        mInspirationRepository = new InspirationRepository(application);
        allInspirations = mInspirationRepository.getAllInspirations();
    }

    public void insert(Inspiration inspiration) {
        mInspirationRepository.insert(inspiration);
    }

    public void update(Inspiration inspiration) {
        mInspirationRepository.update(inspiration);
    }

    public void delete(Inspiration inspiration) {
        mInspirationRepository.delete(inspiration);
    }

    public void deleteById(int id) {
        mInspirationRepository.deleteByID(id);
    }

    public LiveData<List<Inspiration>> getAllInspirations() {
        return allInspirations;
    }
}