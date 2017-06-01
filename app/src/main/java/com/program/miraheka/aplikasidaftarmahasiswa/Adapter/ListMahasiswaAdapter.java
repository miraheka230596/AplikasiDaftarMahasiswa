package com.program.miraheka.aplikasidaftarmahasiswa.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.program.miraheka.aplikasidaftarmahasiswa.Model.Mahasiswa;
import com.program.miraheka.aplikasidaftarmahasiswa.R;

import java.util.ArrayList;

/**
 * Created by Mirah on 5/27/2017.
 */

public class ListMahasiswaAdapter extends BaseAdapter   {
    ArrayList<Mahasiswa> mahasiswaArrayList;
    Context context; //sesuatu yg dimiliki diactivity

    public ListMahasiswaAdapter(ArrayList<Mahasiswa> mahasiswaArrayList, Context context) {
        this.mahasiswaArrayList = mahasiswaArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mahasiswaArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return mahasiswaArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View views = view;
        if (views == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            views = inflater.inflate(R.layout.listrow_mahasiswa,viewGroup,false);
        }
        Mahasiswa mahasiswa = mahasiswaArrayList.get(i);

        TextView nama = (TextView) views.findViewById(R.id.textNamaListRow);
        TextView nim = (TextView) views.findViewById(R.id.textNIMListRow);
        ImageView logo = (ImageView) views.findViewById(R.id.imageLogoListRow);

        nama.setText(mahasiswa.getNama());
        nim.setText(mahasiswa.getNim());


        if (mahasiswa.getJenisKelamin().equals("L")){
            logo.setImageResource(R.drawable.male);
        }else{
            logo.setImageResource(R.drawable.female);
        }
        return views;
    }
}
