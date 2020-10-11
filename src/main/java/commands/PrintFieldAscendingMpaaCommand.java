package commands;

import managers.CollectionManager;
import managers.ConsoleManager;

public class PrintFieldAscendingMpaaCommand extends AbstractCommand {

    public PrintFieldAscendingMpaaCommand(){
        cmdName = "print_field_ascending_mpaa_rating";
        description = "выводит значения поля Mpaa_Rating всех элементов в порядке возрастания";
    }

    @Override
    public Object execute(ConsoleManager consoleManager, CollectionManager collectionManager) {
        collectionManager.sortByMpaaAsc().forEach(x->consoleManager.writeln(x.toString()));

        return true;
    }
}
