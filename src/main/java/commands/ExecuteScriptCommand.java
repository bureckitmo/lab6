package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.ConsoleManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExecuteScriptCommand extends AbstractCommand {

    private boolean executeFault = false;
    private int executeCount = 0;

    public ExecuteScriptCommand(){
        cmdName = "execute_script";
        description = "выполняет команды из скрипта";
        argCount = 1;
    }


    @Override
    public Object execute(ConsoleManager consoleManager, CollectionManager collectionManager) {
        executeCount++;
        executeFault = false;
        if(executeCount == 127){ executeCount = 0; throw new StackOverflowError(); }

        Path pathToScript = Paths.get(args[0]);
        consoleManager.writeln("Идет выполнение скрипта: " + pathToScript.getFileName());
        int lineNum = 1;
        try {
            ConsoleManager _consoleManager = new ConsoleManager(new FileReader(pathToScript.toFile()), new OutputStreamWriter(System.out), true);
            for (lineNum=1; _consoleManager.hasNextLine(); lineNum++) {
                String line = _consoleManager.read().trim();
                if(line.equals("")) continue;

                if(!executeFault) CommandManager.getInstance().parseCommand(line).execute(consoleManager, collectionManager);
            }
            //consoleManager.writeln("Скрипт выполнен.");
        } catch (FileNotFoundException e) {
            executeFault = true;
            consoleManager.writeln("File not found.");
        }catch (StackOverflowError | NullPointerException ex){
            executeFault = true;
            consoleManager.writeln("StackOverflow, execution terminated");
        }

        return true;
    }
}
