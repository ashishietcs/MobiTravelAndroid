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
  private static User globalUser = null;
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
      GotText();
      return true;
    }
    return false;
  }
  public void NextBClick(){
    if(!(otpNumber.Text().trim().isEmpty())){
      String otpValue = otpNumber.Text().trim();
        Toast.makeText(this,"Not a correct OTP. Try again", Toast.LENGTH_SHORT ).show();
        registerUser(otpValue);
        startActivity(new Intent().setClass(this, TicketBooking.class));
    }
  }
  public void otpBClick(){
    if(!(MobileNumber.Text().trim().isEmpty())){
      sendOTP(MobileNumber.Text());
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
  public void sendOTP(String mobile_number){
    Web1.Url(getString(R.string.login_url));
    globalUser = new User();
    globalUser.setMobile_number(mobile_number);
    globalUser.setName("");
    globalUser.setAddress("");
    Gson gson = new Gson();
    String content = gson.toJson(globalUser);
    Log.i("Web Post Text",  content);
    //Web1.PostTextWithEncoding(content,"application/json");
      //List<Object> list = new ArrayList<Object>();
      //list.add(YailList.makeList(new String[] { "Content-Type", "application/json" }));
      //Web1.RequestHeaders(YailList.makeList(list));
    //Web1.PostText(content);
      new HttpRequestTask().execute();
  }

  public void GotText(){
      if ( globalUser.getStatus().equalsIgnoreCase("Verified")  ){
          otpNumber.Enabled(false);
          MobileNumber.Enabled(false);
          otpB.Visible(false);
          NextB.Visible(true);
          LoginA.Visible(false);
          SigninA.Visible(true);
          startActivity(new Intent().setClass(this, TicketBooking.class));
      }
  }
  public void registerUser(String otp_number){
      Web1.Url(getString(R.string.login_url)+"/"+globalUser.getId()+"/otp");
      User u = new User();
      u.setOtp_number(otp_number);
      Gson gson = new Gson();
      String content = gson.toJson(u);
      Web1.PostText(content);
  }
  public void updateUser(){
  }


    private class HttpRequestTask extends AsyncTask<Login, Void, User> {
        Login screen ;
        @Override
        protected User doInBackground(Login... params) {
            screen = params[0];
            try {
                final String uri = getString(R.string.login_url);
                RestTemplate restTemplate = new RestTemplate();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<User> entity = new HttpEntity<User>(globalUser, headers);

                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter ());
                ResponseEntity<User> result = restTemplate.exchange(uri, HttpMethod.POST, entity, User.class);
                return result.getBody();
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(User u) {
            if ( u != null ) {
                globalUser.setId(u.getId());
                globalUser.setStatus(u.getStatus());
                globalUser.setName(u.getName());
                Log.i("web user list ", "" + u.getId());
            }
            screen.GotText();
        }

    }

}