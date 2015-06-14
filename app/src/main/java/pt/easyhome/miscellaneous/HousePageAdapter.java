package pt.easyhome.miscellaneous;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.Choreographer;

import java.util.ArrayList;
import java.util.List;

import pt.easyhome.MainActivity;
import pt.easyhome.fragments.HouseScreenSlidePageFragment;
import pt.easyhome.fragments.NewHouseFragment;
import pt.easyhome.model.House;

/**
 * Created by Peter on 11.5.2015.
 */
public class HousePageAdapter extends FragmentStatePagerAdapter{
    
    List<Fragment> fragments = new ArrayList<>();
    
    public HousePageAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new NewHouseFragment());
    }

    @Override
     public android.support.v4.app.Fragment getItem(int position) {
        return fragments.get(position);
    }

    public void updateHouses(ArrayList<House> houses) {
        fragments.clear();

        for(House house: houses) {
            HouseScreenSlidePageFragment fragment = new HouseScreenSlidePageFragment();
            Bundle params = new Bundle();
            params.putInt(MainActivity.HOUSE_ID_TAG, house.getHouseId());
            params.putString(MainActivity.HOUSE_IP_ADDRESS_TAG, house.getHouseIpAddress());
            params.putInt(MainActivity.HOUSE_PORT_TAG, house.getHousePort());
            params.putString(MainActivity.HOUSE_NAME, house.getHouseName());
            fragment.setArguments(params);
            //pridat dalsie argumenty
            fragments.add(fragment);
        }

        fragments.add(new NewHouseFragment());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

