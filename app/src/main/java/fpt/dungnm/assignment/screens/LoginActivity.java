package fpt.dungnm.assignment.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import fpt.dungnm.assignment.MainActivity;
import fpt.dungnm.assignment.R;
import fpt.dungnm.assignment.database.FileHelperUser;
import fpt.dungnm.assignment.models.User;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout txtUser, txtPass;
    private TextInputEditText edtUser, edtPass;
    private Button btnLogin, btnRegister;
    private CheckBox cbRemember;

    private String userRegister = "", passRegister = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        cbRemember = findViewById(R.id.cbRemember);

        edtUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0) {
                    edtUser.setError("Hãy nhập Username");
                    txtUser.setError("Hãy nhập Username");
                } else {
                    edtUser.setError(null);
                    txtUser.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0) {
                    edtPass.setError("Hãy nhập Password");
                    txtPass.setError("Hãy nhập Password");
                } else {
                    edtPass.setError(null);
                    txtPass.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        SharedPreferences preferences = getSharedPreferences("INFO", MODE_PRIVATE);
        boolean isRemember = preferences.getBoolean("isRemember", false);
        if (isRemember) {
            String user = preferences.getString("userLogin", "");
            String pass = preferences.getString("passLogin", "");
            edtUser.setText(user);
            edtPass.setText(pass);
            cbRemember.setChecked(isRemember);
            userRegister = user;
            passRegister = pass;
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userLogin = edtUser.getText().toString();
                String passLogin = edtPass.getText().toString();

                if(userLogin.isEmpty() || passLogin.isEmpty() ) {
                    Toast.makeText(LoginActivity.this,"Bạn chưa nhập đủ thông tin",Toast.LENGTH_SHORT).show();
                    if(userLogin.isEmpty()) {
                        edtUser.setError("Hãy nhập Username");
                        txtUser.setError("Hãy nhập Username");
                    } else {
                        edtUser.setError(null);
                        txtUser.setError(null);

                    }
                    if(passLogin.isEmpty()) {
                        edtPass.setError("Hãy nhập Password");
                        txtPass.setError("Hãy nhập Password");
                    } else {
                        edtPass.setError(null);
                        txtPass.setError(null);
                    }
                } else if ((userLogin.equals(userRegister) && passLogin.equals(passRegister))
                        || (userLogin.equals("admin") && passLogin.equals("admin"))
                        || readFile(userLogin, passLogin)) {
                    if(cbRemember.isChecked()) {
                        SharedPreferences preferences = getSharedPreferences("INFO", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("userLogin", userLogin);
                        editor.putString("passLogin", passLogin);
                        editor.putBoolean("isRemember", cbRemember.isChecked());
                        editor.apply();
                    } else {
                        SharedPreferences preferences = getSharedPreferences("INFO", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                    }
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Username hoặc Password không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                login.launch(intent);
            }
        });
    }
    private ActivityResultLauncher<Intent> login =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult o) {
                            if (o.getResultCode() == 1) {
                                Intent data = o.getData();
                                userRegister = data.getStringExtra("user");
                                passRegister = data.getStringExtra("pass");
                            }
                        }
                    }
            );

    public boolean readFile(String userLogin, String passLogin ) {
        FileHelperUser fileHelperUser = new FileHelperUser(this);
        ArrayList<User> listUser = fileHelperUser.readFromFile("User.txt");
        if(listUser == null) {
            Toast.makeText(this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
            return false;
        } else if(listUser.isEmpty()) {
            Toast.makeText(this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            for (User user : listUser) {
                if (user.getUserName().equals(userLogin) && user.getPassWord().equals(passLogin)) {
                    return true;
                }
            }
            return false;
        }
    }
}