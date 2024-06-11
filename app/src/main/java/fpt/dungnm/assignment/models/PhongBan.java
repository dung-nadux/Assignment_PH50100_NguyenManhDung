package fpt.dungnm.assignment.models;

import java.io.Serializable;

public class PhongBan implements Serializable {
    private int logoPB;
    private String tenPB;

    public PhongBan(int logoPB, String tenPB) {
        this.logoPB = logoPB;
        this.tenPB = tenPB;
    }

    public int getLogoPB() {
        return logoPB;
    }

    public void setLogoPB(int logoPB) {
        this.logoPB = logoPB;
    }

    public String getTenPB() {
        return tenPB;
    }

    public void setTenPB(String tenPB) {
        this.tenPB = tenPB;
    }
}
