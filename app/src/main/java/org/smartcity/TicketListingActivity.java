package org.smartcity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.util.ArrayList;
import java.util.Arrays;


public class TicketListingActivity extends AppCompatActivity implements TaskCompleteI{

    // Array of strings...
    ListView simpleList;
    ArrayList<Ticket> ticketList ;
    User globalUser = null;
    CustomAdapter customAdapter;

    @Override   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      setContentView(R.layout.activity_ticketbooking);
        simpleList = (ListView)findViewById(R.id.simpleListView);
        globalUser = (User) getIntent().getSerializableExtra("userData");

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
                ticketList = new ArrayList<>(Arrays.asList(list));
                customAdapter = new CustomAdapter(getApplicationContext(), ticketList);
                simpleList.setAdapter(customAdapter);
                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Ticket dataModel= ticketList.get(position);
                        ImageView image = new ImageView(getApplicationContext());
                        Bitmap bitmap = null;
                        try {
                            bitmap = QRImageUtil.TextToImageEncode(dataModel.getId());
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }
                        image.setImageBitmap(bitmap);
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(TicketListingActivity.this).
                                        setMessage("Message above the image").
                                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).
                                        setView(image);
                        builder.create().show();


                        Toast.makeText(getApplicationContext(), "Clicked on ticket "+dataModel.getId(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
