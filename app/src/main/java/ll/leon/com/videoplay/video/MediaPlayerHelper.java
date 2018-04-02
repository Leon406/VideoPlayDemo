package ll.leon.com.videoplay.video;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

public class MediaPlayerHelper {

    private MediaPlayer mMediaPlayer;
    public boolean isPause = false;

    private static class Holder {
        private static final MediaPlayerHelper instance = new MediaPlayerHelper();
    }

    private MediaPlayerHelper() {
    }

    public static MediaPlayerHelper getInstance() {
        return Holder.instance;
    }

    public  MediaPlayer getMediaPlayer() {
        return mMediaPlayer == null ? new MediaPlayer() : mMediaPlayer;
    }

    public void playAudio(String path) {
        playAudio(path, false, null);
    }

    public void playUrlAudio(String path) {
        playAudio(path, true, null);
    }

    public void playAudio(String path, boolean isNet, MediaPlayer.OnCompletionListener onCompletionListener) {
        Log.e("playAudio()", "播放: " + path);
        if (mMediaPlayer == null) {
            mMediaPlayer = getMediaPlayer();
        mMediaPlayer.reset();
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mMediaPlayer.reset();
                return false;
            }
        });
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.e("MediaPlayerHelper", "onPrepared()");
                mp.start();
            }
        });
        } else {
            mMediaPlayer.reset();
        }
        try {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            if (onCompletionListener != null)
                mMediaPlayer.setOnCompletionListener(onCompletionListener);
            mMediaPlayer.setDataSource(path);
            if (isNet)
                mMediaPlayer.prepareAsync();
            else
                mMediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停播放
     */
    public void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            isPause = true;
        }
    }

    /**
     * 继续播放
     */
    public void resume() {
        if (mMediaPlayer != null && isPause) {
            mMediaPlayer.start();
            isPause = false;
        }
    }

    /**
     * 释放资源
     */
    public void realese() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            isPause = true;
        }
    }

    /**
     * 释放资源
     */
    public int getDuration() {
        return mMediaPlayer != null ? mMediaPlayer.getDuration() : 0;
    }

    /**
     * 释放资源
     */
    public void seekTo(int msec) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(msec);

        }
    }

}