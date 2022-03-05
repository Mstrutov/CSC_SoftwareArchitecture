package execution.commands;

import execution.ResultCode;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Cat linux command class.
 */
public class Cat implements BuiltInCmd {
    @Override
    public ResultCode execute(String[] args, StringBuilder buffer) {
        if (args.length == 0 && buffer.isEmpty()) {
            System.err.println("Cat required at least 1 argument or non-empty buffer");
            return new ResultCode(1, false);
        }
        try (BufferedReader br = new BufferedReader(buffer.isEmpty() ? new FileReader(args[0]) : new StringReader(buffer.toString()));) {
            buffer.setLength(0);
            String line;
            List<String> result = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
            buffer.append(result.stream().collect(Collectors.joining(System.lineSeparator())));
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            return new ResultCode(1, false);
        } catch (IOException e) {
            System.err.println("IOException");
            e.printStackTrace();
            return new ResultCode(1, false);
        } finally {

        }
        return new ResultCode(0, false);
    }
}
