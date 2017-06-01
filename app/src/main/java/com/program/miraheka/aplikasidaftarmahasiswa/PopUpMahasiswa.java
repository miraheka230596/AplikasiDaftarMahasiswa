package com.program.miraheka.aplikasidaftarmahasiswa;

import android.Manifest;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.program.miraheka.aplikasidaftarmahasiswa.Model.Mahasiswa;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class PopUpMahasiswa extends DialogFragment {
    ImageView imagePop, imageCall, imageMsg;
    TextView nama, nim;
    Mahasiswa mahasiswa;
    private int RC_CALL = 12;
    static PopUpMahasiswa newInstance(Mahasiswa mahasiswa) {
        PopUpMahasiswa f = new PopUpMahasiswa();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putSerializable("mhs",mahasiswa);
        f.setArguments(args);

        return f;
    }


    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_pop_up_mahasiswa, container, false);
        imagePop = (ImageView) v.findViewById(R.id.imageMahasiswaPop);
        imageCall = (ImageView) v.findViewById(R.id.imageCall);
        imageMsg = (ImageView) v.findViewById(R.id.imageMessage);
        nama = (TextView) v.findViewById(R.id.textMahasiswaPop);
        nim = (TextView) v.findViewById(R.id.textNIMPop);


         mahasiswa = (Mahasiswa) getArguments().getSerializable("mhs");
//        if (bundle != null) {
//            mahasiswa = new Mahasiswa(1, (String) bundle.get("NamaLengkap"), (String) bundle.get("NIM"),
//                    (String) bundle.get("Alamat"), (String) bundle.get("JenisKelamin"), (String) bundle.get("NoTelp"));
//        }
        if (mahasiswa.getJenisKelamin().equals("L")) {
            imagePop.setImageResource(R.drawable.male);
        } else {
            imagePop.setImageResource(R.drawable.female);
        }
        nama.setText(mahasiswa.getNama());
        nim.setText(mahasiswa.getNim());
//
//        imageCall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String number = "tel:" + mahasiswa.getNoTelpon();
//                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
//                if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//                startActivity(callIntent);
//            }
//        });

//        imageMsg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String number = "sms:"+mahasiswa.getNoTelpon();
//                Intent smsIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(number));
//                smsIntent.setType("vnd.android-dir/mms-sms");
//                smsIntent.putExtra("noTelpon",mahasiswa.getNoTelpon());
//                smsIntent.putExtra("sms_body","Hallo hallo");
//                startActivity(smsIntent);
//            }
//        });

        imageMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+mahasiswa.getNoTelpon()));
                intent.putExtra("sms_body", "halo");
                startActivity(intent);
            }
        });
        imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodRequiresCallPermission();
            }
        });
        return v;

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(12)
    private void methodRequiresCallPermission() {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            // Already have permission, do the thing
            // ...
            String number = mahasiswa.getNoTelpon();
            Uri call = Uri.parse("tel:" + number);
            Intent intent = new Intent(Intent.ACTION_CALL, call);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(intent);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "allow this permission? ",
                    RC_CALL, perms);
        }
    }


}
