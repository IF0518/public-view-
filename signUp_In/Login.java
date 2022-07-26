package com.example.portal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONObject;
import org.json.JSONException;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;



public class Login extends AppCompatActivity {

     EditText userN, pass;
     Button login;
     TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(SharedPrefer.getInstance(this).isLogged()){
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);


        }
        userN = findViewById(R.id.Uname);
        pass = findViewById(R.id.pass);
        login = findViewById(R.id.login);
        register = findViewById(R.id.reg);

        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                userLogin();
            }

        });
        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
                Intent intent = new Intent(getApplicationContext(), Verify.class);
                startActivity(intent);
            }
        });
    }

    public void userLogin(){


        String username= userN.getText().toString();
        String password= pass.getText().toString();

        if(TextUtils.isEmpty(username)){
            userN.setError("Username required");
            userN.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){
            pass.setError("Password required");
            pass.requestFocus();
            return;
        }
       StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.login_url,
               new Response.Listener<String>() {
                   public void onResponse(String response) {
                       try {
                           JSONObject obj = new JSONObject(response);

                           if (!obj.getBoolean("error")) {
                               Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                               JSONObject object = obj.getJSONObject("user");

                               User user = new User(object.getString("username"),
                                       object.getString("password"),
                                       object.getString("firstname"),
                                       object.getString("lastname"),
                                       object.getString("phone"),
                                       object.getString("email"),
                                       object.getString("gender"));
                               SharedPrefer.getInstance(getApplicationContext()).userData(user);
                               finish();

                               startActivity(new Intent(getApplicationContext(), MainActivity.class));



                           } else {
                               Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                           }
                       } catch (JSONException e) {
                           e.printStackTrace();

                       }
                   }
               },

           new Response.ErrorListener(){
               public void onErrorResponse(VolleyError error){
                   Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                       }

                   }) {
           @Nullable
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> params = new HashMap<>();
               params.put("username", username);
               params.put("password", password);

               return params;
           }
       };
        VolleyLibrary.getInstance(this).addToRequestQueue(stringRequest);


       }



    }
