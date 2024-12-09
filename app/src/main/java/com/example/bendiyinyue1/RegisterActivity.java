package com.example.bendiyinyue1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register); // 确保你有一个名为register的布局文件

        // 获取布局中的视图
        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        Button registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();


                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("请输入用户名", username); // 建议使用更具体的键名，如"username"
                editor.putString("请输入密码", password); // 建议使用更具体的键名，如"password"
                editor.apply();

                showToast();

                // 注册成功后返回登录页面
                finish();
                Intent intent = new Intent(RegisterActivity.this, DengLu.class);
                startActivity(intent);
            }
        });
    }

    private void showToast() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
    }
}