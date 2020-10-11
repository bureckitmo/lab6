package network.packets;

import java.io.Serializable;

public class UserPacket implements Serializable {
    private final String nick;

    public UserPacket(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }
}