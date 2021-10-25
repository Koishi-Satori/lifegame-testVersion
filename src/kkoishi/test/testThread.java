package kkoishi.test;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class testThread extends Thread {
    static volatile int[][] world;
    //新建原子类整形num来储存全局
    static volatile AtomicReference<Integer> num = new AtomicReference<>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int border = scanner.nextInt();
        scanner.close();
        num.set(border);
        world = new int[border][border];
        int x = new Random().nextInt(border-1);
        int y = new Random().nextInt(border-1);
        world[x][y] = 1;
        world[x+1][y] = 1;
        Thread thread = new Thread(()->{
           while (true) {
               try {
                   Thread.sleep(1000);
                   for (int i = 0;i<border;i++) {
                       for (int j = 0;j<border;j++) {
                           rule_Def(j,i);
                       }
                   }
                   for (int i = 0;i<num.get();i++) {
                       for (int j = 0; j < num.get(); j++) {
                           System.out.print(world[i][j] + " ");
                       }
                       System.out.print("\n");
                   }
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
        //shit,I can't give out a better method.
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
        if (sum == 2) world[x][y] = 1;
        else if (sum == 3) world[x][y] = world[x][y];
        else world[x][y] = 0;
    }

    @Override
    public void run() {
        try {
            sleep(5);
            for (int i = 0;i<num.get();i++) {
                for (int j = 0;j<num.get();j++) {
                    System.out.print(world[i][j] + " ");
                }
                System.out.print("\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
