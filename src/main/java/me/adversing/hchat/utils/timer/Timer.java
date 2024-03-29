package me.adversing.hchat.utils.timer;

public interface Timer {
    boolean isActive(boolean autoReset);

    boolean isActive();

    String formattedExpiration();

    void reset();
}