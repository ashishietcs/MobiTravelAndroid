package org.smartcity;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Toast;

import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.VerticalArrangement;
import com.google.appinventor.components.runtime.TextBox;
import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.TableArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.Clock;
import com.google.appinventor.components.runtime.FirebaseDB;
import com.google.appinventor.components.runtime.Web;
class Login extends Form implements HandlesEventDispatching {
  private VerticalArrangement LoginA;
  private TextBox MobileNumber;
  private TextBox otpNumber;
  private Button otpB;
  private Button NextB;
  private TableArrangement SigninA;
  private Label MobileNumberL;
  private TextBox MobileNumberT;
  private Label NameL;
  private TextBox NameT;
  private Label AddressL;
  private TextBox AddressT;
  private Label AgeL;
  private TextBox AgeT;
  private Button Previous;
  private Button Next;
  private Clock Clock1;
  private FirebaseDB FirebaseDB1;
  private Web Web1;
  private boolean detailsNeeded;
  protected void $define() {
    this.AlignVertical(2);
    this.AppName("MobiTravel");
    this.Title("Login");
    LoginA = new VerticalArrangement(this);
    LoginA.AlignHorizontal(3);
    LoginA.Width(LENGTH_FILL_PARENT);
    MobileNumber = new TextBox(LoginA);
    MobileNumber.Width(LENGTH_FILL_PARENT);
    MobileNumber.TextColor(Color.BLACK);
    MobileNumber.Hint("Mobile Number");
    MobileNumber.NumbersOnly(true);
    otpNumber = new TextBox(LoginA);
    otpNumber.Width(LENGTH_FILL_PARENT);
    otpNumber.Hint("OTP Number");
    otpNumber.TextColor(Color.BLACK);
    otpNumber.Visible(false);
    otpB = new Button(LoginA);
    otpB.Width(LENGTH_FILL_PARENT);
    otpB.Text("Next >>");
    NextB = new Button(LoginA);
    NextB.Width(LENGTH_FILL_PARENT);
    NextB.Text("Next >>");
    NextB.Visible(false);
    SigninA = new TableArrangement(this);
    SigninA.Width(LENGTH_FILL_PARENT);
    SigninA.Rows(5);
    SigninA.Visible(false);
    MobileNumberL = new Label(SigninA);
    MobileNumberL.Column(0);
    MobileNumberL.Width(LENGTH_FILL_PARENT);
    MobileNumberL.Row(0);
    MobileNumberL.Text("Mobile Number");
    MobileNumberT = new TextBox(SigninA);
    MobileNumberT.Column(1);
    MobileNumberT.Hint("Mobile Number");
    MobileNumber.TextColor(Color.BLACK);
    MobileNumberT.Row(0);
    NameL = new Label(SigninA);
    NameL.Column(0);
    NameL.Width(LENGTH_FILL_PARENT);
    NameL.Row(1);
    NameL.Text("Name");
    NameT = new TextBox(SigninA);
    NameT.Column(1);
    NameT.TextColor(Color.BLACK);
    NameT.Hint("Name");
    NameT.Row(1);
    AddressL = new Label(SigninA);
    AddressL.Column(0);
    AddressL.Width(LENGTH_FILL_PARENT);
    AddressL.Row(2);
    AddressL.Text("Address");
    AddressT = new TextBox(SigninA);
    AddressT.Column(1);
    AddressT.TextColor(Color.BLACK);
    AddressT.Hint("Enter Address");
    AddressT.Row(2);
    AgeL = new Label(SigninA);
    AgeL.Column(0);
    AgeL.Width(LENGTH_FILL_PARENT);
    AgeL.Row(3);
    AgeL.Text("Age");
    AgeT = new TextBox(SigninA);
    AgeT.Column(1);
    AgeT.TextColor(Color.BLACK);
    AgeT.Hint("No of passengers");
    AgeT.Row(3);
    Previous = new Button(SigninA);
    Previous.Column(0);
    Previous.Width(LENGTH_FILL_PARENT);
    Previous.Row(4);
    Previous.Text("<< Prev");
    Next = new Button(SigninA);
    Next.Column(1);
    Next.Row(4);
    Next.Text("Next >>");
    Clock1 = new Clock(this);
    Web1 = new Web(this);
    detailsNeeded = false;
    EventDispatcher.registerEventForDelegation(this, "ClickEvent", "Click" );
    EventDispatcher.registerEventForDelegation(this, "WebEvent","GotText");
  }
  public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params){
    if( component.equals(NextB) && eventName.equals("Click") ){
      NextBClick();
      return true;
    }
    if( component.equals(otpB) && eventName.equals("Click") ){
      otpBClick();
      return true;
    }
    if( component.equals(Next) && eventName.equals("Click") ){
      NextClick();
      return true;
    }
    if( component.equals(Web1) && eventName.equals("GotText") ){
      GotText(params);
      return true;
    }
    return false;
  }
  public void NextBClick(){
    if(!(otpNumber.Text().trim().isEmpty())){
      String otpValue = otpNumber.Text().trim();
      if ( otpValue.compareToIgnoreCase("1234") != 0) {
        Toast.makeText(this,"Not a correct OTP. Try again", Toast.LENGTH_SHORT ).show();
        return;
      }
      registerUser();
      otpNumber.Enabled(false);
      MobileNumber.Enabled(false);
      otpB.Visible(false);
      NextB.Visible(true);
      LoginA.Visible(false);
      SigninA.Visible(true);
      startActivity(new Intent().setClass(this, TicketBooking.class));
    }
  }
  public void otpBClick(){
    if(!(MobileNumber.Text().trim().isEmpty())){
      sendOTP();
      otpNumber.Visible(true);
      MobileNumber.Enabled(false);
      otpB.Visible(false);
      NextB.Visible(true);
    }
  }
  public void NextClick(){
    updateUser();
    startActivity(new Intent().setClass(this, TicketBooking.class));
  }
  public void sendOTP(){
    Web1.Url(getString(R.string.login_url));
    Web1.Get();
  }

  public void GotText(Object[] params){

  }
  public void registerUser(){
    Web1.Url("registerUrl");
  }
  public void updateUser(){
    Web1.Url("registerUrl");
    Web1.PutText("one");
  }
}
