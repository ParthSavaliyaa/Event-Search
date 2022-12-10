package algonquin.cst2335.finalproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import algonquin.cst2335.finalproject.Movie_Database.Sonika_Movie;
import algonquin.cst2335.finalproject.Movie_Database.Sonika_MovieDAO;
import algonquin.cst2335.finalproject.Movie_Database.Sonika_MovieDataBase;
import algonquin.cst2335.finalproject.Network.SONIKA_API;
import algonquin.cst2335.finalproject.Network.Sonika_Model;
import algonquin.cst2335.finalproject.databinding.SonikaActivityMainBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sonika_Main_Activity extends AppCompatActivity {

    SonikaActivityMainBinding binding;
    Sonika_MovieDAO movieDAO;
    private Sonika_MovieListAdapter movieListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * bind the IDs.
         */
        binding = SonikaActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /**
         * set title for the toolbar.
         */
        setTitle(R.string.soniak_app_name);

        /**
         * database fro storing the data.
         */
        movieDAO = Sonika_MovieDataBase.getInstance(this).movieDAO();

        /**
         * set the adapter for the list of item to show.
         */
        movieListAdapter = new Sonika_MovieListAdapter();
        /**
         * adapter click listener which store the required field in sting which pass in another class called
         * MovieDetails.
         * where these click listerner showed.
         */
        movieListAdapter.setOnItemClickListener(new Sonika_MovieListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(getApplicationContext(), Sonika_MovieDetails.class);
                Sonika_Movie movie = movieDAO.getFavouriteList().get(position);
                intent.putExtra("id", movie.getId());
                intent.putExtra("title", movie.getTitle());
                intent.putExtra("year", movie.getYear());
                intent.putExtra("rating", movie.getRating());
                intent.putExtra("runtime", movie.getRuntime());
                intent.putExtra("actors", movie.getActors());
                intent.putExtra("plot", movie.getPlot());
                intent.putExtra("poster", movie.getUrl());
                startActivity(intent);
            }
        });

        /**
         * set the recycle layout fot show the added list of data
         */

        binding.recyclerviewSaved.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        binding.recyclerviewSaved.setLayoutManager(new LinearLayoutManager(this));


        /**
         * get the edittext text which you want to search
         */

        binding.movieEditTxt.getText();

        /**
         * search button click listener.
         */
        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * store edittext text into string.
                 */
                String title = binding.movieEditTxt.getText().toString();

                /**
                 * if given stored string is not null than it will pops up the required fields.
                 * all these required field show on another activity called movieDetails.
                 */
                if (!title.isEmpty()) {
                    SONIKA_API.Factory.getInstance().listSearchedMovies(title).enqueue(new Callback<Sonika_Model>() {
                        @Override
                        public void onResponse(Call<Sonika_Model> call, @NonNull Response<Sonika_Model> response) {
                            Intent intent = new Intent(getApplicationContext(), Sonika_MovieDetails.class);
                            intent.putExtra("title", response.body().getTitle());
                            intent.putExtra("year", response.body().getYear());
                            if (response.body().getRatings().size() > 0) {
                                intent.putExtra("rating", response.body().getRatings().get(0).getValue());
                            }
                            intent.putExtra("runtime", response.body().getRuntime());
                            intent.putExtra("actors", response.body().getActors());
                            intent.putExtra("plot", response.body().getPlot());
                            intent.putExtra("poster", response.body().getPoster());
                            startActivity(intent);
                        }

                        /**
                         *  if interconnection is not available it will show the network error in snackbar.
                         * @param call onfailure msg
                         * @param t throwable parameter.
                         */
                        @Override
                        public void onFailure(Call<Sonika_Model> call, Throwable t) {
                            Snackbar.make(view, R.string.network_toast, Snackbar.LENGTH_LONG).show();
                        }
                    });

                    /**
                     * if user direcr tap on search button it will toast the message of empty field
                     */
                } else {
                    Toast.makeText(Sonika_Main_Activity.this, R.string.edittext_toast, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * it will create the sharedpreferences once you leave one activity it will store the data on that field.
     */
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("Shared_Pref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("Search_Movie_text", binding.movieEditTxt.getText().toString());
        myEdit.commit();
    }

    /**
     * @param outState
     * @param outPersistentState
     */

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("edit_Text", binding.movieEditTxt.getText().toString());
    }

    /**
     * @param savedInstanceState store the text
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        binding.movieEditTxt.setText(savedInstanceState.getString("edit_Text"));
    }

    /**
     * it will store the shared preference data as well as call the recycler adapter to how the list.
     */

    @Override
    protected void onResume() {
        super.onResume();
        List<Sonika_Movie> moviesList = movieDAO.getFavouriteList();
        movieListAdapter.setModelForList(moviesList);
        binding.recyclerviewSaved.setAdapter(movieListAdapter);
        SharedPreferences sh = getSharedPreferences("Shared_Pref", Context.MODE_PRIVATE);
        String text = sh.getString("Search_Movie_text", "");
        binding.movieEditTxt.setText(text);
    }

    /**
     * @param menu show menu
     * @return menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sonika_menu, menu);
        return true;
    }

    /**
     * @param item show items
     * @return item
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        /**
         * help menu
         */

        switch (item.getItemId()) {

            case R.id.help:
                Dialog dialog = new Dialog(Sonika_Main_Activity.this);
                dialog.setContentView(R.layout.alert_dialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(true);

                TextView txt1, txt2, txt3, txt4, txt5, txt6;
                Button Btn_ok;
                txt1 = dialog.findViewById(R.id.txt1);
                txt2 = dialog.findViewById(R.id.txt2);
                txt3 = dialog.findViewById(R.id.txt3);
                txt4 = dialog.findViewById(R.id.txt4);
                txt5 = dialog.findViewById(R.id.txt5);
                txt6 = dialog.findViewById(R.id.txt6);
                Btn_ok = dialog.findViewById(R.id.Btn_ok);
                txt1.setText(R.string.dialog_txt1);
                txt2.setText(R.string.dialog_txt2);
                txt3.setText(R.string.dialog_txt3);
                txt4.setText(R.string.dialog_txt4);
                txt5.setText(R.string.dialog_txt5);
                txt6.setText(R.string.dialog_txt6);


                Btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
                return true;


            case R.id.about:
                AlertDialog alertDialog1 = new AlertDialog.Builder(Sonika_Main_Activity.this).create();
                alertDialog1.setTitle(getString(R.string.about));
                alertDialog1.setMessage(getString(R.string.author) + "\n" + getString(R.string.version));
                alertDialog1.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog1.show();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}