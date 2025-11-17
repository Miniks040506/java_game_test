package main;

import entity.Player;
import tile.TileManager;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;
import object.SuperObject;


public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48
    //    public final int characterSize = tileSize * 3;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 960
    public final int screenHeight = tileSize * maxScreenRow; // 768

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
//    public final int worldWidth = tileSize * maxWorldCol;
//    public final int worldHeight = tileSize * maxWorldRow;

    //FPS
    int FPS = 45;

    //SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound soundE = new Sound();
    Sound music = new Sound();

    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    //Entity and Objects
    public Player player = new Player(this, keyH);
    public SuperObject[] obj = new SuperObject[20];

    //Game State
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;



    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.requestFocusInWindow();

    }

    public void setupGame() {

        aSetter.setObject();

        playMusic(0);

    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
        gameState = playState;

    }

//    @Override
//    public void run() {
//
//        double drawInterval = 1000000000 / FPS;
//        double nextDrawTime = System.nanoTime() + drawInterval;
//
//        while (gameThread != null) {
//
//            update();
//
//            repaint();
//
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime = remainingTime / 1000000;
//
//                if(remainingTime < 0) {
//                    remainingTime = 0;
//                }
//
//                Thread.sleep((long) remainingTime);
//
//                nextDrawTime += drawInterval;
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }

    @Override
    public void run() {

        double drawInterval;
        drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {

                update();
                repaint();

                delta--;
                drawCount++;

            }

            if (timer >= 1000000000) {
                //System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }

            try {
                Thread.sleep(1);  // Nhường CPU, giúp game chạy mượt hơn
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void update() {

        if (gameState == playState) {
            player.update();
        }
        if (gameState == pauseState) {
            //not thing!!!
        }

    }
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        //DEBUG
        long drawStart = 0;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        tileM.draw(g2);

        for (SuperObject superObject : obj) {
            if (superObject != null) {
                superObject.draw(g2, this);
            }
        }

        player.draw(g2);

        ui.draw(g2);

        if (keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 500);
            System.out.println("Draw Time: " + passed);
        }

        g2.dispose();

    }

    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();

    }

    public void stopMusic() {

        music.stop();

    }

    public void playSoundEffect(int i) {

        soundE.setFile(i);
        soundE.play();

    }

}
