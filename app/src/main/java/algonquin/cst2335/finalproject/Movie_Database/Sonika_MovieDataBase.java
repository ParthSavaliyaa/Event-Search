package algonquin.cst2335.finalproject.Movie_Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * this is a database.
 */
@Database(entities = {Sonika_Movie.class}, version = 1)
public abstract class Sonika_MovieDataBase extends RoomDatabase {

    /**
     * declaretion of the database class
     */
    private static Sonika_MovieDataBase instance_DB;

    public abstract Sonika_MovieDAO movieDAO();

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(Sonika_MovieDataBase instance) {
            Sonika_MovieDAO dao = instance.movieDAO();
        }

        /**
         * @param voids void
         * @return null value.
         */
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }



    /**
     * @param context context
     * @return instance
     */
    public static synchronized Sonika_MovieDataBase getInstance(Context context) {

        if (instance_DB == null) {

            instance_DB =
                    Room.databaseBuilder(context.getApplicationContext(),
                                    Sonika_MovieDataBase.class, "movie")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(roomCallback)
                            .build();
        }
        return instance_DB;
    }

    /**
     * callback method
     */
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance_DB).execute();
        }
    };

}

