package ec.edu.epn.snai.Controlador.Adaptador;

import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import ec.edu.epn.snai.Modelo.RegistroFotografico;
import ec.edu.epn.snai.R;

import java.util.ArrayList;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del RegistroFotografico, hecho en Java
public class ListaRegistroFotograficoAdaptador extends RecyclerView.Adapter<ListaRegistroFotograficoAdaptador.RegistroFotograficoViewHolder>{

    private List<RegistroFotografico> fotografias = new ArrayList<>();

    public ListaRegistroFotograficoAdaptador(List<RegistroFotografico> fotografias) {
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
        if(fotografias!=null){
            byte[] byteDatos = Base64.decode(fotografias.get(i).getImagenAux(), Base64.DEFAULT);
            fotografias.get(i).setFoto(BitmapFactory.decodeByteArray(byteDatos,0,byteDatos.length));
            //Seteo los valores en los diferentes controles
            viewHolder.imgImagen.setImageBitmap(fotografias.get(i).getFoto());
        }
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
