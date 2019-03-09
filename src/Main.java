import java.io.*;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        //prepare
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("/n");
        while (true) {
            System.out.println("请输入命令!!!");
            String cmd = sc.nextLine();
            if (cmd == null || cmd.trim().length() == 0) {
                System.err.println("命令不能为空:" + cmd + ":");
                continue;
            }
            try {
                String result = execCmd(cmd.trim());
                System.out.println("执行结果:" + result);
            } catch (Exception e) {
                System.err.println("执行错误:" + e.toString());
            }
        }
    }

    public static String execCmd(String cmd) throws Exception {
        String[] cmdA = {"/bin/sh", "-c", cmd};
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        //2776104960:--:189267968:-:187275640
        System.out.println(maxMemory + ":--:" + totalMemory + ":-:" + freeMemory);
        Process process = runtime.exec(cmdA);
        LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        process.destroy();
        return sb.toString();
    }

}
