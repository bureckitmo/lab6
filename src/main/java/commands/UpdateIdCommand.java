package commands;

import exceptions.InvalidValueException;
import managers.CollectionManager;
import managers.ConsoleManager;
import models.Movie;

public class UpdateIdCommand extends AbstractCommand {

    public UpdateIdCommand(){
        cmdName = "update";
        description = "обновляет значение элемента коллекции";
        argCount = 1;
        needInput = false;
    }

    @Override
    public Object getInput(ConsoleManager consoleManager){
        return consoleManager.getMovie();
    }

    @Override
    public Object execute(ConsoleManager consoleManager, CollectionManager collectionManager) {
        int id;
        try {
            id = Integer.parseInt(args[0]);
        } catch (Exception e) {
            throw new InvalidValueException("Invalid id");
        }

        if(!collectionManager.checkIdExist(id))
            throw new InvalidValueException("Element with id(" + id + ") - doesn't exist");

        if(inputData == null) inputData = this.getInput(consoleManager);
        collectionManager.update((Movie) inputData, id);
        consoleManager.writeln("Element with id(" + id + ") - edited");

        inputData = null;
        return true;
    }
}
