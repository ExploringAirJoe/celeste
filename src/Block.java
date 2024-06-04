//障碍物

import java.awt.*;
import javax.swing.*;
import java.net.URL;

public class Block extends JLabel{
    //障碍物坐标，以JFrame内坐标系为参考坐标系
    //坐标为自身碰撞箱正中心，碰撞箱体积以64*64为单位，可以扩展
    private int locationX, locationY;
    private Image currentImage;
    private int width, height;
    private int kind;
    private boolean blockVisible;

    // 定义每种障碍物的尺寸
    private static final int[][] SIZES = {
            { 64,  64}, // 默认尺寸
            { 96,  96}, // 能量豆
            { 64,  64}, // 砖块
            { 64,  64}, // 刺
            {128,  16}, // 平台
            { 64,  16}, // 弹簧
            {112, 104}, // 用过的弹簧
            {128, 128}  // 墙
    };

    //初始化构造函数，传入block类型
    public Block(int kind)
    {
        this.kind = kind;
        this.width = SIZES[kind][0];
        this.height = SIZES[kind][1];
        this.blockVisible=true;
        setVisible(true);
        setImage();
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
    public int getWidth()
    {
        return width;
    }

    //获取高度
    public int getHeight()
    {
        return height;
    }

    //获取种类
    public int getKind()
    {
        return kind;
    }

    //获取可见度
    public boolean getBlockVisible()
    {
        return blockVisible;
    }

    //设置坐标
    public void setPosition(int locX,int locY)
    {
        this.locationX = locX;
        this.locationY = locY;
        setLocation(locX-width/2,locY-height/2);
        repaint();
    }

    //设置种类
    public void setKind(int newkind)
    {
        this.kind=newkind;
        setImage();
        repaint();
    }

    //设置可见度
    public void setBlockVisible(boolean vis)
    {
        blockVisible=vis;
        setVisible(vis);
        repaint();
    }

    //设置当前障碍物的图片
    public void setImage()
    {
        String imagePath;
        switch (kind) {
            case 1: // 能量豆
                imagePath = "./data/images/block/bean.png";
                break;
            case 2: // 砖块
                imagePath = "./data/images/block/block.png";
                break;
            case 3: // 刺
                imagePath = "./data/images/block/nail.png";
                break;
            case 4: // 平台
                imagePath = "./data/images/block/platform.png";
                break;
            case 5: // 弹簧
                imagePath = "./data/images/block/spring.png";
                break;
            case 6: // 用过的弹簧
                imagePath = "./data/images/block/spring_used.png";
                break;
            case 7: //墙
                imagePath = "./data/images/block/wall.png";
                break;
            default: //墙
                imagePath = "./data/images/block/wall.png";
        }
        this.currentImage = new ImageIcon(imagePath).getImage();
        setBounds(locationX-width/2,locationY-height/2,width,height);
    }

    // 判断是否与戴德琳碰撞，0 未碰撞，1234 分别代表从上下左右接触，同时判定多种碰撞时优先返回较小的数
    public int contact(Deadline deadline) {
        Rectangle blockRect = new Rectangle(locationX - width / 2, locationY - height / 2, width, height);
        Rectangle deadlineRect = new Rectangle(deadline.getLocationX() - deadline.getWidth() / 2, deadline.getLocationY() - deadline.getHeight() / 2, deadline.getWidth(), deadline.getHeight());

        if (blockRect.intersects(deadlineRect)) {
            Rectangle intersection = blockRect.intersection(deadlineRect);

            if (intersection.height < intersection.width) {
                if (deadline.getLocationY() < locationY) {
                    return 1; // 上碰撞
                } else {
                    return 2; // 下碰撞
                }
            } else {
                if (deadline.getLocationX() < locationX) {
                    return 3; // 左碰撞
                } else {
                    return 4; // 右碰撞
                }
            }
        }

        return 0; // No collision
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentImage != null) {
            g.drawImage(currentImage, 0, 0, this);
        }
        else {
            System.out.println("no image");
        }
        
    }
}
