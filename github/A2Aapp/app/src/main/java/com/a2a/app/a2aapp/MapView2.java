package com.a2a.app.a2aapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.a2a.app.a2aapp.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Mounika on 4/18/2016.
 */
public class MapView2 extends AppCompatActivity implements RoutingListener,Routing1Listener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,GoogleMap.OnMarkerClickListener{
    protected GoogleMap map;
    protected LatLng start;
    protected LatLng end;
    public Spinner RANGE;
    private String username,resp1;
    private String password;
    MarkerFragment mymarker;
    //Required For Fetching Retailers
    JSONArray retailors=null;
    String jsonStr=null;
    String param;
    private ProgressDialog pDialog;
    private LocationManager locationManager;
    private static String url="http://a2a.co.in/webservice2/fetch_retailers.php";
    static String CATEGORY;
    /////
    /// Dummy Marker objects
    //For temporary use
    private static final LatLng marathalli=new LatLng(12.958698, 77.692630);
    private static final LatLng indiranagar=new LatLng(12.974237, 77.640426);
    private static final LatLng majestic= new LatLng(12.977167, 77.569410);
    ///
    //For route mode
    public int route_mode=1;
    MarkerOptions RETAILER_IN_RANGE;
    ///
    ////
    @InjectView(R.id.start)
    AutoCompleteTextView starting;
    @InjectView(R.id.destination)
    AutoCompleteTextView destination;
    @InjectView(R.id.send)
    ImageView send;

    EditText search;
    String product_txt=null;
    Double ret_lat;
    Double ret_long;
    String ret_username;
    Boolean product_present=Boolean.FALSE;

    private static final String LOG_TAG = "MyActivity";
    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutoCompleteAdapter mAdapter;
    private ProgressDialog progressDialog;
    private ProgressDialog pd;
    private ArrayList<Polyline> polylines;
    public static List<MarkerOptions>Product_present_retailor_list;
    public static List<MarkerOptions>retailors_within_range;
    public static int range = 0;
    private static Location MY_LOC;
    private static final int[] COLORS = new int[]{R.color.primary_dark, R.color.primary, R.color.primary_light, R.color.accent, R.color.primary_dark_material_light};

    private static final LatLngBounds BOUNDS_JAMAICA = new LatLngBounds(new LatLng(-57.965341647205726, 144.9987719580531),
            new LatLng(72.77492067739843, -9.998857788741589));

    /**
     * This activity loads a map and then displays the route and pushpins on it.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapview2);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ButterKnife.inject(this);
        polylines = new ArrayList<>();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        MapsInitializer.initialize(this);
        mGoogleApiClient.connect();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        search=(EditText)findViewById(R.id.searchtext);

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        }
        map = mapFragment.getMap();



        mAdapter = new PlaceAutoCompleteAdapter(this, android.R.layout.simple_list_item_1,
                mGoogleApiClient, BOUNDS_JAMAICA, null);

        //For setting the route mode
        ImageView car=(ImageView)findViewById(R.id.carmode);
        ImageView walk=(ImageView)findViewById(R.id.walkmode);
        ImageView transit=(ImageView)findViewById(R.id.transitmode);

        car.setOnClickListener(new ImageView.OnClickListener(){
            @Override
            public void onClick(View v) {
                route_mode=1;
            }
        });

        walk.setOnClickListener(new ImageView.OnClickListener(){
            @Override
            public void onClick(View v) {
                route_mode=2;
            }
        });

        transit.setOnClickListener(new ImageView.OnClickListener(){
            @Override
            public void onClick(View v) {
                route_mode=3;
            }
        });

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker marker) {

                if(marker.getTitle().toString()!="start"||marker.getTitle().toString()!="end") {

                    MarkerFragment fr=new MarkerFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.retcontainer,fr).commit();
                    String rname = marker.getTitle().toString();
                    Log.e("rname",rname);
                    String FETCH_RETAILOR="http://a2a.co.in/webservice2/fetch_retailers.php?id="+rname;

                    ServiceHandler sh=new ServiceHandler();
                    String resp=sh.makeServiceCall(FETCH_RETAILOR,ServiceHandler.GET);
                    Log.e("getresp",resp);
                    try {
                        JSONObject retailerobj = new JSONObject(resp);
                        JSONArray retailerarr = retailerobj.getJSONArray("user");
                        for (int i = 0; i < retailerarr.length(); i++) {
                            JSONObject temp = retailerarr.getJSONObject(i);
                            String ret_id = temp.getString("id");
                            String ret_name = temp.getString("name");
                            String ret_addr = temp.getString("location");
                            //Output Local File for Storing retailordetails
                            try {
                                FileOutputStream file = openFileOutput("retailordet.txt", MODE_WORLD_READABLE);
                                OutputStreamWriter writer = new OutputStreamWriter(file);
                                writer.write(ret_id + "\n");
                                writer.write(ret_name + "\n");
                                writer.write(ret_addr + "" + "\n");
                                writer.flush();
                                writer.close();
                                Log.e("MyActivity", "File Created");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(MapView2.this,"Select From the Retailers icons",Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {
                LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
                mAdapter.setBounds(bounds);
            }
        });

        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(18.013610, -77.498803));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

        map.moveCamera(center);
        map.animateCamera(zoom);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 5000, 0,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        MY_LOC=location;

                        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);

                        map.moveCamera(center);
                        map.animateCamera(zoom);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                3000, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        MY_LOC=location;
                        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude()));
                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);

                        map.moveCamera(center);
                        map.animateCamera(zoom);

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        Toast.makeText(MapView2.this,"Please enable GPS services",Toast.LENGTH_SHORT).show();

                    }
                });

        /*
        * Adds auto complete adapter to both auto complete
        * text views.
        * */
        starting.setAdapter(mAdapter);
        destination.setAdapter(mAdapter);


        /*
        * Sets the start and destination points based on the values selected
        * from the autocomplete text views.
        * */

        starting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final PlaceAutoCompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);
                Log.i(LOG_TAG, "Autocomplete item selected: " + item.description);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
              details about the place.
              */
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (!places.getStatus().isSuccess()) {
                            // Request did not complete successfully
                            Log.e(LOG_TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                            places.release();
                            return;
                        }
// Get the Place object from the buffer.
                        final Place place = places.get(0);

                        start=place.getLatLng();
                    }
                });

            }
        });
        destination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final PlaceAutoCompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);
                Log.i(LOG_TAG, "Autocomplete item selected: " + item.description);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
              details about the place.
              */
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (!places.getStatus().isSuccess()) {
                            // Request did not complete successfully
                            Log.e(LOG_TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                            places.release();
                            return;
                        }
// Get the Place object from the buffer.
                        final Place place = places.get(0);

                        end=place.getLatLng();
                    }
                });

            }
        });

        /*
        These text watchers set the start and end points to null because once there's
        * a change after a value has been selected from the dropdown
        * then the value has to be reselected from dropdown to get
        * the correct location.
        * */
        starting.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int startNum, int before, int count) {
                if (start != null) {
                    start = null;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        destination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(end!=null)
                {
                    end=null;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Spinner for range
        RANGE=(Spinner)findViewById(R.id.range);
        final List<String>range_List=new ArrayList<>();
        range_List.add("1 km");
        range_List.add("2 km");
        range_List.add("3 km");
        range_List.add("4 km");
        ArrayAdapter<String> madap=new ArrayAdapter<String>(MapView2.this,android.R.layout.simple_spinner_dropdown_item,range_List);
        RANGE.setAdapter(madap);

        //Spinner for Categories
        final Spinner CATEGORIES=(Spinner)findViewById(R.id.categories);
        List<String> categories = new ArrayList<String>();
        categories.add("Groceries");
        categories.add("Fruits");
        categories.add("Vegetables");
        categories.add("Cosmetics");
        categories.add("Snacks");
        categories.add("Beverages");
        categories.add("Branded Foods");
        ArrayAdapter<String> madapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,categories);
        CATEGORIES.setAdapter(madapter);

        CATEGORIES.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CATEGORY = CATEGORIES.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                CATEGORY=CATEGORIES.getItemAtPosition(0).toString();
            }
        });


        RANGE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                range=position+1;
                new RangeSyncClass().execute();
                maprets();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Product_present_retailor_list=new ArrayList<MarkerOptions>();
        retailors_within_range=new ArrayList<MarkerOptions>();
    }//End of OnCreate

    @OnClick(R.id.send)

    public void sendRequest()
    {
        if(Util.Operations.isOnline(this))
        {
            route();
        }
        else
        {
            Toast.makeText(this,"No internet connectivity", Toast.LENGTH_SHORT).show();
        }
    }

    public void route()
    {

        if(start==null || end==null)
        {
            if(start==null)
            {
                if(starting.getText().length()>0)
                {
                    starting.setError("Choose location from dropdown.");
                }
                else
                {
                    Toast.makeText(this,"Please choose a starting point.",Toast.LENGTH_SHORT).show();
                }
            }
            if(end==null)
            {
                if(destination.getText().length()>0)
                {
                    destination.setError("Choose location from dropdown.");
                }
                else
                {
                    Toast.makeText(this,"Please choose a destination.",Toast.LENGTH_SHORT).show();
                }
            }
        }
        else
        {
            map.clear();
            progressDialog = ProgressDialog.show(this, "Please wait...",
                    "Fetching route information.", true);
            switch (route_mode) {
                case 1:  Routing routing1 = new Routing.Builder()
                        .travelMode(AbstractRouting.TravelMode.DRIVING)
                        .withListener(this)
                        .alternativeRoutes(true)
                        .waypoints(start, end)
                        .build();
                        routing1.execute();
                        break;
                case 2: Routing routing2 = new Routing.Builder()
                        .travelMode(AbstractRouting.TravelMode.WALKING)
                        .withListener(this)
                        .alternativeRoutes(true)
                        .waypoints(start, end)
                        .build();
                        routing2.execute();
                        break;
                case 3: Routing routing3 = new Routing.Builder()
                        .travelMode(AbstractRouting.TravelMode.TRANSIT)
                        .withListener(this)
                        .alternativeRoutes(true)
                        .waypoints(start, end)
                        .build();
                        routing3.execute();
                        break;
            }

        }
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        // The Routing request failed
        progressDialog.dismiss();
        if(e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingStart() {
        // The Routing Request starts
    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex)
    {
        progressDialog.dismiss();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(start);
        builder.include(end);
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 17));

        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i <route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = map.addPolyline(polyOptions);
            polylines.add(polyline);
        }


        String toast_header="route";
        switch(route_mode){
            case 1: toast_header="Driving Mode:";break;
            case 2: toast_header="Walking Mode:";break;
            case 3: toast_header="Transit Mode:";break;
        }
        Toast.makeText(getApplicationContext(),toast_header+"\n" +
                "distance - "+ (route.get(shortestRouteIndex).getDistanceValue())/1000+" Km : " +
                "Approx duration - "+ (route.get(shortestRouteIndex).getDurationValue())/60 +" mins",Toast.LENGTH_LONG).show();

        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(start);
        options.title("start");
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
        map.addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.title("end");
        options.position(end);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));
        map.addMarker(options);
        //Fetching Retailers to plot approved Retailors.
        //
        ProductSearch();

    }

    @Override
    public void onRouting1Failure(RouteException e) {

    }

    @Override
    public void onRouting1Start() {

    }

    @Override
    public void onRouting1Success(ArrayList<Route> route, int shortestRouteIndex) {

        Route temp=route.get(shortestRouteIndex);
        int tempdist=temp.getDistanceValue();
        if(tempdist<=range)
        {
            retailors_within_range.add(RETAILER_IN_RANGE);
        }

    }

    @Override
    public void onRouting1Cancelled() {

    }


    private class mapRetailers extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MapView2.this);
            pDialog.setMessage("Fetching Retailor Locations...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr=sh.makeServiceCall(url,ServiceHandler.GET);
            Log.i(LOG_TAG, "Response:" + jsonStr);
            param = jsonStr;
            return jsonStr;
        }


        protected void onPostExecute(String res) {
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (res != null) {
                try {
                    JSONObject jsonObj = new JSONObject(res);
                    retailors = jsonObj.getJSONArray("user");
                    for (int i = 0; i < retailors.length(); i++) {
                        JSONObject obj = retailors.getJSONObject(i);
                        double obj_lat = obj.getDouble("latitude");
                        double obj_long = obj.getDouble("longitude");
                        String obj_name = obj.getString("username");
                        int obj_status=obj.getInt("status");
                        if(obj_status==1)
                        {
                            MarkerOptions opt = new MarkerOptions();
                            opt.title(obj_name);
                            LatLng retailor = new LatLng(obj_lat, obj_long);
                            opt.title(obj_name);
                            opt.position(retailor);
                            opt.icon(BitmapDescriptorFactory.fromResource(R.drawable.shopicon));
                            map.addMarker(opt);
                        }
                    }
                    if(pDialog.isShowing()) {
                    pDialog.dismiss();
                    }
                        Toast.makeText(MapView2.this, "Choose from the Available Retailors", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(MapView2.this, "Cannot Retreive Data.Check Internet Connectivity", Toast.LENGTH_LONG).show();

            }

        }
    }

    @Override
    public void onRoutingCancelled() {
        Log.i(LOG_TAG, "Routing was cancelled.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.v(LOG_TAG,connectionResult.toString());
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        if(marker.getTitle().toString()!="start"||marker.getTitle().toString()!="end") {
            MarkerFragment fr=new MarkerFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.retcontainer,fr).commit();
            String rname = marker.getTitle().toString();
            String FETCH_RETAILOR="http://a2a.co.in/webservice2/fetch_retailers.php?retailerid="+rname;
            ServiceHandler sh=new ServiceHandler();
            String resp=sh.makeServiceCall(FETCH_RETAILOR,ServiceHandler.GET);
            try {
                JSONObject retailerobj = new JSONObject(resp);
                JSONArray retailerarr = retailerobj.getJSONArray("user");
                for (int i = 0; i < retailerarr.length(); i++) {
                    JSONObject temp = retailerarr.getJSONObject(i);
                    String ret_id = temp.getString("id");
                    String ret_name = temp.getString("name");
                    String ret_addr = temp.getString("location");
                    //Output Local File for Storing retailordetails
                    try {
                        FileOutputStream file = openFileOutput("retailordet.txt", MODE_WORLD_READABLE);
                        OutputStreamWriter writer = new OutputStreamWriter(file);
                        writer.write(ret_id + "\n");
                        writer.write(ret_name + "\n");
                        writer.write(ret_addr+"" +"\n");
                        writer.flush();
                        writer.close();
                        Log.e("MyActivity", "Retailor File Created");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(MapView2.this,"Select From the Retailers icons",Toast.LENGTH_SHORT).show();
        }

        return false;
    }
    public void checkout(View v){

        Intent i=new Intent(this,CartActivity.class);
        startActivity(i);
    }

    //Product Search

    public void ProductSearch(){
        product_txt=search.getText().toString();
        if(product_txt.isEmpty())
            Toast.makeText(MapView2.this, "Enter a valid product-name", Toast.LENGTH_SHORT).show();
        else
            new ProductSyncClass().execute();

    }

    private void maprets() {
        if (retailors_within_range.size() != 0) {
            for (int i = 0; i < retailors_within_range.size(); i++)
                map.addMarker(retailors_within_range.get(i));
        }
        else
            Toast.makeText(MapView2.this,"No retailors within range",Toast.LENGTH_SHORT).show();
    }

    private class ProductSyncClass extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(MapView2.this, "Filtering Retailors...",
                    "Fetching Retailors from current Database", true);
        }

        @Override
        protected String doInBackground(String... params) {
            ServiceHandler sh = new ServiceHandler();
            String resp = sh.makeServiceCall(url, ServiceHandler.GET);
            try {
                JSONObject retailerobj = new JSONObject(resp);
                JSONArray retailerarr = retailerobj.getJSONArray("user");
                for (int i = 0; i < retailerarr.length(); i++) {
                    JSONObject temp = retailerarr.getJSONObject(i);
                    String ret_id = temp.getString("id");
                    Log.e("MyActivity", "ret_Id= " + ret_id);
                    /*String category_id = temp.getString("category_id");
                    Log.e("category",category_id);*/

                    ret_lat = temp.getDouble("latitude");
                    ret_long = temp.getDouble("longitude");
                    ret_username = temp.getString("username");
                    Log.e("username", ret_username);
                    String FETCH_PRODUCTS = "http://a2a.co.in/webservice2/fetch_products.php?format=json&category=" + CATEGORY + "&retailerid=" + ret_id;
                    Log.e("MyActivity", "FETCH_PRODUCT- " + FETCH_PRODUCTS);
                    ServiceHandler sh1 = new ServiceHandler();
                    resp1 = sh1.makeServiceCall(FETCH_PRODUCTS, ServiceHandler.GET);
                    Log.e("getresponse", resp1);
                    try {
                        JSONObject parentObject = new JSONObject(resp1);
                        JSONArray userDetails = parentObject.getJSONArray("products");
                        /// JSONArray productarr=new JSONArray(resp1);
                        for (int j = 0; j < userDetails.length(); j++) {
                            JSONObject a = userDetails.getJSONObject(j);
                            String product = a.getString("product_name");
                            Log.e("geavalue", product);

                            if (product.equals(product_txt)) {
                                product_present = Boolean.TRUE;
                                break;
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (product_present == Boolean.TRUE) {
                        MarkerOptions mark = new MarkerOptions();
                        mark.title(ret_username);
                        mark.position(new LatLng(ret_lat, ret_long));
                        mark.icon(BitmapDescriptorFactory.fromResource(R.drawable.shop_icon));

                        Product_present_retailor_list.add(mark);

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return resp1;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            if (Product_present_retailor_list.size() == 0)
                Toast.makeText(MapView2.this, "Product not Found", Toast.LENGTH_SHORT).show();
            else

            {
                for (int i = 0; i < Product_present_retailor_list.size();i++)
                {
                    map.addMarker(Product_present_retailor_list.get(i));
                }
            }

        }

    }



    //////Code for Plotting Retailors within Range
    /////
    ///
    private class RangeSyncClass extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(MapView2.this, "Mapping Retailors...",
                    "Plotting Retailors Within Range=" + RANGE.getSelectedItem().toString(), true);
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            for (int i = 0; i < Product_present_retailor_list.size(); i++) {
                String ret_title=Product_present_retailor_list.get(i).getTitle();
                LatLng ret= Product_present_retailor_list.get(i).getPosition();
                LatLng myloc=new LatLng(MY_LOC.getLatitude(),MY_LOC.getLongitude());
                int max_shortest_distance=0;
                Routing routing1 = new Routing.Builder()
                        .travelMode(AbstractRouting.TravelMode.DRIVING)
                        .withListener(MapView2.this)
                        .alternativeRoutes(true)
                        .waypoints(myloc,ret)
                        .build();
                routing1.execute();
                RETAILER_IN_RANGE=new MarkerOptions();
                RETAILER_IN_RANGE.position(ret);
                RETAILER_IN_RANGE.title(ret_title);
                RETAILER_IN_RANGE.icon(BitmapDescriptorFactory.fromResource(R.drawable.shop_icon));
            }
            pd.dismiss();

        }
    }
    //////
    /////
    ///

    public void map_myloc(View v){

        MarkerOptions myloc=new MarkerOptions();
        double lat=MY_LOC.getLatitude();
        double lon=MY_LOC.getLongitude();
        myloc.title("My Location");
        LatLng my_loc=new LatLng(lat,lon);
        myloc.position(my_loc);
        myloc.icon(BitmapDescriptorFactory.fromResource(R.drawable.pushpin));
        map.addMarker(myloc);

        CameraUpdate center = CameraUpdateFactory.newLatLng(my_loc);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);

        map.moveCamera(center);
        map.animateCamera(zoom);

    }


    }


