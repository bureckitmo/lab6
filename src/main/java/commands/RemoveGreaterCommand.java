package commands;

import managers.CollectionManager;
import managers.ConsoleManager;
import models.Movie;

public class RemoveGreaterCommand extends AbstractCommand {

    public RemoveGreaterCommand(){
        cmdName = "remove_greater";
        description = "удаляет из коллекции все элементы, больше, чем заданный";
        needInput = true;
    }

    @Override
    public Object getInput(ConsoleManager consoleManager){
        return consoleManager.getMovie();
    }

    @Override
    public Object execute(ConsoleManager consoleManager, CollectionManager collectionManager) {
        int initSize = collectionManager.getCollection().size();
        collectionManager.removeGreater((Movie) inputData);
        int afterSize = collectionManager.getCollection().size();

        consoleManager.writeln("Deleted " + (initSize - afterSize) + " elements");

        inputData = null;

        return true;
    }
}
