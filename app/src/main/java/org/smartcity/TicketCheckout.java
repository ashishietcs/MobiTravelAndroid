package org.smartcity;

import android.content.Intent;
import android.widget.Toast;

import com.google.appinventor.components.runtime.Camera;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.Label;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.springframework.http.HttpMethod;

public class TicketCheckout extends Form implements HandlesEventDispatching, TaskCompleteI {
  private Label DetailsL;
  private Camera Camera1;
  protected void $define() {
    this.AppName("MobiTravel");
    this.Title("TicketCheckout");
    DetailsL = new Label(this);
    DetailsL.Visible(Boolean.TRUE);
    Camera1 = new Camera(this);
    new IntentIntegrator(this).initiateScan(); // `this` is the current Activity

  }
  public TicketCheckout() {
    super();
  }
  public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params){
    return false;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
    if (result != null) {

      if (result.getContents() == null) {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
      } else {
        Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
        HttpTicketCheckTask task = new HttpTicketCheckTask();
        task.screen = this;
        TicketCheckJob job = new TicketCheckJob();
        job.setTicketId(result.getContents());
        job.setMethod(HttpMethod.PUT);
        Ticket ticket = new Ticket();
        ticket.setTo("Gurgaon");
        job.setTicket(ticket);
        job.setUrl("https://backend-dot-androidpushnotification-196014.appspot.com/ticket/"+result.getContents());
        task.execute(job);
      }
    } else {
      super.onActivityResult(requestCode, resultCode, data);
    }
  }
  @Override
  public void GotText() {

  }

  @Override
  public void updateDetails(Object u) {
    String response = (String) u;
    Toast.makeText(this, response, Toast.LENGTH_LONG).show();
  }
}