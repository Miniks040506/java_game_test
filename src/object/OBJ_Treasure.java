package object;

import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;


public class OBJ_Treasure extends SuperObject {
    
    GamePanel gp;
    
    public OBJ_Treasure(GamePanel gp) {
        
        name = "Treasure";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/treasure.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
