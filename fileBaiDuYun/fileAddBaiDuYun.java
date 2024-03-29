import java.io.*;
import java.util.Date;

class fileAddBaiDuYun {
    public static int flagDelete = 1;
    public static void AddSomeUseLessByte(String filePath) {
        String goalFilePath = new File(filePath).getParent().toString() + "/Add-" + new File(filePath).getName();
        try {
            FileInputStream fins = new FileInputStream(filePath);
            FileOutputStream fous = new FileOutputStream(goalFilePath);


            //读取准备
            int size = 1024 * 1024;
            int len = -1;
            byte[] b = new byte[size];
            //读取和写入
            while ((len = fins.read(b)) != -1) {
                fous.write(b, 0, len);
            }

            int addLength = 20;
            byte[] add = new byte[20];
            for (int i = 0; i < 20; i++) {
                add[i] = (byte) (Math.random() * 100);
            }
            fous.write(add, 0, addLength);
            //关闭输入输出流
            fous.flush();
            fins.close();
            fous.close();
            System.out.println(new File(filePath).getName() + "  添加干扰字节成功 ");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("readstream occur fault");
        }


    }

    public static void dfs(String filePath){
        File f = new File(filePath);
        if(f.isFile()) {
            if(!f.getName().substring(0,4).equals("Add-")){
                AddSomeUseLessByte(filePath);
                if(flagDelete == 0){
                    f.delete();
                }
            }
        }else if(f.isDirectory()){
            File[] fileArrary = f.listFiles();
            for(int i = 0; i < fileArrary.length; i++){
                dfs(fileArrary[i].toString());
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("fieAddBaiDuYun 文件路径 [0表示删除原文件]");
        String filePath = args[0];
        if(args.length > 1 && args[1].equals("0")) flagDelete = 0;
        dfs(filePath);
        if(flagDelete == 0){
            System.out.println("原文件已删除");
        }else{
            System.out.println("原文件保留");
        }
    }
}
L=aBB	b IK (<_IJ6D