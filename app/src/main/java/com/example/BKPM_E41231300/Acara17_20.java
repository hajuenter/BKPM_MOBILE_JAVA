package com.example.BKPM_E41231300;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.BKPM_E41231300.databinding.ActivityAcara1720Binding;

public class Acara17_20 extends AppCompatActivity {
    ActivityAcara1720Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAcara1720Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set default fragment to HomeFragment
        taruhFragment(new HomeFragment());

        // BottomNavigationView item selection
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                taruhFragment(new HomeFragment());
            } else if (itemId == R.id.profile) {
                taruhFragment(new ProfileFragment());
            } else if (itemId == R.id.setting) {
                taruhFragment(new SettingFragment());
            } else {
                return false;
            }
            return true;
        });
    }

    // Helper method to load fragments
    private void taruhFragment(Fragment fragment) {
        FragmentManager fM = getSupportFragmentManager();
        FragmentTransaction fT = fM.beginTransaction();
        fT.replace(R.id.frame_woi, fragment);
        fT.commit();
    }
}
