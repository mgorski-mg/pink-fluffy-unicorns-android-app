package pl.lizard.project.fluffyunicorns.music.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import pl.lizard.project.fluffyunicorns.music.service.MusicService;

/**
 * Created by Zlatan on 2014-09-05.
 */
public class DeviceStartBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
			Toast.makeText(context, "MusicService Started", Toast.LENGTH_LONG).show();
			Log.d("Unicorn","MusicService Started");
			context.startService(new Intent(context, MusicService.class));
		} else {
			Log.d("Unicorn","Other intent received");
			Toast.makeText(context, "Other intent received", Toast.LENGTH_LONG).show();
		}
	}
}