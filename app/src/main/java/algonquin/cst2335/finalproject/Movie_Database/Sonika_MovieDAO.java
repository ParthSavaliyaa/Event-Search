package algonquin.cst2335.finalproject.Movie_Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
/**
 * This is the DAO interface which is used for insert an remove the data from database.
 */
public interface Sonika_MovieDAO {

    /**
     * it will return fav list from the movie table.
     *
     * @return favouritelist
     */
    @Query("Select * from Sonika_Movie")
    List<Sonika_Movie> getFavouriteList();


    /**
     * @param plot plot
     * @return remove list which store the remove data from table
     */
    @Query("DELETE FROM Sonika_Movie WHERE plot = :plot")
    int removeFavourite(String plot);

    /**
     * @param movie movie
     * @return insert record into movie
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addFavourite(Sonika_Movie movie);

    /**
     * @param plot plot
     * @return specific record form table which you want from the favourite list.
     */
    @Query("SELECT * FROM Sonika_Movie WHERE plot = :plot")
    int isDataExist(String plot);

    /**
     * remove the data from the table
     */
    @Query("DELETE FROM Sonika_Movie")
    void delete();


}
