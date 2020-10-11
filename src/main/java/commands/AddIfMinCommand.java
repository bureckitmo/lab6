package commands;

import managers.CollectionManager;
import managers.ConsoleManager;
import models.Movie;

public class AddIfMinCommand extends AbstractCommand {

    public AddIfMinCommand(){
        cmdName = "add_if_min";
        description = "добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
        needInput = true;
    }

    @Override
    public Object getInput(ConsoleManager consoleManager){
        return consoleManager.getMovie();
    }

    @Override
    public Object execute(ConsoleManager consoleManager, CollectionManager collectionManager) {
        if(collectionManager.addIfMin((Movie) inputData))
            consoleManager.writeln("New item added");
        else
            consoleManager.writeln("The item has not added. The element larger minimum");

        inputData = null;
        return null;
    }
}
