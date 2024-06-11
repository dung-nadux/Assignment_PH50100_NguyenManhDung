package fpt.dungnm.assignment.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpt.dungnm.assignment.MainActivity;
import fpt.dungnm.assignment.R;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout imgPhongBan;
    private LinearLayout imgNhanVien;
    private LinearLayout imgExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgPhongBan = findViewById(R.id.imgPhongBan);
        imgNhanVien = findViewById(R.id.imgNhanVien);
        imgExit = findViewById(R.id.imgExit);
        imgPhongBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, QL_Phong_Ban_Activity.class);
                startActivity(intent);
            }
        });

        imgNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, QL_Nhan_Vien_Activity.class);
                startActivity(intent);
            }
        });

        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn thoát?");
                builder.setPositiveButton("Có", (dialog, which) -> {
                    finish();
                });
                builder.setNegativeButton("Không", null);
                builder.show();
            }
        });

    }
}