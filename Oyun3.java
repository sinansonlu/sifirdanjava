import java.util.ArrayList;
import java.util.List;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.InputModifier;
import com.almasb.fxgl.input.UserAction;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Oyun3 extends GameApplication{

    Viewport viewport;
    Entity karakter;

    double kameraHizi = 1.5;
    double kameraSiniri = 30;
    double yaklastirmaOrani = 0.001;

    ArrayList<Entity> binalar = new ArrayList<Entity>();
    ArrayList<Entity> silinecekBinalar = new ArrayList<Entity>();
    Entity eldekiBina;

    @Override
    protected void initSettings(GameSettings gs) {
        gs.setWidth(600);
        gs.setHeight(600);
        gs.setTitle("Ucuncu Deneme");
    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        input.addAction(new UserAction("ZoomIn") {
            @Override
            protected void onAction() {
                viewport.setZoom(viewport.getZoom() * (1+yaklastirmaOrani));
            }
    
        },KeyCode.UP,InputModifier.SHIFT);

        input.addAction(new UserAction("ZoomOut") {
            @Override
            protected void onAction() {
                viewport.setZoom(viewport.getZoom() * (1-yaklastirmaOrani));
            }
    
        },KeyCode.DOWN,InputModifier.SHIFT);

        input.addAction(new UserAction("SilmeIptal") {
            @Override
            protected void onActionBegin() {
                for (Entity entity : silinecekBinalar) {
                    entity.getViewComponent().setOpacity(1);
                }
                silinecekBinalar.clear();
            }
    
        },KeyCode.SPACE);

        input.addAction(new UserAction("BinaSil") {
            @Override
            protected void onAction() {
                Point2D fareKonumu = FXGL.getInput().getMousePositionWorld();
                List<Entity> entitiesNearby = FXGL.getGameWorld().getEntitiesInRange(
                    new Rectangle2D(fareKonumu.getX()-40, fareKonumu.getY()-40, 40, 40));
                
                for (Entity entity : entitiesNearby) {
                    if(!silinecekBinalar.contains(entity) && entity != karakter) {
                        entity.getViewComponent().setOpacity(0.5);
                        silinecekBinalar.add(entity);
                    }
                }
            }

            @Override
            protected void onActionEnd() {
                for (Entity entity : silinecekBinalar) {
                    if(binalar.remove(entity)) {
                        entity.removeFromWorld();
                    }
                }
            }
    
        },MouseButton.SECONDARY);

        input.addAction(new UserAction("BinaEkle") {
            @Override
            protected void onActionBegin() {
                Point2D fareKonumu = FXGL.getInput().getMousePositionWorld();

                eldekiBina = FXGL.entityBuilder()
                    .at(fareKonumu.getX(), fareKonumu.getY())
                    .view(new Rectangle(40, 40, Color.GRAY))
                    .buildAndAttach();
            }

            @Override
            protected void onAction() {
                if(eldekiBina != null) {
                    Point2D fareKonumu = FXGL.getInput().getMousePositionWorld();
                    eldekiBina.setPosition(fareKonumu.getX(), fareKonumu.getY());
                }
            }

            @Override
            protected void onActionEnd() {
                if(eldekiBina != null) {
                    Point2D fareKonumu = FXGL.getInput().getMousePositionWorld();
                    eldekiBina.setPosition(fareKonumu.getX(), fareKonumu.getY());
                    binalar.add(eldekiBina);
                    eldekiBina = null;
                }
            }
    
        },MouseButton.PRIMARY);
    }
    
    @Override
    protected void initGame() {
        viewport = FXGL.getGameScene().getViewport();
        
        karakter = FXGL.entityBuilder()
            .at(300, 300)
            .view(new Rectangle(50, 50, Color.BLUE))
            .buildAndAttach();
    }

    @Override
    protected void onUpdate(double tpf) {
        Point2D fareKonumu = FXGL.getInput().getMousePositionUI();
        double genislik = viewport.getWidth() / viewport.getZoom();
        double yukseklik = viewport.getHeight() / viewport.getZoom();

        if(fareKonumu.getX() < kameraSiniri) {
            viewport.setX(viewport.getX() - (kameraHizi * (kameraSiniri - fareKonumu.getX())/kameraSiniri));
        }

        if(fareKonumu.getX() > genislik - kameraSiniri) {
            viewport.setX(viewport.getX() + (kameraHizi * (fareKonumu.getX() - genislik + kameraSiniri)/kameraSiniri));
        }

        if(fareKonumu.getY() < kameraSiniri) {
            viewport.setY(viewport.getY() - (kameraHizi * (kameraSiniri - fareKonumu.getY())/kameraSiniri));
        }

        if(fareKonumu.getY() > yukseklik - kameraSiniri) {
            viewport.setY(viewport.getY() + (kameraHizi * (fareKonumu.getY() - yukseklik + kameraSiniri)/kameraSiniri));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
