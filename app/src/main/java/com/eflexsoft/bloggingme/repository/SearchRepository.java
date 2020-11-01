package com.eflexsoft.bloggingme.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.Map;

public class SearchRepository {

    Context context;

    public SearchRepository(Context context){
        this.context = context;
    }

    public void searchPost(){}
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
