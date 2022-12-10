package algonquin.cst2335.finalproject.Network;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sonika_Rating implements Serializable, Parcelable {

    public final static Creator<Sonika_Rating> CREATOR = new Creator<Sonika_Rating>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Sonika_Rating createFromParcel(android.os.Parcel in) {
            return new Sonika_Rating(in);
        }

        public Sonika_Rating[] newArray(int size) {
            return (new Sonika_Rating[size]);
        }

    };

    public Sonika_Rating() {
    }

    private final static long serialVersionUID = 1197254135480892267L;
    @SerializedName("Source")
    @Expose
    private String source;
    @SerializedName("Value")
    @Expose
    private String value;

    protected Sonika_Rating(android.os.Parcel in) {
        this.source = ((String) in.readValue((String.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Sonika_Rating withValue(String value) {
        this.value = value;
        return this;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Sonika_Rating withSource(String source) {
        this.source = source;
        return this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Sonika_Rating.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("source");
        sb.append('=');
        sb.append(((this.source == null) ? "<null>" : this.source));
        sb.append(',');
        sb.append("value");
        sb.append('=');
        sb.append(((this.value == null) ? "<null>" : this.value));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(source);
        dest.writeValue(value);
    }

    public int describeContents() {
        return 0;
    }
}