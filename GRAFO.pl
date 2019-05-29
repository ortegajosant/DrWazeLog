:- module('grafo', [arista/3, vertice/3, buscarRuta/4, concatenar/3, inversa/2]).

% Definicion de la base de datos para el grafo
% Se definen los vertices que pertenecen al grafo, con su respectiva
% posicion en el mapa
% Composición : vertice( <lugar> , <posición en x> , <posicion en y> )
%AgregaVertice
vertice('san jose', 86, 207).
vertice('corralillo',106, 425).
vertice('musgo verde', 270, 355).
vertice('tres rios', 274, 154).
vertice('cartago', 444, 307).
vertice('pacayas', 539, 82).
vertice('cervantes', 686, 203).
vertice('paraiso', 614, 306).
vertice('juan vinas', 789, 119).
vertice('turrialba', 870, 69).
vertice('cachi', 841, 307).
vertice('orosi',723, 444).

% Se definen los aristas que pertenecen al grafo, con su respectiva
% distancia entre nodos
% Composicion : arista( <Inicio> , <Destino>, <Distancia> )
%AgregaArista
arista('tres rios', 'san jose', 8).
arista('cartago', 'tres rios', 8).
arista('cartago', 'paraiso', 10).
arista('paraiso', 'cervantes', 4).
arista('cervantes', 'juan vinas', 5).
arista('juan vinas', 'turrialba', 12).
arista('turrialba', 'pacayas', 18).
arista('san jose', 'corralillo', 22).
arista('corralillo', 'san jose', 22).
arista('musgo verde', 'cartago', 10).
arista('cartago', 'musgo verde', 10).
arista('cartago', 'san jose', 20).
arista('san jose', 'cartago', 20).
arista('tres rios', 'pacayas', 15).
arista('pacayas', 'tres rios', 15).
arista('cartago', 'pacayas', 13).
arista('pacayas', 'cartago', 13).
arista('pacayas', 'cervantes', 8).
arista('cervantes', 'pacayas', 8).
arista('paraiso', 'cachi', 10).
arista('cachi', 'paraiso', 10).
arista('paraiso', 'orosi', 8).
arista('orosi', 'paraiso', 8).
arista('orosi', 'cachi', 12).
arista('cachi', 'orosi', 12).
arista('cachi', 'turrialba', 40).
arista('turrialba', 'cachi', 40).
arista('cervantes', 'cachi', 7).
arista('cachi', 'cervantes', 7).
arista('corralillo', 'musgo verde', 6).
arista('musgo verde', 'corralillo', 6).

:-dynamic
    ruta/2.


% Definicion de reglas varias.
% Verifica la union de dos listas
% concatenar(<Lista>, <Lista2>, <ListaConcatenada>)
concatenar([],Lista,Lista) :- !.
concatenar([X|Lista],Lista2,[X|ListaConcatenada]):- concatenar(Lista,Lista2,ListaConcatenada).

% Verifica la inversa de una lista
% inversa(<Lista>,<ListaInversa>)
inversa([], []).
inversa([X | L1], L) :- inversa(L1,Resto), concatenar(Resto,[X],L).

% Verifica si el elemento esta en la lista
% miembro(<Elemento>, <Lista>)
miembro(Elem,[Elem|_]).
miembro(Elem,[_|Cola]):- miembro(Elem,Cola).

% Verifica el primer elemento de una lista
% primer(<Lista>, <Primer elemento>)
primero([Elemento | _], Elemento).

% Verifica que la lista no tenga el primer elemento
% eliminar(<Lista>, <Lista sin el primer elemento>)
eliminar([_| Cola], Cola).

% ----------------------------------------------------------------------

% Busqueda de la ruta mas corta para un trayecto en el grafo

% ----------------------------------------------------------------------
% Verifica que la ruta sea la mas corta y la agrega
% Ruta: Lista con la ruta actual.
% Distancia: distancia total hasta ese punto.
calcularRuta([Cabeza | Ruta], Distancia) :-
	ruta([Cabeza | _], D), !, Distancia < D,
	retract(ruta([Cabeza | _],_)),
	assert(ruta([Cabeza | Ruta], Distancia)).

calcularRuta(Ruta, Distancia) :-
    assert(ruta(Ruta, Distancia)).

% Se recorren todos los nodos desde un punto inicial
% Inicio: vértice Inicial del recorrido.
% Ruta: Lista con la ruta actual.
% Distancia: Distancia actual de la ruta.
recorrerNodos(Inicio, Ruta, Distancia) :-
	arista(Inicio, Temporal, D),
	not(miembro(Temporal, Ruta)),
	calcularRuta([Temporal,Inicio|Ruta], Distancia + D),
	recorrerNodos(Temporal,[Inicio|Ruta],Distancia + D).

% Inicia el recorrido a partir de un nodo
% Inicio: Vértice inicial.
recorrerNodos(Inicio) :-
	retractall(ruta(_,_)),
	recorrerNodos(Inicio,[],0).

recorrerNodos(_).

% Verifica la posibilidad del recorrido de un punto a otro
% Inicio : punto inicial
% Destino : punto final
% Final : lista de la ruta buscada para el recorrido
ir(Inicio, Destino, Final) :-
	recorrerNodos(Inicio),
	ruta([Destino|RRuta], _) ->
	inversa([Destino|RRuta], Final).

% Obtiene la distancia del recorrido de la ruta
distanciaTotal([_], Distancia) :- Distancia is 0,!.

% Parametros:
% Lista con los lugares a los que se puede llegar
% Distancia : Es la distancia total que se va a recorrer.
distanciaTotal([Primero | Ruta], Distancia):-
	primero(Ruta, Segundo),
	arista(Primero, Segundo, D),
	distanciaTotal(Ruta, Dist),
	Distancia is D + Dist.

% Verifica que la lista de destinos se encuentre vacia
% Inicio: Vértice inicial.
% Destinos: Lista de destinos, contiene un único destino final o como
% elemento final el destino y además paradas intermedias.
% RutaFinal: Lista con la ruta a seguir para completar el recorrido.
encontrarCamino(_, [], _) :- !.


% Maneja el ciclo para encontrar el camino mas corto
encontrarCamino(Inicio, Destinos, RutaFinal) :-
    primero(Destinos, DestinoActual),
    ir(Inicio, DestinoActual, Ruta),
    eliminar(Destinos, ColaDestinos),
    encontrarCamino(DestinoActual, ColaDestinos, SiguienteRuta),
    eliminar(SiguienteRuta, Cola),
    concatenar(Ruta, Cola, RutaFinal).

% Es la regla principal para verificar la ruta del grafo
% Inicio : Inicio del recorrido
% Destinos : Lista con los destinos que se van a recorrer, el ultimo
% elemento es el destino final.
% RutaFinal : Lista con la mejor ruta para el recorrido.
% Distancia : Distancia total del recorrido.
buscarRuta(Inicio, Destinos, RutaFinal, Distancia):-
	encontrarCamino(Inicio, Destinos, RutaFinal),
	distanciaTotal(RutaFinal, Distancia), !.
