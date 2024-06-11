package fpt.dungnm.assignment.models;

import java.io.Serializable;

public class NhanVien implements Serializable {
    private int avatarNV;
    private String tenNV;
    private String maNV;
    private String diaChi;
    private String phongBan;

    public NhanVien(int avatarNV, String tenNV, String maNV, String diaChi, String phongBan) {
        this.avatarNV = avatarNV;
        this.tenNV = tenNV;
        this.maNV = maNV;
        this.diaChi = diaChi;
        this.phongBan = phongBan;
    }

    public int getAvatarNV() {
        return avatarNV;
    }

    public void setAvatarNV(int avatarNV) {
        this.avatarNV = avatarNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }
}
