package pl.lizard.project.fluffyunicorns.admin;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Zlatan on 2014-09-06.
 */
public class FluffyDeviceAdminReceiver extends DeviceAdminReceiver {

	@Override
	public void onEnabled(Context context, Intent intent) {
	}

	@Override
	public CharSequence onDisableRequested(Context context, Intent intent) {
		return "Receiver status disable warning";
	}

	@Override
	public void onDisabled(Context context, Intent intent) {
	}
}