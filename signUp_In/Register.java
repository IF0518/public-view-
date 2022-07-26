package com.example.portal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;
import android.content.Intent;
import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.View;
import android.widget.Toast;
 import java.util.Map;
 import java.util.HashMap;
 import android.widget.RadioButton;

public class Register extends AppCompatActivity {
    RadioGroup rg;
    EditText name,pass,first,last,phoneN,emailA;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if(SharedPrefer.getInstance(this).isLogged()){
            finish();
            startActivity(new Intent(this, Verify.class));
            return;
        }

        name=findViewById(R.id.uname);
        pass=findViewById(R.id.passW);
        first=findViewById(R.id.First);
        last=findViewById(R.id.Last);
        phoneN=findViewById(R.id.Phone);
        emailA=findViewById(R.id.Email);
        rg=findViewById(R.id.radio);
        register=findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                registerUser();
            }

        });
    }

    public void registerUser(){
        String username=name.getText().toString();
        String password=pass.getText().toString();
        String firstname=first.getText().toString();
        String lastname=last.getText().toString();
        String phone=phoneN.getText().toString();
        String email=emailA.getText().toString();
        String gender = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        if(TextUtils.isEmpty(username)){
            name.setError("Enter Username");
            name.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){
             pass.setError("Enter Password");
            pass.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(firstname)){
            first.setError("Firstname required");
            first.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(lastname)){
            last.setError("Lastname required");
            last.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(phone)){
            phoneN.setError("Phone Number required");
            phoneN.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(email)){
            emailA.setError("Email required");
            emailA.requestFocus();
            return;
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
           emailA.setError("Enter valid email");
           emailA.requestFocus();
           return;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.register_url,
                new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try{
                    JSONObject obj = new JSONObject(response);
                    if(!obj.getBoolean("error")){
                        Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                        JSONObject bj = obj.getJSONObject("user");

                        User userV = new User(bj.getString("username"),
                                bj.getString("password"),
                                bj.getString("firstname"),
                                bj.getString("lastname"),
                                bj.getString("phone"),
                                bj.getString("email"),
                                bj.getString("gender"));
                        SharedPrefer.getInstance(getApplicationContext()).userData(userV);
                        finish();
                        Intent intent = new Intent(getApplicationContext(), Verify.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch(JSONException e){
                    e.printStackTrace();

                }
            }

        },
           new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> userD = new HashMap<>();

                userD.put("username", username);
                userD.put("password",password);
                userD.put("firstname",firstname);
                userD.put("lastname",lastname);
                userD.put("phone", phone);
                userD.put("email", email);
                userD.put("gender",gender);

                return userD;
            }

            };

        VolleyLibrary.getInstance(this).addToRequestQueue(stringRequest);

    }
}