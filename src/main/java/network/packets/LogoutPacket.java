package network.packets;

import java.io.Serializable;

public class LogoutPacket extends UserPacket implements Serializable {
    public LogoutPacket(UserPacket user) {
        super(user.getNick());
    }
}