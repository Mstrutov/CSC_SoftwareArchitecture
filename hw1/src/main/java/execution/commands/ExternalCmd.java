package execution.commands;

import execution.ResultCode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * External command class
 */
public class ExternalCmd implements Binary {
    private final String command;

    /**
     * Constructs {@code ExternalCmd} class
     *
     * @param command Name of the command
     */
    public ExternalCmd(String command) {
        this.command = command;
    }

    /**
     * Get command name of this class
     *
     * @return Command name
     */
    public String getCommand() {
        return command;
    }

    @Override
    public ResultCode execute(String[] args, StringBuilder buffer) {
        try {
            Process process = Runtime.getRuntime().exec(command + ' ' + String.join(" ", args));
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitVal = process.waitFor();
            return new ResultCode(exitVal, false);
        } catch (IOException e) {
            System.err.println("IOException while executing external command");
            return new ResultCode(1, false);
        } catch (InterruptedException e) {
            System.err.println("Process interrupted");
            return new ResultCode(1, false);
        }
    }
}
