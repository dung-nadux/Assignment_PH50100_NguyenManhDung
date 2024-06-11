package fpt.dungnm.assignment.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import fpt.dungnm.assignment.R;
import fpt.dungnm.assignment.models.Avatar;

public class AvatarAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Avatar> listAvatar;

    public AvatarAdapter(Context context, ArrayList<Avatar> listAvatar) {
        this.context = context;
        this.listAvatar = listAvatar;
    }

    @Override
    public int getCount() {
        if(listAvatar != null) {
            return listAvatar.size();
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
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.layout_avatar_spinner, parent,false);

        ImageView imgAvatar = convertView.findViewById(R.id.imgAvatar);

        Avatar avatar = listAvatar.get(position);
        imgAvatar.setImageResource(avatar.getAvatarId());

        return convertView;
    }
}
