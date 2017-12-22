package com.logSys.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ytj on 2017/12/18.
 */
public class ExecHelper {

    public static void main(String[] args) {
        InputStreamReader stdISR = null;
        InputStreamReader errISR = null;
        Process process = null;
        String command = "/home/Lance/workspace/someTest/testbash.sh";
        long timeout = 10 * 1000;
        try {
            process = Runtime.getRuntime().exec(command);

            CommandStreamGobbler errorGobbler = new CommandStreamGobbler(process.getErrorStream(), command, "ERR");
            CommandStreamGobbler outputGobbler = new CommandStreamGobbler(process.getInputStream(), command, "STD");

            errorGobbler.start();
            // 必须先等待错误输出ready再建立标准输出
            while (!errorGobbler.isReady()) {
                Thread.sleep(10);
            }
            outputGobbler.start();
            while (!outputGobbler.isReady()) {
                Thread.sleep(10);
            }

            CommandWaitForThread commandThread = new CommandWaitForThread(process);
            commandThread.start();

            long commandTime = new Date().getTime();
            long nowTime = new Date().getTime();
            boolean timeoutFlag = false;
            while (!commandIsFinish(commandThread, errorGobbler, outputGobbler)) {
                if (nowTime - commandTime > timeout) {
                    timeoutFlag = true;
                    break;
                } else {
                    Thread.sleep(100);
                    nowTime = new Date().getTime();
                }
            }
            if (timeoutFlag) {
                // 命令超时
                errorGobbler.setTimeout(1);
                outputGobbler.setTimeout(1);
                System.out.println("正式执行命令：" + command + "超时");
            }else {
                // 命令执行完成
                errorGobbler.setTimeout(2);
                outputGobbler.setTimeout(2);
            }

            while (true) {
                if (errorGobbler.isReadFinish() && outputGobbler.isReadFinish()) {
                    break;
                }
                Thread.sleep(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    private static boolean commandIsFinish(CommandWaitForThread commandThread, CommandStreamGobbler errorGobbler, CommandStreamGobbler outputGobbler) {
        if (commandThread != null) {
            return commandThread.isFinish();
        } else {
            return (errorGobbler.isReadFinish() && outputGobbler.isReadFinish());
        }
    }
}

class CommandStreamGobbler extends Thread {

    private InputStream is;

    private String command;

    private String prefix = "";

    private boolean readFinish = false;

    private boolean ready = false;

    // 命令执行结果,0:执行中 1:超时 2:执行完成
    private int commandResult = 0;

    private List<String> infoList = new LinkedList<String>();

    CommandStreamGobbler(InputStream is, String command, String prefix) {
        this.is = is;
        this.command = command;
        this.prefix = prefix;
    }

    public void run() {
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String line = null;
            ready = true;
            while (commandResult != 1) {
                if (br.ready() || commandResult == 2) {
                    if ((line = br.readLine()) != null) {
                        infoList.add(line);
                    } else {
                        break;
                    }
                } else {
                    Thread.sleep(100);
                }
            }
        } catch (Exception ioe) {
            System.out.println("正式执行命令：" + command + "有IO异常");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException ioe) {
                System.out.println("正式执行命令：" + command + "有IO异常");
            }
            readFinish = true;
        }
    }

    public InputStream getIs() {
        return is;
    }

    public String getCommand() {
        return command;
    }

    public boolean isReadFinish() {
        return readFinish;
    }

    public boolean isReady() {
        return ready;
    }

    public List<String> getInfoList() {
        return infoList;
    }

    public void setTimeout(int timeout) {
        this.commandResult = timeout;
    }
}
class CommandWaitForThread extends Thread {

    private Process process;
    private boolean finish = false;
    private int exitValue = -1;

    public CommandWaitForThread(Process process) {
        this.process = process;
    }

    public void run() {
        try {
            this.exitValue = process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            finish = true;
        }
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public int getExitValue() {
        return exitValue;
    }

}