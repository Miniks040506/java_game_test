package entity;

import main.GamePanel;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Entity {

    GamePanel gp;

    public int worldX, worldY;
    public int speed;

    public BufferedImage down1, down2, down3, up1, up2, up3, left1, left2, left3, right1, right2, right3;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);

    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    public Entity (GamePanel gp) {
        this.gp = gp;
    }

}
