package br.com.project.samsung.inventarioapp.service.impl;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import br.com.project.samsung.inventarioapp.model.Inventario;
import br.com.project.samsung.inventarioapp.service.IFirebaseService;
import br.com.project.samsung.inventarioapp.util.FirebaseConfiguracao;

public class FirebaseServiceImpl implements IFirebaseService {

    private DatabaseReference databaseReference = FirebaseConfiguracao.getDatabaseReference();

    public Query listarInventariosOrdenadosPor(String ordem){
        return databaseReference.orderByChild(ordem);
    }

    public Query listarInventarios(String ordem, String texto){
        return listarInventariosOrdenadosPor(ordem).startAt(texto).endAt(texto+"\uf8ff");
    }

    public Task<Void> removerInventarioPor(String id){
        return databaseReference.child(id).removeValue();
    }

    public Task<Void> adicionarInventario(Inventario inventario){
        return databaseReference.child(inventario.getId()).setValue(inventario);

    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
}
