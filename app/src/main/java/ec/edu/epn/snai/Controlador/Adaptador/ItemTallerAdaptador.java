package ec.edu.epn.snai.Controlador.Adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ec.edu.epn.snai.Modelo.ItemTaller;
import ec.edu.epn.snai.Modelo.Taller;
import ec.edu.epn.snai.R;

import java.util.ArrayList;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del ItemTaller
public class ItemTallerAdaptador extends RecyclerView.Adapter<ItemTallerAdaptador.ItemTallerViewHolder>{

    private List<ItemTaller> itemsTaller= new ArrayList<>();
    private ItemTallerOnItemClickListener onItemTallerClickListener;

    public ItemTallerAdaptador(List<ItemTaller> itemsTaller, ItemTallerOnItemClickListener onItemClickListener) {
        this.itemsTaller = itemsTaller;
        this.onItemTallerClickListener=onItemClickListener;
    }

    @NonNull
    @Override
    public ItemTallerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_actividad_taller,viewGroup, false);
        ItemTallerViewHolder holder= new ItemTallerViewHolder(view,onItemTallerClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemTallerViewHolder viewHolder, int i) {

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



    public class ItemTallerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtActividad, txtObjetivo,txtMateriales,txtResponsable, txtDuracion;
        ItemTallerOnItemClickListener itemTallerOnItemClickListener;

        public ItemTallerViewHolder(View itemView, ItemTallerOnItemClickListener onItemClickListener){
            super(itemView);
            txtActividad=itemView.findViewById(R.id.txtItemActividad);
            txtObjetivo=itemView.findViewById(R.id.txtItemObjetivo);
            txtMateriales=itemView.findViewById(R.id.txtItemMateriales);
            txtResponsable=itemView.findViewById(R.id.txtItemResponsable);
            txtDuracion=itemView.findViewById(R.id.txtItemDuracion);

            this.itemTallerOnItemClickListener =onItemClickListener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            itemTallerOnItemClickListener.OnItemClick(getAdapterPosition());
        }
    }

    public interface ItemTallerOnItemClickListener {

        void OnItemClick(int posicion);

    }
}
