package br.com.project.samsung.inventarioapp.facade;

import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

import org.junit.Test;

import androidx.annotation.NonNull;
import br.com.project.samsung.inventarioapp.model.Inventario;

import static org.junit.Assert.*;

public class InventarioFacadeTest {

    InventarioFacade inventarioFacade = new InventarioFacade();

    @Test
    public void listarInventariosOrdenadosPor() {
        Query query = inventarioFacade.listarInventariosOrdenadosPor("id");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                assertTrue(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                assertTrue(false);
            }
        });

    }

    @Test
    public void listarInventarios() {
        Query query = inventarioFacade.listarInventarios("id", "2019");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                assertTrue(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                assertTrue(false);
            }
        });
    }

    @Test
    public void removerInventarioPor() {
        inventarioFacade.removerInventarioPor("id").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                assertTrue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                assertTrue(false);
            }
        });
    }

    @Test
    public void adicionarInventario() {
        Inventario inventario = new Inventario();
        inventarioFacade.adicionarInventario(inventario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                assertTrue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                assertTrue(false);
            }
        });
    }

    @Test
    public void getDatabaseReference() {
        inventarioFacade.getDatabaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                assertTrue(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                assertTrue(false);
            }
        });
    }

    @Test
    public void removerImagemInventarioUrl() {
        inventarioFacade.removerImagemInventarioUrl("SemUrl").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                assertTrue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                assertTrue(false);
            }
        });
    }

    @Test
    public void adicionarImagemInventario() {
        inventarioFacade.adicionarImagemInventario("2019", Uri.parse("SemUri")).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                assertTrue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                assertTrue(false);
            }
        });
    }
}