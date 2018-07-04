package com.example.gabri.licentafrontend.Activities.Chat_Activities;

/**
 * Created by gabri on 3/27/2018.
 */

import com.example.gabri.licentafrontend.Adapters.MessagesLIstViewAdapter;
import com.example.gabri.licentafrontend.Adapters.SearchedRoutesAdapter;
import com.example.gabri.licentafrontend.Domain.DetailsAboutReview;
import com.example.gabri.licentafrontend.Domain.Mappers.ChatMessageMapper;
import com.example.gabri.licentafrontend.Domain.MessageAdapter;
import com.example.gabri.licentafrontend.Domain.Review;
import com.example.gabri.licentafrontend.R;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_CHAT;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_REVIEW;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.WebSocket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Chat_Activity extends AppCompatActivity {

    private StompClient mStompClient;
    private Context context;

    private Button buttonSend;
    private EditText editTextMessage;
    private ListView listViewMessages;

    private String id_sender;
    private String name_sender;
    private String url;
    private String ip;
    private ArrayList<MessageAdapter> messages_list;
    private MessagesLIstViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        context = this.getApplicationContext();
        this.listViewMessages = (ListView) findViewById(R.id.listViewMessagesCHAT);
        this.messages_list = new ArrayList<>();
        adapter = new MessagesLIstViewAdapter(this, R.layout.list_view_adapter_message, messages_list);
        listViewMessages.setAdapter(adapter);

        this.ip = getIntent().getExtras().getString("ip");
        this.url = getIntent().getExtras().getString("url");
        this.id_sender = getIntent().getExtras().getString("id_user");
        this.name_sender = getIntent().getExtras().getString("name");

        connectStomp();

        populateList();

        this.editTextMessage = (EditText) findViewById(R.id.textViewMessageCHAT);

        buttonSend = (Button)findViewById(R.id.buttonSENDCHAT);
        buttonSend.setOnClickListener(v -> {
           pushButtonSend();
        });
    }

    private void populateList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitAPI_CHAT retrofitAPI_review = retrofit.create(RetrofitAPI_CHAT.class);
        messages_list = new ArrayList<>();
        Call<List<ChatMessageMapper>> call = retrofitAPI_review.getAllMessages();

        call.enqueue(new Callback<List<ChatMessageMapper>>() {
            @Override
            public void onResponse(retrofit.Response<List<ChatMessageMapper>> response, Retrofit retrofit) {
                if(response.body()!=null) {
                    for (ChatMessageMapper message : response.body()) {
                        messages_list.add(new MessageAdapter(Long.parseLong("0"), Long.parseLong(message.getId_sender() + ""), message.getName(),
                                message.getMessage(), message.getSentAt().substring(11, 16) + "   "));
                    }
                    listViewMessages.setAdapter(null);
                    adapter = new MessagesLIstViewAdapter(context, R.layout.list_view_adapter_message, messages_list);
                    listViewMessages.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }

    private void connectStomp() {
        mStompClient = Stomp.over(WebSocket.class, "ws://"+ ip + ":8080/websocket");
        mStompClient.topic("/topic/greetings")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {
                    messages_list = new ArrayList<>();
                    try{
                        JSONArray jsonArr = new JSONArray(topicMessage.getPayload());
                        for (int i = 0; i < jsonArr.length(); i++)
                        {
                            JSONObject jsonObj = jsonArr.getJSONObject(i);
                            Long id = jsonObj.getLong("id");
                            Long id_sender = jsonObj.getLong("idSender");
                            String name = jsonObj.getString("name");
                            String message = jsonObj.getString("message");
                            String sentAt = jsonObj.getString("sentAt");
                            messages_list.add(new MessageAdapter(id, id_sender, name, message, sentAt.substring(11,16) + "   "));
                        }
                        listViewMessages.setAdapter(null);
                        adapter = new MessagesLIstViewAdapter(this, R.layout.list_view_adapter_message, messages_list);
                        listViewMessages.setAdapter(adapter);
                    } catch (JSONException ex){
                        Log.d("exception", ex.getMessage());
                    }
                });
        mStompClient.connect();
        mStompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
                            Log.d("Stomp connection opened","");
                            break;
                        case ERROR:
                            Log.d("Stomp connection error","");
                            show_message("Este necesară conexiunea la internet!");
                            break;
                        case CLOSED:
                            Log.d("Stomp connection closed","");
                            break;
                    }
                });
    }

    private void pushButtonSend(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+ ip + ":8080/sendMessage")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RetrofitAPI_CHAT retrofitAPI_chat = retrofit.create(RetrofitAPI_CHAT.class);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        String time_string = time + "";
        String message_text = editTextMessage.getText().toString();
        ChatMessageMapper message = new ChatMessageMapper(Integer.parseInt(id_sender+""),
                name_sender, message_text, time_string);

        Call<ChatMessageMapper> call = retrofitAPI_chat.sendNewMessage(message);
        call.enqueue(new Callback<ChatMessageMapper>() {
            @Override
            public void onResponse(retrofit.Response<ChatMessageMapper> response, Retrofit retrofit) {
                try {
                    Log.d("message:", response.body().toString());
                } catch (Exception e) {
                    Log.d("onResponse", e.getMessage());
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                show_message("Este necesară conexiunea la internet!");
            }
        });
        this.editTextMessage.setText("");
    }

    private void pushButtonSendbun(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.6:8080/sendMessage")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RetrofitAPI_CHAT retrofitAPI_chat = retrofit.create(RetrofitAPI_CHAT.class);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        String time_string = time + "";
        String message_text = "";
        message_text =  editTextMessage.getText().toString();
        ChatMessageMapper message = new ChatMessageMapper(Integer.parseInt(id_sender), name_sender, message_text, time_string);
        Call<ChatMessageMapper> call = retrofitAPI_chat.sendNewMessage(message);

        call.enqueue(new Callback<ChatMessageMapper>() {
            @Override
            public void onResponse(retrofit.Response<ChatMessageMapper> response, Retrofit retrofit) {
                try {

                } catch (Exception e) {
                    Log.d("onResponse", "error onResponse");
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                show_message("Este necesară conexiunea la internet!");
            }
        });
        this.editTextMessage.setText("");
    }

    public void show_message(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle("Chat");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        AlertDialog alert =builder.create();
        alert.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#ffa500"));
            }
        });
        alert.setIcon( R.drawable.smile1);
        alert.show();
    }
}
