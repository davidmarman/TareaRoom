package com.example.tarearoom;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AlumnoViewModel extends AndroidViewModel {

    AlumnoRepositorio alumnoRepositorio;


    public AlumnoViewModel(@NonNull Application application) {
        super(application);

        //Inicializo la lista de alumnos
        // se le debe pasar la referencia de la aplicación cuando seintancia el listado
        alumnoRepositorio = new AlumnoRepositorio(application);
    }

    public LiveData<List<Alumno>> obtener()
    {
        return alumnoRepositorio.obtener();
    }
    void insertar(Alumno a){
    //llama al metodo insertar del modelo y ademas al tener el interface callback
    //necesita que implementemos los metodos de dicho interfaz en este caso notificarcambios
        alumnoRepositorio.insertar(a);
    }
    void eliminar(Alumno a){
        alumnoRepositorio.eliminar(a);
    }
    void actualizar(Alumno elemento, String nombre, float nota){
        alumnoRepositorio.actualizar(elemento, nombre, nota);
    }
}
