package ec.edu.epn.snai.Controlador.Adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ec.edu.epn.snai.Modelo.Informe;
import ec.edu.epn.snai.R;

import java.util.ArrayList;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del Informe, hecho en Java
public class InformeAdaptador extends RecyclerView.Adapter<InformeAdaptador.InformeViewHolder>{

    private List<Informe> informes = new ArrayList<>();
    private InformeOnItemClickListener onItemClickListenerInforme;

    public InformeAdaptador(List<Informe> informes, InformeOnItemClickListener onItemClickListener) {
        this.informes = informes;
        this.onItemClickListenerInforme=onItemClickListener;
    }

    @NonNull
    @Override
    public InformeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_taller,viewGroup, false);
        InformeViewHolder holder= new InformeViewHolder(view,onItemClickListenerInforme);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull InformeViewHolder viewHolder, int i) {

        //Seteo los valores en los diferentes controles
        viewHolder.txtTema.setText(informes.get(i).getIdTaller().getTema());
        viewHolder.txtNumeroTaller.setText(informes.get(i).getIdTaller().getNumeroTaller().toString());

        //String fecha=informes.get(i).getFecha().getDay()+"/"+informes.get(i).getFecha().getMonth()+"/"+informes.get(i).getFecha().getYear()+1900;
        //viewHolder.txtFecha.setText(fecha);
        //viewHolder.txtHora.setText( informes.get(i).getHoraInicio().toString());
    }

    @Override
    public int getItemCount() {
        return informes.size();
    }

    public class InformeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtTema, txtNumeroTaller,txtFecha,txtHora;
        InformeOnItemClickListener informeOnItemClickListener;

        public InformeViewHolder(View itemView, InformeOnItemClickListener onItemClickListener){
            super(itemView);
            txtTema=itemView.findViewById(R.id.txtTema);
            txtNumeroTaller=itemView.findViewById(R.id.txtNumeroTaller);
            //txtFecha=itemView.findViewById(R.id.txtFecha);
            //txtHora=itemView.findViewById(R.id.txtHora);
            this.informeOnItemClickListener=onItemClickListener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            informeOnItemClickListener.OnItemClick(getAdapterPosition());
        }
    }

    public interface InformeOnItemClickListener{

        void OnItemClick(int posicion);

    }
}
