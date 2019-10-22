package ec.edu.epn.snai.Controlador.Adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ec.edu.epn.snai.Modelo.Reporte6N;
import ec.edu.epn.snai.R;

import java.util.ArrayList;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del ItemInforme
public class Reporte8NAdaptador extends RecyclerView.Adapter<Reporte8NAdaptador.Reporte1ViewHolder>{

    private List<Reporte6N> listaResultadosReporte6N = new ArrayList<>();

    public Reporte8NAdaptador(List<Reporte6N> listaReporte6N) {
        this.listaResultadosReporte6N = listaReporte6N;
    }

    @NonNull
    @Override
    public Reporte1ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_resultados_reporte_8n,viewGroup, false);
        Reporte1ViewHolder holder= new Reporte1ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Reporte1ViewHolder viewHolder, int i) {
        //Seteo los valores en los diferentes controles
        viewHolder.txtUzdiCai.setText(listaResultadosReporte6N.get(i).getCai_uzdi());
        viewHolder.txtNombres.setText(listaResultadosReporte6N.get(i).getNombres());
        viewHolder.txtApellidos.setText(listaResultadosReporte6N.get(i).getApellidos());
        viewHolder.txtEdad.setText(listaResultadosReporte6N.get(i).getEdad().toString());
        viewHolder.txtEstudia.setText(listaResultadosReporte6N.get(i).getEstudia().toString());
        viewHolder.txtRazon.setText(listaResultadosReporte6N.get(i).getRazonNoEstudia());

    }

    @Override
    public int getItemCount() {
        return listaResultadosReporte6N.size();
    }



    public class Reporte1ViewHolder extends RecyclerView.ViewHolder {

        TextView txtUzdiCai, txtNombres,txtApellidos,txtEdad,txtEstudia,txtRazon;

        public Reporte1ViewHolder(View itemView){
            super(itemView);
            txtUzdiCai=itemView.findViewById(R.id.vwTipoCentro8n);
            txtNombres=itemView.findViewById(R.id.vwNombres8n);
            txtApellidos=itemView.findViewById(R.id.vwApellidos8n);
            txtEdad=itemView.findViewById(R.id.vwEdad8n);
            txtEstudia =itemView.findViewById(R.id.vwEstudia8n);
            txtRazon =itemView.findViewById(R.id.vwRazon8n);
        }

    }
}
