package ec.edu.epn.snai.Controlador.Adaptador;

import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import ec.edu.epn.snai.Modelo.RegistroFotografico;
import ec.edu.epn.snai.R;

import java.util.ArrayList;
import java.util.List;

import static ec.edu.epn.snai.R.id.btn_quitar;

//Clase Adaptador correspondiente al RecyclerView del RegistroFotografico, hecho en Java
public class IngresarRegistroFotograficoAdaptador extends RecyclerView.Adapter<IngresarRegistroFotograficoAdaptador.RegistroFotograficoViewHolder> {

    private List<RegistroFotografico> fotografias = new ArrayList<>();

    public IngresarRegistroFotograficoAdaptador(List<RegistroFotografico> fotografias) {
        this.fotografias = fotografias;
    }

    @NonNull
    @Override
    public RegistroFotograficoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_editar_registro_fotografico, viewGroup, false);
        RegistroFotograficoViewHolder holder = new RegistroFotograficoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RegistroFotograficoViewHolder viewHolder, int i) {

        byte[] byteDatos = Base64.decode(fotografias.get(i).getImagenAux(), Base64.DEFAULT);
        fotografias.get(i).setFoto(BitmapFactory.decodeByteArray(byteDatos, 0, byteDatos.length));
        //Seteo los valores en los diferentes controles
        viewHolder.imgImagen.setImageBitmap(fotografias.get(i).getFoto());
        //viewHolder.setOnClickListeners();
        viewHolder.btnQuitar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final int posicion=viewHolder.getLayoutPosition();
                try{
                    fotografias.remove(posicion);
                    notifyDataSetChanged();
                }catch (Exception e){
                    Log.e(e.getMessage(),"Ocurrio un error en onClick, en OnBindViewHolder");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fotografias.size();
    }

    public class RegistroFotograficoViewHolder extends RecyclerView.ViewHolder {

        ImageView imgImagen;
        Button btnQuitar;

        public RegistroFotograficoViewHolder(final View itemView) {
            super(itemView);
            imgImagen = itemView.findViewById(R.id.imgRegistroFotograficoEditar);
            btnQuitar = itemView.findViewById(btn_quitar);
        }
    }
}