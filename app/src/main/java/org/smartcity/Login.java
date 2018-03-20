package org.smartcity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
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
import com.google.appinventor.components.runtime.util.YailList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class Login extends Form implements HandlesEventDispatching, TaskCompleteI {
  private VerticalArrangement LoginA;
  private TextBox MobileNumber;
  private TextBox otpNumber;
  private Button otpB;
  private Button NextB;
  private boolean detailsNeeded;
  private User globalUser = null;
  private int MobileNumberLength = 10;
  private int OTPNumberLength = 4;

  @Override
  public boolean BackPressed() {
    return true;
  }

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
    otpNumber.NumbersOnly(true);
    otpB = new Button(LoginA);
    otpB.Width(LENGTH_FILL_PARENT);
    otpB.Text("Next >>");
    NextB = new Button(LoginA);
    NextB.Width(LENGTH_FILL_PARENT);
    NextB.Text("Next >>");
    NextB.Visible(false);
    EventDispatcher.registerEventForDelegation(this, "ClickEvent", "Click" );
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
    return false;
  }
  public void NextBClick(){
    if(!(otpNumber.Text().trim().isEmpty())){
        String otpValue = otpNumber.Text().trim();
        if ( otpValue.length() != OTPNumberLength) {
          Toast.makeText(this,"Wrong OTP entered. "+ OTPNumberLength + " digits needed." , Toast.LENGTH_SHORT ).show();
          return;
        }
        loginUser(otpValue);
    }
  }
  public void otpBClick(){
    if(!(MobileNumber.Text().trim().isEmpty()) ){
      String mobileNumber = MobileNumber.Text().trim();
      if ( mobileNumber.length() != MobileNumberLength) {
        Toast.makeText(this,"Mobile Number should be of "+MobileNumberLength + " digits.", Toast.LENGTH_SHORT ).show();
        return;
      }
      sendOTP(MobileNumber.Text());
      otpNumber.Visible(true);
      MobileNumber.Enabled(false);
      otpB.Visible(false);
      NextB.Visible(true);
    }
  }
  public void sendOTP(String mobile_number){
    globalUser = new User();
    globalUser.setMobile_number(mobile_number);
    globalUser.setName("");
    globalUser.setAddress("");
    globalUser.setResourceUrl(getString(R.string.login_url));
      HttpLoginRequestTask task = new HttpLoginRequestTask();
      task.screen = this;
      task.execute(globalUser);
  }

  public void GotText(){
      if ( globalUser.getStatus().equalsIgnoreCase("Verified")  ){
        Toast.makeText(this,"Login successful. Enjoy the hassle free MobiTravel.", Toast.LENGTH_SHORT ).show();
        Intent dashboard = new Intent().setClass(this, DashboardActivity.class);
        dashboard.putExtra("userData", globalUser);
        startActivity(dashboard);
      } else if ( globalUser.getOtp_number() != null ){
        Toast.makeText(this,"Login unsuccessful. Please provide correct OTP.", Toast.LENGTH_SHORT ).show();
        return;
      }
  }
  public void loginUser(String otp_number){
      globalUser.setOtp_number(otp_number);
      HttpOTPRequestTask task = new HttpOTPRequestTask();
      task.screen = this;
      task.execute(globalUser);
  }
  public void updateDetails(final Object object ){
      if ( object != null ) {
          User u = (User) object;
          globalUser.setId(u.getId());
          globalUser.setStatus(u.getStatus());
          globalUser.setName(u.getName());
          globalUser.setRole(u.getRole());
          Log.i("user logged in ", "" + u.getId());
      }
  }
}
