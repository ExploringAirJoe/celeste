//戴德琳

import java.awt.*;
import javax.swing.*;
import java.net.URL;

public class Deadline extends JLabel{
    //角色坐标，以JFrame内坐标系为参考坐标系
    //坐标为自身碰撞箱中心，碰撞箱体积为96*64，大致位于戴德琳中心与脚底贴齐的位置即可
    private int locationX,locationY;

    //初始化构造函数
    public Deadline()
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

    //获取X速度
    public int getSpeedX()
    {
        return 0;
    }

    //获取Y速度
    public int getSpeedY()
    {
        return 0;
    }

    //获取能否跳跃
    public boolean getJump()
    {
        return true;
    }

    //获取能否冲刺
    public boolean getSprint()
    {
        return true;
    }

    //设置坐标
    public void setLocation(int locX,int locY)
    {

    }

    //设置速度
    public void setSpeed(int speedX,int speedY)
    {

    }

    //设置能否跳跃
    public void setJump()
    {

    }
   
    //设置能否冲刺
    public void setSprint()
    {

    }

    //设置当前戴德琳的图片
    public void setImage()
    {

    }

    //判断是否与block碰撞，0未碰撞，1234分别代表从上下左右接触，同时判定多种碰撞时优先返回较小的数
    public int contact(Block block)
    {
        return 0;
    }

}
