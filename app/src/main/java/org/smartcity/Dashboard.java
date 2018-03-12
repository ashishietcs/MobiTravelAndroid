package org.smartcity;

import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

class Dashboard extends Form implements HandlesEventDispatching {
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

  protected void $define() {
    this.AppName("MobiTravel");
    this.Title("MobiTravel");
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
    ToL = new Label(BookingA);
    ToL.Column(0);
    ToL.Row(1);
    ToL.Text("To");
    PassengersL = new Label(BookingA);
    PassengersL.Column(0);
    PassengersL.Row(2);
    PassengersL.Text("No of Passengers");
    GoB = new Button(BookingA);
    GoB.Column(1);
    GoB.Row(3);
    GoB.Text("Go");
    ExitB = new Button(BookingA);
    ExitB.Column(0);
    ExitB.Row(3);
    ExitB.Text("Exit");
    Image1 = new Image(TicketBookingA);
    Image1.Width(LENGTH_FILL_PARENT);
    Image1.Visible(false);

    Web1 = new Web(this);
    isValid = false;
    EventDispatcher.registerEventForDelegation(this, "ClickEvent", "Click" );
    EventDispatcher.registerEventForDelegation(this, "GotTextEvent", "GotText" );
  }
  public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params){
    if( component.equals(GoB) && eventName.equals("Click") ){
      GoBClick();
      return true;
    }
    if( component.equals(Web1) && eventName.equals("GotText") ){
      Web1GotText();
      return true;
    }
    return false;
  }
  public void GoBClick(){
  }
  public void Web1GotText(){
  }

}