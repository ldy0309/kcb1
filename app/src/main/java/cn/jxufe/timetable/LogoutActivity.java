package cn.jxufe.timetable;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogoutActivity extends AppCompatActivity {
    private EditText userName,passWord,rePassword;
    private TextView userLogin;
    private Button register;
    private UserDao userdao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_logout);
        userdao = new UserDao(this);
        init();
        ViewClick();
    }

    private void ViewClick() {

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin.setTextColor(Color.rgb(0, 0, 0));
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = userName.getText().toString();
                final String password = passWord.getText().toString();
                final String repassword = rePassword.getText().toString();
                if (username.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "帐号不能为空", Toast.LENGTH_LONG).show();
                    return;
                } else if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_LONG).show();
                    return;
                } else if (!password.equals(repassword)) {
                    Toast.makeText(getApplicationContext(), "两次密码输入不一致", Toast.LENGTH_LONG).show();
                    return;
                }

                Cursor cursor = userdao.query(username.trim(), password.trim());
                if (cursor.moveToNext()) {
                    Toast.makeText(getApplicationContext(), "该用户已被注册，请重新输入", Toast.LENGTH_LONG).show();
                    userName.requestFocus();
                } else {
                    userdao.insertUser(username, password);
                    cursor.close();
                    Toast.makeText(getApplicationContext(), "用户注册成功，请前往登录", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                }

            }
        });
    }

    private void init() {
        userName = (EditText) findViewById(R.id.logout_user_edit);
        passWord = (EditText) findViewById(R.id.logout_passwd_edit);
        rePassword = (EditText) findViewById(R.id.logout_passwd_reedit);
        userLogin = (TextView) findViewById(R.id.link_signup);
        register= (Button) findViewById(R.id.logout_login_btn);

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }

}
