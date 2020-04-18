package br.com.miguelwolf.controletreinamentopessoal.persistencia;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import br.com.miguelwolf.controletreinamentopessoal.model.Treino;

@Database(entities = {Treino.class}, version = 1, exportSchema = false)
public abstract class TreinoDatabase extends RoomDatabase {

    public abstract TreinoDAO treinoDAO();

    private static TreinoDatabase instance;

    public static TreinoDatabase getDatabase(final Context context) {
        if (instance == null) {

            synchronized (TreinoDatabase.class) {

                if (instance == null) {
                    instance = Room.databaseBuilder(context,
                            TreinoDatabase.class,
                            "treino.db").allowMainThreadQueries().build();
                }

            }

        }

        return instance;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
