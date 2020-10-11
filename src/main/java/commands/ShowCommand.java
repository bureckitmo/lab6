package commands;

import managers.CollectionManager;
import managers.ConsoleManager;

public class ShowCommand extends AbstractCommand {

    public ShowCommand(){
        cmdName = "show";
        description = "выводит все элементы коллекции";
    }

    @Override
    public Object execute(ConsoleManager consoleManager, CollectionManager collectionManager) {
        consoleManager.writeln("Count: " + collectionManager.getCollection().size());
        StringBuilder sb = new StringBuilder();
        collectionManager.getCollection().forEach(s -> sb.append(s).append("\n"));

        consoleManager.writeln(sb.toString());

        return true;
    }
}
