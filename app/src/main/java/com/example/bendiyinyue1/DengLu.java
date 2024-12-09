package com.example.bendiyinyue1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bendiyinyue1.R;

public class DengLu extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button registerButton, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.denglu);

        // 获取布局中的视图
        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        registerButton = findViewById(R.id.register_button);
        loginButton = findViewById(R.id.login_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到注册页面
                Intent intent = new Intent(DengLu.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // 获取SharedPreferences中保存的密码和账号
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                String savedUsername = sharedPreferences.getString("请输入用户名", "");
                String savedPassword = sharedPreferences.getString("请输入密码", "");

                if (username.isEmpty()) {
                    showToast("请输入用户名");
                } else if (password.equals(savedPassword) && username.equals(savedUsername)) {
                    showToast("登录成功");
                    Intent intent = new Intent(DengLu.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    showToast("用户账号或密码错误");
                }

                // 清除输入框内容
                usernameEditText.setText("");
                passwordEditText.setText("");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

