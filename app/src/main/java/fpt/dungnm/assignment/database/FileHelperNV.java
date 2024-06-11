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

import fpt.dungnm.assignment.models.NhanVien;

public class FileHelperNV {
    private Context mcontext;

    public FileHelperNV(Context mcontext) {
        this.mcontext = mcontext;
    }

    public void writeToFile(ArrayList<NhanVien> listNV, String filename) {
        File fileDir = mcontext.getFilesDir();
        File file = new File(fileDir, filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listNV);
            oos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<NhanVien> readFromFile(String filename) {
        ArrayList<NhanVien> listNV = null;
        File fileDir = mcontext.getFilesDir();
        File file = new File(fileDir, filename);
        try {
            if(file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                listNV = (ArrayList<NhanVien>) ois.readObject();
                ois.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listNV;
    }
}
