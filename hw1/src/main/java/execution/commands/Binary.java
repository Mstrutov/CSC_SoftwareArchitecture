package main.java.execution.commands;

import main.java.execution.ResultCode;

public interface Binary {
    ResultCode execute(String[] args, StringBuilder buffer);
}
