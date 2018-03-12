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

public class HttpTicketCreateRequestTask extends AsyncTask<Ticket, Void, Ticket[]> {
    public TaskCompleteI screen ;
    @Override
    protected Ticket[] doInBackground(Ticket... params) {
        try {
            Ticket ticket = params[0];
            final String uri = ticket.getResourceUrl();
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Ticket> entity = new HttpEntity<Ticket>(ticket, headers);

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<Ticket[]> result = restTemplate.exchange(uri, HttpMethod.POST, entity, Ticket[].class);
            return result.getBody();
        } catch (Exception e) {
            Log.e("LoginActivity", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Ticket[] u) {
        screen.GotText();
        screen.updateDetails(u);
    }

}
