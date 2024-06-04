//地图类，负责处理某一场景中Deadline与Block的交互

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.io.*;

public class Map extends JPanel{
    private int id;//地图编号
    private Deadline deadline;//戴德琳
    private ArrayList<Block> block;//地图中物品
    private boolean endGame;//是否停止游戏
    
    MyTimer myTimer;//计时器
    private JLabel background;//设置背景
    

    public Map(int mapID,MyTimer myTimer)
    {
        this.myTimer=myTimer;
        add(this.myTimer);

        setBackground(new Color(250, 250, 250));
        setLayout(null);

        ImageIcon backgroundIcon=new ImageIcon("./data/images/background/game.png");
        background=new JLabel(backgroundIcon);
        background.setBounds(0,0,1707,1067);
        background.setVisible(true);
        add(background);

        deadline=new Deadline(0,0);
        this.add(deadline);
        block=new ArrayList<Block>();
        endGame=false;
        buildMap(mapID);
    }

    public void buildMap(int mapID)
    {
        remove(background);

        if(!block.isEmpty())
        {
            for(Block b:block)
            {
                remove(b);
            }
        }
        id=mapID;
        block=new ArrayList<Block>();
        String mapFileName="./data/maps/map_"+String.format("%d",id)+".txt";
        try(BufferedReader br=new BufferedReader(new FileReader(mapFileName)))
        {
            int deadlineX=0,deadlineY=0;

            String line;
            while ((line = br.readLine()) != null) {
                // 解析每一行的键值对
                String[] parts = line.split("="); // 假设使用等号分隔键和值
                String variable = parts[0].trim(); // 变量名
                String value = parts[1].trim(); // 值
                
                switch(variable)
                {
                    case "deadlineX":
                        deadlineX=Integer.parseInt(value);
                        break;
                    case "deadlineY":
                        deadlineY=Integer.parseInt(value);
                        break;
                    case "blockID":
                        int blockID=Integer.parseInt(value);
                        int blockX=0,blockY=0;
                        for(int i=0;i<3;i++)
                        {
                            line=br.readLine();
                            parts=line.split("=");
                            variable=parts[0].trim();
                            value=parts[1].trim();
                            switch(variable)
                            {
                                case "blockX":
                                    blockX=Integer.parseInt(value);
                                    break;
                                case "blockY":
                                    blockY=Integer.parseInt(value);
                                    break;
                                case "blockKind":
                                    block.add(new Block(Integer.parseInt(value)));
                                    break;
                                default:
                                    break;
                            }
                        }
                        block.get(blockID).setPosition(blockX,blockY);
                        break;
                    default:
                        break;
                }
            }
            deadline.setPosition(deadlineX,deadlineY);
        }
        catch(IOException e)
        {
            System.out.println("Map read failed!");
            e.printStackTrace();
        }

        for(Block b : block)
        {
            b.setImage();
            b.setVisible(true);
            this.add(b);
        }

        add(background);

        repaint();
    }

    public void refresh()
    {
        deadline.move();
        for(Block b : block)
        {
            deadline.contact(b);
            b.contact(deadline);
        }
        deadline.update();

        if(restart())
        {
            buildMap(id);
            deadline.setIsDead(false);
            deadline.setSpeed(0,0);
            SoundPlayer.playSound("./data/sound/restart_level.wav");
        }

        if(goToNext())
        {
            id++;
            if(id>5)
            {
                SoundPlayer.playSound("./data/sound/win.wav");
                endGame=true;
                return;
            }
            SoundPlayer.playSound("./data/sound/pass_level.wav");
            buildMap(id);
            deadline.setIsDead(false);
            deadline.setSpeed(0,0);
        }

        myTimer.repaint();
    }

    public boolean restart()
    {
        if(deadline.getIsDead())
        {
            return true;
        }
        return false;
    }

    public boolean goToNext()
    {
        if(deadline.getLocationX()>=1800)
        {
            return true;
        }
        return false;
    }

    public boolean getEndGame()
    {
        return endGame;
    }
}
