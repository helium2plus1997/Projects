package com.a2a.app.a2aapp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by Mounika on 3/3/2016.
 */
public class MapView1 extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener {
    GoogleMap map;
    LatLng loc;
    Button msearch, mhome, ok;
    String address_str;
    EditText location;
    int type;
    //protected GoogleApiClient mGoogleApiClient;
    //private PlaceAutoCompleteAdapter madapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapview);
        msearch = (Button) findViewById(R.id.search);
        ok = (Button) findViewById(R.id.ok);
        location = (EditText) findViewById(R.id.location);
        Bundle data = getIntent().getExtras();
        type = data.getInt("type");
        // msearch.setEnabled(!msearch.isEnabled());
        // Getting reference to SupportMapFragment of the activity_main
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        MapsInitializer.initialize(this);
        // Getting Map for the SupportMapFragment
        map = fm.getMap();

        if (fm == null) {
            fm = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.map, fm).commit();

        }

        if(map!=null) {

            ok.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {

                            if(type==1)
                            {
                            Intent address = new Intent(MapView1.this, Register.class);
                            address.putExtra("address", address_str);
                            setResult(2, address);
                            finish();
                             }


                            if (type == 2) {

                            if (loc != null) {
                                    Intent cordinates = new Intent(MapView1.this, RetailorRegistration.class);
                                    Double lat = loc.latitude;
                                    Double lon = loc.longitude;
                                    String latitude = lat.toString();
                                    String longitude = lon.toString();
                                    cordinates.putExtra("Latitude", latitude);
                                    cordinates.putExtra("Longitude", longitude);
                                    setResult(1, cordinates);
                                    finish();
                                }
                            else {
                                Toast.makeText(getApplicationContext(), "Select a location From Map and then proceed", Toast.LENGTH_SHORT).show();
                            }

                            }
                        }
                    }
            );


            // Setting onclick event listener for the map
            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                                          @Override
                                          public void onMapClick(LatLng point) {
                                              // Already one locations
                                              if (loc != null) {
                                                  map.clear();
                                              }

                                              // Creating MarkerOptions
                                              MarkerOptions options = new MarkerOptions();

                                              // Setting the position of the marker
                                              options.position(point);
                                              CameraUpdate center = CameraUpdateFactory.newLatLng(point);
                                              CameraUpdate zoom = CameraUpdateFactory.zoomTo(8);
                                              options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                              map.addMarker(options);
                                              map.moveCamera(center);
                                              map.animateCamera(zoom);



                                              loc = point;
                                              Geocoder geo=new Geocoder(MapView1.this);
                                              List<Address> add_list=null;
                                              try {
                                                  add_list=geo.getFromLocation(loc.latitude,loc.longitude,1);
                                              } catch (IOException e) {
                                                  e.printStackTrace();
                                              }
                                                  Address add=add_list.get(0);
                                              if(add==null)
                                              {Toast.makeText(MapView1.this,"Unable to fetch Address.Try Again",Toast.LENGTH_SHORT).show();}
                                              else {
                                                  StringBuilder sb = new StringBuilder();
                                                  for (int i = 0; i < add.getMaxAddressLineIndex(); i++) {
                                                      sb.append(add.getAddressLine(i)).append("\n");
                                                  }
                                                  sb.append(add.getLocality()).append("\n");
                                                  sb.append(add.getPostalCode()).append("\n");
                                                  sb.append(add.getCountryName());
                                                  address_str = sb.toString();
                                              }

                                          }
                                      }
            );
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Error loading Google Maps", Toast.LENGTH_SHORT).show();
            finish();

        }
    }



   GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng mylatlng = new LatLng(location.getLatitude(), location.getLongitude());
            map.addMarker(new MarkerOptions().position(mylatlng));
            if(map != null){
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(mylatlng, 16.0f));

                loc=mylatlng;
            }
        }
    };


    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(MapView1.this,"Please Confirm Location",Toast.LENGTH_SHORT).show();
        return false;
    }
/* private void sendRequest() {

        String origin = etOrigin.getText().toString();
        String destination = etDestination.getText().toString();
        if(origin.isEmpty()){
            Toast.makeText(this,"Please enter origin Address!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(destination.isEmpty()){
            Toast.makeText(this,"Please enter Destination Address!",Toast.LENGTH_SHORT).show();
            return;
        }
       /* try{
            new DirectionFinder(this,origin,destination).execute();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    } */

     public void onSearch(View view) {
         String locationsearch = location.getText().toString();
         List<Address> addressList = null;

         if (locationsearch != null || !locationsearch.equals("")) {
             Geocoder geocoder = new Geocoder(MapView1.this);
             try {
                 addressList = geocoder.getFromLocationName(locationsearch, 1);
             } catch (IOException e) {
                 e.printStackTrace();
             }

             Address address = addressList.get(0);
             //String For Complete Address
             StringBuilder sb = new StringBuilder();
             for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                 sb.append(address.getAddressLine(i)).append("\n");
             }
             sb.append(address.getLocality()).append("\n");
             sb.append(address.getPostalCode()).append("\n");
             sb.append(address.getCountryName());
             address_str = sb.toString();

             LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
             map.addMarker(new MarkerOptions().position(latLng).title(locationsearch));
             map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
             loc=latLng;
         }
         else{
             Toast.makeText(MapView1.this,"Enter a Valid Location",Toast.LENGTH_SHORT).show();
         }


             }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this,"Connection established",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(),"Connection Failed ",Toast.LENGTH_SHORT).show();
    }
}