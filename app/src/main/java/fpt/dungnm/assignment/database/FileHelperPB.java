package fpt.dungnm.assignment.database;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import fpt.dungnm.assignment.models.PhongBan;

public class FileHelperPB {
    private Context mContext;

    public FileHelperPB(Context mContext) {
        this.mContext = mContext;
    }

    public void writeToFile(ArrayList<PhongBan> listPB, String filename) {
        File fileDir = mContext.getFilesDir();
        File file = new File(fileDir, filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listPB);
            oos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<PhongBan> readFromFile(String filename) {
        ArrayList<PhongBan> listPB = null;
        File fileDir = mContext.getFilesDir();
        File file = new File(fileDir, filename);
        try {
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                listPB = (ArrayList<PhongBan>) ois.readObject();
                ois.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listPB;
    }
}
