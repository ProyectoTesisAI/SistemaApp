package ec.edu.epn.snai.Controlador.Adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ec.edu.epn.snai.Modelo.Usuario;
import ec.edu.epn.snai.R;

import java.util.ArrayList;
import java.util.List;

//Clase Adaptador correspondiente al RecyclerView del ItemTaller
public class UsuariosAdaptador extends RecyclerView.Adapter<UsuariosAdaptador.UsuariosViewHolder>{

    private List<Usuario> listaUsuarios = new ArrayList<>();
    private UsuarioOnItemClickListener onUsuarioClickListener;

    public UsuariosAdaptador(List<Usuario> listaUsuariosAux, UsuarioOnItemClickListener onItemClickListener) {
        this.listaUsuarios = listaUsuariosAux;
        this.onUsuarioClickListener =onItemClickListener;
    }

    @NonNull
    @Override
    public UsuariosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_usuarios,viewGroup, false);
        UsuariosViewHolder holder= new UsuariosViewHolder(view, onUsuarioClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsuariosViewHolder viewHolder, int i) {

        //Seteo los valores en los diferentes controles
        viewHolder.txtApellidos.setText(listaUsuarios.get(i).getApellidos()+ " "+ listaUsuarios.get(i).getNombres());
        viewHolder.txtUsuario.setText(listaUsuarios.get(i).getUsuario());
        viewHolder.txtRol.setText(listaUsuarios.get(i).getIdRolUsuarioCentro().getIdRol().getRol());

    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }



    public class UsuariosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtUsuario, txtApellidos,txtRol;
        UsuarioOnItemClickListener usuarioOnItemClickListener;

        public UsuariosViewHolder(View itemView, UsuarioOnItemClickListener onItemClickListener){
            super(itemView);
            txtUsuario=itemView.findViewById(R.id.txtUserUsuario);
            txtApellidos=itemView.findViewById(R.id.txtApellidosUsuario);
            txtRol=itemView.findViewById(R.id.txtRol);

            this.usuarioOnItemClickListener =onItemClickListener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            usuarioOnItemClickListener.OnItemClick(getAdapterPosition());
        }
    }

    public interface UsuarioOnItemClickListener {

        void OnItemClick(int posicion);

    }
}
