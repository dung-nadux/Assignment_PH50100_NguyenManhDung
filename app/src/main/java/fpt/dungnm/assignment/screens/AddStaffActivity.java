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
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

public class AddStaffActivity extends AppCompatActivity {

    TextInputEditText edtTenNV, edtMaNV, edtDiaChi;
    Spinner spPhongBan, spAvatar;
    Button btnThem, btnHuy;
    String tenPB="";
    int avatarID = R.drawable.avt_nu1;
    private ArrayList<Avatar> listAvatar = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_staff);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Anh xa
        AnhXa();

        checkTrong();

        //Lấy danh sách avatar
        listAvatar.add(new Avatar(R.drawable.avt_nu1));
        listAvatar.add(new Avatar(R.drawable.avt_nu2));
        listAvatar.add(new Avatar(R.drawable.avt_nu3));
        listAvatar.add(new Avatar(R.drawable.avt_nam1));
        listAvatar.add(new Avatar(R.drawable.avt_nam2));
        listAvatar.add(new Avatar(R.drawable.avt_nam3));
        AvatarAdapter avatarAdapter = new AvatarAdapter(AddStaffActivity.this, listAvatar);
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

        //Lấy danh sách phòng ban
        ArrayList<PhongBan> listPB = new ArrayList<>();
        FileHelperPB fileHelperPB = new FileHelperPB(AddStaffActivity.this);
        listPB = fileHelperPB.readFromFile("ListPB.txt");
        SpinnerPBAdapter spinnerPBAdapter = new SpinnerPBAdapter(AddStaffActivity.this, listPB );
        spPhongBan.setAdapter(spinnerPBAdapter);

        //Lấy tên phòng ban
        ArrayList<PhongBan> finalListPB = listPB;
        spPhongBan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenPB = finalListPB.get(position).getTenPB();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Thêm nhân viên
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String manv = edtMaNV.getText().toString();
                String tennv = edtTenNV.getText().toString();
                String diachi = edtDiaChi.getText().toString();
                int avatar = avatarID;

                if(manv.equals("")||tennv.equals("")||diachi.equals("")) {
                    baoTrong(manv, tennv, diachi);
                    Toast.makeText(AddStaffActivity.this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    NhanVien addNV = new NhanVien(avatar, tennv,manv, diachi, tenPB);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("NVMoi", addNV);
                    intent.putExtras(bundle);
                    setResult(1,intent);
                    finish();
                }
            }
        });

        //Hủy
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void baoTrong(String manv, String tennv, String diachi) {
        if(manv.equals("")) {
            edtMaNV.setError("Hãy nhập mã nhân viên");
        } else {
            edtMaNV.setError(null);
        }
        if(tennv.equals("")) {
            edtTenNV.setError("Hãy nhập tên nhân viên");
        } else {
            edtTenNV.setError(null);
        }
        if(diachi.equals("")) {
            edtDiaChi.setError("Hãy nhập địa chỉ");
        } else {
            edtDiaChi.setError(null);
        }
    }

    private void checkTrong() {
        AnhXa();
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

    private void AnhXa() {
        edtTenNV = findViewById(R.id.edtTenNV);
        edtMaNV = findViewById(R.id.edtMaNV);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        spPhongBan = findViewById(R.id.spPhongBan);
        spAvatar = findViewById(R.id.spAvatar);
        btnThem = findViewById(R.id.btnThem);
        btnHuy = findViewById(R.id.btnHuy);
    }


}