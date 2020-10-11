package network.packets;

import java.io.Serializable;

public class LoginFailedPacket implements Serializable {
    private final String message;

    public LoginFailedPacket(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}