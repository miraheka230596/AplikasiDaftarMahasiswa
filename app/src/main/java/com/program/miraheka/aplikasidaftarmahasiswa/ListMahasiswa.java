package com.program.miraheka.aplikasidaftarmahasiswa;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.program.miraheka.aplikasidaftarmahasiswa.Adapter.ListMahasiswaAdapter;
import com.program.miraheka.aplikasidaftarmahasiswa.Model.Mahasiswa;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListMahasiswa extends AppCompatActivity {
    ListView listFormMahasiswa;
    FloatingActionButton floatList;
    ArrayList<Mahasiswa> mahasiswaArrayList;

    ListMahasiswaAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mahasiswa);
        listFormMahasiswa = (ListView) findViewById(R.id.listFormMahasiswa);
        floatList = (FloatingActionButton) findViewById(R.id.floatList);

        floatList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListMahasiswa.this,FormAddMahasiswa.class);
                startActivity(intent);
            }
        });


        mahasiswaArrayList = new ArrayList<>();
        //addMahasiswa();
        getAllMahasiswa();;
        adapter = new ListMahasiswaAdapter(mahasiswaArrayList,this);
        listFormMahasiswa.setAdapter(adapter);

        listFormMahasiswa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Mahasiswa mahasiswa = mahasiswaArrayList.get(i);
                Log.i("infomirah", "onItemClick: "+mahasiswa.getNama());
                Toast.makeText(ListMahasiswa.this,mahasiswa.getNama(),Toast.LENGTH_SHORT).show();
                showDialog(mahasiswa);
            }
        });

        adapter.notifyDataSetChanged();

    }

    public void addMahasiswa(){
        mahasiswaArrayList.add(new Mahasiswa(1,"Grace","130112567","sukabirus","P","081123345330"));
        mahasiswaArrayList.add(new Mahasiswa(2,"Mirah","130112567","sukabirus","P","081123345330"));
        mahasiswaArrayList.add(new Mahasiswa(3,"Isma","130112567","sukabirus","P","081123345330"));
        mahasiswaArrayList.add(new Mahasiswa(4,"Archie","130112567","sukabirus","L","081123345330"));
        mahasiswaArrayList.add(new Mahasiswa(5,"Rahmat","130112567","sukabirus","L","081123345330"));
    }

    void showDialog(Mahasiswa mahasiswa) {

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentManager fm = getSupportFragmentManager();

//        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
//        if (prev != null) {
//            ft.remove(prev);
//        }
//        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = PopUpMahasiswa.newInstance(mahasiswa);
        newFragment.show(fm,"Hallo");
    }

    //ngambil respons di JSON
    public void getAllMahasiswa(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://api-android.herokuapp.com/mahasiswa",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(ListMahasiswa.this, response, Toast.LENGTH_SHORT).show();
                        try{
                            JSONObject objectMhs = new JSONObject(response);
                            JSONArray arrayMhs = objectMhs.getJSONArray("data");

                            for (int i = 0; i < arrayMhs.length(); i++) {
                                JSONObject object = arrayMhs.getJSONObject(i);
                                Mahasiswa mahasiswa = new Mahasiswa(object.getInt("id"),object.getString("nama"), object.getString("nim"), object.getString("alamat"), object.getString("jenisKelamin"),object.getString("noTelpon"));
                                mahasiswaArrayList.add(mahasiswa);
                            }

                            adapter.notifyDataSetChanged();



                        }catch (Exception e){
                            Toast.makeText(ListMahasiswa.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListMahasiswa.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //post
                //params.put("nama","value");

                return params;
            }
        };
        //menjalankan method
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
