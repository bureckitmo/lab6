package network.packets;

import java.io.Serializable;

public class CommandExecutionPacket implements Serializable {

    private final String message;
    public CommandExecutionPacket(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}