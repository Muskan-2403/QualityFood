package com.example.qualityfood;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.qualityfood.databinding.ActivityMainBinding;
import com.example.qualityfood.model.CocktailModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity {

    TextView textview;
    Dialog dialog;
    List<CocktailModel> list = new ArrayList<>();
    HashMap<String,String> namesIdMap = new HashMap<>();
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //search bar and see All on click
        Log.i("Muskan","Main Activity");
        handleSSLHandshake();
        loadApi();

        binding.seeAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,DrinksActivity.class);
                startActivity(i);
            }
        });
    }

    private void loadApi() {
        RequestQueue volleyQueue = Volley.newRequestQueue(MainActivity.this);
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

                            namesIdMap.put(jsonObject.getString("strDrink"),jsonObject.getString("idDrink"));
                            list.add(cm);
                            }
                        Log.i("Muskan", String.valueOf(list.size()));
                        List<String> namesList = new ArrayList<>();
                        for (int k=0;k<list.size();k++){
                            namesList.add(list.get(k).getStrDrink());
                        }
                        setSearchTab(namesList,namesIdMap,list);
                        setData(list);
                    } catch (JSONException e) {
                        Log.i("Muskan","JSON "+e);
                        e.printStackTrace();
                    }
                },
                (Response.ErrorListener) error -> {
                    Toast.makeText(MainActivity.this, "Some error occurred! Cannot fetch API", Toast.LENGTH_LONG).show();
                    Log.i("Muskan", error.toString());
                }
        );
        volleyQueue.add(jsonObjectRequest);
    }

    private void setSearchTab(List<String> namesList, HashMap<String, String> namesIdMap, List<CocktailModel> list) {
        binding.testView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                dialog.getWindow().setLayout(650,800);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                EditText editText=dialog.findViewById(R.id.edit_text);
                ListView listView=dialog.findViewById(R.id.list_view);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,namesList);
                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        // when item selected from list
//                        // set selected item on textView
////                        textview.setText(list.get(position).getStrDrink());
//                        Toast.makeText(MainActivity.this,position,Toast.LENGTH_LONG).show();
////                        String drinkId = namesIdMap.get(adapter.getItem(position));
//                        dialog.dismiss();
//                    }
//                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.i("Muskan ListView", list.get(position).getStrDrink());
                        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                        intent.putExtra("drink id", list.get(position).getIdDrink());
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void setData(List<CocktailModel> list) {
        Glide.with(this)
                .load(list.get(0).getStrDrinkThumb())
                .error(R.drawable.error_img)
                .override(200, 200)
                .centerCrop()
                .into(binding.Img1);
        Glide.with(this)
                .load(list.get(1).getStrDrinkThumb())
                .error(R.drawable.error_img)
                .override(200, 200)
                .centerCrop()
                .into(binding.Img2);
        Glide.with(this)
                .load(list.get(2).getStrDrinkThumb())
                .error(R.drawable.error_img)
                .override(200, 200)
                .centerCrop()
                .into(binding.Img3);
        Glide.with(this)
                .load(list.get(3).getStrDrinkThumb())
                .error(R.drawable.error_img)
                .override(200, 200)
                .centerCrop()
                .into(binding.Img4);

        binding.prodName1.setText(list.get(0).getStrDrink());
        binding.prodName2.setText(list.get(1).getStrDrink());
        binding.prodName3.setText(list.get(2).getStrDrink());
        binding.prodName4.setText(list.get(3).getStrDrink());

        binding.category1.setText(list.get(0).getStrCategory());
        binding.category2.setText(list.get(1).getStrCategory());
        binding.category3.setText(list.get(2).getStrCategory());
        binding.category4.setText(list.get(3).getStrCategory());

        binding.cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("drink id", list.get(0).getIdDrink());
                startActivity(intent);
            }
        });

        binding.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("drink id", list.get(1).getIdDrink());
                startActivity(intent);
            }
        });

        binding.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("drink id", list.get(2).getIdDrink());
                startActivity(intent);
            }
        });

        binding.cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("drink id", list.get(3).getIdDrink());
                startActivity(intent);
            }
        });
    }

    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }
}