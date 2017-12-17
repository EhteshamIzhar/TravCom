package com.android.parii.travcom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.parii.travcom.Model.Food;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

//import android.view.View;
//import com.cepheuen.elegantnumberbutton.ElegantNumberButton;
//import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

public class FoodDetail extends AppCompatActivity {

    TextView food_name,food_price,food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;

    ElegantNumberButton numberButton;
    FloatingActionButton btnCart;
    String foodId="";

    FirebaseDatabase database;
    DatabaseReference foods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        //firebase

        database= FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");

        //init
        numberButton=(ElegantNumberButton)findViewById(R.id.number_button);
        btnCart=(FloatingActionButton)findViewById(R.id.btnCart);

        food_description=(TextView)findViewById(R.id.food_description);
        food_name=(TextView)findViewById(R.id.food_name);
        food_price=(TextView)findViewById(R.id.food_price);
        food_image=(ImageView) findViewById(R.id.img_food);

        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        //get food from intent

        if(getIntent() != null)
        {
            Log.e("Yahan","Kuch keede hai");
            foodId = getIntent().getStringExtra("foodId");
            Log.e("Ye aaya hai","ok "+foodId);
        }
        if(!foodId.isEmpty())
        {

            getDetailFood(foodId);
        }


    }


    public void see(View v) {

     //   Intent i = new Intent(FoodDetail.this,MapViews.class);
       // startActivity(i);
    }





    private void getDetailFood(String foodId) {

        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Food food = dataSnapshot.getValue(Food.class);

               //set img
                Picasso.with(getBaseContext()).load(food.getImage())
                        .into(food_image);

                collapsingToolbarLayout.setTitle(food.getName());

                food_price.setText(food.getPrice());
                food_name.setText(food.getName());
                food_description.setText(food.getDescription());



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {



            }
        });

    }
}
