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
@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDataBase extends RoomDatabase {

    /**
     * declaretion of the database class
     */
    private static MovieDataBase instance;

    public abstract MovieDAO movieDAO();

    /**
     * @param context context
     * @return instance
     */
    public static synchronized MovieDataBase getInstance(Context context) {

        if (instance == null) {

            instance =
                    Room.databaseBuilder(context.getApplicationContext(),
                                    MovieDataBase.class, "movie")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(roomCallback)
                            .build();
        }
        return instance;
    }

    /**
     * callback method
     */
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(MovieDataBase instance) {
            MovieDAO dao = instance.movieDAO();
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
}
