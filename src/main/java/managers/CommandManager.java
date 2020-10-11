package managers;

import commands.*;
import exceptions.InvalidValueException;
import exceptions.NoCommandException;

import java.util.*;

public class CommandManager {
    private static CommandManager instance;

    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    private Map<String, AbstractCommand> commands = new HashMap<>();

    public CommandManager(){
        addCommand(new AddCommand());
        addCommand(new HelpCommand());
        addCommand(new RemoveIdCommand());
        addCommand(new ShowCommand());
        addCommand(new UpdateIdCommand());
        //addCommand(new SaveCommand());
        addCommand(new ExitCommand());
        addCommand(new AddIfMinCommand());
        addCommand(new FilterByOperatorCommand());
        addCommand(new InfoCommand());
        addCommand(new PrintFieldAscendingMpaaCommand());
        addCommand(new PrintFieldDescendingMpaaCommand());
        addCommand(new RemoveGreaterCommand());
        addCommand(new RemoveLowerCommand());
        addCommand(new ExecuteScriptCommand());
        addCommand(new ClearCommand());
    }

    private void addCommand(AbstractCommand cmd){
        commands.put(cmd.getCmdName(), cmd);
    }

    public AbstractCommand getCommand(String s) throws NoCommandException {
        if (!commands.containsKey(s)) {
            throw new NoCommandException("Command not found");
        }
        return commands.get(s);
    }

    public AbstractCommand parseCommand(String str){
        AbstractCommand cmd = null;
        String[] parse = str.trim().split("\\s+");
        cmd = getCommand(parse[0].toLowerCase());
        String[] args = Arrays.copyOfRange(parse, 1, parse.length);
        if(cmd.getArgCount() == args.length)
            cmd.setArgs(args);
        else throw new InvalidValueException("Entered " + args.length + " args, expected " + cmd.getArgCount());

        return cmd;
    }

    /**
     * Выполняет команды введенные пользователем
     * @param str
     * @param consoleManager
     * @param collectionManager
     */
    public void execute(String str, ConsoleManager consoleManager, CollectionManager collectionManager){
        //parseCommand(str).execute(consoleManager, collectionManager);
    }


    public List<AbstractCommand> getAllCommands() {
        return new ArrayList<>(commands.values());
    }
}
