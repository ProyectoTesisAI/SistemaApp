package ec.edu.epn.snai.Controlador.Adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ec.edu.epn.snai.Modelo.Reporte4;
import ec.edu.epn.snai.R;

import java.util.ArrayList;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del ItemInforme
public class Reporte5Adaptador extends RecyclerView.Adapter<Reporte5Adaptador.Reporte5ViewHolder>{

    private List<Reporte4> listaResultadosReporte5 = new ArrayList<>();

    public Reporte5Adaptador(List<Reporte4> listaReporte5) {
        this.listaResultadosReporte5 = listaReporte5;
    }

    @NonNull
    @Override
    public Reporte5ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_resultados_reporte_5,viewGroup, false);
        Reporte5ViewHolder holder= new Reporte5ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Reporte5ViewHolder viewHolder, int i) {
        //Seteo los valores en los diferentes controles
        viewHolder.txtUzdiCai.setText(listaResultadosReporte5.get(i).getCai_uzdi());
        viewHolder.txtNombres.setText(listaResultadosReporte5.get(i).getNombres());
        viewHolder.txtApellidos.setText(listaResultadosReporte5.get(i).getApellidos());
        viewHolder.txtTipo.setText(listaResultadosReporte5.get(i).getTipoDelito());
        viewHolder.txtMedida.setText(listaResultadosReporte5.get(i).getMedidaSocioeducativa());

    }

    @Override
    public int getItemCount() {
        return listaResultadosReporte5.size();
    }



    public class Reporte5ViewHolder extends RecyclerView.ViewHolder {

        TextView txtUzdiCai, txtNombres,txtApellidos, txtTipo, txtMedida;

        public Reporte5ViewHolder(View itemView){
            super(itemView);
            txtUzdiCai=itemView.findViewById(R.id.vwTipoCentro5);
            txtNombres=itemView.findViewById(R.id.vwNombres5);
            txtApellidos=itemView.findViewById(R.id.vwApellidos5);
            txtTipo =itemView.findViewById(R.id.vwTipoDelito5);
            txtMedida =itemView.findViewById(R.id.vwMedida5);
        }

    }
}
