package commands;

import managers.CollectionManager;
import managers.ConsoleManager;
import models.Movie;
import models.Person;

import java.util.ArrayList;

public class FilterByOperatorCommand extends AbstractCommand {

    public FilterByOperatorCommand(){
        cmdName = "filter_by_operator";
        description = "выводит элементы, значение поля operator которых равно заданному";
        needInput = true;
    }

    @Override
    public Object getInput(ConsoleManager consoleManager){
        return consoleManager.getPerson();
    }

    @Override
    public Object execute(ConsoleManager consoleManager, CollectionManager collectionManager) {
        ArrayList<Movie> out = collectionManager.findOperator((Person) inputData);
        if(out.size() == 0) consoleManager.writeln("Records doesn't exist");
        out.forEach(x-> consoleManager.writeln(x.toString()));

        return true;
    }
}
