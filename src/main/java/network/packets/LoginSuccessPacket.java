package network.packets;

import java.io.Serializable;

public class LoginSuccessPacket implements Serializable {

    private final String message;

    public LoginSuccessPacket(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}