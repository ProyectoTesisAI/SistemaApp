package ec.edu.epn.snai.Controlador.Adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ec.edu.epn.snai.Modelo.RegistroFotografico;
import ec.edu.epn.snai.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del RegistroFotografico, hecho en Java
public class RegistroFotograficoAdaptador extends RecyclerView.Adapter<RegistroFotograficoAdaptador.RegistroFotograficoViewHolder>{

    private List<RegistroFotografico> fotografias = new ArrayList<>();

    public RegistroFotograficoAdaptador(List<RegistroFotografico> fotografias) {
        this.fotografias = fotografias;
    }

    @NonNull
    @Override
    public RegistroFotograficoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_registro_fotografico,viewGroup, false);
        RegistroFotograficoViewHolder holder= new RegistroFotograficoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroFotograficoViewHolder viewHolder, int i) {

        //Seteo los valores en los diferentes controles
        viewHolder.imgImagen.setImageBitmap(fotografias.get(i).getFoto());
    }

    @Override
    public int getItemCount() {
        return fotografias.size();
    }

    public class RegistroFotograficoViewHolder extends RecyclerView.ViewHolder {

        ImageView imgImagen;

        public RegistroFotograficoViewHolder(View itemView){
            super(itemView);
            imgImagen=itemView.findViewById(R.id.imgRegistroFotografico);
        }
    }

}