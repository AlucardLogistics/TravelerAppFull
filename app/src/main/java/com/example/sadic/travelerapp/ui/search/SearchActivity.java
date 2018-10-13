package com.example.sadic.travelerapp.ui.search;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sadic.travelerapp.R;
import com.example.sadic.travelerapp.data.model.CityItem;
import com.example.sadic.travelerapp.data.model.RouteItem;
import com.example.sadic.travelerapp.data.model.weather.ListItem;
import com.example.sadic.travelerapp.data.model.weather.Weather;
import com.example.sadic.travelerapp.data.retrofit.ApiService;
import com.example.sadic.travelerapp.data.retrofit.RetrofitInstance;
import com.example.sadic.travelerapp.ui.login.LoginActivity;
import com.example.sadic.travelerapp.ui.splash.GraphActivity;
import com.example.sadic.travelerapp.ui.tripresults.TripResultsActivity;
import com.example.sadic.travelerapp.utils.SharedPref;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CalendarFragment.OnDataPass, IViewSearch {
    private static final String TAG = "SearchActivity";

    private final String WEATHER_API_KEY = "bb72d2c2b6850337b36813263b0d37ee";

    //EditText etFrom, etTo;
    AutoCompleteTextView etFrom, etTo;
    TextView tvDate, tvSearch, tvWeatherArrivalTemperature, tvWeatherArrivalDescription, tvWeatherArrivalHumidity,
            tvWeatherLocalTemperature, tvWeatherLocalDescription, tvWeatherLocalHumidity;
    TextView tvWeatherArrivalCity, tvWeatherLocalCity;
    ImageView ivWeatherLocalIcon, ivWeatherArrivalIcon;
    Button btSearchTrip, btSwap, btCalendar;
    private ProgressDialog pd;
    IPresenterSearch presenterSearch;
    List<String> cities = new ArrayList<>();
    List<CityItem> cityItemList = new ArrayList<>();
    List<ListItem> myWeatherLocal = new ArrayList<>();
    List<ListItem> myWeatherArrived = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();

        presenterSearch = new PresenterSearch(this);
        presenterSearch.getActivityData();

    }

    void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etFrom = findViewById(R.id.etFrom);
        etTo = findViewById(R.id.etDestination);
        btSwap = findViewById(R.id.btSwap);
        btCalendar = findViewById(R.id.btCalendar);
        tvDate = findViewById(R.id.tvDate);
        tvSearch = findViewById(R.id.tvSearch);

        //weather local setup
        tvWeatherLocalCity = findViewById(R.id.tvWeatherLocalCity);
        tvWeatherLocalCity.setText("Departure City Weather");
        tvWeatherLocalTemperature = findViewById(R.id.tvWeatherLocalTemperature);
        tvWeatherLocalTemperature.setText("-.-°");
        tvWeatherLocalDescription = findViewById(R.id.tvWeatherLocalDescription);
        tvWeatherLocalDescription.setText("Weather: ---");
        tvWeatherLocalHumidity = findViewById(R.id.tvWeatherLocalHumidity);
        tvWeatherLocalHumidity.setText("Humidity: -.- %");
        ivWeatherLocalIcon = findViewById(R.id.ivWeatherLocalIcon);
        //weather arrival setup
        tvWeatherArrivalCity = findViewById(R.id.tvWeatherArrivalCity);
        tvWeatherArrivalCity.setText("Arrival City Weather");
        tvWeatherArrivalTemperature = findViewById(R.id.tvWeatherArrivalTemperature);
        tvWeatherArrivalTemperature.setText("-.-°");
        tvWeatherArrivalDescription = findViewById(R.id.tvWeatherArrivalDescription);
        tvWeatherArrivalDescription.setText("Weather: ---");
        tvWeatherArrivalHumidity = findViewById(R.id.tvWeatherArrivalHumidity);
        tvWeatherArrivalHumidity.setText("Humidity: -.- %");
        ivWeatherArrivalIcon = findViewById(R.id.ivWeatherArrivalIcon);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, cities);
        etFrom.setAdapter(adapter);
        etTo.setAdapter(adapter);

        btSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String from = etFrom.getText().toString();
                String to = etTo.getText().toString();

                etTo.setText(from);
                etFrom.setText(to);
            }
        });

        btCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etFrom.getText().toString().equals("") || etTo.getText().toString().equals("")) {
                    Toast.makeText(SearchActivity.this, "Select Departure or Arrival", Toast.LENGTH_SHORT).show();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.calendarFragment, new CalendarFragment())
                            .addToBackStack(null)
                            .commit();
                }

            }
        });



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView tvName = headerView.findViewById(R.id.tvToolBarName);
        tvName.setText(SharedPref.read(SharedPref.DISPLAY_NAME, ""));
        TextView tvEmail = headerView.findViewById(R.id.tvToolBarEmail);
        tvEmail.setText(SharedPref.read(SharedPref.EMAIL, ""));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
        } else if (id == R.id.nav_history) {
            Intent iGraph = new Intent(SearchActivity.this, GraphActivity.class);
            startActivity(iGraph);
        } else if (id == R.id.nav_contact) {

        } else if (id == R.id.nav_logout) {
            //FirebaseAuth.getInstance().signOut();
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // ...
                            Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDataPass(String data) {
        tvDate.setText(data);
        tvSearch.setText("Search >");
        String weatherDate = data + " 12:00:00";
        getWeather(weatherDate);
    }

    private void getWeather(final String weatherDate) {
        pd = new ProgressDialog(this);
        pd.setTitle("Weather");
        pd.setMessage("Getting weather data.");
        pd.show();

        ApiService apiService = RetrofitInstance.getRetrofitInstanceWeather()
                .create(ApiService.class);

        setCoordonates();
        final String depCity = SharedPref.read(SharedPref.CITY_DEPARTURE, "");
        String depLat = SharedPref.read(SharedPref.LATITUDE_DEPARTURE, "");
        String depLon = SharedPref.read(SharedPref.LONGITUDE_DEPARTURE, "");
        Log.d(TAG, "getWeather: lat and long: " + depLat + " " + depLon);
        final String arrCity = SharedPref.read(SharedPref.CITY_ARRIVAL, "");
        String arrLat = SharedPref.read(SharedPref.LATITUDE_ARRIVAL, "");
        String arrLon = SharedPref.read(SharedPref.LONGITUDE_ARRIVAL, "");

        Call<Weather> weatherCallDep = apiService
                .getWeather(depLat, depLon, "imperial", "40", WEATHER_API_KEY);

        weatherCallDep.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                //Log.d(TAG, "onResponse: respones success " + response.body().toString());
                pd.dismiss();
                myWeatherLocal.clear();
                boolean isMatch = false;
                List<ListItem> weatherDetails = response.body().getList();

                for(int i = 0; i< weatherDetails.size(); i++) {
                    isMatch = false;
                    if(weatherDetails.get(i).getDtTxt() != null && weatherDetails.get(i).getDtTxt().trim().contentEquals(weatherDate.trim())) {
                        Log.d(TAG, "onResponse: Match: " + response.body().getList().get(i).getDtTxt()
                                + " equals " + weatherDate);

                        myWeatherLocal.add(weatherDetails.get(i));
                        isMatch = true;
                        break;
                    }

                }

                if(!isMatch) {
                    Log.d(TAG, "onResponse: Response is null");
                    myWeatherLocal.clear();
                    Toast.makeText(SearchActivity.this, "Data to far ahead.", Toast.LENGTH_SHORT).show();
                    tvWeatherLocalTemperature.setText("-.-°");
                    tvWeatherLocalDescription.setText("Weather: ---");
                    tvWeatherLocalHumidity.setText("Humidity: -.- %");
                    tvWeatherLocalCity.setText("Departure City Weather");
                    ivWeatherLocalIcon.setImageResource(R.drawable.sunny_icon);
                }
                Log.d(TAG, "onResponse: itemNeeded: " + myWeatherLocal.toString());

                String temp = null, desc = null, hum = null, icon = null;
                if(!myWeatherLocal.isEmpty()) {
                    for(int j = 0; j < myWeatherLocal.size(); j++) {
                        int temperature = (int) myWeatherLocal.get(0).getMain().getTemp();
                        temp = String.valueOf(temperature);
                        desc = String.valueOf(myWeatherLocal.get(0).getWeather().get(0).getDescription());
                        hum = String.valueOf(myWeatherLocal.get(0).getMain().getHumidity());
                        icon = String.valueOf(myWeatherLocal.get(0).getWeather().get(0).getIcon());
                    }
                    Log.d(TAG, "onResponse: temperature VALUE: " + temp);


                    tvWeatherLocalCity.setText(depCity);
                    tvWeatherLocalTemperature.setText(temp + "°");
                    tvWeatherLocalDescription.setText("Weather: " + desc);
                    tvWeatherLocalHumidity.setText("Humidity: " + hum);
                    Log.d(TAG, "onResponse: icon symbol: " + icon);

                    switch (icon) {
                        case "01d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherLocalIcon);
                            break;
                        case "02d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherLocalIcon);
                            break;
                        case "03d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherLocalIcon);
                            break;
                        case "04d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherLocalIcon);
                            break;
                        case "04n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherLocalIcon);
                            break;
                        case "10d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherLocalIcon);
                            break;
                        case "11d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherLocalIcon);
                            break;
                        case "13d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherLocalIcon);
                            break;
                        case "01n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherLocalIcon);
                            break;
                        case "02n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherLocalIcon);
                            break;
                        case "03n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherLocalIcon);
                            break;
                        case "10n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherLocalIcon);
                            break;
                        case "11n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherLocalIcon);
                            break;
                        case "13n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherLocalIcon);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.d(TAG, "onFailure: FAIL to get weather");
            }
        });

        Call<Weather> weatherCallArr = apiService
                .getWeather(arrLat, arrLon, "imperial", "40", WEATHER_API_KEY);

        weatherCallArr.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                //Log.d(TAG, "onResponse: respones success " + response.body().toString());
                pd.dismiss();
                myWeatherArrived.clear();
                boolean isMatch = false;
                List<ListItem> weatherDetails = response.body().getList();

                for(int i = 0; i< weatherDetails.size(); i++) {
                    isMatch = false;
                    if(weatherDetails.get(i).getDtTxt() != null && weatherDetails.get(i).getDtTxt().trim().contentEquals(weatherDate.trim())) {
                        Log.d(TAG, "onResponse: Match: " + response.body().getList().get(i).getDtTxt()
                                + " equals " + weatherDate);

                        myWeatherArrived.add(weatherDetails.get(i));
                        isMatch = true;
                        break;
                    }

                }

                if(!isMatch) {
                    Log.d(TAG, "onResponse: Response is null");
                    myWeatherArrived.clear();
                    Toast.makeText(SearchActivity.this, "Data to far ahead.", Toast.LENGTH_SHORT).show();
                    tvWeatherArrivalTemperature.setText("-.-°");
                    tvWeatherArrivalDescription.setText("Weather: ---");
                    tvWeatherArrivalHumidity.setText("Humidity: -.- %");
                    tvWeatherArrivalCity.setText("Arrival City Weather");
                    ivWeatherArrivalIcon.setImageResource(R.drawable.sunny_icon);
                }
                Log.d(TAG, "onResponse: itemNeeded: " + myWeatherLocal.toString());

                String temp = null, desc = null, hum = null, icon = null;
                if(!myWeatherArrived.isEmpty()) {
                    for(int j = 0; j < myWeatherArrived.size(); j++) {
                        int temperature = (int) myWeatherArrived.get(0).getMain().getTemp();
                        temp = String.valueOf(temperature);
                        desc = String.valueOf(myWeatherArrived.get(0).getWeather().get(0).getDescription());
                        hum = String.valueOf(myWeatherArrived.get(0).getMain().getHumidity());
                        icon = String.valueOf(myWeatherArrived.get(0).getWeather().get(0).getIcon());
                    }
                    Log.d(TAG, "onResponse: temperature VALUE: " + temp);

                    tvWeatherArrivalCity.setText(arrCity);
                    tvWeatherArrivalTemperature.setText(temp + "°");
                    tvWeatherArrivalDescription.setText("Weather: " + desc);
                    tvWeatherArrivalHumidity.setText("Humidity: " + hum);
                    Log.d(TAG, "onResponse: icon symbol: " + icon);

                    switch (icon) {
                        case "01d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherArrivalIcon);
                            break;
                        case "02d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherArrivalIcon);
                            break;
                        case "03d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherArrivalIcon);
                            break;
                        case "04d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherArrivalIcon);
                            break;
                        case "04n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherArrivalIcon);
                            break;
                        case "10d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherArrivalIcon);
                            break;
                        case "11d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherArrivalIcon);
                            break;
                        case "13d":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherArrivalIcon);
                            break;
                        case "01n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherArrivalIcon);
                            break;
                        case "02n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherArrivalIcon);
                            break;
                        case "03n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherArrivalIcon);
                            break;
                        case "10n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherArrivalIcon);
                            break;
                        case "11n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherArrivalIcon);
                            break;
                        case "13n":
                            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(ivWeatherArrivalIcon);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.d(TAG, "onFailure: FAIL to get weather");
            }
        });
    }


    public void clickHandler(View view) {
        if(tvDate != null && !tvDate.getText().toString().matches("")) {
            //MAYBE  --- setCoordonates();
            presenterSearch.onButtonClick(view);
        } else {
            if(etFrom.getText().toString().equals("") || etTo.getText().toString().equals(""))
                Toast.makeText(this, "Select Departure or Arrival", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Please select a date.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void setRouteDetails(List<RouteItem> routeItemList) {
        Log.d(TAG, "setRouteDetails: routeList: " + routeItemList.toString());
        String routeID = "", routeName = "";
        for(RouteItem routeItem : routeItemList) {
            routeID = routeItem.getId();
            routeName = routeItem.getRoutename();
        }
        SharedPref.init(this);
        SharedPref.write(SharedPref.ROUTE_ID, routeID);
        SharedPref.write(SharedPref.ROUTE_NAME, routeName);
        if(routeID != null) {
            Intent i = new Intent(SearchActivity.this, TripResultsActivity.class);
            i.putExtra("routeId", routeID);
            i.putExtra("routeName", routeName);
            startActivity(i);
        } else {
            Toast.makeText(this, "No trips found for this route.", Toast.LENGTH_SHORT).show();
        }
        tvDate.setText("");
        tvSearch.setText("");

    }

    void setCoordonates() {
        Log.d(TAG, "setCoordonates: citiItems: " + cityItemList.toString());
        SharedPref.init(this);
        String from = etFrom.getText().toString();
        Log.d(TAG, "setCoordonates: from: " + from);
        String to = etTo.getText().toString();
        Log.d(TAG, "setCoordonates: to: " + to);
        for(CityItem cityItem : cityItemList) {
            if (from != null && to != null) {
                if(from.equals(cityItem.getCityname())){
                    SharedPref.write(SharedPref.CITY_DEPARTURE, cityItem.getCityname());
                    SharedPref.write(SharedPref.LATITUDE_DEPARTURE, cityItem.getCitylatitude());
                    SharedPref.write(SharedPref.LONGITUDE_DEPARTURE, cityItem.getCitylongtitude());
                }
                if(to.equals(cityItem.getCityname())){
                    SharedPref.write(SharedPref.CITY_ARRIVAL, cityItem.getCityname());
                    SharedPref.write(SharedPref.LATITUDE_ARRIVAL, cityItem.getCitylatitude());
                    SharedPref.write(SharedPref.LONGITUDE_ARRIVAL, cityItem.getCitylongtitude());
                }
            }
        }
        Log.d(TAG, "setCoordonates: citiD + lat: "
                + SharedPref.read(SharedPref.CITY_DEPARTURE, "") + " lat: "
                + SharedPref.read(SharedPref.LATITUDE_DEPARTURE, ""));
        Log.d(TAG, "setCoordonates: citiA + lat: "
                + SharedPref.read(SharedPref.CITY_ARRIVAL, "") + " lat: "
                + SharedPref.read(SharedPref.LATITUDE_ARRIVAL, ""));
    }

    @Override
    public void getCityList(List<CityItem> cityItemList) {
        //Log.d(TAG, "getCityList: " + cityItemList.toString());
        this.cityItemList = cityItemList;
        for(CityItem cityItem : cityItemList) {
            cities.add(cityItem.getCityname());
        }
    }
}
