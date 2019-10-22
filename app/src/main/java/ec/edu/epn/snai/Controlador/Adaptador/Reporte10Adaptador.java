package ec.edu.epn.snai.Controlador.Adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ec.edu.epn.snai.Modelo.Reporte8;
import ec.edu.epn.snai.R;

import java.util.ArrayList;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del Reporte 10
public class Reporte10Adaptador extends RecyclerView.Adapter<Reporte10Adaptador.Reporte10ViewHolder>{

    private List<Reporte8> listaResultadosReporte10 = new ArrayList<>();

    public Reporte10Adaptador(List<Reporte8> listaReporte10) {
        this.listaResultadosReporte10 = listaReporte10;
    }

    @NonNull
    @Override
    public Reporte10ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_resultados_reporte_10,viewGroup, false);
        Reporte10ViewHolder holder= new Reporte10ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Reporte10ViewHolder viewHolder, int i) {
        //Seteo los valores en los diferentes controles
        viewHolder.txtCedula.setText(listaResultadosReporte10.get(i).getCedula());
        viewHolder.txtNombres.setText(listaResultadosReporte10.get(i).getNombres());
        viewHolder.txtApellidos.setText(listaResultadosReporte10.get(i).getApellidos());
        viewHolder.txtRol.setText( listaResultadosReporte10.get(i).getRol() );
        viewHolder.txtNumeroInformes.setText( listaResultadosReporte10.get(i).getCantidadInformesCompletos().toString());
    }

    @Override
    public int getItemCount() {
        return listaResultadosReporte10.size();
    }



    public class Reporte10ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCedula, txtNombres,txtApellidos,txtRol, txtNumeroInformes;

        public Reporte10ViewHolder(View itemView){
            super(itemView);
            txtCedula=itemView.findViewById(R.id.txtCedula);
            txtNombres=itemView.findViewById(R.id.txtNombres);
            txtApellidos=itemView.findViewById(R.id.txtApellidos);
            txtRol=itemView.findViewById(R.id.txtRol);
            txtNumeroInformes=itemView.findViewById(R.id.txtNumeroInformes);
        }

    }
}
