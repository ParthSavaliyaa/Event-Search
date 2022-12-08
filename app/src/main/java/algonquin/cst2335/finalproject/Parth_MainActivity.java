package algonquin.cst2335.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

/** Main activity
 */
public class Parth_MainActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 2;

    private ViewPager2 viewPager2;
    private TabLayout sfTabLayout;
    EventDAO eDao;
    FavoriteService favoriteService;
    private FragmentStateAdapter pagerAdapter;

    /** onCreateOptionMenu
     * @param menu menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.parth_menu,menu);

        return true;
    }

    /** onOptionsItemSelected
     * @param item item
     * @return menu
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.about:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.drawable.info_outline);
                builder.setCancelable(true);
                builder.setTitle(R.string.info);
                builder.setMessage("Version 1.0 By Parth Savaliya");
                builder.show();
                break;
            default:
                super.onOptionsItemSelected((MenuItem) item);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /** onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.parth_activity_main);

        getSupportActionBar().setTitle(R.string.parth_app_name );
        getSupportActionBar();
        viewPager2 = findViewById(R.id.viewpager); //Data Binding
        sfTabLayout = findViewById(R.id.sfTabLayout);
        pagerAdapter = new SearchFragmentPagerAdapter(this);
        viewPager2.setAdapter(pagerAdapter);

        new TabLayoutMediator(sfTabLayout, viewPager2, (tab, position) -> {
            tab.setText(position == 0 ? R.string.searchTxt : R.string.favTxt);
        }).attach();


        sfTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals(R.string.searchTxt)) {
                    viewPager2.setCurrentItem(0, true);
                } else if (tab.getText().equals(R.string.favTxt)) {
                    //Toast.makeText(MainActivity.this,tab.getText(), Toast.LENGTH_SHORT).show();
                    viewPager2.setCurrentItem(1, true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }


            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    /** searchAdapter
     */
    public class SearchFragmentPagerAdapter extends FragmentStateAdapter {

        public SearchFragmentPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        /** Fragement
         * @param position position
         * @return createFragement
         */
        @NonNull
        @NotNull
        @Override
        public Fragment createFragment(int position) {

            switch (position) {
                case 0:
                    return new SearchFragment();
                case 1:
                    return new FavoriteFragment();
                default:
                    return new SearchFragment();
            }
        }

        /** getItemCount
         * @return NumPages
         */
        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

    /**onBackPressed
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


}

