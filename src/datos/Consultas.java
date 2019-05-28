package datos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;

public class Consultas {

	public Map<String, Term>[] solicitarVertices(){
		String conexion = "consult('GRAFO.pl')";
		Query con = new Query(conexion);
		
		con.hasMoreSolutions();
		
		String vertices = "arista(X, Y, _)";
		Query verticesConsulta  = new Query(vertices);
		Map<String, Term>[] todosVertices = verticesConsulta.allSolutions();
		
		return todosVertices;
	}
	
	public Map<String, Term>[] solicitarAristas(String lugar){
		String conexion = "consult('GRAFO.pl')";
		Query con = new Query(conexion);
		con.hasMoreSolutions();
		
		String aristas = "arista("+ lugar +", Y, Z)";
		Query aristasConsulta = new Query(aristas);
		Map<String, Term>[] todasAristas = aristasConsulta.allSolutions();

		return todasAristas;
	}
	
	public Map<String, Term>[] solicitarPosiciones(String lugar){
		String conexion = "consult('GRAFO.pl')";
		Query con = new Query(conexion);
		con.hasMoreSolutions();
		
		String posiciones = "vertice("+ lugar +", Y, Z)";	
		Query posicionesConsulta  = new Query(posiciones);
		Map<String, Term>[] todasPosiciones = posicionesConsulta.allSolutions();
		
		return todasPosiciones;
	}
	
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

