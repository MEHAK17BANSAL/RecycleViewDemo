package mehak.com.recycleviewdemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mehak.com.recycleviewdemo.R;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

//import mehak.com.recycleviewdemo.service.BookFetchIntentService;
//import mehak.com.recycleviewdemo.service.BookFetchService;
import mehak.com.recycleviewdemo.adapter.TitleAdapter;
import mehak.com.recycleviewdemo.model.Title;
import mehak.com.recycleviewdemo.utility.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class title extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Title> titlelist;
    TitleAdapter titleadapter;

    // ResponseReceiver responseReceiver;

    void initViews(){
        recyclerView = findViewById(R.id.recyclerView);
        titlelist = new ArrayList<Title>();
    }

    boolean isInternetConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo!=null && networkInfo.isConnected());

    }

    void fetchBooks(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Util.URL_TITLE_FETCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray(Util.Title_ARRAY);

                            String p="",n="",a="";

                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jObj = jsonArray.getJSONObject(i);

                                 p = jObj.getString("sale_price");
                                // n = jObj.getString("name");
                                a = jObj.getString("title");

                                Title title = new Title(a,p);
                                titlelist.add(title);
                            }



                            Toast.makeText(title.this,"Title Fetched Successfully !!",Toast.LENGTH_LONG).show();

                            titleadapter = new TitleAdapter(title.this,R.layout.list_item,titlelist);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(title.this);
                          //  GridLayoutManager gridLayoutManager = new GridLayoutManager(title.this,2);

                            recyclerView.setLayoutManager(linearLayoutManager);
                          //  recyclerView.setLayoutManager(gridLayoutManager);

                            recyclerView.setAdapter(titleadapter);


                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(title.this,"Error while Fetching Books",Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }
        );

        requestQueue.add(stringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        initViews();

        if (isInternetConnected())
            fetchBooks();
        else
            Toast.makeText(this, "Please Connect to the Internet and Try Again", Toast.LENGTH_LONG).show();
    }
        }