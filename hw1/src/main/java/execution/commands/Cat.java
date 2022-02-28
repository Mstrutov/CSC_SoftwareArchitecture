package execution.commands;

import execution.ResultCode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Cat implements BuiltInCmd {
    @Override
    public ResultCode execute(String[] args, StringBuilder buffer) {
        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
                buffer.append(System.getProperty("line.separator"));
            }
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
