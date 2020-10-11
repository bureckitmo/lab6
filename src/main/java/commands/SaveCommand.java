package commands;

import managers.CollectionManager;
import managers.ConsoleManager;

public class SaveCommand extends AbstractCommand {

    public SaveCommand(){
        cmdName = "save";
        description = "сохраняет коллекцию в csv файл";
    }

    @Override
    public Object execute(ConsoleManager consoleManager, CollectionManager collectionManager) {
        collectionManager.getManager().saveList(collectionManager.getCollection(), consoleManager);

        return true;
    }
}
