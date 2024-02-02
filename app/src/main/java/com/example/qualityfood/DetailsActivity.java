package com.example.qualityfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.qualityfood.Adapters.IngredientsAdapter;
import com.example.qualityfood.databinding.ActivityDetailsBinding;
import com.example.qualityfood.databinding.ActivityMainBinding;
import com.example.qualityfood.model.CocktailModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    CocktailModel cocktailModel;
    List<List<String>> mapList = new ArrayList<>();
    ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //back Btn code

        Bundle bundle = getIntent().getExtras();
        String drinkId = bundle.getString("drink id");
        loadApi(drinkId);

        binding.prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void loadApi(String drinkId) {
        RequestQueue volleyQueue = Volley.newRequestQueue(DetailsActivity.this);
        String url = "https://thecocktaildb.com/api/json/v1/1/search.php?s=mojito";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                (Response.Listener<JSONObject>) response -> {
                    try {
//                        JSONObject obj  = response.getJSONObject("");
                        Log.i("Muskan Details", response.toString());
                        JSONArray jsonArray = response.getJSONArray("drinks");

                        //Iterate the jsonArray and print the info of JSONObjects
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Log.i("Muskan Details", jsonObject.getString("strDrink"));
                            if (jsonObject.getString("idDrink").equals(drinkId)) {
                                cocktailModel = new CocktailModel(jsonObject.getString("idDrink"), jsonObject.getString("strDrink"),
                                        jsonObject.getString("strCategory")
                                        , jsonObject.getString("strGlass"), jsonObject.getString("strInstructions"),
                                        jsonObject.getString("strDrinkThumb"),
                                        jsonObject.getString("strIngredient1")
                                        , jsonObject.getString("strIngredient2"), jsonObject.getString("strIngredient3"),
                                        jsonObject.getString("strIngredient4"),
                                        jsonObject.getString("strIngredient5")
                                        , jsonObject.getString("strIngredient6"), jsonObject.getString("strIngredient7"),
                                        jsonObject.getString("strIngredient8"),
                                        jsonObject.getString("strIngredient9")
                                        , jsonObject.getString("strIngredient10"), jsonObject.getString("strIngredient11"),
                                        jsonObject.getString("strIngredient12"),
                                        jsonObject.getString("strIngredient13")
                                        , jsonObject.getString("strIngredient14"), jsonObject.getString("strIngredient15"),
                                        jsonObject.getString("strMeasure1"), jsonObject.getString("strMeasure2")
                                        , jsonObject.getString("strMeasure3"), jsonObject.getString("strMeasure4"), jsonObject.getString("strMeasure5"),
                                        jsonObject.getString("strMeasure6")
                                        , jsonObject.getString("strMeasure7"), jsonObject.getString("strMeasure8"), jsonObject.getString("strMeasure9"),
                                        jsonObject.getString("strMeasure10")
                                        , jsonObject.getString("strMeasure11"), jsonObject.getString("strMeasure12"),
                                        jsonObject.getString("strMeasure13"), jsonObject.getString("strMeasure14")
                                        , jsonObject.getString("strMeasure15"), jsonObject.getString("strImageSource"));
                                SetData(cocktailModel);
                            }
                        }
                    } catch (JSONException e) {
                        Log.i("Muskan Details", "JSON " + e);
                        e.printStackTrace();
                    }
                },
                (Response.ErrorListener) error -> {
                    Toast.makeText(DetailsActivity.this, "Some error occurred! Cannot fetch API", Toast.LENGTH_LONG).show();
                    Log.i("Muskan Details", error.toString());
                }
        );
        volleyQueue.add(jsonObjectRequest);
    }

    private void SetData(CocktailModel cm) {
        binding.prodName.setText(cm.getStrDrink());
        binding.category.setText(cm.getStrCategory());
        binding.glassType.setText(cm.getStrGlass());
        binding.instructions.setText(cm.getStrInstructions());

        Glide.with(this)
                .load(cm.getStrDrinkThumb())
                .error(R.drawable.error_img)
                .override(200, 200)
                .centerCrop()
                .into(binding.Img);

        if (cm.getStrIngredient1()!="null"){
            List<String> list = new ArrayList<>();
            list.add(cm.getStrIngredient1());
            list.add(cm.getStrMeasure1());
            mapList.add(list);
        }
        if (cm.getStrIngredient2()!="null"){
            List<String> list = new ArrayList<>();
            list.add(cm.getStrIngredient2());
            list.add(cm.getStrMeasure2());
            mapList.add(list);
        }
        if (cm.getStrIngredient3()!="null"){
            List<String> list = new ArrayList<>();
            list.add(cm.getStrIngredient3());
            list.add(cm.getStrMeasure3());
            mapList.add(list);
        }
        if (cm.getStrIngredient4()!="null"){
            List<String> list = new ArrayList<>();
            list.add(cm.getStrIngredient4());
            list.add(cm.getStrMeasure4());
            mapList.add(list);
        }
        if (cm.getStrIngredient5()!="null"){
            List<String> list = new ArrayList<>();
            list.add(cm.getStrIngredient5());
            list.add(cm.getStrMeasure5());
            mapList.add(list);
        }
        if (cm.getStrIngredient6()!="null"){
            List<String> list = new ArrayList<>();
            list.add(cm.getStrIngredient6());
            list.add(cm.getStrMeasure6());
            mapList.add(list);
        }
        if (cm.getStrIngredient7()!="null"){
            List<String> list = new ArrayList<>();
            list.add(cm.getStrIngredient7());
            list.add(cm.getStrMeasure7());
            mapList.add(list);
        }
        if (cm.getStrIngredient8()!="null"){
            List<String> list = new ArrayList<>();
            list.add(cm.getStrIngredient8());
            list.add(cm.getStrMeasure8());
            mapList.add(list);
        }
        if (cm.getStrIngredient9()!="null"){
            List<String> list = new ArrayList<>();
            list.add(cm.getStrIngredient9());
            list.add(cm.getStrMeasure9());
            mapList.add(list);
        }
        if (cm.getStrIngredient10()!="null"){
            List<String> list = new ArrayList<>();
            list.add(cm.getStrIngredient10());
            list.add(cm.getStrMeasure10());
            mapList.add(list);
        }
        if (cm.getStrIngredient11()!="null"){
            List<String> list = new ArrayList<>();
            list.add(cm.getStrIngredient11());
            list.add(cm.getStrMeasure11());
            mapList.add(list);
        }
        if (cm.getStrIngredient12()!="null"){
            List<String> list = new ArrayList<>();
            list.add(cm.getStrIngredient12());
            list.add(cm.getStrMeasure12());
            mapList.add(list);
        }
        if (cm.getStrIngredient13()!="null"){
            List<String> list = new ArrayList<>();
            list.add(cm.getStrIngredient13());
            list.add(cm.getStrMeasure13());
            mapList.add(list);
        }
        if (cm.getStrIngredient14()!="null"){
            List<String> list = new ArrayList<>();
            list.add(cm.getStrIngredient14());
            list.add(cm.getStrMeasure14());
            mapList.add(list);
        }
        if (cm.getStrIngredient15()!="null"){
            List<String> list = new ArrayList<>();
            list.add(cm.getStrIngredient15());
            list.add(cm.getStrMeasure15());
            mapList.add(list);
        }

//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(mapList);
//        recyclerView.setAdapter(ingredientsAdapter);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(DetailsActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(ingredientsAdapter);
    }
}