package br.com.project.samsung.inventarioapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import br.com.project.samsung.inventarioapp.R;
import br.com.project.samsung.inventarioapp.model.Inventario;
import br.com.project.samsung.inventarioapp.util.Util;

public class InventarioArrayAdapter extends ArrayAdapter<Inventario>{
    private Context context;

    public InventarioArrayAdapter(@NonNull Context context, @NonNull List<Inventario> objects) {
        super(context, R.layout.custom_inventario, objects);
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.custom_inventario, parent, false);
        String idInventario = getItem(position).getId();
        TextView tvId = view.findViewById(R.id.tvId);
        ImageView imgFoto = view.findViewById(R.id.imgFoto);
        tvId.setText(idInventario);
        Util.exibirImagemTelaCorrespondente(context, getItem(position).getUrlImagem(), imgFoto);

        return view;
    }
}
