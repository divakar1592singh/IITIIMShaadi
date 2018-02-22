package com.senzecit.iitiimshaadi.chat;

/**
 * Created by senzec on 22/2/18.
 */


public class Message {

    public static final int TYPE_MESSAGE = 0;
    public static final int TYPE_LOG = 1;
    public static final int TYPE_ACTION = 2;

    private int mType;



    private String profileUrl;
    private String mMessage;
    private String mUsername;



    private String mUserId;

    private Message() {}

    public int getType() {
        return mType;
    };

    public String getProfileUrl() {
        return profileUrl;
    }
    public String getMessage() {
        return mMessage;
    };

    public String getUsername() {
        return mUsername;
    };

    public String getUserId() {
        return mUserId;
    }

    public static class Builder {
        private final int mType;
        private String profileUrl;
        private String mUserId;
        private String mUsername;
        private String mMessage;
        private String getmUserId;

        public Builder(int type) {
            mType = type;
        }

        public Builder profileUrl(String profileUrl) {
            this.profileUrl = profileUrl;
            return this;
        }
        public Builder username(String username) {
            mUsername = username;
            return this;
        }
        public Builder userId(String userid) {
            mUserId = userid;
            return this;
        }

        public Builder message(String message) {
            mMessage = message;
            return this;
        }

        public Message build() {
            Message message = new Message();
            message.mType = mType;
            message.profileUrl = profileUrl;
            message.mUserId = mUserId;
            message.mUsername = mUsername;
            message.mMessage = mMessage;
            return message;
        }
    }
}
