package org.smartcity;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by ASAHU3 on 3/9/2018.
 */

public class TestJson {

    public static void main(String [] args) {
        Gson gson = new Gson();
        //String jsonInString = "{\"userId\":\"1\",\"userName\":\"Yasir\"}";
        String jsonInString1 = "{\"created\": \"Tue, 06 Mar 2018 15:30:13 GMT\",\"id\": \"aiBqfmFuZHJvaWRwdXNobm90aWZpY2F0aW9uLTE5NjAxNHIRCxIEVXNlchiAgICA3uqJCgw\",\"name\": \"ashish modified\",\"status\": \"Verified\"}";
        String jsonInString2 = "{\"created\": \"Tue, 06 Mar 2018 15:30:13 GMT\",\"id\": \"aiBqfmFuZHJvaWRwdXNobm90aWZpY2F0aW9uLTE5NjAxNHIRCxIEVXNlchiAgICA3uqJCgw\",\"name\": \"ashish modified\",\"status\": \"Verified\"}";
        String jsonInString = "["+jsonInString1 + "," + jsonInString2 + "]";
        //List<User> user= gson.fromJson(jsonInString, User.class);
        List<User> list = gson.fromJson(jsonInString, new TypeToken<List<User>>(){}.getType());

        //system.out.println();
        //Log.i("test","user details "+list.size());
//        Log.i("test","user details "+user.getName());
//        Log.i("test","user details "+user.getStatus());
//        Log.i("test","user details "+user.getCreated());

        User u = new User();
        String res = gson.toJson(u);
        u.setMobile_number("123456789");
        res = gson.toJson(u);
        u.setName("");
        u.setAddress("");
        res = gson.toJson(u);

    }
}
