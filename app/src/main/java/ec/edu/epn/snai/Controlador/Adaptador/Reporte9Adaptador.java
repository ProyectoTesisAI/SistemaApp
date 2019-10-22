package ec.edu.epn.snai.Controlador.Adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ec.edu.epn.snai.Modelo.Reporte7;
import ec.edu.epn.snai.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del Reporte 9
public class Reporte9Adaptador extends RecyclerView.Adapter<Reporte9Adaptador.Reporte9ViewHolder>{

    private List<Reporte7> listaResultadosReporte9 = new ArrayList<>();

    public Reporte9Adaptador(List<Reporte7> listaReporte9) {
        this.listaResultadosReporte9 = listaReporte9;
    }

    @NonNull
    @Override
    public Reporte9ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_resultados_reporte_9,viewGroup, false);
        Reporte9ViewHolder holder= new Reporte9ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Reporte9ViewHolder viewHolder, int i) {
        //Seteo los valores en los diferentes controles
        viewHolder.txtUzdiCai.setText(listaResultadosReporte9.get(i).getCai_uzdi());
        viewHolder.txtNombres.setText(listaResultadosReporte9.get(i).getNombres());
        viewHolder.txtApellidos.setText(listaResultadosReporte9.get(i).getApellidos());
        viewHolder.txtTipoDelito.setText( listaResultadosReporte9.get(i).getTipoDelto() );
        viewHolder.txtProvinciaResidencia.setText( listaResultadosReporte9.get(i).getProvinciaResidencia());
        viewHolder.txtCantonResidencia.setText( listaResultadosReporte9.get(i).getCantonResidencia());
        viewHolder.txtDireccionResidencia.setText( listaResultadosReporte9.get(i).getDireccionResidencia() );
    }

    @Override
    public int getItemCount() {
        return listaResultadosReporte9.size();
    }



    public class Reporte9ViewHolder extends RecyclerView.ViewHolder {

        TextView txtUzdiCai, txtNombres,txtApellidos,txtTipoDelito, txtProvinciaResidencia, txtCantonResidencia, txtDireccionResidencia ;

        public Reporte9ViewHolder(View itemView){
            super(itemView);
            txtUzdiCai=itemView.findViewById(R.id.txtTipoCentro);
            txtNombres=itemView.findViewById(R.id.txtNombres);
            txtApellidos=itemView.findViewById(R.id.txtApellidos);
            txtTipoDelito=itemView.findViewById(R.id.txtTipoDelito);
            txtProvinciaResidencia=itemView.findViewById(R.id.txtProvinciaResidencia);
            txtCantonResidencia=itemView.findViewById(R.id.txtCantonResidencia);
            txtDireccionResidencia=itemView.findViewById(R.id.txtDireccionResidencia);
        }

    }
}
