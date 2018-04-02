package ll.leon.com.videoplay;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.VideoView;

import java.net.URI;

public class Main2Activity extends Activity {

    private VideoView video;
    private EditText et;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initView();
    }

    private void initView() {
        et = (EditText) findViewById(R.id.et1);
        video = (VideoView) findViewById(R.id.video);
        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory().getPath()+"/"+et.getText().toString();//获取视频路径
                Uri uri = Uri.parse(path);//将路径转换成uri
                video.setVideoURI(uri);//为视频播放器设置视频路径
                video.setMediaController(new MediaController(Main2Activity.this));//显示控制栏
                video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        video.start();//开始播放视频
                    }
                });
            }
        });

    }
}
