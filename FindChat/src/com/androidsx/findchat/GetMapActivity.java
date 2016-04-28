package com.androidsx.findchat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GetMapActivity extends FragmentActivity implements LocationListener {

    GoogleMap googleMap;
    double latitude=17.410;
    double longitude=78.452;
     TextView getaddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //show error dialog if GoolglePlayServices not available
       
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        setContentView(R.layout.getmapadd_main);
          getaddress = (TextView) findViewById(R.id.getaddress);
        SupportMapFragment supportMapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
        googleMap = supportMapFragment.getMap();
        googleMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);

        try {
    		List<Address> addresses = geocoder.getFromLocation(latitude,longitude, 1);
    		
    		if(addresses != null) {
    			Address returnedAddress = addresses.get(0);
    			StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
    			for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
    				strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
    			}
    			getaddress.setText(strReturnedAddress.toString());
    		}
    		else{
    			getaddress.setText("No Address returned!");
    		}
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		getaddress.setText("Canont get Address!");
    	}
        getaddress.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("ADDRESS", getaddress.getText().toString());
				setResult(2,i);

				finish();
				
			}
		});
    }

    @Override
    public void onLocationChanged(Location location) {
        TextView locationTv = (TextView) findViewById(R.id.latlongLocation);
        latitude = location.getLatitude();
    longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        locationTv.setText("Latitude:" + latitude + ", Longitude:" + longitude);
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

   

}

