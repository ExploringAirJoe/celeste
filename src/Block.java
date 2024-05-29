//障碍物

import java.awt.*;
import javax.swing.*;
import java.net.URL;

public class Block extends JLabel{
    //障碍物坐标，以JFrame内坐标系为参考坐标系
    //坐标为自身碰撞箱正中心，碰撞箱体积以64*64为单位，可以扩展
    private int locationX,locationY;

    //初始化构造函数，传入block类型
    public Block(int kind)
    {
        
    }

    //获取X坐标
    public int getLocationX()
    {
        return locationX;
    }

    //获取Y坐标
    public int getLocationY()
    {
        return locationY;
    }

    //设置坐标
    public void setLocation(int locX,int locY)
    {

    }

    //设置当前障碍物的图片
    public void setImage()
    {

    }

    //判断是否与戴德琳碰撞，0未碰撞，1234分别代表从上下左右接触，同时判定多种碰撞时优先返回较小的数
    public int contact(Deadline deadline)
    {
        return 0;
    }

}
