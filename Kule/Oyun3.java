import java.util.ArrayList;
import java.util.List;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.InputModifier;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CircleShapeData;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.time.TimerAction;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Oyun3 extends GameApplication{

    public enum Turler {
        KULE, DUSMAN, MERMI, NOKTA
    }

    int toplamCan = 100;

    Viewport viewport;
    Entity karakter;

    double kameraHizi = 1.5;
    double kameraSiniri = 30;
    double yaklastirmaOrani = 0.001;

    ArrayList<Entity> hedefNoktalar = new ArrayList<Entity>();
    ArrayList<Entity> dusmanlar = new ArrayList<Entity>();

    ArrayList<Entity> binalar = new ArrayList<Entity>();
    ArrayList<Entity> silinecekBinalar = new ArrayList<Entity>();
    Entity eldekiBina;

    @Override
    protected void initSettings(GameSettings gs) {
        gs.setWidth(1200);
        gs.setHeight(800);
        gs.setTitle("Ucuncu Deneme");
        gs.setDeveloperMenuEnabled(true);
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
                        entity.getViewComponent().clearChildren(); // ek
                        entity.getViewComponent().addChild(new Rectangle(40, 40, Color.BLUE)); // ek
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
                    .type(Turler.KULE)
                    .at(fareKonumu.getX(), fareKonumu.getY())
                    .view(new Rectangle(40, 40, Color.GRAY))
                    .bbox(new HitBox(new Point2D(-200+20, -200+20), new CircleShapeData(200)))
                    .with(new CollidableComponent(true))
                    .with(new KuleComp())
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

    int seviye = 0;
    int cikacakDusmanSayisi = seviye * 5 + 5;
    int bekleme = 5;

    @Override
    protected void initGame() {
        viewport = FXGL.getGameScene().getViewport();
        
        karakter = FXGL.entityBuilder()
            .at(300, 300)
            .view(new Rectangle(50, 50, Color.BLUE))
            .buildAndAttach();

        int[] xler = {300,400,500,300,100};
        int[] yler = {400,600,900,800,1000};

        for (int i = 0; i < xler.length; i++) {
            hedefNoktalar.add(
            FXGL.entityBuilder()
            .type(Turler.NOKTA)
            .at(xler[i], yler[i])
            .view(new Circle(20,Color.PURPLE))
            .bbox(new HitBox(new Point2D(-20, -20), new CircleShapeData(20))) // collision şeklinin yarıçap kadar kayması için
            .with(new CollidableComponent(true))
            .with(new NoktaComp(i, i == xler.length-1))
            .buildAndAttach()
            );
        }

        TimerAction timerAction = FXGL.getGameTimer().runAtInterval(() -> {
            if(cikacakDusmanSayisi > 0) {
                if(bekleme <= 0) {
                    DusmanComp dc = new DusmanComp(1.2 + (double) seviye * 0.2, 20 + seviye * 5, (int) (1 + (double) seviye / 3));
                    dc.hedefAta(hedefNoktalar.get(1).getPosition());
                    
                    dusmanlar.add(
                    FXGL.entityBuilder()
                    .type(Turler.DUSMAN)
                    .at(hedefNoktalar.get(0).getX(), hedefNoktalar.get(0).getY())
                    .view(new Circle(10 + seviye * 5,Color.RED))
                    .bbox(new HitBox(new Point2D(-(10 + seviye * 5), -(10 + seviye * 5)), new CircleShapeData(10 + seviye * 5))) // collision şeklinin yarıçap kadar kayması için
                    .with(new CollidableComponent(true))
                    .with(dc)
                    .buildAndAttach()
                    );
    
                    cikacakDusmanSayisi--;
                }
                else{
                    bekleme--;
                }
            } else {
                seviye++;
                cikacakDusmanSayisi = seviye * 5 + 5;
                bekleme = 5;
            }
        }, Duration.seconds(0.5));
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

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(Turler.DUSMAN, Turler.NOKTA) {
            @Override
            protected void onCollisionBegin(Entity dusman, Entity nokta) {
                // düşman bir sonraki noktayı hedef alır
                NoktaComp nc = nokta.getComponent(NoktaComp.class);
                if(nc.sonNoktaMi) {
                    toplamCan -= dusman.getComponent(DusmanComp.class).ceza;
                    dusman.getComponent(DusmanComp.class).yokOl();
                }
                else {
                    dusman.getComponent(DusmanComp.class)
                        .hedefAta(hedefNoktalar.get(nc.noktaId+1).getPosition());
                }
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(Turler.DUSMAN, Turler.KULE) {
            @Override
            protected void onCollisionBegin(Entity dusman, Entity kule) {
                kule.getComponent(KuleComp.class).hedefSec(dusman);
            }

            @Override
            protected void onCollisionEnd(Entity dusman, Entity kule) {
                kule.getComponent(KuleComp.class).hedefCikar(dusman);
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(Turler.DUSMAN, Turler.MERMI) {
            @Override
            protected void onCollisionBegin(Entity dusman, Entity mermi) {
                dusman.getComponent(DusmanComp.class).hasarAl(3);
                mermi.removeFromWorld();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
