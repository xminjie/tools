import java.io.*;

class fileRemoveBaiDuYun {
    public static int flagDelete = 1;
    public static void RemoveSomeUseLessByte(String filePath) {
        File f = new File(filePath);
        String goalFilePath = f.getParent().toString() + "/" + f.getName().substring(4,f.getName().length());
        try {
            FileInputStream fins = new FileInputStream(filePath);
            FileOutputStream fous = new FileOutputStream(goalFilePath);


            //读取准备
            long addLength = 20;
            //应该读取多少字节
            long totalSize = new File(filePath).length() - addLength;
            long size = 1024 * 1024;

            //读取多少次完整的
            long time = totalSize / size;
            //剩余应读的字节
            int remain = (int) (totalSize % size);
            int len = -1;
            byte[] b = new byte[(int)size];

            //读取和写入
            for(long i = 0; i < time; i++){
                len = fins.read(b);
                fous.write(b, 0, len);
            }

            fins.read(b,0,remain);
            fous.write(b, 0, remain);


            //关闭输入输出流
            fous.flush();
            fins.close();
            fous.close();
            System.out.println(new File(filePath).getName() + "AddSomeUseLessByte success");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("readstream occur fault");
        }


    }

    public static void dfs(String filePath){
        File f = new File(filePath);
        if(f.isFile()) {
            if(f.getName().substring(0,4).equals("Add-")){
                RemoveSomeUseLessByte(filePath);
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
        System.out.println("fieRemoveBaiDuYun 文件路径 [0表示删除原文件]");
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
