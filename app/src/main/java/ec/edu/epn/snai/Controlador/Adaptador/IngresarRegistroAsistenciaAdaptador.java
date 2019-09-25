package ec.edu.epn.snai.Controlador.Adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import ec.edu.epn.snai.Modelo.AsistenciaAdolescente;
import ec.edu.epn.snai.R;

import java.util.ArrayList;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del RegistoAsistencia, hecho en Java
public class IngresarRegistroAsistenciaAdaptador extends RecyclerView.Adapter<IngresarRegistroAsistenciaAdaptador.RegistroAsistenciaViewHolder>{

    private List<AsistenciaAdolescente> adolescenteInfractoresLista= new ArrayList<>();

    public IngresarRegistroAsistenciaAdaptador() {
    }

    public IngresarRegistroAsistenciaAdaptador(List<AsistenciaAdolescente> adolescenteInfractoresLista) {
        this.adolescenteInfractoresLista = adolescenteInfractoresLista;
    }

    @NonNull
    @Override
    public IngresarRegistroAsistenciaAdaptador.RegistroAsistenciaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_editar_registro_asistencia,viewGroup,false);
        RegistroAsistenciaViewHolder holder= new RegistroAsistenciaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final IngresarRegistroAsistenciaAdaptador.RegistroAsistenciaViewHolder viewHolder, int i) {

        //Seteo los valores en los diferentes controles
        viewHolder.txtNombres.setText(adolescenteInfractoresLista.get(i).getIdAdolescenteInfractor().getNombres());
        viewHolder.txtApellidos.setText(adolescenteInfractoresLista.get(i).getIdAdolescenteInfractor().getApellidos());
        viewHolder.txtCedula.setText(adolescenteInfractoresLista.get(i).getIdAdolescenteInfractor().getCedula());
        viewHolder.txtDocumento.setText( adolescenteInfractoresLista.get(i).getIdAdolescenteInfractor().getDocumento());
        viewHolder.cbxAsistio.setChecked(adolescenteInfractoresLista.get(i).getAsistio());

        viewHolder.cbxAsistio.setTag(i);
        viewHolder.cbxAsistio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Integer pos = (Integer) viewHolder.cbxAsistio.getTag();
                if(adolescenteInfractoresLista.get(pos).getAsistio()){
                    adolescenteInfractoresLista.get(pos).setAsistio(false);
                }else{
                    adolescenteInfractoresLista.get(pos).setAsistio(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return adolescenteInfractoresLista.size();
    }

    public class RegistroAsistenciaViewHolder extends RecyclerView.ViewHolder{

        TextView txtNombres, txtApellidos,txtCedula,txtDocumento;
        CheckBox cbxAsistio;

        public RegistroAsistenciaViewHolder(View itemView){
            super(itemView);
            txtNombres=itemView.findViewById(R.id.txtNombresAIEditar);
            txtApellidos=itemView.findViewById(R.id.txtApellidosAIEditar);
            txtCedula=itemView.findViewById(R.id.txtCedulaAIEditar);
            txtDocumento=itemView.findViewById(R.id.txtDocumentoAIEditar);
            cbxAsistio=itemView.findViewById(R.id.cbxAdolescenteSeleccionadoEditar);
        }

    }

}
