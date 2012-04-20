package play.youtube;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity8 extends Activity {

    // onCreate���\�b�h(��ʏ����\���C�x���g)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���w��
        setContentView(R.layout.video_player8);

        // ���ȉ��̂ǂ��炩���R�����g�A�E�g���Ďg�p���Ă��������B
        // ***************************************
        // MediaPlayer�N���X���g�p��������Đ�
        // useMediaPlayer();
        // ***************************************
        // VideoPlayer�N���X���g�p��������Đ�
        useVideoPlayer();
        // ***************************************

    }

    // useVideoPlayer���\�b�h�iVideoPlayer�N���X���g�p��������Đ�����)
    private void useVideoPlayer() {
        VideoView vvPlayer = (VideoView) findViewById(R.id.vv_player);

        // �����URI���擾
        String urlString =
            getIntent().getStringExtra(
                PlaytubeSample8Activity.IntentKey.MEDIA_URL.name());

        // �����URI��ݒ�
        vvPlayer.setVideoURI(Uri.parse(urlString));

        // ����^�C�g�����擾
        String title =
            getIntent().getStringExtra(
                PlaytubeSample8Activity.IntentKey.MEDIA_TITLE.name());

        // �^�C�g���o�[�ɓ���^�C�g����ݒ�
        setTitle(title);

        // ���f�B�A�R���g���[����ݒ�
        vvPlayer.setMediaController(new MediaController(this));

        // ������Đ�
        vvPlayer.start();
    }

    // useMediaPlayer���\�b�h�iMediaPlayer�N���X���g�p��������Đ������j
    private void useMediaPlayer() {
        // ���f�B�A�v���[���[�C���^���X����
        final MediaPlayer mp = new MediaPlayer();

        // �����URL���擾
        final String urlString =
            getIntent().getStringExtra(
                PlaytubeSample8Activity.IntentKey.MEDIA_URL.name());

        // �T�[�t�F�C�X�r���[�擾
        final SurfaceView sv = (SurfaceView) findViewById(R.id.vv_player);

        // �T�[�t�F�C�X�z���_�擾
        final SurfaceHolder sh = sv.getHolder();
        sh.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        // ���f�B�A�v���[���[�ɃT�[�t�F�C�X�z���_�ݒ�
        mp.setDisplay(sh);

        sh.addCallback(new Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // �����~
                mp.stop();
                mp.release();
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {

                    // �����URL��ݒ�
                    mp.setDataSource(urlString);

                    // ����Đ�����
                    mp.prepare();

                    // ���������16:9�ɕϊ�
                    int w = getWindowManager().getDefaultDisplay().getWidth();
                    int h = w / 16 * 9;
                    sv.layout(0, 0, w, h);

                    // ����Đ�
                    mp.start();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                int width, int height) {
            }
        });
    }

}
