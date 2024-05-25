//计时器，用于记录单局游戏通关时长，并在游戏局内实时显示当前用时

import javax.swing.*;
import java.awt.*;

public class Timer extends JLabel {
    private double gameTime;//本局游戏进行时长（以秒为单位）
    private double lastFrameTime;//上一帧的时间
    private boolean gamePause;//用于标记当前游戏是否暂停

    //初始化，应当在游戏开始时调用生成计时器
    public Timer()
    {
        setBounds(1680,0,200,40);//设置范围
        setFont(new Font("Times New Roman",Font.BOLD,15));//设置字体
        setText("<html>Time:"+String.format("%.2f", gameTime)+"second</html>");//设置显示内容
        setForeground(Color.black);
        setVisible(true);

        gameTime=0;//游戏时间置零
        lastFrameTime=(double)(System.currentTimeMillis()/1000.0);//记录游戏开始时间
        gamePause=false;//设为已经开始
    }

    //刷新显示时间
    public void refreshTime()
    {
        double nowFrameTime=(double)(System.currentTimeMillis()/1000.0);//当前帧的时间
        //未暂停，时间累加
        if(!gamePause)
        {
            gameTime+=(nowFrameTime-lastFrameTime);//累加时间
            setText("<html>Time:"+String.format("%.2f", gameTime)+"second</html>");//设置显示内容
        }
        lastFrameTime=nowFrameTime;//更新上一帧的时间
    }

    //获取游戏已进行的时间
    public double getTime()
    {
        return gameTime;
    }

    //设置是否暂停
    public void setPause(boolean pause)
    {
        gamePause=pause;
    }
}
