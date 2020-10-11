package commands;

import managers.CollectionManager;
import managers.ConsoleManager;
import models.Movie;

public class AddCommand extends AbstractCommand {

    public AddCommand(){
        cmdName = "add";
        description = "добавляет новый элемент в коллекцию";
        needInput = true;
    }

    @Override
    public Object getInput(ConsoleManager consoleManager){
        return consoleManager.getMovie();
    }

    @Override
    public Object execute(ConsoleManager consoleManager, CollectionManager collectionManager) {

        collectionManager.addElement((Movie) inputData);
        consoleManager.writeln("New movie added");

        inputData = null;
        return true;
    }
}
