package com.example.recarga;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ListaPersonaAdaptador extends RecyclerView.Adapter<ListaPersonaAdaptador.PersonasViewHolder> {

    private ArrayList<Usuario> listaUsuario;
    private Context context;


    public ListaPersonaAdaptador( Context context) {
        listaUsuario = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public PersonasViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_historial,viewGroup,false);
        return new PersonasViewHolder(view);
    }

    public void onBindViewHolder( PersonasViewHolder personasViewHolder, int i) {
        personasViewHolder.reempla(listaUsuario.get(i));
    }

    public int getItemCount() {
        return listaUsuario.size();
    }

    public void agregarregistro (Usuario usuario){
        listaUsuario.add(usuario);
        Collections.reverse(listaUsuario);
        this.notifyDataSetChanged();
    }

    public class PersonasViewHolder extends RecyclerView.ViewHolder {
        private TextView telefono,operador,valor,fecha;


        public PersonasViewHolder(View itemView) {
            super(itemView);
            telefono = (TextView) itemView.findViewById(R.id.telefono);
            operador = (TextView) itemView.findViewById(R.id.operador);
            valor = (TextView) itemView.findViewById(R.id.valor);
            fecha = (TextView) itemView.findViewById(R.id.fecha);
        }
        private void reempla (Usuario usuario){
            telefono.setText(String.valueOf(usuario.getTelefono()));
            operador.setText(String.valueOf(usuario.getOperador()));
            valor.setText(String.valueOf(usuario.getValor()));
            fecha.setText(String.valueOf(usuario.getFecha()));
        }
    }
}
