package com.program.miraheka.aplikasidaftarmahasiswa.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mirah on 5/27/2017.
 */

public class Mahasiswa implements Serializable{
    private int id;
    private String nama, nim, alamat, noTelpon, jenisKelamin;

    public Mahasiswa(int id, String nama, String nim, String alamat, String jenisKelamin, String noTelpon) {
        this.id = id;
        this.nama = nama;
        this.nim = nim;
        this.alamat = alamat;
        this.jenisKelamin = jenisKelamin;
        this.noTelpon = noTelpon;

    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getNim() {
        return nim;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNoTelpon() {
        return noTelpon;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNoTelpon(String noTelpon) {
        this.noTelpon = noTelpon;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
}
