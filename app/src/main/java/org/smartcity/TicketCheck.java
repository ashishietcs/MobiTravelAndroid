package org.smartcity;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.Camera;
import com.google.zxing.integration.android.IntentIntegrator;

class TicketCheck extends Form implements HandlesEventDispatching {
  private Label DetailsL;
  private Camera Camera1;
  protected void $define() {
    this.AppName("MobiTravel");
    this.Title("TicketCheck");
    DetailsL = new Label(this);
    DetailsL.Visible(Boolean.TRUE);
    Camera1 = new Camera(this);
    new IntentIntegrator(this).initiateScan(); // `this` is the current Activity

  }
  public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params){
    return false;
  }
}