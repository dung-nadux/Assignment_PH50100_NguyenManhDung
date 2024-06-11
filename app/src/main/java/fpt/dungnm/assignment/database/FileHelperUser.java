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

import fpt.dungnm.assignment.models.User;

public class FileHelperUser {
    private Context mContext;

    public FileHelperUser(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<User> readFromFile(String fileName) {
        ArrayList<User> users = null;
        File fileDir = mContext.getFilesDir();
        File file = new File(fileDir, fileName);
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                users = (ArrayList<User>) ois.readObject();
                ois.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return users;
    }

    public void writeToFile (ArrayList<User> listUser, String fileName) {
        File fileDir = mContext.getFilesDir();
        File file = new File(fileDir, fileName);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(listUser);
            oos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
