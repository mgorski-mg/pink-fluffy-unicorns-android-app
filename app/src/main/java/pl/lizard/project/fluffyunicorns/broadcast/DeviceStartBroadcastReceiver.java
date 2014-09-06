package pl.lizard.project.fluffyunicorns.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import pl.lizard.project.fluffyunicorns.music.service.MusicService;

/**
 * Created by Zlatan on 2014-09-05.
 */
public class DeviceStartBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		if (action.equals(Intent.ACTION_BOOT_COMPLETED) || action.equals(Intent.ACTION_POWER_CONNECTED) || action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
			context.startService(new Intent(context, MusicService.class));
			Log.d("Unicorn", action + " received");
		} else {
			Log.d("Unicorn", "Other intent received");
		}
	}
}