package ec.edu.epn.snai.Controlador.Adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ec.edu.epn.snai.Modelo.Reporte2;
import ec.edu.epn.snai.R;

import java.util.ArrayList;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del ItemInforme
public class Reporte2Adaptador extends RecyclerView.Adapter<Reporte2Adaptador.Reporte2ViewHolder>{

    private List<Reporte2> listaResultadosReporte2 = new ArrayList<>();

    public Reporte2Adaptador(List<Reporte2> listaReporte2) {
        this.listaResultadosReporte2 = listaReporte2;
    }

    @NonNull
    @Override
    public Reporte2ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_resultados_reporte_2,viewGroup, false);
        Reporte2ViewHolder holder= new Reporte2ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Reporte2ViewHolder viewHolder, int i) {
        //Seteo los valores en los diferentes controles
        viewHolder.txtUzdiCai.setText(listaResultadosReporte2.get(i).getCai_uzdi());
        viewHolder.txtNombres.setText(listaResultadosReporte2.get(i).getNombres());
        viewHolder.txtApellidos.setText(listaResultadosReporte2.get(i).getApellidos());
        viewHolder.txtGenero.setText(listaResultadosReporte2.get(i).getGenero());
        viewHolder.txtEdad.setText(listaResultadosReporte2.get(i).getEdad());

    }

    @Override
    public int getItemCount() {
        return listaResultadosReporte2.size();
    }



    public class Reporte2ViewHolder extends RecyclerView.ViewHolder {

        TextView txtUzdiCai, txtNombres,txtApellidos,txtGenero,txtEdad;

        public Reporte2ViewHolder(View itemView){
            super(itemView);
            txtUzdiCai=itemView.findViewById(R.id.txtTipoCentro);
            txtNombres=itemView.findViewById(R.id.txtNombres);
            txtApellidos=itemView.findViewById(R.id.txtApellidos);
            txtGenero =itemView.findViewById(R.id.txtGenero);
            txtEdad =itemView.findViewById(R.id.txtEdad);
        }

    }
}
