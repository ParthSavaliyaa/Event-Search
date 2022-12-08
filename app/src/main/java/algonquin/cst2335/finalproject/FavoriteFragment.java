package algonquin.cst2335.finalproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    private EventAdapter eventAdapter;
    private FavoriteService favoriteService;
    private ArrayList<Event> favoritesArray;
    private RecyclerView favoriteRecyclerView;

    /** onViewCreated method
     * @param view view
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /** onCreateView method that return the view of the favourte fragment
     * @param inflater inflater
     * @param container container
     * @param savedInstanceState savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        favoriteRecyclerView = view.findViewById(R.id.favoriteRecyclerView);
        favoriteService = new FavoriteService(getContext());
        buildRecyclerView();
        return view;
    }

    /** Build Recycler view
     */
    private void buildRecyclerView(){
        favoritesArray = favoriteService.getFavoritesArray();
        //Toast.makeText(getContext(), favoritesArray.toString(), Toast.LENGTH_SHORT).show();
        eventAdapter = new EventAdapter(favoritesArray,getContext(),true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        favoriteRecyclerView.setLayoutManager(linearLayoutManager);

        favoriteRecyclerView.setAdapter(eventAdapter);
    }

    /** to Onresume method that create recycler view again
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.i("FAV","onResume");
        buildRecyclerView();
    }

}