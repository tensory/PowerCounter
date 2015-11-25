package net.tensory.powercounter.logging;

import android.content.Context;
import android.util.Log;

import net.tensory.powercounter.logging.notification.LogSubscriber;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Records a log of an event in a file, storing data about the current day.
 */
public class EventsPerDayLogger implements Logger {
    private static final String FILENAME = "log.txt";
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private Context context;

    private List<LogSubscriber> subscribers;

    public EventsPerDayLogger(Context context) {
        this.context = context;
        subscribers = new ArrayList<>();
    }

    @Override
    public void subscribe(LogSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void logEvent(Date eventTime) {
        File file = prepareFile();

        DateTime lastLoggedDate = null;
        int lastLoggedEventCount = 0;

        // obtain the date and the count
        try {
            String line;
            int lineCount = 0;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                if (lineCount % 2 == 0) {
                    lastLoggedEventCount = Integer.parseInt(line);
                } else {
                    lastLoggedDate = new DateTime(getDate(line)).withTimeAtStartOfDay();
                }

                lineCount += 1;
            }

            reader.close();

        } catch (FileNotFoundException fnfe) {
            Log.e(EventsPerDayLogger.class.getName(), fnfe.getMessage(), fnfe);
        } catch (IOException ioe) {
            Log.e(EventsPerDayLogger.class.getName(), ioe.getMessage(), ioe);
        } catch (ParseException pe) {
            Log.e(EventsPerDayLogger.class.getName(), pe.getMessage(), pe);
        }

        DateTime today = new DateTime().withTimeAtStartOfDay();
        if (lastLoggedDate != null) {
            // compare today's date to lastLoggedDate
            if (today.isEqual(lastLoggedDate)) {
                lastLoggedEventCount += 1;
            } else {
                lastLoggedEventCount = 1;
            }
        } else {
            // initialize the file's values
            lastLoggedEventCount = 1;
        }

        try {
            DateTimeFormatter ddmmyy = DateTimeFormat.forPattern(DATE_FORMAT);
            persist(file, String.valueOf(lastLoggedEventCount), ddmmyy.print(today));

            for (LogSubscriber subscriber : subscribers) {
                subscriber.update(lastLoggedEventCount);
            }
        } catch (IOException ioe) {
            Log.e(EventsPerDayLogger.class.getName(), "Could not write file " + FILENAME, ioe);
        }
    }

    private File prepareFile() {
        File file = new File(context.getFilesDir(), FILENAME);
        if (!file.exists()) {
            OutputStream out;
            try {
                out = new BufferedOutputStream(new FileOutputStream(file));
                out.close();
                return file;
            } catch (IOException ioe) {
                return null;
            }
        }
        return file;
    }

    private DateTime getDate(String string) throws ParseException {
        DateTimeFormatter ddmmyy = DateTimeFormat.forPattern(DATE_FORMAT);
        return ddmmyy.parseDateTime(string);
    }

    private static void persist(File file, String count, String date) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        writer.write(count);
        writer.newLine();
        writer.write(date);

        writer.flush();
        writer.close();
    }
}