package fpt.dungnm.assignment.models;

import java.io.Serializable;

public class Avatar implements Serializable {
    private int avatarId;

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public Avatar(int avatarId) {
        this.avatarId = avatarId;
    }
}
