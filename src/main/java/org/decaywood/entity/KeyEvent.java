package org.decaywood.entity;

/**
 * @author: decaywood
 * @date: 2015/6/15 20:36
 */
public class KeyEvent {

    public KeyEvent() {
    }

    public KeyEvent(String altKey) {
        this.altKey = altKey;
    }


    private String altKey;
    private String ctrlKey;
    private String metaKey;
    private String shiftKey;
    private String which;
    private String userID;
    private String highestScore;
    private String IPAddress;

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }



    public String getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(String highestScore) {
        this.highestScore = highestScore;
    }

    public String getWhich() {
        return which;
    }

    public void setWhich(String which) {
        this.which = which;
    }

    public String getShiftKey() {
        return shiftKey;
    }

    public void setShiftKey(String shiftKey) {
        this.shiftKey = shiftKey;
    }

    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(String metaKey) {
        this.metaKey = metaKey;
    }

    public String getCtrlKey() {
        return ctrlKey;
    }

    public void setCtrlKey(String ctrlKey) {
        this.ctrlKey = ctrlKey;
    }

    public String getAltKey() {
        return altKey;
    }

    public void setAltKey(String altKey) {
        this.altKey = altKey;
    }

    @Override
    public String toString() {
        return "KeyEvent{" +
                "altKey='" + altKey + '\'' +
                ", ctrlKey='" + ctrlKey + '\'' +
                ", metaKey='" + metaKey + '\'' +
                ", shiftKey='" + shiftKey + '\'' +
                ", which='" + which + '\'' +
                '}';
    }
}
