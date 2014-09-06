package pl.lizard.project.fluffyunicorns.music.service;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;

import pl.lizard.project.fluffyunicorns.R;

/**
 * Created by Zlatan on 2014-09-05.
 */
public class MusicService extends Service implements MediaPlayer.OnErrorListener {

	private final IBinder mBinder = new ServiceBinder();
	private MediaPlayer player;
	private WallpaperManager wallpaperManager;

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
		wallpaperManager = WallpaperManager.getInstance(this);
		player = MediaPlayer.create(this, R.raw.pink_fluffy_unicorns);
		player.setOnErrorListener(this);

		if (player != null) {
			player.setLooping(true);
			player.setVolume(1, 1);
		}

		player.setOnErrorListener(new OnErrorListener() {
			public boolean onError(MediaPlayer mp, int what, int extra) {

				onError(player, what, extra);
				return true;
			}
		});
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		player.start();
		try {
			wallpaperManager.setResource(R.raw.pink_fluffy_unicorn_wallpaper);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (player != null) {
			try {
				player.stop();
				player.release();
			} finally {
				player = null;
			}
		}
	}

	public boolean onError(MediaPlayer mp, int what, int extra) {
		Toast.makeText(this, "music player failed", Toast.LENGTH_SHORT).show();
		if (player != null) {
			try {
				player.stop();
				player.release();
			} finally {
				player = null;
			}
		}
		return false;
	}
}