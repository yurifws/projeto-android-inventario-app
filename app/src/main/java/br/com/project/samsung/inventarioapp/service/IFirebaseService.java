package br.com.project.samsung.inventarioapp.service;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import br.com.project.samsung.inventarioapp.model.Inventario;

public interface IFirebaseService {

    public Query listarInventariosOrdenadosPor(String ordem);

    public Query listarInventarios(String ordem, String texto);

    public Task<Void> removerInventarioPor(String id);

    public Task<Void> adicionarInventario(Inventario inventario);

    public DatabaseReference getDatabaseReference();
}
