package estructuraGrafo;
/**
 *  Clase para la creacion de objetos de aristas.
 *  Incluye las variables 
 *  				origen : nodo donde inicia la arista.
 *					destino: nodo donde termina la arista.
 *					distancia : peso de la arista.
 *Las metodos que se incluyen son los get y set de cada variable.
 */
public class Arista {
    private Nodo origen;
    private Nodo destino;
    private Integer distancia;
 
    /**
     * Constructor de arista
     * @param origen : nodo origen.
     * @param destino : nodo destino.
     * @param distancia : numero que peso de la arista.
     */
    public Arista(Nodo origen, Nodo destino, Integer distancia) {
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
    }
 
    /**
     * Obtener de la variable origen 
     * @return Nodo origen
     */
    public Nodo getOrigen() {
        return origen;
    }
 
    /**
     * Insertar un valor a la variable origen.
     * @param origen : Nodo del origen.
     */
    public void setOrigen(Nodo origen) {
        this.origen = origen;
    }
 
    /**
     * Obtener la variable destino.
     * @return Nodo con el destino.
     */
    public Nodo getDestino() {
        return destino;
    }
 
    /**
     * Insertar un valor a la variable destino.
     * @param destino : Nodo de llegada de la arista.
     */
    public void setDestino(Nodo destino) {
        this.destino = destino;
    }
 
    /**
     * Obtener el valor de distancia.
     * @return Integer con la distancia.
     */
    public Integer getDistancia(){
        return distancia;
    }
    /**
     * Insertar un valor a distancia.
     * @param distancia : Integer con el peso de la arista.
     */
    public void setDistancia(Integer distancia) {
        this.distancia = distancia;
    }
 
    /**
     * Modificacion al metodo toString para unir en string las variables 
     * de la clase.
     */
    @Override
    public String toString() {
        return "[" + origen.getLugar() + ", " + destino.getLugar() + ", "
                + distancia + "]";
    }
}
