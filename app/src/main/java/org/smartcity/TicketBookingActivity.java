package org.smartcity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.zxing.WriterException;


public class TicketBookingActivity extends AppCompatActivity implements TaskCompleteI, View.OnClickListener {

    Spinner fromSpin, toSpin;
    EditText passengersText;
    Button bookB, prevB;
    ImageView imageView;
    EditText tokenAmountText;

    User globalUser = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        globalUser = (User) getIntent().getSerializableExtra("userData");
        fromSpin = findViewById(R.id.fromSpinner);
        toSpin = findViewById(R.id.toSpinner);
        passengersText = findViewById(R.id.passengesText);
        bookB = findViewById(R.id.bookticket);
        imageView = findViewById(R.id.iv);
        bookB.setOnClickListener(this);
        prevB = findViewById(R.id.previousB);
        tokenAmountText = findViewById(R.id.amountText);

        prevB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent dashboard = new Intent().setClass(getApplicationContext(), DashboardActivity.class);
                    dashboard.putExtra("userData", globalUser);
                    startActivity(dashboard);
            }
        });
    }

    public void validation(){

    }

    @Override
    public void onClick(View view) {
        validation();
        Ticket ticket = new Ticket();
        HttpTicketCreateRequestTask task = new HttpTicketCreateRequestTask();
        task.screen = this;
        ticket.setResourceUrl(getString(R.string.login_url)+ "/"+ globalUser.getId() + "/ticket");
        ticket.setFrom(fromSpin.getSelectedItem().toString());
        ticket.setTo(toSpin.getSelectedItem().toString());
        ticket.setPersons(passengersText.getText().toString());
        ticket.setAmount(tokenAmountText.getText().toString());
        task.execute(ticket);
    }


    public void generateTicket(final String message){
        try {
            Bitmap bitmap = QRImageUtil.TextToImageEncode(message);
            imageView.setImageBitmap(bitmap);
            imageView.setVisibility(View.VISIBLE);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void GotText() {

    }

    @Override
    public void updateDetails(Object u) {
        if ( u != null) {
            Ticket[] list = (Ticket[]) u;
            if ( list !=  null && list.length != 0) {
                Ticket t = list[0];
                generateTicket(t.getId() );
            }
        }
    }
}
