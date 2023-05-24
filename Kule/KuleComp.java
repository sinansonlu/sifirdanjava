
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.TransformComponent;
import com.almasb.fxgl.physics.CircleShapeData;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.time.TimerAction;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class KuleComp extends Component {
    private TransformComponent position;
    Entity hedefDusman;

    public KuleComp() {
        TimerAction timerAction = FXGL.getGameTimer().runAtInterval(() -> {
            if(hedefDusman != null) {
                MermiComp mc = new MermiComp(5);
                mc.hedefAta(hedefDusman);
                
                FXGL.entityBuilder()
                .type(Oyun3.Turler.MERMI)
                .at(position.getPosition())
                .view(new Circle(4,Color.BLACK))
                .bbox(new HitBox(new Point2D(-4, -4), new CircleShapeData(4))) // collision şeklinin yarıçap kadar kayması için
                .with(new CollidableComponent(true))
                .with(mc)
                .buildAndAttach();
            }
            
        }, Duration.seconds(0.5));
    }

    public void hedefSec(Entity e) {
        if(hedefDusman == null) {
            hedefDusman = e;
        }
    }

    public void hedefCikar(Entity e) {
        if(hedefDusman == e) {
            hedefDusman = null;
        }
    }
}
