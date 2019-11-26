package br.com.project.samsung.inventarioapp.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseConfiguracao {

    private static DatabaseReference databaseReference;
    private static StorageReference storageReference;

    public static DatabaseReference getDatabaseReference(){
        if (databaseReference == null)
            databaseReference = FirebaseDatabase.getInstance().getReference("inventarios");

        return databaseReference;
    }

    public static StorageReference getStorageReference(){
        if (storageReference == null)
            storageReference = FirebaseStorage.getInstance().getReference("image_inventarios");

        return storageReference;
    }

    public static StorageReference getStorageReference(String url){
        return FirebaseStorage.getInstance().getReferenceFromUrl(url);
    }


}
