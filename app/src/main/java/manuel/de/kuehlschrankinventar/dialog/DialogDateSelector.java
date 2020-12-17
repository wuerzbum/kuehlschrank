package manuel.de.kuehlschrankinventar.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.app.AlertDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import manuel.de.kuehlschrankinventar.InterfacesAndStatics.Interfaces;
import manuel.de.kuehlschrankinventar.R;

public class DialogDateSelector extends AlertDialog {
    private Button cancel, save, delete;
    private DatePicker datePicker;
    private final Interfaces.changeListenerDatePicker listener;
    private GregorianCalendar oldDate;

    public DialogDateSelector(Activity activity, Interfaces.changeListenerDatePicker listener, Date oldDate) {
        super(activity);

        this.listener = listener;
        if (oldDate != null) {
            this.oldDate = new GregorianCalendar();
            this.oldDate.setTime(oldDate);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_select_date);

        initUI();
        createListeners();

        if (oldDate != null) {
            datePicker.updateDate(oldDate.get(Calendar.YEAR), oldDate.get(Calendar.MONTH),
                    oldDate.get(Calendar.DAY_OF_MONTH));
        }
    }

    private void initUI() {
        cancel = findViewById(R.id.cancel);
        delete = findViewById(R.id.delete);
        save = findViewById(R.id.save);
        datePicker = findViewById(R.id.date_picker);
    }

    private void createListeners() {
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oldDate != null) {
                    listener.changed(null);
                }
                dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GregorianCalendar cal = new GregorianCalendar();
                cal.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());


                if (oldDate == null || cal.getTimeInMillis() != oldDate.getTimeInMillis()) {
                    listener.changed(cal.getTime());
                }
                dismiss();
            }
        });
    }
}
