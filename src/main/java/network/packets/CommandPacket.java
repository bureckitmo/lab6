package network.packets;

import java.io.Serializable;

public class CommandPacket implements Serializable {

    private String cmdName;
    private String[] args;

    public CommandPacket(String cmdName, String[] args){
        this.cmdName = cmdName;
        this.args = args;
    }

    public String getCmdName() {
        return cmdName;
    }

    public String[] getArgs() {
        return args;
    }
}