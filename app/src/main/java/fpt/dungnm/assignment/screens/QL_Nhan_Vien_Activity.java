package fpt.dungnm.assignment.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
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

import java.util.ArrayList;

import fpt.dungnm.assignment.R;
import fpt.dungnm.assignment.adapters.NhanVienAdapter;
import fpt.dungnm.assignment.database.FileHelperNV;
import fpt.dungnm.assignment.models.NhanVien;

public class QL_Nhan_Vien_Activity extends AppCompatActivity {

    SearchView svNhanVien;
    private ImageView imgBack;
    private LinearLayout btnAdd;
    private ListView lvNhanVien;
    private ArrayList<NhanVien> listNhanVien = new ArrayList<>();
    private NhanVienAdapter adapter;
    private FileHelperNV fileHelperNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ql_nhan_vien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgBack = findViewById(R.id.imgBack);
        btnAdd = findViewById(R.id.btnAdd);
        lvNhanVien = findViewById(R.id.lvNhanVien);
        svNhanVien = findViewById(R.id.svNhanVien);

//        listNhanVien.add(new NhanVien(R.drawable.avt_nu1, "Lê Huyền Trang", "NV001", "Hà Nội", "Nhân sự"));
//        listNhanVien.add(new NhanVien(R.drawable.avt_nu2, "Trần Bảo Ngọc", "NV002", "Hải Phòng", "Đào tạo"));
//        listNhanVien.add(new NhanVien(R.drawable.avt_nu3, "Nguyễn Thị Hiền", "NV003", "Nam Định", "Hành chính"));
//        listNhanVien.add(new NhanVien(R.drawable.avt_nam1, "Trần Anh Tuấn", "NV004", "Hà Nội", "Nhân sự"));
//        listNhanVien.add(new NhanVien(R.drawable.avt_nam2, "Nguyễn Hoàng Hải", "NV005", "Quảng Ninh", "Đào tạo"));
//        listNhanVien.add(new NhanVien(R.drawable.avt_nam3, "Lê Phương Nam", "NV006", "Hà Nội", "Nhân sự"));
//        listNhanVien.add(new NhanVien(R.drawable.avt_nu1, "Hà Mai Linh", "NV007", "Ninh Bình", "Hành chính"));
//        listNhanVien.add(new NhanVien(R.drawable.avt_nam2, "Nguyễn Mạnh Dũng", "NV008", "Hà Nội", "Đào tạo"));
//        listNhanVien.add(new NhanVien(R.drawable.avt_nu2, "Hoàng Yến Tuyết", "NV009", "Hà Nam", "Hành chính"));

        loadNhanVien(listNhanVien);

        //Tìm kiếm
        svNhanVien.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<NhanVien> listFilter = new ArrayList<>();
                fileHelperNV = new FileHelperNV(QL_Nhan_Vien_Activity.this);
                listNhanVien = fileHelperNV.readFromFile("ListNV.txt");
                for (NhanVien nhanVien : listNhanVien) {
                    if(nhanVien.getTenNV().toLowerCase().contains(newText.toLowerCase()) ||
                        nhanVien.getMaNV().toLowerCase().contains(newText.toLowerCase()) ||
                        nhanVien.getDiaChi().toLowerCase().contains(newText.toLowerCase()) ||
                        nhanVien.getPhongBan().toLowerCase().contains(newText.toLowerCase())) {
                        listFilter.add(nhanVien);
                    }
                }
                NhanVienAdapter adapter = new NhanVienAdapter(QL_Nhan_Vien_Activity.this, listFilter, myLauncher);
                lvNhanVien.setAdapter(adapter);
                return false;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QL_Nhan_Vien_Activity.this, AddStaffActivity.class);
                myLauncher.launch(intent);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    private ActivityResultLauncher<Intent> myLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {


                            //thêm trả về
                            if(result.getResultCode() == 1) {
                                Intent intent = result.getData();
                                Bundle bundle = intent.getExtras();
                                NhanVien nhanVienMoi = (NhanVien) bundle.getSerializable("NVMoi");
                                readFile(nhanVienMoi, -5);
                                fileHelperNV.writeToFile(listNhanVien, "ListNV.txt");
                                loadNhanVien(listNhanVien);
                                Toast.makeText(QL_Nhan_Vien_Activity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            }
                            //sửa trả về
                            if(result.getResultCode() == 2) {
                                Intent intent = result.getData();
                                Bundle bundle = intent.getExtras();
                                NhanVien nhanVienSua = (NhanVien) bundle.getSerializable("nhanVienSua");
                                int viTriSua = bundle.getInt("viTriSua");
                                readFile(nhanVienSua, viTriSua);
                                fileHelperNV.writeToFile(listNhanVien, "ListNV.txt");
                                loadNhanVien(listNhanVien);
                                Toast.makeText(QL_Nhan_Vien_Activity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    private void loadNhanVien(ArrayList<NhanVien> list) {
        fileHelperNV = new FileHelperNV(QL_Nhan_Vien_Activity.this);
        list = fileHelperNV.readFromFile("ListNV.txt");
        NhanVienAdapter adapter = new NhanVienAdapter(this, list, myLauncher);
        lvNhanVien.setAdapter(adapter);
    }

    private void readFile(NhanVien nhanVien, int viTri) {
        fileHelperNV = new FileHelperNV(QL_Nhan_Vien_Activity.this);
        listNhanVien = fileHelperNV.readFromFile("ListNV.txt");
        if(listNhanVien != null) {
            if(viTri == -5) {
                listNhanVien.add(nhanVien);
            } else {
                listNhanVien.set(viTri, nhanVien);
            }
        } else {
            if(viTri == -5) {
                listNhanVien = new ArrayList<>();
                listNhanVien.add(nhanVien);
            } else {
                listNhanVien = new ArrayList<>();
                listNhanVien.set(viTri, nhanVien);
            }
        }
    }
}