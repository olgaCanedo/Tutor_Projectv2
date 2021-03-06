package com.example.olgac.tutor_temp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.olgac.tutor_temp.views.CampusFragment;
import com.example.olgac.tutor_temp.views.SkillsFragment;
import com.example.olgac.tutor_temp.views.SubjectsFragment;
import com.example.olgac.tutor_temp.views.TutorsFragment;
import com.example.olgac.tutor_temp.views.UsersFragment;

import java.util.ArrayList;
import java.util.List;

public class TutorsManagement extends AppCompatActivity {

    public static final String INDEX_TAB = "index_tab";
    private static final String TAG = TutorsManagement.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    public static int posIndex=0;
    private static Intent positionIntent;
    private ViewPager viewPager;
    private TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutors_management);

        // Setting ViewPager for each Tabs
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(listener);

        // Adding Floating Action Button to bottom right of main view
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (posIndex)
                {
                    case 0:
                        positionIntent = new Intent(getApplicationContext(), CampusS.class);
                        startActivity(positionIntent);
                        break;
                    case 1:
                        positionIntent = new Intent(getApplicationContext(), SubjectsA.class);
                        startActivity(positionIntent);
                        break;
                    case 2:
                        positionIntent = new Intent(getApplicationContext(), SkillA.class);
                        startActivity(positionIntent);
                        break;
                    case 3:
                        positionIntent = new Intent(getApplicationContext(), AddUpdateTutor.class);
                        startActivity(positionIntent);
                        break;
                    case 4:
                        positionIntent = new Intent(getApplicationContext(), Users.class);
                        startActivity(positionIntent);
                        break;
                }
            }
        });
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new CampusFragment(), "Campus");
        adapter.addFragment(new SubjectsFragment(), "Labs");
        adapter.addFragment(new SkillsFragment(), "Skills");
        adapter.addFragment(new TutorsFragment(), "Tutors");
        adapter.addFragment(new UsersFragment(), "Users");
        viewPager.setAdapter(adapter);
    }

    class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            transaction.addToBackStack("fragment");
            transaction.commit();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }
    }

    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            posIndex=position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getOrder()) {
            case 10:
                final Intent intent = new Intent(getApplicationContext(), AddUpdateTutor.class);
                startActivity(intent);
                return true;

            case 20:
                displayToast(getString(R.string.action_delete));
                return true;

            case 30:
                displayToast(getString(R.string.action_update));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void displayToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
