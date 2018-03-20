package org.smartcity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by tf6ghij on 3/12/2018.
 */
public class CustomAdapter extends ArrayAdapter<Ticket> implements View.OnClickListener {
    Context context;
    private ArrayList<Ticket> ticketList;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, ArrayList<Ticket> ticketList) {
        super(applicationContext, R.layout.activity_listview, ticketList);
        this.context = context;
        this.ticketList = ticketList;
        inflter = (LayoutInflater.from(applicationContext));
    }


    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Ticket dataModel=(Ticket)object;
        Toast.makeText(getContext(), "Clicked on ticket ",Toast.LENGTH_SHORT).show();
    }



    @Override
    public int getCount() {
        return ticketList.size();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Ticket ticket = getItem(i);
        view = inflter.inflate(R.layout.activity_listview, null);
        TextView fromtv = (TextView) view.findViewById(R.id.fromTextView);
        fromtv.setText(ticket.getTo());
        TextView totv = (TextView) view.findViewById(R.id.toTextView);
        totv.setText(ticket.getFrom());
        TextView persontv = (TextView) view.findViewById(R.id.personTextView);
        persontv.setText(ticket.getNo_persons());
        TextView datetv = (TextView) view.findViewById(R.id.dateTextView);
        datetv.setText(ticket.getCreated());
        return view;
    }
}