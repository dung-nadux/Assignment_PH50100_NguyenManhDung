package fpt.dungnm.assignment.screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import fpt.dungnm.assignment.R;
import fpt.dungnm.assignment.database.FileHelperUser;
import fpt.dungnm.assignment.models.User;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout txtUser, txtPass, txtRePass;
    private TextInputEditText edtUser, edtPass, edtRePass;
    private Button btnRegister, btnBack;
    private ArrayList<User> listUser = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        txtRePass = findViewById(R.id.txtRePass);
        edtRePass = findViewById(R.id.edtRePass);
        btnRegister = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btnBack);

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

        edtRePass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0) {
                    edtRePass.setError("Hãy nhập lại Password");
                    txtRePass.setError("Hãy nhập lại Password");
                } else {
                    edtRePass.setError(null);
                    txtRePass.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                String rePass = edtRePass.getText().toString().trim();

                if(user.equals("")||pass.equals("")||rePass.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    if (user.equals("")) {
                        edtUser.setError("Hãy nhập Username");
                        txtUser.setError("Hãy nhập Username");
                    } else {
                        edtUser.setError(null);
                        txtUser.setError(null);
                    }
                    if (pass.equals("")) {
                        edtPass.setError("Hãy nhập Password");
                        txtPass.setError("Hãy nhập Password");
                    } else {
                        edtPass.setError(null);
                        txtPass.setError(null);
                    }
                    if (rePass.equals("")) {
                        edtRePass.setError("Hãy nhập lại Password");
                        txtRePass.setError("Hãy nhập lại Password");
                    } else {
                        edtRePass.setError(null);
                        txtRePass.setError(null);
                    }
                }

                else if (!pass.equals(rePass)) {
                    Toast.makeText(RegisterActivity.this, "Xác nhận mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                } else {
                    FileHelperUser fileHelperUser = new FileHelperUser(RegisterActivity.this);
                    listUser = fileHelperUser.readFromFile("User.txt");
                    User userMoi = new User(user, pass);
                    if(listUser != null) {
                        listUser.add(userMoi);
                    } else {
                        listUser = new ArrayList<>();
                        listUser.add(userMoi);
                    }
                    fileHelperUser.writeToFile(listUser, "User.txt");
                    Intent intent = new Intent();
                    intent.putExtra("user", user);
                    intent.putExtra("pass", pass);
                    setResult(1, intent);
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}