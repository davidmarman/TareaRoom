package com.example.tarearoom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tarearoom.databinding.FragmentNuevoAlumnoBinding;


public class NuevoAlumno extends Fragment {

    private FragmentNuevoAlumnoBinding binding;
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
        return (binding = FragmentNuevoAlumnoBinding.inflate(inflater,container,false)).getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AlumnoViewModel.class);
        navController = Navigation.findNavController(view);

        binding.botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = binding.textoNombre.getText().toString();
                String notaString = binding.textoNota.getText().toString();

                //Validamos que los campos no esten vacios
                if (nombre.isEmpty()){
                    Toast.makeText(getContext(), "Añade un nombre", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(notaString.isEmpty()){
                    Toast.makeText(getContext(), "Añade una nota", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validamos que sea formato correcto
                float nota;
                try{
                    nota = Float.parseFloat(notaString);

                }catch (NumberFormatException e){
                    Toast.makeText(getContext(), "Formato de numero no valido", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Validamos que este entre 0 y 10
                if(nota < 0 || nota > 10){
                    Toast.makeText(getContext(), "La nota tiene que estar entre 0 y 10", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Hemos pasado todas las validaciones
                alumno = new Alumno(nombre,nota);
                viewModel.insertar(alumno);

                navController.popBackStack();

            }
        });

        binding.botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });


    }
}