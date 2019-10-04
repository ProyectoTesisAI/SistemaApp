package ec.edu.epn.snai.Controlador.Adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ec.edu.epn.snai.Modelo.AsistenciaAdolescente;
import ec.edu.epn.snai.R;

import java.util.ArrayList;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del RegistoAsistencia, hecho en Java
public class ListaAsistenciaAdaptador extends RecyclerView.Adapter<ListaAsistenciaAdaptador.RegistroAsistenciaViewHolder>{

    private List<AsistenciaAdolescente> listaAsistenciaAdolescentesInfractores = new ArrayList<>();


    public ListaAsistenciaAdaptador(List<AsistenciaAdolescente> adolescenteInfractoresLista) {
        this.listaAsistenciaAdolescentesInfractores = adolescenteInfractoresLista;
    }

    @NonNull
    @Override
    public RegistroAsistenciaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_listado_asistencia,viewGroup,false);
        RegistroAsistenciaViewHolder holder= new RegistroAsistenciaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroAsistenciaViewHolder viewHolder, int i) {

        //Seteo los valores en los diferentes controles
        viewHolder.txtNombres.setText(listaAsistenciaAdolescentesInfractores.get(i).getIdAdolescenteInfractor().getNombres().toUpperCase());
        viewHolder.txtApellidos.setText(listaAsistenciaAdolescentesInfractores.get(i).getIdAdolescenteInfractor().getApellidos().toUpperCase());
        viewHolder.txtCedula.setText(listaAsistenciaAdolescentesInfractores.get(i).getIdAdolescenteInfractor().getCedula());
        viewHolder.txtDocumento.setText( listaAsistenciaAdolescentesInfractores.get(i).getIdAdolescenteInfractor().getDocumento());
    }

    @Override
    public int getItemCount() {
        return listaAsistenciaAdolescentesInfractores.size();
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