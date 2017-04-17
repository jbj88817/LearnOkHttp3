package us.bojie.learnokhttp.model;

import java.io.Serializable;

/**
 * Created by bojiejiang on 4/16/17.
 */

public class User implements Serializable {

    private String username;
    private String id;
    private String hearUrl;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHearUrl() {
        return hearUrl;
    }

    public void setHearUrl(String hearUrl) {
        this.hearUrl = hearUrl;
    }
}
