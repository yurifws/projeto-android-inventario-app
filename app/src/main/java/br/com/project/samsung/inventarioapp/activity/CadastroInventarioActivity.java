package br.com.project.samsung.inventarioapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.com.project.samsung.inventarioapp.R;
import br.com.project.samsung.inventarioapp.facade.InventarioFacade;
import br.com.project.samsung.inventarioapp.model.Inventario;
import br.com.project.samsung.inventarioapp.util.Util;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class CadastroInventarioActivity extends AppCompatActivity {

    public static final int GALLERY_PICK = 1;
    private InventarioFacade inventarioFacade = new InventarioFacade();
    private Inventario inventarioSelecionado = new Inventario();
    private Inventario inventarioNovo = new Inventario();

    private TextInputLayout txtTipoEquipamento;
    private TextInputLayout txtModelo;
    private TextView txtMesAno;
    private TextInputLayout txtValorAquisicao;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Button btnIncluirAlterar;
    private Button btnFoto;
    private Button btnMesAno;

    private Uri uri;
    private ImageView imgFotoCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_inventario);
        init();
        btnIncluirAlterar.setText(getString(R.string.incluir_inventario));

        Intent intent = getIntent();
        if(intent.hasExtra("itemSelecionado")) {
            inventarioSelecionado = intent.getParcelableExtra("itemSelecionado");
            txtTipoEquipamento.getEditText().setText(inventarioSelecionado.getTipo());
            txtModelo.getEditText().setText(inventarioSelecionado.getModelo());
            txtMesAno.setText(inventarioSelecionado.getMesAno());
            txtValorAquisicao.getEditText().setText(String.valueOf(inventarioSelecionado.getValorAquisicao()));
            btnIncluirAlterar.setText(getString(R.string.alterar_inventario));
            Util.exibirImagemTelaCorrespondente(CadastroInventarioActivity.this, inventarioSelecionado.getUrlImagem(), imgFotoCadastro);
        }

        btnMesAno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escolherMesAno();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                preencherMesAno(ano, mes);

            }
        };

        btnIncluirAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inventarioNovo = preencherInventario();
                if(uri != null) {
                    uploadArquivo();
                }else{
                    manterInventario();
                }


            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escolherArquivo();
            }
        });
    }

    public void init(){
        txtTipoEquipamento = findViewById(R.id.txtTipoEquipamento);
        txtModelo = findViewById(R.id.txtModelo);
        txtMesAno = findViewById(R.id.txtMesAno);
        txtValorAquisicao = findViewById(R.id.txtValorAquisicao);
        btnIncluirAlterar = findViewById(R.id.btnIncluirAlterar);
        btnFoto = findViewById(R.id.btnFoto);
        imgFotoCadastro = findViewById(R.id.imgFotoCadastro);
        btnMesAno = findViewById(R.id.btnMesAno);
    }

    private void manterInventario() {
        inventarioFacade.adicionarInventario(inventarioNovo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                redirecionarTelaPrincipal();
                Util.log("INSERT", "SUCESS");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                redirecionarTelaPrincipal();
                Util.log("INSERT", "FAIL");
            }
        });
    }



    private Inventario preencherInventario() {
        Inventario inventario = new Inventario();
        inventario.setId(inventarioSelecionado.getId());
        if(Util.isNull(inventario.getId())) {
            inventario.setId(Util.getCurrentDateTime(Util.DATETIME_FORMAT_KEY));
        }
        inventario.setTipo(txtTipoEquipamento.getEditText().getText().toString());
        inventario.setModelo(txtModelo.getEditText().getText().toString());
        String mesAno = txtMesAno.getText().toString();
        if(mesAno.contains("/")) {
            inventario.setMesAno(mesAno);
        }
        String valor = txtValorAquisicao.getEditText().getText().toString();
        if (!Util.isNull(valor)) {
            inventario.setValorAquisicao(Double.parseDouble(valor));
        }
        inventario.setUrlImagem(inventarioSelecionado.getUrlImagem());
        return inventario;
    }

    private void escolherMesAno() {
        int ano = Util.getCurrentYear();
        int mes = Util.getCurrentMonth();
        int dia = Util.getCurrentDay();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                CadastroInventarioActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                dateSetListener,ano,mes,dia);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    private void preencherMesAno(int ano, int mes) {
        String mesAno = mes +"/"+ano;
        txtMesAno.setText(mesAno);
    }

    private void escolherArquivo(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.selecione_uma_imagem)), GALLERY_PICK);

    }

    private void uploadArquivo(){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(getString(R.string.uploading));
            progressDialog.show();

        inventarioFacade.adicionarImagemInventario(inventarioNovo.getId(), uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();
                    inventarioNovo.setUrlImagem(downloadUrl.toString());
                    inventarioFacade.adicionarInventario(inventarioNovo).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            redirecionarTelaPrincipal();
                            Util.log("INSERT", "SUCESS");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            redirecionarTelaPrincipal();
                            Util.log("INSERT", "FAIL");
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Util.log("INSERT", "FILE FAIL");
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress =(100* taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage(getString(R.string.uploading_progress)+" "+progress+"%...");
                    Util.log("INSERT", "FILE PROCESS");
                }
            });

    }

    private void redirecionarTelaPrincipal() {
        if(Util.isNull(inventarioSelecionado.getId())){
            redirecionarTelaQRCode();
        }else{
            Intent intent = new Intent(CadastroInventarioActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }


    private void redirecionarTelaQRCode() {
            Intent intent = new Intent(CadastroInventarioActivity.this, QRCodeActivity.class);
            intent.putExtra("itemSelecionado", inventarioNovo);
            startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            Picasso.with(CadastroInventarioActivity.this).load(uri).into(imgFotoCadastro);
        }
    }
}
