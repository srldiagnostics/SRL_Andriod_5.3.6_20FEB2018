package com.srllimited.srl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by sri on 12/20/2016.
 */

public class CompleteMap extends FragmentActivity implements OnMapReadyCallback
{

	GoogleMap map;

	ArrayList<LatLng> markerPoints;

	Location location;

	LatLng latLng;

	Marker currLocationMarker;

	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.source_destination_map_activity);

		context = CompleteMap.this;
		// Initializing
		markerPoints = new ArrayList<LatLng>();

		// Getting reference to SupportMapFragment of the activity_main
		MapFragment fm = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

		fm.getMapAsync(this);

		if (map != null)
		{

			// Enable MyLocation Button in the Map
			try
			{
				map.setMyLocationEnabled(true);
			}
			catch (Exception e)
			{
			}
			// Setting onclick event listener for the map
			map.setOnMapClickListener(new GoogleMap.OnMapClickListener()
			{

				@Override
				public void onMapClick(LatLng point)
				{

					// Already two locations
					if (markerPoints.size() > 1)
					{
						markerPoints.clear();
						map.clear();
					}

					// Adding new item to the ArrayList
					markerPoints.add(point);

					// Creating MarkerOptions
					MarkerOptions options = new MarkerOptions();

					// Setting the position of the marker
					options.position(point);

					/**
					 * For the start location, the color of marker is GREEN and
					 * for the end location, the color of marker is RED.
					 */
					if (markerPoints.size() == 1)
					{
						options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
					}
					else if (markerPoints.size() == 2)
					{
						options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
					}

					// Add new marker to the Google Map Android API V2
					map.addMarker(options);

					// Checks, whether start and end locations are captured
					if (markerPoints.size() >= 2)
					{
						LatLng origin = markerPoints.get(0);
						LatLng dest = markerPoints.get(1);

						// Getting URL to the Google Directions API
						String url = getDirectionsUrl(origin, dest);
						DownloadTask downloadTask = new DownloadTask();
						// Start downloading json data from Google Directions API
						downloadTask.execute(url);
					}

				}
			});
		}
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		GPSTracker gpsTracker = new GPSTracker(CompleteMap.this);
		Boolean gpsEnabled = gpsTracker.canGetLocation();
		if (gpsTracker.canGetLocation())
		{
			location = gpsTracker.getLocation();
			Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
			List<Address> addresses = null;
			try
			{
				if (location != null)
				{
					addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			Address address = addresses != null ? addresses.get(0) : null;
			if (address != null && location != null)
			{
				Toast.makeText(getApplicationContext(), address.getLocality() + " " + address.getSubLocality(),
						Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(), location.getLatitude() + " " + location.getLongitude(),
						Toast.LENGTH_LONG).show();
				String cityName = address.getLocality();

			}

			if (location != null)
			{
				latLng = new LatLng(location.getLatitude(), location.getLongitude());

				map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

				// zoomLevel = 16.0; //This goes up to 21
				//mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16.0));

				map.animateCamera(CameraUpdateFactory.zoomTo(12));
				CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(14).build();
				map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

				MarkerOptions markerOptions = new MarkerOptions();
				markerOptions.position(latLng);
				markerOptions.title("Current Position");
				markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
				currLocationMarker = map.addMarker(markerOptions);
			}
		}
		else
		{
			showSettingsAlert();
		}
	}

	private String getDirectionsUrl(LatLng origin, LatLng dest)
	{

		// Origin of route
		String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

		// Destination of route
		String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

		// Sensor enabled
		String sensor = "sensor=false";

		// Building the parameters to the web service
		String parameters = str_origin + "&" + str_dest + "&" + sensor;

		// Output format
		String output = "json";

		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

		return url;
	}

	/**
	 * A method to download json data from url
	 */
	private String downloadUrl(String strUrl) throws IOException
	{
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try
		{
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null)
			{
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		}
		catch (Exception e)
		{
			//Log.d("Exception while downloading url", e.toString());
		}
		finally
		{
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	@Override
	public void onMapReady(GoogleMap googleMap)
	{

		map = googleMap;
	}

	public void showSettingsAlert()
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(CompleteMap.this);
		alertDialog.setTitle("SETTINGS");
		alertDialog.setMessage("Enable GPS?");
		alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				CompleteMap.this.startActivity(intent);
			}
		});
		alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
				finish();
			}
		});
		alertDialog.show();

	}

	// Fetches data from url passed
	private class DownloadTask extends AsyncTask<String, Void, String>
	{

		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url)
		{

			// For storing data from web service
			String data = "";

			try
			{
				// Fetching the data from web service
				data = downloadUrl(url[0]);
			}
			catch (Exception e)
			{

			}
			return data;
		}

		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(String result)
		{
			super.onPostExecute(result);

			ParserTask parserTask = new ParserTask();

			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);

		}
	}

	/**
	 * A class to parse the Google Places in JSON format
	 */
	private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>>
	{

		// Parsing the data in non-ui thread
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(String... jsonData)
		{

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try
			{
				jObject = new JSONObject(jsonData[0]);
				DirectionsJSONParser parser = new DirectionsJSONParser();

				// Starts parsing data
				routes = parser.parse(jObject);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return routes;
		}

		// Executes in UI thread, after the parsing process
		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result)
		{
			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;
			MarkerOptions markerOptions = new MarkerOptions();

			// Traversing through all the routes
			for (int i = 0; i < result.size(); i++)
			{
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();

				// Fetching i-th route
				List<HashMap<String, String>> path = result.get(i);

				// Fetching all the points in i-th route
				for (int j = 0; j < path.size(); j++)
				{
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				// Adding all the points in the route to LineOptions
				lineOptions.addAll(points);
				lineOptions.width(5);
				lineOptions.color(Color.RED);

			}

			// Drawing polyline in the Google Map for the i-th route
			if (lineOptions != null)
			{
				map.addPolyline(lineOptions);
			}
		}
	}

}
