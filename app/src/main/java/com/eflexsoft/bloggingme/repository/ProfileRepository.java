package com.eflexsoft.bloggingme.repository;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.bloggingme.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileRepository {

    Context context;
    public MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    public ProfileRepository(Context context) {
        this.context = context;
    }

    public void getUserDetails() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(id);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                User user = value.toObject(User.class);

                userMutableLiveData.setValue(user);


            }
        });

    }
}
