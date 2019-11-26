package br.com.project.samsung.inventarioapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Inventario extends BaseEntity implements Parcelable {

    private String tipo = "";
    private String modelo = "";
    private String mesAno = "";
    private double valorAquisicao = 0;
    private String urlImagem = "";

    protected Inventario(Parcel in) {
        setId(in.readString());
        tipo = in.readString();
        modelo = in.readString();
        mesAno = in.readString();
        valorAquisicao = in.readDouble();
        urlImagem = in.readString();
    }

    public Inventario(){
        super();
    }



    public static final Creator<Inventario> CREATOR = new Creator<Inventario>() {
        @Override
        public Inventario createFromParcel(Parcel in) {
            return new Inventario(in);
        }

        @Override
        public Inventario[] newArray(int size) {
            return new Inventario[size];
        }
    };

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMesAno() {
        return mesAno;
    }

    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }

    public double getValorAquisicao() {
        return valorAquisicao;
    }

    public void setValorAquisicao(double valorAquisicao) {
        this.valorAquisicao = valorAquisicao;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getId());
        parcel.writeString(tipo);
        parcel.writeString(modelo);
        parcel.writeString(mesAno);
        parcel.writeDouble(valorAquisicao);
        parcel.writeString(urlImagem);
    }
}
