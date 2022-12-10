package algonquin.cst2335.finalproject.Movie_Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie")
public class Sonika_Movie {


    /**
     * constructor
     *
     * @param title   title
     * @param year    year
     * @param rating  rating
     * @param runtime runtime
     * @param actors  actors
     * @param plot    plot
     * @param url     URL
     */
    public Sonika_Movie(String title, String year, String rating, String runtime, String actors, String plot, String url) {
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.runtime = runtime;
        this.actors = actors;
        this.plot = plot;
        this.url = url;
    }

    /**
     * id is primarykey in movie table
     */
    @PrimaryKey(autoGenerate = true)
    private long id;


    /**
     * get ID
     *
     * @return ID
     */
    public long getId() {
        return id;
    }


    /**
     * @param id set the ID
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return year
     */

    public String getYear() {
        return year;
    }


    /**
     * @return runtime
     */
    public String getRuntime() {
        return runtime;
    }

    /**
     * @return actors
     */
    public String getActors() {
        return actors;
    }

    /**
     * @return rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * @return plot
     */
    public String getPlot() {
        return plot;
    }


    /**
     * @return URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * URL column in table
     */
    @ColumnInfo(name = "url")
    private String url;
    /**
     * year column in table
     */
    @ColumnInfo(name = "year")
    private String year;

    /**
     * rating column in table
     */
    @ColumnInfo(name = "rating")
    private String rating;

    /**
     * Runtime column in table
     */
    @ColumnInfo(name = "runtime")
    private String runtime;


    /**
     * actors  column in table
     */
    @ColumnInfo(name = "actors")
    private String actors;


    /**
     * Title column in table
     */
    @ColumnInfo(name = "title")
    private String title;

    /**
     * plot column in table
     */
    @ColumnInfo(name = "plot")
    private String plot;


}
