package manuel.de.kuehlschrankinventar.scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import manuel.de.kuehlschrankinventar.R;

public class EMailActivity extends AppCompatActivity {

    private EditText inSubject, inBody;
    private TextView txtEmailAddress;
    private Button btnSendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_mail);

        initViews();
    }
    private void initViews() {
        inSubject = findViewById(R.id.inSubject);
        inBody = findViewById(R.id.inBody);
        txtEmailAddress = findViewById(R.id.txtEmailAddress);
        btnSendEmail = findViewById(R.id.btnSendEmail);


        if (getIntent().getStringExtra("email_address") != null) {
            //TODO text in xml eintragen
            txtEmailAddress.setText("Recipient : " + getIntent().getStringExtra("email_address"));
        }

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{txtEmailAddress.getText().toString()});
                intent.putExtra(Intent.EXTRA_SUBJECT, inSubject.getText().toString().trim());
                intent.putExtra(Intent.EXTRA_TEXT, inBody.getText().toString().trim());

                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });
    }
}