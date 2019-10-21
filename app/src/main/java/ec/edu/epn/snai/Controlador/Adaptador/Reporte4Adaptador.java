package ec.edu.epn.snai.Controlador.Adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ec.edu.epn.snai.Modelo.Reporte3;
import ec.edu.epn.snai.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del Reporte 4
public class Reporte4Adaptador extends RecyclerView.Adapter<Reporte4Adaptador.Reporte4ViewHolder>{

    private List<Reporte3> listaResultadosReporte4 = new ArrayList<>();

    public Reporte4Adaptador(List<Reporte3> listaReporte4) {
        this.listaResultadosReporte4 = listaReporte4;
    }

    @NonNull
    @Override
    public Reporte4ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_resultados_reporte_4,viewGroup, false);
        Reporte4ViewHolder holder= new Reporte4ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Reporte4ViewHolder viewHolder, int i) {
        //Seteo los valores en los diferentes controles
        viewHolder.txtUzdiCai.setText(listaResultadosReporte4.get(i).getCai_uzdi());
        viewHolder.txtNombres.setText(listaResultadosReporte4.get(i).getNombres());
        viewHolder.txtApellidos.setText(listaResultadosReporte4.get(i).getApellidos());
        viewHolder.txtNacionalidad.setText(listaResultadosReporte4.get(i).getNacionalidad() );
        viewHolder.txtPaisOrigen.setText( listaResultadosReporte4.get(i).getPaisOrigen());
        viewHolder.txtTipoDelito.setText(listaResultadosReporte4.get(i).getTipoDelito() );
    }

    @Override
    public int getItemCount() {
        return listaResultadosReporte4.size();
    }



    public class Reporte4ViewHolder extends RecyclerView.ViewHolder {

        TextView txtUzdiCai, txtNombres,txtApellidos,txtNacionalidad, txtPaisOrigen, txtTipoDelito;

        public Reporte4ViewHolder(View itemView){
            super(itemView);
            txtUzdiCai=itemView.findViewById(R.id.txtTipoCentro);
            txtNombres=itemView.findViewById(R.id.txtNombres);
            txtApellidos=itemView.findViewById(R.id.txtApellidos);
            txtNacionalidad=itemView.findViewById(R.id.txtNacionalidad);
            txtPaisOrigen=itemView.findViewById(R.id.txtPaisOrigen);
            txtTipoDelito=itemView.findViewById(R.id.txtTipoDelito);
        }

    }
}
