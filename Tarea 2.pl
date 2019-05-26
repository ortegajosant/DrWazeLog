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
    get(Y), char_code(A,Y),
    atomic_concat(P,A,Result),
    leerLinea(Result,F,T).
leerLinea(P,F,T):- peek_code(X),X == 32,
    get0(X),
    leerLinea("",[P|F],T).
leerLinea(P,F,T):- peek_code(X),
    X == 46,get0(X),
    inversa([P|F],T),
    frase(T,[]).


% -----------------------------------------------------------------------
% Gramatica permitida en la conversacion.
% Analizador para el proyecto
%-----------------------------------------------
frase(S0,S):- oracion(S0,S).
frase(S0,S):- sintagma_prep(S0,S).
frase(S0,S):- respuesta(S0,S).
frase(S0,S):- saludos(S0,S1),
    oracion(S1,S).
frase(S0,S):-pClave(S0,S).
%-----------------------------------------------
saludos(S0,S):-saludo(S0,S1),
    nom(_,_,S1,S).
saludos(S0,S):-saludo(S0,S).

%-----------------------------------------------
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
det(singular,fenenino,tercera,['al'|S],S).
%-----------------------------------------------
nom(singular,masculino,['WazeLog'|S],S).
nom(singular,masculino,['Automercado'|S],S).
nom(singular,_,['Supermercado'|S],S).
%-----------------------------------------------
respuesta(['si'|S],S).
respuesta(['no'|S],S).
%-----------------------------------------------
infi(['pasar'|S],S).
%-----------------------------------------------
pron(singular,primera,_,['me'|S],S).
pron(_,tercera,_,['se'|S],S).
%-----------------------------------------------
verbo(singular,primera,['estoy'|S],S).
verbo(singular,primera,['tengo'|S],S).
verbo(singular,_,['gustaría'|S],S).
verbo(singular,tercera,['ubica'|S],S).
verbo(singular,primera,['ubico'|S],S).
%-----------------------------------------------
prep(['en'|S],S).
%-----------------------------------------------
conector(['que'|S],S).
%-----------------------------------------------
saludo(['Hola'|S],S).
saludo(['Buenos días',S],S).
saludo(['Buenas noches'|S],S).

%oracion([el,hombre,come,la,manzana],[]).
%------------------------------------------------
pClave([S0|S],S):-arista(S0,_,_).




% ------------------------------------------------------------------------
% Tercera parte
%

mainWLog():-write("BIENVENIDO a WazeLog la mejor lógica de llegar a su destino."),
    nl,nl,write("Por Favor indíqueme dónde se encuentra."),nl,
    leerLinea(_),
    nl,write("Muy bien, ¿Cuál es su destino?"),nl,
    leerLinea(_),
    nl,write("Excelente, ¿Tiene algún destino intermedio?"),nl,
    leerLinea(_),
    nl,write("¿Dónde se encuentra AutoMercado?"),nl,
    leerLinea(_),
    nl,write("¿Algún destino intermedio?"),nl,
    leerLinea(_),
    nl,write("Fin").
