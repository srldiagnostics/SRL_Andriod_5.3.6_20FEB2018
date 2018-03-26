package com.srllimited.srl;

import com.srllimited.srl.constants.Constants;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MobikwikActivity extends Activity
{
	public static Activity mobi;
	Context context;
	TextView done, mobikwik;
	//private Button button;
	private WebView webView;

	public void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_web_view);
		context = MobikwikActivity.this;

		mobi = this;
		webView = (WebView) findViewById(R.id.webView1);
		done = (TextView) findViewById(R.id.done);
		mobikwik = (TextView) findViewById(R.id.mobikwik);
		mobikwik.setVisibility(View.VISIBLE);

		done.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				finish();
			}
		});
		done.setVisibility(View.GONE);
		startWebView(Constants.mobikwikUrl);

	}

	private void startWebView(String url)
	{

		//Create new webview Client to show progress dialog
		//When opening a url or click on link

		webView.setWebViewClient(new WebViewClient()
		{
			//			ProgressDialog progressDialog;

			//If you will not use this method url links are opeen in new brower not in webview
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				view.loadUrl(url);
				return true;
			}

			//Show loader on url load
			public void onLoadResource(WebView view, String url)
			{
				//				if (progressDialog == null)
				//				{
				//					// in standard case YourActivity.this
				//					progressDialog = new ProgressDialog(MobikwikActivity.this);
				//					progressDialog.setMessage("Loading...");
				//					progressDialog.show();
				//				}
			}

			public void onPageFinished(WebView view, String url)
			{
				//				try
				//				{
				//					if (progressDialog.isShowing())
				//					{
				//						progressDialog.dismiss();
				//						progressDialog = null;
				//					}
				//				}
				//				catch (Exception exception)
				//				{
				//					exception.printStackTrace();
				//				}
			}

		});

		// Javascript inabled on webview
		webView.getSettings().setJavaScriptEnabled(true);

		// Other webview options
		/*
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webView.setScrollbarFadingEnabled(false);
		webView.getSettings().setBuiltInZoomControls(true);
		*/

		/*
		 String summary = "<html><body>You scored <b>192</b> points.</body></html>";
		 webview.loadData(summary, "text/html", null);
		 */

		//Load url in webview
		webView.loadUrl(url);

	}

	// Open previous opened link from history on webview when back button pressed

	@Override
	// Detect when the back button is pressed
	public void onBackPressed()
	{
		if (webView.canGoBack())
		{
			webView.goBack();
		}
		else
		{
			// Let the system handle the back button
			super.onBackPressed();
		}
	}

	private void showPdf(String url)
	{

		final ProgressDialog mProgressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
		mProgressDialog.setMessage("Loading Pdf...");
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setCancelable(false);
		mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		mProgressDialog.show();

		//		final ProgressDialog pDialog = new ProgressDialog(context);
		//		pDialog.setMessage("Loading Pdf...");
		//		pDialog.setIndeterminate(false);
		//		pDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
		//		                  new DialogInterface.OnClickListener()
		//		                  {
		//			                  @Override
		//			                  public void onClick(DialogInterface dialog, int which)
		//			                  {
		//				                  dialog.dismiss();
		//			                  }
		//		                  });
		//		pDialog.setCancelable(false);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient()
		{
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon)
			{
				super.onPageStarted(view, url, favicon);
				mProgressDialog.show();
			}

			@Override
			public void onPageFinished(WebView view, String url)
			{
				super.onPageFinished(view, url);
				if (mProgressDialog != null)
				{
					mProgressDialog.dismiss();
				}
			}
		});
		String pdf = url;
		webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);

	}

}
