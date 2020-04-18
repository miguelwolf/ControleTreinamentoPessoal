package br.com.miguelwolf.controletreinamentopessoal.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import br.com.miguelwolf.controletreinamentopessoal.model.Treino;

@Dao
public interface TreinoDAO {

    @Insert
    long insert(Treino treino);

    @Delete
    void delete(Treino treino);

    @Update
    void update(Treino treino);

    @Query("SELECT * FROM treino WHERE id = :id")
    Treino queryForId(long id);

    @Query("SELECT * FROM treino ORDER BY nome ASC")
    List<Treino> queryAll();

}
