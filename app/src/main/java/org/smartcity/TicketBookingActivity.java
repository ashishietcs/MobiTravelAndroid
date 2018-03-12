package org.smartcity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;


public class TicketBookingActivity extends AppCompatActivity implements TaskCompleteI, View.OnClickListener {

    Spinner fromSpin, toSpin;
    EditText passengersText;
    Button bookB, prevB;
    ImageView imageView;
    private static int QRcodeWidth = 500;
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

        prevB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( globalUser.getStatus().equalsIgnoreCase("Verified")  ){
                    Intent dashboard = new Intent().setClass(getApplicationContext(), DashboardActivity.class);
                    dashboard.putExtra("userData", globalUser);
                    startActivity(dashboard);
                }
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
        ticket.setNo_persons(passengersText.getText().toString());
        task.execute(ticket);
    }


    public void generateTicket(final String message){
        try {
            Bitmap bitmap = TextToImageEncode(message);
            imageView.setImageBitmap(bitmap);
            imageView.setVisibility(View.VISIBLE);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
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
                generateTicket(t.toString());
            }
        }
    }
}
