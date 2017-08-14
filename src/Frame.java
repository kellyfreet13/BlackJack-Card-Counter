import javax.swing.*;
import java.awt.*;

/**
 * Created by Kelly on 3/2/2017.
 */
public class Frame extends JFrame{

    private Panel panel;

    public Frame(){
        setBounds(25, 25, 600, 800);
        panel = new Panel();
        panel.setBackground(Color.LIGHT_GRAY);
        getContentPane().add(panel);
    }

}
