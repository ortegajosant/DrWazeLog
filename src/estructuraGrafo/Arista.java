package estructuraGrafo;

public class Arista {
    private Nodo origen;
    private Nodo destino;
    private Integer distancia;
 
    public Arista(Nodo origen, Nodo destino, Integer distancia) {
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
    }
 
    public Nodo getOrigen() {
        return origen;
    }
 
    public void setOrigen(Nodo origen) {
        this.origen = origen;
    }
 
    public Nodo getDestino() {
        return destino;
    }
 
    public void setDestino(Nodo destino) {
        this.destino = destino;
    }
 
    public Integer getDistancia(){
        return distancia;
    }
 
    public void setDistancia(Integer distancia) {
        this.distancia = distancia;
    }
 
    @Override
    public String toString() {
        return "[" + origen.getLugar() + ", " + destino.getLugar() + ", "
                + distancia + "]";
    }
}
