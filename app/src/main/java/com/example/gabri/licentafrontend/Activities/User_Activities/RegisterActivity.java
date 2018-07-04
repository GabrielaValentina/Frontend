package com.example.gabri.licentafrontend.Activities.User_Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gabri.licentafrontend.Domain.Mappers.UserMapper;
import com.example.gabri.licentafrontend.Domain.User;
import com.example.gabri.licentafrontend.R;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_USER;
import com.example.gabri.licentafrontend.Utils.Validators.Registration_Validator;
import com.squareup.okhttp.ResponseBody;

import java.util.Random;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by gabri on 3/26/2018.
 */

public class RegisterActivity extends Activity {

    private String ip;
    private String url;
    Random random;

    private EditText editTextLastName;
    private EditText editTextFirstName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPhoneNumber;

    private Registration_Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg);

        ip = getIntent().getExtras().getString("arg_ip");
        url = "http://"+ ip +":8080";

        editTextLastName = (EditText) findViewById(R.id.editTextLastNameREGISTER);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstNameREGISTER);
        editTextEmail = (EditText) findViewById(R.id.editTextEmailREGISTER);
        editTextPassword = (EditText) findViewById(R.id.editTextPasswordREGISTER);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumberREGISTER);

    }

    public void sendCode(UserMapper user){
        random = new Random();
        final String code = "" + random.nextInt(((9999 - 1000) + 1)+1000);
        user.setCode(code);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitAPI_USER retrofitAPI_user = retrofit.create(RetrofitAPI_USER.class);

        Call<String> call = retrofitAPI_user.addNewUser(user, "STRING");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(retrofit.Response<String> response, Retrofit retrofit) {
                try {
                    String a = response.body() + "";
                    if(a.equals("nu")){
                        show_message("Această adresă de email a fost folosită de un alt utilizator!");
                    }
                    else {
                    //    show_message( "Am trimis codul corespunzător înregistrării pe adresa de email " +
                            //    user.getEmailAddress() + ". Codul este valabil 24 ore");
                        Intent intent = new Intent(getApplicationContext(), RegistrationConfirmation_Activity.class);
                        intent.putExtra("arg_ip", ip);
                        intent.putExtra("email", editTextEmail.getText().toString());
                        startActivity(intent);
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

    public void show_message(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle("Date incorecte!");

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

    public void buttonRegister(View view){
        String lastName = editTextLastName.getText().toString();
        String firstName = editTextFirstName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String number = editTextPhoneNumber.getText().toString();
        validator = new Registration_Validator();

        try{
            validator.validator(firstName, lastName, email, number, password);
            sendCode(new UserMapper(lastName, firstName, email, password, number, ""));
        }catch (Exception ex){
            show_message(ex.getMessage());
        }
    }
}
