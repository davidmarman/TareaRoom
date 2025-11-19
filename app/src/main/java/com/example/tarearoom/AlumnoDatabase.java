package com.example.tarearoom;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Alumno.class}, version = 1)
public abstract class AlumnoDatabase extends RoomDatabase{

    private static volatile AlumnoDatabase INSTANCIA;
    public abstract AlumnoDao getAlumnoDao();

    public static AlumnoDatabase obtenerInstancia(final Context context) {
        if (INSTANCIA == null) {
            synchronized (AlumnoDatabase.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(context, AlumnoDatabase.class, "alumnos.db").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCIA;
    }

}
