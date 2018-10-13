package com.example.sadic.travelerapp.ui.splash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Bullet;
import com.anychart.charts.Pie;
import com.anychart.charts.Resource;
import com.anychart.enums.AvailabilityPeriod;
import com.anychart.enums.TimeTrackingMode;
import com.anychart.scales.calendar.Availability;
import com.example.sadic.travelerapp.R;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Resource resource = AnyChart.resource();

        resource.zoomLevel(1d)
                .timeTrackingMode(TimeTrackingMode.ACTIVITY_PER_CHART)
                .currentStartDate("2018-09-29");

        resource.resourceListWidth(120);

        resource.calendar().availabilities(new Availability[] {
                new Availability(AvailabilityPeriod.DAY, (Double) null, 10d, true, (Double) null, (Double) null, 18d),
                new Availability(AvailabilityPeriod.DAY, (Double) null, 14d, false, (Double) null, (Double) null, 15d),
                new Availability(AvailabilityPeriod.WEEK, (Double) null, (Double) null, false, 5d, (Double) null, 18d),
                new Availability(AvailabilityPeriod.WEEK, (Double) null, (Double) null, false, 6d, (Double) null, 18d)
        });

        List<DataEntry> data = new ArrayList<>();

        data.add(new ResourceDataEntry(
                "Dragos",
                "Developer",
                "http://i65.tinypic.com/2hwjhi8.png",
                new Activity[]{
                        new Activity(
                                "UI Design",
                                new Interval[]{
                                        new Interval("2018-09-29", "2018-10-01", 120)
                                },
                                "#62BEC1"),
                        new Activity(
                                "UI customization",
                                new Interval[]{
                                        new Interval("2018-09-29", "2018-10-01", 180)
                                },
                                "#62BEC1"),
                        new Activity(
                                "API implementation",
                                new Interval[]{
                                        new Interval("2018-10-02", "2018-10-04", 120)
                                },
                                "#62BEC1"),
                        new Activity(
                                "Weather API",
                                new Interval[]{
                                        new Interval("2018-10-05", "2018-10-07", 300)
                                },
                                "#62BEC1"),
                        new Activity(
                                "Payment Implementation",
                                new Interval[]{
                                        new Interval("2018-10-08", "2018-10-08", 300)
                                },
                                "#62BEC1")
//                        new Activity(
//                                "iPad touch problems",
//                                new Interval[]{
//                                        new Interval("2018-10-12", "2018-10-16", 300),
//                                        new Interval("2018-10-17", "2018-10-21", 60)
//                                },
//                                "#62BEC1"),
//                        new Activity(
//                                "Some improvements for chart labels",
//                                new Interval[]{
//                                        new Interval("2018-10-17", "2018-10-22", 240),
//                                        new Interval("2018-10-22", "2018-10-26", 240)
//                                },
//                                "#62BEC1")
                }));
        data.add(new ResourceDataEntry( //http://cdn.anychart.com/images/resource-chart/developer-antonio.png
                "Jessie",
                "Developer",
                "http://i67.tinypic.com/2d1o277.png",
                new Activity[]{
                        new Activity(
                                "Retrofit data",
                                new Interval[]{
                                        new Interval("2018-10-02", "2018-10-05", 120)
                                },
                                "#EA526F"),
                        new Activity(
                                "Google Maps",
                                new Interval[]{
                                        new Interval("2018-10-06", "2018-10-08", 240)
                                },
                                "#EA526F")
//                        new Activity(
//                                "Chart bug fixes",
//                                new Interval[]{
//                                        new Interval("2018-10-06", "2016-10-08", 180)
//                                },
//                                "#EA526F")
                }));

        resource.data(data);

        anyChartView.setChart(resource);
    }

    private class ResourceDataEntry extends DataEntry {
        ResourceDataEntry(String name, String description, String image, Activity[] activities) {
            setValue("name", name);
            setValue("description", description);
            setValue("image", image);
            setValue("activities", activities);
        }
    }

    private class Activity extends DataEntry {
        Activity(String name, Interval[] intervals, String fill) {
            setValue("name", name);
            setValue("intervals", intervals);
            setValue("fill", fill);
        }
    }

    private class Interval extends DataEntry {
        Interval(String start, String end, Integer minutesPerDay) {
            setValue("start", start);
            setValue("end", end);
            setValue("minutesPerDay", minutesPerDay);
        }
    }

}
