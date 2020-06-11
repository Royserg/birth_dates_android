package com.example.birthdates;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.birthdates.ui.components.AddBottomSheetDialog;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private BottomAppBar bottomAppBar;
    private Toolbar topToolbar;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomAppBar = findViewById(R.id.bottom_app_bar);
        topToolbar = findViewById(R.id.top_toolbar);

        fab = findViewById(R.id.fab);
        fab.setColorFilter(Color.WHITE);
        fab.setOnClickListener(this::onFabPressed);

        // Setting menu in the bottom app bar
        setSupportActionBar(bottomAppBar);
        setSupportActionBar(topToolbar);

        initializeNavigation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Enable divider between options groups
        MenuCompat.setGroupDividerEnabled(menu, true);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_settings:
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.navigation_settings);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Initialize Navigation between fragment */
    protected void initializeNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        // Connect bottom navigation with destinations
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_people, R.id.navigation_events)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }

    public void onFabPressed(View view) {
        // Show Bottom sheet dialog
        AddBottomSheetDialog bottomSheet = new AddBottomSheetDialog(this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        bottomSheet.show(ft, AddBottomSheetDialog.TAG);
    }

    @Override
    public void onBackPressed() {
        System.out.println("Back Button Pressed");
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
