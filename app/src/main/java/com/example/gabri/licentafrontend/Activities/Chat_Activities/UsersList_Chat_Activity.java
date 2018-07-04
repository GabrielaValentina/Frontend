package com.example.gabri.licentafrontend.Activities.Chat_Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabri.licentafrontend.Adapters.UsersListChat_Adapter;
import com.example.gabri.licentafrontend.Domain.User;
import com.example.gabri.licentafrontend.Domain.UsersListChatAdapter;
import com.example.gabri.licentafrontend.R;
import com.example.gabri.licentafrontend.Repository.LocalRepository;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_USER;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by gabri on 5/25/2018.
 */

public class UsersList_Chat_Activity extends AppCompatActivity{

    private ListView users_listView;
    UsersListChat_Adapter adapter;

    private ArrayList<UsersListChatAdapter> users_list;

    private String url;
    private String last_name_USER;
    private String first_name_USER;
    private String email_address_USER;
    private String password_USER;
    private String phone_number_USER;
    private String id_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_list_chat_activity);

        url = getIntent().getExtras().getString("url");
        id_USER = getIntent().getExtras().getString("id_user");
        last_name_USER = getIntent().getExtras().getString("last_name");
        first_name_USER = getIntent().getExtras().getString("first_name");
        email_address_USER = getIntent().getExtras().getString("email_address");
        password_USER = getIntent().getExtras().getString("password");
        phone_number_USER = getIntent().getExtras().getString("phone_number");

        this.users_listView = (ListView) findViewById(R.id.listViewUsersCHATACTIVITY);

        populate_listView();

        users_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String name = adapter.getItem(position).getName();
                String isOnline = adapter.getItem(position).getOnline().toString();
                String id_user = adapter.getItem(position).getId_user().toString();
                Toast toast = Toast.makeText(getApplicationContext(), name + " " + isOnline + " " +id_user , Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), Chat_Activity.class);
                intent.putExtra("name", name);
                intent.putExtra("id_sender", id_USER);
                intent.putExtra("id_receiver", id_user);
                startActivity(intent);
            }
        });
    }

    private void request_users_from_server(){
        this.users_list = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.3:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitAPI_USER retrofitAPI_user = retrofit.create(RetrofitAPI_USER.class);

        Call<List<User>> call = retrofitAPI_user.getAllUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(retrofit.Response<List<User>> response, Retrofit retrofit) {

                try {
                    UsersListChatAdapter new_user = new UsersListChatAdapter();
                    for(User user : response.body()) {
                        //cauta daca userul curent e in vreun tren
                        new_user = new UsersListChatAdapter(user.getId(), user.getFirstName() + " " + user.getLastName(),
                                "Trenul 1254", false);
                        Log.d("users->", user.getFirstName());
                        users_list.add(new_user);
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "error onResponse");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }

    private void populate_users_list(){
        this.users_list = new ArrayList<>();
        LocalRepository repository = new LocalRepository();
        for(User u : repository.getAllUsers())
         users_list.add(new UsersListChatAdapter(u.getId(), u.getFirstName() + " " + u.getLastName(),
                 "Trenul 1234", false));
    }

    private void populate_listView() {
       // request_users_from_server();
        populate_users_list();
        adapter = new UsersListChat_Adapter(this, R.layout.list_view_adapter_users_chat, users_list);
        users_listView.setAdapter(adapter);
    }
}
