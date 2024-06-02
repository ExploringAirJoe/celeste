import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener{
    private boolean[] keyStates = new boolean[256];

    public MyKeyListener() {
        // 初始化键盘状态数组
        for (int i = 0; i < keyStates.length; i++) {
            keyStates[i] = false;
        }
    }

    // 当键被按下时调用
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode >= 0 && keyCode < keyStates.length) {
            keyStates[keyCode] = true;
        }
    }

    // 当键被释放时调用
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode >= 0 && keyCode < keyStates.length) {
            keyStates[keyCode] = false;
        }
    }

    // 当键被输入时调用（一般不需要实现）
    @Override
    public void keyTyped(KeyEvent e) {
        // Do nothing
    }

    // 获取某个键的状态
    public boolean isKeyPressed(int keyCode) {
        if (keyCode >= 0 && keyCode < keyStates.length) {
            return keyStates[keyCode];
        }
        return false;
    }
}
