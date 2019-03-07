import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("/n");
        System.out.println("请输入路径？");
        String path = sc.nextLine();
        load(path);
        System.out.println("执行结束");
    }

    public static void load(String fileName) {
        File file = new File(fileName);

        if (!file.exists()) {
            System.err.println("不存在该文件: " + fileName);
            return;
        }
        File errorFile = new File(fileName + "_err.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            errorFile.createNewFile();
            PrintWriter writer = new PrintWriter(new FileWriter(errorFile));
            String lineTxt = null;
            boolean hasError = false;
            while ((lineTxt = reader.readLine()) != null) {
                if (!browserUrl(lineTxt)) {
                    hasError = true;
                    writer.println(lineTxt);
                }
            }
            reader.close();
            writer.close();
            if (!hasError) {
                errorFile.delete();
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }


    public static boolean browserUrl(String url) {
        try {
            Runtime.getRuntime().exec("cmd   /c   start  " + url);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
        return true;
    }

}
