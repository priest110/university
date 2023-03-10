\documentclass[a4paper]{article}
\usepackage[a4paper,left=3cm,right=2cm,top=2.5cm,bottom=2.5cm]{geometry}
\usepackage{palatino}
\usepackage[colorlinks=true,linkcolor=blue,citecolor=blue]{hyperref}
\usepackage{graphicx}
\usepackage{cp1819t}
\usepackage{subcaption}
\usepackage{adjustbox}
\usepackage{color}
%================= local x=====================================================%
\def\getGif#1{\includegraphics[width=0.3\textwidth]{cp1819t_media/#1.png}}
\let\uk=\emph
\def\aspas#1{``#1"}
%================= lhs2tex=====================================================%
%include polycode.fmt 
%format (div (x)(y)) = x "\div " y
%format succ = "\succ "
%format ==> = "\Longrightarrow "
%format map = "\map "
%format length = "\length "
%format fst = "\p1"
%format p1  = "\p1"
%format snd = "\p2"
%format p2  = "\p2"
%format Left = "i_1"
%format Right = "i_2"
%format i1 = "i_1"
%format i2 = "i_2"
%format >< = "\times"
%format >|<  = "\bowtie "
%format |-> = "\mapsto"
%format . = "\comp "
%format (kcomp (f)(g)) = f "\kcomp " g
%format -|- = "+"
%format conc = "\mathsf{conc}"
%format summation = "{\sum}"
%format (either (a) (b)) = "\alt{" a "}{" b "}"
%format (frac (a) (b)) = "\frac{" a "}{" b "}"
%format (uncurry f) = "\uncurry{" f "}"
%format (const f) = "\underline{" f "}"
%format TLTree = "\mathsf{TLTree}"
%format (lcbr (x)(y)) = "\begin{lcbr}" x "\\" y "\end{lcbr}"
%format (split (x) (y)) = "\conj{" x "}{" y "}"
%format (for (f) (i)) = "\for{" f "}\ {" i "}"
%format B_tree = "\mathsf{B}\mbox{-}\mathsf{tree} "
\def\ana#1{\mathopen{[\!(}#1\mathclose{)\!]}}
%format (cataA (f) (g)) = "\cata{" f "~" g "}_A"
%format (anaA (f) (g)) = "\ana{" f "~" g "}_A"
%format (cataB (f) (g)) = "\cata{" f "~" g "}_B"
%format (anaB (f) (g)) = "\ana{" f "~" g "}_B"
%format Either a b = a "+" b 
%format fmap = "\mathsf{fmap}"
%format NA   = "\textsc{na}"
%format NB   = "\textsc{nb}"
%format inT = "\mathsf{in}"
%format outT = "\mathsf{out}"
%format Null = "1"
%format (Prod (a) (b)) = a >< b
%format fF = "\fun F "
%format e1 = "e_1 "
%format e2 = "e_2 "
%format Dist = "\fun{Dist}"
%format IO = "\fun{IO}"
%format BTree = "\fun{BTree} "
%format LTree = "\mathsf{LTree}"
%format inNat = "\mathsf{in}"
%format (cataNat (g)) = "\cata{" g "}"
%format Nat0 = "\N_0"
%format muB = "\mu "
%format (frac (n)(m)) = "\frac{" n "}{" m "}"
%format (fac (n)) = "{" n "!}"
%format (underbrace (t) (p)) = "\underbrace{" t "}_{" p "}"
%format matrix = "matrix"
%format (bin (n) (k)) = "\Big(\vcenter{\xymatrix@R=1pt{" n "\\" k "}}\Big)"
%format `ominus` = "\mathbin{\ominus}"
%format % = "\mathbin{/}"
%format <-> = "{\,\leftrightarrow\,}"
%format <|> = "{\,\updownarrow\,}"
%format `minusNat`= "\mathbin{-}"
%format ==> = "\Rightarrow"
%format .==>. = "\Rightarrow"
%format .<==>. = "\Leftrightarrow"
%format .==. = "\equiv"
%format .<=. = "\leq"
%format .&&&. = "\wedge"
%format cdots = "\cdots "
%format pi = "\pi "
%format (expn (a) (n)) = "{" a "}^{" n "}"

%---------------------------------------------------------------------------

\title{
       	    C??lculo de Programas
\\
       	Trabalho Pr??tico
\\
       	MiEI+LCC --- 2018/19
}

\author{
       	\dium
\\
       	Universidade do Minho
}


\date\mydate

\makeindex
\newcommand{\rn}[1]{\textcolor{red}{#1}}
\begin{document}

\maketitle

\begin{center}\large
\begin{tabular}{ll}
\textbf{Grupo} nr. & 105
\\\hline
a83610 & Rui Nuno Borges Cruz Oliveira	
\\
a84475 & Ana Rita Miranda Rosendo 
\\
a85731 & Gon??alo Jos?? Azevedo Esteves 
\end{tabular}
\end{center}

\section{Pre??mbulo}

A disciplina de \CP\ tem como objectivo principal ensinar
a progra\-ma????o de computadores como uma disciplina cient??fica. Para isso
parte-se de um repert??rio de \emph{combinadores} que formam uma ??lgebra da
programa????o (conjunto de leis universais e seus corol??rios) e usam-se esses
combinadores para construir programas \emph{composicionalmente}, isto ??,
agregando programas j?? existentes.
  
Na sequ??ncia pedag??gica dos planos de estudo dos dois cursos que t??m
esta disciplina, restringe-se a aplica????o deste m??todo ?? programa????o
funcional em \Haskell. Assim, o presente trabalho pr??tico coloca os
alunos perante problemas concretos que dever??o ser implementados em
\Haskell.  H?? ainda um outro objectivo: o de ensinar a documentar
programas, valid??-los, e a produzir textos t??cnico-cient??ficos de
qualidade.

\section{Documenta????o} Para cumprir de forma integrada os objectivos
enunciados acima vamos recorrer a uma t??cnica de programa\-????o dita
``\litp{liter??ria}'' \cite{Kn92}, cujo princ??pio base ?? o seguinte:
\begin{quote}\em Um programa e a sua documenta????o devem coincidir.
\end{quote} Por outras palavras, o c??digo fonte e a documenta????o de um
programa dever??o estar no mesmo ficheiro.

O ficheiro \texttt{cp1819t.pdf} que est?? a ler ?? j?? um exemplo de \litp{programa????o
liter??ria}: foi gerado a partir do texto fonte \texttt{cp1819t.lhs}\footnote{O
suffixo `lhs' quer dizer \emph{\lhaskell{literate Haskell}}.} que encontrar??
no \MaterialPedagogico\ desta disciplina descompactando o ficheiro \texttt{cp1819t.zip}
e executando
\begin{Verbatim}[fontsize=\small]
    $ lhs2TeX cp1819t.lhs > cp1819t.tex
    $ pdflatex cp1819t
\end{Verbatim}
em que \href{https://hackage.haskell.org/package/lhs2tex}{\texttt\LhsToTeX} ??
um pre-processador que faz ``pretty printing''
de c??digo Haskell em \Latex\ e que deve desde j?? instalar executando
\begin{Verbatim}[fontsize=\small]
    $ cabal install lhs2tex
\end{Verbatim}
Por outro lado, o mesmo ficheiro \texttt{cp1819t.lhs} ?? execut??vel e cont??m
o ``kit'' b??sico, escrito em \Haskell, para realizar o trabalho. Basta executar
\begin{Verbatim}[fontsize=\small]
    $ ghci cp1819t.lhs
\end{Verbatim}

%if False
\begin{code}
{-# OPTIONS_GHC -XNPlusKPatterns #-}
{-# LANGUAGE GeneralizedNewtypeDeriving, DeriveDataTypeable, FlexibleInstances #-}
module Cp1819t where 
import Cp
import List  hiding (fac)
import Nat  
import Data.List hiding (find)
import Data.Typeable
import Data.Ratio
import Data.Bifunctor
import Data.Maybe
import Data.Matrix hiding ((!))
import Test.QuickCheck hiding ((><),choose)
import qualified Test.QuickCheck as QuickCheck
import System.Random  hiding (split)
import System.Process
import GHC.IO.Exception
import qualified Graphics.Gloss as G
import Control.Monad
import Control.Applicative hiding ((<|>))
import Data.Either
import Exp
\end{code}
%endif

\noindent Abra o ficheiro \texttt{cp1819t.lhs} no seu editor de texto preferido
e verifique que assim ??: todo o texto que se encontra dentro do ambiente
\begin{quote}\small\tt
\verb!\begin{code}!
\\ ... \\
\verb!\end{code}!
\end{quote}
vai ser seleccionado pelo \GHCi\ para ser executado.

\section{Como realizar o trabalho}
Este trabalho te??rico-pr??tico deve ser realizado por grupos de tr??s alunos.
Os detalhes da avalia????o (datas para submiss??o do relat??rio e sua defesa
oral) s??o os que forem publicados na \cp{p??gina da disciplina} na \emph{internet}.

Recomenda-se uma abordagem participativa dos membros do grupo
de trabalho por forma a poderem responder ??s quest??es que ser??o colocadas
na \emph{defesa oral} do relat??rio.

Em que consiste, ent??o, o \emph{relat??rio} a que se refere o par??grafo anterior?
?? a edi????o do texto que est?? a ser lido, preenchendo o anexo \ref{sec:resolucao}
com as respostas. O relat??rio dever?? conter ainda a identifica????o dos membros
do grupo de trabalho, no local respectivo da folha de rosto.

Para gerar o PDF integral do relat??rio deve-se ainda correr os comando seguintes,
que actualizam a bibliografia (com \Bibtex) e o ??ndice remissivo (com \Makeindex),
\begin{Verbatim}[fontsize=\small]
    $ bibtex cp1819t.aux
    $ makeindex cp1819t.idx
\end{Verbatim}
e recompilar o texto como acima se indicou. Dever-se-?? ainda instalar o utilit??rio
\QuickCheck,
que ajuda a validar programas em \Haskell\ e a biblioteca \gloss{Gloss} para
gera????o de gr??ficos 2D:
\begin{Verbatim}[fontsize=\small]
    $ cabal install QuickCheck gloss
\end{Verbatim}
Para testar uma propriedade \QuickCheck~|prop|, basta invoc??-la com o comando:
\begin{verbatim}
    > quickCheck prop
    +++ OK, passed 100 tests.
\end{verbatim}

Qualquer programador tem, na vida real, de ler e analisar (muito!) c??digo
escrito por outros. No anexo \ref{sec:codigo} disponibiliza-se algum
c??digo \Haskell\ relativo aos problemas que se seguem. Esse anexo dever??
ser consultado e analisado ?? medida que isso for necess??rio.

\Problema

Um compilador ?? um programa que traduz uma linguagem dita de
\emph{alto n??vel} numa linguagem (dita de \emph{baixo n??vel}) que
seja execut??vel por uma m??quina.
Por exemplo, o \gcc{GCC} compila C/C++ em c??digo objecto que
corre numa variedade de arquitecturas.

Compiladores s??o normalmente programas complexos.
Constam essencialmente de duas partes:
o \emph{analisador sint??tico} que l?? o texto de entrada
(o programa \emph{fonte} a compilar) e cria uma sua representa????o
interna, estruturada em ??rvore;
e o \emph{gerador de c??digo} que converte essa representa????o interna
em c??digo execut??vel.
Note-se que tal representa????o interm??dia pode ser usada para outros fins,
por exemplo,
para gerar uma listagem de qualidade (\uk{pretty print}) do programa fonte.

O projecto de compiladores ?? um assunto complexo que
ser?? assunto de outras disciplinas.
Neste trabalho pretende-se apenas fazer uma introdu????o ao assunto,
mostrando como tais programas se podem construir funcionalmente ?? custa de
cata/ana/hilo-morfismos da linguagem em causa.

Para cumprirmos o nosso objectivo, a linguagem desta quest??o ter?? que ser, naturalmente,
muito simples: escolheu-se a das express??es aritm??ticas com inteiros,
\eg\ \( 1 + 2 \), \( 3 * (4 + 5) \) etc.
Como representa????o interna adopta-se o seguinte tipo polinomial, igualmente simples:
%
\begin{spec}
data Expr = Num Int | Bop Expr Op Expr 
data Op = Op String
\end{spec}

\begin{enumerate}
\item
Escreva as defini????es dos \{cata, ana e hilo\}-morfismos deste tipo de dados
segundo o m??todo ensinado nesta disciplina (recorde m??dulos como \eg\ |BTree| etc).
\item
Como aplica????o do m??dulo desenvolvido no ponto 1,
defina como \{cata, ana ou hilo\}-morfismo a fun????o seguinte:
\begin{itemize}
\item |calcula :: Expr -> Int| que calcula o valor
de uma express??o;
\begin{propriedade}
O valor zero ?? um elemento neutro da adi????o.
\begin{code}
prop_neutro1 :: Expr -> Bool
prop_neutro1 = calcula . addZero .==. calcula where
            addZero e = Bop (Num 0) (Op "+") e
prop_neutro2 :: Expr -> Bool
prop_neutro2 = calcula . addZero .==. calcula where
            addZero e = Bop e (Op "+") (Num 0) 
\end{code}
\end{propriedade}  
\begin{propriedade}
As opera????es de soma e multiplica????o s??o comutativas.
\begin{code}
prop_comuta = calcula . mirror .==. calcula where
            mirror = cataExpr (either Num g2)
            g2 = (uncurry (uncurry Bop)) . (swap >< id) . assocl . (id >< swap)
\end{code}
\end{propriedade}  
\end{itemize}
\item
Defina como \{cata, ana ou hilo\}-morfismos as fun????es
\begin{itemize}
\item |compile :: String -> Codigo| - trata-se do compilador propriamente
      dito. Dever?? ser gerado c??digo posfixo para uma m??quina elementar
      de \pda{stack}. O tipo |Codigo| pode ser definido ?? escolha.
      D??o-se a seguir exemplos de comportamentos aceit??veis para esta
      fun????o:
\begin{verbatim}
Tp4> compile "2+4"
["PUSH 2", "PUSH 4", "ADD"]
Tp4> compile "3*(2+4)"
["PUSH 3", "PUSH 2", "PUSH 4", "ADD", "MUL"]
Tp4> compile "(3*2)+4"
["PUSH 3", "PUSH 2", "MUL", "PUSH 4", "ADD"]
Tp4> 
\end{verbatim}
\item |show' :: Expr -> String| - gera a representa????o textual
      de uma |Expr| pode encarar-se como o \uk{pretty printer}
      associado ao nosso compilador
\begin{propriedade}
Em anexo, ?? fornecido o c??digo da fun????o |readExp|, que ?? ``inversa" da fun????o |show'|,
tal como a propriedade seguinte descreve:
\begin{code}
prop_inv :: Expr -> Bool
prop_inv = p1 . head . readExp . show' .==. id
\end{code}
\end{propriedade}  
\end{itemize}
%% \item Generalize o tipo |Expr| de forma a admitir operadores
%% un??rios (\eg\ \(-5\)) e repita os exerc??cios dos pontos anteriores.
\end{enumerate}

\paragraph{Valoriza????o}
Em anexo ?? apresentado c??digo \Haskell\ que permite declarar
|Expr| como inst??ncia da classe |Read|. Neste contexto,  
|read| pode ser vista como o analisador
sint??tico do nosso min??sculo compilador de express??es aritm??ticas.

Analise o c??digo apresentado, corra-o e escreva no seu relat??rio uma explica????o
\textbf{breve} do seu funcionamento, que dever?? saber defender aquando da
apresenta????o oral do relat??rio.

Exprima ainda o analisador sint??tico |readExp| como um anamorfismo.

\Problema

Pretende-se neste problema definir uma linguagem gr??fica \aspas{brinquedo}
a duas dimens??es (2D) capaz de especificar e desenhar agrega????es de
caixas que cont??m informa????o textual.
Vamos designar essa linguagem por |L2D| e vamos defini-la como um tipo
 em \Haskell:
\begin{code}
type L2D = X Caixa Tipo 
\end{code}
onde |X| ?? a estrutura de dados
\begin{code}
data X a b = Unid a | Comp b (X a b) (X a b) deriving Show
\end{code}
e onde:
\begin{code}
type Caixa = ((Int,Int),(Texto,G.Color))
type Texto = String
\end{code}
Assim, cada caixa de texto ?? especificada pela sua largura, altura, o seu
texto e a sua c??r.\footnote{Pode relacionar |Caixa| com as caixas de texto usadas
nos jornais ou com \uk{frames} da linguagem \Html\ usada na Internet.}
Por exemplo,
\begin{spec}
((200,200),("Caixa azul",col_blue))
\end{spec}
designa a caixa da esquerda da figura \ref{fig:L2D}.

O que a linguagem |L2D| faz ?? agregar tais caixas tipogr??ficas
umas com as outras segundo padr??es especificados por v??rios
\aspas{tipos}, a saber,
\begin{code}
data Tipo = V | Vd | Ve | H | Ht | Hb 
\end{code}
com o seguinte significado:
\begin{itemize}
\item[|V|] - agrega????o vertical alinhada ao centro
\item[|Vd|] - agrega????o vertical justificada ?? direita
\item[|Ve|] - agrega????o vertical justificada ?? esquerda
\item[|H|] - agrega????o horizontal alinhada ao centro
\item[|Hb|] - agrega????o horizontal alinhada pela base
\item[|Ht|] - agrega????o horizontal alinhada pelo topo
\end{itemize}
Como |L2D| instancia o par??metro |b| de |X| com
|Tipo|, ?? f??cil de ver que cada \aspas{frase} da linguagem
|L2D| ?? representada por uma ??rvore bin??ria em que cada n??
indica qual o tipo de agrega????o a aplicar ??s suas duas sub-??rvores.
%
Por exemplo, a frase
\begin{code}
ex2= Comp Hb  (Unid ((100,200),("A",col_blue)))
              (Unid ((50,50),("B",col_green)))
\end{code}
dever?? corresponder ?? imagem da direita da figura \ref{fig:L2D}.
E poder-se-?? ir t??o longe quando a linguagem o permita. Por exemplo, pense na
estrutura da frase que representa o \uk{layout} da figura \ref{fig:L2D1}.

\begin{figure}
\centering
\begin{picture}(190.00,130.00)(-15,-15)
\put(00.00,0.00){$(0,0)$}
\put(80.00,50.00){$(200,200)$}
\put(20.00,-10.00){
	\includegraphics[width=70\unitlength]{cp1819t_media/ex3.png}
}
\end{picture}
%
\includegraphics[width=0.2\textwidth]{cp1819t_media/ex2.png}
%
\caption{Caixa simples e caixa composta.\label{fig:L2D}}
\end{figure}

\begin{figure}
\centering
\includegraphics[width=0.6\textwidth]{cp1819t_media/ex.png}
\caption{\uk{Layout} feito de v??rias caixas coloridas.\label{fig:L2D1}}
\end{figure}

?? importante notar que cada ``caixa'' n??o disp??e informa????o relativa
ao seu posicionamento final na figura. De facto, ?? a posi????o relativa
que deve ocupar face ??s restantes caixas que ir?? determinar a sua
posi????o final. Este ?? um dos objectivos deste trabalho:
\emph{calcular o posicionamento absoluto de cada uma das caixas por forma a
respeitar as restri????es impostas pelas diversas agrega????es}. Para isso vamos
considerar um tipo de dados que comporta a informa????o de todas as
caixas devidamente posicionadas (i.e. com a informa????o adicional da
origem onde a caixa deve ser colocada).

\begin{spec}
type Fig = [(Origem,Caixa)]
type Origem = (Float, Float)
\end{spec}
%
A informa????o mais relevante deste tipo ?? a referente ?? lista de
``caixas posicionadas'' (tipo |(Origem,Caixa)|). Regista-se a?? a origem
da caixa que, com a informa????o da sua altura e comprimento, permite
definir todos os seus pontos (consideramos as caixas sempre paralelas
aos eixos). 

\begin{enumerate}
\item Forne??a a defini????o da fun????o |calc_origems|, que calcula as
coordenadas iniciais das caixas no plano:
\begin{spec}
calc_origems :: (L2D,Origem) -> X (Caixa,Origem) ()
\end{spec}
\item Forne??a agora a defini????o da fun????o |agrup_caixas|, que agrupa
todas as caixas e respectivas origens numa s?? lista:
\begin{spec}
agrup_caixas :: X (Caixa,Origem) () -> Fig
\end{spec}
\end{enumerate}

Um segundo problema neste projecto ?? \emph{descobrir como visualizar a
informa????o gr??fica calculada por |desenho|}. A nossa estrat??gia para
superar o problema baseia-se na biblioteca \gloss{Gloss}, que permite a gera????o
de gr??ficos 2D. Para tal disponibiliza-se a fun????o
\begin{spec}
crCaixa :: Origem  -> Float -> Float -> String -> G.Color -> G.Picture
\end{spec}
que cria um rect??ngulo com base numa coordenada, um valor para a largura, um valor
para a altura, um texto que ir?? servir de etiqueta, e a cor pretendida.
Disponibiliza-se tamb??m a fun????o
\begin{spec}
display :: G.Picture -> IO ()
\end{spec}
que dado um valor do tipo |G.picture| abre uma janela com esse valor desenhado. O objectivo
final deste exerc??cio ?? implementar ent??o uma fun????o 
\begin{spec}
mostra_caixas :: (L2D,Origem) -> IO ()
\end{spec}
que dada uma frase da linguagem |L2D| e coordenadas iniciais apresenta
o respectivo desenho no ecr??.
%
\textbf{Sugest??o}: 
Use a fun????o |G.pictures| disponibilizada na biblioteca \gloss{Gloss}.

\Problema

Nesta disciplina estudou-se como fazer \pd{programa????o din??mica} por c??lculo,
recorrendo ?? lei de recursividade m??tua.\footnote{Lei (\ref{eq:fokkinga})
em \cite{Ol18}, p??gina \pageref{eq:fokkinga}.}

Para o caso de fun????es sobre os n??meros naturais (|Nat0|, com functor |fF
X = 1 + X|) ?? f??cil derivar-se da lei que foi estudada uma
	\emph{regra de algibeira}
	\label{pg:regra}
que se pode ensinar a programadores que n??o tenham estudado
\cp{C??lculo de Programas}. Apresenta-se de seguida essa regra, tomando como exemplo o
c??lculo do ciclo-\textsf{for} que implementa a fun????o de Fibonacci, recordar
o sistema
\begin{spec}
fib 0 = 1
fib(n+1) = f n

f 0 = 1
f (n+1) = fib n + f n
\end{spec}
Obter-se-?? de imediato
\begin{code}
fib' = p1 . for loop init where
   loop(fib,f)=(f,fib+f)
   init = (1,1)
\end{code}
usando as regras seguintes:
\begin{itemize}
\item	O corpo do ciclo |loop| ter?? tantos argumentos quanto o n??mero de fun????es mutuamente recursivas.
\item	Para as vari??veis escolhem-se os pr??prios nomes das fun????es, pela ordem
que se achar conveniente.\footnote{Podem obviamente usar-se outros s??mbolos, mas numa primeiraleitura
d?? jeito usarem-se tais nomes.}
\item	Para os resultados v??o-se buscar as express??es respectivas, retirando a vari??vel |n|.
\item	Em |init| coleccionam-se os resultados dos casos de base das fun????es, pela mesma ordem.
\end{itemize}
Mais um exemplo, envolvendo polin??mios no segundo grau a $x^2 + b x + c$ em |Nat0|.
Seguindo o m??todo estudado nas aulas\footnote{Sec????o 3.17 de \cite{Ol18}.},
de $f\ x = a x^2 + b x + c$ derivam-se duas fun????es mutuamente recursivas:
\begin{spec}
f 0 = c
f (n+1) = f n + k n

k 0 = a + b
k(n+1) = k n + 2 a
\end{spec}
Seguindo a regra acima, calcula-se de imediato a seguinte implementa????o, em Haskell:
\begin{code}
f' a b c = p1 . for loop init where
  loop(f,k) = (f+k,k+2*a)
  init = (c,a+b) 
\end{code}

Qual ?? o assunto desta quest??o, ent??o? Considerem f??rmula que d?? a s??rie de Taylor da
fun????o coseno:
\begin{eqnarray*}
	cos\ x = \sum_{i=0}^\infty \frac{(-1)^i}{(2i)!} x^{2i}
\end{eqnarray*}
Pretende-se o ciclo-\textsf{for} que implementa a fun????o 
|cos' x n| que d?? o valor dessa s??rie tomando |i| at?? |n| inclusiv??:
\begin{spec}
cos' x = cdots . for loop init where cdots
\end{spec}
%
\textbf{Sugest??o}: Come??ar por estudar muito bem o processo de c??lculo dado
no anexo \ref{sec:recmul} para o problema (semelhante) da fun????o exponencial.

\begin{propriedade}
Testes de que |cos' x| calcula bem o coseno de |pi| e o coseno de |pi/2|:
\begin{code}
prop_cos1 n = n >= 10 ==> abs(cos pi - cos' pi n) < 0.001
prop_cos2 n = n >= 10 ==> abs(cos (pi/2) - cos' (pi/2) n) < 0.001
\end{code}
\end{propriedade}

\paragraph{Valoriza????o} Transliterar |cos'| para a linguagem C; compilar
e testar o c??digo. Conseguia, por intui????o apenas, chegar a esta fun????o?

\Problema

Pretende-se nesta quest??o desenvolver uma biblioteca de fun????es para
manipular \emph{sistemas de ficheiros} gen??ricos.
Um sistema de ficheiros ser?? visto como uma associa????o de \emph{nomes}
a ficheiros ou \emph{directorias}.
Estas ??ltimas ser??o vistas como sub-sistemas de ficheiros e assim
recursivamente.
Assumindo que |a| ?? o tipo dos identificadores dos ficheiros e
directorias, e que |b| ?? o tipo do conte??do dos ficheiros,
podemos definir um tipo indutivo de dados para representar sistemas de
ficheiros da seguinte forma:
\begin{code}
data FS a b = FS [(a,Node a b)] deriving (Eq,Show)
data Node a b = File b | Dir (FS a b) deriving (Eq,Show)
\end{code}
Um caminho (\emph{path}) neste sistema de ficheiros pode ser representado pelo
seguinte tipo de dados:
\begin{code}
type Path a = [a]
\end{code}
Assumindo estes tipos de dados, o seguinte termo
\begin{spec}
FS [("f1", File "Ola"),
    ("d1", Dir (FS [("f2", File "Ole"),
                    ("f3", File "Ole")
                   ]))
   ]
\end{spec}
representar?? um sistema de ficheiros em cuja ra??z temos um ficheiro chamado
|f1| com conte??do |"Ola"| e uma directoria chamada |"d1"| constitu??da por dois
ficheiros, um chamado |"f2"| e outro chamado |"f3"|, ambos com conte??do |"Ole"|.
%
Neste caso, tanto o tipo dos identificadores como o tipo do conte??do dos
ficheiros ?? |String|. No caso geral, o conte??do de um ficheiro ?? arbitr??rio:
pode ser um bin??rio, um texto, uma colec????o de dados, etc.

A defini????o das usuais fun????es |inFS| e |recFS| para este tipo ?? a seguinte:
\begin{code}
inFS = FS . map (id >< inNode) 
inNode = either File Dir

recFS f = baseFS id id f
\end{code}
Suponha que se pretende definir como um |catamorfismo| a fun????o que
conta o n??mero de ficheiros existentes num sistema de ficheiros. Uma
poss??vel defini????o para esta fun????o seria:
\begin{code}
conta :: FS a b -> Int
conta = cataFS (sum . map ((either (const 1) id) . snd))
\end{code}
O que ?? para fazer:
\begin{enumerate}
\item Definir as fun????es |outFS|, |baseFS|, |cataFS|, |anaFS| e |hyloFS|.

\item Apresentar, no relat??rio, o diagrama de |cataFS|.

\item Definir as seguintes fun????es para manipula????o de sistemas de
  ficheiros usando, obrigatoriamente, catamorfismos, anamorfismos ou
  hilomorfismos:

  \begin{enumerate}
  \item Verifica????o da integridade do sistema de ficheiros (i.e. verificar
    que n??o existem identificadores repetidos dentro da mesma directoria). \\
    |check :: FS a b -> Bool|
  \begin{propriedade}
    A integridade de um sistema de ficheiros n??o depende da ordem em que os    
    ??ltimos s??o listados na sua directoria:
\begin{code}
prop_check :: FS String String -> Bool
prop_check = check . (cataFS (inFS . reverse)).==. check
\end{code}
  \end{propriedade}  
  \item Recolha do conte??do de todos os ficheiros num arquivo indexado pelo \emph{path}.\\
    |tar :: FS a b -> [(Path a, b)]|
  \begin{propriedade}
    O n??mero de ficheiros no sistema deve ser igual ao n??mero de ficheiros
    listados pela fun????o |tar|.
\begin{code}
prop_tar :: FS String String -> Bool
prop_tar = length . tar .==. conta 
\end{code}
  \end{propriedade}  
  \item Transforma????o de um arquivo com o conte??do dos ficheiros
    indexado pelo \emph{path} num sistema de ficheiros.\\
    |untar :: [(Path a, b)] -> FS a b| \\
  \textbf{Sugest??o}: Use a fun????o |joinDupDirs| para juntar directorias que estejam na mesma
  pasta e que possuam o mesmo identificador.
  \begin{propriedade}
    A composi????o |tar . untar| preserva o n??mero de ficheiros no sistema.
\begin{code}
prop_untar :: [(Path String, String)] -> Property
prop_untar = validPaths .==>. ((length . tar . untar) .==. length)
validPaths :: [(Path String, String)] -> Bool
validPaths = (== 0) . length . (filter (\(a,_) -> length a == 0))
\end{code}
\end{propriedade}  
  \item Localiza????o de todos os \emph{paths} onde existe um
    determinado ficheiro.\\
    |find :: a -> FS a b -> [Path a]|
  \begin{propriedade}
    A composi????o |tar . untar| preserva todos os ficheiros no sistema.
\begin{code}
prop_find :: String -> FS String String -> Bool
prop_find = curry $ 
      length . (uncurry find) .==. length . (uncurry find) . (id >< (untar . tar))
\end{code}
  \end{propriedade}  
  \item Cria????o de um novo ficheiro num determinado \emph{path}.\\
    |new :: Path a -> b -> FS a b -> FS a b|
  \begin{propriedade}
A adi????o de um ficheiro n??o existente no sistema n??o origina ficheiros duplicados.
\begin{code}
prop_new :: ((Path String,String), FS String String) -> Property
prop_new = ((validPath .&&&. notDup) .&&&. (check . p2)) .==>. 
      (checkFiles . (uncurry (uncurry new)))  where
      validPath = (/=0) . length . p1 . p1
      notDup = not . (uncurry elem) . (p1 >< ((fmap p1) . tar))
\end{code}
\textbf{Quest??o}: Supondo-se que no c??digo acima se substitui a propriedade
|checkFiles| pela propriedade mais fraca |check|, ser?? que a propriedade
|prop_new| ainda ?? v??lida? Justifique a sua resposta. 
\end{propriedade}
 
\begin{propriedade}
	A listagem de ficheiros logo ap??s uma adi????o nunca poder?? ser menor que a listagem
	de ficheiros antes dessa mesma adi????o.
\begin{code}
prop_new2 :: ((Path String,String), FS String String) -> Property
prop_new2 = validPath .==>. ((length . tar . p2) .<=. (length . tar . (uncurry (uncurry new)))) where
      validPath = (/=0) . length . p1 . p1
\end{code}
  \end{propriedade}  
  \item Duplica????o de um ficheiro.\\
    |cp :: Path a -> Path a -> FS a b -> FS a b|
  \begin{propriedade}
    A listagem de ficheiros com um dado nome n??o diminui ap??s uma duplica????o.
\begin{code}
prop_cp :: ((Path String, Path String),  FS String String) -> Bool
prop_cp =   length . tar . p2 .<=. length . tar . (uncurry (uncurry cp))
\end{code}
  \end{propriedade}  
  \item Elimina????o de um ficheiro.\\
    |rm :: Path a -> FS a b -> FS a b|

  \textbf{Sugest??o}: Construir um anamorfismo |nav :: (Path a, FS a b) -> FS a b|
  que navegue por um sistema de ficheiros tendo como base o |path| dado como argumento.
    \begin{propriedade}
    Remover duas vezes o mesmo ficheiro tem o mesmo efeito que o remover apenas uma vez.
\begin{code}
prop_rm :: (Path String, FS String String) -> Bool
prop_rm = (uncurry rm ) . (split p1 (uncurry rm)) .==. (uncurry rm)
\end{code}
\end{propriedade}
\begin{propriedade}
Adicionar um ficheiro e de seguida remover o mesmo n??o origina novos ficheiros no sistema.
\begin{code}
prop_rm2 :: ((Path String,String), FS String String) -> Property
prop_rm2 = validPath  .==>. ((length . tar . (uncurry rm) . (split (p1. p1) (uncurry (uncurry new)))) 
         .<=. (length . tar . p2)) where
         validPath = (/=0) . length . p1 . p1
\end{code}
\end{propriedade}  
  \end{enumerate}
\end{enumerate}

\paragraph{Valoriza????o} 

Definir uma fun????o para visualizar em \graphviz{Graphviz}
a estrutura de um sistema de ficheiros. A Figura~\ref{ex_prob1}, por exemplo,
apresenta a estrutura de um sistema com precisamente dois ficheiros dentro
de uma directoria chamada |"d1"|. 

Para realizar este exerc??cio ser?? necess??rio apenas  escrever o anamorfismo
\begin{quote}
|cFS2Exp :: (a, FS a b) -> (Exp () a)| 
\end{quote}
que converte a estrutura de um sistema de ficheiros numa ??rvore de express??es
descrita em \href{http://wiki.di.uminho.pt/twiki/pub/Education/CP/MaterialPedagogico/Exp.hs}{Exp.hs}.
A fun????o |dotFS| depois tratar?? de passar a estrutura do sistema de ficheiros para o visualizador.
\begin{figure}
\centering
\includegraphics[scale=0.5]{cp1819t_media/fs.png}
\caption{Exemplo de um sistema de ficheiros visualizado em \graphviz{Graphviz}.}
\label{ex_prob1}
\end{figure}

%----------------- Programa, bibliotecas e c??digo auxiliar --------------------%

\newpage

\part*{Anexos}

\appendix

\section{Como exprimir c??lculos e diagramas em LaTeX/lhs2tex}
Estudar o texto fonte deste trabalho para obter o efeito:\footnote{Exemplos tirados de \cite{Ol18}.} 
\begin{eqnarray*}
\start
	|id = split f g|
%
\just\equiv{ universal property }
%
        |lcbr(
		p1 . id = f
	)(
		p2 . id = g
	)|
%
\just\equiv{ identity }
%
        |lcbr(
		p1 = f
	)(
		p2 = g
	)|
\qed
\end{eqnarray*}

Os diagramas podem ser produzidos recorrendo ?? \emph{package} \LaTeX\ 
\href{https://ctan.org/pkg/xymatrix}{xymatrix}, por exemplo: 
\begin{eqnarray*}
\xymatrix@@C=2cm{
    |Nat0|
           \ar[d]_-{|cataNat g|}
&
    |1 + Nat0|
           \ar[d]^{|id + (cataNat g)|}
           \ar[l]_-{|inNat|}
\\
     |B|
&
     |1 + B|
           \ar[l]^-{|g|}
}
\end{eqnarray*}

\section{Programa????o din??mica por recursividade m??ltipla}\label{sec:recmul}
Neste anexo d??o-se os detalhes da resolu????o do Exerc??cio \ref{ex:exp} dos apontamentos da
disciplina\footnote{Cf.\ \cite{Ol18}, p??gina \pageref{ex:exp}.},
onde se pretende implementar um ciclo que implemente
o c??lculo da aproxima????o at?? |i=n| da fun????o exponencial $exp\ x = e^x$
via s??rie de Taylor:
\begin{eqnarray}
	exp\ x 
& = &
	\sum_{i=0}^{\infty} \frac {x^i} {i!}
\end{eqnarray}
Seja $e\ x\ n = \sum_{i=0}^{n} \frac {x^i} {i!}$ a fun????o que d?? essa aproxima????o.
?? f??cil de ver que |e x 0 = 1| e que $|e x (n+1)| = |e x n| + \frac {x^{n+1}} {(n+1)!}$.
Se definirmos $|h x n| = \frac {x^{n+1}} {(n+1)!}$ teremos |e x| e |h x| em recursividade
m??tua. Se repetirmos o processo para |h x n| etc obteremos no total tr??s fun????es nessa mesma
situa????o:
\begin{spec}
e x 0 = 1
e x (n+1) = h x n + e x n

h x 0 = x
h x (n+1) = x/(s n) * h x n

s 0 = 2
s (n+1) = 1 + s n
\end{spec}
Segundo a \emph{regra de algibeira} descrita na p??gina \ref{pg:regra} deste enunciado,
ter-se-??, de imediato:
\begin{code}
e' x = prj . for loop init where
     init = (1,x,2)
     loop(e,h,s)=(h+e,x/s*h,1+s)
     prj(e,h,s) = e
\end{code}

\section{C??digo fornecido}\label{sec:codigo}

\subsection*{Problema 1}
Tipos:
\begin{code}
data Expr = Num Int 
               | Bop Expr Op Expr  deriving  (Eq,Show)

data Op = Op String deriving (Eq,Show)

type Codigo = [String]
\end{code}
Functor de base:
\begin{code}
baseExpr f g = id -|- (f >< (g >< g))
\end{code}
Inst??ncias:
\begin{code}
instance Read Expr where
   readsPrec _ = readExp
\end{code}
Read para Exp's:
\begin{code}
readOp :: String -> [(Op,String)]
readOp input = do 
                 (x,y) <- lex input
                 return ((Op x),y)

readNum :: ReadS Expr
readNum  = (map (\ (x,y) -> ((Num x), y))).reads

readBinOp :: ReadS Expr
readBinOp = (map (\ ((x,(y,z)),t) -> ((Bop x y z),t))) .
                   ((readNum `ou` (pcurvos readExp))
                    `depois` (readOp `depois` readExp))

readExp :: ReadS Expr
readExp = readBinOp `ou` (
          readNum `ou` (
          pcurvos readExp))
\end{code}
Combinadores:
\begin{code}

depois :: (ReadS a) -> (ReadS b) -> ReadS (a,b)
depois _ _ [] = []
depois r1 r2 input = [((x,y),i2) | (x,i1) <- r1 input , 
                                   (y,i2) <- r2 i1]

readSeq :: (ReadS a) -> ReadS [a]
readSeq r input 
  = case (r input) of
    [] -> [([],input)]
    l -> concat (map continua l)
         where continua (a,i) = map (c a) (readSeq r i)
               c x (xs,i) = ((x:xs),i)                     

ou :: (ReadS a) -> (ReadS a) -> ReadS a
ou r1 r2 input = (r1 input) ++ (r2 input)

senao :: (ReadS a) -> (ReadS a) -> ReadS a
senao r1 r2 input = case (r1 input) of
                     [] -> r2 input
                     l  -> l

readConst :: String -> ReadS String
readConst c = (filter ((== c).fst)) . lex

pcurvos = parentesis '(' ')'
prectos = parentesis '[' ']'
chavetas = parentesis '{' '}'

parentesis :: Char -> Char -> (ReadS a) -> ReadS a
parentesis _ _ _ [] = []
parentesis ap pa r input 
  = do 
      ((_,(x,_)),c) <- ((readConst [ap]) `depois` (
                        r                `depois` (
                        readConst [pa])))           input
      return (x,c)
\end{code}

\subsection*{Problema 2}
Tipos:
\begin{code}
type Fig = [(Origem,Caixa)]
type Origem = (Float, Float)
\end{code}
``Helpers":
\begin{code}
col_blue = G.azure
col_green = darkgreen

darkgreen = G.dark (G.dark G.green)
\end{code}
Exemplos:
\begin{code}
ex1Caixas = G.display (G.InWindow "Problema 4" (400, 400) (40, 40)) G.white $
          crCaixa (0,0) 200 200 "Caixa azul" col_blue

ex2Caixas =  G.display (G.InWindow "Problema 4" (400, 400) (40, 40)) G.white $
          caixasAndOrigin2Pict ((Comp Hb bbox gbox),(0.0,0.0)) where
          bbox = Unid ((100,200),("A",col_blue))
          gbox = Unid ((50,50),("B",col_green))

ex3Caixas = G.display (G.InWindow "Problema 4" (400, 400) (40, 40)) G.white mtest where
          mtest = caixasAndOrigin2Pict $ (Comp Hb (Comp Ve bot top) (Comp Ve gbox2 ybox2), (0.0,0.0))
          bbox1 = Unid ((100,200),("A",col_blue))
          bbox2 = Unid ((150,200),("E",col_blue))
          gbox1 = Unid ((50,50),("B",col_green))
          gbox2 = Unid ((100,300),("F",col_green))
          rbox1 = Unid ((300,50),("C",G.red))
          rbox2 = Unid ((200,100),("G",G.red))
          wbox1 = Unid ((450,200),("",G.white)) 
          ybox1 = Unid ((100,200),("D",G.yellow))
          ybox2 = Unid ((100,300),("H",G.yellow))
          bot = Comp Hb wbox1 bbox2 
          top = (Comp Ve (Comp Hb bbox1 gbox1) (Comp Hb rbox1 (Comp H ybox1 rbox2)))
\end{code}
A seguinte fun????o cria uma caixa a partir dos seguintes par??metros: origem,
largura, altura, etiqueta e c??r de preenchimento.
\begin{code}
crCaixa :: Origem  -> Float -> Float -> String -> G.Color -> G.Picture
crCaixa (x,y) w h l c = G.Translate (x+(w/2)) (y+(h/2)) $  G.pictures [caixa, etiqueta] where
                    caixa = G.color c (G.rectangleSolid w h)
                    etiqueta = G.translate calc_trans_x calc_trans_y $ 
                             G.Scale calc_scale calc_scale $ G.color G.black $ G.Text l
                    calc_trans_x = (- ((fromIntegral (length l)) * calc_scale) / 2 )*base_shift_x
                    calc_trans_y = (- calc_scale / 2)*base_shift_y
                    calc_scale = bscale * (min h w) 
                    bscale = 1/700
                    base_shift_y = 100
                    base_shift_x = 64
\end{code}
Fun????o para visualizar resultados gr??ficos:
\begin{code}
display = G.display (G.InWindow "Problema 4" (400, 400) (40, 40)) G.white 
\end{code}

\subsection*{Problema 4}
Fun????es para gest??o de sistemas de ficheiros:
\begin{code}
concatFS = inFS . (uncurry (++)) . (outFS >< outFS)
mkdir (x,y) = FS [(x, Dir y)]
mkfile (x,y) = FS [(x, File y)]

joinDupDirs :: (Eq a) => (FS a b) -> (FS a b)
joinDupDirs  = anaFS (prepOut . (id >< proc) . prepIn) where 
         prepIn = (id >< (map (id >< outFS))) . sls . (map distr) . outFS
         prepOut = (map undistr) . (uncurry (++)) . ((map i1) >< (map i2)) . (id >< (map (id >< inFS)))
         proc = concat . (map joinDup) . groupByName
         sls = split lefts rights

joinDup :: [(a,[b])] -> [(a,[b])]
joinDup = cataList (either nil g) where g = return . (split (p1 . p1) (concat . (map p2) . (uncurry (:))))

createFSfromFile :: (Path a, b) -> (FS a b)
createFSfromFile ([a],b) = mkfile(a,b)
createFSfromFile (a:as,b) = mkdir(a, createFSfromFile (as,b))
\end{code}
Fun????es auxiliares:
\begin{code}
checkFiles :: (Eq a) => FS a b -> Bool
checkFiles = cataFS ((uncurry (&&)) . (split f g)) where
           f = nr . (fmap p1) . lefts . (fmap distr)
           g = and . rights . (fmap p2)

groupByName :: (Eq a) => [(a,[b])] -> [[(a,[b])]]
groupByName = (groupBy (curry p)) where
            p = (uncurry (==)) . (p1 >< p1)

filterPath :: (Eq a) => Path a -> [(Path a, b)] -> [(Path a, b)]
filterPath = filter . (\p -> \(a,b) -> p == a)
\end{code}
Dados para testes:
\begin{itemize}
\item Sistema de ficheiros vazio:
\begin{code}
efs = FS []
\end{code}
\item N??vel 0
\begin{code}
f1 = FS [("f1", File "hello world")]
f2 = FS [("f2", File "more content")]
f00 = concatFS (f1,f2)
f01 = concatFS (f1, mkdir("d1", efs))
f02 = mkdir("d1",efs)
\end{code}
\item N??vel 1
\begin{code}
f10 = mkdir("d1", f00)
f11 = concatFS(mkdir("d1", f00), mkdir("d2", f00))
f12 = concatFS(mkdir("d1", f00), mkdir("d2", f01))
f13 = concatFS(mkdir("d1", f00), mkdir("d2", efs))
\end{code}
\item N??vel 2
\begin{code}
f20 = mkdir("d1", f10)
f21 = mkdir("d1", f11)
f22 = mkdir("d1", f12)
f23 = mkdir("d1", f13)
f24 = concatFS(mkdir("d1",f10), mkdir("d2",f12))
\end{code}
\item Sistemas de ficheiros inv??lidos:
\begin{code}
ifs0 = concatFS (f1,f1)
ifs1 = concatFS (f1, mkdir("f1", efs))
ifs2 = mkdir("d1", ifs0)
ifs3 = mkdir("d1", ifs1)
ifs4 = concatFS(mkdir("d1",ifs1), mkdir("d2",f12))
ifs5 = concatFS(mkdir("d1",f1), mkdir("d1",f2))
ifs6 = mkdir("d1",ifs5)
ifs7 = concatFS(mkdir("d1",f02),mkdir("d1",f02))
\end{code}
\end{itemize}
Visualiza????o em \graphviz{Graphviz}:
\begin{code}
dotFS :: FS String b -> IO ExitCode
dotFS = dotpict . bmap (const "") id . (cFS2Exp "root")
\end{code}
\def\omitted{``Automagically" generated:
\begin{code}
instance (Arbitrary a, Arbitrary b) => Arbitrary (FS a b) where
   arbitrary = sized genfs  where
             genfs 0 = liftM (inFS . (map (id >< (i1)))) QuickCheck.arbitrary
             genfs n = oneof [liftM (inFS . (map (id >< (i1)))) QuickCheck.arbitrary,
                     liftM (inFS . return . (id >< (i2))) (liftM2 (,) 
                     QuickCheck.arbitrary (genfs (n - 1))),
                     liftM3 genAux QuickCheck.arbitrary (genfs (n - 1)) (genfs (n - 1))]
             genAux a x y = inFS [(a, i2 x), (a, i2 y)]

instance Arbitrary Expr where
   arbitrary = (genExpr 10)  where
             genExpr 0 = liftM (inExpr . i1) QuickCheck.arbitrary
             genExpr n = oneof [liftM (inExpr . i1) QuickCheck.arbitrary,
                       liftM (inExpr . i2 . (uncurry genAux1)) 
                       $ liftM2 (,)  (genExpr (n-1)) (genExpr (n-1)),
                       liftM (inExpr . i2 . (uncurry genAux2)) 
                       $ liftM2 (,)  (genExpr (n-1)) (genExpr (n-1))
                     ]
             genAux1 x y = (Op "+",(x,y))
             genAux2 x y = (Op "*",(x,y))
\end{code}}

\subsection*{Outras fun????es auxiliares}
%----------------- Outras defini????es auxiliares -------------------------------------------%
L??gicas:
\begin{code}
infixr 0 .==>.
(.==>.) :: (Testable prop) => (a -> Bool) -> (a -> prop) -> a -> Property
p .==>. f = \a -> p a ==> f a

infixr 0 .<==>.
(.<==>.) :: (a -> Bool) -> (a -> Bool) -> a -> Property
p .<==>. f = \a -> (p a ==> property (f a)) .&&. (f a ==> property (p a))

infixr 4 .==.
(.==.) :: Eq b => (a -> b) -> (a -> b) -> (a -> Bool)
f .==. g = \a -> f a == g a

infixr 4 .<=.
(.<=.) :: Ord b => (a -> b) -> (a -> b) -> (a -> Bool)
f .<=. g = \a -> f a <= g a

infixr 4 .&&&.
(.&&&.) :: (a -> Bool) -> (a -> Bool) -> (a -> Bool)
f .&&&. g = \a -> ((f a) && (g a))
\end{code}
Compila????o e execu????o dentro do interpretador:\footnote{Pode ser ??til em testes
envolvendo \gloss{Gloss}. Nesse caso, o teste em causa deve fazer parte de uma fun????o
|main|.}
\begin{code}

run = do { system "ghc cp1819t" ; system "./cp1819t" }
\end{code}

%----------------- Solu????es dos alunos -----------------------------------------%

\section{Solu????es dos alunos}\label{sec:resolucao}
Os alunos devem colocar neste anexo as suas solu????es aos exerc??cios
propostos, de acordo com o "layout" que se fornece. N??o podem ser
alterados os nomes ou tipos das fun????es dadas, mas pode ser adicionado texto e/ou 
outras fun????es auxiliares que sejam necess??rias.

\subsection*{Problema 1}

\subsubsection*{inExpr}

\begin{eqnarray*}
\start
  inExpr = [Num,\ Bop]
%
\just\equiv{\textcolor{blue}{Universal-+\ (17)}}
%
        |lcbr(
    inExpr . i1 = Num
  )(
    inExpr . i2 = Bop
  )|
%
\just\equiv{\textcolor{blue}{Igualdade\ Extensional\ (69),\ Def-comp\ (70)}}
%
      |lcbr(
    inExpr (i1 n) = Num n
  )(
    inExpr (i2 (o, (e1, e1))) = Bop e1 o e2
  )|
\end{eqnarray*}

\vspace{0.5cm}

\subsubsection*{outExpr}

\begin{eqnarray*}
\start
  outExpr\ .\ [Num,\ Bop] = id
%
\just\equiv{\textcolor{blue}{Fus??o-+\ (20)}}
%
  [outExpr\ .\ Num,\ outExpr\ .\  Bop] = id
%
\just\equiv{\textcolor{blue}{Universal-+\ (17)}}
%
        |lcbr(
    id . i1 = outExpr . Num
  )(
    id . i2 = outExpr . Bop
  )|
%
\just\equiv{\textcolor{blue}{Natural-id\ (1),\ Igualdade\ Extensional\ (69),\ Def-comp\ (70)}}
%
      |lcbr(
    outExpr(Num n) = i1 n
  )(
    outExpr(Bop e1 o e2) = i2 (o, (e1, e2))
  )|
\end{eqnarray*}

\vspace{0.5cm}

\subsubsection*{recExpr}

\xymatrixcolsep{0.5pc}\xymatrixrowsep{5pc}
\centerline{\xymatrix{
   Expr \ar[d]_-{|f|}
                \ar@@/^2pc/ [rr]^-{|outExpr|} & \qquad \cong
&   Int + (Op \times (Expr \times Expr))  \ar[d]^{|id + (id >< (f >< f))|}
                                     \ar@@/^2pc/ [ll]^-{|inExpr|}
\\
    |C| &  & Int + (Op \times (C \times C))\ar[ll]
}}

\vspace{0.5cm}

\begin{eqnarray*}
\start
  recExpr\ f = {|id + (id >< (f >< f))|}
%
\just\equiv{\textcolor{blue}{Aplicando\ a\ defini????o\ dada\ de\ baseExpr}}
%
  recExpr\ f = baseExpr\ id\ f
%
\end{eqnarray*}

\vspace{0.5cm}

\subsubsection*{cataExpr}

\begin{eqnarray*}
\start
\just\equiv{\textcolor{blue}{Cancelamento-cata\ (44)}}
%
  |cataNat g|\ .\ in = g\ .\ F|cataNat g|
%
\just\equiv{\textcolor{blue}{in.out = id}}
%
  |cataNat g|  = g\ .\ F|cataNat g|\ .\ out
%
\just\equiv{\textcolor{blue}{Aplicando as defini????es em Haskell j?? determinadas}}
%
  cataExpr\ g = g\ .\ recExpr(cataExpr\ g)\ .\ outExpr
%
\end{eqnarray*}

\vspace{0.5cm}

\subsubsection*{anaExpr}

\begin{eqnarray*}
\start
\just\equiv{\textcolor{blue}{Cancelamento-ana\ (53)}}
%
  out\ .\ \ana{g} = F\ana{g}\ .\ g
%
\just\equiv{\textcolor{blue}{in.out = id}}
%
  \ana{g} = in\ .\ F\ana{g}\ .\ g
%
\just\equiv{\textcolor{blue}{Aplicando as defini????es em Haskell j?? determinadas}}
%
  anaExpr\ g = inExpr\ .\ recExpr(anaExpr\ g)\ .\ g
%
\end{eqnarray*}

\vspace{0.5cm}

\subsubsection*{hyloExpr}

\begin{eqnarray*}
\start
\just\equiv{\textcolor{blue}{Defini????o de hilomorfismo}}
%
  hyloExpr\ g\ h = \cata{g}.\ana{h}
%
\just\equiv{\textcolor{blue}{Cancelamento-cata (44); Cancelamento-ana(53)}}
%
  hyloExpr\ g\ h = g.F\cata{g}.inExpr.outExpr.F\ana{h}.h
%
\just\equiv{\textcolor{blue}{in.out = id; Aplicando as defini????es em Haskell j?? determinadas}}
%
  hyloExpr\ g\ h = g.recExpr(cataExpr g).recExpr(anaExpr h).h
%
\end{eqnarray*}

\vspace{0.5cm}

\subsubsection*{calcula}

\par\noindent\hspace{0.5cm}Por forma a obtermos o valor final de uma express??o, podemos recorrer ao uso do catamorfismo de \textit{Expr}, definindo o diagrama de \textit{calcula} da seguinte maneira.

\vspace{0.5cm}

\xymatrixcolsep{0.5pc}\xymatrixrowsep{5pc}
\centerline{\xymatrix{
   Expr \ar[d]_-{|calcula|}
                \ar@@/^2pc/ [rr]^-{|outExpr|} & \qquad \cong
&   Int + (Op \times (Expr \times Expr))  \ar[d]^{|id + (id >< (calcula >< calcula))|}
                                     \ar@@/^2pc/ [ll]^-{|inExpr|}
\\
    |Int| &  & Int + (Op \times (Int \times Int))\ar[ll]^-{|g|}
}}

\vspace{0.5cm}

Neste caso, percorremos todas as express??es, de modo a determinar o valor de cada uma, aplicando posteriormente a cada par de valores (obtido a partir de cada par de express??es) a opera????o determinada pelo \textit{Op}, emparelhado, inicialmente, com cada par de express??es (agora um par de \textit{Int}). Ou seja, o gene \textit{g} pode ser representado pelo seguinte diagrama:

\vspace{0.5cm}

\xymatrixcolsep{2pc}\xymatrixrowsep{2pc}
\centerline{\xymatrix {
    Int + (Op \times (Int \times Int)) \ar[d]_{id + ((operAux.outOp) \times id)} \\
    Int + (F \times (Int \times Int)) \ar [d]_{id+cal} \\
    Int + Int \ar [d]_{[id, id]}\\
    Int \\
}}

\vspace{0.5cm}

Onde \textit{operAux} ?? a fun????o que, dada uma \textit{String} correspondende ao s??mbolo de uma opera????o aritm??tica, devolve essa mesma opera????o; e \textit{cal} ?? a fun????o que recebe um par com uma opera????o aritm??tica e um par de \textit{Int} e devolve o resultado da aplica????o dessa opera????o aos elementos do par de inteiros.
Assim sendo, podemos afirmar que o gene \textit{g} se define como:

\vspace{0.5cm}

\begin{eqnarray*}
\start
  g = [id,\ id].(id\ +\ cal).(id\ +\ ((operAux.outOp)\ \times id))
%
\just\equiv{\textcolor{blue}{Absor????o-+ (22)}}
%
  g = [id.id.id,\ id.cal.((operAux.outOp) \times id)]
%
\just\equiv{\textcolor{blue}{Natural-id (1); Def-x (10)}}
%
  g = [id,\ cal.<operAux.outOp.\pi1,\ \pi2>]
%
\end{eqnarray*}

\vspace{0.5cm}

Por fim, podemos concluir que a fun????o \textit{calcula} poder?? ser definida como:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{23em}
    \center $ | calcula = cataExpr ( either (id) (cal.(split (operAux.outOp.p1) (p2))))| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

\subsubsection*{show'}

\par\noindent\hspace{0.5cm}Tal como para \textit{calcula}, podemos definir a fun????o \textit{show'} como um catamorfismo de \textit{Expr}. Assim sendo, chegamos ao seguinte diagrama representativo de \textit{show'}.

\vspace{0.5cm}

\xymatrixcolsep{0.5pc}\xymatrixrowsep{5pc}
\centerline{\xymatrix{
   Expr \ar[d]_-{|show'|}
                \ar@@/^2pc/ [rr]^-{|outExpr|} & \qquad \cong
&   Int + (Op \times (Expr \times Expr))  \ar[d]^{|id + (id >< (show' >< show'))|}
                                     \ar@@/^2pc/ [ll]^-{|inExpr|}
\\
    |String| &  & Int + (Op \times (String \times String))\ar[ll]^-{|g|}
}}

\vspace{0.5cm}

Ap??s isto, ?? necess??rio definir o gene deste catamorfismo. Para esta fun????o, a ideia ser?? determinar o formato em \textit{String} de cada uma das \textit{Expr}, concatenando-as depois com o respetivo operador aritm??tico associado, guardado em cada \textit{Op}. No entanto, temos de manter o formato "original" da express??o, ou seja, a concatena????o ter?? de respeitar a seguinte ordem: a \textit{String} da esquerda (do par), seguida do operador aritm??tico, seguida da \textit{String} da direita. Deste modo, chegamos a seguinte representa????o por diagrama do gene \textit{g}.

\vspace{0.5cm}

\xymatrixcolsep{2pc}\xymatrixrowsep{2pc}
\centerline{\xymatrix {
    Int + (Op \times (String \times String)) \ar[d]_{id + (assocl.(outOp \times id))} \\
    Int + ((String \times String) \times String) \ar [d]_{id+(conc.swap \times id)} \\
    Int + (String \times String) \ar [d]_{id + pcurv.conc}\\
    Int + String \ar [d]_{[showMod, id]} \\
    String \\
}}

\vspace{0.5cm}

Neste diagrama, a fun????o \textit{pcurv} coloca uma \textit{String} dentro de par??ntesis curvos, enquanto que a fun????o \textit{showMod} aplica a fun????o \textit{show} a um inteiro, transformando-o na \textit{String} correspondente, e caso o inteiro seja negativo, ainda aplica a fun????o \textit{pcurv} ao valor em \textit{String} desse inteiro. Assim sendo, o gene \textit{g} poder?? ser definido da seguinte forma:

\vspace{0.5cm}

\begin{eqnarray*}
\start
  g = [showMod,\ id].(id\ +\ pcurv.conc).(id\ +\ (conc.swap\ \times id)).(id\ +\ (assocl.(outOp\ \times id)))
%
\just\equiv{\textcolor{blue}{Absor????o-+ (22), Natural-id (1)}}
%
  g = [showMod,\ pcurv.conc.(conc.swap \times id).assocl.(outOp\ \times id)]
%
\just\equiv{\textcolor{blue}{Def-x (10)}}
%
  g = [showMod,\ pcurv.conc.<conc.swap.\pi1,\ \pi2>.assocl.<outOp.\pi1,\ \pi2>]
%
\end{eqnarray*}

\vspace{0.5cm}

Finalizando, podemos definir a fun????o \textit{show'} como:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{40em}
    \center $ | show' = cataExpr (either (showMod) (pcurv.conc.(split (conc.swap.p1) (p2)).assocl.(split (outOp.p1) (p2))))| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

\subsubsection*{compile}

\par\noindent\hspace{0.5cm}Por forma a ser mais f??cil definir esta fun????o, podemos recorrer ?? fun????o dada \textit{readExp}, que transforma uma \textit{String} numa lista de pares \textit{Expr} e \textit{String}. Sabendo que o primeiro par da lista criada por \textit{readExp} possui a \textit{Expr} da \textit{String} recebida como input, podemos obt??-la, tal como o diagrama demonstra.

\vspace{0.5cm}

\xymatrixcolsep{2pc}\xymatrixrowsep{2pc}
\centerline{\xymatrix {
    String \ar[d]_{readExp} \\
    (Expr \times String)* \ar [d]_{\pi1.head} \\
    Expr\\
}}

\vspace{0.5cm}

Agora, podemos, novamente, recorrer ao catamorfismo de \textit{Expr}, por forma a obter a defini????o de \textit{compilaAux}, que ser?? a fun????o que, para uma dada express??o, a transforma em c??digo-m??quina.

\vspace{0.5cm}

\xymatrixcolsep{0.5pc}\xymatrixrowsep{5pc}
\centerline{\xymatrix{
   Expr \ar[d]_-{|compileAux|}
                \ar@@/^2pc/ [rr]^-{|outExpr|} & \qquad \cong
&   Int + (Op \times (Expr \times Expr))  \ar[d]^{|id + (id >< (compileAux >< compileAux))|}
                                     \ar@@/^2pc/ [ll]^-{|inExpr|}
\\
    |Codigo| &  & Int + (Op \times (Codigo \times Codigo))\ar[ll]^-{|g|}
}}

\vspace{0.5cm}

Posto isto, teremos de determinar o gene para este catamorfismo. Neste caso, a inten????o ser?? concatenar o par de \textit{Codigo} obtido de cada par de \textit{Expr}, e a este concatenar, no fim, a opera????o aritm??tica a aplicar, "traduzida" para linguagem m??quina. Isto deve-se ao facto de que, em linguagem m??quina, primeiro s??o introduzidos os dois valores aos quais se vai aplicar uma opera????o, e s?? depois a respetiva opera????o. Assim sendo, chegamos ao diagrama representativo do gene deste catamorfismo.

\vspace{0.5cm}

\xymatrixcolsep{2pc}\xymatrixrowsep{2pc}
\centerline{\xymatrix {
    Int + (Op \times (Codigo \times Codigo)) \ar[d]_{id + (swap.(singl.get.outOp \times conc))} \\
    Int + (Codigo \times Codigo) \ar [d]_{[singl.push, conc]} \\
    Codigo \\
}}

\vspace{0.5cm}

Conv??m real??ar que a fun????o \textit{get} transforma uma \textit{String} que seja um s??mbolo de uma opera????o aritm??tica, na \textit{String} representativa dessa opera????o em linguagem m??quina; e que a fun????o \textit{push} concatena a express??o \textit{PUSH} ?? representa????o, em \textit{String}, do valor do \textit{Int} que recebe (representa????o essa fornecida pela fun????o \textit{show}). Como tal, podemos definir o gene \textit{g} como: 

\vspace{0.5cm}

\begin{eqnarray*}
\start
  g = [singl.push,\ conc].(id\ +\ (swap.(singl.get.outOp \times conc)))
%
\just\equiv{\textcolor{blue}{Absor????o-+ (22), Natural-id (1)}}
%
  g = [singl.push,\ conc.swap.(singl.get.outOp \times conc)]
%
\just\equiv{\textcolor{blue}{Def-x (10); swap = (split (p2) (p1)); Fus??o-x (9)}}
%
  g = [singl.push,\ conc.<\pi2.<singl.get.outOp.\pi1,\ conc.\pi2>,\ \pi1.<singl.get.outOp.\pi1,\ conc.\pi2>>]
\just\equiv{\textcolor{blue}{Cancelamento-x (7)}}
%
  g = [singl.push,\ conc.<conc.\pi2,\ singl.get.outOp.\pi1>]
%
\end{eqnarray*}

\vspace{0.5cm}

Deste modo, podemos definir a fun????o \textit{compileAux} como:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{33em}
    \center $ | compileAux = cataExpr (either (singl.push) (conc.(split (conc.p2) (singl.get.outOp.p1))))| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

Concluindo, assim, a seguinte defini????o de \textit{compile}:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{19em}
    \center $ | compile = compileAux.p1.head.readExp| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

\subsubsection*{Solu????o}
\begin{code}

inExpr :: Either Int (Op,(Expr,Expr)) -> Expr
inExpr (Left n) = Num n
inExpr (Right (o, (e1, e2))) = Bop e1 o e2

outExpr :: Expr -> Either Int (Op,(Expr,Expr))
outExpr (Num n) = i1 n
outExpr (Bop e1 o e2) = i2 (o, (e1, e2))

inOp :: String -> Op
inOp = Op

outOp :: Op -> String
outOp (Op a) = a

recExpr f = baseExpr id f

cataExpr g = g.recExpr(cataExpr g).outExpr

anaExpr g = inExpr.recExpr(anaExpr g).g

hyloExpr h g = h.recExpr(cataExpr h).recExpr(anaExpr g).g

calcula :: Expr -> Int
calcula = cataExpr (either (id) (cal.(split (operAux.outOp.p1) (p2)))) 
                   where cal (o, (x,y)) = o x y

operAux "+" = (+)
operAux "-" = (-)
operAux "*" = (*)
operAux "/" = div

show' = cataExpr (either (showMod) (pcurv.conc.(split (conc.swap.p1) (p2)).assocl.(split (outOp.p1) (p2))))
                 where pcurv = conc.(split (const "(") (conc.(split (id) (const ")"))))
                       showMod = cond (< 0) (pcurv.show) (show)

compile :: String -> Codigo
compile = compileAux.p1.head.readExp

compileAux = cataExpr (either (singl.push) (conc.(split (conc.p2) (singl.get.outOp.p1))))
                      where push = conc.(split (const "PUSH ") (show))

get "+" = "ADD"
get "-" = "SUB"
get "*" = "MUL"
get "/" = "DIV"

\end{code}


\subsubsection*{Valoriza????o}

\par\noindent\hspace{0.5cm}Para come??ar, a fun????o \textit{readExp} consegue "compreender" que as opera????es compreendidas entre par??ntesis s??o priorit??rias e, como tal, estas dever??o estar compreendidas dentro de uma mesma \textit{Expr}.
\par Esta fun????o interpreta a \textit{String} inserida, dividindo a em partes: separa os n??meros, os operadores aritm??ticos e as express??es compreendidas entre par??ntesis. Posto isto, come??a a construir a express??o final por partes, criando primeiramente uma \textit{Expr} com o primeiro n??mero/express??o inserido, o primeiro operador inserido (fora de par??ntesis) e, por fim, o segundo n??mero/express??o inserido/a; o restante que n??o foi lido, ?? guardado numa \textit{String}, que emparelha com a \textit{Expr} criada. Para criar o segundo par, a fun????o pega no par anteriormente criado, coloca a \textit{Expr} l?? presente como primeira \textit{Expr} da nova \textit{Expr} (no conjunto \textit{Bop Expr Op Expr}); depois, pega na \textit{String} emparelhada e vai buscar o pr??ximo operador (que ser?? o primeiro caract??r da \textit{String}) e o pr??ximo n??mero/express??o, que ser?? a segunda \textit{Expr} dentro da nova \textit{Expr} que est?? a ser criada. Mais uma vez, guarda o resto da \textit{String} n??o lida numa \textit{String}, emparelhada com a \textit{Expr} rec??m-criada.
\par Isto ocorre sucessivamente, at?? que a \textit{String} fica vazia, o que significa que a \textit{Expr} emparelhada com esta \textit{String} vazia, ser?? a \textit{Expr} final. Deste modo, ficamos com uma lista de pares \textit{(Expr, String)}, estando presente na cabe??a da lista a \textit{Expr} correspondente ?? \textit{String} introduzida.

\subsection*{Problema 2}

\subsubsection*{inL2D}

\begin{eqnarray*}
\start
  inL2D = [Unid,\ Comp]
%
\just\equiv{\textcolor{blue}{Universal-+\ (17)}}
%
        |lcbr(
    inL2D . i1 = Unid
  )(
    inL2D . i2 = Comp
  )|
%
\just\equiv{\textcolor{blue}{Igualdade\ Extensional\ (69),\ Def-comp\ (70)}}
%
      |lcbr(
    inL2D (i1 a) = Unid a
  )(
    inL2D (i2 (b, (a, c))) = Comp b a c
  )|
\end{eqnarray*}

\vspace{0.5cm}

\subsubsection*{outL2D}

\begin{eqnarray*}
\start
  outL2D\ .\ [Unid,\ Comp] = id
%
\just\equiv{\textcolor{blue}{Fus??o-+\ (20)}}
%
  [outL2D\ .\ Unid,\ outL2D\ .\  Comp] = id
%
\just\equiv{\textcolor{blue}{Universal-+\ (17)}}
%
        |lcbr(
    id . i1 = outL2D . Unid
  )(
    id . i2 = outL2D . Comp
  )|
%
\just\equiv{\textcolor{blue}{Natural-id\ (1),\ Igualdade\ Extensional\ (69),\ Def-comp\ (70)}}
%
      |lcbr(
    outL2D(Unid a) = i1 a
  )(
    outL2D(Comp b a c) = i2 (b, (a, c))
  )|
\end{eqnarray*}

\vspace{0.5cm}

\subsubsection*{baseL2D e recL2D}

\vspace{0.5cm}

\xymatrixcolsep{0.5pc}\xymatrixrowsep{5pc}
\centerline{\xymatrix{
   X \ar[d]_-{|g|}
                \ar@@/^2pc/ [rr]^-{|outL2D|} & \qquad \cong
&   A + (B \times (X \times X))  \ar[d]^{|h+ (f >< (g >< g))|}
                                     \ar@@/^2pc/ [ll]^-{|inL2D|}
\\
    |C| &  & A' + (B' \times (C \times C))\ar[ll]
}}

\vspace{0.5cm}

\begin{eqnarray*}
\start
  baseL2D\ h\ f\ g = {|h+ (f >< (g >< g))|}
%
\end{eqnarray*}

\begin{eqnarray*}
\start
  recL2D\ f = {|id + (id >< (f >< f))|}
%
\just\equiv{\textcolor{blue}{Aplicando\ a\ defini????o\ dada\ de\ baseL2D}}
%
  recL2D\ f = baseL2D\ id\ id\ f
%
\end{eqnarray*}

\vspace{0.5cm}

\subsubsection*{cataL2D}

\begin{eqnarray*}
\start
\just\equiv{\textcolor{blue}{Cancelamento-cata\ (44)}}
%
  |cataNat g|\ .\ in = g\ .\ F|cataNat g|
%
\just\equiv{\textcolor{blue}{in.out = id}}
%
  |cataNat g|  = g\ .\ F|cataNat g|\ .\ out
%
\just\equiv{\textcolor{blue}{Aplicando as defini????es em Haskell j?? determinadas}}
%
  cataL2D\ g = g\ .\ recL2D(cataL2D\ g)\ .\ outL2D
%
\end{eqnarray*}

\vspace{0.5cm}

\subsubsection*{anaL2D}

\begin{eqnarray*}
\start
\just\equiv{\textcolor{blue}{Cancelamento-ana\ (53)}}
%
  out\ .\ \ana{g} = F\ana{g}\ .\ g
%
\just\equiv{\textcolor{blue}{in.out = id}}
%
  \ana{g} = in\ .\ F\ana{g}\ .\ g
%
\just\equiv{\textcolor{blue}{Aplicando as defini????es em Haskell j?? determinadas}}
%
  anaL2D\ g = inL2D\ .\ recL2D(anaL2D\ g)\ .\ g
%
\end{eqnarray*}

\subsubsection*{hyloL2D}

\begin{eqnarray*}
\start
\just\equiv{\textcolor{blue}{Defini????o de hilomorfismo}}
%
  hyloL2D\ g\ h = \cata{g}.\ana{h}
%
\just\equiv{\textcolor{blue}{Cancelamento-cata (44); Cancelamento-ana(53)}}
%
  hyloL2D\ g\ h = g.F\cata{g}.inL2D.outL2D.F\ana{h}.h
%
\just\equiv{\textcolor{blue}{in.out = id; Aplicando as defini????es em Haskell j?? determinadas}}
%
  hyloL2D\ g\ h = g.recL2D(cataL2D g).recL2D(anaL2D h).h
%
\end{eqnarray*}

\vspace{0.5cm}

\vspace{0.5cm}

\subsubsection*{dimen}

\par\noindent\hspace{0.5cm}Logo ?? partida, sabemos que quando duas caixas s??o agregadas na vertical, independetemente de como as agregamos (\textit{V}, \textit{Ve} ou \textit{Vd}), a altura total do conjunto ser?? a soma das alturas das duas caixas, enquanto que a sua largura ser?? igual ?? da caixa mais larga. O mesmo se aplica, de forma inversa, a quando da agrega????o de caixas na horizontal (\textit{H}, \textit{Ht} ou \textit{Hb}) - a sua largura ser?? a soma das larguras de ambas as caixas, enquanto que a altura ser?? igual ?? da caixa mais alta. Posto isto, e recorrendo ao uso de um catamorfismo, podemos definir o seguinte diagrama:

\vspace{0.5cm}

\xymatrixcolsep{0.5pc}\xymatrixrowsep{5pc}
\centerline{\xymatrix{
   L2D \ar[d]_-{|dimen|}
                \ar@@/^2pc/ [rr]^-{|outL2D|} & \qquad \cong
&   Caixa + (Tipo \times (L2D \times L2D))  \ar[d]^{|id + (id >< (dimen >< dimen))|}
                                     \ar@@/^2pc/ [ll]^-{|inL2D|}
\\
    (Float \times Float) &  & Caixa + (Tipo \times ((Float \times Float) \times (Float \times Float)))\ar[ll]^-{|g|}
}}

\vspace{0.5cm}

Deste modo, fica assim a ser necess??rio determinar o gene \textit{g}. No caso de apenas termos uma caixa, as dimens??es desta j?? est??o declaradas, em formato \textit{Int}. Como tal, ?? apenas necess??rio aplicar-lhes apenas a fun????o \textit{fromIntegral}, de modo a ficar compat??vel com o tipo de sa??da da fun????o \textit{dimen} (\textit{(Float, Float)}). No caso de termos um par de \textit{L2D} e o \textit{Tipo} de agrega????o que as une, ap??s aplicarmos a fun????o \textit{recL2D (dimen)} vamos ficar com dois pares de \textit{Float}, um para cada \textit{L2D}. Uma vez que as caixas da direita s??o sempre agregadas as da esquerda tendo em conta o tipo associado ao par \textit{L2D}, vamos calcular a dimens??o total de um par de \textit{L2D} usando as dimens??es de ambas as caixas e o tipo associado.  Assim sendo, podemos definir o gene \textit{g} com o seguinte diagrama:

\vspace{0.5cm}

\xymatrixcolsep{2pc}\xymatrixrowsep{2pc}
\centerline{\xymatrix {
    Caixa + (Tipo \times ((Float \times Float) \times (Float \times Float))) \ar[d]_{[(fromIntegral \times fromIntegral).\pi1, aux]} \\
    (Float \times Float) \\
}}

\vspace{0.5cm}

Agora, ?? necess??rio definir a fun????o \textit{aux}, que tratar?? de calcular as dimens??es de um par de caixas, usando as dimens??es de cada uma e sabendo a forma como est??o unidas. Assim sendo, o mais f??cil ser?? defini-la usando um condicional, da seguinte forma:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{32em}
    \center $ |aux = (cond (detTipo.p1) (((uncurry max) >< (uncurry (+))).p2) (((uncurry (+)) >< (uncurry max)).p2)).get| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

Neste caso, a fun????o \textit{detTipo} determina o tipo de uni??o das caixas - se for uma uni??o vertical (\textit{V, Ve, Vd}) retorna \textit{True}, se for uma uni??o horizontal (\textit{H, Ht, Hb}) retorna \textit{False}; enquanto que a fun????o \textit{get} reorganiza os pares com as dimens??es, juntando as larguras no primeiro par e as alturas no segundo. Ou seja, para um input de entrada igual a \textit{((x,y),(a,b))} ficamos com \textit{((x,a),(y,b))}. Deste modo, quando estamos perante o "caso verdadeiro", ou seja, quando verificamos que a uni??o ?? vertical, determinamos qual a largura m??xima, de entre as duas caixas, e calculamos a soma das alturas. Quando estamos perante uma uni??o horizontal, fazemos o oposto. No fim de tudo isto, podemos chegar ?? seguinte defini????o de \textit{dimen}:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{26em}
    \center $ |dimen = cataL2D (either ((fromIntegral >< fromIntegral).p1) (aux))| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

\subsubsection*{collectLeafs}

\par\noindent\hspace{0.5cm}De forma indutiva, ?? poss??vel chegar a uma defini????o para \textit{collectLeafs}, tendo por base o catamorfismo de \textit{L2D}. Como tal, poderemos estabelecer o seguinte diagrama:

\vspace{0.5cm}

\xymatrixcolsep{0.5pc}\xymatrixrowsep{5pc}
\centerline{\xymatrix{
   X \ar[d]_-{|collectLeafs|}
                \ar@@/^2pc/ [rr]^-{|outL2D|} & \qquad \cong
&   A + (B \times (X \times X))  \ar[d]^{|id + (id >< (collectLeafs >< collectLeafs))|}
                                     \ar@@/^2pc/ [ll]^-{|inL2D|}
\\
    |A*| &  & A + (B \times (A* \times A*))\ar[ll]^-{|g|}
}}

\vspace{0.5cm}

?? agora necess??rio, no entanto, definir o gene \textit{g} do catamorfismo. Ora, como o nosso objetivo ser?? simplesmente determinar uma lista de \textit{A}'s, para qualquer \textit{A}, precisamos, ent??o, de determinar a lista de \textit{A}'s de cada um dos \textit{X} do par, e concatenar as listas. Assim sendo, poderemos definir o diagrama que se segue para caracterizar o gene do catamorfismo.

\vspace{0.5cm}

\xymatrixcolsep{2pc}\xymatrixrowsep{2pc}
\centerline{\xymatrix {
    A + (B \times (A* \times A*)) \ar[d]_{[singl, conc.\pi2]} \\
    A* \\
}}

\vspace{0.5cm}

Deste modo, podemos concluir a seguinte defini????o de \textit{collectLeafs}:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{17em}
    \center $ | collectLeafs = cataL2D (either (singl) (conc.p2))| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

\subsubsection*{agrupcaixas}

\par\noindent\hspace{0.5cm}Olhando para a tipagem da fun????o (recebe um \textit{(X (Caixa, Origem) ())} e devolve uma \textit{Fig}, que ?? uma lista de \textit{(Origem, Caixa)}), podemos concluir que a fun????o devolve todas as "folhas", ??s quais ?? aplicada um swap. Como tal, podemos chegar ?? seguinte defini????o da fun????o \textit{agrupcaixas}:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{18em}
    \center $ | agrup_caixas = (map swap).collectLeafs| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

\subsubsection*{calcOrigins}

\par\noindent\hspace{0.5cm}Uma vez que a tipagem da fun????o nos indica que ela parte de um \textit{X A B} e chega a outro \textit{X A B}, podemos assumir que esta fun????o poder?? ser definida como um anamorfismo de \textit{L2D}, apesar de possuirem \textit{A} e \textit{B} diferentes. Como tal, chegamos ao seguinte diagrama

\vspace{0.5cm}

\xymatrixcolsep{1pc}\xymatrixrowsep{5pc}
\centerline{\xymatrix{
  X\ar[d]_-{|calcOrigins|}
                \ar [rr]^-{|h|} & &  (Caixa \times Origem) + (1 \times (X \times X))  \ar[d]^{|id + (id >< (calcOrigins >< calcOrigins))|}
\\
    |X'| &  &  (Caixa \times Origem) + (1 \times (X' \times X'))\ar[ll]^-{|inL2D|}
}}

\vspace{0.5cm}

Onde \textit{X} ?? o tipo \textit{(L2D, Origem)} e \textit{X'} o tipo \textit{( X (Caixa, Origem) ())}. Deste modo, necessitamos agora de definir o gene \textit{h} do anamorfismo. Uma vez que a nossa fun????o recebe todas as caixas guardadas na estrutura \textit{L2D} e a origem inicial, o nosso intuito ser?? atribuir a origem ?? primeira caixa (que ser?? a que estar?? o mais ?? esquerda poss??vel) e depois calcular a origem da caixa da direita, tendo por base as dimens??es que a caixa da esquerda ocupa, e o tipo de enquandramento que queremos fazer. Como tal, podemos desenvolver o gene \textit{h} da seguinte forma:

\vspace{0.5cm}

\xymatrixcolsep{2pc}\xymatrixrowsep{2pc}
\centerline{\xymatrix {
    L2D \times Origem \ar[d]_{outL2D \times id} \\
    (Caixa + (Tipo \times (L2D \times L2D))) \times Origem \ar [d]_{distl} \\
    (Caixa \times Origem) + ((Tipo \times (L2D \times L2D)) \times Origem) \ar [d]_{calcOriginsAux}\\
    (Caixa \times Origem) + (1 \times (X' \times X')) \\
}}

\vspace{0.5cm}

Posto isto, ?? agora necess??rio definir a fun????o \textit{calcOriginsAux}. Este poder?? ser definida atrav??s do seguinte diagrama:

\vspace{0.5cm}

\xymatrixcolsep{2pc}\xymatrixrowsep{2pc}
\centerline{\xymatrix {
    (Caixa \times Origem) + ((Tipo \times (L2D \times L2D)) \times Origem) \ar[d]_{id+aux1} \\
    (Caixa \times Origem) + (Tipo \times ((L2D \times Origem) \times (L2D \times Origem))) \ar [d]_{id+aux2} \\
    (Caixa \times Origem) + (Tipo \times ((L2D \times Origem) \times (L2D \times Origem))) \ar [d]_{id+(! \times id)}\\
    (Caixa \times Origem) + (1 \times ((L2D \times Origem) \times (L2D \times Origem))) \\
}}

\vspace{0.5cm}

Neste caso, a fun????o \textit{aux1} ir?? "distribuir" a origem inicial pelos dois \textit{L2D} existentes, criando dois pares \textit{L2D x Origem}, enquanto que a fun????o \textit{aux2} apenas ir?? alterar a origem do segundo par, atualizando-a tendo em conta a dimens??o da \textit{L2D} do primeiro par (determinada atrav??s do uso da fun????o \textit{dimen}), o \textit{Tipo} que caracteriza a uni??o dos dois \textit{L2D} e a origem inicial. Estes atributos ser??o utilizados pela fun????o \textit{calc} para determinar a nova origem. Deste modo, podemos definir a fun????o \textit{calcOriginsAux} como:

\vspace{0.5cm}

\begin{eqnarray*}
\start
  calcOriginsAux = (id\ +\ (!\ \times id)).(id\ +\ aux2).(id\ +\ aux1)
%
\just\equiv{\textcolor{blue}{Def-+ (21), Absor????o-+ (22), Natural-id (1)}}
%
  calcOriginsAux = [i1,\ i2.(!\ \times id).aux2.aux1)]
%
\just\equiv{\textcolor{blue}{Def-x (10)}}
%
  calcOriginsAux = [i1,\ i2.<!.\pi1,\ id.\pi2>.aux2.aux1)]
%
\end{eqnarray*}

\vspace{0.5cm}

Onde \textit{aux1} e \textit{aux2} s??o definidas como:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{28em}
    \center $ | aux1 = split (p1.p1) (split (split (p1.p2.p1) (p2)) (split (p2.p2.p1) (p2))) | $
    \center $ | aux2 = split (p1) (split (p1.p2) (split (p1.p2.p2) (calcOriginsAux2))) | $
    \center $ | calcOriginsAux2 = aplicaCalc.(split (p1) ((split (p2.p2) (dimen.p1.p1)).p2)) | $
    \center $ | aplicaCalc (t, (o, c)) = calc t o c| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

Assim sendo, podemos definir a fun????o \textit{calcOrigins} como:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{28em}
    \center $ | calcOrigins = anaL2D (calcOriginsAux.distl.(split (outL2D.p1) (p2))) | $
\end{minipage}}
\end{center}

\vspace{0.5cm}

\subsubsection*{caixasAndOrigin2Pict}

\par\noindent\hspace{0.5cm}Uma vez que temos definida a fun????o \textit{crCaixa}, caso consigamos "juntar" cada caixa com a sua respetiva origem, podemos aplicar esta fun????o para criar a \textit{G.Picture} correspondente. Ora, uma vez que temos as fun????es \textit{calcOrigins} e \textit{agrupcaixas} definidas, conseguimos percorrer toda a ??rvore de caixas, e juntar cada caixa com a sua origem respetiva, criando uma lista de pares \textit{(Origem, Caixa)}. No entanto, a fun????o \textit{agrupcaixas} aplica um \textit{swap} que neste caso ser?? desnecess??rio; mais ainda, as fun????es s??o baseadas num anamorfismo e num catamorfismo, respetivamente, o que nos leva a pensar que as poderemos juntar num hilomorfismo. Como o tipo de entrada de \textit{calcOrigins} ?? o mesmo que o de \textit{caixasAndOrigin2Pict} (\textit{(L2D,Origem)}), o tipo de saida de \textit{calcOrigins} ?? o mesmo que o tipo de entrada de \textit{agrupcaixas} (\textit{X (Caixa, Origem) ()}), e o tipo de sa??da de \textit{agrupcaixas}, se n??o considerarmos o \textit{swap}, ?? \textit{[(Caixa, Origem)]}, podemos criar o seguinte hilomorfismo.

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{32em}
    \center $ | hilo = hyloL2D ((either (singl) (conc.p2))) (calcOriginsAux.distl.(split (outL2D.p1) (p2))) | $
\end{minipage}}
\end{center}

\vspace{0.5cm}

Posto isto, precisamos agora de aplicar a fun????o \textit{crCaixa} a cada par, por forma a criar uma lista de \textit{G.Picture}. Podemos faz??-lo, utilizando a seguinte fun????o, que recebe um par \textit{(Caixa, Origem)}:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{34em}
    \center $ | criarPic (((x,y),(t,c)),o) = crCaixa o (fromIntegral x) (fromIntegral y) t c | $
\end{minipage}}
\end{center}

\vspace{0.5cm}

Neste caso, o par \textit{(x,y)} representa as dimens??es da caixa, \textit{t} representa o texto escrito nela, \textit{c} a cor usada para preencher a caixa, e \textit{o} a origem. Assim sendo, aplicando esta fun????o a todos os elementos da lista de pares, criamos uma lista de \textit{G.Picture}. Havendo uma fun????o \textit{G.pictures} que transforma uma lista de figuras numa s?? figura, podemos definir \textit{caixasAndOrigin2Pict} como:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{27em}
    \center $ | caixasAndOrigin2Pict = (G.pictures).(map criarPic).hilo| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

\subsubsection*{mostracaixas}

\par\noindent\hspace{0.5cm}Aproveitando a fun????o \textit{caixasAndOrigin2Pict}, que transforma um \textit{(L2D, Origem)} numa \textit{G.Picture}, e a fun????o \textit{display}, que transforma uma figura em \textit{IO ()}, podemos definir a fun????o \textit{mostracaixas} como:

\begin{center}
\fbox{\begin{minipage}{21em}
    \center $ | mostra_caixas = display.caixasAndOrigin2Pict| $
\end{minipage}}
\end{center}


\subsubsection*{Solu????o}

\begin{code}
inL2D :: Either a (b, (X a b,X a b)) -> X a b
inL2D (Left a) = Unid a
inL2D (Right (b, (a, c))) = Comp b a c 

outL2D :: X a b -> Either a (b, (X a b,X a b))
outL2D (Unid a) = i1 a
outL2D (Comp b a c) = i2 (b, (a, c)) 

baseL2D h f g = h -|- f >< (g >< g)

recL2D f = baseL2D id id f

cataL2D g = g.recL2D (cataL2D g).outL2D

anaL2D g = inL2D.recL2D (anaL2D g).g

hyloL2D c d = c.recL2D (cataL2D c).recL2D (anaL2D d).d

collectLeafs = cataL2D (either (singl) (conc.p2))

dimen :: X Caixa Tipo -> (Float, Float)
dimen = cataL2D (either ((fromIntegral >< fromIntegral).p1) (aux))
        where aux = (cond (detTipo.p1) (((uncurry max) >< (uncurry (+))).p2) (((uncurry (+)) >< (uncurry max)).p2)).get
              get = id >< (split (split (p1.p1) (p1.p2)) (split (p2.p1) (p2.p2)))
              detTipo V = True
              detTipo Ve = True
              detTipo Vd = True
              detTipo H = False
              detTipo Hb = False
              detTipo Ht = False

agrup_caixas :: X (Caixa, Origem) () -> Fig
agrup_caixas = (map swap).collectLeafs

calcOrigins :: ((X Caixa Tipo),Origem) -> X (Caixa,Origem) ()
calcOrigins = anaL2D (calcOriginsAux.distl.(split (outL2D.p1) (p2)))

calcOriginsAux = (either (i1) (i2.(split (bang.p1) (p2)).aux2.aux1))
                  where aux1 = split (p1.p1) (split (split (p1.p2.p1) (p2)) (split (p2.p2.p1) (p2)))
                        aux2 = split (p1) (split (p1.p2) (split (p1.p2.p2) (calcOriginsAux2)))

calcOriginsAux2 = aplicaCalc.(split (p1) ((split (p2.p2) (dimen.p1.p1)).p2))
                  where aplicaCalc (t, (o, c)) = calc t o c


calc :: Tipo -> Origem -> (Float, Float) -> Origem
calc V (a,b) (x,y) = (a+(x/2), b+y)  
calc Vd (a,b) (x,y) = (a+x, b+y)
calc Ve (a,b) (x,y) = (a, b+y)
calc H (a,b) (x,y) =  (a+x, b+(y/2))
calc Ht (a,b) (x,y) = (a+x, b+y)
calc Hb (a,b) (x,y) = (a+x, b)

caixasAndOrigin2Pict = (G.pictures).(map criarPic).(hyloL2D (agrup) (origens))
                       where agrup = (either (singl) (conc.p2))
                             origens = (calcOriginsAux.distl.(split (outL2D.p1) (p2)))
                             criarPic (((x,y),(t,c)),o) = crCaixa o (fromIntegral x) (fromIntegral y) t c

mostra_caixas :: (L2D, Origem) -> IO()
mostra_caixas = display.caixasAndOrigin2Pict
\end{code}

\subsection*{Problema 3}

\par\noindent\hspace{0.5cm}Uma vez que o cosseno de um ??ngulo pode ser dado pelo somat??rio

\begin{eqnarray*}
  cos\ x = \sum_{i=0}^\infty \frac{(-1)^i}{(2i)!}*x^{2i}
\end{eqnarray*}

podemos fazer um c??lculo deste para um dado \textit{n}, ao inv??s de infinito, por forma a permitir a sua determina????o aproximada.

\begin{eqnarray*}
  cosFst\ x\ n = \sum_{i=0}^{n} \frac{(-1)^i}{(2i)!}*x^{2i}
\end{eqnarray*}

Deste modo, podemos concluir que:

\begin{eqnarray*}
cosFst\ x\ 0 = 1
\end{eqnarray*}
\begin{eqnarray*}
cosFst\ x\ (n+1) = (cosFst\ x\ n) + \frac{(-1)^{n+1}}{(2(n+1))!}*x^{2(n+1)}
\end{eqnarray*}

Se definirmos agora uma nova fun????o \textit{cosSnd} como sendo

\begin{eqnarray*}
cosSnd\ x\ n = \frac{(-1)^{n+1}}{(2(n+1))!}*x^{2(n+1)}
\end{eqnarray*}

teremos \textit{cosFst} e \textit{cosSnd} em recursividade m??tua, tal que:

\begin{eqnarray*}
cosFst\ x\ 0 = 1
\end{eqnarray*}
\begin{eqnarray*}
cosFst\ x\ (n+1) = (cosFst\ x\ n) + (cosSnd\ x\ n)
\end{eqnarray*}
\begin{eqnarray*}
cosSnd\ x\ 0 = \frac{-1}{2}*x^{2}
\end{eqnarray*}
\begin{eqnarray*}
cosSnd\ x\ (n+1) = \frac{(-1)^{n+1+1}}{(2(n+1+1))!}*x^{2(n+1+1)}
\end{eqnarray*}

Desenvolvendo a express??o de \textit{cosSnd} chegamos ?? seguinte defini????o:

\begin{eqnarray*}
cosSnd\ x\ 0 = \frac{-1}{2}*x^{2}
\end{eqnarray*}
\begin{eqnarray*}
cosSnd\ x\ (n+1) = (cosSnd\ x\ n)*\frac{(-1)*x^{2}}{4n^{2}+14n+12}
\end{eqnarray*}

Considerando agora que existe uma fun????o \textit{cosTrd}, expressa da seguinte maneira

\begin{eqnarray*}
cosTrd\ n = 4n^{2}+14n+12
\end{eqnarray*}

podemos chegar a:

\begin{eqnarray*}
cosTrd\ 0 = 12
\end{eqnarray*}
\begin{eqnarray*}
cosTrd\ (n+1) = (cosTrd\ n)+8n+18
\end{eqnarray*}

Se agora criarmos uma ??ltima fun????o, \textit{cosFth}, que ?? definida como se segue

\begin{eqnarray*}
cosFth\ n = 8n+18
\end{eqnarray*}

chegamos ?? conclus??o de que

\begin{eqnarray*}
cosFth\ 0 = 18
\end{eqnarray*}
\begin{eqnarray*}
cosFth\ (n+1) = (cosFth\ n)+8
\end{eqnarray*}

Posto tudo isto, criamos 4 fun????es mutuamente recursivas, desenvolvendo um caso de recursividade m??ltipla, podendo combin??-las da seguinte forma:

\begin{eqnarray*}
cosFst\ x\ 0 = 1
\end{eqnarray*}
\begin{eqnarray*}
cosFst\ x\ (n+1) = (cosFst\ x\ n) + (cosSnd\ x\ n)
\end{eqnarray*}
\begin{eqnarray*}
cosSnd\ x\ 0 = \frac{-1}{2}*x^{2}
\end{eqnarray*}
\begin{eqnarray*}
cosSnd\ x\ (n+1) = (cosSnd\ x\ n)*\frac{(-1)*x^{2}}{(cosTrd\ n)}
\end{eqnarray*}
\begin{eqnarray*}
cosTrd\ 0 = 12
\end{eqnarray*}
\begin{eqnarray*}
cosTrd\ (n+1) = (cosTrd\ n)+(cosFth\ n)
\end{eqnarray*}
\begin{eqnarray*}
cosFth\ 0 = 18
\end{eqnarray*}
\begin{eqnarray*}
cosFth\ (n+1) = (cosFth\ n)+8
\end{eqnarray*}

\subsubsection*{Solu????o}
\par\noindent\hspace{0.5cm}Deste modo, e usando a \textit{regra de algibeira} que nos foi apresentada, podemos definir a fun????o \textit{cos'} como o seguinte ciclo \textit{for}:
\begin{code}
cos' x = prj . for loop init where
   loop (cFs, cS, cT, cFt) = (cFs + cS, cS*((-1)*(x^2)/(cT)), cT + cFt, cFt + 8)
   init = (1, (-0.5)*(x^2), 12, 18)
   prj (cFs, cS, cT, cFt) = cFs
\end{code}

\subsubsection*{Valoriza????o}
\par\noindent\hspace{0.5cm}Tendo em vista a valoriza????o proposta para este problema, e baseando-nos no ciclo \textit{for} obtido anteriormente, em linguagem Haskell, definimos a seguinte fun????o \textit{mycos}, em linguagem C, em que o argumento \textit{x} ?? o valor do ??ngulo cujo cosseno pretendemos obter, e \textit{n} ?? o n??mero de itera????es que o ciclo \textit{for} deve correr (quanto maior o n??mero de itera????es, maior a precis??o do valor do cosseno obtido).
\begin{verbatim}
double mycos (double x, int n){
  double fst = 1, snd = (-0.5)*(x*x), trd = 12, fth = 18;
  for(int i = 0; i < n; i++){
    fst += snd;
    snd *= ((-1)*(x*x)/(trd));
    trd += fth;
    fth += 8;
  }
  return fst;
}
\end{verbatim}
\par\noindent\hspace{0.5cm}Atrav??s da execu????o do c??digo e an??lise dos resultados obtidos, conclu??mos que a partir das 10 itera????es, ou seja, para valores de \textit{n} superiores ou iguais a 10, come??amos a obter valores de cosseno pr??ximos do esperado.
\par Conclu??mos, tamb??m, que dificilmente chegar??amos a esta resolu????o apenas por intui????o, sendo necess??ria a determina????o das express??es auxiliares que levam ?? constru????o da fun????o.

\subsection*{Problema 4}

\subsubsection*{outNode}

\begin{eqnarray*}
\start
  outNode\ .\ [File,\ Dir] = id
%
\just\equiv{\textcolor{blue}{Fus??o-+\ (20)}}
%
  [outNode\ .\ File,\ outNode\ .\  Dir] = id
%
\just\equiv{\textcolor{blue}{Universal-+\ (17)}}
%
        |lcbr(
    id . i1 = outNode . File
  )(
    id . i2 = outNode . Dir
  )|
%
\just\equiv{\textcolor{blue}{Natural-id\ (1),\ Igualdade\ Extensional\ (69),\ Def-comp\ (70)}}
%
      |lcbr(
    outNode(File b) = i1 b
  )(
    outNode(Dir f) = i2 (outFS f)
  )|
\end{eqnarray*}

\vspace{0.5cm}

\subsubsection*{outFS}

\begin{eqnarray*}
\start
  outNode\ .\ FS\ .\ map\ (id\ \times inNode) = id
%
\just\equiv{\textcolor{blue}{}}
%
  outNode\ .\ FS = map\ (id\ \times outNode)
%
\just\equiv{\textcolor{blue}{Igualdade\ Extensional\ (69),\ Def-comp\ (70)}}
%
  outNode\ (FS\ l) = map\ (id\ \times outNode)\ l
\end{eqnarray*}

\vspace{0.5cm}

\subsubsection*{baseFS e recFS}

\vspace{0.5cm}

\xymatrixcolsep{0.5pc}\xymatrixrowsep{5pc}
\centerline{\xymatrix{
   FS \ar[d]_-{|h|}
                \ar@@/^2pc/ [rr]^-{|outFS|} & \qquad \cong
&   (A \times (B + FS))*  \ar[d]^{|map (f >< (g + h))|}
                                     \ar@@/^2pc/ [ll]^-{|inFS|}
\\
    |C| &  & (A' \times (B' + C))*\ar[ll]
}}

\vspace{0.5cm}

\begin{eqnarray*}
\start
  baseFS\ f\ g\ h = {|map (f >< (g + h))|}
%
\end{eqnarray*}

\begin{eqnarray*}
\start
  recFS\ f = {|map (id >< (id + h))|}
%
\just\equiv{\textcolor{blue}{Aplicando\ a\ defini????o\ dada\ de\ baseFS}}
%
  recFS\ f = baseFS\ id\ id\ f
%
\end{eqnarray*}

\vspace{0.5cm}

\subsubsection*{cataFS}

\vspace{0.5cm}

\xymatrixcolsep{0.5pc}\xymatrixrowsep{5pc}
\centerline{\xymatrix{
   FS \ar[d]_-{|h = cataNat(g)|}
                \ar@@/^2pc/ [rr]^-{|outFS|} & \qquad \cong
&   (A \times (B + FS))*  \ar[d]^{|map (id >< (id + h))|}
                                     \ar@@/^2pc/ [ll]^-{|inFS|}
\\
    |C| &  & (A \times (B + C))*\ar[ll]^-{|g|}
}}

\vspace{0.5cm}

\begin{eqnarray*}
\start
\just\equiv{\textcolor{blue}{Cancelamento-cata\ (44)}}
%
  |cataNat g|\ .\ in = g\ .\ F|cataNat g|
%
\just\equiv{\textcolor{blue}{in.out = id}}
%
  |cataNat g|  = g\ .\ F|cataNat g|\ .\ out
%
\just\equiv{\textcolor{blue}{Aplicando as defini????es em Haskell j?? determinadas}}
%
  cataFS\ g = g\ .\ recFS(cataFS\ g)\ .\ outFS
%
\end{eqnarray*}

\vspace{0.5cm}

\subsubsection*{anaFS}

\begin{eqnarray*}
\start
\just\equiv{\textcolor{blue}{Cancelamento-ana\ (53)}}
%
  out\ .\ \ana{g} = F\ana{g}\ .\ g
%
\just\equiv{\textcolor{blue}{in.out = id}}
%
  \ana{g} = in\ .\ F\ana{g}\ .\ g
%
\just\equiv{\textcolor{blue}{Aplicando as defini????es em Haskell j?? determinadas}}
%
  anaFS\ g = inFS\ .\ recFS(anaFS\ g)\ .\ g
%
\end{eqnarray*}

\subsubsection*{hyloFS}

\begin{eqnarray*}
\start
\just\equiv{\textcolor{blue}{Defini????o de hilomorfismo}}
%
  hyloFS\ g\ h = \cata{g}.\ana{h}
%
\just\equiv{\textcolor{blue}{Cancelamento-cata (44); Cancelamento-ana(53)}}
%
  hyloFS\ g\ h = g.F\cata{g}.inFS.outFS.F\ana{h}.h
%
\just\equiv{\textcolor{blue}{in.out = id; Aplicando as defini????es em Haskell j?? determinadas}}
%
  hyloFS\ g\ h = g.recFS(cataFS g).recFS(anaFS h).h
%
\end{eqnarray*}

\vspace{0.5cm}

\vspace{0.5cm}

\subsubsection*{untar}

\par\noindent\hspace{0.5cm}Uma vez que o tipo de entrada desta fun????o se trata de uma lista, ?? plaus??vel considerar que possamos resolver o problema com o recurso a um catamorfismo de listas. Como tal, podemos chegar ao seguinte diagrama representativo de \textit{untar}:

\vspace{0.5cm}

\xymatrixcolsep{0.5pc}\xymatrixrowsep{5pc}
\centerline{\xymatrix{
   (Path \times B)* \ar[d]_-{|untar|}
                \ar@@/^2pc/ [rr]^-{|outList|} & \qquad \cong
&   1 + ((Path \times B) + (Path \times B)*)  \ar[d]^{|id + (id >< untar)|}
                                     \ar@@/^2pc/ [ll]^-{|inList|}
\\
    |FS| &  & 1 + ((Path \times B) \times FS)\ar[ll]^-{|g|}
}}

\vspace{0.5cm}

Deste modo, fica agora a necessidade de definir o gene \textit{g}. A utilidade desta fun????o passa pela transforma????o da lista de pares \textit{(Path a, b)} num ??nico \textit{FS}. Como tal, atrav??s da interpreta????o do diagrama do catamorfismo de listas, chegamos ?? conclus??o de que a cauda da lista ser?? transformada num \textit{FS}, ficando a restar apenas a necessidade de transformar a cabe??a da lista num \textit{FS} tambem, e de alguma forma juntar as duas num ??nico \textit{FS}. Ora, recorrendo as fun????es auxiliares disponibilizadas, podemos chegar ao seguinte diagrama do gene \textit{g}.

\vspace{0.5cm}

\xymatrixcolsep{2pc}\xymatrixrowsep{2pc}
\centerline{\xymatrix {
    1 + ((Path \times B) \times FS) \ar[d]_{id + ((createFSfromFile) \times id)} \\
    1 + (FS \times FS) \ar [d]_{id+(joinDupDirs.concatFS)} \\
    1 + FS \ar[d]_{|[inFS.nil, id]|}\\
    FS\\
}}

\vspace{0.5cm}

A fun????o \textit{createFSfromFile} transforma um par \textit{(Path a, b)} no \textit{FS} correspondente, enquanto que a fun????o \textit{concatFS} concatena dois \textit{FS} num s??. Recorre-se, por fim, ?? fun????o \textit{joinDupDirs}, por forma a juntar diretorias com o mesmo nome, presentes na mesma pasta, numa s?? diretoria, mantendo todos os ficheiros. Deste modo, podemos simplificar o gene da seguinte forma:

\vspace{0.5cm}

\begin{eqnarray*}
\start
  g = [inFS.nil,\ id].(id\ +\ (joinDupDirs.concatFS)).(id\ +\ ((createFSfromFile)\ \times id))
%
\just\equiv{\textcolor{blue}{Absor????o-+ (22); Natural-id (1)}}
%
  g = [inFS.nil,\ joinDupDirs.concatFS.(createFSfromFile \times id)]
%
\end{eqnarray*}

\vspace{0.5cm}

Por fim, podemos concluir que a fun????o \textit{untar} poder?? ser definida como:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{33em}
    \center $ | untar = cataList(either (inFS.nil) (joinDupDirs.concatFS.(createFSfromFile >< id)))| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

\subsubsection*{Solu????o}

Triologia ``ana-cata-hilo":
\begin{code}
outFS (FS l) = map (id >< outNode) l

outNode (File b) = i1 b
outNode (Dir f) = i2 f

baseFS f g h = map (f >< (g -|- h))

cataFS :: ([(a, Either b c)] -> c) -> FS a b -> c
cataFS g = g.recFS(cataFS g).outFS

anaFS :: (c -> [(a, Either b c)]) -> c -> FS a b
anaFS g = inFS.recFS(anaFS g).g

hyloFS g h = g.recFS(cataFS g).recFS(anaFS h).h
\end{code}
Outras fun????es pedidas:
\begin{code}
check :: (Eq a) => FS a b -> Bool
check = undefined

tar :: FS a b -> [(Path a, b)]
tar = undefined

untar :: (Eq a) => [(Path a, b)] -> FS a b
untar = cataList(either (inFS.nil) (joinDupDirs.concatFS.(createFSfromFile >< id)))

find :: (Eq a) => a -> FS a b -> [Path a]
find = undefined

new :: (Eq a) => Path a -> b -> FS a b -> FS a b
new a b f = undefined

cp :: (Eq a) => Path a -> Path a -> FS a b -> FS a b
cp = undefined

rm :: (Eq a) => (Path a) -> (FS a b) -> FS a b
rm = undefined

auxJoin :: ([(a, Either b c)],d) -> [(a, Either b (d,c))]
auxJoin = undefined

cFS2Exp :: a -> FS a b -> (Exp () a)
cFS2Exp = undefined
\end{code}

%----------------- Fim do anexo com solu????es dos alunos ------------------------%

%----------------- ??ndice remissivo (exige makeindex) -------------------------%

\printindex

%----------------- Bibliografia (exige bibtex) --------------------------------%

\bibliographystyle{plain}
\bibliography{cp1819t}

%----------------- Fim do documento -------------------------------------------%
\end{document}
