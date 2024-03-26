package ex5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TrafficLight extends JFrame {
    private JLabel lightLabel;
    private Color currentColor;

    public TrafficLight() {
        super("Traffic Light");

        lightLabel = new JLabel();
        lightLabel.setPreferredSize(new Dimension(300, 300));

        add(lightLabel, BorderLayout.CENTER);

        currentColor = Color.GREEN;
        lightLabel.setOpaque(true);
        lightLabel.setBackground(currentColor);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 350);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void startTrafficLight() {
        Thread greenThread = new Thread(new LightThread(Color.GREEN, 3000));
        Thread yellowThread = new Thread(new LightThread(Color.YELLOW, 1000));
        Thread redThread = new Thread(new LightThread(Color.RED, 2000));

        redThread.start();
        yellowThread.start();
        greenThread.start();
    }

    private class LightThread implements Runnable {
        private Color color;
        private int duration;

        public LightThread(Color color, int duration) {
            this.color = color;
            this.duration = duration;
        }

        public void run() {
            try {
                while (true) {
                    synchronized (TrafficLight.this) {
                        while (currentColor != color) {
                            TrafficLight.this.wait();
                        }
                        lightLabel.setBackground(color);
                        Thread.sleep(duration);
                        if (color == Color.RED)
                            currentColor = Color.GREEN;
                        else if (color == Color.GREEN)
                            currentColor = Color.YELLOW;
                        else
                            currentColor = Color.RED;
                        TrafficLight.this.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TrafficLight trafficLight = new TrafficLight();
            trafficLight.startTrafficLight();
        });
    }
}
