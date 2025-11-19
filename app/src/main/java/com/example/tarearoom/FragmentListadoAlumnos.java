package com.example.tarearoom;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomSQLiteQuery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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





        // crear el Adaptador
        ElementosAdapter elementosAdapter = new ElementosAdapter();

        // asociar el Adaptador con el RecyclerView
        binding.recyclerView.setAdapter(elementosAdapter);

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
            holder.binding.etNota.setText((int) elemento.nota);

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