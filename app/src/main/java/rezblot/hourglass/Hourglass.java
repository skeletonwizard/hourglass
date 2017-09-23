package rezblot.hourglass;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import junit.framework.Assert;

import java.util.Timer;
import java.util.TimerTask;

public class Hourglass extends Activity {//extends AppCompatActivity {


    private Stopwatch _stopwatch;
    //private long _fallingSand = 0;
    private long _sand = 0;
    private Timer _timer;

    private final String KEY_SAND = "KEY_SAND";
    private final String KEY_SHARED_PREFERENCES = "KEY_SHARED_PREFERENCES";
    private final String KEY_STATE_DOCUMENT = "STONE_TABLET";

    private boolean _isPositive = true;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle savedPersistentState) {
        super.onCreate(savedInstanceState, savedPersistentState);

        _stopwatch = new Stopwatch();
        _timer  = new Timer();

        setContentView(R.layout.activity_demo);

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(KEY_SHARED_PREFERENCES, 0);
        _sand = settings.getLong(KEY_SAND, 0);

        UpdateSandViews();

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        InitializeListeners();
    }

    private void InitializeListeners() {
        final FloatingActionButton startStopButton = (FloatingActionButton) findViewById(R.id.StartStopButton);
        final FloatingActionButton upDownButton = (FloatingActionButton) findViewById(R.id.UpDownButton);

        try {
            startStopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (_stopwatch.isRunning()) {
                            startStopButton.setImageResource(android.R.drawable.button_onoff_indicator_off);
                            //grey out & disable upDownButton
                            upDownButton.show();


                            StopHourglass();
                        } else {
                            startStopButton.setImageResource(android.R.drawable.button_onoff_indicator_on);
                            upDownButton.hide();
                            StartHourglass();
                        }

                    } catch (Exception ex) {
                        System.out.print(ex.getMessage());
                    }
                }
            });

            upDownButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (!_stopwatch.isRunning()) {
                            if (_isPositive) {
                                upDownButton.setImageResource(android.R.drawable.arrow_down_float);
                                _isPositive = false;
                            } else {
                                upDownButton.setImageResource(android.R.drawable.arrow_up_float);
                                _isPositive = true;
                            }
                        }
                    } catch (Exception ex) {
                        System.out.print(ex.getMessage());
                    }
                }
            });

        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
    }

    ////When does this call occur?
    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    //@Override
    //public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
    //{
    //    outState.putLong(KEY_STATE_DOCUMENT, _sand);
    //    outPersistentState.putLong(KEY_STATE_DOCUMENT, _sand);
    //    super.onSaveInstanceState(outState, outPersistentState);
    //}

    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    //@Override
    //public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState)
    //{
    //    super.onRestoreInstanceState(savedInstanceState, persistentState);
    //    if (persistentState != null)
    //    {
    //        _sand = persistentState.getLong(KEY_STATE_DOCUMENT, 0);
    //    }
    //    else if(savedInstanceState != null)
    //    {
    //        _sand = savedInstanceState.getLong(KEY_STATE_DOCUMENT, 0);
    //    }
    //    else
    //    {
    //        _sand = 20;
    //    }
    //
    //    UpdateSandViews();
    //}

    @Override
    protected void onPause()
    {
        super.onPause();

        //Supposedly onStop doesn't get called all the time.
        SaveSandToPreferences();
    }

    //See https://developer.android.com/guide/topics/data/data-storage.html#pref
    @Override
    protected void onStop(){
        super.onStop();

        SaveSandToPreferences();
    }

    private void SaveSandToPreferences()
    {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(KEY_SHARED_PREFERENCES, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(KEY_SAND, _sand);

        // Commit the edits!
        editor.commit();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_demo, menu);
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


    public String ReadSand() {
        return String.valueOf(this.CombineSand());
    }

    public String ReadFallingSand() {
        return String.valueOf(_stopwatch.getSeconds());
    }

    public void UpdateSandViews()
    {
        TextView fallingSandView = (TextView)findViewById(R.id.FallingSand);
        TextView sandView = (TextView)findViewById(R.id.Sand);

        fallingSandView.setText(ReadFallingSand());
        sandView.setText(ReadSand());
    }

    public long CombineSand()
    {
        //Assert.assertEquals(!_stopwatch.isRunning(), true);

        if(!_stopwatch.isRunning())
        {
            long fallenSand = _stopwatch.getSeconds();

            _sand = _isPositive
                ? _sand + fallenSand
                : _sand - fallenSand;
        }

        return _sand;
    }



    public void StartHourglass()
    {
        int msRefresh = 1000;

        try{
            _stopwatch.start();


            final Runnable updateSandViewsTask = new Runnable() {
                public void run() {
                    UpdateSandViews();
                }
            };

            TimerTask updateSandViewsTimerTask = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(updateSandViewsTask);
                }
            };

            //More elegant way of doing this ??
            _timer = new Timer();
            _timer.schedule(updateSandViewsTimerTask, msRefresh, msRefresh);
        }
        catch(Exception ex)
        {
            System.out.print(ex.getMessage());
        }
    }

    public void StopHourglass()
    {
        try{
            if(_stopwatch.isRunning())
            {
                //These need to happen at the exact same time?
                //Will need to address this or deal with slightly inaccurate ms readings
                _stopwatch.stop();
                _timer.cancel();

                UpdateSandViews();
            }
        }
        catch(Exception ex)
        {
            System.out.print(ex.getMessage());
        }
    }
}
