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

class TicketListing extends Form implements HandlesEventDispatching, TaskCompleteI {
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
    FromLV.ElementsFromString("Dwarka");
    List<String> list = new ArrayList<String>();
    list.add("Dwarka");
    list.add("Gurgaon");
    list.add("Noida");
    list.add("Faridabad");
    FromLV.Elements(YailList.makeList(list));
     FromLV.Row(0);
    ToL = new Label(BookingA);
    ToL.Column(0);
    ToL.Row(1);
    ToL.Text("To");
    ToLV = new ListView(BookingA);
    ToLV.BackgroundColor(0x00FFFFFF);
    ToLV.Column(1);
    ToLV.ElementsFromString("Gurgaon");
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
    String [] passengers = new String[] {"1", "2","3", "4", "5", "6"};
    PassengersLV.ElementsFromString("1");
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
    HttpTicketRequestTask task = new HttpTicketRequestTask();
    task.screen = this;
    globalUser.setResourceUrl(getString(R.string.login_url)+ "/"+ globalUser.getId() + "/ticket");
    task.execute(globalUser);
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
        FromLV.ElementsFromString(t.getFrom());
        ToLV.ElementsFromString(t.getTo());
        PassengersLV.ElementsFromString(t.getNo_persons());
      }
    }

  }
}