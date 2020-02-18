package vn.poly.volley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.poly.volley.model.FlickrPhoto;
import vn.poly.volley.model.Photo;

public class MainActivity extends AppCompatActivity {

    String httpGet = "https://jsonplaceholder.typicode.com/todos/1";
    String httpPost = "https://jsonplaceholder.typicode.com/posts";
    // param là
    //      title: 'foo',
    //      body: 'bar',
    //      userId: 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void httpGet(View view) {

        final TextView textView = findViewById(R.id.textView);
        RequestQueue requestQueue =
                Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                httpGet, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                User1 user1 = gson.fromJson(response, User1.class);
                textView.setText(user1.getTitle());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText(error.getMessage());
            }
        });

        requestQueue.add(stringRequest);
    }

    public void httpPost(View view) {
        final TextView textView = findViewById(R.id.textView);
        RequestQueue requestQueue =
                Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://www.flickr.com/services/rest", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();

                FlickrPhoto flickrPhoto =
                        gson.fromJson(response, FlickrPhoto.class);

                List<Photo> photos = flickrPhoto.getPhotos().getPhoto();

                textView.setText("Count " + photos.size());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // param là
                //      title: 'foo',
                //      body: 'bar',
                //      userId: 1
                Map<String, String> params = new HashMap<>();
                params.put("api_key", "24bf810575bc5bfbe2aef1ed6cd4517b");
                params.put("user_id", "184057905@N03");
                params.put("extras", "views, media, path_alias, url_sq, url_t, url_s, url_q, url_m, url_n, url_z, url_c, url_l, url_o");
                params.put("format", "json");
                params.put("method", "flickr.favorites.getList");
                params.put("nojsoncallback", "1");


//                api_key:24bf810575bc5bfbe2aef1ed6cd4517b
//                user_id:184057905@N03
//                extras:views, media, path_alias, url_sq, url_t, url_s, url_q, url_m, url_n, url_z, url_c, url_l, url_o
//                format:json
//                method:flickr.favorites.getList
//                nojsoncallback:1
//                per_page:10
//                page:0

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
