package ec.edu.epn.snai.Controlador.Adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ec.edu.epn.snai.Modelo.Taller;
import ec.edu.epn.snai.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del Taller, hecho en Java
public class ListaTalleresAdaptador extends RecyclerView.Adapter<ListaTalleresAdaptador.TallerViewHolder>{

    private List<Taller> talleres= new ArrayList<>();
    private TallerOnItemClickListener onItemClickListenerTaller;

    public ListaTalleresAdaptador(List<Taller> talleres, TallerOnItemClickListener onItemClickListener) {
        this.talleres = talleres;
        this.onItemClickListenerTaller=onItemClickListener;
    }

    @NonNull
    @Override
    public TallerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_taller,viewGroup, false);
        TallerViewHolder holder= new TallerViewHolder(view,onItemClickListenerTaller);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TallerViewHolder viewHolder, int i) {

        //Seteo los valores en los diferentes controles
        viewHolder.txtTema.setText(talleres.get(i).getTema().toUpperCase());
        viewHolder.txtNumeroTaller.setText(talleres.get(i).getNumeroTaller().toString());
        if(talleres.get(i).getFecha()!=null){
            viewHolder.txtFecha.setText(fomatearFecha(talleres.get(i).getFecha()));
        }
        viewHolder.txtTipo.setText( talleres.get(i).getTipo());

    }

    @Override
    public int getItemCount() {
        return talleres.size();
    }

    public class TallerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtTema, txtNumeroTaller,txtFecha,txtTipo;
        TallerOnItemClickListener tallerOnItemClickListener;

        public TallerViewHolder(View itemView, TallerOnItemClickListener onItemClickListener){
            super(itemView);
            txtTema=itemView.findViewById(R.id.txtTema);
            txtNumeroTaller=itemView.findViewById(R.id.txtNumeroTaller);
            txtFecha=itemView.findViewById(R.id.txtFecha);
            txtTipo=itemView.findViewById(R.id.txtTipoTaller);
            this.tallerOnItemClickListener=onItemClickListener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            tallerOnItemClickListener.OnItemClick(getAdapterPosition());
        }
    }

    public interface TallerOnItemClickListener{

        void OnItemClick(int posicion);

    }

    private String fomatearFecha(Date fecha){

        String  pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(fecha);
    }
}
