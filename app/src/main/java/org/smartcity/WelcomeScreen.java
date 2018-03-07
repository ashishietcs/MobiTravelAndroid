package org.smartcity;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Image;
import com.google.appinventor.components.runtime.Clock;
import android.content.Intent;

class WelcomeScreen extends Form implements HandlesEventDispatching {
  private Image Image1;
  private Clock Clock1;
  private int welcomeTime;
  protected void $define() {
    this.AppName("MobiTravel");
    this.Title("WelcomeScreen");
    Image1 = new Image(this);
    Image1.Picture("mobitravel.jpg");
    Clock1 = new Clock(this);
    welcomeTime = 5;
    EventDispatcher.registerEventForDelegation(this, "TimerEvent", "Timer" );
  }
  public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params){
    if( component.equals(Clock1) && eventName.equals("Timer") ){
      Clock1Timer();
      return true;
    }
    return false;
  }
  public void Clock1Timer(){
    welcomeTime = (Integer) (welcomeTime - 1);
    if(welcomeTime == 0){
      startActivity(new Intent().setClass(this, Login.class));
    }
  }
}