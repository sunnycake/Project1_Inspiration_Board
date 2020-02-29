package com.mynuex.project1_inspiration_board;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface InspirationDao {

    @Insert
    void insert(Inspiration inspiration);

    @Update
    void update(Inspiration inspiration);

    @Delete
    void delete(Inspiration inspiration);

    @Query("DELETE FROM novel_inspiration_board WHERE id = :id" )
    void deleteById(int id);

    @Query("SELECT * FROM novel_inspiration_board ORDER BY UPPER(title) ASC")
    LiveData<List<Inspiration>> getAllInspirations();


}
