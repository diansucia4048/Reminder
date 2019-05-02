package org.d3ifcool.com.reminder;

public class Reminder {
    private String nama, deskripsi, waktu;
    private int gambar;

    public Reminder(String nama, String deskripsi, String waktu, int gambar) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.waktu = waktu;
        this.gambar = gambar;
    }

    public String getNama() {
        return nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getWaktu() {
        return waktu;
    }

    public int getGambar() {
        return gambar;
    }
}
