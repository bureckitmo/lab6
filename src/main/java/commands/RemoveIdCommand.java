package commands;

import exceptions.InvalidValueException;
import managers.CollectionManager;
import managers.ConsoleManager;

public class RemoveIdCommand extends AbstractCommand {

    public RemoveIdCommand(){
        cmdName = "remove";
        description = "удаляет элемент из коллекции по его id";
        argCount = 1;
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

        collectionManager.remove(id);
        consoleManager.writeln("Element with id(" + id + ") - successfully deleted");

        return null;
    }
}
