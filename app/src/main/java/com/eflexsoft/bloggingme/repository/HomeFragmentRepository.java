package com.eflexsoft.bloggingme.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eflexsoft.bloggingme.R;
import com.eflexsoft.bloggingme.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.Map;

public class HomeFragmentRepository {

    Context context;
    public MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    public HomeFragmentRepository(Context context) {
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

    public void addLike(String postId) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {


                try {
                    DocumentReference documentReference = firebaseFirestore.collection("Posts").document(postId);

                    DocumentSnapshot documentSnapshot = transaction.get(documentReference);

                    long newLikeCount = documentSnapshot.getLong("likes") + 1;
                    transaction.update(documentReference, "likes", newLikeCount);
                } catch (NullPointerException n) {

                }

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

                DocumentReference documentReference = firebaseFirestore.collection("Posts").document(postId)
                        .collection("likesId").document(FirebaseAuth.getInstance().getUid());

                Map<String, Object> map = new HashMap<>();
                map.put("id", FirebaseAuth.getInstance().getUid());

                documentReference.set(map);


            }
        });

    }

    public void removeLike(String postId) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {


                try {
                    DocumentReference documentReference = firebaseFirestore.collection("Posts").document(postId);

                    DocumentSnapshot documentSnapshot = transaction.get(documentReference);

                    long newLikeCount = documentSnapshot.getLong("likes") - 1;
                    transaction.update(documentReference, "likes", newLikeCount);
                } catch (NullPointerException n) {

                }


                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

                DocumentReference documentReference = firebaseFirestore.collection("Posts").document(postId)
                        .collection("likesId").document(FirebaseAuth.getInstance().getUid());

                documentReference.delete();


            }
        });
        ;

    }


}
