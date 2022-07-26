package com.example.portal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;

import java.util.concurrent.TimeUnit;



public class Verify extends AppCompatActivity {
    Button num, verify;
    EditText phoneN, code;
    FirebaseAuth auth;
    String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        auth = FirebaseAuth.getInstance();
        num = findViewById(R.id.Number);
        verify = findViewById(R.id.verify);
        code = findViewById(R.id.code);
        phoneN = findViewById(R.id.phone);
        num.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
              if(TextUtils.isEmpty(phoneN.getText())){
                  phoneN.setError("Enter phone number");
                  phoneN.requestFocus();
              }
              else {
                  String phone = "+254" + phoneN.getText().toString();
                  sendVerificationCode(phone);
              }
            }
        });


        verify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String otp = code.getText().toString();
                if (TextUtils.isEmpty(otp)) {
                    code.setError("Enter Verification code is must");
                    code.requestFocus();
                } else {
                    verifyCode(otp);
                }

            }

        });

    }

    private void signInWithCredential(PhoneAuthCredential phoneCredentials) {
        auth.signInWithCredential(phoneCredentials).addOnCompleteListener( new OnCompleteListener < AuthResult > ()
        {
            public void onComplete (@NonNull Task < AuthResult > task) {
            if (task.isSuccessful()) {
                Intent intent = new Intent(Verify.this, Register.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        });
    }

    public void sendVerificationCode(String phone) {

        try {
            PhoneAuthOptions options = PhoneAuthOptions.newBuilder()
                    .setPhoneNumber(phone)
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(this)
                    .setCallbacks(mCallbacks)
                    .build();
            PhoneAuthProvider.verifyPhoneNumber(options);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken token){
            super.onCodeSent(s, token);

            verificationId=s;
        }

        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential){

            String Code = phoneAuthCredential.getSmsCode();

            if(Code != null){
                code.setText(Code);

                verifyCode(Code);
            }
        }
        public void onVerificationFailed(FirebaseException e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    public void verifyCode(String VCode ){
        PhoneAuthCredential authCredential = PhoneAuthProvider.getCredential(verificationId, VCode);

        signInWithCredential(authCredential);
    }
}
