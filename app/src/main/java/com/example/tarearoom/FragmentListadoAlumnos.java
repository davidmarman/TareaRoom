package com.example.tarearoom;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomSQLiteQuery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tarearoom.databinding.FragmentListadoAlumnosBinding;
import com.example.tarearoom.databinding.ViewholderAlumnoBinding;

import java.util.List;
import java.util.zip.Inflater;


public class FragmentListadoAlumnos extends Fragment {

    private FragmentListadoAlumnosBinding binding;
    private AlumnoViewModel viewModel;
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (binding = FragmentListadoAlumnosBinding.inflate(inflater,container,false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AlumnoViewModel.class);

        navController = Navigation.findNavController(view);


        binding.irANuevoAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nuevoAlumno);
            }
        });


        // crear el Adaptador
        ElementosAdapter elementosAdapter = new ElementosAdapter();

        // asociar el Adaptador con el RecyclerView
        binding.recyclerView.setAdapter(elementosAdapter);

        viewModel.obtener().observe(getViewLifecycleOwner(), new Observer<List<Alumno>>() {
            @Override
            public void onChanged(List<Alumno> alumnos) {
                elementosAdapter.establecerLista(alumnos);
            }
        });

    }



    public class ElementosAdapter extends RecyclerView.Adapter<AlumnoViewHolder> {

        List<Alumno> elementos;

        @NonNull
        @Override
        public AlumnoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FragmentListadoAlumnos.AlumnoViewHolder(ViewholderAlumnoBinding.inflate(getLayoutInflater(),parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull AlumnoViewHolder holder, int position) {

            Alumno elemento = elementos.get(position);

            holder.binding.etNombre.setText(elemento.nombre);
            holder.binding.etNota.setText(String.valueOf(elemento.nota));
            holder.binding.btnBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteConfirmationDialog(getContext(),elemento);
                }
            });


            holder.binding.btnModificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Enviar el alumno para editar en un bundle al nuevo fragmento
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("alumnoSeleccionado",elemento);

                    navController.navigate(R.id.modificarAlumnoFr,bundle);
                }
            });
        }

        /*
         * Muestra el diálogo de confirmación antes de borrar una tarea.
         */
        private void showDeleteConfirmationDialog(Context context, Alumno a) {
            new AlertDialog.Builder(context)
                    // Título del diálogo
                    .setTitle("Confirmar Borrado")
                    // Pregunta de comprobación
                    .setMessage("¿Está seguro de borrar este alumno: \"" +
                            a.getNombre() + "\"? Esta acción es irreversible.")
                    // Botón de confirmación (Positivo)
                    .setPositiveButton("Sí, Borrar", (dialog, which) -> {
                        // Si el usuario hace clic en "Sí, Borrar",ejecutamos la acciónalumnoViewModel.eliminar(a);
                        viewModel.eliminar(a);
                        Toast.makeText(context, "Alumno '" + a.getNombre() +
                                "' eliminado.", Toast.LENGTH_SHORT).show();
                    })
                    // Botón de cancelación (Negativo)
                    .setNegativeButton("Cancelar", (dialog, which) -> {
                        // Si el usuario hace clic en "Cancelar", simplementese cierra el diálogo
                        dialog.dismiss();
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert) // Icono de alerta
                    .show();
        }

        @Override
        public int getItemCount() {
            return elementos != null ? elementos.size() : 0;
        }

        public void establecerLista(List<Alumno> elementos) {
            this.elementos = elementos;
            notifyDataSetChanged();
        }


    }

    public class AlumnoViewHolder extends RecyclerView.ViewHolder{
        final ViewholderAlumnoBinding binding;

        public AlumnoViewHolder(ViewholderAlumnoBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}