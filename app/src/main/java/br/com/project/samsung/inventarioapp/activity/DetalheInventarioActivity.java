package br.com.project.samsung.inventarioapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import br.com.project.samsung.inventarioapp.R;
import br.com.project.samsung.inventarioapp.facade.InventarioFacade;
import br.com.project.samsung.inventarioapp.model.Inventario;
import br.com.project.samsung.inventarioapp.util.Util;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import org.joda.time.DateTime;
import org.joda.time.Months;

import java.util.Calendar;

public class DetalheInventarioActivity extends AppCompatActivity {

    public static final String BARRA = "/";
    private InventarioFacade inventarioFacade = new InventarioFacade();
    private Inventario inventario = new Inventario();

    private ImageView imgFotoDetalhe;
    private TextInputLayout txtTipoEquipamentoDetalhe;
    private TextInputLayout txtModeloDetalhe;
    private TextInputLayout txtMesAnoDetalhe;
    private TextInputLayout txtValorAquisicaoDetalhe;
    private TextInputLayout txtValorEstimadoAtual;
    private Button btnAlterar;
    private Button btnDeletar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_inventario);
        init();

        Intent intent = getIntent();
        if(intent.hasExtra("itemSelecionado")) {
            inventario = intent.getParcelableExtra("itemSelecionado");
            txtTipoEquipamentoDetalhe.getEditText().setText(inventario.getTipo());
            txtModeloDetalhe.getEditText().setText(inventario.getModelo());
            txtMesAnoDetalhe.getEditText().setText(inventario.getMesAno());
            String valorAquisicao = String.valueOf(inventario.getValorAquisicao());
            txtValorAquisicaoDetalhe.getEditText().setText(valorAquisicao);
            txtValorEstimadoAtual.getEditText().setText(valorAquisicao);
            preencherEstimadoAtual();
            Util.exibirImagemTelaCorrespondente(DetalheInventarioActivity.this, inventario.getUrlImagem(), imgFotoDetalhe);


        }

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirecionarTelaCadastro();
            }
        });

        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                excluirInventario();
            }
        });
    }

    private void preencherEstimadoAtual() {
        String depreciacao = getString(R.string.depreciacao);
        if(inventario.getMesAno().contains(BARRA) && inventario.getValorAquisicao() > 0 && Util.isNumeric(depreciacao)){
            int quantidadeMeses = calcularMesesEntreDatas();
            double valorDepreciacao = Double.parseDouble(depreciacao)*quantidadeMeses/100;
            double valorEstimadoAtual = inventario.getValorAquisicao() - valorDepreciacao;
            txtValorEstimadoAtual.getEditText().setText(String.valueOf(valorEstimadoAtual));
        }
    }

    private int calcularMesesEntreDatas() {
        Calendar calendar = Calendar.getInstance();
        String mesAno[] =  inventario.getMesAno().split(BARRA);
        int mes = Integer.parseInt(mesAno[0]);
        int ano = Integer.parseInt(mesAno[1]);
        DateTime inicio = new DateTime(ano, mes, 01, 0, 0, 0, 0);
        DateTime fim = new DateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 01, 0, 0, 0, 0);
        return Months.monthsBetween(inicio, fim).getMonths();
    }

    public void init(){
        imgFotoDetalhe = findViewById(R.id.imgFotoDetalhe);
        txtTipoEquipamentoDetalhe = findViewById(R.id.txtTipoEquipamentoDetalhe);
        txtModeloDetalhe = findViewById(R.id.txtModeloDetalhe);
        txtMesAnoDetalhe = findViewById(R.id.txtMesAnoDetalhe);
        txtValorAquisicaoDetalhe = findViewById(R.id.txtValorAquisicaoDetalhe);
        txtValorEstimadoAtual = findViewById(R.id.txtValorEstimadoAtual);
        btnAlterar = findViewById(R.id.btnAlterar);
        btnDeletar = findViewById(R.id.btnDeletar);
    }

    private void excluirInventario() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.deleting));
        progressDialog.show();
        inventarioFacade.removerInventarioPor(inventario.getId()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                String url = inventario.getUrlImagem();
                if (!Util.isNull(url)){
                    inventarioFacade.removerImagemInventarioUrl(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            redirecionarTelaPrincipal();
                            Util.log("DELETE FILE", "SUCESS");
                        }
                    });
                }else{
                    progressDialog.dismiss();
                    redirecionarTelaPrincipal();

                }
                Util.log("DELETE","SUCESS");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                redirecionarTelaPrincipal();
                Util.log("DELETE","FAIL");
            }
        });
    }

    private void redirecionarTelaCadastro() {
        Intent intent = new Intent(DetalheInventarioActivity.this, CadastroInventarioActivity.class);
        intent.putExtra("itemSelecionado", inventario);
        startActivity(intent);
    }

    private void redirecionarTelaPrincipal() {
        Intent intent = new Intent(DetalheInventarioActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
