package com.srllimited.srl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class TrackCollectExportActivity extends AppCompatActivity
{
	public static Activity trackview;
	Toolbar toolbar;
	String URL;

	Context context;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		context = TrackCollectExportActivity.this;
		trackview = this;
		// super.addContentView(R.layout.activity_track_view);
		setContentView(R.layout.activity_track_view);
		/* toolbar =(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
		if (getIntent().hasExtra("HOMECOLLECT_LINK"))
		{
			URL = getIntent().getStringExtra("HOMECOLLECT_LINK");
		}

		if (URL != null)
		{
			webView = (WebView) findViewById(R.id.webView1);
			WebSettings settings = webView.getSettings();
			settings.setJavaScriptEnabled(true);
			settings.setBuiltInZoomControls(true);
			webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
			webView.setWebViewClient(new WebViewClient());
			webView.loadUrl(URL);
			WebSettings webSettings = webView.getSettings();
			webSettings.setJavaScriptEnabled(true);
		}
		else
		{

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack())
		{
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	public class WebViewClient extends android.webkit.WebViewClient
	{
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon)
		{

			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url)
		{

			// TODO Auto-generated method stub
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url)
		{

			// TODO Auto-generated method stub

			super.onPageFinished(view, url);
		}

	}

	/* @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle action bar item clicks here. The action bar will
	    // automatically handle clicks on the Home/Up button, so long
	    // as you specify a parent activity in AndroidManifest.xml.
	    int id = item.getItemId();
	
	    //noinspection SimplifiableIfStatement
	
	     if (item.getItemId() == android.R.id.home) {
	        finish();
	    }
	    return super.onOptionsItemSelected(item);
	}*/
}
