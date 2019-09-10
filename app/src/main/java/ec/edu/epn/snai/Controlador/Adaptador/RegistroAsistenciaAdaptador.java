package ec.edu.epn.snai.Controlador.Adaptador;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ec.edu.epn.snai.Modelo.AdolescenteInfractor;
import ec.edu.epn.snai.Modelo.Taller;
import ec.edu.epn.snai.R;

import java.util.ArrayList;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del RegistoAsistencia, hecho en Java
public class RegistroAsistenciaAdaptador extends RecyclerView.Adapter<RegistroAsistenciaAdaptador.RegistroAsistenciaViewHolder>{

    private List<AdolescenteInfractor> adolescenteInfractoresLista= new ArrayList<>();
    private Context context;

    public RegistroAsistenciaAdaptador() {
    }

    public RegistroAsistenciaAdaptador(List<AdolescenteInfractor> adolescenteInfractoresLista, Context context) {
        this.adolescenteInfractoresLista = adolescenteInfractoresLista;
        this.context=context;
    }

    @NonNull
    @Override
    public RegistroAsistenciaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.activity_item_registro_asistencia,null);
        RegistroAsistenciaViewHolder holder= new RegistroAsistenciaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroAsistenciaViewHolder viewHolder, int i) {

        //Seteo los valores en los diferentes controles
        viewHolder.txtNombres.setText(adolescenteInfractoresLista.get(i).getNombres());
        viewHolder.txtApellidos.setText(adolescenteInfractoresLista.get(i).getApellidos());
        viewHolder.txtCedula.setText(adolescenteInfractoresLista.get(i).getCedula());
        viewHolder.txtDocumento.setText( adolescenteInfractoresLista.get(i).getDocumento());
    }

    @Override
    public int getItemCount() {
        return adolescenteInfractoresLista.size();
    }

    public class RegistroAsistenciaViewHolder extends RecyclerView.ViewHolder{

        TextView txtNombres, txtApellidos,txtCedula,txtDocumento;

        public RegistroAsistenciaViewHolder(View itemView){
            super(itemView);
            txtNombres=itemView.findViewById(R.id.txtNombresAI);
            txtApellidos=itemView.findViewById(R.id.txtApellidosAI);
            txtCedula=itemView.findViewById(R.id.txtCedulaAI);
            txtDocumento=itemView.findViewById(R.id.txtDocumentoAI);
        }

    }

}
