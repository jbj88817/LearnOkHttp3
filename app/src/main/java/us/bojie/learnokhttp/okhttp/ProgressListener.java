package us.bojie.learnokhttp.okhttp;

/**
 * Created by bojiejiang on 4/16/17.
 */

public interface ProgressListener {

    public void onProgress(int progress);
    public void onDone(long totalSize);
}
