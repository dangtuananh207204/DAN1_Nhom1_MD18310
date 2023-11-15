package com.example.dan1_nhom1_md18310;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.Toolbar);
        FrameLayout frameLayout = findViewById(R.id.FrameLayout);
        NavigationView nav = findViewById(R.id.Nav);
        drawerLayout = findViewById(R.id.DrawerLayout);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.menu);


        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.mnQLH){
                    fragment = new QLHangFragment();

                } else if (item.getItemId() == R.id.mnQLLH) {
                    fragment = new QLLHangFragment();

                } else if (item.getItemId() == R.id.mnQLHD) {
                    fragment = new QLHoaDonFragment();

                } else if (item.getItemId() == R.id.mnQLTV) {
                    fragment = new QLThanhVienFragment();


                } else if (item.getItemId() == R.id.mnDoanhthu) {
                    fragment = new TKDoanhThuFragment();

                } else if (item.getItemId() == R.id.mnTTV) {
                    fragment = new TThanhVienFragment();


//                } else if (item.getItemId() == R.id.mnDMK) {
//                    showDialogDoiMK();

                } else if (item.getItemId() == R.id.mnDX) {
                    Intent intent = new Intent(MainActivity.this, DangNhap.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else {
                    fragment = new QLHangFragment();
                }

                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.FrameLayout, fragment)
                            .commit();

                    drawerLayout.closeDrawer(GravityCompat.START);

                    toolbar.setTitle(item.getTitle());
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
    }
