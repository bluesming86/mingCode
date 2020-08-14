package com.ming.leetCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author ming
 * @time 2020/3/6 15:07
 */
public class test {

    static Set<String> result = new HashSet<>();
    static int count = 0;
    public static void main(String[] args) {
       /* String ss = "一退码态送币调布者将理分三尊上同名明搏戏列战通业初速 删模营&性()利刪+中帮启0123水临制样刷常游为主值房础局所ACDE陆G版I告手牌行桌LM虎偏乐P限QRS展牛停九属池扣c除d补表器i扩物豪马确n息o驰买平t幸特登百庄的验二源库五投额增务抢红护钥报度级产动助邮骰花钱状炸点人麻钻精线品组情细集盈立操经黑绑监默盘站四胜飞回统代以部注端维银件系任拼开异包（）有看服式编化多猜土张传次欢败账大用在本地失匹场区机活放派杀十允权充等测配子体奔魔单录奖字存留博作神杠卡赢步全八公转置据走数即具典饼兽好签长极概厅内超率消馈安殊王福析辑输斗龙料定喜宝实计管客认控推玩记新箱家德许容禽设访便证参心跃密过采诈始重反运金发比试志变秘菜话保州域信询解查工知日详问路台至间时号对基出导" +
                "转为";

        Set<String> a = new HashSet<>();
        for (int i=0;i<ss.length();i++){
            a.add(ss.substring(i,i+1));
        }

        StringBuffer as = new StringBuffer();
        for (String s : a) {
            as.append(s);
        }
        System.out.println(as.toString());*/

        readFolder("D:\\boss\\web-manager\\WebContent");
        StringBuffer as = new StringBuffer();
        for (String s : result) {
            as.append(s);
        }
        System.out.println("read num:"+count);
        System.out.println(as.toString());
    }

    public static void readFolder(String filePath){

        try {
            File file = new File(filePath);
            if (!file.isDirectory()){
                System.out.println("---------- 该文件不是一个目录文件 ----------");
            } else if (file.isDirectory()){
                System.out.println("---------- 很好，这是一个目录文件夹 ----------");
                String[] fileList = file.list();
                for (String fileName : fileList) {
                    File readFile = new File(filePath + "\\" + fileName);
                    String  absolutePath = readFile.getAbsolutePath();
                    if (readFile.isDirectory()){
                        readFolder(absolutePath);
                    }else {
                        if (absolutePath.indexOf(".jsp") >= 0|| absolutePath.indexOf(".html") >= 0){
                            readFile(absolutePath);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void readFile(String absolutePath){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(absolutePath)));

            for (String temp = null; (temp = bufferedReader.readLine()) != null; temp = null){
                for (int i = 0; i<temp.length(); i ++) {
                    count++;
                    result.add(temp.substring(i,i+1));
                }
            }
            bufferedReader.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
