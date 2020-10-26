package manuel.de.kuehlschrankinventar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import manuel.de.kuehlschrankinventar.InterfacesAndStatics.StaticInts;
import manuel.de.kuehlschrankinventar.R;
import manuel.de.kuehlschrankinventar.scanner.ScannedCodeActivity;

public class MainScreenFragment extends Fragment {

    private MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.main_screen, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        activity = ((MainActivity)  getActivity());

        activity.setTitle(activity.getString(R.string.app_name));
        initUI();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //TODO falls doch gewünscht, dass man fotografieren kann das folgende wieder "entkommentieren"
            /*case R.id.takePicture:
                startActivity(new Intent(MainActivity.this, PictureCodeActivity.class));
                break;*/

            case R.id.scanBarcode:
                /*
                Intent i = new Intent(activity, ScannedCodeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);*/
                activity.showUI(StaticInts.SELECTED_SCAN_SCREEN);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        String abc = "abcdefghijklmnopqrstuvwxyz";
        int i = 5;
        try {
            TextView testTV = getView().findViewById(R.id.testTextView);
            testTV.setText("Es sind noch keine Lebensmittel vorhanden!");

            if(i==6) {
                String abcFromR = getString(R.string.app_name);
                testTV.setText(abcFromR);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            activity.failedInitUI();
        }
    }
}