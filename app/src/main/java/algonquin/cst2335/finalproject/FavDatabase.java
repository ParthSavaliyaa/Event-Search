package algonquin.cst2335.finalproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Event.class}, version=1)
/** Fav database that has abstract method and extends the room database
 */
public abstract class FavDatabase  extends RoomDatabase {
    public abstract EventDAO cmDAO();
}

