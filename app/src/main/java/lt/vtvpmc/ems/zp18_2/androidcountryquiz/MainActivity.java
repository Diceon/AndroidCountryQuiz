package lt.vtvpmc.ems.zp18_2.androidcountryquiz;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    /* Ar ivyko nustatymu pakeitimai */
    private boolean preferencesChanged = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Nustatymai pagal nutylejima */
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        /* Klausytojo registracija objekto 'SharedPreferences' */
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(preferenceChangeListener);

    }

    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            /* Vartotojas pakeite nustatymus */
            preferencesChanged = true;

            /* Sukurt MainActivityFragment */
            MainActivityFragment quizFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.quizFragment);

            /* Nustatymu patikrinimas */
            if (key.equals(CHOICES)) {
                quizFragment.updateGuessRows(sharedPreferences);
            } else if (key.equals(REGIONS)) {
                Set<String> regions = sharedPreferences.getStringSet(REGIONS, null);
                if (regions != null && regions.size() > 0) {
                    quizFragment.updateRegions(sharedPreferences);
                    quizFragment.resetQuiz();
                }
            }

        }
    };

    public void setSupportActionBar(Toolbar toolbar) {
    }
}
