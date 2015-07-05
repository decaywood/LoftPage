package org.decaywood.entity;

/**
 * @author: decaywood
 * @date: 2015/6/15 20:36
 */
public class KeyEvent {

    public KeyEvent() {
    }

    private String gameState;

    private String userID;
    private String highestScore;
    private String altKey;
    private String ctrlKey;
    private String metaKey;
    private String shiftKey;
    private String which;
    private String currentNum;
    private String expectNum;

    private String IPAddress;
    private String randomTiles;

    public String getRandomTiles() {
        return randomTiles;
    }

    public void setRandomTiles(String randomTiles) {
        this.randomTiles = randomTiles;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    public String getExpectNum() {
        return expectNum;
    }

    public void setExpectNum(String expectNum) {
        this.expectNum = expectNum;
    }

    public String getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(String currentNum) {
        this.currentNum = currentNum;
    }

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

    public void copyOf(KeyEvent keyEvent) {
        this.gameState = keyEvent.gameState;

        this.userID = keyEvent.userID;
        this.highestScore = keyEvent.highestScore;
        this.altKey = keyEvent.altKey;
        this.ctrlKey = keyEvent.ctrlKey;
        this.metaKey = keyEvent.metaKey;
        this.shiftKey = keyEvent.shiftKey;
        this.which = keyEvent.which;
        this.currentNum = keyEvent.currentNum;
        this.expectNum = keyEvent.expectNum;

        this.IPAddress = keyEvent.IPAddress;
        this.randomTiles = keyEvent.randomTiles;
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
