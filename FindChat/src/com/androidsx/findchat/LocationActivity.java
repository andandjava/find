package com.androidsx.findchat;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class LocationActivity extends Activity implements LocationListener  {
  private TextView latituteField;
  private TextView longitudeField;
  private LocationManager locationManager;
  private String provider;

  
/** Called when the activity is first created. */

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.location_main);
    try {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(LocationActivity.this);
        Toast.makeText(this, "before if",
                Toast.LENGTH_LONG).show();

        if (16 != 0 || 17 != 0) {
            addresses = geocoder.getFromLocation(16 ,
                    17, 1);
                    String address = addresses.get(0).getAddressLine(0);
        String city = addresses.get(0).getAddressLine(1);
        String country = addresses.get(0).getAddressLine(2);
         Log.d("TAG", "address = "+address+", city ="+city+", country = "+country );
        Toast.makeText(this, "address = "+address+", city ="+city+", country = "+country, Toast.LENGTH_LONG).show();

        } 
        else {
            Toast.makeText(this, "latitude and longitude are null",
                    Toast.LENGTH_LONG).show();

        }
    } catch (Exception e) {
        e.printStackTrace();

    }
  
 
    latituteField = (TextView) findViewById(R.id.TextView02);
    longitudeField = (TextView) findViewById(R.id.TextView04);

    // Get the location manager
    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    // Define the criteria how to select the locatioin provider -> use
    // default
    Criteria criteria = new Criteria();
    provider = locationManager.getBestProvider(criteria, false);
    Location location = locationManager.getLastKnownLocation(provider);

    // Initialize the location fields
    if (location != null) {
      System.out.println("Provider " + provider + " has been selected.");
      onLocationChanged(location);
    } else {
      latituteField.setText("Location not available");
      longitudeField.setText("Location not available");
    }
  }

  /* Request updates at startup */
  @Override
  protected void onResume() {
    super.onResume();
    locationManager.requestLocationUpdates(provider, 400, 1, this);
  }

  /* Remove the locationlistener updates when Activity is paused */
  @Override
  protected void onPause() {
    super.onPause();
    locationManager.removeUpdates(this);
  }

  @Override
  public void onLocationChanged(Location location) {
    int lat = (int) (location.getLatitude());
    int lng = (int) (location.getLongitude());
    latituteField.setText(String.valueOf(lat));
    longitudeField.setText(String.valueOf(lng));
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onProviderEnabled(String provider) {
    Toast.makeText(this, "Enabled new provider " + provider,
        Toast.LENGTH_SHORT).show();

  }

  @Override
  public void onProviderDisabled(String provider) {
    Toast.makeText(this, "Disabled provider " + provider,
        Toast.LENGTH_SHORT).show();
  }

}



