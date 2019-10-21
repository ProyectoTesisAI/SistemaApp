package ec.edu.epn.snai.Controlador.Adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ec.edu.epn.snai.Modelo.Reporte;
import ec.edu.epn.snai.R;

import java.util.ArrayList;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del Reporte, hecho en Java
public class ListaReportesAdaptador extends RecyclerView.Adapter<ListaReportesAdaptador.ReporteViewHolder>{

    private List<Reporte> reportes = new ArrayList<>();
    private ReporteOnItemClickListener onItemClickListenerReporte;

    public ListaReportesAdaptador(List<Reporte> reportes, ReporteOnItemClickListener onItemClickListener) {
        this.reportes = reportes;
        this.onItemClickListenerReporte =onItemClickListener;
    }

    @NonNull
    @Override
    public ReporteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_reporte,viewGroup, false);
        ReporteViewHolder holder= new ReporteViewHolder(view, onItemClickListenerReporte);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReporteViewHolder viewHolder, int i) {

        //Seteo los valores en los diferentes controles
        viewHolder.txtReporte.setText(reportes.get(i).getDescripcion());

    }

    @Override
    public int getItemCount() {
        return reportes.size();
    }

    public class ReporteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtReporte;
        ReporteOnItemClickListener reporteOnItemClickListener;

        public ReporteViewHolder(View itemView, ReporteOnItemClickListener onItemClickListener){
            super(itemView);
            txtReporte=itemView.findViewById(R.id.txtReporte);
            this.reporteOnItemClickListener=onItemClickListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            reporteOnItemClickListener.OnItemClick(getAdapterPosition());
        }
    }

    public interface ReporteOnItemClickListener {

        void OnItemClick(int posicion);

    }

}
