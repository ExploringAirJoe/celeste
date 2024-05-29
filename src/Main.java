//游戏主控类，负责控制整个游戏运行逻辑。

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;

public class Main{
    //跳转至游戏主界面
    public static void getToHome()
    {
        JFrame homePage=new JFrame();
        homePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homePage.setResizable(false);//固定大小
        homePage.setExtendedState(JFrame.MAXIMIZED_BOTH);//全屏模式
        homePage.setUndecorated(true);
        homePage.setVisible(true);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(250, 250, 250));
        panel.setLayout(null);
        homePage.add(panel);

        //开始新游戏
        JButton newGame=new JButton("New Game");
        newGame.setBounds(80,480,140,30);
        panel.add(newGame);
        newGame.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                homePage.setVisible(false);
                getToGame();//跳转至游戏场景
            }
        });

        //查看排行榜
        JButton rank=new JButton("Rank");
        rank.setBounds(80,540,140,30);
        panel.add(rank);
        rank.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                homePage.setVisible(false);
                getToRank();//跳转至排行榜
            }
        });

        //退出游戏
        JButton exitGame=new JButton("Exit Game");
        exitGame.setBounds(80,600,140,30);
        panel.add(exitGame);
        exitGame.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                homePage.setVisible(false);
                System.exit(0);
            }
        });
    }

    //跳转至游戏场景
    public static void getToGame()
    {
        JFrame gamePage=new JFrame();
        gamePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePage.setResizable(false);//固定大小
        gamePage.setBounds(0, 0,1680,1040);
        gamePage.setExtendedState(JFrame.MAXIMIZED_BOTH);//全屏模式
        gamePage.setUndecorated(true);
        gamePage.setVisible(true);

    }

    //跳转至排行榜
    public static void getToRank()
    {
        JFrame rankPage=new JFrame();
        rankPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rankPage.setResizable(false);//固定大小
        rankPage.setExtendedState(JFrame.MAXIMIZED_BOTH);//全屏模式
        rankPage.setUndecorated(true);
        rankPage.setVisible(true);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(250, 250, 250));
        panel.setLayout(null);
        rankPage.add(panel);

        //展示排行榜
        JLabel nameLabel=new JLabel("Name");
        JLabel recordLabel=new JLabel("Record");
        nameLabel.setBounds(600,60,140,30);
        recordLabel.setBounds(940,60,140,30);
        panel.add(nameLabel);
        panel.add(recordLabel);
        try(BufferedReader br=new BufferedReader(new FileReader("./data/rank.txt")))
        {
            int recordCnt=0;
            String recordName;
            double recordTime;
            while((recordName=br.readLine())!=null)
            {
                recordTime=Double.parseDouble(br.readLine());
                JLabel name=new JLabel(recordName);
                JLabel time=new JLabel(String.format("%.2f", recordTime));
                name.setBounds(600,120+80*recordCnt,140,30);
                time.setBounds(940,120+80*recordCnt,140,30);
                panel.add(name);
                panel.add(time);
                recordCnt++;
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        //返回主界面
        JButton back=new JButton("Back");
        back.setBounds(770,960,140,30);
        panel.add(back);
        back.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                rankPage.setVisible(false);
                getToHome();//跳转至主界面
            }
        });
    }

    //主控函数
    public static void main(String[] args)
    {
        getToHome();
    }
}