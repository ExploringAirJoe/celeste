//游戏主控类，负责控制整个游戏运行逻辑。

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.io.*;

public class Main{
    //跳转至游戏主界面,大小为1707*1067
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
        newGame.setVisible(true);
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
        rank.setVisible(true);
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
        exitGame.setVisible(true);
        panel.add(exitGame);
        exitGame.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                homePage.setVisible(false);
                System.exit(0);
            }
        });

        //设置背景
        ImageIcon backgroundIcon=new ImageIcon("./data/images/background/homepage.png");
        JLabel background=new JLabel(backgroundIcon);
        background.setBounds(0,0,1707,1067);
        background.setVisible(true);
        panel.add(background);

        homePage.repaint();
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

        MyTimer myTimer=new MyTimer();

        int nowMapID=1;
        Map map=new Map(nowMapID,myTimer);
        gamePage.add(map);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // 这里写需要执行的操作
                map.refresh();
                if(map.getEndGame())
                {
                    cancel();
                    getToEnd(myTimer.getTime());
                }
                //map.add(myTimer);
                myTimer.refreshTime();
            }
        };

        // 以毫秒为单位，每隔20毫秒执行一次任务
        timer.scheduleAtFixedRate(task, 0, 20);
    }

    //游戏结束
    public static void getToEnd(double usedTime)
    {
        JFrame endPage=new JFrame();
        endPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endPage.setResizable(false);//固定大小
        endPage.setExtendedState(JFrame.MAXIMIZED_BOTH);//全屏模式
        endPage.setUndecorated(true);
        endPage.setVisible(true);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(250, 250, 250));
        panel.setLayout(null);
        endPage.add(panel);

        JLabel label=new JLabel("恭喜通关！请输入你的名字");
        label.setBounds(420,330,300,60);
        label.setFont(new Font("Serif",Font.BOLD,18));
        label.setForeground(Color.WHITE);
        panel.add(label);

        //获取玩家名
        JTextField textField = new JTextField();
        textField.setBounds(500,420,140,30);
        panel.add(textField);
        
        //记录玩家纪录
        JButton record =new JButton("确定");
        record.setBounds(500,480,140,30);
        panel.add(record);
        record.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                editRank(textField.getText(),usedTime);
                getToHome();//跳转至主界面
            }
        });

        //设置背景
        ImageIcon backgroundIcon=new ImageIcon("./data/images/background/end.png");
        JLabel background=new JLabel(backgroundIcon);
        background.setBounds(0,0,1707,1067);
        background.setVisible(true);
        panel.add(background);

        endPage.repaint();
    }

    //修改排行榜
    public static void editRank(String playerName,double usedTime)
    {
        if(playerName=="")
        {
            playerName="NULL";
        }
        String recordName=new String("");
        double recordTime=10000;
        try(BufferedReader br=new BufferedReader(new FileReader("./data/rank.txt")))
        {
            recordName=br.readLine();
            recordTime=Double.parseDouble(br.readLine());
            br.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        try(BufferedWriter bw=new BufferedWriter(new FileWriter("./data/rank.txt")))
        {
            if(usedTime<recordTime)
            {
                bw.write(playerName);
                bw.newLine();
                bw.write(String.format("%.2f", usedTime));
                bw.newLine();
            }
            else
            {
                bw.write(recordName);
                bw.newLine();
                bw.write(String.format("%.2f", recordTime));
                bw.newLine();
            }
            bw.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
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
        nameLabel.setFont(new Font("Serif",Font.BOLD,18));
        recordLabel.setFont(new Font("Serif",Font.BOLD,18));
        nameLabel.setForeground(Color.WHITE);
        recordLabel.setForeground(Color.WHITE);
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
                name.setFont(new Font("Serif",Font.BOLD,18));
                time.setFont(new Font("Serif",Font.BOLD,18));
                name.setForeground(Color.WHITE);
                time.setForeground(Color.WHITE);
                panel.add(name);
                panel.add(time);
                recordCnt++;
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        //设置背景
        ImageIcon backgroundIcon=new ImageIcon("./data/images/background/homepage.png");
        JLabel background=new JLabel(backgroundIcon);
        background.setBounds(0,0,1707,1067);
        background.setVisible(true);
        panel.add(background);

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