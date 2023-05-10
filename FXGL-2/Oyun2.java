import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.time.TimerAction;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Oyun2 extends GameApplication{

    Entity karakter;

    ArrayList<Entity> dusmanlar;

    boolean kaybettikMi = false;
    Random rnd = new Random();

    double dx, dy;

    double hiz = 2.4;

    public enum Turler {
        KARAKTER, DUSMAN
    }
    
    @Override
    protected void initSettings(GameSettings gs) {
        gs.setWidth(600);
        gs.setHeight(600);
        gs.setTitle("İkinci Deneme");
    }

    @Override
    protected void initGame() {
        karakter = FXGL.entityBuilder()
            .type(Turler.KARAKTER)
            .at(300, 300)
            .viewWithBBox(new Rectangle(50, 50, Color.BLUE))
            .with(new CollidableComponent(true))
            .buildAndAttach();

        dusmanlar = new ArrayList<Entity>();

        TimerAction timerAction = FXGL.getGameTimer().runAtInterval(() -> {
            dusmanlar.add(FXGL.entityBuilder()
                .type(Turler.DUSMAN)
                .at(rnd.nextDouble(600),rnd.nextDouble(600))
                .viewWithBBox(new Rectangle(30,30, Color.RED))
                .with(new CollidableComponent(true))
                .with(new HareketliComp())
                .buildAndAttach());
        }, Duration.seconds(1));
    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(Turler.DUSMAN, Turler.KARAKTER) {
            @Override
            protected void onCollisionBegin(Entity dusman, Entity karakter) {
                dusmanlar.remove(dusman);
                dusman.removeFromWorld();

                int saglik = FXGL.getWorldProperties().getInt("saglik");
                if(saglik - 5 <= 0) {
                    // kaybediyoruz
                    karakter.removeFromWorld();
                    kaybettikMi = true;
                    FXGL.getWorldProperties().setValue("saglik", 0);
                }
                else {
                    FXGL.inc("saglik", -5);
                }
            }
        });
    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        input.addAction(new UserAction("YonDegistir") {
            @Override
            protected void onActionBegin() {
                for (int i = 0; i < dusmanlar.size(); i++) {
                    dusmanlar.get(i).getComponent(HareketliComp.class).yonDegistir();
                }
            }
        }, KeyCode.SPACE);

        input.addAction(new UserAction("Sol") {
            @Override
            protected void onAction() {
                dx = -1;
            }

            @Override
            protected void onActionEnd() {
                dx = 0;
            }
        }, KeyCode.LEFT);

        input.addAction(new UserAction("Sag") {
            @Override
            protected void onAction() {
                dx = 1;
            }

            @Override
            protected void onActionEnd() {
                dx = 0;
            }
        }, KeyCode.RIGHT);

        input.addAction(new UserAction("Yukari") {
            @Override
            protected void onAction() {
                dy = -1;
            }

            @Override
            protected void onActionEnd() {
                dy = 0;
            }
        }, KeyCode.UP);

        input.addAction(new UserAction("Asagi") {
            @Override
            protected void onAction() {
                dy = 1;
            }

            @Override
            protected void onActionEnd() {
                dy = 0;
            }
        }, KeyCode.DOWN);
    }

    @Override
    protected void initUI() {
        Text saglik = new Text();
        saglik.setTranslateX(50);
        saglik.setTranslateY(50);

        saglik.textProperty().bind(
            FXGL.getWorldProperties().intProperty("saglik").asString());
        
        FXGL.getGameScene().addUINode(saglik);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("saglik", 100);
    }

    @Override
    protected void onUpdate(double tpf) {
        if(!kaybettikMi) {
            karakter.translateTowards(new Point2D(karakter.getX()+dx*hiz, karakter.getY()+dy*hiz), hiz);
            for (int i = 0; i < dusmanlar.size(); i++) {
                dusmanlar.get(i).getComponent(HareketliComp.class).karakteriGor(karakter.getPosition());
            }
        }
        else{
            for (int i = 0; i < dusmanlar.size(); i++) {
                dusmanlar.get(i).getComponent(HareketliComp.class).karakteriGor(null);
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
