package ll.leon.com.videoplay.video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class VideoView extends SurfaceView implements SurfaceHolder.Callback {
    public VideoView(Context context) {
        this(context,null);
    }

    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context  context) {
        getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        getHolder().addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        listener.onStart(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        listener.onChange(holder,format,width,height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        listener.onDestroyed(holder);
    }

    public void setListener(VideoListener listener) {
        this.listener = listener;
    }

    private VideoListener listener;
    public interface VideoListener {
        void onStart(SurfaceHolder holder);
        void onChange(SurfaceHolder holder, int format, int width, int height);
        void onDestroyed(SurfaceHolder holder);

    }
}
