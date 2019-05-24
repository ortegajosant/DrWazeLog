% Definición de la base de datos del grafo

arista("tres rios", "san jose", 8).
arista("cartago", "tres rios", 8).
arista(cartago, paraiso, 10).
arista(paraiso, cervantes, 4).
arista(cervantes, juan_vainas, 5).
arista(juan_vainas, turrialba, 12).
arista(turrialba, pacayas, 18).
arista(san_jose, corralillo, 22).
arista(corralillo, san_jose, 22).
arista(musgo_verde, cartago, 10).
arista(cartago, musgo_verde, 10).
arista(cartago, san_jose, 20).
arista(san_jose, cartago, 20).
arista(tres_rios, pacayas, 15).
arista(pacayas, tres_rios, 15).
arista(cartago, pacayas, 13).
arista(pacayas, cartago, 13).
arista(pacayas, cervantes, 8).
arista(cervantes, pacayas, 8).
arista(paraiso, cachi, 10).
arista(cachi, paraiso, 10).
arista(paraiso, orosi, 8).
arista(orosi, paraiso, 8).
arista(orosi, cachi, 12).
arista(cachi, orosi, 12).
arista(cachi, turrialba, 40).
arista(turrialba, cachi, 40).
arista(cervantes, cachi, 7).
arista(cachi, cervantes, 7).
arista(corralillo, musgo_verde, 6).
arista(musgo_verde, corralillo, 6).

:-dynamic
    ruta/2.


% Definición de reglas varias.

concatenar([],L,L).
concatenar([X|L1],L2,[X|L3]):- concatenar(L1,L2,L3).

inversa([], []).
inversa([X | L1], L) :- inversa(L1,Resto), concatenar(Resto,[X],L).

miembro(Elem,[Elem|_]).
miembro(Elem,[_|Cola]):- miembro(Elem,Cola).

primero([Elemento | _], Elemento).

eliminar([_ | Cola], Cola).

vacio([]).

% ----------------------------------------------------------------------
% ----------------------------------------------------------------------

% Código para la búsqueda de las rutas del grafo

% Verifica que la ruta sea la más corta
calcularRuta([Cabeza | Ruta], Distancia) :-
	ruta([Cabeza | Visitados], D), !, Distancia < D,
	retract(ruta([Cabeza | _],_)),
	writef('%w is closer than %w\n', [[Cabeza | Ruta], [Cabeza | Visitados]]),
	assert(ruta([Cabeza | Ruta], Distancia)).

%Agrega una posible ruta a los recorridos
calcularRuta(Ruta, Distancia) :-
    assert(ruta(Ruta, Distancia)).

% Se recorren todos los nodos desde el punto inicial
recorrerNodos(Inicio, Ruta, Distancia) :-
	arista(Inicio, Temporal, D),
	not(miembro(Temporal, Ruta)),
	calcularRuta([Temporal,Inicio|Ruta], Distancia + D),
	recorrerNodos(Temporal,[Inicio|Ruta],Distancia + D).

% Inicia el recorrido de los nodos
recorrerNodos(Inicio) :-
	retractall(ruta(_,_)),
	recorrerNodos(Inicio,[],0).

recorrerNodos(_).

% Identifica cual es la ruta más corta para el trayecto, se buscan todas las rutas desde el inicio hasta todas las posibilidades
ir(Inicio, Destino, Final) :-
	recorrerNodos(Inicio),
	ruta([Destino|RRuta], Distancia) ->
	  inversa([Destino|RRuta], Ruta),
	  SumaDistancia is round(Distancia),
          writef('Shortest path is %w with distance %w\n',
	       [Ruta, SumaDistancia]),
          Final = Ruta;
	writef('There is no route from %w to %w\n', [Inicio, Destino]).

% Establece la condición de parada para que se termine la búsqueda de la ruta
encontrarCamino(_, Destinos, _) :- vacio(Destinos), !.

% Inicia la búsqueda para el destino, tomando en cuenta que hay paradas intermedias
encontrarCamino(Inicio, Destinos, RutaFinal) :-
    primero(Destinos, DestinoActual),
    writef("\n\nEvaluando %w con %w\n\n", [Inicio, DestinoActual]),
    ir(Inicio, DestinoActual, Ruta),
    eliminar(Destinos, ColaDestinos),
    encontrarCamino(DestinoActual, ColaDestinos, SiguienteRuta),
    eliminar(SiguienteRuta, Cola),
    concatenar(Ruta, Cola, RutaFinal).


