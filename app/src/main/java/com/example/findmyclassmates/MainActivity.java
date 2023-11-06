package com.example.findmyclassmates;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // Define your BottomNavigationView
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home as default selection
        bottomNavigationView.setSelectedItemId(R.id.navigation_user_profile);

        // Set up the listener for BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                int id = item.getItemId();
                if (id == R.id.navigation_user_profile) {
                    selectedFragment = new UserProfileFragment();
                } else if (id == R.id.navigation_classes) {
                    selectedFragment = new ClassesFragment();
                } else if (id == R.id.navigation_find_classmates) {
                    selectedFragment = new FindClassmatesFragment();
                } else if (id == R.id.navigation_inbox) {
                    selectedFragment = new InboxFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                return true;
            }
        });

        // Manually displaying the first fragment - one time only
        Fragment initialFragment = new UserProfileFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, initialFragment).commit();
    }
}
