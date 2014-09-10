package pl.lizard.project.fluffyunicorns.music.service;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import pl.lizard.project.fluffyunicorns.R;

/**
 * Created by Zlatan on 2014-09-05.
 */
public class MusicService extends Service implements MediaPlayer.OnErrorListener {

	private final IBinder mBinder = new ServiceBinder();
	private MediaPlayer player;
	private final Timer timer = new Timer();

	public class ServiceBinder extends Binder {

		MusicService getService() {
			return MusicService.this;
		}
	}

	@Override
	public IBinder onBind(Intent arg) {
		return mBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		timer.scheduleAtFixedRate(new FluffyTimerTask(), new Date(114, 8, 11, 12, 0, 0), 12 * 60 * 60 * 1000);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	private class FluffyTimerTask extends TimerTask {

		@Override
		public void run() {
			setVolumeToMax(MusicService.this);
			startPlayer(MusicService.this);
			setFluffyWallpaper(MusicService.this);
			Log.d("UnicornService", "Timer fired");
		}
	}

	private void startPlayer(Context context) {
		player = MediaPlayer.create(context, R.raw.pink_fluffy_unicorns);
		if (player != null) {
			player.setLooping(false);
			player.setVolume(1, 1);
			player.setAudioStreamType(AudioManager.STREAM_MUSIC);
			player.start();
			Log.d("UnicornService", "Player started");
		}
	}

	private void setVolumeToMax(Context context) {
		AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_SHOW_UI);
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
			Log.d("UnicornService", "Wallpaper changed");
		} catch (IOException e) {
			Log.d("UnicornService", "Can't change wallpaper");
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopPlayer();
	}

	public boolean onError(MediaPlayer mp, int what, int extra) {
		stopPlayer();
		return false;
	}

	private void stopPlayer() {
		if (player != null) {
			try {
				player.stop();
				player.release();
			} finally {
				player = null;
			}
		}
	}
}