package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.ConsoleManager;

import java.util.List;

public class HelpCommand extends AbstractCommand {

    public HelpCommand(){
        cmdName = "help";
        description = "выводит список команд и их описание";
    }

    @Override
    public Object execute(ConsoleManager consoleManager, CollectionManager collectionManager) {

        List<AbstractCommand> aCommands = CommandManager.getInstance().getAllCommands();
        for (AbstractCommand cmd: aCommands){
            consoleManager.writeln(cmd.getCmdName() + " - " + cmd.getDescription() );
        }

        return true;
    }
}
