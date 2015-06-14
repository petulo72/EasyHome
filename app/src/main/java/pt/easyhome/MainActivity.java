package pt.easyhome;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;

import pt.easyhome.database.Db;
import pt.easyhome.miscellaneous.HousePageAdapter;
import pt.easyhome.model.House;

public class MainActivity extends ActionBarActivity {

    public static String HOUSE_ID_TAG = "house_id";
    public static String HOUSE_IP_ADDRESS_TAG = "house_ip_address";
    public static String HOUSE_PORT_TAG = "house_port";
    public static String HOUSE_NAME = "house_name";

    private ViewPager houseViewPager;
    private HousePageAdapter housePageAdapter;

    private Db database;

    private ArrayList<House> houseList;

    private LinearLayout housesLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new Db(this.getApplicationContext());
        houseViewPager = (ViewPager) findViewById(R.id.houseViewPager);
        housePageAdapter = new HousePageAdapter(getSupportFragmentManager());
//        housesLinearLayout = (LinearLayout) findViewById(R.id.houseHorizontalScrollLayout);
        houseViewPager.setAdapter(housePageAdapter);
        refresh();
    }


    public void refresh() {
        houseList = database.getHouses();
        Log.i("HouseList obtained:", "" + houseList.toString());

        housePageAdapter.updateHouses(houseList);
//        housesLinearLayout.removeAllViews();
//        housesLinearLayout.refreshDrawableState();

//        for (final House house : houseList) {
//            LinearLayout houseButton = (LinearLayout) getLayoutInflater().inflate(R.layout.item_image_button, null);
//            houseButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent houseIntent = new Intent(MainActivity.this, HouseActivity.class);
//                    houseIntent.putExtra(HOUSE_ID_TAG, house.getHouseId());
//                    houseIntent.putExtra(HOUSE_IP_ADDRESS_TAG, house.getHouseIpAddress());
//                    houseIntent.putExtra(HOUSE_PORT_TAG, house.getHousePort());
//                    startActivity(houseIntent);
//                }
//            });
//            houseButton.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    MenuDialogFragment menuDialogFragment = new MenuDialogFragment();
//                    Bundle arguments = new Bundle();
//                    arguments.putInt(HOUSE_ID_TAG, house.getHouseId());
//                    menuDialogFragment.setArguments(arguments);
//                    menuDialogFragment.show(fragmentManager, "houseMenuDialog");
//                    return true;
//                }
//            });
//            housesLinearLayout.addView(houseButton);
//        }
//        createAddHouseButton();
    }
//    /**
//     * adding "add new house" icon to list. If list is empty, this will be the only one icon.
//     */
//    private void createAddHouseButton() {
//
//        LinearLayout newHouseButton = (LinearLayout) getLayoutInflater().inflate(R.layout.item_image_button, null);
//        ((ImageView) newHouseButton.findViewById(R.id.contentItem)).setImageDrawable(getResources().getDrawable(R.drawable.plus));
//        newHouseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NewHouseDialog newHouseDialog = new NewHouseDialog(MainActivity.this, null);
//                newHouseDialog.show();
//                newHouseDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//            }
//        });
//        housesLinearLayout.addView(newHouseButton);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

