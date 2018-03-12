package org.smartcity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.Image;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.ListView;
import com.google.appinventor.components.runtime.TableArrangement;
import com.google.appinventor.components.runtime.VerticalArrangement;
import com.google.appinventor.components.runtime.Web;

import com.google.appinventor.components.runtime.util.YailList;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

class TicketBooking extends Form implements HandlesEventDispatching, TaskCompleteI {
  private VerticalArrangement TicketBookingA;
  private Label WelcomeL;
  private TableArrangement BookingA;
  private Label FromL;
  private ListView FromLV;
  private Label ToL;
  private ListView ToLV;
  private Label PassengersL;
  private ListView PassengersLV;
  private Button GoB;
  private Button ExitB;
  private Image Image1;
  private ImageView imageView;
  private Web Web1;
  private boolean isValid;
  private static final String IMAGE_DIRECTORY = "/QRcodeDemonuts";
  private static int QRcodeWidth = 500;
  private User globalUser = null;

  protected void $define() {
    this.AppName("MobiTravel");
    this.Title("TicketBooking");
    TicketBookingA = new VerticalArrangement(this);
    TicketBookingA.AlignHorizontal(3);
    TicketBookingA.Width(LENGTH_FILL_PARENT);
    WelcomeL = new Label(TicketBookingA);
    WelcomeL.FontSize(18);
    WelcomeL.Width(LENGTH_FILL_PARENT);
    WelcomeL.Text("Welcome User");
    BookingA = new TableArrangement(TicketBookingA);
    BookingA.Width(LENGTH_FILL_PARENT);
    BookingA.Rows(4);
    FromL = new Label(BookingA);
    FromL.Column(0);
    FromL.Row(0);
    FromL.Text("From");
    FromLV = new ListView(BookingA);
    FromLV.BackgroundColor(0x00FFFFFF);
    FromLV.Column(1);
    List<String> list = new ArrayList<String>();
    list.add("Dwarka");
    list.add("Gurgaon");
    list.add("Noida");
    FromLV.Elements(YailList.makeList(list));
     FromLV.Row(0);
    ToL = new Label(BookingA);
    ToL.Column(0);
    ToL.Row(1);
    ToL.Text("To");
    ToLV = new ListView(BookingA);
    ToLV.BackgroundColor(0x00FFFFFF);
    ToLV.Column(1);
    ToLV.Row(1);
    ToLV.Elements(YailList.makeList(list));
    PassengersL = new Label(BookingA);
    PassengersL.Column(0);
    PassengersL.Row(2);
    PassengersL.Text("No of Passengers");
    PassengersLV = new ListView(BookingA);
    PassengersLV.BackgroundColor(0xFFFFFFFF);
    PassengersLV.Column(1);
    PassengersLV.Row(2);
    String [] passengers = new String[] {"1", "2"};
    PassengersLV.Elements(YailList.makeList(passengers));
    GoB = new Button(BookingA);
    GoB.Column(1);
    GoB.Row(3);
    GoB.Text("Go");
    ExitB = new Button(BookingA);
    ExitB.Column(0);
    ExitB.Row(3);
    ExitB.Text("Previous");
    Image1 = new Image(TicketBookingA);
    Image1.Width(LENGTH_FILL_PARENT);
    Image1.Visible(false);
    EventDispatcher.registerEventForDelegation(this, "ClickEvent", "Click" );
  }
  public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params){
    if( component.equals(GoB) && eventName.equals("Click") ){
      GoBClick();
      return true;
    }
    if( component.equals(ExitB) && eventName.equals("Click") ){
      PreviousClick();
      return true;
    }
    return false;
  }

  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    globalUser = (User) getIntent().getSerializableExtra("userData");
  }

  public void PreviousClick(){
    if ( globalUser.getStatus().equalsIgnoreCase("Verified")  ){
      Intent dashboard = new Intent().setClass(this, DashboardActivity.class);
      dashboard.putExtra("userData", globalUser);
      startActivity(dashboard);
    }
  }
  public void GoBClick(){
    validation();
    HttpTicketCreateRequestTask task = new HttpTicketCreateRequestTask();
    task.screen = this;
    globalUser.setResourceUrl(getString(R.string.login_url)+ "/"+ globalUser.getId() + "/ticket");
    //task.execute(globalUser);
    }
  public void validation(){
    isValid = (Boolean) false;
  }
  public void generateTicket(final String message){
    Web1.Url("generate");
    try {
      Bitmap bitmap = TextToImageEncode(message);
      String path = saveImage(bitmap);  //give read write permission
      Image1.Picture(path);
      Image1.Visible(true);
      Toast.makeText(TicketBooking.this, "QRCode saved to -> "+path, Toast.LENGTH_SHORT).show();
      new File(path).delete();
    } catch (WriterException e) {
      e.printStackTrace();
    }

  }
  public void validateResponse(){
    isValid = (Boolean) false;
  }
  public void generateQR(){
    isValid = (Boolean) true;
  }

  public String saveImage(Bitmap myBitmap) {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
    File wallpaperDirectory = new File(
            Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
    // have the object build the directory structure, if needed.

    if (!wallpaperDirectory.exists()) {
      Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
      wallpaperDirectory.mkdirs();
    }

    try {
      File f = new File(wallpaperDirectory, Calendar.getInstance()
              .getTimeInMillis() + ".jpg");
      f.createNewFile();   //give read write permission
      FileOutputStream fo = new FileOutputStream(f);
      fo.write(bytes.toByteArray());
      MediaScannerConnection.scanFile(this,
              new String[]{f.getPath()},
              new String[]{"image/jpeg"}, null);
      fo.close();
      Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

      return f.getAbsolutePath();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    return "";

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
  public void updateDetails(final Object u) {
    if ( u != null) {
      Ticket[] list = (Ticket[]) u;
      if ( list !=  null && list.length != 0) {
        Ticket t = list[0];
        generateTicket(t.toString());
      }
    }

  }
}