import java.io.*;
import java.lang.reflect.Method;
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
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String lineTxt = null;
            while ((lineTxt = reader.readLine()) != null) {
                browserUrlWindow(lineTxt);
            }
            reader.close();
        } catch (Exception e) {
            System.err.println(":--:" + e.toString());
        }
    }


    public static void browserUrlWindow(String url) throws Exception {
//        if (java.awt.Desktop.isDesktopSupported()) {
//            // 创建一个URI实例
//            java.net.URI uri = java.net.URI.create(url);
//            // 获取当前系统桌面扩展
//            java.awt.Desktop dp = java.awt.Desktop.getDesktop();
//            // 判断系统桌面是否支持要执行的功能
//            if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
//                // 获取系统默认浏览器打开链接
//                dp.browse(uri);
//            }
//        } else {
//            System.err.println("不支持浏览器!");
//        }


        // 获取操作系统的名字
        String osName = System.getProperty("os.name", "");
        System.out.println("OsName:" + osName);
        if (osName.startsWith("Mac OS")) {
            // 苹果的打开方式
            Class fileMgr = Class.forName("com.apple.eio.FileManager");
            Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[]{String.class});
            openURL.invoke(null, new Object[]{url});
        } else if (osName.startsWith("Windows")) {
            // windows的打开方式。
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
        } else if (osName.startsWith("Linux")) {
            // 这个值在上面已经成功的得到了一个进程。
            Process exec = Runtime.getRuntime().exec(new String[]{"google-chrome", url});
            OutputStream os = exec.getOutputStream();
            InputStream errorStream = exec.getErrorStream();
            InputStream inputStream = exec.getInputStream();
            int errAvailable = errorStream.available();
            byte[] errBytes = new byte[errAvailable];
            if (errorStream.read(errBytes) != -1) {
                String errStr = new String(errBytes);
                System.out.println(":errStr--:" + errStr + ":");
            }
            int available = inputStream.available();

            byte[] bytes = new byte[available];
            if (inputStream.read(bytes) != -1) {
                String str = new String(errBytes);
                System.out.println(":--:" + str + ":");
            }
            System.out.println(errAvailable + ":----:" + available);
            os.close();
            errorStream.close();
            inputStream.close();
        }

    }

}
