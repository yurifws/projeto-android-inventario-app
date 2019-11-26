package br.com.project.samsung.inventarioapp.facade;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.storage.UploadTask;

import br.com.project.samsung.inventarioapp.model.Inventario;
import br.com.project.samsung.inventarioapp.service.IFirebaseService;
import br.com.project.samsung.inventarioapp.service.IStorageService;
import br.com.project.samsung.inventarioapp.service.impl.FirebaseServiceImpl;
import br.com.project.samsung.inventarioapp.service.impl.StorageServiceImpl;

public class InventarioFacade {

    private IFirebaseService firebaseService = new FirebaseServiceImpl();
    private IStorageService storageService = new StorageServiceImpl();

    public Query listarInventariosOrdenadosPor(String ordem){
        return firebaseService.listarInventariosOrdenadosPor(ordem);
    }

    public Query listarInventarios(String ordem, String texto){
        return firebaseService.listarInventarios(ordem, texto);
    }

    public Task<Void> removerInventarioPor(String id){
        return firebaseService.removerInventarioPor(id);
    }

    public Task<Void> adicionarInventario(Inventario inventario){
        return firebaseService.adicionarInventario(inventario);

    }

    public DatabaseReference getDatabaseReference() {
        return firebaseService.getDatabaseReference();
    }

    public Task<Void> removerImagemInventarioUrl(String url){
        return storageService.removerImagemInventarioUrl(url);
    }

    public UploadTask adicionarImagemInventario(String id, Uri uri){
        return storageService.adicionarImagemInventario(id, uri);
    }

}
