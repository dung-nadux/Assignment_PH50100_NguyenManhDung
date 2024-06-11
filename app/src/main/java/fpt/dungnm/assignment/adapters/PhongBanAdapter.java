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
import fpt.dungnm.assignment.database.FileHelperPB;
import fpt.dungnm.assignment.models.PhongBan;
import fpt.dungnm.assignment.screens.Update_PhongBan_Activity;

public class PhongBanAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<PhongBan> mPhongBan;
    private ActivityResultLauncher<Intent> myLauncher;

    public PhongBanAdapter(Context mContext, ArrayList<PhongBan> mPhongBan, ActivityResultLauncher<Intent> myLauncher) {
        this.mContext = mContext;
        this.mPhongBan = mPhongBan;
        this.myLauncher = myLauncher;
    }

    @Override
    public int getCount() {
        if (mPhongBan != null) {
            return mPhongBan.size();
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
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        convertView = inflater.inflate(R.layout.layout_list_phong_ban, parent, false);

        ImageView imgLogoPB = convertView.findViewById(R.id.imgLogoPB);
        TextView tvTenPB = convertView.findViewById(R.id.tvTenPB);
        LinearLayout btnSua = convertView.findViewById(R.id.btnSua);
        LinearLayout btnXoa = convertView.findViewById(R.id.btnXoa);

        PhongBan phongBan = mPhongBan.get(position);
        imgLogoPB.setImageResource(phongBan.getLogoPB());
        tvTenPB.setText(String.valueOf(phongBan.getTenPB()));

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Update_PhongBan_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("phongBan", mPhongBan.get(position));
                bundle.putInt("position", position);
                intent.putExtras(bundle);
                myLauncher.launch(intent);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDeleteDialog(position);
            }
        });

        return convertView;
    }

    private void ShowDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Xóa");
        builder.setMessage("Bạn có muốn xóa phòng ban này?");
        builder.setPositiveButton("Có", (dialog, which) -> {
            mPhongBan.remove(position);
            FileHelperPB fileHelperPB = new FileHelperPB(mContext);
            fileHelperPB.writeToFile(mPhongBan, "ListPB.txt");
            notifyDataSetChanged();
            Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Không", null);
        builder.show();
    }
}
