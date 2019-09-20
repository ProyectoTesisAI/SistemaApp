package ec.edu.epn.snai.Controlador.Adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ec.edu.epn.snai.Modelo.ItemTaller;
import ec.edu.epn.snai.R;

import java.util.ArrayList;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del ItemInforme
public class ItemInformeAdaptador extends RecyclerView.Adapter<ItemInformeAdaptador.ItemInformeViewHolder>{

    private List<ItemTaller> itemsTaller = new ArrayList<>();

    public ItemInformeAdaptador(List<ItemTaller> itemsInforme) {
        this.itemsTaller = itemsInforme;
    }

    @NonNull
    @Override
    public ItemInformeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_actividad_informe,viewGroup, false);
        ItemInformeViewHolder holder= new ItemInformeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemInformeViewHolder viewHolder, int i) {
        //Seteo los valores en los diferentes controles
        viewHolder.txtActividad.setText(itemsTaller.get(i).getActividad());
        viewHolder.txtObjetivo.setText(itemsTaller.get(i).getObjetivoEspecifico());
        viewHolder.txtMateriales.setText(itemsTaller.get(i).getMateriales());
        viewHolder.txtResponsable.setText(itemsTaller.get(i).getResponsable());
        viewHolder.txtDuracion.setText(itemsTaller.get(i).getDuracion().toString());

    }

    @Override
    public int getItemCount() {
        return itemsTaller.size();
    }



    public class ItemInformeViewHolder extends RecyclerView.ViewHolder {

        TextView txtActividad, txtObjetivo,txtMateriales,txtResponsable, txtDuracion;

        public ItemInformeViewHolder(View itemView){
            super(itemView);
            txtActividad=itemView.findViewById(R.id.txtItemActividadI);
            txtObjetivo=itemView.findViewById(R.id.txtItemObjetivoI);
            txtMateriales=itemView.findViewById(R.id.txtItemMaterialesI);
            txtResponsable=itemView.findViewById(R.id.txtItemResponsableI);
            txtDuracion=itemView.findViewById(R.id.txtItemDuracionI);
        }

    }
}
