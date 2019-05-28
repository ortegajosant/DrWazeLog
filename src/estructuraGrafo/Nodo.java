package estructuraGrafo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Nodo {
    private String lugar;
    private Integer posX = 0;
    private Integer posY = 0;
    private List<Arista> aristas;
 
    public Nodo(String lugar) {
        this.lugar = lugar;
    }
 
    public Integer getPosX() {
		return posX;
	}

	public void setPosX(Integer posX) {
		this.posX = posX;
	}

	public Integer getPosY() {
		return posY;
	}

	public void setPosY(Integer posY) {
		this.posY = posY;
	}

	public void setAristas(List<Arista> aristas) {
		this.aristas = aristas;
	}

	public String getLugar() {
        return lugar;
    }
 
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
 
    public List<Arista> getAristas() {
        return aristas;
    }
 
    public void addArista(Arista arista) {
        if (aristas == null) {
            aristas = new ArrayList<>();
        }
        aristas.add(arista);
    }
 
    @Override
    public String toString() {
        return "\n[" + lugar + ", " + aristas + "]";
    }
}
