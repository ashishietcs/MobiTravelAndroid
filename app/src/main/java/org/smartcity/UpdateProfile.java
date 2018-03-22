package org.smartcity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Clock;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.FirebaseDB;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.TableArrangement;
import com.google.appinventor.components.runtime.TextBox;
import com.google.appinventor.components.runtime.VerticalArrangement;
import com.google.appinventor.components.runtime.Web;

public class UpdateProfile extends Form implements HandlesEventDispatching, TaskCompleteI{

  private TableArrangement SigninA;
  private Label MobileNumberL;
  private TextBox MobileNumberT;
  private Label NameL;
  private TextBox NameT;
  private Label AddressL;
  private TextBox AddressT;
//  private Label AgeL;
//  private TextBox AgeT;
  private Button Previous;
  private Button Next;
  private Clock Clock1;
  private boolean detailsNeeded;
  private User globalUser = null;

  public UpdateProfile() {
    super();
  }
  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    globalUser = (User) getIntent().getSerializableExtra("userData");
    MobileNumberT.Text(globalUser.getMobile_number());
    MobileNumberT.Enabled(false);
    NameT.Text(globalUser.getName());
    AddressT.Text(globalUser.getAddress());
  }

  protected void $define() {
    this.AlignVertical(2);
    this.AppName("MobiTravel");
    this.Title("Login");
    SigninA = new TableArrangement(this);
    SigninA.Width(LENGTH_FILL_PARENT);
    SigninA.Rows(5);
    SigninA.Visible(true);
    MobileNumberL = new Label(SigninA);
    MobileNumberL.Column(0);
    MobileNumberL.Width(LENGTH_FILL_PARENT);
    MobileNumberL.Row(0);
    MobileNumberL.Text("Mobile Number");
    MobileNumberT = new TextBox(SigninA);
    MobileNumberT.Column(1);
    MobileNumberT.Hint("Mobile Number");
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
//    AgeL = new Label(SigninA);
//    AgeL.Column(0);
//    AgeL.Width(LENGTH_FILL_PARENT);
//    AgeL.Row(3);
//    AgeL.Text("Age");
//    AgeT = new TextBox(SigninA);
//    AgeT.Column(1);
//    AgeT.TextColor(Color.BLACK);
//    AgeT.Hint("Age");
//    AgeT.Row(3);
    Previous = new Button(SigninA);
    Previous.Column(0);
    Previous.Width(LENGTH_FILL_PARENT);
    Previous.Row(4);
    Previous.Text("<< Prev");
    Next = new Button(SigninA);
    Next.Column(1);
    Next.Row(4);
    Next.Text("Update");
    EventDispatcher.registerEventForDelegation(this, "ClickEvent", "Click" );
  }
  public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params){
    if( component.equals(Next) && eventName.equals("Click") ){
      NextClick();
      return true;
    }
    if( component.equals(Previous) && eventName.equals("Click") ){
      PreviousClick();
      return true;
    }
    return false;
  }

  public void PreviousClick(){
      Intent dashboard = new Intent().setClass(this, DashboardActivity.class);
      dashboard.putExtra("userData", globalUser);
      startActivity(dashboard);
  }
  public void NextClick(){
    HttpLoginRequestTask task = new HttpLoginRequestTask();
    globalUser.setResourceUrl(getString(R.string.login_url));
    globalUser.setAddress(AddressT.Text());
    task.screen = this;
    task.execute(globalUser);
  }

  public void GotText(){
        Intent dashboard = new Intent().setClass(this, DashboardActivity.class);
        dashboard.putExtra("userData", globalUser);
        startActivity(dashboard);
  }
  public void updateDetails(final Object object ){
      if ( object != null ) {
          User u = (User) object;
          globalUser.setId(u.getId());
          globalUser.setStatus(u.getStatus());
          globalUser.setName(u.getName());
          globalUser.setAddress(u.getAddress());
          Log.i("web user list ", "" + u.getId());
      }
  }



}
