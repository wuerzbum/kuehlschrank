package manuel.de.kuehlschrankinventar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import manuel.de.kuehlschrankinventar.holder.Inventar;
import manuel.de.kuehlschrankinventar.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Inventar inv = new Inventar();

        String abc = "abcdefghijklmnopqrstuvwxyz";
        int i = inv.getAnzahlLebensmittel();
        TextView testTV = findViewById(R.id.testTextView);
        testTV.setText("Es sind noch keine Lebensmittel vorhanden!");

        if(i==6) {
            String abcFromR = getString(R.string.app_name);
            testTV.setText(abcFromR);
        }
    }
}