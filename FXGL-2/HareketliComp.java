import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.TransformComponent;

import javafx.geometry.Point2D;

class HareketliComp extends Component {

    private TransformComponent position;
    private Point2D karakterPos;

    double hiz = 1.5;

    boolean solaMi = false;

    @Override
    public void onUpdate(double deltaTime) {
        if(karakterPos != null) {
            position.translateTowards(karakterPos, hiz);
        }
    }

    public void yonDegistir() {
        solaMi = !solaMi;
    }

    public void karakteriGor(Point2D karakterPos) {
        this.karakterPos = karakterPos;
    }


}