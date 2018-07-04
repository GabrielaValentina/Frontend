package com.example.gabri.licentafrontend.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gabri.licentafrontend.Domain.MessageAdapter;
import com.example.gabri.licentafrontend.R;

import java.util.ArrayList;

/**
 * Created by gabri on 6/2/2018.
 */

public class MessagesLIstViewAdapter extends ArrayAdapter<MessageAdapter> {

    private Context myContext;
    private int myRecouce;
    private int[] colors = new int[] {0x30FF0000, 0x300000FF};
    int color = Color.parseColor("#FFE4C4");
    int color1 = Color.parseColor("#B0C4DE");
    private int[] colors1 = new int[] {color, color1};

    public MessagesLIstViewAdapter(Context context, int resouce, ArrayList<MessageAdapter> objects){
        super(context, resouce, objects);
        myContext = context;
        myRecouce = resouce;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String name = getItem(position).getName();
        String message = getItem(position).getMessage();
        String data = getItem(position).getData();

        LayoutInflater inflater = LayoutInflater.from(myContext);
        convertView = inflater.inflate(myRecouce, parent, false);

        TextView text = (TextView) convertView.findViewById(R.id.textViewMessageChatADAPTER);
        text.setText(name + ": " + message);
       // text.setBackgroundColor(colors[position % colors.length]);

        TextView textArrivalTime = (TextView) convertView.findViewById(R.id.textViewDataChatADAPTER);
        textArrivalTime.setText(data + "");

        int colorPos = position % colors1.length;
      //  convertView.setBackgroundColor(colors[colorPos]);
        convertView.setBackgroundColor(colors1[colorPos]);
        return convertView;
    }
}
