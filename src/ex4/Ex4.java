package ex4;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

public class Ex4 extends JFrame implements ActionListener, Runnable {
    JButton creatClock = new JButton("new Clock");
    JLabel clock = new JLabel();
    Thread t;
    public Ex4() {
        Container cont = this.getContentPane();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        clock = new JLabel(sdf.format(cal.getTime()),JLabel.CENTER);

        clock.setFont(new Font(clock.getFont().getName(), Font.PLAIN,40));
        clock.setForeground(Color.BLACK);

        cont.add(creatClock,"North");
        cont.add(clock);
        this.pack();
        this.setVisible(true);

        creatClock.addActionListener(this);

        t = new Thread(this);
        t.start();
        setLocationRelativeTo(null);
    }
    public void tick() {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            clock = new JLabel(sdf.format(cal.getTime()));

            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void run() {
        while(true) {
            tick();
        }
    }
    public void actionPerformed(ActionEvent e) {
        Thread t = new Thread(new Ex4());
        t.start();
    }
    public static void main(String[] a) {
        new Ex4();
    }
}
