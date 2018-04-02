package ll.leon.com.videoplay.video;

import android.media.MediaPlayer;
import android.view.SurfaceView;

import java.io.IOException;

public class AudioVideoPlayer {
    private MediaPlayer mediaPlayer ;

    public boolean isPause;

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    private AudioVideoPlayer() {
        mediaPlayer = new MediaPlayer();
    }

    public static AudioVideoPlayer getInstance() {
        return Holder.INSTANCE;
    }

    public static class Holder {
        static AudioVideoPlayer INSTANCE = new AudioVideoPlayer();
    }
    public void initPlayer(String path, MediaPlayer.OnPreparedListener listener,
                           MediaPlayer.OnCompletionListener onCompletionListener) {

        initPlayer(path,listener,onCompletionListener,false,null);


    }
    public void initPlayer(String path, MediaPlayer.OnPreparedListener listener,
                           MediaPlayer.OnCompletionListener onCompletionListener
            , boolean isVideoPlayer,SurfaceView surfaceView

    ) {
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.setOnPreparedListener(listener);
            mediaPlayer.setOnCompletionListener(onCompletionListener);
            if (isVideoPlayer && surfaceView != null) {
                mediaPlayer.setDisplay(surfaceView.getHolder());
            }

            mediaPlayer.prepareAsync();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 暂停播放
     */
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPause = true;
        }
    }
    /**
     * 暂停播放
     */
    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.reset();

            isPause = false;
        }
    }

    /**
     * 继续播放
     */
    public void resume() {
        if (mediaPlayer != null && isPause) {
            mediaPlayer.start();
            isPause = false;
        }
    }

    /**
     * 释放资源
     */
    public void realese() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            isPause = true;
        }
    }

    /**
     * 释放资源
     */
    public int getDuration() {
        return mediaPlayer != null ? mediaPlayer.getDuration() : 0;
    }

    /**
     * 释放资源
     */
    public void seekTo(int msec) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(msec);
            isPause = false;
        }
    }
}
