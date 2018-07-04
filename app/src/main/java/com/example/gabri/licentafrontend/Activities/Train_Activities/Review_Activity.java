package com.example.gabri.licentafrontend.Activities.Train_Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.gabri.licentafrontend.Domain.Review;
import com.example.gabri.licentafrontend.R;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_REVIEW;

import java.sql.Timestamp;
import java.util.Date;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by gabri on 4/17/2018.
 */

public class Review_Activity extends AppCompatActivity implements View.OnClickListener {


    private final String REVIEW_TYPE_TRAIN = "review_for_train";
    private final String REVIEW_TYPE_ROUTE = "review_for_route";
    private String url;

    private RadioButton radioButtonForTrain;
    private RadioButton radioButtonForRoute;
    private RatingBar ratingBarForStars;
    private EditText editTextComment;
    private Button buttonAddReview;
    private String comment;

    private Long id_user;
    private String currentTrain;
    private String currentRoute; // [id_route;current_data]
    private String email_address;
    private String user_name;

    private String reviewType;
    private double starsNumber;

    private Review review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_page);

        //take info from fragment
        this.id_user = Long.parseLong(getIntent().getExtras().getString("id_user"));
        this.user_name = getIntent().getExtras().getString("userName");
        this.currentRoute = getIntent().getExtras().getString("currentRoute");
        this.currentTrain = getIntent().getExtras().getString("currentTrain");
        this.email_address = getIntent().getExtras().getString("email");
        this.url = getIntent().getExtras().getString("url");

        //initialize the elements of the activity
        this.radioButtonForTrain = (RadioButton) findViewById(R.id.radioButtonReviewForTrain);
        this.radioButtonForRoute = (RadioButton) findViewById(R.id.radioButtonReviewForRoue);
        this.ratingBarForStars = (RatingBar) findViewById(R.id.ratingBarReview);
        this.editTextComment = (EditText) findViewById(R.id.editTextCommentREVIEWACTIVITY);
        this.buttonAddReview = (Button) findViewById(R.id.buttonSaveReviewREVIEWACTIVITY);
        this.reviewType = "";
        this.starsNumber = 0;
        this.comment = "";
        this.review = new Review();

        //make this edit text on click listener to clean it
        this.editTextComment.setOnClickListener(this);

        //on click button to add review
        this.buttonAddReview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //get the comment
                comment = editTextComment.getText().toString();
                //if the user doesn't write a comment
                if (comment.equals("Adauga un comentariu..."))
                    comment = "";

                if (reviewType.equals("")) {
                    show_message("Trebuie să specificați pentru care variantă realizați recenzia!");
                } else {
                    if (reviewType.equals(REVIEW_TYPE_TRAIN)) {
                        //review for train
                        Timestamp time = new Timestamp(System.currentTimeMillis());
                        String time_string = time + "";
                        review = new Review(id_user, user_name, comment, starsNumber, reviewType, currentTrain, time_string);
                        //send review to server
                        addNewReview(review);
                    } else if (reviewType.equals(REVIEW_TYPE_ROUTE)) {
                        Date date = new Date();
                        Timestamp time = new Timestamp(date.getTime());
                        //review for route
                        review = new Review(id_user, user_name, comment, starsNumber, reviewType, currentRoute, time + "");
                        addNewReview(review);
                    }
                }
            }
        });

        this.ratingBarForStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                starsNumber = rating;
            }
        });
    }

    //on click radio button
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButtonReviewForTrain:
                if (checked)
                    this.reviewType = REVIEW_TYPE_TRAIN;
                break;
            case R.id.radioButtonReviewForRoue:
                if (checked)
                    this.reviewType = REVIEW_TYPE_ROUTE;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (editTextComment.getText().toString().equals("Adauga un comentariu..."))
            editTextComment.setText("");
    }

    public void addNewReview(Review review) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitAPI_REVIEW retrofitAPI = retrofit.create(RetrofitAPI_REVIEW.class);

        Call<Review> call = retrofitAPI.savePost(review);

        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(retrofit.Response<Review> response, Retrofit retrofit) {

                try {
                    show_message("Adaugarea recenziei cu succes!");
                } catch (Exception e) {
                    Log.d("onResponse", "error onResponse");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
               show_message("Nu există conexiune la internet!");
            }
        });
    }

    public void show_message(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle("Adăugare recenzie");

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
