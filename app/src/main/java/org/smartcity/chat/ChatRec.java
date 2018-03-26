package org.smartcity.chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.smartcity.R;

/**
 * Created by beast on 14/4/17.
 */

public class ChatRec extends RecyclerView.ViewHolder  {



    TextView leftText,rightText;

    public ChatRec(View itemView){
        super(itemView);

        leftText = (TextView)itemView.findViewById(R.id.leftText);
        rightText = (TextView)itemView.findViewById(R.id.rightText);


    }
}