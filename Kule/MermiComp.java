import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.TransformComponent;

public class MermiComp extends Component {
    private TransformComponent position;
    private Entity hedef;

    double hiz;

    public MermiComp(double hiz) {
        this.hiz = hiz;
    }

    @Override
    public void onUpdate(double deltaTime) {
        if(hedef != null && hedef.isActive()) {
            position.translateTowards(hedef.getPosition(), hiz);
        } else {
            getEntity().removeFromWorld();
        }
    }

    public void hedefAta(Entity hedef) {
        this.hedef = hedef;
    }
}
