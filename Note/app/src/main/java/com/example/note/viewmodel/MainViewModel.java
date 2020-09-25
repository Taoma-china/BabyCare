package com.example.note.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.note.database.AppRepository;
import com.example.note.database.NoteEntity;
import com.example.note.utilities.SampleData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    public LiveData<List<NoteEntity>> mNotes;
    private AppRepository mRepository;
    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mNotes = mRepository.mNotes;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllNotes() {
        mRepository.deleteAllNotes();
    }
}
