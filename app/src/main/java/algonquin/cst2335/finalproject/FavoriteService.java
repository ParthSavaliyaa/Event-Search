package algonquin.cst2335.finalproject;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/** favourite class to manage the faviourite list
 */
public class FavoriteService {
    public ArrayList<Event> favorites;
    SharedPreferences sharedPreferences;
    Context context;

    SharedPreferences.Editor editor;
    Gson gson;

    /** faviouriteService method
     * @param context
     */
    public FavoriteService(Context context){
        this.sharedPreferences = context.getSharedPreferences("myPref",context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
        this.context = context;
        String defaultSet = "[]";
        gson = new Gson();
        this.favorites = gson.fromJson(this.sharedPreferences.getString("favorites",defaultSet),
                new TypeToken<ArrayList<Event>>(){}.getType());
    }

    /** update method to update the list
     */
    public void updateSet(){
        String defaultSet = "[]";
        this.favorites = gson.fromJson(this.sharedPreferences.getString("favorites",defaultSet),
                new TypeToken<ArrayList<Event>>(){}.getType());
    }

    /** Update SharedPreferences
     */
    public void updateSharedPreferences(){
        editor.putString("favorites",gson.toJson(this.favorites));
        editor.commit();
    }

    /** push the event to the list
     * @param event event
     */
    public void push(Event event){
        if(!isPresent(event)){
            this.favorites.add(event);
        }
        updateSharedPreferences();
        //Toast.makeText(context, this.favorites.toString(), Toast.LENGTH_SHORT).show();
    }

    /** remove the event to list
     * @param event event
     */
    public void remove(Event event){
        if(!this.favorites.isEmpty() && isPresent(event)){
            this.favorites.remove(event);
        }
        updateSharedPreferences();
        //Toast.makeText(context, this.favorites.toString(), Toast.LENGTH_SHORT).show();
    }

    /** remove all from the favourite list
     */
    public void removeAll(){
        if(!this.favorites.isEmpty() ){
            this.favorites.removeAll(favorites);
        }
        updateSharedPreferences();
        //Toast.makeText(context, this.favorites.toString(), Toast.LENGTH_SHORT).show();
    }

    /** check the event is it in the list or not
     * @param event
     * @return
     */
    public boolean isPresent(Event event){
        if(this.favorites.contains(event)){
            return true;
        }
        else{
            return false;
        }
    }

    /** get the favourite list
     * @return
     */
    public ArrayList<Event> getFavoritesArray(){
        updateSet();
        return this.favorites;
    }

}
