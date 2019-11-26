package br.com.project.samsung.inventarioapp.service.impl;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import br.com.project.samsung.inventarioapp.service.IStorageService;
import br.com.project.samsung.inventarioapp.util.FirebaseConfiguracao;

public class StorageServiceImpl implements IStorageService {

    private StorageReference storageReference = FirebaseConfiguracao.getStorageReference();

    public Task<Void> removerImagemInventarioUrl(String url){
        return FirebaseConfiguracao.getStorageReference(url).delete();
    }

    public UploadTask adicionarImagemInventario(String id, Uri uri){
        return storageReference.child(id).putFile(uri);
    }
}
