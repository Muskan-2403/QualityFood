package com.example.qualityfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.qualityfood.Adapters.DrinksAdapter;
import com.example.qualityfood.Adapters.IngredientsAdapter;
import com.example.qualityfood.databinding.ActivityDrinksBinding;
import com.example.qualityfood.databinding.ActivityMainBinding;
import com.example.qualityfood.model.CocktailModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DrinksActivity extends AppCompatActivity {

    List<CocktailModel> list = new ArrayList<>();
    ActivityDrinksBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDrinksBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        loadApi();

        binding.prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DrinksActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void loadApi() {
        RequestQueue volleyQueue = Volley.newRequestQueue(DrinksActivity.this);
        String url = "https://thecocktaildb.com/api/json/v1/1/search.php?s=mojito";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                (Response.Listener<JSONObject>) response -> {
                    try {
//                        JSONObject obj  = response.getJSONObject("");
                        Log.i("Muskan",response.toString());
                        JSONArray jsonArray = response.getJSONArray("drinks");

                        //Iterate the jsonArray and print the info of JSONObjects
                        for(int i=0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Log.i("Muskan", jsonObject.getString("strDrink"));
                            CocktailModel cm = new CocktailModel(jsonObject.getString("idDrink"),jsonObject.getString("strDrink"),
                                    jsonObject.getString("strCategory")
                                    ,jsonObject.getString("strGlass"),jsonObject.getString("strInstructions"),
                                    jsonObject.getString("strDrinkThumb"),
                                    jsonObject.getString("strIngredient1")
                                    ,jsonObject.getString("strIngredient2"),jsonObject.getString("strIngredient3"),
                                    jsonObject.getString("strIngredient4"),
                                    jsonObject.getString("strIngredient5")
                                    ,jsonObject.getString("strIngredient6"),jsonObject.getString("strIngredient7"),
                                    jsonObject.getString("strIngredient8"),
                                    jsonObject.getString("strIngredient9")
                                    ,jsonObject.getString("strIngredient10"),jsonObject.getString("strIngredient11"),
                                    jsonObject.getString("strIngredient12"),
                                    jsonObject.getString("strIngredient13")
                                    ,jsonObject.getString("strIngredient14"),jsonObject.getString("strIngredient15"),
                                    jsonObject.getString("strMeasure1"),jsonObject.getString("strMeasure2")
                                    ,jsonObject.getString("strMeasure3"),jsonObject.getString("strMeasure4"),jsonObject.getString("strMeasure5"),
                                    jsonObject.getString("strMeasure6")
                                    ,jsonObject.getString("strMeasure7"),jsonObject.getString("strMeasure8"),jsonObject.getString("strMeasure9"),
                                    jsonObject.getString("strMeasure10")
                                    ,jsonObject.getString("strMeasure11"),jsonObject.getString("strMeasure12"),
                                    jsonObject.getString("strMeasure13"),jsonObject.getString("strMeasure14")
                                    ,jsonObject.getString("strMeasure15"),jsonObject.getString("strImageSource"));

                            list.add(cm);
                        }
                        Log.i("Muskan DrinksActivity", String.valueOf(list.size()));
                        DrinksAdapter drinksAdapter = new DrinksAdapter(getApplicationContext(),list);
                        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(DrinksActivity.this);
                        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        RecyclerView mRecyclerView = findViewById(R.id.recView);
                        mRecyclerView.setHasFixedSize(true);
                        mRecyclerView.setLayoutManager(mLinearLayoutManager);
                        mRecyclerView.setAdapter(drinksAdapter);
                    } catch (JSONException e) {
                        Log.i("Muskan","JSON "+e);
                        e.printStackTrace();
                    }
                },
                (Response.ErrorListener) error -> {
                    Toast.makeText(DrinksActivity.this, "Some error occurred! Cannot fetch API", Toast.LENGTH_LONG).show();
                    Log.i("Muskan", error.toString());
                }
        );
        volleyQueue.add(jsonObjectRequest);
    }
}