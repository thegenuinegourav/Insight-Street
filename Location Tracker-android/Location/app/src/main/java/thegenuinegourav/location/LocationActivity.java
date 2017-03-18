package thegenuinegourav.location;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LocationActivity extends Activity implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 5;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;

    private ArrayList<String> shopNames = new ArrayList<String>();
    private ArrayList<Double> latitudes = new ArrayList<Double>();
    private ArrayList<Double> longitudes = new ArrayList<Double>();
    private TextView past,upcoming,current;
    private int flag=1;
    private TextView text2,text3, text4,text5,text6,text7,text8, text9,text10,text11,text12,text13;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate ...............................");
        //show error dialog if GoolglePlayServices not available
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        setContentView(R.layout.activity_location);

        past = (TextView)findViewById(R.id.past);
        upcoming = (TextView)findViewById(R.id.upcoming);
        current  =(TextView)findViewById(R.id.current);

        text2 = (TextView)findViewById(R.id.textView2);
        text3 = (TextView)findViewById(R.id.textView3);
        text4 = (TextView)findViewById(R.id.textView4);
        text5 = (TextView)findViewById(R.id.textView5);
        text6 = (TextView)findViewById(R.id.textView6);
        text7 = (TextView)findViewById(R.id.textView7);
        text8 = (TextView)findViewById(R.id.textView8);
        text9 = (TextView)findViewById(R.id.textView9);
        text10 = (TextView)findViewById(R.id.textView10);
        text11 = (TextView)findViewById(R.id.textView11);
        text12 = (TextView)findViewById(R.id.textView12);
        text13 = (TextView)findViewById(R.id.textView13);



    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired ..............");
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop fired ..............");
        mGoogleApiClient.disconnect();
        Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
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

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        startLocationUpdates();
    }

    protected void startLocationUpdates() {

        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "Location update started ..............: ");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        shopNames.add("Shop A"); shopNames.add("Shop B"); shopNames.add("Shop C");
        shopNames.add("Shop D"); shopNames.add("Shop E");
        latitudes.add(25.4321687); latitudes.add(25.4322103); latitudes.add(25.4322173);
        latitudes.add(25.4323121); latitudes.add(25.4323074);
        longitudes.add(81.770623); longitudes.add(81.7707612); longitudes.add(81.7707559);
        longitudes.add(81.7706216); longitudes.add(81.7706237);
        Log.d(TAG, "Firing onLocationChanged..............................................");
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();
        moveFootPath();
    }

    public void moveFootPath() {
        if (flag == 1) {
            text2.setVisibility(View.INVISIBLE);
            text4.setVisibility(View.INVISIBLE);
            text6.setVisibility(View.INVISIBLE);
            text8.setVisibility(View.INVISIBLE);
            text10.setVisibility(View.INVISIBLE);
            text12.setVisibility(View.INVISIBLE);
            text3.setVisibility(View.VISIBLE);
            text5.setVisibility(View.VISIBLE);
            text7.setVisibility(View.VISIBLE);
            text9.setVisibility(View.VISIBLE);
            text11.setVisibility(View.VISIBLE);
            text13.setVisibility(View.VISIBLE);
            flag=2;
        }else if(flag==2)
        {
            text3.setVisibility(View.INVISIBLE);
            text5.setVisibility(View.INVISIBLE);
            text7.setVisibility(View.INVISIBLE);
            text9.setVisibility(View.INVISIBLE);
            text11.setVisibility(View.INVISIBLE);
            text13.setVisibility(View.INVISIBLE);
            text2.setVisibility(View.VISIBLE);
            text4.setVisibility(View.VISIBLE);
            text6.setVisibility(View.VISIBLE);
            text8.setVisibility(View.VISIBLE);
            text10.setVisibility(View.VISIBLE);
            text12.setVisibility(View.VISIBLE);
            flag=1;
        }
    }

    private void updateUI() {
        Log.d(TAG, "UI update initiated .............");
        if (null != mCurrentLocation) {
           /* String lat = String.valueOf(mCurrentLocation.getLatitude());
            String lng = String.valueOf(mCurrentLocation.getLongitude());
            tvLocation.setText("At Time: " + mLastUpdateTime + "\n" +
                    "Latitude: " + lat + "\n" +
                    "Longitude: " + lng + "\n" +

                    "Accuracy: " + mCurrentLocation.getAccuracy() + "\n" +
                    "Provider: " + mCurrentLocation.getProvider());
*/
            String nearShops[]=nearestShops(shopNames,latitudes,longitudes);
            //shops.setText("First : "+ nearShops[0]  + " \nSecond : "+nearShops[1] + "\nThird : "+nearShops[2] );
            current.setText(nearShops[0]); past.setText(nearShops[1]); upcoming.setText(nearShops[2]);

        } else {
            Log.d(TAG, "location is null ...............");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        Log.d(TAG, "Location update stopped .......................");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }
    }

    public String[] nearestShops(ArrayList<String> shopNames,ArrayList<Double> latitudes,ArrayList<Double> longitudes)
    {
        int pos=0,temp;
        double small;
        String[] result= new String[shopNames.size()];

        for(int j=0;j<3;j++) {
            double min = distance(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), latitudes.get(j), longitudes.get(j));
            for (int i = 0; i < shopNames.size(); i++) {
                if (min > distance(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), latitudes.get(i), longitudes.get(i))) {
                    min = distance(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), latitudes.get(i), longitudes.get(i));
                    pos = i;
                }
            }
            result[j] = shopNames.get(pos);
            shopNames.remove(pos);
        }


     /*   ArrayList<String> result = new ArrayList<String>();
        result.add(shopNames.get(pos));
        shopNames.remove(pos);

        min = distance(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude(),latitudes.get(0),longitudes.get(0));
        for(int i=0;i<shopNames.size();i++)
        {
            if(min>distance(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude(),latitudes.get(i),longitudes.get(i)))
            {
                min=distance(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude(),latitudes.get(i),longitudes.get(i));
                pos=i;
            }
        }
        result.add(shopNames.get(pos));
        shopNames.remove(pos);

        min = distance(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude(),latitudes.get(0),longitudes.get(0));
        for(int i=0;i<shopNames.size();i++)
        {
            if(min>distance(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude(),latitudes.get(i),longitudes.get(i)))
            {
                min=distance(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude(),latitudes.get(i),longitudes.get(i));
                pos=i;
            }
        }
        result.add(shopNames.get(pos));
        shopNames.remove(pos);


*/
        return result;
    }


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


}