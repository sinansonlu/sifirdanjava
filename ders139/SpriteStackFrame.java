import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

public class SpriteStackFrame extends JFrame implements ComponentListener{

    SpriteStack ss;

    public SpriteStackFrame(ArrayList<BufferedImage> resimler) {
        ss = new SpriteStack();
        ss.resimleriAl(resimler);
        add(ss);
        setBounds(500, 400, 400, 400);
        setVisible(true);
        ss.pencereGuncelle(getWidth(), getHeight());
        addComponentListener(this);
    }

    public SpriteStack spriteStack() {
        return ss;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        ss.pencereGuncelle(getWidth(), getHeight());
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
    
}
