package com.srllimited.srl.serverapis.rest.volley.custom.requests;

import java.util.Map;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class CustomStringRequest extends StringRequest
{
	/**
	 * Default charset for JSON request.
	 */
	protected static final String PROTOCOL_CHARSET = "utf-8";

	private String mRequestBody;

	private Map<String, String> mParams;

	private Map<String, String> mHeaders;

	/**
	 * Creates a new request with the given method.
	 *
	 * @param method        the request {@link Method} to use
	 * @param url           URL to fetch the string at
	 * @param listener      Listener to receive the String response
	 * @param errorListener Error listener, or null to ignore errors
	 */
	public CustomStringRequest(int method, String url, Response.Listener<String> listener,
			Response.ErrorListener errorListener)
	{
		super(method, url, listener, errorListener);
	}

	/**
	 * Creates a new request.
	 *
	 * @param method        the HTTP method to use
	 * @param url           URL to fetch the JSON from
	 * @param jsonRequest   A {@link JSONObject} to post with the request. Null is allowed and
	 *                      indicates no parameters will be posted along with request.
	 * @param headers       A list of extra HTTP headers to go along with this request.
	 * @param listener      Listener to receive the JSON response
	 * @param errorListener Error listener, or null to ignore errors.
	 */
	public CustomStringRequest(int method, String url, JSONObject jsonRequest, final Map<String, String> headers,
			Response.Listener<String> listener, Response.ErrorListener errorListener)
	{
		super(method, url, listener, errorListener);
		setRequestBody(jsonRequest);
		setHeaders(headers);
	}

	public void setRequestBody(final JSONObject jsonRequest)
	{
		setRequestBody((jsonRequest == null) ? null : jsonRequest.toString());
	}

	public void setRequestBody(final String requestBody)
	{
		mRequestBody = requestBody;
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError
	{
		if (mParams != null)
		{
			return mParams;
		}
		return super.getParams();
	}

	public void setParams(final Map<String, String> params)
	{
		mParams = params;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError
	{
		if (mHeaders != null)
		{
			return mHeaders;
		}
		return super.getHeaders();
	}

	public void setHeaders(final Map<String, String> headers)
	{
		//headers.put("iToken","SRLDIAG2017");
		mHeaders = headers;
	}

	/*@Override
	public byte[] getBody() throws AuthFailureError
	{
	//		if(mRequestBody != null)
	//		{
	//			try
	//			{
	//				return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
	//			}
	//			catch (UnsupportedEncodingException uee)
	//			{
	//				Log.w("Unsupported Encoding while trying to get the bytes of %s using %s",
	//				      mRequestBody, PROTOCOL_CHARSET);
	//				return null;
	//			}
	//		}
	
		return super.getBody();
	}*/
}
