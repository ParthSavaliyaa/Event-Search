package algonquin.cst2335.finalproject;

import static java.lang.Math.max;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/** Event adapter to perform a task on click listener of the any listed events
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private static final int VIEW_TYPE_EVENT = 0;
    private static final int VIEW_TYPE_EMPTY = 1;

    private ArrayList<Event> events;
    private Context context;
    private FavoriteService favoriteService;
    private boolean isFavoriteArray;
    int duration = Toast.LENGTH_SHORT;
    EventDAO eDao;
    FavDatabase db;

    /** Event adapter to set context and array list
     * @param events list of arrays
     * @param context context
     * @param isFavoriteArray isFavouriteaArray
     */
    public EventAdapter(ArrayList<Event> events, Context context, boolean isFavoriteArray) {
        this.events = events;
        this.context = context;
        this.favoriteService = new FavoriteService(context);
        this.isFavoriteArray = isFavoriteArray;
    }

    /** Get item view type
     * @param position position
     * @return viewType
     */
    @Override
    public int getItemViewType(int position) {
        if (events.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_EVENT;
        }
    }

    /** OncreateViewHolder
     * @param parent parent
     * @param viewType viewType
     * @return view
     */
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view;
        viewType = getItemViewType(0);
        if (viewType == VIEW_TYPE_EVENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        } else if (viewType == VIEW_TYPE_EMPTY && isFavoriteArray) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list_favorite, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list_search, parent, false);
        }

        return new ViewHolder(view);
    }

    /** onBindVIewholder
     * @param holder holder
     * @param position position
     */
    @Override
    public void onBindViewHolder(@NonNull @NotNull EventAdapter.ViewHolder holder, int position) {
        db = Room.databaseBuilder(context.getApplicationContext(), FavDatabase.class, "database-name").build();
        eDao = db.cmDAO();
        if (getItemViewType(0) == VIEW_TYPE_EVENT) {
            Event event = events.get(position);
            //holder.id.setText(event.id);
            holder.name.setText(event.getName());
            holder.startDate.setText(event.getStartDate());
            Picasso.get()
                    .load(event.getImgUrl())

                    .into(holder.imgView);
            holder.price.setText(event.getMin() + "$ - " + event.getMax() + "$");
            holder.likeButton.setImageResource(getLikeButtonResource(event));//placeholder
            holder.likeButton.setOnClickListener(v -> {
                if (favoriteService.isPresent(event)) {
                    holder.likeButton.setImageResource(R.drawable.heart_outline_black);
                   // Toast toast2 = Toast.makeText(context, event.getName() + "is successfully removed from faviourite list", duration);
                 //   toast2.show();
                    Event event1 = new Event(event.getId(), event.getName(), event.getStartDate(), event.getMin(), event.getMax(),
                            event.getImgUrl(), event.getUrlString());
                    if (isFavoriteArray) {
                        Executor thread = Executors.newSingleThreadExecutor();
                        favoriteService.remove(event);
                        thread.execute(() -> {
                            eDao.deleteFav(event);
                        });
                        events.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, events.size());

                    } else {
                        favoriteService.remove(event);

                        Executor thread = Executors.newSingleThreadExecutor();
                        thread.execute(() -> {
                            eDao.deleteFav(event);
                        });
                        Snackbar.make(v, R.string.snackbarToast, Snackbar.LENGTH_LONG).setAction(R.string.undoTxt, clkk -> {


                            Executor thread1 = Executors.newSingleThreadExecutor();

                            thread1.execute(() -> {
                                eDao.insertFav(event);
                              favoriteService.push(event);
                            });
                            holder.likeButton.setImageResource(R.drawable.heart_fill_red);

//                            notifyItemInserted(position);

                        }).show();
                    }
                } else {
                    favoriteService.push(event);
                    Event event1 = new Event(event.getId(), event.getName(), event.getStartDate(), event.getMin(), event.getMax(),
                            event.getImgUrl(), event.getUrlString());
                    Executor thread = Executors.newSingleThreadExecutor();
                    thread.execute(() -> {
                        eDao.insertFav(event);
                    });

                    Toast toast = Toast.makeText(context, (R.string.addToFavTxt), duration);
                    toast.show();
                    holder.likeButton.setImageResource(R.drawable.heart_fill_red);
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(v.getContext());
                    dialog.setContentView(R.layout.activity_event_details);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCancelable(true);
                    Button cancel = dialog.findViewById(R.id.popUp_cancel);
                    TextView name, date, URL, price;
                    ImageView img;
                    price = dialog.findViewById(R.id.popUp_Price);
                    date = dialog.findViewById(R.id.popUp_StartDate);
                    URL = dialog.findViewById(R.id.popUp_URL);
                    img = dialog.findViewById(R.id.popUp_image);
                    name = dialog.findViewById(R.id.popUp_name);
                    name.setText(event.getName());
                    price.setText(R.string.ticketPrice + event.getMin() + "$ -" + event.getMax() + "$");
                    date.setText(R.string.startDate + event.getStartDate());
                    URL.setText(R.string.ForMoreInfo + event.getUrlString());
                    Picasso.get()
                            .load(event.getImgUrl())

                            .into(img);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });

        }

    }

    /** getItemCOunts
     * @return int
     */
    @Override
    public int getItemCount() {
        return max(events.size(), 1);
    }

    /** To set the like button accordingly
     * @param event
     * @return
     */
    public int getLikeButtonResource(Event event) {
        if (favoriteService.isPresent(event)) {
            return R.drawable.heart_fill_red;
        } else {
            return R.drawable.heart_outline_black;
        }
    }

    /** ViewHolder CLasss to Set item
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, startDate, price, id;
        private ImageButton likeButton;
        private ImageView imgView;

        /**  View holder to bind the id
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.name);
            name = itemView.findViewById(R.id.emptyRecords);
            startDate = itemView.findViewById(R.id.startDate);
            price = itemView.findViewById(R.id.price);
            likeButton = itemView.findViewById(R.id.likeButton);
            imgView = itemView.findViewById(R.id.imgView);
        }
    }

}

