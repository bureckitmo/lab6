package commands;

import managers.CollectionManager;
import managers.ConsoleManager;

public class ClearCommand extends AbstractCommand {

    public ClearCommand(){
        cmdName = "clear";
        description = "очищает коллекцию";
    }

    @Override
    public Object execute(ConsoleManager consoleManager, CollectionManager collectionManager) {
        collectionManager.clear();
        consoleManager.writeln("All elements deleted");

        return true;
    }
}
