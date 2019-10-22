package ec.edu.epn.snai.Controlador.Adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ec.edu.epn.snai.Modelo.Reporte5;
import ec.edu.epn.snai.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del ItemInforme
public class Reporte6Adaptador extends RecyclerView.Adapter<Reporte6Adaptador.Reporte1ViewHolder>{

    private List<Reporte5> listaResultadosReporte6 = new ArrayList<>();

    public Reporte6Adaptador(List<Reporte5> listaReporte6) {
        this.listaResultadosReporte6 = listaReporte6;
    }

    @NonNull
    @Override
    public Reporte1ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_resultados_reporte_6,viewGroup, false);
        Reporte1ViewHolder holder= new Reporte1ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Reporte1ViewHolder viewHolder, int i) {
        //Seteo los valores en los diferentes controles
        viewHolder.txtUzdiCai.setText(listaResultadosReporte6.get(i).getCai_uzdi());
        viewHolder.txtNombres.setText(listaResultadosReporte6.get(i).getNombres());
        viewHolder.txtApellidos.setText(listaResultadosReporte6.get(i).getApellidos());
        viewHolder.txtFechaAprehension.setText( fomatearFecha(listaResultadosReporte6.get(i).getFechaAprehension()) );
        viewHolder.txtTiempoSentencia.setText( listaResultadosReporte6.get(i).getTiempoSetenciaMedida().toString());
        viewHolder.txtFechaCumplimiento60.setText( fomatearFecha(listaResultadosReporte6.get(i).getFechaCumplimiento60()) );
        viewHolder.txtFechaCumplimiento80.setText( fomatearFecha(listaResultadosReporte6.get(i).getFechaCumplimiento80()) );
        viewHolder.txtPorcentajeCumplimiento.setText( formatearPorcentajeCumplimiento(listaResultadosReporte6.get(i).getPorcentajeCumplimiento()) );
    }

    @Override
    public int getItemCount() {
        return listaResultadosReporte6.size();
    }



    public class Reporte1ViewHolder extends RecyclerView.ViewHolder {

        TextView txtUzdiCai, txtNombres,txtApellidos,txtFechaAprehension, txtTiempoSentencia, txtFechaCumplimiento60, txtFechaCumplimiento80, txtPorcentajeCumplimiento ;

        public Reporte1ViewHolder(View itemView){
            super(itemView);
            txtUzdiCai=itemView.findViewById(R.id.txtTipoCentro);
            txtNombres=itemView.findViewById(R.id.txtNombres);
            txtApellidos=itemView.findViewById(R.id.txtApellidos);
            txtFechaAprehension=itemView.findViewById(R.id.txtFechaAprehension);
            txtTiempoSentencia=itemView.findViewById(R.id.txtTiempoSentencia);
            txtFechaCumplimiento60=itemView.findViewById(R.id.txtFechaCumplimiento60);
            txtFechaCumplimiento80=itemView.findViewById(R.id.txtFechaCumplimiento80);
            txtPorcentajeCumplimiento=itemView.findViewById(R.id.txtPorcentajeCumplimiento);
        }

    }

    private String fomatearFecha(Date fecha){

        String  pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(fecha);
    }

    private String formatearPorcentajeCumplimiento(Double cumplimientoMedida){

        cumplimientoMedida=cumplimientoMedida*100;
        int porcetaje=(int) Math.round(cumplimientoMedida);
        return String.valueOf(porcetaje);
    }
}
