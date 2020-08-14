package com.ming.learning.other;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author ming
 * @time 2020/7/20 15:19
 */
public class PictureDemo {

    static  int a = 0;

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\Administrator\\Desktop\\上传\\en_US\\bbnn");
        getfile(file);
        System.out.println(a);
    }

    public static  void getfile(File file) throws IOException {
        for (File f : file.listFiles()){
            if (f.listFiles() != null && f.listFiles().length >0){
                getfile(f);
            } else {
                if (f.getName().endsWith(".jpg") || f.getName().endsWith(".png")){
                    System.out.println(f.getName());
                    watermark(f);
                }
            }
        }
    }

    public static void watermark(File file) throws IOException {
        BufferedImage img = ImageIO.read(file);
        Graphics2D g = img.createGraphics();
        g.setColor(Color.red);
        g.setFont(new Font("微软雅黑", 3,30));
        g.drawString("@beix86",img.getWidth()-150,img.getHeight()-20);
        a++;
        ImageIO.write(img, "png", new FileOutputStream(file.getAbsoluteFile()));
    }
}
