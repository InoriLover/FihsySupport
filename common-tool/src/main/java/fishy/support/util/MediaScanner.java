package fishy.support.util;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

import java.io.File;
import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * 通知系统的媒体库去更新媒体资料帮助类
 * 解决比如调用系统相机拍照后，相册中并没有实时更新等问题
 * <h3>
 * 编辑记录
 * </h3>
 * <p>
 * 第一次完成<br/>
 * edited by shallowFish on 2018/5/16
 * </p>
 *
 * @author ShallowFish
 * @version 1.0.0
 */

public class MediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {
    MediaScannerConnection mediaScannerConnection;
    File file;
    OnMediaConnectStatusListener onMediaConnectStatusListener;


    public MediaScanner(WeakReference<Context> contextWeak, File file) {
        mediaScannerConnection = new MediaScannerConnection(contextWeak.get(), this);
        this.file = file;
    }

    public void setOnMediaConnectStatusListener(OnMediaConnectStatusListener onMediaConnectStatusListener) {
        this.onMediaConnectStatusListener = onMediaConnectStatusListener;
    }

    @Override
    public void onMediaScannerConnected() {
        if (onMediaConnectStatusListener != null) {
            onMediaConnectStatusListener.onConnected();
        }
        mediaScannerConnection.scanFile(file.getAbsolutePath(), null);

    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        if (onMediaConnectStatusListener != null) {
            onMediaConnectStatusListener.onScanCompleted(path, uri);
        }
        mediaScannerConnection.disconnect();
    }

    /**
     * 一般直接通知就行了，但是如果外界想操作的话，这边给接口回调
     * 仅仅是透传了回调而已
     */
    public interface OnMediaConnectStatusListener {
        void onConnected();

        void onScanCompleted(String path, Uri uri);
    }
}
