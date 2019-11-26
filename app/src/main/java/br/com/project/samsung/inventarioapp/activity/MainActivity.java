package br.com.project.samsung.inventarioapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.com.project.samsung.inventarioapp.facade.InventarioFacade;
import br.com.project.samsung.inventarioapp.R;
import br.com.project.samsung.inventarioapp.adapter.InventarioArrayAdapter;
import br.com.project.samsung.inventarioapp.model.Inventario;
import br.com.project.samsung.inventarioapp.util.Util;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private InventarioFacade inventarioFacade = new InventarioFacade();
    private EditText txtBusca;
    private ListView listView;
    private Button btnIncluir;
    private List<Inventario> arrayList = new ArrayList<Inventario>(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        final InventarioArrayAdapter arrayAdapter = new InventarioArrayAdapter(this, arrayList);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                redirecionarTelaDetalhe(i);
            }
        });

        inventarioFacade.getDatabaseReference().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Inventario inventario = dataSnapshot.getValue(Inventario.class);
                arrayAdapter.add(inventario);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnIncluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirecionarTelaCadastro();
            }
        });

        txtBusca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String texto = txtBusca.getText().toString().trim();
                buscarPorCodigo(texto, arrayAdapter);
            }
        });
    }

    private void init(){
        txtBusca = findViewById(R.id.txtBusca);
        listView = findViewById(R.id.listView);
        btnIncluir = findViewById(R.id.btnIncluir);
    }

    private void buscarPorCodigo(String texto, final InventarioArrayAdapter arrayAdapter) {
        Query query;
        String ordem = "id";

        if (Util.isNull(texto)) {
            query = inventarioFacade.listarInventariosOrdenadosPor(ordem);
        } else {
            query = inventarioFacade.listarInventarios(ordem, texto);
        }

        arrayAdapter.clear();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Inventario inventario = item.getValue(Inventario.class);
                    arrayAdapter.add(inventario);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void redirecionarTelaDetalhe(int i) {
        Intent intent = new Intent(MainActivity.this, DetalheInventarioActivity.class);
        intent.putExtra("itemSelecionado", arrayList.get(i));
        startActivity(intent);
    }

    private void redirecionarTelaCadastro() {
        Intent intent = new Intent(MainActivity.this, CadastroInventarioActivity.class);
        startActivity(intent);
    }
}
