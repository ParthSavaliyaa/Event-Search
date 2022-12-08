package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import algonquin.cst2335.finalproject.Movie_Database.Movie;
import algonquin.cst2335.finalproject.Movie_Database.MovieDAO;
import algonquin.cst2335.finalproject.Movie_Database.MovieDataBase;
import algonquin.cst2335.finalproject.databinding.MovieDetailsBinding;

public class Sonika_MovieDetails extends AppCompatActivity {

    /**
     * declaration of variable
     */
    MovieDetailsBinding binding;
    Menu fav;
    List<Movie> favMoviesList;
    private boolean isFavourite = false;
    MovieDAO movieDAO;
    private Movie movieObject;
    private String title;
    private String year;
    private String rating;
    private String runtime;
    private String actors;
    private String plot;
    private String url;
    private String id;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MovieDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitle(R.string.soniak_app_name);
        movieDAO = MovieDataBase.getInstance(this).movieDAO();
        favMoviesList = movieDAO.getFavouriteList();

        /**
         * getExtras for the getting the clicked data on next activity.
         */
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            id = bundle.getString("id", "");
            title = bundle.getString("title");
            binding.tvTitle.setText(title);
            year = bundle.getString("year");
            binding.tvYear.setText(year);
            rating = bundle.getString("rating", "");
            binding.tvRating.setText(rating);
            runtime = bundle.getString("runtime");
            binding.tvRuntime.setText(runtime);
            actors = bundle.getString("actors");
            binding.tvMainActors.setText(actors);
            plot = bundle.getString("plot");
            binding.tvPlot.setText(plot);
            url = bundle.getString("poster");
            movieObject = new Movie(title, year, rating, runtime, actors, plot, url);

            /**
             * used the glide library for load the image also piccso works same.
             */
            Glide
                    .with(this)
                    .load(url)
                    .centerCrop()
                    .into(binding.ivPoster);

        }

        checkIfMovieFavourited(movieObject);
    }


    /**
     * @param featureId featureId
     * @param menu  menu
     * @return menu
     */
    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        super.onCreatePanelMenu(featureId, menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_fav, menu);
        this.fav = menu;
        updateIcon();
        return true;
    }

    /**
     * @param item item
     * @return item
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fav:
                item.setCheckable(false);
                if (!isFavourite) {
                    addMovieTOFav();
                    Snackbar.make(findViewById(android.R.id.content), R.string.movie_added_toast, Snackbar.LENGTH_LONG).show();
                } else {
                    removeMovieFromFav();
                    Snackbar.make(findViewById(android.R.id.content), R.string.movie_remove_toast, Snackbar.LENGTH_LONG).show();
                }
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * it will identify the fav and unfav list
     */
    private void addMovieTOFav() {
        checkIfMovieFavourited(movieObject);
        if (!isFavourite) {
            long insertId = movieDAO.addFavourite(movieObject);
            isFavourite = true;
            updateIcon();
            movieObject.setId(insertId);
        }

    }


    /**
     * it will used for the fill the icon when user click on the add button for add the moviw into the list.
     */
    private void updateIcon() {
        if (isFavourite) {
            fav.findItem(R.id.fav).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_fill));
        } else {
            fav.findItem(R.id.fav).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_unfill));
        }
    }

    /**
     * Remove from db
     */
    private void removeMovieFromFav() {
        long id = movieObject.getId();
        int val = movieDAO.removeFavourite(movieObject.getPlot());
        isFavourite = false;
        updateIcon();
    }

    /**
     * Check if the entry is available in DATABASE
     *
     * @param movieObject
     */
    private void checkIfMovieFavourited(Movie movieObject) {
        long id = movieObject.getId();
//        for(Movie x: favMoviesList) {
//            isFavourite = Objects.equals(x.getId(), movieObject.getId());
//        }
        if (movieDAO.isDataExist(movieObject.getPlot()) == 0) { //if 0 = data does not exist in table
            isFavourite = false;
        } else {
            isFavourite = true;
        }
    }
}