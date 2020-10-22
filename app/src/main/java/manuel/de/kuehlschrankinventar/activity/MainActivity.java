package manuel.de.kuehlschrankinventar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        /* Menu Icons in Dropdown anzeigen
         * if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
         }
         */

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.takePicture:
                //TODO takePicture
                break;

            case R.id.scanBarcode:
                //TODO scan barcode
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}