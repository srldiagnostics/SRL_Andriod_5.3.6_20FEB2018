package com.srllimited.srl.location.findplaces;

import android.content.Context;

import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.PlaceDetailsLoadingFailure;
import com.seatgeek.placesautocomplete.PlacesApi;
import com.seatgeek.placesautocomplete.PlacesApiBuilder;
import com.seatgeek.placesautocomplete.async.BackgroundExecutorService;
import com.seatgeek.placesautocomplete.async.BackgroundJob;
import com.seatgeek.placesautocomplete.history.AutocompleteHistoryManager;
import com.seatgeek.placesautocomplete.history.DefaultAutocompleteHistoryManager;
import com.seatgeek.placesautocomplete.model.AutocompleteResultType;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;
import com.seatgeek.placesautocomplete.network.PlacesHttpClientResolver;
import com.srllimited.srl.R;
import com.srllimited.srl.adapters.PlacesAutocompleteAdapter;

/**
 * Created by RuchiTiwari on 12/25/2016.
 */

public class FindPlaces
{
	private static final String DEFAULT_HISTORY_FILE_NAME = "places_history.json";

	private PlacesAutocompleteAdapter mPlacesAutocompleteAdapter;

	public FindPlaces(final Context context)
	{
		PlacesApi api = getPlacesApi(context.getString(R.string.google_maps_api_key),
				context.getString(R.string.places_search_language_code));
		AutocompleteHistoryManager historyManager = DefaultAutocompleteHistoryManager.fromPath(context,
				DEFAULT_HISTORY_FILE_NAME);
		mPlacesAutocompleteAdapter = new PlacesAutocompleteAdapter(context, api, AutocompleteResultType.ADDRESS,
				historyManager);
	}

	public PlacesAutocompleteAdapter getAdapter()
	{
		return mPlacesAutocompleteAdapter;
	}

	public void filter(CharSequence constraint)
	{
		getAdapter().getFilter().filter(constraint);
	}

	private PlacesApi getPlacesApi(String finalApiKey, String languageCode)
	{
		PlacesApi mPlacesApi = new PlacesApiBuilder().setApiClient(PlacesHttpClientResolver.PLACES_HTTP_CLIENT)
				.setGoogleApiKey(finalApiKey).build();

		if (languageCode != null)
		{
			mPlacesApi.setLanguageCode(languageCode);
		}
		return mPlacesApi;
	}

	/**
	 * A helper method for fetching the {@link PlaceDetails} from the PlacesApi
	 *
	 * @param place    the place to get details for
	 * @param callback a callback that will be invoked on the main thread when the place details
	 *                 has been fetched from the Places API
	 */
	public void getDetailsFor(final Place place, final DetailsCallback callback)
	{
		BackgroundExecutorService.INSTANCE.enqueue(new BackgroundJob<PlaceDetails>()
		{
			@Override
			public PlaceDetails executeInBackground() throws Exception
			{
				return getAdapter().getApi().details(place.place_id).result;
			}

			@Override
			public void onSuccess(final PlaceDetails result)
			{
				if (result != null)
				{
					callback.onSuccess(result);
				}
				else
				{
					callback.onFailure(new PlaceDetailsLoadingFailure(place));
				}
			}

			@Override
			public void onFailure(final Throwable error)
			{
				callback.onFailure(new PlaceDetailsLoadingFailure(place, error));
			}
		});
	}
}
