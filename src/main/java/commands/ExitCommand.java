package commands;

import managers.CollectionManager;
import managers.ConsoleManager;

public class ExitCommand extends AbstractCommand {

    public ExitCommand(){
        cmdName = "exit";
        description = "выход из программы без сохранения";
    }

    @Override
    public Object execute(ConsoleManager consoleManager, CollectionManager collectionManager) {
        System.exit(0);

        return true;
    }
}
