package network.packets;

import java.io.Serializable;

public class LoginPacket extends UserPacket implements Serializable {
    public LoginPacket(UserPacket user) {
        super(user.getNick());
    }
}