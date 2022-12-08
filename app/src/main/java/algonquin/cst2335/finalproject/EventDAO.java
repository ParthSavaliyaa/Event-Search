package algonquin.cst2335.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


@Dao
/** interface to Add and remove from/to database
 */
public interface EventDAO {
    @Insert
    /** to insert data to favourite table
     */
    public void insertFav(Event e);

    @Delete
    /**to remove data to favourite table
     */
    public void deleteFav(Event e);

    /** to delete all data to favourite table
     */
    @Query("Delete from Event")
    void deleteAll();
}
