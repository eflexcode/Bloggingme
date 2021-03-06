package com.eflexsoft.bloggingme.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.bloggingme.R;
import com.eflexsoft.bloggingme.model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class PostDetailedRepository {

    Context context;
    public MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLiked = new MutableLiveData<>();


    public PostDetailedRepository(Context context) {
        this.context = context;
    }

    public void getUserDetails(String id) {

        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(id);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                User user = value.toObject(User.class);

                userMutableLiveData.setValue(user);


            }
        });

    }

    public void getIsLiked(String postId){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firebaseFirestore.collection("Posts").document(postId)
                .collection("likesId").document(FirebaseAuth.getInstance().getUid());

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                assert value != null;
                if (value.exists()) {
                 isLiked.setValue(true);
                } else {
                    isLiked.setValue(false);
                }
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
