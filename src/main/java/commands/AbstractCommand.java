package commands;

import managers.*;

import java.io.Serializable;

/**
 * Абстрактный класс для команд
 */
public abstract class AbstractCommand implements Serializable {
    int argCount = 0;
    String[] args;
    String cmdName;
    String description;
    boolean needInput = false;
    Object inputData = null;

    public AbstractCommand(){}
    /**
     *
     * @param consoleManager управление консолью
     * @param collectionManager управление коллекцией
     */
    public abstract Object execute(ConsoleManager consoleManager, CollectionManager collectionManager);

    public Object getInput(ConsoleManager consoleManager){
        return null;
    }


    public int getArgCount(){ return argCount; }
    public String getCmdName() {
        return cmdName;
    }
    public String[] getArgs(){ return this.args; }
    public String getDescription() {
        return description;
    }
    public boolean getNeedInput(){ return needInput; }

    public void setArgs(String[] args){ this.args = args; }
    public void setInputData(Object inputData){ this.inputData = inputData; }

    public boolean isNumeric(String strNum) {
        if (strNum == null)
            return false;
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}