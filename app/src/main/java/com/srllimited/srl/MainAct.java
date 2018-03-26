package com.srllimited.srl;

/**
 * Created by RuchiTiwari on 1/16/2017.
 */

import com.srllimited.srl.constants.ClearableAutoTextView;
import com.srllimited.srl.constants.SelectionListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainAct extends Activity implements SelectionListener
{

	//	private com.codefyne.mysrl.constants.ClearableAutoTextView mAutoText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//Customized auto complete URL. For demo we have used Wiki suggestions
		//		mAutoText = (com.codefyne.mysrl.constants.ClearableAutoTextView) findViewById(R.id.auto_text);
		//		mAutoText.setSelectionListener(this);
		//		mAutoText.setParser(new ClearableAutoTextView.AutoCompleteResponseParser()
		//		{
		//
		//			@Override
		//			public ArrayList<ClearableAutoTextView.DisplayStringInterface> parseAutoCompleteResponse(
		//					String response)
		//			{
		//				ArrayList<ClearableAutoTextView.DisplayStringInterface> models = new ArrayList<ClearableAutoTextView.DisplayStringInterface>();
		//				try
		//				{
		//					JSONArray jsonArray = new JSONArray(response);
		//					JSONArray array = jsonArray.optJSONArray(1);
		//					if (array != null)
		//					{
		//						for (int i = 0; i < array.length(); i++)
		//						{
		//							models.add(new WikiModel(array.getString(i)));
		//						}
		//					}
		//				}
		//				catch (JSONException e)
		//				{
		//					e.printStackTrace();
		//				}
		//
		//				return models;
		//			}
		//		});

		//For Google places suggestions, we just need to provide Google API key in XML
		((com.srllimited.srl.constants.ClearableAutoTextView) findViewById(R.id.auto_text_2))
				.setSelectionListener(this);
	}

	@Override
	public void onItemSelection(ClearableAutoTextView.DisplayStringInterface selectedItem)
	{
		WikiModel model = null;
		if (selectedItem instanceof WikiModel)
		{
			model = (WikiModel) selectedItem; //gets the selected item
		}
		Toast.makeText(MainAct.this, "Selected Item: " + selectedItem.getDisplayString(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onReceiveLocationInformation(double lat, double lng)
	{
		Toast.makeText(MainAct.this, "lat lng: " + lat + " " + lng, Toast.LENGTH_SHORT).show();
	}

	public class WikiModel implements ClearableAutoTextView.DisplayStringInterface
	{
		public String item;

		public WikiModel(String item)
		{
			this.item = item;
		}

		@Override
		public String getDisplayString()
		{
			return item;
		}
	}

}