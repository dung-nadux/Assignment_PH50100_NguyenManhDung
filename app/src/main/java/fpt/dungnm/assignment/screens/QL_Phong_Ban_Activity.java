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
import fpt.dungnm.assignment.adapters.PhongBanAdapter;
import fpt.dungnm.assignment.database.FileHelperPB;
import fpt.dungnm.assignment.models.PhongBan;

public class QL_Phong_Ban_Activity extends AppCompatActivity {

    private ListView lvPhongBan;
    private ImageView imgBack;
    private SearchView svPhongBan;
    private LinearLayout btnAddPB;
    private PhongBanAdapter phongBanAdapter;
    private ArrayList<PhongBan> listPhongBan = new ArrayList<>();
    private FileHelperPB fileHelperPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ql_phong_ban);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //ánh xạ
        lvPhongBan = findViewById(R.id.lvPhongBan);
        imgBack = findViewById(R.id.imgBack);
        svPhongBan = findViewById(R.id.svPhongBan);
        btnAddPB = findViewById(R.id.btnAddPB);

        //load dữ liệu
        loadPhongBan(listPhongBan);

        //Tìm kiếm
        svPhongBan.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FileHelperPB fileHelperPB = new FileHelperPB(QL_Phong_Ban_Activity.this);
                listPhongBan = fileHelperPB.readFromFile("ListPB.txt");
                ArrayList<PhongBan> listFilter = new ArrayList<>();
                for (PhongBan phongBan : listPhongBan) {
                    if(phongBan.getTenPB().toLowerCase().contains(newText.toLowerCase())) {
                        listFilter.add(phongBan);
                    }
                }
                phongBanAdapter = new PhongBanAdapter(QL_Phong_Ban_Activity.this, listFilter, myLauncher);
                lvPhongBan.setAdapter(phongBanAdapter);
                return false;
            }
        });

        // Back
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Thêm phòng ban
        btnAddPB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QL_Phong_Ban_Activity.this, AddPhongBanActivity.class);
                myLauncher.launch(intent);
            }
        });
    }

    ActivityResultLauncher<Intent> myLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //thêm phòng ban
                    if (result.getResultCode() == 1) {
                        Intent intent = result.getData();
                        Bundle bundle = intent.getExtras();
                        PhongBan phongBanMoi = (PhongBan) bundle.getSerializable("PBMoi");
                        readFile(phongBanMoi, -5);
                        fileHelperPB.writeToFile(listPhongBan,"ListPB.txt");
                        loadPhongBan(listPhongBan);
                        Toast.makeText(QL_Phong_Ban_Activity.this, "Thêm phòng ban thành công", Toast.LENGTH_SHORT).show();
                    }

                    //sửa phòng ban
                    if (result.getResultCode() == 2) {
                        Intent intent = result.getData();
                        Bundle bundle = intent.getExtras();
                        PhongBan phongBanSua = (PhongBan) bundle.getSerializable("PBSua");
                        int viTri = bundle.getInt("viTri");
                        readFile(phongBanSua, viTri);
                        fileHelperPB.writeToFile(listPhongBan,"ListPB.txt");
                        loadPhongBan(listPhongBan);
                        Toast.makeText(QL_Phong_Ban_Activity.this, "Sửa phòng ban thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    public void readFile(PhongBan phongBan, int viTri) {
        fileHelperPB = new FileHelperPB(QL_Phong_Ban_Activity.this);
        listPhongBan = fileHelperPB.readFromFile("ListPB.txt");
        if(listPhongBan != null) {
            if(viTri == -5) {
                listPhongBan.add(phongBan);
            } else {
                listPhongBan.set(viTri, phongBan);
            }
        } else {
            if(viTri == -5) {
                listPhongBan = new ArrayList<>();
                listPhongBan.add(phongBan);
            } else {
                listPhongBan = new ArrayList<>();
                listPhongBan.set(viTri, phongBan);
            }
        }
    }

    private void loadPhongBan(ArrayList<PhongBan> list) { //adapter
        FileHelperPB fileHelperPB = new FileHelperPB(QL_Phong_Ban_Activity.this);
        list = fileHelperPB.readFromFile("ListPB.txt");
        phongBanAdapter = new PhongBanAdapter(this, list, myLauncher);
        lvPhongBan.setAdapter(phongBanAdapter);
    }

    public ArrayList<PhongBan> getDSPB() { //data
        listPhongBan = new ArrayList<>();
        listPhongBan.add(new PhongBan(R.drawable.home_work, "Nhân sự"));
        listPhongBan.add(new PhongBan(R.drawable.home_work, "Hành chính"));
        listPhongBan.add(new PhongBan(R.drawable.home_work, "Đào tạo"));
        return listPhongBan;
    }
}