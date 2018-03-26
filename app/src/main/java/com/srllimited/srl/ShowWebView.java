package com.srllimited.srl;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.util.Log;
import com.srllimited.srl.util.NetworkConnectivity;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowWebView extends AppCompatActivity
{
	private static final String TAG = "ShowWebView";
	public static Activity sowweb;
	static File destinationUri1;
	Context context;
	TextView done;
	ProgressDialog progressDialog = null;
	ImageView download;
	String redirectUrl = "";
	String url = "";
	String first = "";
	//private Button button;
	private WebView webView;
	private DownloadManager dm1;
	private CustomWebViewClient mWebViewClient;
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			switch (request.getReferralCode())
			{
				case GET_PDF:
				{
					url = "";
					first = "";

					try
					{
						first = Html.fromHtml(serverResponseData.getFullData().getString("data")) + "";
					}
					catch (Exception e)
					{

					}

					if (Validate.notEmpty(first))
					{

						//  new DownAsyncTask().execute(first);

						loadUrlWithWebView(first);
					}
					else
					{
						Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
					}
				}
					break;
			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{

		}
	};
	private BroadcastReceiver receiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			String action = intent.getAction();
			if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action))
			{
				android.util.Log.d(TAG, "onReceive() returned: " + action);
				//   view()
				progressDialog.dismiss();
				view(destinationUri1, ShowWebView.this);
				// TODO: 2016-10-12
			}
			else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(action))
			{
				android.util.Log.d(TAG, "onReceive() returned: " + action);
				// TODO: 2016-10-12
			}
		}
	};

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_web_view);
		context = ShowWebView.this;

		sowweb = this;
		download = (ImageView) findViewById(R.id.download);
		webView = (WebView) findViewById(R.id.webView1);
		done = (TextView) findViewById(R.id.done);

		dm1 = (DownloadManager) this.getSystemService(DOWNLOAD_SERVICE);

		registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

		done.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{

				try
				{
					if (progressDialog != null)
					{
						progressDialog.dismiss();
					}
					if (webView != null)
					{
						webView.stopLoading();
					}
				}
				catch (Exception e)
				{

				}
				finish();
			}
		});

		download.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				//                new DownloadFile().execute(redirectUrl, "SRLDiagnostics"+ System.currentTimeMillis()+"");
				//   new DownAsyncTask().execute(redirectUrl);
				// downloadPDF(redirectUrl);

				//////////////////
				if (NetworkConnectivity.isOnline(context))
				{
					progressDialog = new ProgressDialog(ShowWebView.this);
					progressDialog.setTitle("Preparing for Download, this may take a while");
					progressDialog.setCanceledOnTouchOutside(true);
					progressDialog.show();
					new Thread(new Runnable()
					{
						@Override
						public void run()
						{

							//   progressDialog = ProgressDialog.show(ShowWebView.this, "", "Preparing for Download, this may take a while");
							//if(checkWtirePermissionGranted()) {
							downloadFileInTask1(v.getContext(), redirectUrl);
							//   downloadPDF11(v);
							// }
						}
					}).start();
				}
				else
				{
					showErrorPopup(context, "No Network", "Please check the internet connection");
				}
				// new DownAsyncTask(v.getContext(),redirectUrl).execute();

			}
		});
		if (NetworkConnectivity.isOnline(context))
		{
			showProgressDialogstart();
		}
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		//  new CustomWebViewClient(context, webView).showProgressDialog();
		webView.setWebViewClient(new CustomWebViewClient(context, webView));

		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		String cityid = SharedPrefsUtil.getStringPreference(context, "selectedcityid");
		if (cityid != null)
		{
			sendRequest(ApiRequestHelper.getPdfReports(context, cityid, Constants.storedAccesstion,
					Util.getStoredUsername(context), "9704683480", "", Constants.devicetobepassed, "1", "ANDROID", "1",
					"1"));
		}
		else
		{
			Toast.makeText(ShowWebView.this, "please select city first!", Toast.LENGTH_SHORT).show();
			dismissProgressDialogstart();
			return;
		}
	}

	@Override

	public void onBackPressed()
	{
		try
		{
			if (progressDialog != null)
			{
				progressDialog.dismiss();
			}
			if (webView != null)
			{
				webView.stopLoading();
			}
		}
		catch (Exception e)
		{

		}

		super.onBackPressed();

	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	public void loadUrlWithWebView(String url)
	{
		mWebViewClient = new CustomWebViewClient(context, webView);
		webView.loadUrl(url);
	}

	private void showProgressDialogstart()

	{
		this.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				dismissProgressDialogstart();
				progressDialog = ProgressDialog.show(ShowWebView.this, "", "Loading...");
			}
		});
	}

	private void dismissProgressDialogstart()
	{
		this.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				if (progressDialog != null && progressDialog.isShowing())
				{
					progressDialog.dismiss();
					progressDialog = null;
				}
			}
		});
	}

	private void downloadPDF(String url)
	{

		try
		{

			String servicestring = Context.DOWNLOAD_SERVICE;
			DownloadManager downloadmanager;
			downloadmanager = (DownloadManager) getSystemService(servicestring);
			Uri uri = Uri.parse(url.trim());
			DownloadManager.Request request = new DownloadManager.Request(uri);
			Long reference = downloadmanager.enqueue(request);
			request.setDestinationInExternalFilesDir(getApplicationContext(), null, "sample.pdf");
			request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

			new Handler().postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					Toast.makeText(ShowWebView.this, "Downloading completed!", Toast.LENGTH_SHORT).show();
				}
			}, 4000);
		}
		catch (Exception e)
		{

		}
	}

	private boolean checkWtirePermissionGranted()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
		{
			if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
			{
				Log.v(TAG, "Permission is granted");
				//File write logic here
				return true;
			}
			else
			{
				Log.v(TAG, "Permission is revoked");
				ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
				return false;
			}
		}
		else
		{ //permission is automatically granted on sdk<23 upon installation
			Log.v(TAG, "Permission is granted");
			return true;
		}

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
		{
			Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
			//resume tasks needing this permission
			downloadFileInTask1(context, redirectUrl);
		}
	}

	private void downloadFileInTask1(Context v, String url)
	{
		// url ="file:///storage/emulated/0/Android/data/com.biotech.biotechvision/files/test_invoice1.pdf";
		if (TextUtils.isEmpty(url))
		{
			throw new IllegalArgumentException("url cannot be empty or null");
		}

		//url = Environment.getExternalStorageDirectory().toString() + "/SRL/" + "Report/";

		// when redirecting from hashed url and found headerField "Content-Disposition"
		String resolvedFile = getPDFname(url, "unknown_file");//resolveFile(url, "unknown_file");
		//  url = url+resolvedFile;
		DownloadManager.Request request1 = new DownloadManager.Request(Uri.parse(url));
		request1.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		if (isExternalStorageWritable())
		{
			File file = new File(v.getExternalFilesDir(null), resolvedFile);
			Uri destinationUri = Uri.fromFile(file);
			destinationUri1 = file;
			request1.setMimeType("application/pdf");
			request1.setDestinationUri(destinationUri);
			dm1.enqueue(request1);
		}
	}

	private String getPDFname(String url, String defaultFileName)
	{
		String filename = defaultFileName;
		String[] strArr = url.split("//")[1].toString().split("/");
		for (int i = 0; i < strArr.length; i++)
		{
			if (strArr[i].endsWith(".pdf"))
			{
				filename = strArr[i];
			}
		}
		return filename;
	}

	private String resolveFile(String url, String defaultFileName)
	{
		String filename = defaultFileName;

		HttpURLConnection con = null;
		try
		{
			con = (HttpURLConnection) new URL(url).openConnection();
			con.setInstanceFollowRedirects(true);
			con.connect();

			String contentDisposition = con.getHeaderField("Content-Disposition");
			if (!TextUtils.isEmpty(contentDisposition))
			{
				String[] splittedCD = contentDisposition.split(";");
				for (int i = 0; i < splittedCD.length; i++)
				{
					if (splittedCD[i].trim().startsWith("filename="))
					{
						filename = splittedCD[i].replaceFirst("filename=", "").trim();
						break;
					}
				}
			}

			con.disconnect();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return filename;
	}

	//...
	public boolean isExternalStorageWritable()
	{
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state);
	}

	public boolean isExternalStorageReadable()
	{
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
	}

	//function to view pdf file
	public void view(File filePath, Context context)
	{
		Uri path = Uri.fromFile(filePath);
		Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
		pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri apkURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider",
				filePath);
		pdfIntent.setDataAndType(apkURI, "application/pdf");
		pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

		// pdfIntent.setDataAndType(path, "application/pdf");
		////

		/*  File file = ...;
		Intent install = new Intent(Intent.ACTION_VIEW);
		install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		// Old Approach
		install.setDataAndType(Uri.fromFile(file), mimeType);
		// End Old approach
		// New Approach
		Uri apkURI = FileProvider.getUriForFile(
		        context,
		        context.getApplicationContext()
		                .getPackageName() + ".provider", file);
		install.setDataAndType(apkURI, mimeType);
		install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		// End New Approach
		context.startActivity(install);*/

		///

		try
		{
			context.startActivity(pdfIntent);
		}
		catch (ActivityNotFoundException e)
		{
			e.printStackTrace();
			Toast.makeText(context, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		// unregisterReceiver(receiver);
	}

	private void showErrorPopup(Context context, String title, String message)
	{
		try
		{
			AlertDialog mAlertDialog;

			AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
			builder.setTitle(title).setMessage(message).setCancelable(false).setPositiveButton("OK",
					new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int id)
						{
							dialog.dismiss();

						}
					});
			mAlertDialog = builder.create();
			mAlertDialog.show();
		}
		catch (Exception e)
		{
		}
	}

	public void downloadPDF11(View v)
	{
		new DownloadFile11().execute("https://www.mysrl.in/srl_web_reports/Reports/0002QG056618_765179l2.pdf");

	}

	private class CustomWebViewClient extends WebViewClient
	{
		private static final String TAG = "CustomWebViewClient";

		private static final String PDF_EXTENSION = ".pdf";

		private static final String PDF_VIEWER_URL = "http://docs.google.com/gview?embedded=true&url=";

		private Context mContext;

		private WebView mWebView;

		private ProgressDialog mProgressDialog;

		private boolean isLoadingPdfUrl;

		public CustomWebViewClient(Context context, WebView webView)
		{
			mContext = context;
			mWebView = webView;
			mWebView.setWebViewClient(this);

		}

		public void loadUrl(String url)
		{
			mWebView.stopLoading();

			isLoadingPdfUrl = isPdfUrl(url);
			if (isLoadingPdfUrl)
			{
				mWebView.clearHistory();
			}

			showProgressDialog();
			// changeProgressDialogMessage("Loading..1");
			mWebView.loadUrl(url);

		}

		@SuppressWarnings("deprecation")
		@Override
		public boolean shouldOverrideUrlLoading(WebView webView, String url)
		{
			dismissProgressDialogstart();
			return handleUrl(webView, url);

		}

		@SuppressWarnings("deprecation")
		@Override
		public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl)
		{
			dismissProgressDialogstart();
			handleError(webView, errorCode, description.toString(), failingUrl);
		}

		@TargetApi(Build.VERSION_CODES.N)
		@Override
		public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request)
		{
			final Uri uri = request.getUrl();

			return handleUrl(webView, uri.toString());
		}

		@TargetApi(Build.VERSION_CODES.N)
		@Override
		public void onReceivedError(final WebView webView, final WebResourceRequest request,
				final WebResourceError error)
		{
			final Uri uri = request.getUrl();
			handleError(webView, error.getErrorCode(), error.getDescription().toString(), uri.toString());
		}

		@Override
		public void onPageFinished(final WebView view, final String url)
		{
			if (Validate.notEmpty(redirectUrl))
				download.setVisibility(View.VISIBLE);
			dismissProgressDialogstart();
			dismissProgressDialog();
		}

		private boolean handleUrl(final WebView webView, final String url)
		{

			if (!isLoadingPdfUrl && isPdfUrl(url))
			{
				redirectUrl = url;
				webView.stopLoading();

				final String pdfUrl = PDF_VIEWER_URL + url;

				new Handler().postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						loadUrl(pdfUrl);

					}
				}, 300);

				return true;
			}

			// Returning false means that you are going to load this url in the webView itself
			return false;
		}

		private void handleError(final WebView webView, final int errorCode, final String description,
				final String failingUrl)
		{

			Log.e(TAG, "Error: " + description);
		}

		public void changeProgressDialogMessage(final String message)
		{

			if (mProgressDialog != null)
			{
				mProgressDialog.setMessage(message);

			}

		}

		private void showProgressDialog()

		{
			dismissProgressDialog();
			mProgressDialog = ProgressDialog.show(mContext, "", "Loading...");
		}

		private void dismissProgressDialog()

		{
			if (mProgressDialog != null && mProgressDialog.isShowing())
			{
				mProgressDialog.dismiss();
				mProgressDialog = null;
			}
		}

		private boolean isPdfUrl(String url)
		{
			try
			{
				url = url.trim();
				String pdfWord = url.substring(url.lastIndexOf(PDF_EXTENSION));
				return pdfWord.equalsIgnoreCase(PDF_EXTENSION);
			}
			catch (Exception e)
			{

			}
			return false;
		}
	}

	private class DownloadFile extends AsyncTask<String, Void, Void>
	{

		@Override
		protected Void doInBackground(String... strings)
		{
			String fileUrl = strings[0]; // -> http://maven.apache.org/maven-1.x/maven.pdf
			String fileName = strings[1]; // -> maven.pdf
			String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
			File folder = new File(extStorageDirectory, "SRLDiagnostics");
			folder.mkdir();

			File pdfFile = new File(folder, fileName);

			try
			{
				pdfFile.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			FileDownloader.downloadFile(fileUrl, pdfFile);
			return null;
		}
	}

	public class DownAsyncTask extends AsyncTask<String, Integer, Void>
	{

		private static final int MEGABYTE = 1024 * 1024;
		ProgressDialog pd;
		boolean responseflag = false;
		int i = 0;

		String fileName = "";

		Context mContext;

		String mURL;

		public DownAsyncTask(Context context, String url)
		{
			mContext = context;
			mURL = url;
		}

		@Override
		protected void onPreExecute()
		{
			// TODO Auto-generated method stub
			pd = new ProgressDialog(context);
			pd.setMessage("Preparing for Download, this may take a while.");

			pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pd.setIndeterminate(false);
			pd.setProgress(0);
			pd.setMax(100);
			pd.setCancelable(false);
			pd.show();
		}

		@Override
		protected Void doInBackground(String... params)
		{
			// TODO Auto-generated method stub
			//  if(checkWtirePermissionGranted()) {
			downloadFileInTask1(mContext, mURL);
			//  }
			////////////////////////////////////////////////////////////////////////////////////
			/* String fileUrl =  params[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
			String fileName ="sample.pdf";  // -> maven.pdf
			String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
			File folder = new File(extStorageDirectory, "SRLDiagnostics");
			folder.mkdir();

			File pdfFile = new File(folder, fileName);

			try{
			    pdfFile.createNewFile();
			}catch (IOException e){
			    e.printStackTrace();
			}

			try {

			    URL url = new URL(fileUrl);
			    HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			    //urlConnection.setRequestMethod("GET");
			    //urlConnection.setDoOutput(true);
			    urlConnection.connect();

			    InputStream inputStream = urlConnection.getInputStream();
			    FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
			    int totalSize = urlConnection.getContentLength();

			    byte[] buffer = new byte[MEGABYTE];
			    int bufferLength = 0;
			    while((bufferLength = inputStream.read(buffer))>0 ){
			        fileOutputStream.write(buffer, 0, bufferLength);
			    }
			    fileOutputStream.close();
			} catch (FileNotFoundException e) {
			    e.printStackTrace();
			    responseflag =false;
			} catch (MalformedURLException e) {
			    e.printStackTrace();
			    responseflag =false;
			} catch (IOException e) {
			    e.printStackTrace();
			    responseflag =false;
			}

			responseflag = true;
			*/
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			// TODO Auto-generated method stub
			try
			{
				pd.dismiss();
				/*
				if (responseflag)
				{
				    File pdfFile = new File(Environment.getExternalStorageDirectory()
				            + "/SRLDiagnostics/" + fileName);
				    view(pdfFile, context);
				}
				else
				{
				    Toast.makeText(ShowWebView.this, "Downloading completed!", Toast.LENGTH_SHORT).show();
				  *//*  Utils.openAlertBox(context,
						        "Something went wrong, Please check internet connection and try again.");*//*
																											}*/
			}
			catch (Exception e)
			{

			}
		}

		@Override
		protected void onProgressUpdate(Integer... values)
		{
			// TODO Auto-generated method stub
			System.out.println("totalSize " + i);
			i++;
			pd.setProgress(values[0]);

		}
	}

	private class DownloadFile11 extends AsyncTask<String, Void, Void>
	{
		private ProgressDialog pDialog;

		private void showpDialog()
		{
			if (!pDialog.isShowing())
				pDialog.show();
		}

		private void hidepDialog()
		{
			if (pDialog.isShowing())
				pDialog.dismiss();
		}

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			pDialog = new ProgressDialog(ShowWebView.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			showpDialog();
		}

		@Override
		protected Void doInBackground(String... strings)
		{

			String fileUrl = strings[0];
			// -> https://letuscsolutions.files.wordpress.com/2015/07/five-point-someone-chetan-bhagat_ebook.pdf
			String fileName = strings[1];
			// ->five-point-someone-chetan-bhagat_ebook.pdf
			String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
			File folder = new File(extStorageDirectory, "PDF DOWNLOAD");
			folder.mkdir();

			File pdfFile = new File(folder, fileName);

			try
			{
				pdfFile.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			FileDownloader.downloadFile(fileUrl, pdfFile);
			return null;

		}

		@Override
		protected void onPostExecute(Void aVoid)
		{
			super.onPostExecute(aVoid);
			hidepDialog();
			Toast.makeText(getApplicationContext(), "Download PDf successfully", Toast.LENGTH_SHORT).show();

			Log.d("Download complete", "----------");
		}
	}

}
