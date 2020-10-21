package com.eflexsoft.bloggingme.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.format.Time;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.bloggingme.MainActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class PostRepository {

    Context context;

    public MutableLiveData<Boolean> booleanMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();

    public PostRepository(Context context) {
        this.context = context;
    }

    public String getFile(Uri url) {
        ContentResolver contentResolver = context.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(url));
    }

    public void sendPost(String title, String body, Uri postImg) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        String postId = String.valueOf(System.currentTimeMillis());

        if (postImg == null) {

            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Posts").document(postId);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

            Time time = new Time(Time.getCurrentTimezone());

            String date = dateFormat.format(Calendar.getInstance().getTime());

            HashMap<String, Object> map = new HashMap<>();
            map.put("StoryTitle", title);
            map.put("StoryBody", body);
            map.put("PosterId", firebaseAuth.getUid());
            map.put("postId", postId);
            map.put("date", date);
            map.put("postImage", "none");

            documentReference.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        isSuccess.setValue(true);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    isSuccess.setValue(false);
                }
            });

        } else {
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference mainReference = firebaseStorage.getReference("postImages");

            StorageReference firstReference = mainReference.child(getFile(postImg) + "" + System.currentTimeMillis());
            UploadTask firstUploadTask = firstReference.putFile(postImg);
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

                        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Posts").document(postId);

                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

                        Time time = new Time(Time.getCurrentTimezone());

                        String date = dateFormat.format(Calendar.getInstance().getTime());

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("StoryTitle", title);
                        map.put("StoryBody", body);
                        map.put("PosterId", firebaseAuth.getUid());
                        map.put("postId", postId);
                        map.put("date", date);
                        map.put("postImage", downloadUri);

                        documentReference.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    isSuccess.setValue(true);
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
        map.put("StoryTitle", title);
        map.put("StoryBody", body);
        map.put("PosterId", firebaseAuth.getUid());
        map.put("postId", postId);
        map.put("postImage", postImg);

        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Posts").document(postId);


    }

}
