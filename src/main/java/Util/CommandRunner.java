package Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 命令行执行工具类
 *
 * @author linyuqiang
 * @version 1.0.0 2017/4/14
 */
public class CommandRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 命令行执行环境键值
     */
    private Map<String, String> envMap;

    /**
     * 命令行执行工作目录
     */
    private String commandWorkDir;

    public CommandRunner() {
        this.envMap = new HashMap<>();
        this.commandWorkDir = System.getProperty("user.home");
    }

    /**
     * 同步执行，返回执行结果信息
     *
     * @param command
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public CommandResult syncExecute(String command) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(partitionCommandLine(command));
        builder.directory(new File(commandWorkDir));
        builder.environment().putAll(envMap);
        Process process = builder.start();
        InputStream errorStream = process.getErrorStream();
        InputStream inputStream = process.getInputStream();

        String errorLog = catchLog(errorStream);
        String log = catchLog(inputStream);
        logger.info("log = {}", log);
        logger.info("errorLog = {}", errorLog);

        int exitCode = -999;
        try {
            exitCode = process.waitFor();
        } finally {
            process = null;
        }
        return new CommandResult(CommandCode.valueOf(exitCode), log, errorLog);
    }

    /**
     * 异步执行，执行结果通过监听器传递
     *
     * @param command
     * @param commandListener
     */
    public void asyncExecute(String command, CommandListener commandListener) {
        final String commandf = command;
        final CommandListener commandListenerF = commandListener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                CommandResult result = null;
                try {
                    result = syncExecute(commandf);
                    if (commandListenerF != null) {
                        if (result.getExitCode() == CommandCode.SUCCESS) {
                            commandListenerF.succeed(result);
                        } else {
                            commandListenerF.failed(result);
                        }
                    }
                } catch (IOException e) {
                    logger.error("IOException ", e);
                    if (commandListenerF != null) {
                        commandListenerF.exception(result, e);
                    }
                } catch (InterruptedException e) {
                    logger.error("InterruptedException ", e);
                    if (commandListenerF != null) {
                        commandListenerF.exception(result, e);
                    }
                }
            }
        }).start();
    }

    /**
     * 命令行异步执行监听器
     */
    public interface CommandListener {
        void succeed(CommandResult result);

        void failed(CommandResult result);

        void exception(CommandResult result, Exception e);
    }

    /**
     * 返回结果码
     */
    public enum CommandCode {
        SUCCESS(0, "success"),
        FAILED(-999, "failed");
        private int code;
        private String description;

        CommandCode(int code, String description) {
            this.code = code;
            this.description = description;
        }

        @Override
        public String toString() {
            return "CommandCode{" +
                    "code=" + code +
                    ", description='" + description + '\'' +
                    '}';
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static CommandCode valueOf(int code) {
            for (int i = 0; i < values().length; i++) {
                CommandCode ms = values()[i];
                if (ms.getCode() == code) {
                    return ms;
                }
            }
            return null;
        }
    }

    /**
     * 命令行执行结果信息
     */
    public static class CommandResult {
        private String log;
        private String errorLog;
        private CommandCode exitCode;

        @Override
        public String toString() {
            return "CommandResult{" +
                    "log='" + log + '\'' +
                    ", errorLog='" + errorLog + '\'' +
                    '}';
        }

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }

        public String getErrorLog() {
            return errorLog;
        }

        public void setErrorLog(String errorLog) {
            this.errorLog = errorLog;
        }

        public CommandCode getExitCode() {
            return exitCode;
        }

        public void setExitCode(CommandCode exitCode) {
            this.exitCode = exitCode;
        }

        public CommandResult(CommandCode exitCode, String log, String errorLog) {
            this.exitCode = exitCode;
            this.log = log;
            this.errorLog = errorLog;
        }
    }

    /**
     * 从流读取成功或错误信息
     *
     * @param is
     * @return
     */
    private String catchLog(InputStream is) {
        StringBuilder log = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info(line);
                log.append(line).append("\n");
            }
            return log.toString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("接收日志出错，推出日志接收");
            return "接收日志出错，退出日志接收";
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                reader = null;
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                is = null;
            }
        }
    }

    /**
     * Splits the command into a unix like command line structure. Quotes and
     * single quotes are treated as nested strings.
     *
     * @param command
     * @return
     */
    private String[] partitionCommandLine(String command) {

        ArrayList<String> commands = new ArrayList<String>();

        String os = System.getProperties().getProperty("os.name");
        if (os != null && (os.startsWith("win") || os.startsWith("Win"))) {
            commands.add("CMD.EXE");
            commands.add("/C");
            commands.add(command);
        } else {
            int index = 0;

            StringBuffer buffer = new StringBuffer(command.length());

            boolean isApos = false;
            boolean isQuote = false;
            while (index < command.length()) {
                char c = command.charAt(index);

                switch (c) {
                    case ' ':
                        if (!isQuote && !isApos) {
                            String arg = buffer.toString();
                            buffer = new StringBuffer(command.length() - index);
                            if (arg.length() > 0) {
                                commands.add(arg);
                            }
                        } else {
                            buffer.append(c);
                        }
                        break;
                    case '\'':
                        if (!isQuote) {
                            isApos = !isApos;
                        } else {
                            buffer.append(c);
                        }
                        break;
                    case '"':
                        if (!isApos) {
                            isQuote = !isQuote;
                        } else {
                            buffer.append(c);
                        }
                        break;
                    default:
                        buffer.append(c);
                }

                index++;
            }

            if (buffer.length() > 0) {
                String arg = buffer.toString();
                commands.add(arg);
            }
        }
        return commands.toArray(new String[commands.size()]);
    }

    public Map<String, String> getEnvMap() {
        return envMap;
    }

    public void setEnvMap(Map<String, String> envMap) {
        this.envMap = envMap;
    }

    public String getCommandWorkDir() {
        return commandWorkDir;
    }

    public void setCommandWorkDir(String commandWorkDir) {
        this.commandWorkDir = commandWorkDir;
    }
}
