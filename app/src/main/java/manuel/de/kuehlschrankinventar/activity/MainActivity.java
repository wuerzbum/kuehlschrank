package manuel.de.kuehlschrankinventar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import manuel.de.kuehlschrankinventar.R;
import manuel.de.kuehlschrankinventar.holder.Inventar;
import manuel.de.kuehlschrankinventar.scanner.ScannedCodeActivity;

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
            //TODO falls doch gewünscht, dass man fotografieren kann das folgende wieder "entkommentieren"
            /*case R.id.takePicture:
                startActivity(new Intent(MainActivity.this, PictureCodeActivity.class));
                break;*/

            case R.id.scanBarcode:
                Intent i = new Intent(MainActivity.this, ScannedCodeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}