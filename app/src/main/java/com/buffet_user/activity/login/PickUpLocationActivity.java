package com.buffet_user.activity.login;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buffet_user.R;
import com.buffet_user.activity.BaseActivity;
import com.buffet_user.activity.dashboard.DashboardActivity;
import com.buffet_user.activity.review.BlogHomeActivity;
import com.buffet_user.global.AppUtils;
import com.buffet_user.pojo.VenueItems;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;

public class PickUpLocationActivity extends BaseActivity implements OnMapReadyCallback, LocationListener {


    private static final int GPS_PERMISSION = 123;
    private static final int VENUE_SEARCH = 134;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1000;
    protected static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    boolean GenericMode = true; //Other Venue Mode
    LatLng CenterLocation;
    String mAddressOutput;
    protected String mAreaOutput;
    protected String mCityOutput;
    protected String mStateOutput;
    String PlaceName = null;
    VenueItems SelectedMarker;
    boolean MarkerClick = false;
    FloatingActionButton Next;
    GoogleMap mMap;
    TextView Address;
    GoogleApiClient mGoogleApiClient;
    AddressResultReceiver mResultReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(sharedPreferences.getString("user_gender", "Male").equals("Female") ? R.style.AppThemeFemale : R.style.AppTheme);
        setContentView(R.layout.activity_pick_up_location);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        initialize();
        set_user_color();
        setUpMap();
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PickUpLocationActivity.this, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    private void set_user_color() {
        Next.setBackgroundTintList(ColorStateList.valueOf(sharedPreferences.getString("user_gender", "Male").equals("Female") ? getResources().getColor(R.color.colorPrimaryDarkFemale) : getResources().getColor(R.color.colorPrimaryDark)));
    }


    void initialize() {
        Address = (TextView) findViewById(R.id.Address);
        Next = (FloatingActionButton) findViewById(R.id.Next);

        findViewById(R.id.Locality).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GoogleLocationSearch();
            }
        });


        mResultReceiver = new AddressResultReceiver(new Handler());


    }


    void setUpMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        View mapView = mapFragment.getView();

        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 90, 200);
        }

        try {
            MapsInitializer.initialize(this.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (checkPlayServices()) {
            if (!AppUtils.isLocationEnabled(getBaseContext())) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(PickUpLocationActivity.this);
                dialog.setMessage("Location not enabled!");
                dialog.setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(myIntent, GPS_PERMISSION);
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        paramDialogInterface.dismiss();
                        finish();
                    }
                });
                dialog.show();
        }
            buildGoogleApiClient();
        } else {
            Toast.makeText(getBaseContext(), "Location not supported in this device", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayAddressOutput() {
        if (GenericMode && (SelectedMarker == null)) {
            try {
                if (mAddressOutput != null && PlaceName == null) {
                    Address.setText(mAddressOutput.trim().replace("\n", ", "));
                } else {
                    Address.setText(PlaceName.trim().replace("\n", ", "));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        try {
            mGoogleApiClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mMap != null && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            mMap.setMyLocationEnabled(true);
        }
        GenericMode = true;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.style_json));
        displayAddressOutput();
        if (SelectedMarker != null) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(SelectedMarker.getLatitude(), SelectedMarker.getLongitude()))
                    .zoom(15f).tilt(50).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }

        buildGoogleApiClient();
        mGoogleApiClient.connect();

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                CenterLocation = cameraPosition.target;

                try {
                    Location mLocation = new Location("");
                    mLocation.setLatitude(CenterLocation.latitude);
                    mLocation.setLongitude(CenterLocation.longitude);

                    startIntentService(mLocation);
                    displayAddressOutput();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                if (i == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                    findViewById(R.id.Locality).setVisibility(View.INVISIBLE);
                    Next.setVisibility(View.INVISIBLE);
                }
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                findViewById(R.id.Locality).setVisibility(View.VISIBLE);
                Next.setVisibility(View.VISIBLE);
            }
        });

        mMap.setOnCameraMoveCanceledListener(new GoogleMap.OnCameraMoveCanceledListener() {
            @Override
            public void onCameraMoveCanceled() {
                findViewById(R.id.Locality).setVisibility(View.VISIBLE);
                Next.setVisibility(View.VISIBLE);
            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(@Nullable Bundle bundle) {
                            if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                    || ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                                if (mLastLocation != null) {
                                    changeMap(mLastLocation);
                                } else {
                                    try {
                                        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, PickUpLocationActivity.this);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                try {
                                    LocationRequest mLocationRequest = new LocationRequest();
                                    mLocationRequest.setInterval(1000);
                                    mLocationRequest.setFastestInterval(5000);
                                    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, PickUpLocationActivity.this);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            mGoogleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        }
                    })
                    .addApi(LocationServices.API)
                    .build();
        }
    }
    public void onPlaceSelected(Place place) {
        String venue = place.getAddress().toString().replace("/n", ", ");
        Address.setText(venue);
    }

    private void changeMap(Location location) {
        if (mMap != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }

            mMap.getUiSettings().setZoomControlsEnabled(false);
            mMap.getUiSettings().setCompassEnabled(false);

            LatLng latLong = new LatLng(location.getLatitude(), location.getLongitude());

            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLong).zoom(15f).tilt(50).build();

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    return true;
                }
            });

            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            if (GenericMode)
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            Address.setText("Lat : " + location.getLatitude() + ", Long : " + location.getLongitude());
            startIntentService(location);
        } else {
            Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            if (location != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                changeMap(location);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void startIntentService(Location mLocation) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(AppUtils.LocationConstants.RECEIVER, mResultReceiver);
        intent.putExtra(AppUtils.LocationConstants.LOCATION_DATA_EXTRA, mLocation);
        startService(intent);
    }

    private class AddressResultReceiver extends ResultReceiver {
        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         * Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(AppUtils.LocationConstants.RESULT_DATA_KEY);
            mAreaOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_AREA);
            mCityOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_CITY);
            mStateOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_STREET);

            displayAddressOutput();
        }
    }

    void GoogleLocationSearch() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(PickUpLocationActivity.this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            GoogleApiAvailability.getInstance().getErrorDialog(PickUpLocationActivity.this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GPS_PERMISSION && resultCode == RESULT_OK) {
            buildGoogleApiClient();

            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        }

        if (requestCode == REQUEST_CODE_AUTOCOMPLETE && resultCode == RESULT_OK) {
            Place place = PlaceAutocomplete.getPlace(getBaseContext(), data);

            this.onPlaceSelected(place);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }

            CameraPosition cameraPosition = new CameraPosition.Builder().target(place.getLatLng()).zoom(15f).tilt(50).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
