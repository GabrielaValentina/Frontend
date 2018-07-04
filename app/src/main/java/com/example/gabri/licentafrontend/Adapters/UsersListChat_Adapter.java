package com.example.gabri.licentafrontend.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabri.licentafrontend.Domain.UsersListChatAdapter;
import com.example.gabri.licentafrontend.R;

import java.util.ArrayList;

/**
 * Created by gabri on 5/25/2018.
 */

public class UsersListChat_Adapter  extends ArrayAdapter<UsersListChatAdapter> {

    private Context myContext;
    private int myRecouce;
  //  private int[] colors = new int[] {0x30FF0000, 0x300000FF};

    // 789299, #bbc8cc
    public UsersListChat_Adapter(Context context, int resouce, ArrayList<UsersListChatAdapter> objects){
        super(context, resouce, objects);
        myContext = context;
        myRecouce = resouce;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String name = getItem(position).getName().toString();
        String train = getItem(position).getCurrent_train().toString();
        Boolean isOnline = getItem(position).getOnline();

        Log.d("users->", "adapt" + name);

        UsersListChatAdapter adapter = new UsersListChatAdapter(name, train, isOnline);

        LayoutInflater inflater = LayoutInflater.from(myContext);
        convertView = inflater.inflate(myRecouce, parent, false);

        TextView textName = (TextView) convertView.findViewById(R.id.textViewUserNameCHATUSERSACTIVITY);
        textName.setText( "\n" + name);

        TextView textTrain = (TextView) convertView.findViewById(R.id.textViewNumberOfTrainCHATUSERSACTIVITY);
        textTrain.setText("\n" + train);

        TextView textOnline = (TextView) convertView.findViewById(R.id.textViewIsOnlineCHATUSERSACTIVITY);
        textOnline.setText("");

     //   int colorPos = position % colors.length;
        return convertView;

    }
}
