package fpt.dungnm.assignment.screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import fpt.dungnm.assignment.R;
import fpt.dungnm.assignment.adapters.AvatarAdapter;
import fpt.dungnm.assignment.adapters.SpinnerPBAdapter;
import fpt.dungnm.assignment.database.FileHelperPB;
import fpt.dungnm.assignment.models.Avatar;
import fpt.dungnm.assignment.models.NhanVien;
import fpt.dungnm.assignment.models.PhongBan;

public class UpdateStaffActivity extends AppCompatActivity {

    TextInputEditText edtTenNV, edtMaNV, edtDiaChi;
    Spinner spPhongBan, spAvatar;
    Button btnSua, btnHuy;
    String tenPB="";
    int avatarID = R.drawable.avt_nu1;
    private ArrayList<Avatar> listAvatar = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_staff);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ánh xạ
        AnhXa();

        //Check trống
        checkTrong();

        //Lấy danh sách avatar
        listAvatar.add(new Avatar(R.drawable.avt_nu1));
        listAvatar.add(new Avatar(R.drawable.avt_nu2));
        listAvatar.add(new Avatar(R.drawable.avt_nu3));
        listAvatar.add(new Avatar(R.drawable.avt_nam1));
        listAvatar.add(new Avatar(R.drawable.avt_nam2));
        listAvatar.add(new Avatar(R.drawable.avt_nam3));
        AvatarAdapter avatarAdapter = new AvatarAdapter(UpdateStaffActivity.this, listAvatar);
        spAvatar.setAdapter(avatarAdapter);

        //Lấy avatar
        spAvatar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                avatarID = listAvatar.get(position).getAvatarId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        //đổ data vào spinner phòng ban
        FileHelperPB fileHelperPB = new FileHelperPB(UpdateStaffActivity.this);
        ArrayList<PhongBan> listPB = fileHelperPB.readFromFile("ListPB.txt");
        SpinnerPBAdapter spinnerPBAdapter = new SpinnerPBAdapter(UpdateStaffActivity.this, listPB );
        spPhongBan.setAdapter(spinnerPBAdapter);

        //lấy tên phòng ban
        spPhongBan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenPB = listPB.get(position).getTenPB();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //nhận dữ liệu từ intent(adapter)
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        NhanVien nhanVien = (NhanVien) bundle.getSerializable("nhanVienSua");
        int viTriSua = bundle.getInt("viTriSua");
        edtTenNV.setText(String.valueOf(nhanVien.getTenNV()));
        edtMaNV.setText(String.valueOf(nhanVien.getMaNV()));
        edtDiaChi.setText(String.valueOf(nhanVien.getDiaChi()));

        //lấy index của phòng ban trong spinner
        int index = -1;
        for(int i=0; i<listPB.size(); i++) {
            if(listPB.get(i).getTenPB().equals(nhanVien.getPhongBan())) {
                index = i;
                break;
            }
        }
        spPhongBan.setSelection(index);

        int viTriAvatar = -1;
        for(int i=0; i<listAvatar.size(); i++) {
            if(listAvatar.get(i).getAvatarId() == nhanVien.getAvatarNV() ) {
                viTriAvatar = i;
                break;
            }
        }
        spAvatar.setSelection(viTriAvatar);

        //Sửa nhân viên
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maNV = edtMaNV.getText().toString();
                String tenNV = edtTenNV.getText().toString();
                String diaChi = edtDiaChi.getText().toString();
                int avatar = avatarID;

                if(maNV.equals("")||tenNV.equals("")||diaChi.equals("")) {
                    checkTrong();
                    Toast.makeText(UpdateStaffActivity.this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    NhanVien nhanVienSua = new NhanVien(avatar, tenNV, maNV, diaChi, tenPB);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("nhanVienSua", nhanVienSua);
                    bundle.putInt("viTriSua", viTriSua);
                    intent.putExtras(bundle);
                    setResult(2, intent);
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

    private void AnhXa() {
        edtTenNV = findViewById(R.id.edtTenNV);
        edtMaNV = findViewById(R.id.edtMaNV);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        spPhongBan = findViewById(R.id.spPhongBan);
        spAvatar = findViewById(R.id.spAvatar);
        btnSua = findViewById(R.id.btnSua);
        btnHuy = findViewById(R.id.btnHuy);
    }

    private void checkTrong() {
        edtTenNV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0) {
                    edtTenNV.setError("Hãy nhập tên nhân viên");
                } else {
                    edtTenNV.setError(null);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        edtMaNV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0) {
                    edtMaNV.setError("Hãy nhập mã nhân viên");
                } else {
                    edtMaNV.setError(null);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        edtDiaChi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0) {
                    edtDiaChi.setError("Hãy nhập địa chỉ");
                } else {
                    edtDiaChi.setError(null);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}