import commands.AbstractCommand;
import exceptions.InvalidValueException;
import exceptions.NoCommandException;
import managers.CollectionManager;
import managers.CommandManager;
import managers.ConsoleManager;
import utils.AppConstant;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
    public static void main(String[] args) {
        ConsoleManager consoleManager = new ConsoleManager(new InputStreamReader(System.in), new OutputStreamWriter(System.out), false);
        consoleManager.writeln("Use help command for information");


        String path = AppConstant.FILE_PATH;
        CollectionManager collectionManager = new CollectionManager(path);


        consoleManager.write("> ");
        while (consoleManager.hasNextLine()) {

            String cmd = consoleManager.read().trim();
            if(cmd.equals("")) continue;

            try {
                AbstractCommand command = CommandManager.getInstance().parseCommand(cmd);
                if (command.getNeedInput()) command.setInputData(command.getInput(consoleManager));
                command.execute(consoleManager, collectionManager);

            }catch (NoCommandException ex) {
                consoleManager.writeln("Command not found :(\nEnter help command");
            }catch (NumberFormatException | ClassCastException ex){
                consoleManager.writeln("Cast error: " + ex.getMessage());
            } catch (InvalidValueException ex){
                consoleManager.writeln(ex.getMessage());
            }finally {
                consoleManager.write("> ");
            }
        }
    }
}
