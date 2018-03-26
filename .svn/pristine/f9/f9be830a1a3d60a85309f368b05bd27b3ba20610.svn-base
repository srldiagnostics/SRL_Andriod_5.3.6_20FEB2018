package com.srllimited.srl.adapters;

import com.seatgeek.placesautocomplete.PlacesApi;
import com.seatgeek.placesautocomplete.adapter.AbstractPlacesAutocompleteAdapter;
import com.seatgeek.placesautocomplete.history.AutocompleteHistoryManager;
import com.seatgeek.placesautocomplete.model.AutocompleteResultType;
import com.seatgeek.placesautocomplete.model.DescriptionTerm;
import com.seatgeek.placesautocomplete.model.Place;
import com.srllimited.srl.R;
import com.srllimited.srl.util.Validate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by RuchiTiwari on 12/25/2016.
 */

public class PlacesAutocompleteAdapter extends AbstractPlacesAutocompleteAdapter
{
	public PlacesAutocompleteAdapter(final Context context, final PlacesApi api,
			final AutocompleteResultType resultType, final AutocompleteHistoryManager history)
	{
		super(context, api, resultType, history);
	}

	@Override
	protected View newView(final ViewGroup parent)
	{
		return LayoutInflater.from(parent.getContext()).inflate(R.layout.search_places_autocomplete_item, parent,
				false);
	}

	@Override
	protected void bindView(final View view, final Place item)
	{
		String header = "";
		String details = "";
		if (Validate.notEmpty(item.terms))
		{
			final int size = item.terms.size();
			for (int i = 0; i < size; i++)
			{
				DescriptionTerm term = item.terms.get(i);
				if (Validate.notNull(term) && Validate.notEmpty(term.value.trim()))
				{
					if (!Validate.notEmpty(header))
					{
						header = term.value.trim();
					}
					else
					{
						details += term.value.trim();
						if (i != size - 1)
						{
							details += ", ";
						}
					}
				}
			}
		}
		else
		{
			final String description = item.description;
			int indexOf = description.indexOf(",");
			header = description.substring(0, indexOf).trim();
			if (indexOf > 0)
			{
				details = description.substring(indexOf + 1, description.length()).trim();
			}
		}

		((TextView) view.findViewById(R.id.header)).setText(header);
		((TextView) view.findViewById(R.id.details)).setText(details);
	}

}