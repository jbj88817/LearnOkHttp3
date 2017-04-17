package us.bojie.learnokhttp.model;

/**
 * Created by bojiejiang on 4/16/17.
 */

public class BaseResult {

    private int success;
    private String message;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
