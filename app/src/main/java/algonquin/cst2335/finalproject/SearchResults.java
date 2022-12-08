package algonquin.cst2335.finalproject;

import static java.net.URLEncoder.encode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/** Result class to search result
 */
public class SearchResults extends AppCompatActivity {

    String location, distance;
    private ArrayList<Event> eventList;
    private EventAdapter eventAdapter;
    private RecyclerView recyclerView;
    private FavoriteService favoriteService;
    String url = null;
    EventDAO eDao;

    /** public constuctor
     */
    public SearchResults() {
    }

    /** onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        location = getIntent().getExtras().getString("location");
        distance = getIntent().getExtras().getString("distance");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.Black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\'black\'>" + "Search Results" + "</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     getSupportActionBar();


        eventList = new ArrayList<>();
        recyclerView = findViewById(R.id.searchRecyclerView);


        RequestQueue requestQueue = Volley.newRequestQueue(this);


        url = "https://app.ticketmaster.com/discovery/v2/events.json?apikey=GCLqYdIHAAQ0WWt0L4ktTAh7hGjcaTGW&city=" + location + "&radius" + distance;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                response -> {

                    try {

                        // JSONObject Data=bundle.
                        JSONObject embedded = response.getJSONObject("_embedded");
                        JSONArray events = embedded.getJSONArray("events");

                        // Log.d("ddddddd", String.valueOf(events.length()));
                        for (int i = 0; i < events.length(); i++) {
                            JSONObject position0 = events.getJSONObject(i);
                            String name = position0.getString("name");
                            String id = position0.getString("id");
                            Log.d("name", name);
                            JSONObject Date = position0.getJSONObject("dates");
                            JSONObject dates = Date.getJSONObject("start");
                            String startDate = dates.getString("localDate");
                            String urlString = position0.getString("url");
                            JSONArray imgArray = position0.getJSONArray("images");


                            JSONObject img1 = imgArray.getJSONObject(new Random().nextInt(imgArray.length()));
                            String imgUrl = img1.getString("url");
                            JSONArray priceRange = position0.getJSONArray("priceRanges");
                            JSONObject position1 = priceRange.getJSONObject(0);
                            double min = position1.getDouble("min");
                            double max = position1.getDouble("max");
                            Log.d("cc", name);

                            eventList.add(new Event(id, name, startDate, min, max, imgUrl, urlString));
                            Log.d("eventlist", "eventList");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    buildRecyclerView();
                },
                error -> {
                    Log.d("error", "helllooo");
                    //Toast.makeText(SearchResults.this, error.toString().substring(200), Toast.LENGTH_SHORT).show();
                });

        requestQueue.add(jsonObjectRequest);

    }

    /** to assign menu toolbar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.parth_menu, menu);
        return true;
    }

    /** onOptionsItemSelected
     * @param item item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
            case R.id.about:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.drawable.info_outline);
                builder.setCancelable(true);
                builder.setTitle("Info");
                builder.setMessage("Version 1.0 By Parth Savaliya");
                builder.show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    /** build recyclerVIew
     */
    private void buildRecyclerView () {
            eventAdapter = new EventAdapter(eventList, SearchResults.this, false);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            //   recyclerView.setHasFixedSize(f);

            recyclerView.setLayoutManager(linearLayoutManager);

            recyclerView.setAdapter(eventAdapter);
        }

    /** onBackPressed
     */
        @Override
        public void onBackPressed () {
            super.onBackPressed();
            this.finish();
        }
    }