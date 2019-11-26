package br.com.project.samsung.inventarioapp.service;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.UploadTask;

public interface IStorageService {

    public Task<Void> removerImagemInventarioUrl(String url);

    public UploadTask adicionarImagemInventario(String id, Uri uri);
}
