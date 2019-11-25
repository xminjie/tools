import java.io.*;

import static java.lang.Integer.max;


  class Encry3 {
    //传递两个参数（文件名（必填），密码（默认为0）, 几分钟后删除（默认2分钟,负数和0不删除））


    private static byte[] passwd = new byte[8];

    //处理文件名。包括隐藏文件
    public static String getGoalPath(File file) {
        String goalFilePath;
        if (file.getName().charAt(0) == '.') {
            if (file.getName().length() >= 8 && file.getName().substring(0, 8).equals(".encry3-")) {
                int len = file.getName().length();
                goalFilePath = file.getParent() + "/" + "." + file.getName().substring(8);
            } else {
                goalFilePath = file.getParent() + "/.encry3-" + file.getName().substring(1);
            }

        } else {
            if (file.getName().length() >= 7 && file.getName().substring(0, 7).equals("encry3-")) {
                int len = file.getName().length();
                goalFilePath = file.getParent() + "/" + file.getName().substring(7);
            } else {
                goalFilePath = file.getParent() + "/encry3-" + file.getName();
            }
        }
        return goalFilePath;
    }


    //    对单个文件加密或者解密, 加密过的->解密， 原文件 ->加密
    public static void encry3(File file) {

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
                    b[i] = (byte) (b[i] ^ passwd[i%8]);
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
            encry3(file);
        }
    }

    public static void main(String[] args) {
        String homePath;
        File homefile;

        //参数校验
        if (args.length == 2) {
            homePath = args[0];

            //累加法
            for (int i = 0; i < args[1].length(); i++) {
                passwd[0] += (byte) (args[1].charAt(i) - '0');
                passwd[0] = (byte) (passwd[0] * passwd[0] * passwd[0]);
            }
            //平方累加区域法；
            for (int i = 0; i < args[1].length(); i++) {
                passwd[1] += (byte) ((args[1].charAt(i) - '0') * (args[1].charAt(i) - '0'));
                passwd[1] = (byte) (passwd[1] * passwd[1] * passwd[1]);
            }
            //立方累加区域法；
            for (int i = 0; i < args[1].length(); i++) {
                passwd[2] += (byte) ((args[1].charAt(i) - '0') * (args[1].charAt(i) - '0') * (args[1].charAt(i) - '0'));
                passwd[2] = (byte) (passwd[2] * passwd[2] * passwd[2]);
            }
            //连城法 ；
            for (int i = 0; i < args[1].length(); i++) {
                passwd[3] *= (byte) (max((args[1].charAt(i) - '0'), 1));
                passwd[3] = (byte) (passwd[3] * passwd[3] * passwd[3]);
                passwd[3] = (byte) (max(passwd[3], 1) + 22);
            }
            //平方连城法区域法；
            for (int i = 0; i < args[1].length(); i++) {
                passwd[4] *= (byte) (args[1].charAt(i) - '0') * (args[1].charAt(i) - '0');
                passwd[4] = (byte) (passwd[4] * passwd[4] * passwd[4]);
                passwd[4] = (byte) (max(passwd[4], 1) + 35);
            }
            //取余连城法区域法；
            for (int i = 0; i < args[1].length(); i++) {
                passwd[5] *= (byte) (((args[1].charAt(i) - '0') * (args[1].charAt(i) - '0') % 98));
                passwd[5] = (byte) (passwd[5] * passwd[5] * passwd[5]);
                passwd[5] = (byte) (max(passwd[5], 1) + 94);

            }
            //累加法 * 字符串长度
            for (int i = 0; i < args[1].length(); i++) {
                passwd[6] += (byte) (args[1].charAt(i) - '0') * (args[1].length());
                passwd[6] = (byte) (passwd[6] * passwd[6] * passwd[6]);
            }
            //累加法 +平方
            for (int i = 0; i < args[1].length(); i++) {
                passwd[7] += (byte) (args[1].charAt(i) - '0') + (args[1].charAt(i) - '0') * (args[1].charAt(i) - '0');
                passwd[7] = (byte) (passwd[7] * passwd[7] * passwd[7]);
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

