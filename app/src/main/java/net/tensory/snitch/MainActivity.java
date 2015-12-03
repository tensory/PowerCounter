package net.tensory.snitch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import net.danlew.android.joda.JodaTimeAndroid;
import net.tensory.snitch.logging.EventsPerDayLoggerFactory;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JodaTimeAndroid.init(this.getApplicationContext());

        // start the PowerEventService
        Intent intent = new Intent(this, PowerEventService.class);
        startService(intent);

        LogViewer.getInstance().initialize(EventsPerDayLoggerFactory.getInstance(this.getApplicationContext()));
        Toast.makeText(this.getApplicationContext(), getString(R.string.txt_startup_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
