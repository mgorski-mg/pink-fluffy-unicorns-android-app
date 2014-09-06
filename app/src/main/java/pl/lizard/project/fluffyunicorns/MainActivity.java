package pl.lizard.project.fluffyunicorns;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import pl.lizard.project.fluffyunicorns.admin.FluffyDeviceAdminReceiver;

/**
 * Created by Zlatan on 2014-09-05.
 */
public class MainActivity extends Activity {

	private static final int REQ_ACTIVATE_DEVICE_ADMIN = 42;

	private ComponentName mPolicyAdmin;
	private DevicePolicyManager mDPM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPolicyAdmin = new ComponentName(getApplicationContext(), FluffyDeviceAdminReceiver.class);

		mDPM = (DevicePolicyManager) getApplicationContext().getSystemService(Context.DEVICE_POLICY_SERVICE);

		if (!mDPM.isAdminActive(mPolicyAdmin)) {
			Intent activateDeviceAdminIntent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			activateDeviceAdminIntent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mPolicyAdmin);

			// It is good practice to include the optional explanation text to
			// explain to user why the application is requesting to be a device
			// administrator. The system will display this message on the activation
			// screen.
			activateDeviceAdminIntent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getResources().getString(R.string.device_admin_activation_message));
			startActivityForResult(activateDeviceAdminIntent, REQ_ACTIVATE_DEVICE_ADMIN);
		}
	}
}
