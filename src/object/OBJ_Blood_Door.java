package object;

import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;


public class OBJ_Blood_Door extends SuperObject {

    GamePanel gp;

    public OBJ_Blood_Door(GamePanel gp) {

        name = "Blood_Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/blood_door.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }

}
