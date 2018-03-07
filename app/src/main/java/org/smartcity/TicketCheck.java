package org.smartcity;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.Camera;
class TicketCheck extends Form implements HandlesEventDispatching {
  private Label DetailsL;
  private Camera Camera1;
  protected void $define() {
    this.AppName("MobiTravel");
    this.Title("TicketCheck");
    DetailsL = new Label(this);
    DetailsL.Visible(false);
    Camera1 = new Camera(this);
  }
  public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params){
    return false;
  }
}