package kkoishi.main;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class MainThread {
    public static volatile int[][] world;
    static volatile JButton[][] Hell_nya;
    public static volatile AtomicReference<Integer> times = new AtomicReference<>(100);
    public static volatile AtomicReference<Integer> num = new AtomicReference<>(0);
    public static void main(String[] args) {
        //超超超短主方法
        //the shortest main method.
        build();
    }
    //input size data frame.
    //大小数值输入窗口
    public static void build() {
        JFrame frame = new JFrame("输入大小input size");
        frame.setResizable(false);
        frame.setBounds(400,400,300,200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JTextField textField = new JTextField("输入边长 input length of the side");
        JTextField textField1 = new JTextField("刷新频率(默认100ms)");
        JButton button = new JButton("confirm确认");
        button.addActionListener(e -> {
            num.set(Integer.parseInt(textField.getText()));
            times.set(Integer.parseInt(textField1.getText()));
            if (Integer.parseInt(textField.getText())<4) textField.setText("过小too small");
            else hell();
        });
        textField.setBounds(10,10,280,40);
        textField1.setBounds(10,70,280,40);
        button.setBounds(100,120,100,30);

        panel.add(textField1);
        panel.add(textField);
        panel.add(button);
        panel.setVisible(true);
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void hell() {
        JFrame frame = new JFrame("结果result");
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(num.get(),num.get()));
        Hell_nya = new JButton[num.get()][num.get()];
        for (int i = 0;i<num.get();i++) {
            for (int j = 0;j<num.get();j++) {
                Hell_nya[i][j] = new JButton();
                Hell_nya[i][j].setSize(40,40);
                Hell_nya[i][j].setBackground(Color.PINK);
                Hell_nya[i][j].setBorder(BorderFactory.createLineBorder(Color.PINK));
                panel.add(Hell_nya[i][j]);
            }
        }
        frame.add(panel);
        frame.setVisible(true);
        int border = num.get();
        world = new int[border][border];
        int x = new Random().nextInt(border-1);
        int y = new Random().nextInt(border-1);
        world[x][y] = 1;
        world[x+1][y] = 1;
        Hell_nya[x][y].setBackground(Color.black);
        Hell_nya[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Hell_nya[x+1][y].setBackground(Color.black);
        Hell_nya[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Thread thread = new Thread(()->{
            while (true) {
                try {
                    Thread.sleep(times.get());
                    for (int i = 0;i<border;i++) for (int j = 0; j < border; j++) rule_Def(j, i);
                    System.out.print("\n");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    //What the hell am I doing?:(
    public static void rule_Def(int x,int y) {
        int sum = 0;
        //shit,I can't give a better method.
        //rnm，想不出更好的判断方法了
        if ((x < num.get()-1&&x>0)&&(y <num.get()-1&&y>0)) sum = world[x - 1][y - 1] +
                world[x][y - 1] + world[x + 1][y - 1] + world[x - 1][y] + world[x - 1][y + 1]
                + world[x][y + 1] + world[x + 1][y + 1] + world[x + 1][y];
        else if (x == 0&&y != 0&&y != num.get()-1) sum = world[x][y - 1] + world[x][y + 1] + world[x + 1][y - 1]
                + world[x + 1][y] + world[x + 1][y + 1];
        else if (x == num.get()-1&&y != 0&&y != num.get()-1) sum = world[x][y-1] + world[x][y+1] + world[x][y + 1]
                + world[x - 1][y - 1] + world[x - 1][y] + world[x - 1][y + 1];
        else if (y == 0&& x!= 0&&x != num.get()-1) sum = world[x-1][y] + world[x+1][y] + world[x-1][y+1]
                + world[x][y+1] + world[x+1][y+1];
        else if (y == num.get()-1&&x != 0&&x != num.get()-1) sum = world[x-1][y] + world[x+1][y] + world[x-1][y-1]
                + world[x][y-1] + world[x+1][y-1];
        else if (x == 0&&y == 0) sum = world[x+1][y] + world[x][y+1] + world[x+1][y+1];
        else if (x == num.get()-1&&y == num.get()-1) sum = world[x-1][y]
                + world[x][y-1] + world[x-1][y-1];
        else if (x == num.get()-1&&y == 0) sum = world[x][y+1] + world[x-1][y] + world[x-1][y+1];
        else sum = world[x][y-1] + world[x+1][y] + world[x+1][y-1];
        //记得从基点开始搜索全图
        if (sum == 2) {
            world[x][y] = 1;
            //检索到细胞存活或者应该存在细胞 按钮设置为黑色
            Hell_nya[x][y].setBackground(Color.black);
            Hell_nya[x][y].setBorder(BorderFactory.createLineBorder(Color.black));
        }
        //其实这个else if没有意义 但是我就是玩（
        else if (sum == 3) world[x][y] = world[x][y];
        else {
            world[x][y] = 0;
            //粉粉的按钮
            Hell_nya[x][y].setBackground(Color.PINK);
            Hell_nya[x][y].setBorder(BorderFactory.createLineBorder(Color.PINK));
        }
    }
}
