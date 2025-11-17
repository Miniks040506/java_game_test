package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;


public final class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

//    public int hasKey = 0;
//    public int hasBloodKey = 0;

    int standCounter = 0;
//    public  int pixelCounter = 0;
//    public boolean moving = false;

    public Player (GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 12;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 24;
        solidArea.height = 24;

        setDefaultValues();
        getPlayerImage();

    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

    }

    public void getPlayerImage() {

        down1 = setup("down_1");
        down2 = setup("down_2");
        down3 = setup("down_3");
        up1 = setup("up_1");
        up2 = setup("up_2");
        up3 = setup("up_3");
        left1 = setup("left_1");
        left2 =setup("left_2");
        left3 = setup("left_3");
        right1 = setup("right_1");
        right2 = setup("right_2");
        right3 = setup("right_3");


/*
        try {

            down1 = ImageIO.read(getClass().getResourceAsStream("/player_image/down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player_image/down_2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/player_image/down_3.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/player_image/up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player_image/up_2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/player_image/up_3.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player_image/left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player_image/left_2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/player_image/left_3.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player_image/right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player_image/right_2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/player_image/right_3.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
*/

    }

    public BufferedImage setup(String imageName) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream("/player_image/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }


    public void update() {

        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {

            if (keyH.upPressed == true) {
                direction = "up";

            }
            else if (keyH.downPressed == true) {
                direction = "down";

            }
            else if (keyH.leftPressed == true) {
                direction = "left";

            }
            else if (keyH.rightPressed == true) {
                direction = "right";

            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickupObject(objIndex);

            if(collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter != 0) {
                if (spriteCounter == 8) {
                    spriteNum = 2;
                }
                else if (spriteCounter == 16) {
                    spriteNum = 1;
                }
                else if (spriteCounter == 24) {
                    spriteNum = 3;
                }
                else if (spriteCounter == 32) {
                    spriteNum = 1;
                }
                else if (spriteCounter > 32) {
                    spriteCounter = 0;
                }
            }
        }

        else {
            standCounter++;
            if (standCounter == 8) {
                spriteNum = 1;
                standCounter = 0;
            }

        }
    }


    public void pickupObject(int i) {

        if (i != 999) {

        }

    }

    public void draw(Graphics2D g2) {

//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch(direction) {
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                if (spriteNum == 3) {
                    image = down3;
                }

                break;
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                if (spriteNum == 3) {
                    image = up3;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                if (spriteNum == 3) {
                    image = left3;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                if (spriteNum == 3) {
                    image = right3;
                }
                break;

        }

        g2.drawImage(image, screenX, screenY, null);

    }

}
