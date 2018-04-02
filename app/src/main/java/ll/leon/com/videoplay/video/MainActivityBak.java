package ll.leon.com.videoplay.video;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import ll.leon.com.videoplay.R;

/**
 * 需要注意的是我实验了.mp4,3gp和avi3种格式的视频，在5.1的真机和模拟器上avi格式都是只有声音没有影像，其他两种格式
 * 播放正常。
 */

public class MainActivityBak extends Activity {

    public static final String TAG = "LeonTag";
    private VideoView sfv;//能够播放图像的控件
    private SeekBar sb;//进度条
    private String path ;//本地文件路径
    private MediaPlayer player;//媒体播放器
    private Button play;//播放按钮
    private Timer timer;//定时器
    private TimerTask task;//定时器任务
    private int position = 0;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    //初始化控件，并且为进度条和图像控件添加监听
    private void initView() {
        sfv = (VideoView) findViewById(R.id.sfv);
        sb = (SeekBar) findViewById(R.id.sb);
        play = (Button) findViewById(R.id.play);
        et = (EditText) findViewById(R.id.et);
        play.setEnabled(false);


        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //当进度条停止拖动的时候，把媒体播放器的进度跳转到进度条对应的进度
                if (player != null) {
                    player.seekTo(seekBar.getProgress());
                }
            }
        });

        sfv.setListener(new VideoView.VideoListener() {
            @Override
            public void onStart(SurfaceHolder holder) {
                Log.d(TAG,"onStart");
                play.setEnabled(true);
            }

            @Override
            public void onChange(SurfaceHolder holder, int format, int width, int height) {
                Log.d(TAG,"onChange");
            }

            @Override
            public void onDestroyed(SurfaceHolder holder) {
                Log.d(TAG,"onDestroyed");
                if (player != null) {
                    position = player.getCurrentPosition();
                    stop();
                }
            }
        });


    }

    private void play() {

        play.setEnabled(false);//在播放时不允许再点击播放按钮

        if (isPause) {//如果是暂停状态下播放，直接start
            isPause = false;
            player.start();
            return;
        }

        path = Environment.getExternalStorageDirectory().getPath()+"/";
        path = path + et.getText().toString();//sdcard的路径加上文件名称是文件全路径
        File file = new File(path);
        if (!file.exists()) {//判断需要播放的文件路径是否存在，不存在退出播放流程
            Toast.makeText(this,"文件路径不存在",Toast.LENGTH_LONG).show();
            return;
        }

        path= "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";
        try {
            player = new MediaPlayer();
            player.setDataSource(path);
            player.setDisplay(sfv.getHolder());//将影像播放控件与媒体播放控件关联起来

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {//视频播放完成后，释放资源
                    play.setEnabled(true);
                    stop();
                }
            });

            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    //媒体播放器就绪后，设置进度条总长度，开启计时器不断更新进度条，播放视频
                    Log.d("zhangdi","onPrepared");
                    sb.setMax(player.getDuration());
                    timer = new Timer();
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            if (player != null) {
                                int time = player.getCurrentPosition();
                                sb.setProgress(time);
                            }
                        }
                    };
                    timer.schedule(task,0,500);
                    sb.setProgress(position);
                    player.seekTo(position);
                    player.start();
                }
            });

            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play(View v) {
        play();
        Log.d("zhangdi",path);
    }

    private boolean isPause;
    private void pause() {
        if (player != null && player.isPlaying()) {
            player.pause();
            isPause = true;
            play.setEnabled(true);
        }
    }

    public void pause(View v) {
        pause();
    }

    private void replay() {
        isPause = false;
        if (player != null) {
            stop();
            play();
        }
    }

    public void replay(View v) {
        replay();
    }

    private void stop(){
        isPause = false;
        if (player != null) {
            sb.setProgress(0);
            player.stop();
            player.release();
            player = null;
            if (timer != null) {
                timer.cancel();
            }
            play.setEnabled(true);
        }
    }

    public void stop(View v) {
        stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
    }
}
