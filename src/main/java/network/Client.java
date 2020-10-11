package network;

import commands.AbstractCommand;
import commands.ExecuteScriptCommand;
import commands.ExitCommand;
import commands.SaveCommand;
import exceptions.InvalidValueException;
import exceptions.NoCommandException;
import exceptions.SelfCallingScriptException;
import lombok.extern.log4j.Log4j2;
import managers.CommandManager;
import managers.ConsoleManager;
import network.packets.*;
import utils.AppConstant;
import utils.Serializer;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

@Log4j2
public class Client {
    private HashSet<String> executePaths = new HashSet<>();
    private boolean executeFault = false;
    private int executeCount = 0;

    private DatagramSocket socket;
    private InetAddress IPAddress;
    private int PORT;
    private UserPacket userPacket;
    private ConsoleManager consoleManager;
    private boolean isConnected = false;
    private boolean isLogin = false;
    private int tryConnect = 2;

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        new Client(args).run();
    }

    public Client(String[] args) throws IOException {
        connect(args);
    }

    private void connect(String host, int port) throws IOException {
        PORT = port;
        IPAddress = InetAddress.getByName( host );
        socket = new DatagramSocket();
        socket.setSoTimeout(3000);
    }

    private void connect(String[] args) throws IOException {
        try {
            if (args.length >= 2) {
                connect(args[0], Integer.parseInt(args[1]));
            } else if (args.length == 1) {
                String[] hostAndPort = args[0].split(":");
                if (hostAndPort.length != 2) {
                    throw new InvalidValueException("");
                }
                connect(hostAndPort[0], Integer.parseInt(hostAndPort[1]));
            } else {
                connect("localhost", AppConstant.DEFAULT_PORT);
            }
        }catch (Exception ex){
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }

    public void run() throws IOException, ClassNotFoundException, InterruptedException {
        consoleManager = new ConsoleManager(new InputStreamReader(System.in), new OutputStreamWriter(System.out), false);

        while (true) {
            consoleManager.write("Введите имя: ");
            if(consoleManager.hasNextLine()) {
                String str = consoleManager.read();
                if (!str.isEmpty()) {
                    this.userPacket = new UserPacket(str);
                    send(new LoginPacket(this.userPacket));
                    consoleManager.writeln("Waiting to connect to the server...");
                    objectHandler(recv());
                    if(isLogin) break;
                }
            }
        }


        tryConnect = 1;
        while (isConnected) {
            consoleManager.write("> ");
            if (consoleManager.hasNextLine()) {
                executePaths.clear();
                executeFault = false;
                executeCount = 0;

                sendCommand(consoleManager.read(), consoleManager);
            }
        }
    }

    private void sendCommand(String sCmd, ConsoleManager cMgr) throws IOException, ClassNotFoundException {
        if(sCmd.isEmpty() || executeFault) return;
        try {
            AbstractCommand cmd = CommandManager.getInstance().parseCommand(sCmd);
            if(cmd instanceof ExitCommand){ send(new LogoutPacket(this.userPacket)); socket.disconnect(); isConnected = false; }
            else if(cmd instanceof SaveCommand){ cMgr.writeln("Команда не работает в клиентской части."); }
            else if(cmd instanceof ExecuteScriptCommand){

                executeCount++;
                if(executeCount == 127) throw new StackOverflowError();
                /*if(executePaths.contains(cmd.getArgs()[0])) {
                    executeFault = true;
                    throw new SelfCallingScriptException("Рекурсивный вызов запрещен");
                }*/

                Path pathToScript = Paths.get(cmd.getArgs()[0]);
                //executePaths.add(cmd.getArgs()[0]);

                int lineNum = 1;
                try {
                    cMgr = new ConsoleManager(new FileReader(pathToScript.toFile()), new OutputStreamWriter(System.out), true);
                    for (lineNum=1; cMgr.hasNextLine(); lineNum++) {
                        String line = cMgr.read().trim();
                        if(!line.isEmpty() && !executeFault) { sendCommand(line, cMgr); }
                    }
                } catch (FileNotFoundException ex) {
                    executeFault = true;
                    consoleManager.writeln("Файла не найден.");
                    log.error(ex.getMessage());
                }catch (SelfCallingScriptException ex){
                    consoleManager.writeln(ex.getMessage());
                    log.error(ex.getMessage());
                }catch (StackOverflowError ex){
                    if(!executeFault) {
                        consoleManager.writeln("Стек переполнен, выполнение прервано");
                    }

                    executeFault = true;
                }catch (Exception ex){
                    executeFault = true;
                    consoleManager.writeln(ex.getMessage());
                    log.error(ex.getMessage());
                }

            }
            else {
                if (cmd.getNeedInput()) cmd.setInputData(cmd.getInput(cMgr));
                send(cmd);
                objectHandler(recv());
            }
        }catch (NoCommandException ex) {
            cMgr.writeln("Такая команда не найдена :(\nВведите команду help, чтобы вывести спискок команд");
            log.error(ex.getMessage());
        }catch (NumberFormatException|ClassCastException ex){
            cMgr.writeln("Ошибка во время каста\n" + ex.getMessage());
            log.error(ex.getMessage());
        } catch (InvalidValueException ex){
            cMgr.writeln(ex.getMessage());
            log.error(ex.getMessage());
        }
    }


    private void objectHandler(Object obj){
        if(obj != null) {
            if (obj instanceof LoginSuccessPacket) {
                isConnected = true;
                isLogin = true;
                consoleManager.writeln(((LoginSuccessPacket) obj).getMessage());
            } else if (obj instanceof LoginFailedPacket) {
                isConnected = true;
                isLogin = false;
                consoleManager.writeln(((LoginFailedPacket) obj).getMessage());
            } else if (obj instanceof CommandExecutionPacket) {
                consoleManager.writeln(((CommandExecutionPacket) obj).getMessage());
            }
        }
    }

    private void send(Object obj) throws IOException {

        byte[] data = Serializer.Serialize(obj);
        final ByteBuffer buf = ByteBuffer.wrap(data);

        try {
            DatagramPacket packet = new DatagramPacket(buf.array(), buf.array().length, IPAddress, PORT);
            socket.send(packet);
        }catch (IOException ex){
            consoleManager.writeln(ex.getMessage());
            log.error(ex.getMessage());
        }

    }

    private Object recv() throws ClassNotFoundException, IOException {
        Object out = null;
        try {
            byte[] receiveData = new byte[AppConstant.MESSAGE_BUFFER];
            DatagramPacket received = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(received);
            if (received.getLength() != 0) {
                out = Serializer.Deserialize(received.getData());
            }

        }catch (SocketTimeoutException ex){
            if(tryConnect == 0) System.exit(1);

            tryConnect--;
            consoleManager.writeln("Failed to connect");
            log.error(ex.getMessage());
        }

        return out;
    }

}