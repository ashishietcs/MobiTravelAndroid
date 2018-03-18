package org.smartcity;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Ashish Sahu on 3/10/2018.
 */

public class HttpTicketCheckTask extends AsyncTask<TicketCheckJob, Void, TicketCheckJobResponse> {
    public TaskCompleteI screen ;
    @Override
    protected TicketCheckJobResponse doInBackground(TicketCheckJob... params) {
        try {
            TicketCheckJob ticket = params[0];
            final String uri = ticket.getUrl();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            if ( HttpMethod.GET.compareTo(ticket.getMethod()) == 0) {
                ResponseEntity<TicketCheckJobResponse> result = restTemplate.getForEntity(uri,TicketCheckJobResponse.class);
                return result.getBody();
            } else {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Ticket> entity = new HttpEntity<Ticket>(ticket.getTicket(), headers);
                ResponseEntity<TicketCheckJobResponse> result = restTemplate.exchange(uri, ticket.getMethod(), entity, TicketCheckJobResponse.class);
                return result.getBody();
            }
        } catch (Exception e) {
            Log.e("Login", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(TicketCheckJobResponse u) {
        screen.GotText();
        screen.updateDetails(u.getStatus());
    }

}
