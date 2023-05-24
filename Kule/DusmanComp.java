import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.TransformComponent;

import javafx.geometry.Point2D;
import javafx.scene.text.Text;

public class DusmanComp extends Component{
    
    private TransformComponent position;
    private Point2D hedefNokta;

    Text can_txt;
    
    double hiz;
    int can;
    int ceza;

    public DusmanComp(double hiz, int can, int ceza) {
        this.hiz = hiz;
        this.can = can;
        this.ceza = ceza;
        can_txt = new Text("" + can);
        FXGL.getGameScene().addUINode(can_txt);
    }

    @Override
    public void onUpdate(double deltaTime) {
        if(hedefNokta != null) {
            position.translateTowards(hedefNokta, hiz);
        }
        can_txt.setText("" + can);
        can_txt.setTranslateX((position.getX() - FXGL.getGameScene().getViewport().getX() - 5)
            * FXGL.getGameScene().getViewport().getZoom());
        can_txt.setTranslateY((position.getY() - FXGL.getGameScene().getViewport().getY() - 10)
        * FXGL.getGameScene().getViewport().getZoom());
    }

    public void hedefAta(Point2D hedefNokta) {
        this.hedefNokta = hedefNokta;
    }

    public void hasarAl(int i) {
        can-=i;
        if(can <= 0) {
            yokOl();
        }
    }

    public void yokOl() {
        getEntity().removeFromWorld();
        FXGL.getGameScene().removeUINode(can_txt);
    }
}
