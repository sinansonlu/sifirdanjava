import java.util.Map;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Oyun extends GameApplication{

    Entity kup, kup2;

    AnimationChannel ac1;
    AnimatedTexture tex1;

    int hiz = 2;

    @Override
    protected void initSettings(GameSettings gs) {
        gs.setWidth(600);
        gs.setHeight(600);
        gs.setTitle("İlk Deneme");
    }

    @Override
    protected void initGame() {
        ac1 = new AnimationChannel(FXGL.image("newdude.png"), 4, 32, 42, Duration.millis(600), 0, 3);
        tex1 = new AnimatedTexture(ac1);

        kup = FXGL.entityBuilder()
            .at(300, 300)
            .view(tex1)
            // .view(new Rectangle(40, 40, Color.RED))
            .buildAndAttach();

        kup2 = FXGL.entityBuilder()
            .at(300, 500)
            .view(new Rectangle(200, 40, Color.PURPLE))
            .buildAndAttach();

        kup.getTransformComponent().setScaleOrigin(new Point2D(16,21));
    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        input.addAction(new UserAction("SolaGit") {

            @Override
            protected void onActionBegin() {
                tex1.loop();
                kup.setScaleX(-1);
            }

            @Override
            protected void onAction() {
                kup.translateX(-hiz);
                FXGL.inc("pozisyon", -hiz);
            }

            @Override
            protected void onActionEnd() {
                tex1.stop();
            }
            
        }, KeyCode.LEFT);

        input.addAction(new UserAction("SagaGit") {
            @Override
            protected void onActionBegin() {
                tex1.loop();
                kup.setScaleX(1);
            }

            @Override
            protected void onAction() {
                kup.translateX(hiz);
                FXGL.inc("pozisyon", hiz);
            }

            @Override
            protected void onActionEnd() {
                tex1.stop();
            }
        }, KeyCode.RIGHT);
        
    }

    @Override
    protected void initUI() {
        Text pozisyonUI = new Text();
        pozisyonUI.setTranslateX(50);
        pozisyonUI.setTranslateY(50);

        pozisyonUI.textProperty().bind(
            FXGL.getWorldProperties().intProperty("pozisyon").asString());
        
        FXGL.getGameScene().addUINode(pozisyonUI);

        Text zamanUI = new Text();
        zamanUI.setTranslateX(50);
        zamanUI.setTranslateY(70);

        zamanUI.textProperty().bind(
            FXGL.getWorldProperties().intProperty("zaman").asString());
        
        FXGL.getGameScene().addUINode(zamanUI);

        Text can = new Text();
        can.setTranslateX(50);
        can.setTranslateY(30);

        can.textProperty().bind(
            FXGL.getWorldProperties().intProperty("can").asString());
        
        FXGL.getGameScene().addUINode(can);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pozisyon",300);
        vars.put("zaman",0);
        vars.put("can",9);
    }

    @Override
    protected void onUpdate(double tpf) {
        Point2D fare = FXGL.getInput().getMousePositionWorld();
        Point2D zeminPozisyonu = new Point2D(fare.getX()-100, 500);
        kup2.setPosition(zeminPozisyonu);

        if(kup.getY() > 640) {
            kup.setY(0);
            FXGL.inc("can", -1);
            if(FXGL.getWorldProperties().intProperty("can").intValue() < 0) {
                
            }
        }

        if(kup.getX()+32 >= kup2.getX() 
            && kup.getX() <= kup2.getX() + 200
            && kup.getY()+42 >= kup2.getY()-3 
            && kup.getY()+42 <= kup2.getY()+3) {
            kup.setY(kup2.getY()-42);
            FXGL.inc("zaman", 1);
        }
        else{
            kup.setY(kup.getY()+1);
        }

        if(kup.getX() > 632) {
           kup.setX(-32);
           FXGL.set("pozisyon", -32);
        }

        if(kup.getX() < -32) {
            kup.setX(632);
            FXGL.set("pozisyon", 632);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
