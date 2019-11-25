import java.io.*;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Date;

 class Encry2 {
    //传递两个参数（文件名（必填），密码（默认为0）, 几分钟后删除（默认2分钟,负数和0不删除））


    private static byte passwd;

    //处理文件名。包括隐藏文件
    public static String getGoalPath(File file) {
        String goalFilePath;
        if (file.getName().charAt(0) == '.') {
            if (file.getName().length() >= 7 && file.getName().substring(0, 7).equals(".encry-")) {
                int len = file.getName().length();
                goalFilePath = file.getParent() + "/" + "." + file.getName().substring(7);
            } else {
                goalFilePath = file.getParent() + "/.encry-" + file.getName().substring(1);
            }

        } else {
            if (file.getName().length() >= 6 && file.getName().substring(0, 6).equals("encry-")) {
                int len = file.getName().length();
                goalFilePath = file.getParent() + "/" + file.getName().substring(6);
            } else {
                goalFilePath = file.getParent() + "/encry-" + file.getName();
            }
        }
        return goalFilePath;
    }


    //    对单个文件加密或者解密, 加密过的->解密， 原文件 ->加密
    public static void encry(File file) {

        //判断文件情况
        String goalFilePath = getGoalPath(file);

        //加密算法
        try {
            FileInputStream fins = new FileInputStream(file);
            FileOutputStream fous = new FileOutputStream(goalFilePath);

            int size = 1024 * 1024;
            byte[] b = new byte[size];
            int len = -1;
            while ((len = fins.read(b)) != -1) {
                for (int i = 0; i < size; i++) {
                    b[i] = (byte) (b[i] ^ passwd);
                }
                fous.write(b, 0, len);
            }

            fous.flush();
            fins.close();
            fous.close();

            //删除旧文件
            System.out.println("创建成功: " + goalFilePath);
            file.delete();
            System.out.println("删除成功: " + file.getName());

        } catch (IOException e) {
            System.out.println("readstream occur fault");
        }

        return;
    }


    public static void dfs(File file) {
        //判断情况，递归文件夹
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                dfs(f);
            }
            String goalFilePath = getGoalPath(file);
            file.renameTo(new File(goalFilePath));
        } else {
            encry(file);
        }
    }

    public static void main(String[] args) {
        String homePath;
        File homefile;

        //参数校验
        if (args.length == 2) {
            homePath = args[0];
            passwd = 0;
            for (int i = 0; i < args[1].length(); i++) {
                passwd += (byte) ((args[1].charAt(i) - '0') * (args[1].charAt(i) - '0'));
            }
            try {
                homefile = new File(homePath);

            } catch (Exception e) {
                System.out.println("文件名有误");
                return;
            }
        } else {
            System.out.println("请输正确文件名和密码，例如： /home/test passwd1");
            return;
        }

        dfs(homefile);


    }
}

