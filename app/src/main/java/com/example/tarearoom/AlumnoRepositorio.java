package com.example.tarearoom;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
La clase AlumnoRepositorio necesitará un objeto
AlumnoDao para poder ejecutar los métodos obtener(), insertar(),
actualizar() y eliminar().
*/
public class AlumnoRepositorio {

    //List<Alumno> elementos = new ArrayList<>();
    AlumnoDao alumnoDao;
    //Las operacions update, insert, delete se deben hacer en segundo plano, por ello creamos un objeto EXECUTOR
    Executor executor = Executors.newSingleThreadExecutor();
    public AlumnoRepositorio(Application application)
    {
        alumnoDao = AlumnoDatabase.obtenerInstancia(application).getAlumnoDao();
    }
    public AlumnoRepositorio(){
    }
    public LiveData<List<Alumno>> obtener() {
        return alumnoDao.getAlumno();
    }
    /*
    Debido a que estos cambios sobre los datos se reflejarán
    automáticamente en el LiveData de la consulta SELECT,
    ya no es necesario el callback para retornar la lista resultante.
    En la clase ViewModel tambien elimino los CallBacks
    */
    void insertar(Alumno alumno){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                alumnoDao.addAlumno(alumno);
            }
        });
    }
    void eliminar(Alumno alumno) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                alumnoDao.deleteAlumno(alumno);
            }
        });
    }
    public void actualizar(Alumno a, String nombre, float nota) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                a.setNombre(nombre);
                a.setNota(nota);
                alumnoDao.updateAlumno(a);
            }
        });
    }


}
