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
       	    Cálculo de Programas
\\
       	Trabalho Prático
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
a85731 & Gonçalo José Azevedo Esteves 
\end{tabular}
\end{center}

\section{Preâmbulo}

A disciplina de \CP\ tem como objectivo principal ensinar
a progra\-mação de computadores como uma disciplina científica. Para isso
parte-se de um repertório de \emph{combinadores} que formam uma álgebra da
programação (conjunto de leis universais e seus corolários) e usam-se esses
combinadores para construir programas \emph{composicionalmente}, isto é,
agregando programas já existentes.
  
Na sequência pedagógica dos planos de estudo dos dois cursos que têm
esta disciplina, restringe-se a aplicação deste método à programação
funcional em \Haskell. Assim, o presente trabalho prático coloca os
alunos perante problemas concretos que deverão ser implementados em
\Haskell.  Há ainda um outro objectivo: o de ensinar a documentar
programas, validá-los, e a produzir textos técnico-científicos de
qualidade.

\section{Documentação} Para cumprir de forma integrada os objectivos
enunciados acima vamos recorrer a uma técnica de programa\-ção dita
``\litp{literária}'' \cite{Kn92}, cujo princípio base é o seguinte:
\begin{quote}\em Um programa e a sua documentação devem coincidir.
\end{quote} Por outras palavras, o código fonte e a documentação de um
programa deverão estar no mesmo ficheiro.

O ficheiro \texttt{cp1819t.pdf} que está a ler é já um exemplo de \litp{programação
literária}: foi gerado a partir do texto fonte \texttt{cp1819t.lhs}\footnote{O
suffixo `lhs' quer dizer \emph{\lhaskell{literate Haskell}}.} que encontrará
no \MaterialPedagogico\ desta disciplina descompactando o ficheiro \texttt{cp1819t.zip}
e executando
\begin{Verbatim}[fontsize=\small]
    $ lhs2TeX cp1819t.lhs > cp1819t.tex
    $ pdflatex cp1819t
\end{Verbatim}
em que \href{https://hackage.haskell.org/package/lhs2tex}{\texttt\LhsToTeX} é
um pre-processador que faz ``pretty printing''
de código Haskell em \Latex\ e que deve desde já instalar executando
\begin{Verbatim}[fontsize=\small]
    $ cabal install lhs2tex
\end{Verbatim}
Por outro lado, o mesmo ficheiro \texttt{cp1819t.lhs} é executável e contém
o ``kit'' básico, escrito em \Haskell, para realizar o trabalho. Basta executar
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
e verifique que assim é: todo o texto que se encontra dentro do ambiente
\begin{quote}\small\tt
\verb!\begin{code}!
\\ ... \\
\verb!\end{code}!
\end{quote}
vai ser seleccionado pelo \GHCi\ para ser executado.

\section{Como realizar o trabalho}
Este trabalho teórico-prático deve ser realizado por grupos de três alunos.
Os detalhes da avaliação (datas para submissão do relatório e sua defesa
oral) são os que forem publicados na \cp{página da disciplina} na \emph{internet}.

Recomenda-se uma abordagem participativa dos membros do grupo
de trabalho por forma a poderem responder às questões que serão colocadas
na \emph{defesa oral} do relatório.

Em que consiste, então, o \emph{relatório} a que se refere o parágrafo anterior?
É a edição do texto que está a ser lido, preenchendo o anexo \ref{sec:resolucao}
com as respostas. O relatório deverá conter ainda a identificação dos membros
do grupo de trabalho, no local respectivo da folha de rosto.

Para gerar o PDF integral do relatório deve-se ainda correr os comando seguintes,
que actualizam a bibliografia (com \Bibtex) e o índice remissivo (com \Makeindex),
\begin{Verbatim}[fontsize=\small]
    $ bibtex cp1819t.aux
    $ makeindex cp1819t.idx
\end{Verbatim}
e recompilar o texto como acima se indicou. Dever-se-á ainda instalar o utilitário
\QuickCheck,
que ajuda a validar programas em \Haskell\ e a biblioteca \gloss{Gloss} para
geração de gráficos 2D:
\begin{Verbatim}[fontsize=\small]
    $ cabal install QuickCheck gloss
\end{Verbatim}
Para testar uma propriedade \QuickCheck~|prop|, basta invocá-la com o comando:
\begin{verbatim}
    > quickCheck prop
    +++ OK, passed 100 tests.
\end{verbatim}

Qualquer programador tem, na vida real, de ler e analisar (muito!) código
escrito por outros. No anexo \ref{sec:codigo} disponibiliza-se algum
código \Haskell\ relativo aos problemas que se seguem. Esse anexo deverá
ser consultado e analisado à medida que isso for necessário.

\Problema

Um compilador é um programa que traduz uma linguagem dita de
\emph{alto nível} numa linguagem (dita de \emph{baixo nível}) que
seja executável por uma máquina.
Por exemplo, o \gcc{GCC} compila C/C++ em código objecto que
corre numa variedade de arquitecturas.

Compiladores são normalmente programas complexos.
Constam essencialmente de duas partes:
o \emph{analisador sintático} que lê o texto de entrada
(o programa \emph{fonte} a compilar) e cria uma sua representação
interna, estruturada em árvore;
e o \emph{gerador de código} que converte essa representação interna
em código executável.
Note-se que tal representação intermédia pode ser usada para outros fins,
por exemplo,
para gerar uma listagem de qualidade (\uk{pretty print}) do programa fonte.

O projecto de compiladores é um assunto complexo que
será assunto de outras disciplinas.
Neste trabalho pretende-se apenas fazer uma introdução ao assunto,
mostrando como tais programas se podem construir funcionalmente à custa de
cata/ana/hilo-morfismos da linguagem em causa.

Para cumprirmos o nosso objectivo, a linguagem desta questão terá que ser, naturalmente,
muito simples: escolheu-se a das expressões aritméticas com inteiros,
\eg\ \( 1 + 2 \), \( 3 * (4 + 5) \) etc.
Como representação interna adopta-se o seguinte tipo polinomial, igualmente simples:
%
\begin{spec}
data Expr = Num Int | Bop Expr Op Expr 
data Op = Op String
\end{spec}

\begin{enumerate}
\item
Escreva as definições dos \{cata, ana e hilo\}-morfismos deste tipo de dados
segundo o método ensinado nesta disciplina (recorde módulos como \eg\ |BTree| etc).
\item
Como aplicação do módulo desenvolvido no ponto 1,
defina como \{cata, ana ou hilo\}-morfismo a função seguinte:
\begin{itemize}
\item |calcula :: Expr -> Int| que calcula o valor
de uma expressão;
\begin{propriedade}
O valor zero é um elemento neutro da adição.
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
As operações de soma e multiplicação são comutativas.
\begin{code}
prop_comuta = calcula . mirror .==. calcula where
            mirror = cataExpr (either Num g2)
            g2 = (uncurry (uncurry Bop)) . (swap >< id) . assocl . (id >< swap)
\end{code}
\end{propriedade}  
\end{itemize}
\item
Defina como \{cata, ana ou hilo\}-morfismos as funções
\begin{itemize}
\item |compile :: String -> Codigo| - trata-se do compilador propriamente
      dito. Deverá ser gerado código posfixo para uma máquina elementar
      de \pda{stack}. O tipo |Codigo| pode ser definido à escolha.
      Dão-se a seguir exemplos de comportamentos aceitáveis para esta
      função:
\begin{verbatim}
Tp4> compile "2+4"
["PUSH 2", "PUSH 4", "ADD"]
Tp4> compile "3*(2+4)"
["PUSH 3", "PUSH 2", "PUSH 4", "ADD", "MUL"]
Tp4> compile "(3*2)+4"
["PUSH 3", "PUSH 2", "MUL", "PUSH 4", "ADD"]
Tp4> 
\end{verbatim}
\item |show' :: Expr -> String| - gera a representação textual
      de uma |Expr| pode encarar-se como o \uk{pretty printer}
      associado ao nosso compilador
\begin{propriedade}
Em anexo, é fornecido o código da função |readExp|, que é ``inversa" da função |show'|,
tal como a propriedade seguinte descreve:
\begin{code}
prop_inv :: Expr -> Bool
prop_inv = p1 . head . readExp . show' .==. id
\end{code}
\end{propriedade}  
\end{itemize}
%% \item Generalize o tipo |Expr| de forma a admitir operadores
%% unários (\eg\ \(-5\)) e repita os exercícios dos pontos anteriores.
\end{enumerate}

\paragraph{Valorização}
Em anexo é apresentado código \Haskell\ que permite declarar
|Expr| como instância da classe |Read|. Neste contexto,  
|read| pode ser vista como o analisador
sintático do nosso minúsculo compilador de expressões aritméticas.

Analise o código apresentado, corra-o e escreva no seu relatório uma explicação
\textbf{breve} do seu funcionamento, que deverá saber defender aquando da
apresentação oral do relatório.

Exprima ainda o analisador sintático |readExp| como um anamorfismo.

\Problema

Pretende-se neste problema definir uma linguagem gráfica \aspas{brinquedo}
a duas dimensões (2D) capaz de especificar e desenhar agregações de
caixas que contêm informação textual.
Vamos designar essa linguagem por |L2D| e vamos defini-la como um tipo
 em \Haskell:
\begin{code}
type L2D = X Caixa Tipo 
\end{code}
onde |X| é a estrutura de dados
\begin{code}
data X a b = Unid a | Comp b (X a b) (X a b) deriving Show
\end{code}
e onde:
\begin{code}
type Caixa = ((Int,Int),(Texto,G.Color))
type Texto = String
\end{code}
Assim, cada caixa de texto é especificada pela sua largura, altura, o seu
texto e a sua côr.\footnote{Pode relacionar |Caixa| com as caixas de texto usadas
nos jornais ou com \uk{frames} da linguagem \Html\ usada na Internet.}
Por exemplo,
\begin{spec}
((200,200),("Caixa azul",col_blue))
\end{spec}
designa a caixa da esquerda da figura \ref{fig:L2D}.

O que a linguagem |L2D| faz é agregar tais caixas tipográficas
umas com as outras segundo padrões especificados por vários
\aspas{tipos}, a saber,
\begin{code}
data Tipo = V | Vd | Ve | H | Ht | Hb 
\end{code}
com o seguinte significado:
\begin{itemize}
\item[|V|] - agregação vertical alinhada ao centro
\item[|Vd|] - agregação vertical justificada à direita
\item[|Ve|] - agregação vertical justificada à esquerda
\item[|H|] - agregação horizontal alinhada ao centro
\item[|Hb|] - agregação horizontal alinhada pela base
\item[|Ht|] - agregação horizontal alinhada pelo topo
\end{itemize}
Como |L2D| instancia o parâmetro |b| de |X| com
|Tipo|, é fácil de ver que cada \aspas{frase} da linguagem
|L2D| é representada por uma árvore binária em que cada nó
indica qual o tipo de agregação a aplicar às suas duas sub-árvores.
%
Por exemplo, a frase
\begin{code}
ex2= Comp Hb  (Unid ((100,200),("A",col_blue)))
              (Unid ((50,50),("B",col_green)))
\end{code}
deverá corresponder à imagem da direita da figura \ref{fig:L2D}.
E poder-se-á ir tão longe quando a linguagem o permita. Por exemplo, pense na
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
\caption{\uk{Layout} feito de várias caixas coloridas.\label{fig:L2D1}}
\end{figure}

É importante notar que cada ``caixa'' não dispõe informação relativa
ao seu posicionamento final na figura. De facto, é a posição relativa
que deve ocupar face às restantes caixas que irá determinar a sua
posição final. Este é um dos objectivos deste trabalho:
\emph{calcular o posicionamento absoluto de cada uma das caixas por forma a
respeitar as restrições impostas pelas diversas agregações}. Para isso vamos
considerar um tipo de dados que comporta a informação de todas as
caixas devidamente posicionadas (i.e. com a informação adicional da
origem onde a caixa deve ser colocada).

\begin{spec}
type Fig = [(Origem,Caixa)]
type Origem = (Float, Float)
\end{spec}
%
A informação mais relevante deste tipo é a referente à lista de
``caixas posicionadas'' (tipo |(Origem,Caixa)|). Regista-se aí a origem
da caixa que, com a informação da sua altura e comprimento, permite
definir todos os seus pontos (consideramos as caixas sempre paralelas
aos eixos). 

\begin{enumerate}
\item Forneça a definição da função |calc_origems|, que calcula as
coordenadas iniciais das caixas no plano:
\begin{spec}
calc_origems :: (L2D,Origem) -> X (Caixa,Origem) ()
\end{spec}
\item Forneça agora a definição da função |agrup_caixas|, que agrupa
todas as caixas e respectivas origens numa só lista:
\begin{spec}
agrup_caixas :: X (Caixa,Origem) () -> Fig
\end{spec}
\end{enumerate}

Um segundo problema neste projecto é \emph{descobrir como visualizar a
informação gráfica calculada por |desenho|}. A nossa estratégia para
superar o problema baseia-se na biblioteca \gloss{Gloss}, que permite a geração
de gráficos 2D. Para tal disponibiliza-se a função
\begin{spec}
crCaixa :: Origem  -> Float -> Float -> String -> G.Color -> G.Picture
\end{spec}
que cria um rectângulo com base numa coordenada, um valor para a largura, um valor
para a altura, um texto que irá servir de etiqueta, e a cor pretendida.
Disponibiliza-se também a função
\begin{spec}
display :: G.Picture -> IO ()
\end{spec}
que dado um valor do tipo |G.picture| abre uma janela com esse valor desenhado. O objectivo
final deste exercício é implementar então uma função 
\begin{spec}
mostra_caixas :: (L2D,Origem) -> IO ()
\end{spec}
que dada uma frase da linguagem |L2D| e coordenadas iniciais apresenta
o respectivo desenho no ecrã.
%
\textbf{Sugestão}: 
Use a função |G.pictures| disponibilizada na biblioteca \gloss{Gloss}.

\Problema

Nesta disciplina estudou-se como fazer \pd{programação dinâmica} por cálculo,
recorrendo à lei de recursividade mútua.\footnote{Lei (\ref{eq:fokkinga})
em \cite{Ol18}, página \pageref{eq:fokkinga}.}

Para o caso de funções sobre os números naturais (|Nat0|, com functor |fF
X = 1 + X|) é fácil derivar-se da lei que foi estudada uma
	\emph{regra de algibeira}
	\label{pg:regra}
que se pode ensinar a programadores que não tenham estudado
\cp{Cálculo de Programas}. Apresenta-se de seguida essa regra, tomando como exemplo o
cálculo do ciclo-\textsf{for} que implementa a função de Fibonacci, recordar
o sistema
\begin{spec}
fib 0 = 1
fib(n+1) = f n

f 0 = 1
f (n+1) = fib n + f n
\end{spec}
Obter-se-á de imediato
\begin{code}
fib' = p1 . for loop init where
   loop(fib,f)=(f,fib+f)
   init = (1,1)
\end{code}
usando as regras seguintes:
\begin{itemize}
\item	O corpo do ciclo |loop| terá tantos argumentos quanto o número de funções mutuamente recursivas.
\item	Para as variáveis escolhem-se os próprios nomes das funções, pela ordem
que se achar conveniente.\footnote{Podem obviamente usar-se outros símbolos, mas numa primeiraleitura
dá jeito usarem-se tais nomes.}
\item	Para os resultados vão-se buscar as expressões respectivas, retirando a variável |n|.
\item	Em |init| coleccionam-se os resultados dos casos de base das funções, pela mesma ordem.
\end{itemize}
Mais um exemplo, envolvendo polinómios no segundo grau a $x^2 + b x + c$ em |Nat0|.
Seguindo o método estudado nas aulas\footnote{Secção 3.17 de \cite{Ol18}.},
de $f\ x = a x^2 + b x + c$ derivam-se duas funções mutuamente recursivas:
\begin{spec}
f 0 = c
f (n+1) = f n + k n

k 0 = a + b
k(n+1) = k n + 2 a
\end{spec}
Seguindo a regra acima, calcula-se de imediato a seguinte implementação, em Haskell:
\begin{code}
f' a b c = p1 . for loop init where
  loop(f,k) = (f+k,k+2*a)
  init = (c,a+b) 
\end{code}

Qual é o assunto desta questão, então? Considerem fórmula que dá a série de Taylor da
função coseno:
\begin{eqnarray*}
	cos\ x = \sum_{i=0}^\infty \frac{(-1)^i}{(2i)!} x^{2i}
\end{eqnarray*}
Pretende-se o ciclo-\textsf{for} que implementa a função 
|cos' x n| que dá o valor dessa série tomando |i| até |n| inclusivé:
\begin{spec}
cos' x = cdots . for loop init where cdots
\end{spec}
%
\textbf{Sugestão}: Começar por estudar muito bem o processo de cálculo dado
no anexo \ref{sec:recmul} para o problema (semelhante) da função exponencial.

\begin{propriedade}
Testes de que |cos' x| calcula bem o coseno de |pi| e o coseno de |pi/2|:
\begin{code}
prop_cos1 n = n >= 10 ==> abs(cos pi - cos' pi n) < 0.001
prop_cos2 n = n >= 10 ==> abs(cos (pi/2) - cos' (pi/2) n) < 0.001
\end{code}
\end{propriedade}

\paragraph{Valorização} Transliterar |cos'| para a linguagem C; compilar
e testar o código. Conseguia, por intuição apenas, chegar a esta função?

\Problema

Pretende-se nesta questão desenvolver uma biblioteca de funções para
manipular \emph{sistemas de ficheiros} genéricos.
Um sistema de ficheiros será visto como uma associação de \emph{nomes}
a ficheiros ou \emph{directorias}.
Estas últimas serão vistas como sub-sistemas de ficheiros e assim
recursivamente.
Assumindo que |a| é o tipo dos identificadores dos ficheiros e
directorias, e que |b| é o tipo do conteúdo dos ficheiros,
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
representará um sistema de ficheiros em cuja raíz temos um ficheiro chamado
|f1| com conteúdo |"Ola"| e uma directoria chamada |"d1"| constituída por dois
ficheiros, um chamado |"f2"| e outro chamado |"f3"|, ambos com conteúdo |"Ole"|.
%
Neste caso, tanto o tipo dos identificadores como o tipo do conteúdo dos
ficheiros é |String|. No caso geral, o conteúdo de um ficheiro é arbitrário:
pode ser um binário, um texto, uma colecção de dados, etc.

A definição das usuais funções |inFS| e |recFS| para este tipo é a seguinte:
\begin{code}
inFS = FS . map (id >< inNode) 
inNode = either File Dir

recFS f = baseFS id id f
\end{code}
Suponha que se pretende definir como um |catamorfismo| a função que
conta o número de ficheiros existentes num sistema de ficheiros. Uma
possível definição para esta função seria:
\begin{code}
conta :: FS a b -> Int
conta = cataFS (sum . map ((either (const 1) id) . snd))
\end{code}
O que é para fazer:
\begin{enumerate}
\item Definir as funções |outFS|, |baseFS|, |cataFS|, |anaFS| e |hyloFS|.

\item Apresentar, no relatório, o diagrama de |cataFS|.

\item Definir as seguintes funções para manipulação de sistemas de
  ficheiros usando, obrigatoriamente, catamorfismos, anamorfismos ou
  hilomorfismos:

  \begin{enumerate}
  \item Verificação da integridade do sistema de ficheiros (i.e. verificar
    que não existem identificadores repetidos dentro da mesma directoria). \\
    |check :: FS a b -> Bool|
  \begin{propriedade}
    A integridade de um sistema de ficheiros não depende da ordem em que os    
    últimos são listados na sua directoria:
\begin{code}
prop_check :: FS String String -> Bool
prop_check = check . (cataFS (inFS . reverse)).==. check
\end{code}
  \end{propriedade}  
  \item Recolha do conteúdo de todos os ficheiros num arquivo indexado pelo \emph{path}.\\
    |tar :: FS a b -> [(Path a, b)]|
  \begin{propriedade}
    O número de ficheiros no sistema deve ser igual ao número de ficheiros
    listados pela função |tar|.
\begin{code}
prop_tar :: FS String String -> Bool
prop_tar = length . tar .==. conta 
\end{code}
  \end{propriedade}  
  \item Transformação de um arquivo com o conteúdo dos ficheiros
    indexado pelo \emph{path} num sistema de ficheiros.\\
    |untar :: [(Path a, b)] -> FS a b| \\
  \textbf{Sugestão}: Use a função |joinDupDirs| para juntar directorias que estejam na mesma
  pasta e que possuam o mesmo identificador.
  \begin{propriedade}
    A composição |tar . untar| preserva o número de ficheiros no sistema.
\begin{code}
prop_untar :: [(Path String, String)] -> Property
prop_untar = validPaths .==>. ((length . tar . untar) .==. length)
validPaths :: [(Path String, String)] -> Bool
validPaths = (== 0) . length . (filter (\(a,_) -> length a == 0))
\end{code}
\end{propriedade}  
  \item Localização de todos os \emph{paths} onde existe um
    determinado ficheiro.\\
    |find :: a -> FS a b -> [Path a]|
  \begin{propriedade}
    A composição |tar . untar| preserva todos os ficheiros no sistema.
\begin{code}
prop_find :: String -> FS String String -> Bool
prop_find = curry $ 
      length . (uncurry find) .==. length . (uncurry find) . (id >< (untar . tar))
\end{code}
  \end{propriedade}  
  \item Criação de um novo ficheiro num determinado \emph{path}.\\
    |new :: Path a -> b -> FS a b -> FS a b|
  \begin{propriedade}
A adição de um ficheiro não existente no sistema não origina ficheiros duplicados.
\begin{code}
prop_new :: ((Path String,String), FS String String) -> Property
prop_new = ((validPath .&&&. notDup) .&&&. (check . p2)) .==>. 
      (checkFiles . (uncurry (uncurry new)))  where
      validPath = (/=0) . length . p1 . p1
      notDup = not . (uncurry elem) . (p1 >< ((fmap p1) . tar))
\end{code}
\textbf{Questão}: Supondo-se que no código acima se substitui a propriedade
|checkFiles| pela propriedade mais fraca |check|, será que a propriedade
|prop_new| ainda é válida? Justifique a sua resposta. 
\end{propriedade}
 
\begin{propriedade}
	A listagem de ficheiros logo após uma adição nunca poderá ser menor que a listagem
	de ficheiros antes dessa mesma adição.
\begin{code}
prop_new2 :: ((Path String,String), FS String String) -> Property
prop_new2 = validPath .==>. ((length . tar . p2) .<=. (length . tar . (uncurry (uncurry new)))) where
      validPath = (/=0) . length . p1 . p1
\end{code}
  \end{propriedade}  
  \item Duplicação de um ficheiro.\\
    |cp :: Path a -> Path a -> FS a b -> FS a b|
  \begin{propriedade}
    A listagem de ficheiros com um dado nome não diminui após uma duplicação.
\begin{code}
prop_cp :: ((Path String, Path String),  FS String String) -> Bool
prop_cp =   length . tar . p2 .<=. length . tar . (uncurry (uncurry cp))
\end{code}
  \end{propriedade}  
  \item Eliminação de um ficheiro.\\
    |rm :: Path a -> FS a b -> FS a b|

  \textbf{Sugestão}: Construir um anamorfismo |nav :: (Path a, FS a b) -> FS a b|
  que navegue por um sistema de ficheiros tendo como base o |path| dado como argumento.
    \begin{propriedade}
    Remover duas vezes o mesmo ficheiro tem o mesmo efeito que o remover apenas uma vez.
\begin{code}
prop_rm :: (Path String, FS String String) -> Bool
prop_rm = (uncurry rm ) . (split p1 (uncurry rm)) .==. (uncurry rm)
\end{code}
\end{propriedade}
\begin{propriedade}
Adicionar um ficheiro e de seguida remover o mesmo não origina novos ficheiros no sistema.
\begin{code}
prop_rm2 :: ((Path String,String), FS String String) -> Property
prop_rm2 = validPath  .==>. ((length . tar . (uncurry rm) . (split (p1. p1) (uncurry (uncurry new)))) 
         .<=. (length . tar . p2)) where
         validPath = (/=0) . length . p1 . p1
\end{code}
\end{propriedade}  
  \end{enumerate}
\end{enumerate}

\paragraph{Valorização} 

Definir uma função para visualizar em \graphviz{Graphviz}
a estrutura de um sistema de ficheiros. A Figura~\ref{ex_prob1}, por exemplo,
apresenta a estrutura de um sistema com precisamente dois ficheiros dentro
de uma directoria chamada |"d1"|. 

Para realizar este exercício será necessário apenas  escrever o anamorfismo
\begin{quote}
|cFS2Exp :: (a, FS a b) -> (Exp () a)| 
\end{quote}
que converte a estrutura de um sistema de ficheiros numa árvore de expressões
descrita em \href{http://wiki.di.uminho.pt/twiki/pub/Education/CP/MaterialPedagogico/Exp.hs}{Exp.hs}.
A função |dotFS| depois tratará de passar a estrutura do sistema de ficheiros para o visualizador.
\begin{figure}
\centering
\includegraphics[scale=0.5]{cp1819t_media/fs.png}
\caption{Exemplo de um sistema de ficheiros visualizado em \graphviz{Graphviz}.}
\label{ex_prob1}
\end{figure}

%----------------- Programa, bibliotecas e código auxiliar --------------------%

\newpage

\part*{Anexos}

\appendix

\section{Como exprimir cálculos e diagramas em LaTeX/lhs2tex}
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

Os diagramas podem ser produzidos recorrendo à \emph{package} \LaTeX\ 
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

\section{Programação dinâmica por recursividade múltipla}\label{sec:recmul}
Neste anexo dão-se os detalhes da resolução do Exercício \ref{ex:exp} dos apontamentos da
disciplina\footnote{Cf.\ \cite{Ol18}, página \pageref{ex:exp}.},
onde se pretende implementar um ciclo que implemente
o cálculo da aproximação até |i=n| da função exponencial $exp\ x = e^x$
via série de Taylor:
\begin{eqnarray}
	exp\ x 
& = &
	\sum_{i=0}^{\infty} \frac {x^i} {i!}
\end{eqnarray}
Seja $e\ x\ n = \sum_{i=0}^{n} \frac {x^i} {i!}$ a função que dá essa aproximação.
É fácil de ver que |e x 0 = 1| e que $|e x (n+1)| = |e x n| + \frac {x^{n+1}} {(n+1)!}$.
Se definirmos $|h x n| = \frac {x^{n+1}} {(n+1)!}$ teremos |e x| e |h x| em recursividade
mútua. Se repetirmos o processo para |h x n| etc obteremos no total três funções nessa mesma
situação:
\begin{spec}
e x 0 = 1
e x (n+1) = h x n + e x n

h x 0 = x
h x (n+1) = x/(s n) * h x n

s 0 = 2
s (n+1) = 1 + s n
\end{spec}
Segundo a \emph{regra de algibeira} descrita na página \ref{pg:regra} deste enunciado,
ter-se-á, de imediato:
\begin{code}
e' x = prj . for loop init where
     init = (1,x,2)
     loop(e,h,s)=(h+e,x/s*h,1+s)
     prj(e,h,s) = e
\end{code}

\section{Código fornecido}\label{sec:codigo}

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
Instâncias:
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
A seguinte função cria uma caixa a partir dos seguintes parâmetros: origem,
largura, altura, etiqueta e côr de preenchimento.
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
Função para visualizar resultados gráficos:
\begin{code}
display = G.display (G.InWindow "Problema 4" (400, 400) (40, 40)) G.white 
\end{code}

\subsection*{Problema 4}
Funções para gestão de sistemas de ficheiros:
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
Funções auxiliares:
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
\item Nível 0
\begin{code}
f1 = FS [("f1", File "hello world")]
f2 = FS [("f2", File "more content")]
f00 = concatFS (f1,f2)
f01 = concatFS (f1, mkdir("d1", efs))
f02 = mkdir("d1",efs)
\end{code}
\item Nível 1
\begin{code}
f10 = mkdir("d1", f00)
f11 = concatFS(mkdir("d1", f00), mkdir("d2", f00))
f12 = concatFS(mkdir("d1", f00), mkdir("d2", f01))
f13 = concatFS(mkdir("d1", f00), mkdir("d2", efs))
\end{code}
\item Nível 2
\begin{code}
f20 = mkdir("d1", f10)
f21 = mkdir("d1", f11)
f22 = mkdir("d1", f12)
f23 = mkdir("d1", f13)
f24 = concatFS(mkdir("d1",f10), mkdir("d2",f12))
\end{code}
\item Sistemas de ficheiros inválidos:
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
Visualização em \graphviz{Graphviz}:
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

\subsection*{Outras funções auxiliares}
%----------------- Outras definições auxiliares -------------------------------------------%
Lógicas:
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
Compilação e execução dentro do interpretador:\footnote{Pode ser útil em testes
envolvendo \gloss{Gloss}. Nesse caso, o teste em causa deve fazer parte de uma função
|main|.}
\begin{code}

run = do { system "ghc cp1819t" ; system "./cp1819t" }
\end{code}

%----------------- Soluções dos alunos -----------------------------------------%

\section{Soluções dos alunos}\label{sec:resolucao}
Os alunos devem colocar neste anexo as suas soluções aos exercícios
propostos, de acordo com o "layout" que se fornece. Não podem ser
alterados os nomes ou tipos das funções dadas, mas pode ser adicionado texto e/ou 
outras funções auxiliares que sejam necessárias.

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
\just\equiv{\textcolor{blue}{Fusão-+\ (20)}}
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
\just\equiv{\textcolor{blue}{Aplicando\ a\ definição\ dada\ de\ baseExpr}}
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
\just\equiv{\textcolor{blue}{Aplicando as definições em Haskell já determinadas}}
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
\just\equiv{\textcolor{blue}{Aplicando as definições em Haskell já determinadas}}
%
  anaExpr\ g = inExpr\ .\ recExpr(anaExpr\ g)\ .\ g
%
\end{eqnarray*}

\vspace{0.5cm}

\subsubsection*{hyloExpr}

\begin{eqnarray*}
\start
\just\equiv{\textcolor{blue}{Definição de hilomorfismo}}
%
  hyloExpr\ g\ h = \cata{g}.\ana{h}
%
\just\equiv{\textcolor{blue}{Cancelamento-cata (44); Cancelamento-ana(53)}}
%
  hyloExpr\ g\ h = g.F\cata{g}.inExpr.outExpr.F\ana{h}.h
%
\just\equiv{\textcolor{blue}{in.out = id; Aplicando as definições em Haskell já determinadas}}
%
  hyloExpr\ g\ h = g.recExpr(cataExpr g).recExpr(anaExpr h).h
%
\end{eqnarray*}

\vspace{0.5cm}

\subsubsection*{calcula}

\par\noindent\hspace{0.5cm}Por forma a obtermos o valor final de uma expressão, podemos recorrer ao uso do catamorfismo de \textit{Expr}, definindo o diagrama de \textit{calcula} da seguinte maneira.

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

Neste caso, percorremos todas as expressões, de modo a determinar o valor de cada uma, aplicando posteriormente a cada par de valores (obtido a partir de cada par de expressões) a operação determinada pelo \textit{Op}, emparelhado, inicialmente, com cada par de expressões (agora um par de \textit{Int}). Ou seja, o gene \textit{g} pode ser representado pelo seguinte diagrama:

\vspace{0.5cm}

\xymatrixcolsep{2pc}\xymatrixrowsep{2pc}
\centerline{\xymatrix {
    Int + (Op \times (Int \times Int)) \ar[d]_{id + ((operAux.outOp) \times id)} \\
    Int + (F \times (Int \times Int)) \ar [d]_{id+cal} \\
    Int + Int \ar [d]_{[id, id]}\\
    Int \\
}}

\vspace{0.5cm}

Onde \textit{operAux} é a função que, dada uma \textit{String} correspondende ao símbolo de uma operação aritmética, devolve essa mesma operação; e \textit{cal} é a função que recebe um par com uma operação aritmética e um par de \textit{Int} e devolve o resultado da aplicação dessa operação aos elementos do par de inteiros.
Assim sendo, podemos afirmar que o gene \textit{g} se define como:

\vspace{0.5cm}

\begin{eqnarray*}
\start
  g = [id,\ id].(id\ +\ cal).(id\ +\ ((operAux.outOp)\ \times id))
%
\just\equiv{\textcolor{blue}{Absorção-+ (22)}}
%
  g = [id.id.id,\ id.cal.((operAux.outOp) \times id)]
%
\just\equiv{\textcolor{blue}{Natural-id (1); Def-x (10)}}
%
  g = [id,\ cal.<operAux.outOp.\pi1,\ \pi2>]
%
\end{eqnarray*}

\vspace{0.5cm}

Por fim, podemos concluir que a função \textit{calcula} poderá ser definida como:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{23em}
    \center $ | calcula = cataExpr ( either (id) (cal.(split (operAux.outOp.p1) (p2))))| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

\subsubsection*{show'}

\par\noindent\hspace{0.5cm}Tal como para \textit{calcula}, podemos definir a função \textit{show'} como um catamorfismo de \textit{Expr}. Assim sendo, chegamos ao seguinte diagrama representativo de \textit{show'}.

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

Após isto, é necessário definir o gene deste catamorfismo. Para esta função, a ideia será determinar o formato em \textit{String} de cada uma das \textit{Expr}, concatenando-as depois com o respetivo operador aritmético associado, guardado em cada \textit{Op}. No entanto, temos de manter o formato "original" da expressão, ou seja, a concatenação terá de respeitar a seguinte ordem: a \textit{String} da esquerda (do par), seguida do operador aritmético, seguida da \textit{String} da direita. Deste modo, chegamos a seguinte representação por diagrama do gene \textit{g}.

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

Neste diagrama, a função \textit{pcurv} coloca uma \textit{String} dentro de parêntesis curvos, enquanto que a função \textit{showMod} aplica a função \textit{show} a um inteiro, transformando-o na \textit{String} correspondente, e caso o inteiro seja negativo, ainda aplica a função \textit{pcurv} ao valor em \textit{String} desse inteiro. Assim sendo, o gene \textit{g} poderá ser definido da seguinte forma:

\vspace{0.5cm}

\begin{eqnarray*}
\start
  g = [showMod,\ id].(id\ +\ pcurv.conc).(id\ +\ (conc.swap\ \times id)).(id\ +\ (assocl.(outOp\ \times id)))
%
\just\equiv{\textcolor{blue}{Absorção-+ (22), Natural-id (1)}}
%
  g = [showMod,\ pcurv.conc.(conc.swap \times id).assocl.(outOp\ \times id)]
%
\just\equiv{\textcolor{blue}{Def-x (10)}}
%
  g = [showMod,\ pcurv.conc.<conc.swap.\pi1,\ \pi2>.assocl.<outOp.\pi1,\ \pi2>]
%
\end{eqnarray*}

\vspace{0.5cm}

Finalizando, podemos definir a função \textit{show'} como:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{40em}
    \center $ | show' = cataExpr (either (showMod) (pcurv.conc.(split (conc.swap.p1) (p2)).assocl.(split (outOp.p1) (p2))))| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

\subsubsection*{compile}

\par\noindent\hspace{0.5cm}Por forma a ser mais fácil definir esta função, podemos recorrer à função dada \textit{readExp}, que transforma uma \textit{String} numa lista de pares \textit{Expr} e \textit{String}. Sabendo que o primeiro par da lista criada por \textit{readExp} possui a \textit{Expr} da \textit{String} recebida como input, podemos obtê-la, tal como o diagrama demonstra.

\vspace{0.5cm}

\xymatrixcolsep{2pc}\xymatrixrowsep{2pc}
\centerline{\xymatrix {
    String \ar[d]_{readExp} \\
    (Expr \times String)* \ar [d]_{\pi1.head} \\
    Expr\\
}}

\vspace{0.5cm}

Agora, podemos, novamente, recorrer ao catamorfismo de \textit{Expr}, por forma a obter a definição de \textit{compilaAux}, que será a função que, para uma dada expressão, a transforma em código-máquina.

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

Posto isto, teremos de determinar o gene para este catamorfismo. Neste caso, a intenção será concatenar o par de \textit{Codigo} obtido de cada par de \textit{Expr}, e a este concatenar, no fim, a operação aritmética a aplicar, "traduzida" para linguagem máquina. Isto deve-se ao facto de que, em linguagem máquina, primeiro são introduzidos os dois valores aos quais se vai aplicar uma operação, e só depois a respetiva operação. Assim sendo, chegamos ao diagrama representativo do gene deste catamorfismo.

\vspace{0.5cm}

\xymatrixcolsep{2pc}\xymatrixrowsep{2pc}
\centerline{\xymatrix {
    Int + (Op \times (Codigo \times Codigo)) \ar[d]_{id + (swap.(singl.get.outOp \times conc))} \\
    Int + (Codigo \times Codigo) \ar [d]_{[singl.push, conc]} \\
    Codigo \\
}}

\vspace{0.5cm}

Convém realçar que a função \textit{get} transforma uma \textit{String} que seja um símbolo de uma operação aritmética, na \textit{String} representativa dessa operação em linguagem máquina; e que a função \textit{push} concatena a expressão \textit{PUSH} à representação, em \textit{String}, do valor do \textit{Int} que recebe (representação essa fornecida pela função \textit{show}). Como tal, podemos definir o gene \textit{g} como: 

\vspace{0.5cm}

\begin{eqnarray*}
\start
  g = [singl.push,\ conc].(id\ +\ (swap.(singl.get.outOp \times conc)))
%
\just\equiv{\textcolor{blue}{Absorção-+ (22), Natural-id (1)}}
%
  g = [singl.push,\ conc.swap.(singl.get.outOp \times conc)]
%
\just\equiv{\textcolor{blue}{Def-x (10); swap = (split (p2) (p1)); Fusão-x (9)}}
%
  g = [singl.push,\ conc.<\pi2.<singl.get.outOp.\pi1,\ conc.\pi2>,\ \pi1.<singl.get.outOp.\pi1,\ conc.\pi2>>]
\just\equiv{\textcolor{blue}{Cancelamento-x (7)}}
%
  g = [singl.push,\ conc.<conc.\pi2,\ singl.get.outOp.\pi1>]
%
\end{eqnarray*}

\vspace{0.5cm}

Deste modo, podemos definir a função \textit{compileAux} como:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{33em}
    \center $ | compileAux = cataExpr (either (singl.push) (conc.(split (conc.p2) (singl.get.outOp.p1))))| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

Concluindo, assim, a seguinte definição de \textit{compile}:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{19em}
    \center $ | compile = compileAux.p1.head.readExp| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

\subsubsection*{Solução}
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


\subsubsection*{Valorização}

\par\noindent\hspace{0.5cm}Para começar, a função \textit{readExp} consegue "compreender" que as operações compreendidas entre parêntesis são prioritárias e, como tal, estas deverão estar compreendidas dentro de uma mesma \textit{Expr}.
\par Esta função interpreta a \textit{String} inserida, dividindo a em partes: separa os números, os operadores aritméticos e as expressões compreendidas entre parêntesis. Posto isto, começa a construir a expressão final por partes, criando primeiramente uma \textit{Expr} com o primeiro número/expressão inserido, o primeiro operador inserido (fora de parêntesis) e, por fim, o segundo número/expressão inserido/a; o restante que não foi lido, é guardado numa \textit{String}, que emparelha com a \textit{Expr} criada. Para criar o segundo par, a função pega no par anteriormente criado, coloca a \textit{Expr} lá presente como primeira \textit{Expr} da nova \textit{Expr} (no conjunto \textit{Bop Expr Op Expr}); depois, pega na \textit{String} emparelhada e vai buscar o próximo operador (que será o primeiro caractér da \textit{String}) e o próximo número/expressão, que será a segunda \textit{Expr} dentro da nova \textit{Expr} que está a ser criada. Mais uma vez, guarda o resto da \textit{String} não lida numa \textit{String}, emparelhada com a \textit{Expr} recém-criada.
\par Isto ocorre sucessivamente, até que a \textit{String} fica vazia, o que significa que a \textit{Expr} emparelhada com esta \textit{String} vazia, será a \textit{Expr} final. Deste modo, ficamos com uma lista de pares \textit{(Expr, String)}, estando presente na cabeça da lista a \textit{Expr} correspondente à \textit{String} introduzida.

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
\just\equiv{\textcolor{blue}{Fusão-+\ (20)}}
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
\just\equiv{\textcolor{blue}{Aplicando\ a\ definição\ dada\ de\ baseL2D}}
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
\just\equiv{\textcolor{blue}{Aplicando as definições em Haskell já determinadas}}
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
\just\equiv{\textcolor{blue}{Aplicando as definições em Haskell já determinadas}}
%
  anaL2D\ g = inL2D\ .\ recL2D(anaL2D\ g)\ .\ g
%
\end{eqnarray*}

\subsubsection*{hyloL2D}

\begin{eqnarray*}
\start
\just\equiv{\textcolor{blue}{Definição de hilomorfismo}}
%
  hyloL2D\ g\ h = \cata{g}.\ana{h}
%
\just\equiv{\textcolor{blue}{Cancelamento-cata (44); Cancelamento-ana(53)}}
%
  hyloL2D\ g\ h = g.F\cata{g}.inL2D.outL2D.F\ana{h}.h
%
\just\equiv{\textcolor{blue}{in.out = id; Aplicando as definições em Haskell já determinadas}}
%
  hyloL2D\ g\ h = g.recL2D(cataL2D g).recL2D(anaL2D h).h
%
\end{eqnarray*}

\vspace{0.5cm}

\vspace{0.5cm}

\subsubsection*{dimen}

\par\noindent\hspace{0.5cm}Logo à partida, sabemos que quando duas caixas são agregadas na vertical, independetemente de como as agregamos (\textit{V}, \textit{Ve} ou \textit{Vd}), a altura total do conjunto será a soma das alturas das duas caixas, enquanto que a sua largura será igual á da caixa mais larga. O mesmo se aplica, de forma inversa, a quando da agregação de caixas na horizontal (\textit{H}, \textit{Ht} ou \textit{Hb}) - a sua largura será a soma das larguras de ambas as caixas, enquanto que a altura será igual à da caixa mais alta. Posto isto, e recorrendo ao uso de um catamorfismo, podemos definir o seguinte diagrama:

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

Deste modo, fica assim a ser necessário determinar o gene \textit{g}. No caso de apenas termos uma caixa, as dimensões desta já estão declaradas, em formato \textit{Int}. Como tal, é apenas necessário aplicar-lhes apenas a função \textit{fromIntegral}, de modo a ficar compatível com o tipo de saída da função \textit{dimen} (\textit{(Float, Float)}). No caso de termos um par de \textit{L2D} e o \textit{Tipo} de agregação que as une, após aplicarmos a função \textit{recL2D (dimen)} vamos ficar com dois pares de \textit{Float}, um para cada \textit{L2D}. Uma vez que as caixas da direita são sempre agregadas as da esquerda tendo em conta o tipo associado ao par \textit{L2D}, vamos calcular a dimensão total de um par de \textit{L2D} usando as dimensões de ambas as caixas e o tipo associado.  Assim sendo, podemos definir o gene \textit{g} com o seguinte diagrama:

\vspace{0.5cm}

\xymatrixcolsep{2pc}\xymatrixrowsep{2pc}
\centerline{\xymatrix {
    Caixa + (Tipo \times ((Float \times Float) \times (Float \times Float))) \ar[d]_{[(fromIntegral \times fromIntegral).\pi1, aux]} \\
    (Float \times Float) \\
}}

\vspace{0.5cm}

Agora, é necessário definir a função \textit{aux}, que tratará de calcular as dimensões de um par de caixas, usando as dimensões de cada uma e sabendo a forma como estão unidas. Assim sendo, o mais fácil será defini-la usando um condicional, da seguinte forma:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{32em}
    \center $ |aux = (cond (detTipo.p1) (((uncurry max) >< (uncurry (+))).p2) (((uncurry (+)) >< (uncurry max)).p2)).get| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

Neste caso, a função \textit{detTipo} determina o tipo de união das caixas - se for uma união vertical (\textit{V, Ve, Vd}) retorna \textit{True}, se for uma união horizontal (\textit{H, Ht, Hb}) retorna \textit{False}; enquanto que a função \textit{get} reorganiza os pares com as dimensões, juntando as larguras no primeiro par e as alturas no segundo. Ou seja, para um input de entrada igual a \textit{((x,y),(a,b))} ficamos com \textit{((x,a),(y,b))}. Deste modo, quando estamos perante o "caso verdadeiro", ou seja, quando verificamos que a união é vertical, determinamos qual a largura máxima, de entre as duas caixas, e calculamos a soma das alturas. Quando estamos perante uma união horizontal, fazemos o oposto. No fim de tudo isto, podemos chegar à seguinte definição de \textit{dimen}:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{26em}
    \center $ |dimen = cataL2D (either ((fromIntegral >< fromIntegral).p1) (aux))| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

\subsubsection*{collectLeafs}

\par\noindent\hspace{0.5cm}De forma indutiva, é possível chegar a uma definição para \textit{collectLeafs}, tendo por base o catamorfismo de \textit{L2D}. Como tal, poderemos estabelecer o seguinte diagrama:

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

É agora necessário, no entanto, definir o gene \textit{g} do catamorfismo. Ora, como o nosso objetivo será simplesmente determinar uma lista de \textit{A}'s, para qualquer \textit{A}, precisamos, então, de determinar a lista de \textit{A}'s de cada um dos \textit{X} do par, e concatenar as listas. Assim sendo, poderemos definir o diagrama que se segue para caracterizar o gene do catamorfismo.

\vspace{0.5cm}

\xymatrixcolsep{2pc}\xymatrixrowsep{2pc}
\centerline{\xymatrix {
    A + (B \times (A* \times A*)) \ar[d]_{[singl, conc.\pi2]} \\
    A* \\
}}

\vspace{0.5cm}

Deste modo, podemos concluir a seguinte definição de \textit{collectLeafs}:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{17em}
    \center $ | collectLeafs = cataL2D (either (singl) (conc.p2))| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

\subsubsection*{agrupcaixas}

\par\noindent\hspace{0.5cm}Olhando para a tipagem da função (recebe um \textit{(X (Caixa, Origem) ())} e devolve uma \textit{Fig}, que é uma lista de \textit{(Origem, Caixa)}), podemos concluir que a função devolve todas as "folhas", às quais é aplicada um swap. Como tal, podemos chegar à seguinte definição da função \textit{agrupcaixas}:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{18em}
    \center $ | agrup_caixas = (map swap).collectLeafs| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

\subsubsection*{calcOrigins}

\par\noindent\hspace{0.5cm}Uma vez que a tipagem da função nos indica que ela parte de um \textit{X A B} e chega a outro \textit{X A B}, podemos assumir que esta função poderá ser definida como um anamorfismo de \textit{L2D}, apesar de possuirem \textit{A} e \textit{B} diferentes. Como tal, chegamos ao seguinte diagrama

\vspace{0.5cm}

\xymatrixcolsep{1pc}\xymatrixrowsep{5pc}
\centerline{\xymatrix{
  X\ar[d]_-{|calcOrigins|}
                \ar [rr]^-{|h|} & &  (Caixa \times Origem) + (1 \times (X \times X))  \ar[d]^{|id + (id >< (calcOrigins >< calcOrigins))|}
\\
    |X'| &  &  (Caixa \times Origem) + (1 \times (X' \times X'))\ar[ll]^-{|inL2D|}
}}

\vspace{0.5cm}

Onde \textit{X} é o tipo \textit{(L2D, Origem)} e \textit{X'} o tipo \textit{( X (Caixa, Origem) ())}. Deste modo, necessitamos agora de definir o gene \textit{h} do anamorfismo. Uma vez que a nossa função recebe todas as caixas guardadas na estrutura \textit{L2D} e a origem inicial, o nosso intuito será atribuir a origem à primeira caixa (que será a que estará o mais à esquerda possível) e depois calcular a origem da caixa da direita, tendo por base as dimensões que a caixa da esquerda ocupa, e o tipo de enquandramento que queremos fazer. Como tal, podemos desenvolver o gene \textit{h} da seguinte forma:

\vspace{0.5cm}

\xymatrixcolsep{2pc}\xymatrixrowsep{2pc}
\centerline{\xymatrix {
    L2D \times Origem \ar[d]_{outL2D \times id} \\
    (Caixa + (Tipo \times (L2D \times L2D))) \times Origem \ar [d]_{distl} \\
    (Caixa \times Origem) + ((Tipo \times (L2D \times L2D)) \times Origem) \ar [d]_{calcOriginsAux}\\
    (Caixa \times Origem) + (1 \times (X' \times X')) \\
}}

\vspace{0.5cm}

Posto isto, é agora necessário definir a função \textit{calcOriginsAux}. Este poderá ser definida através do seguinte diagrama:

\vspace{0.5cm}

\xymatrixcolsep{2pc}\xymatrixrowsep{2pc}
\centerline{\xymatrix {
    (Caixa \times Origem) + ((Tipo \times (L2D \times L2D)) \times Origem) \ar[d]_{id+aux1} \\
    (Caixa \times Origem) + (Tipo \times ((L2D \times Origem) \times (L2D \times Origem))) \ar [d]_{id+aux2} \\
    (Caixa \times Origem) + (Tipo \times ((L2D \times Origem) \times (L2D \times Origem))) \ar [d]_{id+(! \times id)}\\
    (Caixa \times Origem) + (1 \times ((L2D \times Origem) \times (L2D \times Origem))) \\
}}

\vspace{0.5cm}

Neste caso, a função \textit{aux1} irá "distribuir" a origem inicial pelos dois \textit{L2D} existentes, criando dois pares \textit{L2D x Origem}, enquanto que a função \textit{aux2} apenas irá alterar a origem do segundo par, atualizando-a tendo em conta a dimensão da \textit{L2D} do primeiro par (determinada através do uso da função \textit{dimen}), o \textit{Tipo} que caracteriza a união dos dois \textit{L2D} e a origem inicial. Estes atributos serão utilizados pela função \textit{calc} para determinar a nova origem. Deste modo, podemos definir a função \textit{calcOriginsAux} como:

\vspace{0.5cm}

\begin{eqnarray*}
\start
  calcOriginsAux = (id\ +\ (!\ \times id)).(id\ +\ aux2).(id\ +\ aux1)
%
\just\equiv{\textcolor{blue}{Def-+ (21), Absorção-+ (22), Natural-id (1)}}
%
  calcOriginsAux = [i1,\ i2.(!\ \times id).aux2.aux1)]
%
\just\equiv{\textcolor{blue}{Def-x (10)}}
%
  calcOriginsAux = [i1,\ i2.<!.\pi1,\ id.\pi2>.aux2.aux1)]
%
\end{eqnarray*}

\vspace{0.5cm}

Onde \textit{aux1} e \textit{aux2} são definidas como:

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

Assim sendo, podemos definir a função \textit{calcOrigins} como:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{28em}
    \center $ | calcOrigins = anaL2D (calcOriginsAux.distl.(split (outL2D.p1) (p2))) | $
\end{minipage}}
\end{center}

\vspace{0.5cm}

\subsubsection*{caixasAndOrigin2Pict}

\par\noindent\hspace{0.5cm}Uma vez que temos definida a função \textit{crCaixa}, caso consigamos "juntar" cada caixa com a sua respetiva origem, podemos aplicar esta função para criar a \textit{G.Picture} correspondente. Ora, uma vez que temos as funções \textit{calcOrigins} e \textit{agrupcaixas} definidas, conseguimos percorrer toda a árvore de caixas, e juntar cada caixa com a sua origem respetiva, criando uma lista de pares \textit{(Origem, Caixa)}. No entanto, a função \textit{agrupcaixas} aplica um \textit{swap} que neste caso será desnecessário; mais ainda, as funções são baseadas num anamorfismo e num catamorfismo, respetivamente, o que nos leva a pensar que as poderemos juntar num hilomorfismo. Como o tipo de entrada de \textit{calcOrigins} é o mesmo que o de \textit{caixasAndOrigin2Pict} (\textit{(L2D,Origem)}), o tipo de saida de \textit{calcOrigins} é o mesmo que o tipo de entrada de \textit{agrupcaixas} (\textit{X (Caixa, Origem) ()}), e o tipo de saída de \textit{agrupcaixas}, se não considerarmos o \textit{swap}, é \textit{[(Caixa, Origem)]}, podemos criar o seguinte hilomorfismo.

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{32em}
    \center $ | hilo = hyloL2D ((either (singl) (conc.p2))) (calcOriginsAux.distl.(split (outL2D.p1) (p2))) | $
\end{minipage}}
\end{center}

\vspace{0.5cm}

Posto isto, precisamos agora de aplicar a função \textit{crCaixa} a cada par, por forma a criar uma lista de \textit{G.Picture}. Podemos fazê-lo, utilizando a seguinte função, que recebe um par \textit{(Caixa, Origem)}:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{34em}
    \center $ | criarPic (((x,y),(t,c)),o) = crCaixa o (fromIntegral x) (fromIntegral y) t c | $
\end{minipage}}
\end{center}

\vspace{0.5cm}

Neste caso, o par \textit{(x,y)} representa as dimensões da caixa, \textit{t} representa o texto escrito nela, \textit{c} a cor usada para preencher a caixa, e \textit{o} a origem. Assim sendo, aplicando esta função a todos os elementos da lista de pares, criamos uma lista de \textit{G.Picture}. Havendo uma função \textit{G.pictures} que transforma uma lista de figuras numa só figura, podemos definir \textit{caixasAndOrigin2Pict} como:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{27em}
    \center $ | caixasAndOrigin2Pict = (G.pictures).(map criarPic).hilo| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

\subsubsection*{mostracaixas}

\par\noindent\hspace{0.5cm}Aproveitando a função \textit{caixasAndOrigin2Pict}, que transforma um \textit{(L2D, Origem)} numa \textit{G.Picture}, e a função \textit{display}, que transforma uma figura em \textit{IO ()}, podemos definir a função \textit{mostracaixas} como:

\begin{center}
\fbox{\begin{minipage}{21em}
    \center $ | mostra_caixas = display.caixasAndOrigin2Pict| $
\end{minipage}}
\end{center}


\subsubsection*{Solução}

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

\par\noindent\hspace{0.5cm}Uma vez que o cosseno de um ângulo pode ser dado pelo somatório

\begin{eqnarray*}
  cos\ x = \sum_{i=0}^\infty \frac{(-1)^i}{(2i)!}*x^{2i}
\end{eqnarray*}

podemos fazer um cálculo deste para um dado \textit{n}, ao invês de infinito, por forma a permitir a sua determinação aproximada.

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

Se definirmos agora uma nova função \textit{cosSnd} como sendo

\begin{eqnarray*}
cosSnd\ x\ n = \frac{(-1)^{n+1}}{(2(n+1))!}*x^{2(n+1)}
\end{eqnarray*}

teremos \textit{cosFst} e \textit{cosSnd} em recursividade mútua, tal que:

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

Desenvolvendo a expressão de \textit{cosSnd} chegamos à seguinte definição:

\begin{eqnarray*}
cosSnd\ x\ 0 = \frac{-1}{2}*x^{2}
\end{eqnarray*}
\begin{eqnarray*}
cosSnd\ x\ (n+1) = (cosSnd\ x\ n)*\frac{(-1)*x^{2}}{4n^{2}+14n+12}
\end{eqnarray*}

Considerando agora que existe uma função \textit{cosTrd}, expressa da seguinte maneira

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

Se agora criarmos uma última função, \textit{cosFth}, que é definida como se segue

\begin{eqnarray*}
cosFth\ n = 8n+18
\end{eqnarray*}

chegamos à conclusão de que

\begin{eqnarray*}
cosFth\ 0 = 18
\end{eqnarray*}
\begin{eqnarray*}
cosFth\ (n+1) = (cosFth\ n)+8
\end{eqnarray*}

Posto tudo isto, criamos 4 funções mutuamente recursivas, desenvolvendo um caso de recursividade múltipla, podendo combiná-las da seguinte forma:

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

\subsubsection*{Solução}
\par\noindent\hspace{0.5cm}Deste modo, e usando a \textit{regra de algibeira} que nos foi apresentada, podemos definir a função \textit{cos'} como o seguinte ciclo \textit{for}:
\begin{code}
cos' x = prj . for loop init where
   loop (cFs, cS, cT, cFt) = (cFs + cS, cS*((-1)*(x^2)/(cT)), cT + cFt, cFt + 8)
   init = (1, (-0.5)*(x^2), 12, 18)
   prj (cFs, cS, cT, cFt) = cFs
\end{code}

\subsubsection*{Valorização}
\par\noindent\hspace{0.5cm}Tendo em vista a valorização proposta para este problema, e baseando-nos no ciclo \textit{for} obtido anteriormente, em linguagem Haskell, definimos a seguinte função \textit{mycos}, em linguagem C, em que o argumento \textit{x} é o valor do ângulo cujo cosseno pretendemos obter, e \textit{n} é o número de iterações que o ciclo \textit{for} deve correr (quanto maior o número de iterações, maior a precisão do valor do cosseno obtido).
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
\par\noindent\hspace{0.5cm}Através da execução do código e análise dos resultados obtidos, concluímos que a partir das 10 iterações, ou seja, para valores de \textit{n} superiores ou iguais a 10, começamos a obter valores de cosseno próximos do esperado.
\par Concluímos, também, que dificilmente chegaríamos a esta resolução apenas por intuição, sendo necessária a determinação das expressões auxiliares que levam à construção da função.

\subsection*{Problema 4}

\subsubsection*{outNode}

\begin{eqnarray*}
\start
  outNode\ .\ [File,\ Dir] = id
%
\just\equiv{\textcolor{blue}{Fusão-+\ (20)}}
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
\just\equiv{\textcolor{blue}{Aplicando\ a\ definição\ dada\ de\ baseFS}}
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
\just\equiv{\textcolor{blue}{Aplicando as definições em Haskell já determinadas}}
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
\just\equiv{\textcolor{blue}{Aplicando as definições em Haskell já determinadas}}
%
  anaFS\ g = inFS\ .\ recFS(anaFS\ g)\ .\ g
%
\end{eqnarray*}

\subsubsection*{hyloFS}

\begin{eqnarray*}
\start
\just\equiv{\textcolor{blue}{Definição de hilomorfismo}}
%
  hyloFS\ g\ h = \cata{g}.\ana{h}
%
\just\equiv{\textcolor{blue}{Cancelamento-cata (44); Cancelamento-ana(53)}}
%
  hyloFS\ g\ h = g.F\cata{g}.inFS.outFS.F\ana{h}.h
%
\just\equiv{\textcolor{blue}{in.out = id; Aplicando as definições em Haskell já determinadas}}
%
  hyloFS\ g\ h = g.recFS(cataFS g).recFS(anaFS h).h
%
\end{eqnarray*}

\vspace{0.5cm}

\vspace{0.5cm}

\subsubsection*{untar}

\par\noindent\hspace{0.5cm}Uma vez que o tipo de entrada desta função se trata de uma lista, é plausível considerar que possamos resolver o problema com o recurso a um catamorfismo de listas. Como tal, podemos chegar ao seguinte diagrama representativo de \textit{untar}:

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

Deste modo, fica agora a necessidade de definir o gene \textit{g}. A utilidade desta função passa pela transformação da lista de pares \textit{(Path a, b)} num único \textit{FS}. Como tal, através da interpretação do diagrama do catamorfismo de listas, chegamos à conclusão de que a cauda da lista será transformada num \textit{FS}, ficando a restar apenas a necessidade de transformar a cabeça da lista num \textit{FS} tambem, e de alguma forma juntar as duas num único \textit{FS}. Ora, recorrendo as funções auxiliares disponibilizadas, podemos chegar ao seguinte diagrama do gene \textit{g}.

\vspace{0.5cm}

\xymatrixcolsep{2pc}\xymatrixrowsep{2pc}
\centerline{\xymatrix {
    1 + ((Path \times B) \times FS) \ar[d]_{id + ((createFSfromFile) \times id)} \\
    1 + (FS \times FS) \ar [d]_{id+(joinDupDirs.concatFS)} \\
    1 + FS \ar[d]_{|[inFS.nil, id]|}\\
    FS\\
}}

\vspace{0.5cm}

A função \textit{createFSfromFile} transforma um par \textit{(Path a, b)} no \textit{FS} correspondente, enquanto que a função \textit{concatFS} concatena dois \textit{FS} num só. Recorre-se, por fim, à função \textit{joinDupDirs}, por forma a juntar diretorias com o mesmo nome, presentes na mesma pasta, numa só diretoria, mantendo todos os ficheiros. Deste modo, podemos simplificar o gene da seguinte forma:

\vspace{0.5cm}

\begin{eqnarray*}
\start
  g = [inFS.nil,\ id].(id\ +\ (joinDupDirs.concatFS)).(id\ +\ ((createFSfromFile)\ \times id))
%
\just\equiv{\textcolor{blue}{Absorção-+ (22); Natural-id (1)}}
%
  g = [inFS.nil,\ joinDupDirs.concatFS.(createFSfromFile \times id)]
%
\end{eqnarray*}

\vspace{0.5cm}

Por fim, podemos concluir que a função \textit{untar} poderá ser definida como:

\vspace{0.5cm}

\begin{center}
\fbox{\begin{minipage}{33em}
    \center $ | untar = cataList(either (inFS.nil) (joinDupDirs.concatFS.(createFSfromFile >< id)))| $
\end{minipage}}
\end{center}

\vspace{0.5cm}

\subsubsection*{Solução}

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
Outras funções pedidas:
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

%----------------- Fim do anexo com soluções dos alunos ------------------------%

%----------------- Índice remissivo (exige makeindex) -------------------------%

\printindex

%----------------- Bibliografia (exige bibtex) --------------------------------%

\bibliographystyle{plain}
\bibliography{cp1819t}

%----------------- Fim do documento -------------------------------------------%
\end{document}
