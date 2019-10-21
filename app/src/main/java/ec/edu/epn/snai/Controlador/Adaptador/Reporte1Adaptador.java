package ec.edu.epn.snai.Controlador.Adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ec.edu.epn.snai.Modelo.Reporte1;
import ec.edu.epn.snai.R;

import java.util.ArrayList;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del ItemInforme
public class Reporte1Adaptador extends RecyclerView.Adapter<Reporte1Adaptador.Reporte1ViewHolder>{

    private List<Reporte1> listaResultadosReporte1 = new ArrayList<>();

    public Reporte1Adaptador(List<Reporte1> listaReporte1) {
        this.listaResultadosReporte1 = listaReporte1;
    }

    @NonNull
    @Override
    public Reporte1ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_resultados_reporte_1,viewGroup, false);
        Reporte1ViewHolder holder= new Reporte1ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Reporte1ViewHolder viewHolder, int i) {
        //Seteo los valores en los diferentes controles
        viewHolder.txtUzdiCai.setText(listaResultadosReporte1.get(i).getCai_uzdi());
        viewHolder.txtNombres.setText(listaResultadosReporte1.get(i).getNombres());
        viewHolder.txtApellidos.setText(listaResultadosReporte1.get(i).getApellidos());
        viewHolder.txtTipoDelito.setText(listaResultadosReporte1.get(i).getTipoDelto());

    }

    @Override
    public int getItemCount() {
        return listaResultadosReporte1.size();
    }



    public class Reporte1ViewHolder extends RecyclerView.ViewHolder {

        TextView txtUzdiCai, txtNombres,txtApellidos,txtTipoDelito;

        public Reporte1ViewHolder(View itemView){
            super(itemView);
            txtUzdiCai=itemView.findViewById(R.id.txtTipoCentro);
            txtNombres=itemView.findViewById(R.id.txtNombres);
            txtApellidos=itemView.findViewById(R.id.txtApellidos);
            txtTipoDelito=itemView.findViewById(R.id.txtTipoDelito);
        }

    }
}
