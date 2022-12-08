package algonquin.cst2335.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

/** Search Fragment
 */
public class SearchFragment extends Fragment {
    private EditText distance, location;
    private Button search, clear;

    /** SearchFragment
     */
    public SearchFragment() {
        // Required empty public constructor
    }

    /** onViewCreated
     * @param view view
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        distance = getView().findViewById(R.id.distance);
        location = getView().findViewById(R.id.location);
        search = getView().findViewById(R.id.search);
        clear = getView().findViewById(R.id.clear);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(getContext(), "Validation Success", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getContext(), SearchResults.class);
                bundle.putString("location", location.getText().toString());
                bundle.putString("distance", distance.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location.setText("");
                distance.setText("10");
            }
        });
    }

    /** onResume function to store the location or city name
     */
    @Override
    public void onResume() {
        super.onResume();
        location.setText(location.getText());
        distance.setText(distance.getText());
    }

    /** onCreateView
     * @param inflater inflater
     * @param container container
     * @param  savedInstanceState savedInstanceState
     * @return Viewgroup
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return (ViewGroup) inflater.inflate(R.layout.fragment_search, container, true);
    }


}