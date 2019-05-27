:-use_module(grafo).

%Comprueba que la lista L se a la inversa de L1.
%L1 lista.
%L lista invertida
inversa(L1,L):- inversa(L1,[],L).
inversa([],L,L).
inversa([X|R],L2,L3):-inversa(R,[X|L2],L3).
% -----------------------------------------------------------------------
% Permite leer frases de entrada completas y preparar una lista para ser
% evaluada en la gramatica
% Lee frase de entrada, la separa en palabras y
% comprueba si es correcta.

leerLinea(T):- peek_code(X),X \== 46,
    X \== 32,
    leerLinea("",[],T).
leerLinea(P,F,T):- peek_code(X),
    X \== 46, X \== 32,
    get(Y), char_code(A,Y),downcase_atom(A,LowerCase),
    atomic_concat(P,LowerCase,Result),
    leerLinea(Result,F,T).
leerLinea(P,F,T):- peek_code(X),X == 32,
    get0(X),
    leerLinea("",[P|F],T).
leerLinea(P,F,T):- peek_code(X),
    X == 46,get0(X),
    inversa([P|F],T).


% -----------------------------------------------------------------------
% Gramatica permitida en la conversacion.
% Analizador para el proyecto
%-----------------------------------------------
frase(S0,S):- oracion(S0,S).
frase(S0,S):- sintagma_prep(S0,S).
frase(S0,S):- respuesta(S0,S).
frase(S0,S):- saludos(S0,S1),
    oracion(S1,S).
frase(S0,S):- pClave(S0,S).
%-----------------------------------------------
saludos(S0,S):-saludo(S0,S1),
    nom(_,_,S1,S).
saludos(S0,S):-saludo(S0,S).

%-----------------------------------------------
oracion(S0, S1):- sintagma_nominal(_, _, _, S0, S1).

oracion(S0,S):- sintagma_nominal(Num,Pers,_,S0,S1),
    sintagma_verbal(Num,Pers,S1,S).

oracion(S0,S):- sintagma_verbal(_,_,S0,S1),
    conector(S1,S2),
    infi(S2,S3),
    sintagma_nominal(_,_,_,S3,S),!.

oracion(S0,S):- pron(Num,Pers,_,S0,S1),
    sintagma_verbal(Num,Pers,S1,S).

oracion(S0,S):- pron(Num,Pers,_,S0,S1),
    sintagma_verbal(Num,Pers,S1,S2),
    sintagma_prep(S2,S).

oracion(S0,S):-sintagma_verbal(_,_,S0,S).

%-----------------------------------------------
sintagma_nominal(Num,Pers,Gen,S0,S):- det(Num,Gen,Pers,S0,S1),
    nom(Num,Gen,S1,S).

sintagma_nominal(_,_,_,S0,S):- nom(_,_,S0,S).

sintagma_nominal(_,_,_,S0,S):- pClave(S0,S).

%-----------------------------------------------
sintagma_verbal(Num,Pers,S0,S):- verbo(Num,Pers,S0,S).

sintagma_verbal(Num,Pers,S0,S):- verbo(Num,Pers,S0,S1),
    sintagma_nominal(Num,Pers,_,S1,S).

sintagma_verbal(_,_,S0,S):- verbo(_,_,S0,S1),
    sintagma_prep(S1,S).

%-----------------------------------------------
sintagma_prep(S0,S):- prep(S0,S1),
    sintagma_nominal(_,_,_,S1,S).

%-----------------------------------------------
det(singular,masculino,tercera,['el'|S],S).
det(singular,masculino,tercera,['al'|S],S).
det(singular, femenino, tercera, ['la'|S], S).

%-----------------------------------------------
nom(singular,masculino,['wazeLog'|S],S).
nom(singular,masculino,['automercado'|S],S).
nom(singular,masculino,['supermercado'|S],S).
nom(singular,masculino,['estadio'|S],S).
nom(singular, femenino, ['plaza'|S], S).
nom(singular, femenino, ['fuente'|S], S).
nom(singular, femenino, ['universidad'|S], S).
nom(singular, femenino, ['escuela'|S], S).
nom(singular, masculino, ['colegio'|S],S).
nom(singular, masculino, ['pal�'|S], S).
nom(singular, masculino, ['megasuper'|S], S).
nom(singular, femenino, ['casa'|S], S).
nom(singular, masculino, ['hospital'|S], S).
nom(singular, femenino, ['iglesia'|S], S).
nom(singular, masculino, ['templo'|S], S).

%-----------------------------------------------
respuesta(['si'|S],S).
respuesta(['no'|S],S).

%-----------------------------------------------
infi(['pasar'|S],S).
infi(['asistir'|S], S).
infi(['viajar'|S], S).
infi(['ir'|S], S).

%-----------------------------------------------
pron(singular,primera,_,['me'|S],S).
pron(_,tercera,_,['se'|S],S).

%-----------------------------------------------
verbo(singular,primera,['estoy'|S],S).
verbo(singular,primera,['tengo'|S],S).
verbo(singular,_,['gustar�a'|S],S).
verbo(singular,tercera,['ubica'|S],S).
verbo(singular,primera,['ubico'|S],S).
verbo(singular, primera, ['voy'|S],S).
verbo(singular, primera, ['desplazo'|S], S).
verbo(plural, primera, ['vamos'|S], S).
verbo(plural, primera, ['estamos'|S], S).
verbo(plural, primera, ['tenemos'|S], S).
verbo(singular, primera, ['manejo'|S], S).
verbo(singular, primera, ['camino'|S], S).
verbo(singular, primera, ['dirijo'|S], S).
verbo(singular, primera, ['encuentro'|S], S).
verbo(singular, primera, ['dirijo'|S], S).

%-----------------------------------------------
prep(['en'|S],S).
prep(['a'|S], S).
prep(['para'|S], S).
prep(['por'|S], S).
prep(['hasta'|S],S).
prep(['hacia'|S], S).

%-----------------------------------------------
conector(['que'|S],S).


%-----------------------------------------------
saludo(['hola'|S],S).
saludo(['buenos d�as',S],S).
saludo(['buenas noches'|S],S).
saludo(['tuanis'|S], S).

%oracion([el,hombre,come,la,manzana],[]).
%------------------------------------------------
pClave([S0|S],S):- arista(S0,_,_).


% ------------------------------------------------------------------------
% Tercera parte
%

mainWLog():- tab(30),write("* * *"),nl,
    write("---  BIENVENIDO A WazeLog LA MEJOR FORMA DE LLEGAR A SU DESTINO"),
    nl,nl,write("- Por Favor ind�queme d�nde se encuentra."),nl,
    tab(5),write(">"),
    leerLinea(R0),aSintac(R0,G0),analizarRes(G0,O),

    nl,write("- Muy bien, �Cu�l es su destino?"),nl,
    tab(5),write(">"),
    leerLinea(R1),aSintac(R1,G1),write(G1),analizarRes(G1,D),

    nl,write("- Excelente, �Tiene alg�n destino intermedio?"),
    nl,tab(5),write(">"),
    leerLinea(R3),aSintac(R3,G3),analizarResSec(G3,L),nl,

    inversa([D|L],L0),buscarRuta(O,L0,Rf,Dis),
    write("- Su ruta ser�a "),imprimirLista(Rf,[]),write(" la distancia es "),
    write(Dis),write("km"),nl,
    write("- Gracias por usar WazeLog"),
    nl,nl,mainWLog().




imprimirLista([R|Resto],L):- write(R),write(","),tab(1), imprimirLista(Resto,L).

imprimirLista(L,L).
%-----------------------------------------------------------
aSintac(R0,G0):- frase(R0,[]), G0 = R0.

aSintac(_,G3):-write("No le entend� ni picha"),nl,
    write("Por favor, intente volver a escribi su respueta"),
    nl,tab(5),write(">"),
    leerLinea(R3),aSintac(R3,G3).

%-----------------------------------------------------------
% Recibe una lista que representa la respuesta del usuario
% y compruba si en la respuesta exsite un lugar del grafo.
analizarRes(R0,O):-buscarpClave(R0,O).

% Cuando no encuetra un lugar del grafo, pide que vuelva a escribir el
% lugar
analizarRes(_,O):- write("- Disculpa, no te he entendido"),nl,
    write("- Podr�as repetir el lugar que me has dicho"),nl,
    tab(5),write(">"),
    leerLinea(R0),aSintac(R0,G0),analizarRes(G0,O).

buscarpClave([S0|_],S0):- pClave([S0|_],[]),!.

buscarpClave([_|Resto],P):-buscarpClave(Resto,P).

% -----------------------------------------------------------
analizarResSec(['no'|_],_).

analizarResSec(R,L):- buscarPSec(R,L).

buscarPSec([S0|_],L):- pSec(S0),nl,
    write("�D�nde se encuentra "),
    nom(Num,Gen,[S0],[]), det(Num,Gen,_,[Det|_],[]),
    write(Det),tab(1),write(S0),write("?"),nl,
    tab(5),write(">"),
    leerLinea(R0),aSintac(R0,G0),analizarRes(G0,O),
    write("Tienes alg�n otro destino intermedio"),nl,
    tab(5),write(">"),
    leerLinea(Read1),aSintac(Read1,Grama1),
    analizarResSec(Grama1, Respuesta),
    concatenar([O],Respuesta,L).

buscarPSec([_|S0],L):- buscarPSec(S0,L).

pSec(S) :- nom(_, _,[S|_], _).
