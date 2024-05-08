package com.example.day_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.day_02.data.ApiClient;
import com.example.day_02.data.ApiConfig;
import com.example.day_02.databinding.ActivityRegisterBinding;
import com.example.day_02.response.Register;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        binding =  ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.suruhLogin.setOnClickListener(click -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        binding.btnReg.setOnClickListener(click -> {
            String username = binding.regUser.getText().toString();
            String name = binding.regName.getText().toString();
            String passsword = binding.regPass.getText().toString();
            register(username,name,passsword);
        });
    }

    private void register(String username,String name,String password){
        ApiConfig apiConfig = ApiClient.getClient().create(ApiConfig.class);
        Call<Register> call = apiConfig.registerResponse(username, name, password);

        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.isSuccessful() && response.body() != null){
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    Toast.makeText(RegisterActivity.this, "Akun berhasil dibuat", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else {
                    Toast.makeText(RegisterActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}