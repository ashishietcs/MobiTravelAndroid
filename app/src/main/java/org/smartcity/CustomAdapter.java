package org.smartcity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by tf6ghij on 3/12/2018.
 */
public class CustomAdapter extends BaseAdapter {
    Context context;
    Ticket ticketList[];
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, Ticket[] ticketList) {
        this.context = context;
        this.ticketList = ticketList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return ticketList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_listview, null);
        TextView fromtv = (TextView) view.findViewById(R.id.fromTextView);
        fromtv.setText(ticketList[i].getTo());
        TextView totv = (TextView) view.findViewById(R.id.toTextView);
        totv.setText(ticketList[i].getFrom());
        TextView persontv = (TextView) view.findViewById(R.id.personTextView);
        persontv.setText(ticketList[i].getNo_persons());
        TextView datetv = (TextView) view.findViewById(R.id.dateTextView);
        datetv.setText(ticketList[i].getCreated());
        return view;
    }
}