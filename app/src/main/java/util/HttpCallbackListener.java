package util;

public interface HttpCallbackListener {
    void onFinsh(String response);
    void onError(Exception e);
}
