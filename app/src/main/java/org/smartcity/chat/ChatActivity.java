package org.smartcity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class DashboardActivity extends AppCompatActivity {

    Button bookTicketB;
    Button viewHistoryB;
    Button updateProfileB;
    Button talkToUsB;
    Button validateTicketB;
    Button checkinTicketB;
    Button checkoutTicketB;
    LinearLayout adminLayout;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You will exit current session")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Exit the session!
                        startActivity(new Intent().setClass(getApplicationContext(), Login.class));
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        final User data = (User)getIntent().getSerializableExtra("userData");
        bookTicketB = findViewById(R.id.bookticket);
        viewHistoryB = findViewById(R.id.viewticket);
        updateProfileB = findViewById(R.id.updateprofile);
        talkToUsB = findViewById(R.id.talktous);
        adminLayout = findViewById(R.id.adminLayout);
        validateTicketB = findViewById(R.id.validateTicket);
        checkinTicketB = findViewById(R.id.checkinTicket);
        checkoutTicketB = findViewById(R.id.checkoutTicket);

        if (data.getRole() != null && data.getRole().trim().equalsIgnoreCase("admin")) {
            adminLayout.setVisibility(View.VISIBLE);
        }

        bookTicketB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent().setClass(getApplicationContext(), TicketBookingActivity.class);
                in.putExtra("userData", data);
                startActivity(in);
            }
        });

        viewHistoryB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent().setClass(getApplicationContext(), TicketListingActivity.class);
                in.putExtra("userData", data);
                startActivity(in);
            }
        });

        updateProfileB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent().setClass(getApplicationContext(), UpdateProfile.class);
                in.putExtra("userData", data);
                startActivity(in);
            }
        });

        validateTicketB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent().setClass(getApplicationContext(), TicketCheck.class);
                in.putExtra("userData", data);
                startActivity(in);
            }
        });

        checkinTicketB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent().setClass(getApplicationContext(), TicketCheckin.class);
                in.putExtra("userData", data);
                startActivity(in);
            }
        });

        checkoutTicketB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent().setClass(getApplicationContext(), TicketCheckout.class);
                in.putExtra("userData", data);
                startActivity(in);
            }
        });

    }
}
