package com.program.miraheka.aplikasidaftarmahasiswa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.program.miraheka.aplikasidaftarmahasiswa.Model.Mahasiswa;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FormAddMahasiswa extends AppCompatActivity {
    EditText editNamaLengkap, editNIM, editAlamat, editNomorTelepon;
    Button buttonSimpan;
    RadioButton radioPerempuan, radioLakiLaki;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_add_mahasiswa);
        editNamaLengkap = (EditText) findViewById(R.id.editNamaLengkap);
        editNIM = (EditText) findViewById(R.id.editNIM);
        editAlamat = (EditText) findViewById(R.id.editAlamat);
        editNomorTelepon = (EditText) findViewById(R.id.editNomorTelepon);
        buttonSimpan = (Button) findViewById(R.id.buttonSimpan);
        radioPerempuan = (RadioButton) findViewById(R.id.radioPerempuan);
        radioLakiLaki = (RadioButton) findViewById(R.id.radioLakiLaki);


        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mahasiswa mahasiswa;
                if (editNIM.length() == 10 && editAlamat.length()>=4 && editNomorTelepon.length()>=7){
                    if (radioPerempuan.isChecked()) {
                        mahasiswa = new Mahasiswa(1, editNamaLengkap.getText().toString(), editNIM.getText().toString(), editAlamat.getText().toString(), "P", editNomorTelepon.getText().toString());
                        postData("http://api-android.herokuapp.com/mahasiswa",mahasiswa);
                        //Toast.makeText(FormAddMahasiswa.this, ""+mahasiswa.toString(), Toast.LENGTH_SHORT).show();
                    }else if (radioLakiLaki.isChecked()){
                        mahasiswa = new Mahasiswa(1, editNamaLengkap.getText().toString(), editNIM.getText().toString(), editAlamat.getText().toString(),"L",editNomorTelepon.getText().toString());
                        postData("http://api-android.herokuapp.com/mahasiswa",mahasiswa);
                        //Toast.makeText(FormAddMahasiswa.this, ""+mahasiswa.toString(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(FormAddMahasiswa.this, "Lengkapi Form", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(FormAddMahasiswa.this, "NIM salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void postData(String url,final Mahasiswa mahasiswa){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://api-android.herokuapp.com/mahasiswa",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       Toast.makeText(FormAddMahasiswa.this, ""+response, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(mahasiswa.this, "lakukan",Toast.LENGTH_SHORT).show();
                        String status;
                        try{
                            JSONObject objectMhs = new JSONObject(response);
                            if (objectMhs.getString("status").equals("success")){
                                editNamaLengkap.setText("");
                                editNIM.setText("");
                                editAlamat.setText("");
                                editNomorTelepon.setText("");
                                radioLakiLaki.setChecked(false);
                                radioPerempuan.setChecked(false);
                                Toast.makeText(FormAddMahasiswa.this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(FormAddMahasiswa.this, ListMahasiswa.class));
                                finish();
                            } else {
                                Toast.makeText(FormAddMahasiswa.this, "Data Gagal Ditambahkan", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(FormAddMahasiswa.this, "gagagagagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FormAddMahasiswa.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                try {

                    params.put("nama", mahasiswa.getNama());
                    params.put("nim", mahasiswa.getNim());
                    params.put("jenisKelamin", mahasiswa.getJenisKelamin());
                    params.put("alamat", mahasiswa.getAlamat());
                    params.put("noTelpon", mahasiswa.getNoTelpon());
                    return params;
                }catch (Exception e){
                    Toast.makeText(FormAddMahasiswa.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    return params;
                }
            }
        };
        //menjalankan method
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
