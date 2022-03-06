package execution.commands;

import execution.ResultCode;

import java.io.*;

/**
 * Wc linux command class.
 */
public class Wc implements BuiltInCmd {
    @Override
    public ResultCode execute(String[] args, StringBuilder buffer) {

        if (args.length == 0 && buffer.isEmpty()) {
            System.err.println("Wc required at least 1 argument or non-empty buffer");
            return new ResultCode(1, false);
        }
        try (BufferedReader br =
                     new BufferedReader(buffer.isEmpty() ? new FileReader(args[0]) : new StringReader(buffer.toString()))) {
            buffer.setLength(0);
            int lineCounter = 0;
            while (br.readLine() != null) {
                lineCounter++;
            }
            buffer.append(lineCounter);
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            return new ResultCode(1, false);
        } catch (IOException e) {
            System.err.println("IOException");
            e.printStackTrace();
            return new ResultCode(1, false);
        }
        return new ResultCode(0, false);
    }
}
