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

public class HttpOTPRequestTask extends AsyncTask<User, Void, User> {
    public Login screen ;
    @Override
    protected User doInBackground(User... params) {
        try {
            User user = params[0];
            final String uri = screen.getString(R.string.login_url)+ "/"+ user.getId() + "/otp";
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<User> entity = new HttpEntity<User>(user, headers);

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<User[]> result = restTemplate.exchange(uri, HttpMethod.POST, entity, User[].class);
            User [] u = result.getBody();
            return u[0];
        } catch (Exception e) {
            Log.e("LoginActivity", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(User u) {
        screen.updateDetails(u);
        screen.GotText();
    }

}
