package pl.lizard.project.fluffyunicorns.broadcast;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.IOException;

import pl.lizard.project.fluffyunicorns.R;
import pl.lizard.project.fluffyunicorns.music.service.MusicService;

/**
 * Created by Zlatan on 2014-09-05.
 */
public class FluffyBroadcastReceiver extends BroadcastReceiver {

	private static MediaPlayer player;

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		Log.d("Unicorn", action + " received");

//		if (action.equals(Intent.ACTION_BOOT_COMPLETED) || action.equals(Intent.ACTION_POWER_CONNECTED)) {
		context.startService(new Intent(context, MusicService.class));

		if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
			stopPlayer();
		} else {
			setVolumeToMax(context);
			startPlayer(context);
			setFluffyWallpaper(context);
		}
	}

	private void setFluffyWallpaper(Context context) {
		WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
		try {
			DisplayMetrics metrics = context.getResources().getDisplayMetrics();
			if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				wallpaperManager.suggestDesiredDimensions(metrics.widthPixels, metrics.heightPixels);
			} else {
				wallpaperManager.suggestDesiredDimensions(metrics.heightPixels, metrics.widthPixels);
			}
			wallpaperManager.setResource(R.raw.fluffy_wallpaper);
			Log.d("Unicorn", "Wallpaper changed");
		} catch (IOException e) {
			Log.d("Unicorn", "Can't change wallpaper");
		}
	}

	private void startPlayer(Context context) {
		player = MediaPlayer.create(context, R.raw.pink_fluffy_unicorns);
		if (player != null) {
			player.setLooping(false);
			player.setVolume(1, 1);
			player.setAudioStreamType(AudioManager.STREAM_MUSIC);
			player.start();
			Log.d("Unicorn", "Player started");
		}
	}

	private void setVolumeToMax(Context context) {
		AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_SHOW_UI);
	}

	private void stopPlayer() {
		if (player != null) {
			try {
				player.stop();
				player.release();
				Log.d("Unicorn", "Player stopped");
			} finally {
				player = null;
			}
		}
	}
}