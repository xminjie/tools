import java.io.*;
import java.time.Clock;
import java.util.Date;

class Encry {
    //传递两个参数（文件名（必填），密码（默认为0）, 几分钟后删除（默认2分钟,负数和0不删除））
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("请输入文件名");
        }
        System.out.println("输入参数 文件名 [加密密码] [生成文件删除时间]");
        String filePath = args[0];
        File f = new File(filePath);
        String goalFilePath = f.getParent() + "/encry-" + f.getName();
        try {
            FileInputStream fins = new FileInputStream(filePath);
            FileOutputStream fous = new FileOutputStream(goalFilePath);

            byte encry = (byte) 0;
            if(args.length >= 2){
                int sum = 0;
                for(int i = 0; i < args[1].length(); i++){
                    sum += (int)(args[1].charAt(i) - '0');
                }
                encry = (byte) (sum % 100);
            }

            Date d1 = new Date();

            int size = 1024*1024;
            int sumCnt = Math.toIntExact(f.length() / size), cnt = 0;
            byte[] b = new byte[size];
            int len = -1;
            while ((len = fins.read(b)) != -1) {
                for (int i = 0; i < size; i++) {
                    b[i] = (byte) (b[i] ^ encry);
                }
                System.out.println(100 * cnt / sumCnt + "%");
                cnt++;
                fous.write(b, 0, len);
            }


            fous.flush();
            fins.close();
            fous.close();
            System.out.println("success");

            Date d2 = new Date();


            System.out.println(d2.getTime() - d1.getTime());

            double time = 2;
            if(args.length == 3) {
                try{
                    time = Double.parseDouble(args[2]);
                }catch (Exception e){
                    System.out.println("时间参数有误");
                }
            }
            File file = new File(goalFilePath);

            if(time > 0){
                System.out.println(goalFilePath + " file will be delete after " + time + " minutes");
                Thread.sleep((long) (time * 60 * 1000));
                file.delete();
                System.out.println(goalFilePath + "  file have been delete");
            }


        } catch (FileNotFoundException e) {
            System.out.println(filePath + " file not found");
        } catch (IOException e) {
            System.out.println("readstream occur fault");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ;

    }
}

