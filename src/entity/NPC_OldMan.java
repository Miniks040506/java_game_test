package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {

        super(gp);

        direction = "down";
        speed = 1;

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

}
