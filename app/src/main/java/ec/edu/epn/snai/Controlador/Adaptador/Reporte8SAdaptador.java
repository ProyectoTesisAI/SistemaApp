package ec.edu.epn.snai.Controlador.Adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ec.edu.epn.snai.Modelo.Reporte6S;
import ec.edu.epn.snai.R;

import java.util.ArrayList;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del ItemInforme
public class Reporte8SAdaptador extends RecyclerView.Adapter<Reporte8SAdaptador.Reporte1ViewHolder>{

    private List<Reporte6S> listaResultadosReporte6S = new ArrayList<>();

    public Reporte8SAdaptador(List<Reporte6S> listaReporte6S) {
        this.listaResultadosReporte6S = listaReporte6S;
    }

    @NonNull
    @Override
    public Reporte1ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_resultados_reporte_8s,viewGroup, false);
        Reporte1ViewHolder holder= new Reporte1ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Reporte1ViewHolder viewHolder, int i) {
        //Seteo los valores en los diferentes controles
        viewHolder.txtUzdiCai.setText(listaResultadosReporte6S.get(i).getCai_uzdi());
        viewHolder.txtNombres.setText(listaResultadosReporte6S.get(i).getNombres());
        viewHolder.txtApellidos.setText(listaResultadosReporte6S.get(i).getApellidos());
        viewHolder.txtEstudia.setText(listaResultadosReporte6S.get(i).getEdad().toString());
        viewHolder.txtEstudia.setText(listaResultadosReporte6S.get(i).getEstudia().toString());
        viewHolder.txtNivelEducativo.setText(listaResultadosReporte6S.get(i).getNivelEducativo());

    }

    @Override
    public int getItemCount() {
        return listaResultadosReporte6S.size();
    }



    public class Reporte1ViewHolder extends RecyclerView.ViewHolder {

        TextView txtUzdiCai, txtNombres,txtApellidos,txtEdad,txtEstudia,txtNivelEducativo;

        public Reporte1ViewHolder(View itemView){
            super(itemView);
            txtUzdiCai=itemView.findViewById(R.id.vwTipoCentro8s);
            txtNombres=itemView.findViewById(R.id.vwNombres8s);
            txtApellidos=itemView.findViewById(R.id.vwApellidos8s);
            txtEdad=itemView.findViewById(R.id.vwEdad8s);
            txtEstudia =itemView.findViewById(R.id.vwEstudia8s);
            txtNivelEducativo =itemView.findViewById(R.id.vwNivelEducativo8s);
        }

    }
}
