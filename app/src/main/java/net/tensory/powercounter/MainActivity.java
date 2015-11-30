package net.tensory.powercounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import net.danlew.android.joda.JodaTimeAndroid;
import net.tensory.powercounter.logging.EventsPerDayLoggerFactory;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @Bind(R.id.event_count)
    TextView tvEventCount;
    @Bind(R.id.times_today)
    TextView tvTimesToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        JodaTimeAndroid.init(this.getApplicationContext());

        // start the PowerEventService
        Intent intent = new Intent(this, PowerEventService.class);
        startService(intent);

        LogViewer.getInstance().initialize(EventsPerDayLoggerFactory.getInstance(this.getApplicationContext()));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onResume() {
        super.onResume();

        tvEventCount.setText(String.valueOf(LogViewer.getInstance().getCount()));
        tvTimesToday.setText(getResources().getQuantityString(R.plurals.times_today, LogViewer.getInstance().getCount()));
    }
}
