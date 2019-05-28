:- module('grafo', [arista/3, buscarRuta/4, concatenar/3, vertice/3]).

% Definición de la base de datos para el grafo
% Se definen los vertices que pertenecen al grafo, con su respectiva
% posición en el mapa
% Composición : vertice( <lugar> , <posición en x> , <posición en y> )
%AgregaVertice
vertice('san jose', 86, 207).
vertice('corralillo',106, 425).
vertice('musgo verde', 270, 355).
vertice('tres rios', 274, 154).
vertice('cartago', 444, 307).
vertice('pacayas', 539, 82).
vertice('cervantes', 686, 203).
vertice('paraiso', 614, 306).
vertice('juan viñas', 789, 119).
vertice('turrialba', 870, 69).
vertice('cachi', 841, 307).
vertice('orosi',723, 444).

% Se definen los aristas que pertenecen al grafo, con su respectiva
% distancia entre nodos
% Composición : arista( <Inicio> , <Destino>, <Distancia> )
%AgregaArista
arista('tres rios', 'san jose', 8).
arista('cartago', 'tres rios', 8).
arista('cartago', 'paraiso', 10).
arista('paraiso', 'cervantes', 4).
arista('cervantes', 'juan viñas', 5).
arista('juan viñas', 'turrialba', 12).
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


% Definición de reglas varias.

concatenar([],L,L) :- !.
concatenar([X|L1],L2,[X|L3]):- concatenar(L1,L2,L3).

inversa([], []).
inversa([X | L1], L) :- inversa(L1,Resto), concatenar(Resto,[X],L).

miembro(Elem,[Elem|_]).
miembro(Elem,[_|Cola]):- miembro(Elem,Cola).

primero([Elemento | _], Elemento).

eliminar([_| Cola], Cola).

%sumaLista([Elem | Cola], Suma) :-

% ----------------------------------------------------------------------

% Búsqueda de la ruta más corta para un trayecto en el grafo

% ----------------------------------------------------------------------
% Verifica que la ruta sea la más corta
calcularRuta([Cabeza | Ruta], Distancia) :-
	ruta([Cabeza | _], D), !, Distancia < D,
	retract(ruta([Cabeza | _],_)),
	assert(ruta([Cabeza | Ruta], Distancia)).

%Agrega una posible ruta a los recorridos
calcularRuta(Ruta, Distancia) :-
    assert(ruta(Ruta, Distancia)).

% Se recorren todos los nodos desde un punto inicial
recorrerNodos(Inicio, Ruta, Distancia) :-
	arista(Inicio, Temporal, D),
	not(miembro(Temporal, Ruta)),
	calcularRuta([Temporal,Inicio|Ruta], Distancia + D),
	recorrerNodos(Temporal,[Inicio|Ruta],Distancia + D).

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

% Parámetros:
% Lista con los lugares a los que se puede llegar
% Distancia : Es la distancia total que se va a recorrer.
distanciaTotal([Primero | Ruta], Distancia):-
	primero(Ruta, Segundo),
	arista(Primero, Segundo, D),
	distanciaTotal(Ruta, Dist),
	Distancia is D + Dist.

% Verifica que la lista de destinos esté vacía
% Destinos : Lista
encontrarCamino(_, [], _) :- !.


% Maneja el ciclo para encontrar el camino más corto
% Inicio : Inicio de la ruta
% Destino : Lista con todos los destinos a los que se llegará tomando en cuenta que se el último elemento de la lista es el destino final
% RutaFinal : Lista de la ruta buscada.
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






