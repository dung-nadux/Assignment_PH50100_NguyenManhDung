package fpt.dungnm.assignment.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;

import java.util.ArrayList;

import fpt.dungnm.assignment.R;
import fpt.dungnm.assignment.database.FileHelperNV;
import fpt.dungnm.assignment.models.NhanVien;
import fpt.dungnm.assignment.screens.UpdateStaffActivity;

public class NhanVienAdapter extends BaseAdapter {

    private Context mcontext;
    private ArrayList<NhanVien> listNhanVien;

    private ActivityResultLauncher<Intent> myLauncher;



    public NhanVienAdapter(Context mcontext, ArrayList<NhanVien> listNhanVien, ActivityResultLauncher<Intent> myLauncher) {
        this.mcontext = mcontext;
        this.listNhanVien = listNhanVien;
        this.myLauncher = myLauncher;
    }

    @Override
    public int getCount() {
        if (listNhanVien != null) {
            return listNhanVien.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) mcontext).getLayoutInflater();
        convertView = inflater.inflate(R.layout.layout_list_nhan_vien, parent, false);

        ImageView imgAvatar = convertView.findViewById(R.id.imgAvatar);
        TextView tvTenNV = convertView.findViewById(R.id.tvTenNV);
        TextView tvMaNV = convertView.findViewById(R.id.tvMaNV);
        TextView tvDiaChi = convertView.findViewById(R.id.tvDiaChi);
        TextView tvPhongBan = convertView.findViewById(R.id.tvPhongBan);
        LinearLayout btnSuaNV = convertView.findViewById(R.id.btnSuaNV);
        LinearLayout btnXoaNV = convertView.findViewById(R.id.btnXoaNV);


        NhanVien nhanVien = listNhanVien.get(position);
        imgAvatar.setImageResource(nhanVien.getAvatarNV());
        tvTenNV.setText(String.valueOf(nhanVien.getTenNV()));
        tvMaNV.setText(String.valueOf(nhanVien.getMaNV()));
        tvDiaChi.setText(String.valueOf(nhanVien.getDiaChi()));
        tvPhongBan.setText(String.valueOf(nhanVien.getPhongBan()));

        btnSuaNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, UpdateStaffActivity.class );
                Bundle bundle = new Bundle();
                bundle.putSerializable("nhanVienSua", listNhanVien.get(position));
                bundle.putInt("viTriSua", position);
                intent.putExtras(bundle);
                myLauncher.launch(intent);
            }
        });

        btnXoaNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDeleteDialog(position);
            }
        });

        return convertView;
    }

    private void ShowDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        builder.setTitle("Xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa nhân viên?");
        builder.setPositiveButton("Có", (dialog, which) -> {
            listNhanVien.remove(position);
            FileHelperNV fileHelperNV = new FileHelperNV(mcontext);
            fileHelperNV.writeToFile(listNhanVien, "ListNV.txt");
            notifyDataSetChanged();
            Toast.makeText(mcontext, "Xóa thành công", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Không", null);
        builder.show();

    }
}
