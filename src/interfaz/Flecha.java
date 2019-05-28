package interfaz;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;


public class Flecha extends Path{
    private static final double tamanoPredetereminado = 7.0;
    
    public Flecha(double inicioX, double inicioY, double finalX, double finalY, double tamanoFlecha){
        super();
        strokeProperty().bind(fillProperty());
        setFill(Color.GREEN);
        
        //Linea
        getElements().add(new MoveTo(inicioX, inicioY));
        getElements().add(new LineTo(finalX, finalY));
        
        //Flecha
        double angulo = Math.atan2((finalY - inicioY), (finalX - inicioX)) - Math.PI / 2.0;
        double sin = Math.sin(angulo);
        double cos = Math.cos(angulo);
        //Punto 1
        double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * tamanoFlecha + finalX;
        double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * tamanoFlecha + finalY;
        //Punto 2
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * tamanoFlecha + finalX;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * tamanoFlecha + finalY;
        
        getElements().add(new LineTo(x1, y1));
        getElements().add(new LineTo(x2, y2));
        getElements().add(new LineTo(finalX, finalY));
    }
    
    public Flecha(double inicioX, double inicioY, double finalX, double finalY){
        this(inicioX, inicioY, finalX, finalY, tamanoPredetereminado);
    }
}