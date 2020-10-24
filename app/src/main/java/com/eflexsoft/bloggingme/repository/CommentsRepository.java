package com.eflexsoft.bloggingme.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.text.format.Time;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class CommentsRepository {

    Context context;
    public MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();


    public CommentsRepository(Context context) {
        this.context = context;
    }

    public void sendComment(String commentText, String postId, Uri imageUri) {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);

        String date = dateFormat.format(Calendar.getInstance().getTime());

        if (imageUri == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("CommenterId", firebaseAuth.getUid());
            map.put("commentId", System.currentTimeMillis());
            map.put("date", date);
            map.put("commentsBody", commentText);
            map.put("CommentImage", "none");

            DocumentReference documentReference = firebaseFirestore.collection("Posts").document(postId)
                    .collection("comments").document(FirebaseAuth.getInstance().getUid());

            documentReference.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    isSuccess.setValue(true);
                    firebaseFirestore.runTransaction(new Transaction.Function<Void>() {
                        @Nullable
                        @Override
                        public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                            DocumentReference documentReference = firebaseFirestore.collection("Posts").document(postId);

                            DocumentSnapshot snapshot = transaction.get(documentReference);

                            long comments = snapshot.getLong("comments") + 1;

                            transaction.update(documentReference, "comments", comments);
                            return null;
                        }
                    });

                }
            });

        } else {
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference mainReference = firebaseStorage.getReference("postImages");

            StorageReference firstReference = mainReference.child(getFile(imageUri) + "" + System.currentTimeMillis());
            UploadTask firstUploadTask = firstReference.putFile(imageUri);

            firstUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return firstReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {

                        String downloadUri = task.getResult().toString();


                        DocumentReference documentReference = firebaseFirestore.collection("Posts").document(postId)
                                .collection("comments").document(FirebaseAuth.getInstance().getUid());

                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);

//                        Time time = new Time(Time.getCurrentTimezone());

                        String date = dateFormat.format(Calendar.getInstance().getTime());

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("commenterId", firebaseAuth.getUid());
                        map.put("comment", System.currentTimeMillis());
                        map.put("date", date);
                        map.put("commentsBody", commentText);
                        map.put("CommentImage", downloadUri);

                        documentReference.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    isSuccess.setValue(true);
                                    firebaseFirestore.runTransaction(new Transaction.Function<Void>() {
                                        @Nullable
                                        @Override
                                        public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                                            DocumentReference documentReference = firebaseFirestore.collection("Posts").document(postId);

                                            DocumentSnapshot snapshot = transaction.get(documentReference);

                                            long comments = snapshot.getLong("comments") + 1;

                                            transaction.update(documentReference, "comments", comments);
                                            return null;
                                        }
                                    });
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                isSuccess.setValue(false);
                            }
                        });

                    }
                }
            });

        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("CommenterId", firebaseAuth.getUid());
        map.put("postId", postId);
        map.put("date", date);
        map.put("commentsBody", commentText);
        map.put("CommentImage", imageUri);

        DocumentReference documentReference = firebaseFirestore.collection("Posts").document(postId)
                .collection("comments").document(FirebaseAuth.getInstance().getUid());

//        DocumentReference documentReference = firebaseFirestore.collection("Posts").document(postId);

    }

    public String getFile(Uri url) {
        ContentResolver contentResolver = context.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(url));
    }
}
