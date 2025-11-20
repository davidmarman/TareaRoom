package com.example.tarearoom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tarearoom.databinding.FragmentModificarAlumnoBinding;


public class ModificarAlumnoFr extends Fragment {

    private FragmentModificarAlumnoBinding binding;
    private NavController navController;
    private AlumnoViewModel viewModel;
    private Alumno alumno;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (binding = FragmentModificarAlumnoBinding.inflate(inflater,container,false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AlumnoViewModel.class);

        navController = Navigation.findNavController(view);

        // Recuperamos el id del Alumno que nos ha llegado por el bundle
        if(getArguments() != null){
            alumno = (Alumno) getArguments().getSerializable("alumnoSeleccionado");
        }

        if(alumno!= null){
            binding.editNombre.setText(alumno.nombre);
            binding.editNota.setText(String.valueOf(alumno.nota));
        }

        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCambios();
                navController.popBackStack();
            }
        });

        binding.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });

    }

    private void guardarCambios(){
        String nuevoNombre = binding.editNombre.getText().toString();
        String nuevaNotaString = binding.editNota.getText().toString();
        float nuevaNota = Float.parseFloat(nuevaNotaString);

        if (nuevoNombre.isEmpty() || nuevaNotaString.isEmpty()){
            Toast.makeText(getContext(),"Faltan Datos",Toast.LENGTH_SHORT).show();
            return;
        }

        // Ponemos los nuevos valroes al alumno

        viewModel.actualizar(alumno,nuevoNombre,nuevaNota);

        // notificamos de la actualizacion
        Toast.makeText(getContext(),"Alumno Actualizado",Toast.LENGTH_SHORT).show();
    }
}