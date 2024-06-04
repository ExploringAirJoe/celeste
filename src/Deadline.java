//戴德琳

import java.awt.*;
import javax.swing.*;
import java.net.URL;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Deadline extends JLabel implements FocusListener{
    //角色坐标，以JFrame内坐标系为参考坐标系
    //坐标为自身碰撞箱中心，碰撞箱体积为96*64，大致位于戴德琳中心与脚底贴齐的位置即可
    private boolean isDead;
    private int locationX, locationY;
    private int speedX, speedY;
    private boolean canJump, canSprint;
    private Image currentImage;
    private int width;
    private int height;
    private boolean isAirborne;
    private int dashCount;
    private boolean facingRight;
    private boolean isClimbing;
    private boolean[] keyStates = new boolean[256];

    //初始化构造函数
    public Deadline(int startX, int startY) {
        this.isDead=false;
        this.locationX = startX;
        this.locationY = startY;
        this.speedX = 0;
        this.speedY = 0;
        this.canJump = true;
        this.canSprint = true;
        this.currentImage = new ImageIcon("./data/images/deadline/stand_right.png").getImage();
        this.width=currentImage.getWidth(null);
        this.height=currentImage.getHeight(null);
        this.isAirborne = true;
        this.dashCount = 1;
        this.facingRight = true;
        this.isClimbing = false;
        for(int i=0;i<keyStates.length;i++)
        {
            keyStates[i]=false;
        }
        addFocusListener(this);
        setBounds(startX-width/2,startY-height/2,width,height);
        setVisible(true);
        repaint();
    }

    // 实现 FocusListener 接口的方法
    @Override
    public boolean isFocusTraversable() {
        return true;
    }

    @Override
    public void focusGained(FocusEvent e) {
        // 获得焦点时注册键盘事件监听器
        addKeyListener(new MyKeyListener());
        // 请求焦点，确保键盘事件能够触发
        requestFocusInWindow();
    }

    @Override
    public void focusLost(FocusEvent e) {
        // 失去焦点时移除键盘事件监听器
        removeKeyListener(getKeyListeners()[0]);
    }

    //获取生命
    public boolean getIsDead()
    {
        return isDead;
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

    //获取宽度
    public int getWidth() { return width; }

    //获取长度
    public int getHeight() { return height; }

    //获取X速度
    public int getSpeedX()
    {
        return speedX;
    }

    //获取Y速度
    public int getSpeedY()
    {
        return speedY;
    }

    //获取能否跳跃
    public boolean getJump()
    {
        return canJump;
    }

    //获取能否冲刺
    public boolean getSprint()
    {
        return canSprint;
    }

    //设置生命
    public void setIsDead(boolean dead)
    {
        this.isDead=dead;
    }

    //设置坐标
    public void setPosition(int locX,int locY)
    {
        this.locationX = locX;
        this.locationY = locY;
    }

    //设置速度
    public void setSpeed(int speedX,int speedY)
    {
        this.speedX = speedX;
        this.speedY = speedY;
    }

    //设置能否跳跃
    public void setJump(boolean jump)
    {
        this.canJump = jump;
    }

    //设置能否冲刺
    public void setSprint(boolean sprint)
    {
        this.canSprint = sprint;
    }

    //设置当前戴德琳的图片
    public void setImage()
    {
        String imagePath=facingRight ? "./data/images/deadline/stand_right.png" : "./data/images/deadline/stand_left.png";
        if (isClimbing) {
            if (dashCount == 0) {
                imagePath = facingRight ? "./data/images/deadline/blue_climb_right.png" : "./data/images/deadline/blue_climb_left.png";
            }
            else if (dashCount == 1) {
                imagePath = facingRight ? "./data/images/deadline/climb_right.png" : "./data/images/deadline/climb_left.png";
            }
        }
        else if (isAirborne) {
            if (dashCount == 0) {
                if (speedY > 0 && ((speedX != 0)||(isKeyDown(KeyEvent.VK_A)||isKeyDown(KeyEvent.VK_D)))) {
                    imagePath = facingRight ? "./data/images/deadline/blue_drop_to_right.png" : "./data/images/deadline/blue_drop_to_left.png";
                } else if (speedY > 0) {
                    imagePath = facingRight ? "./data/images/deadline/blue_drop_right.png" : "./data/images/deadline/blue_drop_left.png";
                } else {
                    imagePath = facingRight ? "./data/images/deadline/blue_jump_right.png" : "./data/images/deadline/blue_jump_left.png";
                }
            } else if (dashCount == 1) {
                if (speedY > 0 && speedX != 0) {
                    imagePath = facingRight ? "./data/images/deadline/red_drop_to_right.png" : "./data/images/deadline/red_drop_to_left.png";
                } else if (speedY > 0) {
                    imagePath = facingRight ? "./data/images/deadline/red_drop_right.png" : "./data/images/deadline/red_drop_left.png";
                }
            }
        }
        else {
            if ((speedX != 0)||(isKeyDown(KeyEvent.VK_A)||isKeyDown(KeyEvent.VK_D))) {
                imagePath = facingRight ? "./data/images/deadline/run_right.png" : "./data/images/deadline/run_left.png";
            } else {
                imagePath = facingRight ? "./data/images/deadline/stand_right.png" : "./data/images/deadline/stand_left.png";
            }
        }
        this.currentImage = new ImageIcon(imagePath).getImage();
        this.width=currentImage.getWidth(null);
        this.height=currentImage.getHeight(null);
    }
    //判断是否与block碰撞，0未碰撞，1234分别代表从上下左右接触，同时判定多种碰撞时优先返回较小的数
    public int contact(Block block) {
        Rectangle deadlineRect = new Rectangle(locationX - 52, locationY - 64, 104, 128);
        Rectangle blockRect = new Rectangle(block.getLocationX()-block.getWidth()/2, block.getLocationY()-block.getHeight()/2, block.getWidth(), block.getHeight());

        if (deadlineRect.intersects(blockRect)) {
            Rectangle intersection = deadlineRect.intersection(blockRect);

            //与能量球碰撞
            if(block.getKind()==1)
            {
                if(block.getBlockVisible()&&(getSprint()==false))
                {
                   resetDash();   
                   block.setBlockVisible(false);
                }
                return 0;
            }
            //与刺碰撞
            if(block.getKind()==3)
            {
                setIsDead(true);
                return 0;
            }
            //与平台碰撞
            if(block.getKind()==4)
            {
                if (locationY < block.getLocationY()) 
                {
                    locationY-=(intersection.height-1);
                    if(speedY>0)
                    {
                        speedY=0;
                        setJump(true);
                        resetDash();
                    }
                    isAirborne=false;
                    return 1; // 上碰撞
                }
                return 0;
            }
            //与弹簧碰撞
            if(block.getKind()==6)
            {
                if(locationY<block.getLocationY())
                {
                    speedY=-15;
                    resetDash();
                    return 1;
                }
                return 0;
            }
            if ((intersection.height <= intersection.width)&&(intersection.width>1))
            {
                if (locationY < block.getLocationY()) 
                {
                    locationY-=(intersection.height-1);
                    if(speedY>0)
                    {
                        speedY=0;
                    }
                    isAirborne=false;
                    setJump(true);
                    resetDash();
                    return 1; // 上碰撞
                } 
                else 
                {
                    locationY+=(intersection.height-1);
                    if(speedY<0)
                    {
                        speedY=0;
                    }
                    return 2; // 下碰撞
                }
            } 
            else
            {
                if (locationX < block.getLocationX()) 
                {
                    locationX-=(intersection.width-1);
                    if(speedX>0)
                    {
                        speedX=0;
                    }
                    if(isKeyDown(KeyEvent.VK_D)&&(intersection.height>2))
                    {
                        speedY=0;
                        isClimbing=true;
                        setJump(true);
                    }
                    return 3; // 左碰撞
                } 
                else 
                {
                    locationX+=(intersection.width-1);
                    if(speedX<0)
                    {
                        speedX=0;
                    }
                    if(isKeyDown(KeyEvent.VK_A)&&(intersection.height>2))
                    {
                        speedY=0;
                        isClimbing=true;
                        setJump(true);
                    }
                    return 4; // 右碰撞
                }
            }
        }
        return 0; // 无碰撞
    }

    private void performDash() {
        int dashSpeed = 25; // Dash speed can be adjusted
        int diagCount = 0;
        speedX = 0;
        speedY = 0;

        if (this.isKeyDown(KeyEvent.VK_S) && isAirborne) 
        {
            speedY = dashSpeed; // 下冲
            diagCount++;
        } 
        else if (this.isKeyDown(KeyEvent.VK_W) && !this.isKeyDown(KeyEvent.VK_S))
        {
            speedY = -dashSpeed; // 上冲
            diagCount++;
        }
        if (this.isKeyDown(KeyEvent.VK_A) && !this.isKeyDown(KeyEvent.VK_D)) 
        {
            speedX = -dashSpeed; // 左冲
            diagCount++;
        }
        else if (this.isKeyDown(KeyEvent.VK_D))
        {
            speedX = dashSpeed; // 右冲
            diagCount++;
        }
        canSprint = false;
        dashCount--;
        if (diagCount == 2) 
        {
            speedX = speedX * 3 / 4;
            speedY = speedY * 3 / 4;
        }
    }

    public void resetDash() {
        this.dashCount = 1;
        this.canSprint = true;
    }

    //每次循环更新先更新位置
    public void move() {
        locationX += speedX;
        locationY += speedY;
        if(locationX-52<0)
        {
            locationX=52;
        }
        if(locationY<0)
        {
            locationY=0;
            speedY=0;
        }

        // 左移A右移D
        if(isKeyDown(KeyEvent.VK_A)==isKeyDown(KeyEvent.VK_D))
        {
            if(speedX>0)
            {
                speedX--;
            }
            if(speedX<0)
            {
                speedX++;
            };
        }
        else if(isKeyDown(KeyEvent.VK_A))
        {
            if(speedX<-10)
            {
                speedX++;
            }
            else
            {
                speedX = Math.max(-10,speedX-1);
            }
            facingRight = false;
        }
        else if(isKeyDown(KeyEvent.VK_D))
        {
            if(speedX>10)
            {
                speedX--;
            }
            else
            {
                speedX = Math.min(10,speedX+1);
            }
            facingRight = true;
        }
        // 跳跃K
        if(isKeyDown(KeyEvent.VK_K))
        {
            if (!isAirborne || canJump) 
            {
                speedY = -20; // Jump speed
                if(isClimbing)
                {
                    speedY=-15;
                    if(isKeyDown(KeyEvent.VK_A))
                    {
                        facingRight=false;
                        locationX+=10;
                        speedX=15;
                    }
                    else if(isKeyDown(KeyEvent.VK_D))
                    {
                        facingRight=true;
                        locationX-=10;
                        speedX=-15;
                    }
                }
                isAirborne = true;
                setJump(false);
            }
        }
        // 冲刺L
        if(isKeyDown(KeyEvent.VK_L))
        {
            if (canSprint && dashCount > 0)
            {
                performDash();
            }
        }

        isAirborne=true;
        isClimbing=false;
    }

    //进行完碰撞判定后再进行更新
    public void update() {

        if (isAirborne) {
            speedY += 1; // 重力
        }

        if ((speedY > 0)&&(isClimbing==false)) {
            canJump = false;
        }

        setImage(); // 每次更新时设置图像
        setBounds(locationX-width/2,locationY-height/2,width,height);
        repaint();

        //死亡
        if(locationY>1400&&locationX<1800)
        {
            isDead=true;
        }
    }

    private class MyKeyListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key >= 0 && key < keyStates.length) {
                keyStates[key] = true;
            }
            
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key >= 0 && key < keyStates.length) {
                keyStates[key] = false;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
    }

    public boolean isKeyDown(int keyCode) {
        if (keyCode >= 0 && keyCode < keyStates.length) {
            return keyStates[keyCode];
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(currentImage, 0, 0, this);
    }

}
