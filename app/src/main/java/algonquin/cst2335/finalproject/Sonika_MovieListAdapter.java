package algonquin.cst2335.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.Movie_Database.Movie;

public class Sonika_MovieListAdapter extends RecyclerView.Adapter<Sonika_MovieListAdapter.MovieViewHolder> {

    /**
     * declartion of varibles
     */
    private static ClickListener mClickHandler;
    private List<Movie> modelList = new ArrayList<>();

    /**
     * interface
     */
    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    /**
     * @param clickListener
     */
    public void setOnItemClickListener(ClickListener clickListener) {
        Sonika_MovieListAdapter.mClickHandler = clickListener;
    }

    public Sonika_MovieListAdapter() {
    }

    /**
     * @param parent   parent
     * @param viewType viewtype
     * @return movie list.
     */
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.item_movie;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieViewHolder(view);
    }

    /**
     * @param holder   adapter holder
     * @param position index for the list
     */

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.titleTV.setText(modelList.get(position).getTitle());
    }

    /**
     * @return list size.
     */
    @Override
    public int getItemCount() {
        return modelList.size();

    }

    /**
     * which also notify the adapter when list get affected.
     *
     * @param model
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setModelForList(List<Movie> model) {
        modelList = model;
        notifyDataSetChanged();
    }

    /**
     * movie holder for the recycleview item selection.
     */

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTV;

        /**
         * bind the adapter items id
         *
         * @param itemView itemview
         */
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.tvRVTitle);
            itemView.setOnClickListener(this);
        }

        /**
         * it will call when user click on the recyclerview item selected
         *
         * @param v view
         */

        @Override
        public void onClick(View v) {
            mClickHandler.onItemClick(getAdapterPosition(), v);
        }
    }
}
