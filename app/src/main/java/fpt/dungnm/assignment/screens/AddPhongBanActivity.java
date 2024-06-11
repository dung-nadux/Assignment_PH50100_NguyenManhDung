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

import fpt.dungnm.assignment.R;
import fpt.dungnm.assignment.models.PhongBan;

public class AddPhongBanActivity extends AppCompatActivity {

    private TextInputEditText edtTenPB;
    private Button btnThem, btnHuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_phong_ban);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtTenPB = findViewById(R.id.edtTenPB);
        btnThem = findViewById(R.id.btnThem);
        btnHuy = findViewById(R.id.btnHuy);

        edtTenPB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0) {
                    edtTenPB.setError("Hãy nhập tên phòng ban");
                } else {
                    edtTenPB.setError(null);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenPB = edtTenPB.getText().toString();

                if(tenPB.equals("")) {
                    edtTenPB.setError("Hãy nhập tên phòng ban");
                    Toast.makeText(AddPhongBanActivity.this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    edtTenPB.setError(null);
                    PhongBan phongBan = new PhongBan(R.drawable.home_work, tenPB);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("PBMoi", phongBan);
                    intent.putExtras(bundle);
                    setResult(1, intent);
                    finish();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}