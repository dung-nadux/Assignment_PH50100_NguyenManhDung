package fpt.dungnm.assignment.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpt.dungnm.assignment.models.PhongBan;

public class SpinnerPBAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<PhongBan> listPB;

    public SpinnerPBAdapter(Context mContext, ArrayList<PhongBan> listPB) {
        this.mContext = mContext;
        this.listPB = listPB;
    }

    @Override
    public int getCount() {
        if (listPB != null){
            return listPB.size();
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
        convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent,false );

        TextView txtTenPB = convertView.findViewById(android.R.id.text1);

        txtTenPB.setText(String.valueOf(listPB.get(position).getTenPB()));
        return convertView;
    }
}
