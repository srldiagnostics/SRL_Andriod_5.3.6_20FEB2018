package com.srllimited.srl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.srllimited.srl.data.ReportsData;
import com.srllimited.srl.database.ReportsDatabase;
import com.srllimited.srl.util.DateUtil;
import com.srllimited.srl.util.Log;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

public class HealthChartActivity extends MenuBaseActivity
{
	public static Activity healthchart;

	TextView element_name, result_units;
	int prdct_id;
	String cpt_code;
	//    private String[] mMonths = new String[]{
	//            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
	//    };
	String mmElementRange = "";
	double min_range;
	double max_range;
	ReportsDatabase reportsDb = new ReportsDatabase(this);
	ArrayList<ReportsData> _tempreportData = new ArrayList<>();
	ReportsData _reportsData;
	Context context;
	boolean clicked = true;
	String resultValue;
	String getDate;
	private LineChart mChart;
	private String[] mMonths = new String[50];
	private Dialog promoDialog;
	private OnChartGestureListener mChartGestureListener = new OnChartGestureListener()
	{
		@Override
		public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture)
		{

			Log.i("onTouch", "START, x: " + me.getX() + ", y: " + me.getY());
		}

		@Override
		public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture)
		{

			Log.i("onTouch", "END, lastGesture: " + lastPerformedGesture);

			/*// un-highlight values after the gesture is finished and no single-tap
			if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
			// or highlightTouch(null) for callback to onNothingSelected(...)
			{
				mChart.highlightValues(null);
			}*/
		}

		@Override
		public void onChartLongPressed(MotionEvent me)
		{
			Log.i("LongPress", "Chart longpressed.");
		}

		@Override
		public void onChartDoubleTapped(MotionEvent me)
		{
			Log.i("onTap", "Chart double-tapped.");
		}

		@Override
		public void onChartSingleTapped(MotionEvent me)
		{
			Log.i("SingleTap", "Chart single-tapped.");
		}

		@Override
		public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY)
		{
			Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
		}

		@Override
		public void onChartScale(MotionEvent me, float scaleX, float scaleY)
		{
			Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
		}

		@Override
		public void onChartTranslate(MotionEvent me, float dX, float dY)
		{
			Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
		}
	};
	private OnChartValueSelectedListener mChartValueSelectedListener = new OnChartValueSelectedListener()
	{
		@Override
		public void onValueSelected(final Entry e, final Highlight h)
		{
			Log.i("Entry selected", e.toString());
			Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleX() + ", high: " + mChart.getHighestVisibleX());

			Log.i("MIN MAX", "xmin: " + mChart.getXChartMin() + ", xmax: " + mChart.getXChartMax() + ", ymin: "
					+ mChart.getYChartMin() + ", ymax: " + mChart.getYChartMax());
		}

		@Override
		public void onNothingSelected()
		{
			Log.i("Nothing selected", "Nothing selected.");
		}
	};

	public static float round(float d)
	{
		int decimalPlace = 2;
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_health_chart);
		super.addContentView(R.layout.activity_health_chart);
		context = HealthChartActivity.this;
		healthchart = this;
		header_loc_name.setText("Graphical Health Tracker");
		header_loc_name.setEnabled(false);

		element_name = (TextView) findViewById(R.id.element_name);
		result_units = (TextView) findViewById(R.id.result_units);
		mChart = (LineChart) findViewById(R.id.linechart);
		mChart.setOnChartGestureListener(mChartGestureListener);
		mChart.setOnChartValueSelectedListener(mChartValueSelectedListener);
		mChart.setDrawGridBackground(false);

		try
		{
			reportsDb = new ReportsDatabase(getApplicationContext());
		}
		catch (Exception e)
		{

		}

		/*promoDialog = new Dialog(this);
		promoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		promoDialog.setCancelable(false);
		promoDialog.setContentView(R.layout.pop_up_chart_details);*/
		/*accessdate = (TextView) promoDialog.findViewById(R.id.accessdate);
		highvalue = (TextView) promoDialog.findViewById(R.id.highvalue);
		resultvalue = (TextView) promoDialog.findViewById(R.id.resultvalue);
		lowvalue = (TextView) promoDialog.findViewById(R.id.lowvalue);*/

		try
		{
			Bundle extras = getIntent().getExtras();
			prdct_id = extras.getInt("PRDCT_CD");
			cpt_code = extras.getString("CPT_CODE");

			getReportData(prdct_id);
			getReportsData(cpt_code);

		}
		catch (Exception e)
		{

		}

		try
		{
			// add data
			setData();
			mChart.animateX(2500);
			// get the legend (only possible after setting data)
			/*Legend l = mChart.getLegend();
			// modify the legend ...
			// l.setPosition(LegendPosition.LEFT_OF_CHART);
			l.setForm(Legend.LegendForm.LINE);*/

			Description description = new Description();
			description.setText("");
			mChart.setDescription(description);
			mChart.setNoDataText("You need to provide data for the chart.");

			// enable scaling and dragging
			mChart.setDragEnabled(true);
			mChart.setScaleEnabled(true);
			mChart.setPinchZoom(true);

			Log.e("value", (float) min_range + "");
			LimitLine upper_limit = new LimitLine((float) max_range, "Max");
			upper_limit.setLineWidth(1f);
			upper_limit.enableDashedLine(0f, 0f, 0f);
			upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
			upper_limit.setTextSize(10f);
			upper_limit.setLabel("Max :" + max_range);
			upper_limit.setTextColor(Color.RED);

			LimitLine lower_limit = new LimitLine((float) min_range, "Min");
			lower_limit.setLineWidth(1f);
			lower_limit.enableDashedLine(0f, 0f, 0f);
			lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
			lower_limit.setTextSize(10f);
			lower_limit.setLabel("Min: " + min_range);
			lower_limit.setTextColor(Color.RED);
			YAxis leftAxis = mChart.getAxisLeft();
			leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
			leftAxis.addLimitLine(upper_limit);
			leftAxis.addLimitLine(lower_limit);

			float min_f = (float) min_range;
			float max_f = (float) max_range;
			float yminVal = 0;
			try
			{
				if (mChart != null && mChart.getData() != null)
					yminVal = mChart.getData().getYMin();
			}
			catch (Exception e)
			{

			}
			if (yminVal > min_f)
			{
				if (min_f != 0.0)
					//leftAxis.setAxisMinimum(min_f - min_f / 10 - 0.30f);
					leftAxis.setAxisMinimum(min_f - 0.30f);
				else
					leftAxis.setAxisMinimum(min_f - 0.30f);
			}
			else
			{
				//leftAxis.setAxisMinimum(yminVal - yminVal / 10 - 0.30f);
				leftAxis.setAxisMinimum(yminVal - .5f);
			}
			float ymaxVal = 0;
			try
			{
				if (mChart != null && mChart.getData() != null)
					ymaxVal = mChart.getData().getYMax();
			}
			catch (Exception e)
			{

			}
			if (ymaxVal > max_f)
				leftAxis.setAxisMaximum(mChart.getData().getYMax() + mChart.getData().getYMax() / 5 + 0.20f);

			else
				leftAxis.setAxisMaximum(max_f + max_f / 5 + 0.20f);
			//            leftAxis.setGranularity(0.20f);
			leftAxis.setDrawGridLines(false);
			leftAxis.setDrawLimitLinesBehindData(false);

			XAxis xAxis = mChart.getXAxis();
			xAxis = mChart.getXAxis();
			xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
			xAxis.setAxisMinimum(0f);
			xAxis.setGranularity(1f);
			xAxis.setDrawGridLines(false);

			xAxis.setValueFormatter(new IAxisValueFormatter()
			{
				@Override
				public String getFormattedValue(float value, AxisBase axis)
				{
					return mMonths[(int) value % mMonths.length];
				}
			});

			mChart.getAxisRight().

					setEnabled(false);

			//mChart.getViewPortHandler().setMaximumScaleY(2f);
			//            mChart.getViewPortHandler().setMaximumScaleX(2f);

			//	mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

			//  dont forget to reload the drawing
			mChart.invalidate();

			// enable touch gestures
			mChart.setTouchEnabled(true);
			CustomMarkerView marker = new CustomMarkerView(context, R.layout.pop_up_chart_details, getDate, max_range,
					min_range);
			marker.setChartView(mChart);
			mChart.setMarker(marker);
			// mChart.setMarkerView(marker);

		}
		catch (Exception e)
		{

		}
	}

	private void getReportsData(String cpt_code)
	{
		try
		{
			List<ReportsData> avail_reportsList = new ArrayList<>();
			try
			{
				avail_reportsList = reportsDb.getCpdtCodeResult(cpt_code);

			}
			catch (Exception e)
			{
			}
			_tempreportData = new ArrayList<>();

			if (avail_reportsList != null && avail_reportsList.size() > 0)
			{
				try
				{

					setDetails(avail_reportsList.get(0));
					Collections.sort(avail_reportsList, new Comparator<ReportsData>()
					{
						@Override
						public int compare(ReportsData o1, ReportsData o2)
						{

							final long d1 = o1.getACC_DT();
							final long d2 = o2.getACC_DT();
							return (d1 > d2) ? -1 : (d1 > d2) ? 1 : 0;

						}
					});

					Collections.reverse(avail_reportsList);
				}
				catch (Exception e)
				{

				}
				for (int i = 0; i < avail_reportsList.size(); i++)
				{
					_reportsData = new ReportsData();
					_reportsData
							.setRSLT(String.format("%.2f", Double.valueOf(avail_reportsList.get(i).getRSLT() + "")));
					_tempreportData.add(_reportsData);
					getDate = DateUtil.fetchGraphDate(avail_reportsList.get(i).getACC_DT());
					mMonths[i] = getDate;
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();

		}
	}

	private void getReportData(int prdct_id)
	{
		ReportsData _reportsData = null;
		try
		{
			_reportsData = reportsDb.getReportObject(prdct_id);
			if (_reportsData != null)
			{
				setDetails(_reportsData);
			}

		}
		catch (Exception e)
		{

		}

	}

	private void setDetails(ReportsData _reportsData)
	{

		if (_reportsData != null)
		{
			if (_reportsData.getELMNT_NAME() != null && !_reportsData.getELMNT_NAME().equalsIgnoreCase("null"))
			{
				element_name.setText(_reportsData.getELMNT_NAME() + "");
			}

			if (_reportsData.getELMNT_RSLT_UNIT() != null
					&& !_reportsData.getELMNT_RSLT_UNIT().equalsIgnoreCase("null"))
			{
				result_units.setText(_reportsData.getELMNT_RSLT_UNIT() + "");
			}
			if (_reportsData.getELMNT_MIN_RANGE() != null
					&& !_reportsData.getELMNT_MIN_RANGE().equalsIgnoreCase("null"))
			{
				min_range = Double.valueOf(_reportsData.getELMNT_MIN_RANGE());
			}
			if (_reportsData.getELMNT_MAX_RANGE() != null
					&& !_reportsData.getELMNT_MAX_RANGE().equalsIgnoreCase("null"))
			{
				max_range = Double.valueOf(_reportsData.getELMNT_MAX_RANGE());
			}
		}

	}

	private ArrayList<String> setXAxisValues()
	{
		ArrayList<String> xVals = new ArrayList<String>();
		xVals.add("10");
		xVals.add("20");
		xVals.add("30");
		xVals.add("30.5");
		xVals.add("40");

		return xVals;
	}

	private ArrayList<Entry> setYAxisValues()
	{
		ArrayList<Entry> yVals1 = new ArrayList<>();
		if (_tempreportData != null && _tempreportData.size() > 0)
		{

			for (int i = 0; i < _tempreportData.size(); i++)
			{
				if (_tempreportData.get(i).getRSLT() != null
						&& !_tempreportData.get(i).getRSLT().equalsIgnoreCase("null"))
				{
					yVals1.add(new Entry(i, round(Float
							.valueOf(String.format("%.2f", Double.valueOf(_tempreportData.get(i).getRSLT())) + ""))));
					resultValue = String.format("%.2f", Double.valueOf(_tempreportData.get(i).getRSLT()));
				}
			}
		}
		return yVals1;

	}

	private void setData()
	{

		try
		{
			ArrayList<String> xVals = setXAxisValues();

			ArrayList<Entry> yVals = setYAxisValues();

			LineDataSet set1 = new LineDataSet(yVals, "");
			set1.setColor(Color.BLUE);
			set1.setCircleColor(Color.BLACK);
			set1.setLineWidth(1f);
			set1.setCircleRadius(4f);
			set1.setDrawCircleHole(false);

			set1.setValueTextSize(9f);
			set1.setDrawFilled(true);

			set1.setValueFormatter(new MyValueFormatter());
			set1.setDrawFilled(false);//fill color make false
			ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
			dataSets.add(set1); // add the datasets
			// create a data object with the datasets
			LineData data = new LineData(dataSets);
			// set data

			mChart.getXAxis().setAxisMaximum(data.getXMax() + 0.25f);

			mChart.setData(data);
			////////////////chart in cubic
			List<ILineDataSet> sets = mChart.getData().getDataSets();

			for (ILineDataSet iSet : sets)
			{

				LineDataSet set = (LineDataSet) iSet;
				set.setMode(set.getMode() == LineDataSet.Mode.CUBIC_BEZIER ? LineDataSet.Mode.LINEAR
						: LineDataSet.Mode.CUBIC_BEZIER);
			}
			mChart.invalidate();
			//////////////////////

		}
		catch (Exception e)
		{

		}
	}

	public class MyValueFormatter implements IValueFormatter
	{

		private DecimalFormat mFormat;

		public MyValueFormatter()
		{
			mFormat = new DecimalFormat("#,##,##,###0.00"); // use one decimal
		}

		@Override
		public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler)
		{
			// write your logic here
			return mFormat.format(value); // e.g. append a dollar-sign
		}
	}

	public class CustomMarkerView extends MarkerView
	{
		String getDate;
		double max_range;
		double min_range;
		//private TextView tvContent;
		private TextView accessdate, highvalue, resultvalue, lowvalue;

		public CustomMarkerView(Context context, int layoutResource, String tempDate, double maxValue, double minValue)
		{
			super(context, layoutResource);
			// this markerview only displays a textview
			//tvContent = (TextView) findViewById(R.id.accessdate);

			accessdate = (TextView) findViewById(R.id.accessdate);
			highvalue = (TextView) findViewById(R.id.highvalue);
			resultvalue = (TextView) findViewById(R.id.resultvalue);
			lowvalue = (TextView) findViewById(R.id.lowvalue);
			getDate = tempDate;
			max_range = maxValue;
			min_range = minValue;
		}

		@Override
		public void refreshContent(Entry e, Highlight highlight)
		{
			try
			{
				//getXOffset();
				String accesion_date = mChart.getXAxis().getValueFormatter().getFormattedValue(e.getX(),
						mChart.getXAxis());
				Log.e("accesion_date", "date" + accesion_date + "");
				if (accesion_date != null && !accesion_date.equalsIgnoreCase("null"))
				{
					accessdate.setText("Accession " + accesion_date + "");
				}
				else
				{
					accessdate.setText("");
				}

				{
					if (max_range != 0.0)
					{
						highvalue.setText(String.format("%.2f", Double.valueOf(max_range)) + "");
					}
					else
					{
						highvalue.setText("");
					}
				}
				if (resultValue != null && !resultValue.equalsIgnoreCase("null"))
				{
					resultvalue.setText(String.format("%.2f", Double.valueOf(e.getY())) + "");
				}
				else
				{
					resultvalue.setText("");
				}
				//
				if (min_range != 0.0)
				{
					lowvalue.setText(String.format("%.2f", Double.valueOf(min_range)) + "");
				}
				else
				{
					lowvalue.setText("");
				}
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}

			Log.e("Entry selected", e.toString());
			Log.e("LOWHIGH", "low: " + mChart.getLowestVisibleX() + ", high: " + mChart.getHighestVisibleX());

			Log.e("MIN MAX", "xmin: " + mChart.getXChartMin() + ", xmax: " + mChart.getXChartMax() + ", ymin: "
					+ mChart.getYChartMin() + ", ymax: " + mChart.getYChartMax());
		}

		@Override
		public MPPointF getOffset()
		{
			return new MPPointF(-(getWidth() / 2), -getHeight());
		}

		public int getXOffset()
		{
			// this will center the marker-view horizontally
			return -(getWidth() / 2);
		}

		public int getYOffset()
		{
			// this will cause the marker-view to be above the selected value
			return -getHeight();
		}

		@Override
		public void draw(Canvas canvas, float posx, float posy)
		{
			try
			{

				// take offsets into consideration
				posx += getXOffset();
				posy += getYOffset();
				// AVOID OFFSCREEN
				if (posx < 45)
				{
					posx = 45;
				}
				if (posx > 265)
				{
					posx = 265;
				}

				if (posy < 38)
				{
					posy = 38;
				}
				/*if (posy > 265)
				{
					posy = 265;
				}*/
				// translate to the correct position and draw
				canvas.translate(posx, posy);
				draw(canvas);
				canvas.translate(-posx, -posy);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
