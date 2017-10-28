package com.buffet_user.activity.login;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;

import com.buffet_user.R;
import com.buffet_user.global.AppUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ankit on 13/08/17.
 */

public class FetchAddressIntentService extends IntentService {
    private static final String TAG = "FetchAddressIS";

    /**
     * The receiver where results are forwarded from this service.
     */
    protected ResultReceiver mReceiver;
    protected ResultReceiver mVenueReceiver;

    /**
     * This constructor is required, and calls the super IntentService(String)
     * constructor with the name for a worker thread.
     */
    public FetchAddressIntentService() {
        // Use the TAG to name the worker thread.
        super(TAG);
    }

    /**
     * Tries to get the location address using a Geocoder. If successful, sends an address to a
     * result receiver. If unsuccessful, sends an error message instead.
     * Note: We define a {@link ResultReceiver} in * MainActivity to process content
     * sent from this service.
     * <p>
     * This service calls this method from the default worker thread with the intent that started
     * the service. When this method returns, the service automatically stops.
     */
    @Override
    protected void onHandleIntent(Intent intent)
    {
        String errorMessage = "";

        mReceiver = intent.getParcelableExtra(AppUtils.LocationConstants.RECEIVER);
        mVenueReceiver=intent.getParcelableExtra(AppUtils.LocationConstants.RECEIVER1);

        // Check if receiver was properly registered.
        if (mReceiver == null && mVenueReceiver==null) {
            return;
        }
        // Get the location passed to this service through an extra.
        Location location = intent.getParcelableExtra(AppUtils.LocationConstants.LOCATION_DATA_EXTRA);

        // Make sure that the location data was really sent over through an extra. If it wasn't,
        // send an error error message and return.
        if (location == null)
        {
            errorMessage = getString(R.string.no_location_data_provided);
            deliverResultToReceiver(AppUtils.LocationConstants.FAILURE_RESULT, errorMessage, null,mReceiver);
            deliverResultToReceiver(AppUtils.LocationConstants.FAILURE_RESULT, errorMessage, null,mVenueReceiver);
            return;
        }

        // Errors could still arise from using the Geocoder (for example, if there is no
        // connectivity, or if the Geocoder is given illegal location data). Or, the Geocoder may
        // simply not have an address for a location. In all these cases, we communicate with the
        // receiver using a resultCode indicating failure. If an address is found, we use a
        // resultCode indicating success.

        // The Geocoder used in this sample. The Geocoder's responses are localized for the given
        // Locale, which represents a specific geographical or linguistic region. Locales are used
        // to alter the presentation of information such as numbers or dates to suit the conventions
        // in the region they describe.
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        // Address found using the Geocoder.
        List<Address> addresses = null;

        try {
            // Using getFromLocation() returns an array of Addresses for the area immediately
            // surrounding the given latitude and longitude. The results are a best guess and are
            // not guaranteed to be accurate.
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, we get just a single address.
                    1);


        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = getString(R.string.service_not_available);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = getString(R.string.invalid_lat_long_used);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found);

            }
            deliverResultToReceiver(AppUtils.LocationConstants.FAILURE_RESULT, errorMessage, null,mReceiver);
            deliverResultToReceiver(AppUtils.LocationConstants.FAILURE_RESULT, errorMessage, null,mVenueReceiver);
        } else {
            android.location.Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));

            }
            deliverResultToReceiver(AppUtils.LocationConstants.SUCCESS_RESULT,
                    TextUtils.join(System.getProperty("line.separator"), addressFragments), address,mReceiver);
            deliverResultToReceiver(AppUtils.LocationConstants.SUCCESS_RESULT,
                    TextUtils.join(System.getProperty("line.separator"), addressFragments), address,mVenueReceiver);
            deliverResultToReceiver(AppUtils.LocationConstants.FAILURE_RESULT, errorMessage, null,mVenueReceiver);
            deliverResultToReceiver(AppUtils.LocationConstants.FAILURE_RESULT, errorMessage, null,mReceiver);
        }
    }

    private void deliverResultToReceiver(int resultCode, String message, Address address,ResultReceiver resultReceiver)
    {
        try
        {
            if(address==null || resultReceiver == null)
                return;

            Bundle bundle = new Bundle();
            bundle.putString(AppUtils.LocationConstants.RESULT_DATA_KEY, message);
            bundle.putString(AppUtils.LocationConstants.LOCATION_DATA_AREA,address.getSubLocality());
            bundle.putString(AppUtils.LocationConstants.LOCATION_DATA_CITY, address.getLocality());
            bundle.putString(AppUtils.LocationConstants.LOCATION_DATA_STREET, address.getAddressLine(0));
            resultReceiver.send(resultCode, bundle);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

