package algonquin.cst2335.finalproject;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;
@Entity
/** Class to create the database table and store the data
 */
public class Event {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "startDate")
    private String startDate;
    @ColumnInfo(name = "urlString")
    private String urlString;
    @ColumnInfo(name = "imgUrl")
    private String imgUrl;
    @ColumnInfo(name = "min")
    private double min;
    @ColumnInfo(name = "max")
    private double max;

    /** Constructor
     * @param id id
     * @param name name
     * @param startDate startdDate
     * @param min min
     * @param max max
     * @param imgUrl imgUrl
     * @param urlString urlString
     */
    public Event(String id, String name, String startDate, double min, double max, String imgUrl, String urlString){
      this.name=name;
      this.startDate=startDate;
      this.id=id;
      this.min=min;
      this.max=max;
      this.imgUrl=imgUrl;
      this.urlString=urlString;
    }

    /*** setter for id
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /** getters for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /** getters for start date
     * @return start date
     */
    public String getStartDate() {
        return startDate;
    }

    /** getters for id
     * @return id
     */
    public String getId() {
        return id;
    }

    /** getters for urlString
     * @return urlString
     */
    public String getUrlString() {
        return urlString;
    }

    /** getters for imgUrl
     * @return imgUrl
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /** getters for min
     * @return min
     */
    public double getMin() {
        return min;
    }

    /** getters for max
     * @return max
     */
    public double getMax() {
        return max;
    }

    /** override method
     * @param o object
     * @return name
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event1 = (Event) o;
        return Objects.equals(name, event1.name);
    }

    /** hashCOde
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(id,name, startDate, startDate, min, max,imgUrl,urlString);
    }
}
