import com.almasb.fxgl.entity.component.Component;

public class NoktaComp extends Component{

    public int noktaId;
    public boolean sonNoktaMi;
    
    public NoktaComp(int noktaId, boolean sonNoktaMi) {
        this.noktaId = noktaId;
        this.sonNoktaMi = sonNoktaMi;
    }
}
