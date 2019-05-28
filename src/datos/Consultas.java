package datos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;

/**
 * Clase encargada de realizar las diferentes consultas al archivo
 *  de prolog .
 *  		Contiene los metodos:
 *  			- solicitarVertices
 *  			- solicitarAristas
 *  			- solicitarPosiciones
 *  			- solicitarRuta
 */
public class Consultas {

	/**
	 * Funcion encargada de consultar los vertices del archivo prolog.
	 * @return Mapa con el nombre de los vertices.
	 */
	public Map<String, Term>[] solicitarVertices(){
		String conexion = "consult('GRAFO.pl')";
		Query con = new Query(conexion);
		
		con.hasMoreSolutions();
		
		String vertices = "arista(X, Y, _)";
		Query verticesConsulta  = new Query(vertices);
		Map<String, Term>[] todosVertices = verticesConsulta.allSolutions();
		
		return todosVertices;
	}
	
	/**
	 * Consulta al archivo prolog sobre las aritas de un vertice
	 * @param lugar : nombre del vertice a consultar
	 * @return  Mapa con los nombres de vertices que conectar con lugar 
	 * 			y su distancia.
	 */
	public Map<String, Term>[] solicitarAristas(String lugar){
		String conexion = "consult('GRAFO.pl')";
		Query con = new Query(conexion);
		con.hasMoreSolutions();
		
		String aristas = "arista("+ lugar +", Y, Z)";
		Query aristasConsulta = new Query(aristas);
		Map<String, Term>[] todasAristas = aristasConsulta.allSolutions();

		return todasAristas;
	}
	/**
	 * Consulta al archivo de prolog sobre las posicones en pantalla del vertice.
	 * @param lugar : nombre del vertice a consultar
	 * @return Mapa con las posiciones del vertice
	 */
	public Map<String, Term>[] solicitarPosiciones(String lugar){
		String conexion = "consult('GRAFO.pl')";
		Query con = new Query(conexion);
		con.hasMoreSolutions();
		
		String posiciones = "vertice("+ lugar +", Y, Z)";	
		Query posicionesConsulta  = new Query(posiciones);
		Map<String, Term>[] todasPosiciones = posicionesConsulta.allSolutions();
		
		return todasPosiciones;
	}
	/**
	 * Realiza la consulta sobre la ruta para llegar a un destino
	 * 	pasando por diferentes puntos.
	 * @param caminos : Lista con los nombres de los vertices a visitar
	 * @return Lista de string con los nombres de los vertices que forman la ruta.
	 */
	public ArrayList<String> solicitarRuta(List<String> caminos){
		String conexion = "consult('GRAFO.pl')";
		Query con = new Query(conexion);
		con.hasMoreSolutions();
		
		String ruta = "buscarRuta(" + caminos.get(0) + ", [";
		for(Integer i = 1; i < caminos.size(); i++) {
			ruta += caminos.get(i) + ", ";
		}
		ruta = ruta.substring(0, ruta.length()-2);
		ruta += "], X, Y)";
		
		Query consultaRuta = new Query(ruta);
		
		Map<String, Term>[] mejorRuta = consultaRuta.allSolutions();
		ArrayList<String> rutaConsultada = new ArrayList<String>();
		
		String distancia = mejorRuta[0].get("Y").toString();
		System.out.println(distancia);
		
		for (int i = 0; i < mejorRuta.length; i++) {
			Term term = mejorRuta[i].get("X");
			
			for (Term termTemp : term.toTermArray()) {
				rutaConsultada.add(termTemp.toString());
			}
		}
		
		rutaConsultada.add(distancia);
		return rutaConsultada;
	}
}

