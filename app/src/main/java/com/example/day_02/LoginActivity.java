package com.example.day_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.day_02.data.ApiClient;
import com.example.day_02.data.ApiConfig;
import com.example.day_02.databinding.ActivityLoginBinding;
import com.example.day_02.databinding.ActivityRegisterBinding;
import com.example.day_02.response.Login;
import com.example.day_02.response.LoginData;
import com.example.day_02.response.Register;
import com.example.day_02.ui.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding =  ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvCa.setOnClickListener(click -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        binding.btnlgn.setOnClickListener(click -> {
            String username = binding.usrlgn.getText().toString();
            String passsword = binding.passlgn.getText().toString();
            login(username,passsword);
        });
    }

    private void login(String username,String password){
        ApiConfig apiConfig = ApiClient.getClient().create(ApiConfig.class);
        Call<Login> call = apiConfig.loginResponse(username, password);


        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful() && response.body() != null){

                    SessionManager sessionManager = new SessionManager(LoginActivity.this);
                    LoginData loginData = response.body().getData();
                    sessionManager.createLoginSession(loginData);


                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Toast.makeText(LoginActivity.this, "Anda telah Login",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(LoginActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}