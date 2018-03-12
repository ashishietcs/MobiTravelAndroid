package org.smartcity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class TicketListingActivity extends AppCompatActivity implements TaskCompleteI{

    // Array of strings...
    ListView simpleList;
    Ticket ticketList[] = {};
    User globalUser = null;

    @Override   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      setContentView(R.layout.activity_ticketbooking);
        simpleList = (ListView)findViewById(R.id.simpleListView);
        globalUser = (User) getIntent().getSerializableExtra("userData");

        CustomAdapter customAdapter;
        customAdapter = new CustomAdapter(getApplicationContext(), ticketList);
        simpleList.setAdapter(customAdapter);

        Ticket ticket = new Ticket();
        HttpTicketRequestTask task = new HttpTicketRequestTask();
        task.screen = this;
        ticket.setResourceUrl(getString(R.string.login_url)+ "/"+ globalUser.getId() + "/ticket");
        task.execute(ticket);
    }

    @Override
    public void GotText() {

    }

    @Override
    public void updateDetails(Object u) {
        if ( u != null) {
            Ticket[] list = (Ticket[]) u;
            if ( list !=  null && list.length != 0) {
                CustomAdapter customAdapter;
                customAdapter = new CustomAdapter(getApplicationContext(), list);
                simpleList.setAdapter(customAdapter);

            }
        }
    }
}
