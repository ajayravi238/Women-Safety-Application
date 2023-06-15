package com.example.womensafetyapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnClickListener {

    public TextView Address;
    public Button buttonCall;
    public Button buttonLocationSend;
    public Context context = this;
    public DatabaseHelper databaseHelper;
    LocationManager locationManager;

    public GoogleMap mMap;
    public String result = null;
    public User user;

    private ImageView qrImage;
    private String inputValue;
    private String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private String TAG = "GenerateQrCode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        initViews();
        initListeners();
        initObjects();

        //((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        supportMapFragment.getMapAsync(MapsActivity.this);
        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0 && ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
            Toast.makeText(this,"Turn on loacation Please",Toast.LENGTH_LONG).show();
            Log.d("REEE","Turn on location");
            return;
        }
        if (this.locationManager.isProviderEnabled("network")) {
            this.locationManager.requestLocationUpdates("network", 0, 0.0f, new LocationListener() {
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng latLng = new LatLng(latitude, longitude);
                    try {
                        List<Address> addressList = new Geocoder(MapsActivity.this.getApplicationContext()).getFromLocation(latitude, longitude, 1);
                        Log.d("Address",addressList.toString());
                        String str = ((((Address) addressList.get(0)).getLocality() + ",") + ((Address) addressList.get(0)).getCountryName() + ",") + ((Address) addressList.get(0)).getAdminArea();
                        Address address = (Address) addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)).append("\n");
                        }
                        sb.append(address.getAddressLine(0)).append("\n");
                        sb.append(address.getLocality()).append("\n");
                        sb.append(address.getAdminArea()).append("\n");
                        sb.append(address.getPostalCode()).append("\n");
                        sb.append(address.getFeatureName()).append("\n");
                        sb.append(address.getCountryName());
                        MapsActivity.this.result = sb.toString();
                        MapsActivity.this.Address.setText("Address: " + MapsActivity.this.result);
                        MapsActivity.this.mMap.clear();
                        MapsActivity.this.mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        qrcodegenerater(result);
                        MapsActivity.this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.9f));
                        MapsActivity.this.LanLatData(latitude, longitude);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            });
        } else if (this.locationManager.isProviderEnabled("gps")) {
            this.locationManager.requestLocationUpdates("gps", 0, 0.0f, new LocationListener() {
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng latLng = new LatLng(latitude, longitude);
                    try {
                        List<Address> addressList = new Geocoder(MapsActivity.this.getApplicationContext()).getFromLocation(latitude, longitude, 1);
                        Log.d("RES",addressList.toString());
                        String str = ((((Address) addressList.get(0)).getLocality() + ",") + ((Address) addressList.get(0)).getCountryName() + ",") + ((Address) addressList.get(0)).getAdminArea();
                        Address address = (Address) addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)).append("\n");
                        }
                        sb.append(address.getAddressLine(0)).append("\n");
                        sb.append(address.getLocality()).append("\n");
                        sb.append(address.getAdminArea()).append("\n");
                        sb.append(address.getPostalCode()).append("\n");
                        sb.append(address.getFeatureName()).append("\n");
                        sb.append(address.getCountryName());
                        MapsActivity.this.result = sb.toString();
                        MapsActivity.this.Address.setText("Address: " + MapsActivity.this.result);
                        MapsActivity.this.mMap.clear();
                        MapsActivity.this.mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        qrcodegenerater(result);
                        MapsActivity.this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.9f));
                        MapsActivity.this.LanLatData(latitude, longitude);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            });
        }


    }

    private void qrcodegenerater(String res) {

        String mobileno1 = "";
        String mobileno2 = "";
        String mobileno3 = "";
        String mobileno4 = "";
        String mobileno5 = "";

        Cursor cursor = databaseHelper.getdata("SELECT * FROM Mobile_Reg_no");
        while (cursor.moveToNext())
        {
            mobileno1 = cursor.getString(1);
            mobileno2 = cursor.getString(2);
            mobileno3 = cursor.getString(3);
            mobileno4 = cursor.getString(4);
            mobileno5 = cursor.getString(5);
        }

        inputValue = "Name : "+ getIntent().getStringExtra("Name") +" Mobile No: "+ getIntent().getStringExtra("mobile") +" Current Location : "+ res +" Parents Mobile No1 : " + mobileno1 + "  Mobile No2 : " + mobileno2 + " Mobile No3 : " + mobileno3 + " Mobile No4 : "+ mobileno4 + " Mobile No5 : "+ mobileno5;
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;

        qrgEncoder = new QRGEncoder(
                inputValue, null,
                QRGContents.Type.TEXT,
                smallerDimension);
        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            qrImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.v(TAG, e.toString());
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
    }

    private void initViews() {
        this.buttonLocationSend = (Button) findViewById(R.id.buttonLocationSend);
        this.buttonCall = (Button) findViewById(R.id.buttonCall);
        this.Address = (TextView) findViewById(R.id.Address);
        this.qrImage = findViewById(R.id.qr_image);
    }

    private void initListeners() {
        this.buttonCall.setOnClickListener(this);
    }

    private void initObjects() {
        this.databaseHelper = new DatabaseHelper(this);
        this.user = new User();
    }

    public void LanLatData(double latitude, double longitude) {
        this.buttonLocationSend.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Cursor res = MapsActivity.this.databaseHelper.getAllMobileNo();
                if (res.getCount() != 0) {
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer.append(res.getString(1));
                    }
                    String data = buffer.toString();
                    MapsActivity.this.sendSMS(data, MapsActivity.this.result);
                    Cursor res1 = MapsActivity.this.databaseHelper.getAllMobileNo1();
                    if (res1.getCount() != 0) {
                        StringBuffer buffer1 = new StringBuffer();
                        while (res1.moveToNext()) {
                            buffer1.append(res1.getString(2));
                        }
                        String data1 = buffer1.toString();
                        MapsActivity.this.sendSMS(data1, MapsActivity.this.result);
                        Cursor res2 = MapsActivity.this.databaseHelper.getAllMobileNo2();
                        if (res2.getCount() != 0) {
                            StringBuffer buffer2 = new StringBuffer();
                            while (res2.moveToNext()) {
                                buffer2.append(res2.getString(3));
                            }
                            String data2 = buffer2.toString();
                            MapsActivity.this.sendSMS(data2, MapsActivity.this.result);
                            Cursor res3 = MapsActivity.this.databaseHelper.getAllMobileNo3();
                            if (res3.getCount() != 0) {
                                StringBuffer buffer3 = new StringBuffer();
                                while (res3.moveToNext()) {
                                    buffer3.append(res3.getString(4));
                                }
                                String data3 = buffer3.toString();
                                MapsActivity.this.sendSMS(data3, MapsActivity.this.result);
                                Cursor res4 = MapsActivity.this.databaseHelper.getAllMobileNo4();
                                if (res4.getCount() != 0) {
                                    StringBuffer buffer4 = new StringBuffer();
                                    while (res4.moveToNext()) {
                                        buffer4.append(res4.getString(5));
                                    }
                                    String data4 = buffer4.toString();
                                    MapsActivity.this.sendSMS(data4, MapsActivity.this.result);
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager.getDefault().sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCall :
                Cursor res = this.databaseHelper.getAllMobileNo();
                if (res.getCount() != 0) {
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer.append(res.getString(1));
                    }
                    String data = buffer.toString();
                    if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_CALENDAR") != 0) {
                    }
                    Intent intent = new Intent("android.intent.action.CALL");
                    intent.setData(Uri.parse("tel:" + data));
                    this.context.startActivity(intent);
                    return;
                }
                return;
            default:
                return;
        }
    }
}
