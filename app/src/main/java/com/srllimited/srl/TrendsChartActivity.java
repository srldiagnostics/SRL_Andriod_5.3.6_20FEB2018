/*
package com.srllimited.srl;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.srllimited.srl.data.ReportsData;
import com.srllimited.srl.util.DateUtil;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class TrendsChartActivity extends Activity {
   // private XYPlot plot;
    public static Activity Trendschart;
    Context context;
    ArrayList<ReportsData> _tempreportData = new ArrayList<>();
    ReportsData _reportsData;
    private String[] mMonths = new String[50];
    private int[] RSTLPoint = new int[1];
    String getDate;
  //  private TextLabelWidget selectionWidget;
    private String[] domainLabels;
    private Number[] series1Numbers,series2Numbers;
    float[] rangeFloat;
    List<ReportsData> avail_reportsList = new ArrayList<>() ;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_xy_plot_example);

        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.plot);
        ReportsData reportsData= (ReportsData) getIntent().getSerializableExtra("obj1");
        getReportsData(reportsData);
        // create a couple arrays of y-values to plot:

        // turn the above arrays into XYSeries':
        // (Y_VALS_ONLY means use the element index as the x value)
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Low");
        XYSeries series2 = new SimpleXYSeries(
                Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "High");

        // create formatters to use for drawing a series using LineAndPointRenderer
        // and configure them from xml:
        final LineAndPointFormatter series1Format =
                new LineAndPointFormatter(this, R.xml.line_point_formatter);

        final LineAndPointFormatter series2Format =
                new LineAndPointFormatter(this, R.xml.line_point_formatter);
        series2Format.getLinePaint().setColor(Color.RED);

        // just for fun, add some smoothing to the lines:
        // see: http://androidplot.com/smooth-curves-and-androidplot/
        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        series2Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));


// Customize domain and range labels.
        plot.setDomainLabel("Date---->");
        plot.setRangeLabel("mg/dL---->");
        // wrap each series in instances of ScalingXYSeries before adding to the plot
        // so that we can animate the series values below:
        final ScalingXYSeries scalingSeries1 = new ScalingXYSeries(series1, 0, ScalingXYSeries.Mode.Y_ONLY);
        plot.addSeries(scalingSeries1, series1Format);

        final ScalingXYSeries scalingSeries2 = new ScalingXYSeries(series2, 0, ScalingXYSeries.Mode.Y_ONLY);
        plot.addSeries(scalingSeries2, series2Format);

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj,
                                       @NonNull StringBuffer toAppendTo,
                                       @NonNull FieldPosition pos) {
                int i = Math.round(((Number) obj).floatValue());
               Log.v("fdsfsdf","obj : "+obj.toString()+"iii:"+i);
                return toAppendTo.append(domainLabels[i]);
            }
            @Override
            public Object parseObject(String source, @NonNull ParsePosition pos) {
                return null;
            }
        });

        //set header text //////////////
        ///header text dispaly  in selectionWidget
        selectionWidget = new TextLabelWidget(plot.getLayoutManager(), "Graph ",
                new Size(
                        PixelUtils.dpToPix(100), SizeMode.ABSOLUTE,
                        PixelUtils.dpToPix(100), SizeMode.ABSOLUTE),
                TextOrientation.HORIZONTAL);

        selectionWidget.getLabelPaint().setTextSize(PixelUtils.dpToPix(16));

        // add a dark, semi-transparent background to the selection label widget:
        Paint p = new Paint();
        p.setARGB(100, 0, 0, 0);
        selectionWidget.setBackgroundPaint(p);

        selectionWidget.position(
                0, HorizontalPositioning.RELATIVE_TO_CENTER,
                PixelUtils.dpToPix(45), VerticalPositioning.ABSOLUTE_FROM_TOP,
                Anchor.TOP_MIDDLE);
        selectionWidget.pack();
        selectionWidget.setText(avail_reportsList.get(0).getPRDCT_NAME());

        ////////////////////
        ///////////////////////
        ///set result point on plot ////////////////////

        XYSeries seriesPoint1 = generateScatter("Result", 1,*/
/*RSTLPoint*//*
				new RectRegion(3,14,10,RSTLPoint[0]));//3 and RSTLPoint is result point minX & MaxY
				
				// create formatters to use for drawing a series using LineAndPointRenderer
				// and configure them from xml:
				final LineAndPointFormatter seriesPointFormat1 =
				   new LineAndPointFormatter(this, R.xml.point_formatter);
				
				*/
/*  LineAndPointFormatter series2Format12 =
                new LineAndPointFormatter(this, R.xml.point_formatter_2);*//*
																			
																			
																			// add each series to the xyplot:
																			//  plot.addSeries(series11, series1Format11);
																			// plot.addSeries(series22, series2Format12);
																			
																			// reduce the number of range labels
																			plot.setLinesPerRangeLabel(3);
																			
																			
																			final ScalingXYSeries scalingSeriesPoint1 = new ScalingXYSeries(seriesPoint1, 0, ScalingXYSeries.Mode.Y_ONLY);
																			plot.addSeries(scalingSeriesPoint1, seriesPointFormat1);
																			
																			
																			//////////////////////////////
																			
																			
																			plot.setRangeBoundaries(0, rangeFloat[1]+40, BoundaryMode.FIXED);
																			
																			// animate a scale value from a starting val of 0 to a final value of 1:
																			ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
																			
																			// use an animation pattern that begins and ends slowly:
																			animator.setInterpolator(new AccelerateDecelerateInterpolator());
																			
																			animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
																			@Override
																			public void onAnimationUpdate(ValueAnimator valueAnimator) {
																			float scale = valueAnimator.getAnimatedFraction();
																			scalingSeries1.setScale(scale);
																			scalingSeries2.setScale(scale);
																			scalingSeriesPoint1.setScale(scale);
																			plot.redraw();
																			}
																			});
																			animator.addListener(new Animator.AnimatorListener() {
																			@Override
																			public void onAnimationStart(Animator animator) {
																			
																			}
																			
																			@Override
																			public void onAnimationEnd(Animator animator) {
																			// the animation is over, so show point labels:
																			series1Format.getPointLabelFormatter().getTextPaint().setColor(Color.WHITE);
																			series2Format.getPointLabelFormatter().getTextPaint().setColor(Color.WHITE);
																			seriesPointFormat1.getPointLabelFormatter().getTextPaint().setColor(Color.WHITE);
																			plot.redraw();
																			}
																			
																			@Override
																			public void onAnimationCancel(Animator animator) {
																			
																			}
																			
																			@Override
																			public void onAnimationRepeat(Animator animator) {
																			
																			}
																			});
																			
																			// the animation will run for 1.5 seconds:
																			animator.setDuration(1500);
																			animator.start();
																			}
																			*/
/**
     * Generate a XYSeries of random points within a specified region
     * @param title
     * @param numPoints
     * @param region
     * @return
     */
/*

private XYSeries generateScatter(String title, int numPoints,*/
/*int[]rsltpoint*//*
					RectRegion region) {
					SimpleXYSeries series = new SimpleXYSeries(title);
					
					//RectRegion(Number minX, Number maxX, Number minY, Number maxY)
					//new RectRegion(2, 6, 2, 7)
					for(int i = 0; i < */
/*rsltpoint.length*//*
					numPoints; i++) {
					
					//series.addLast(rsltpoint[i],rsltpoint[i]);
					
					series.addLast(
					region.getMinX().doubleValue()*/
/* + (Math.random() * region.getWidth().doubleValue())*//*
														,
														region.getMaxY().doubleValue() */
/*+ (Math.random() * region.getHeight().doubleValue())*//*
														
														);
														*/
/*  series.addLast(
                    region.getMinX().doubleValue() + (Math.random() * region.getWidth().doubleValue()),
                    region.getMinY().doubleValue() + (Math.random() * region.getHeight().doubleValue())
            );*//*
					
					}
					return series;
					}
					
					//set report data
					private void getReportsData(ReportsData reportsData*/
/*String cpt_code*//*
					) {
					try {
					try {
					String[] range = reportsData.getPRNT_RNG_TXT().split("-");
					rangeFloat = new float[range.length];
					for (int i = 0; i < rangeFloat.length; i++) {
					 rangeFloat[i] = Float.parseFloat(range[i]);
					}
					}catch (Exception e){
					rangeFloat[0] =Integer.parseInt(reportsData.getELMNT_MIN_RANGE());
					rangeFloat[1] =Integer.parseInt(reportsData.getELMNT_MAX_RANGE());
					}
					domainLabels = new String[]{"1", "2", "3", "6", "7", "8", "9", "10", "13", "14"};
					series1Numbers = new Number[]{rangeFloat[0],rangeFloat[0],rangeFloat[0],rangeFloat[0],rangeFloat[0],rangeFloat[0],rangeFloat[0],rangeFloat[0],rangeFloat[0],rangeFloat[0]};
					series2Numbers = new Number[]{rangeFloat[1],rangeFloat[1],rangeFloat[1],rangeFloat[1],rangeFloat[1],rangeFloat[1],rangeFloat[1],rangeFloat[1],rangeFloat[1],rangeFloat[1]};
					avail_reportsList = new ArrayList<>();
					avail_reportsList.add(reportsData);
					
					*/
/* try {
                avail_reportsList = reportsDb.getCpdtCodeResult(cpt_code);

            } catch (Exception e) {
            }*//*
				
				_tempreportData = new ArrayList<>();
				
				
				if (avail_reportsList != null && avail_reportsList.size() > 0) {
				 try {
				
				    // setDetails(avail_reportsList.get(0));
				     Collections.sort(avail_reportsList, new Comparator<ReportsData>() {
				         @Override
				         public int compare(ReportsData o1, ReportsData o2) {
				
				             final long d1 = o1.getACC_DT();
				             final long d2 = o2.getACC_DT();
				             return (d1 > d2) ? -1 : (d1 > d2) ? 1 : 0;
				
				         }
				     });
				
				     Collections.reverse(avail_reportsList);
				 } catch (Exception e) {
				
				 }
				 for (int i = 0; i < avail_reportsList.size(); i++) {
				     _reportsData = new ReportsData();
				     _reportsData.setRSLT(String.format("%.2f", Double.valueOf(avail_reportsList.get(i).getRSLT()+"")));
				     _tempreportData.add(_reportsData);
				     getDate = DateUtil.fetchGraphDate(avail_reportsList.get(i).getACC_DT());
				     mMonths[i] = getDate;
				     RSTLPoint[i] = Integer.parseInt(avail_reportsList.get(0).getRSLT());
				 }
				 domainLabels[3] =mMonths[0];
				}
				
				} catch (Exception e) {
				e.printStackTrace();
				
				}
				}
				}
				
				*/
