%ex2 Room Manager + Autentication(ex1 do guião 2)
-module(frontend).
-export([start/0]).

% Start da app
start() ->
  {ok, LSock} = gen_tcp:listen(3000, [binary, {active, once}, {packet, line}, {reuseaddr, true}]),
  authenticate:start(),
  spawn(fun() -> acceptor(LSock,0) end),
  spawn(fun() -> notification_connection() end),
  ok.

notification_connection() ->
  {ok, Context} = erlzmq:context(),
  {ok, Frontend}= erlzmq:socket(Context, [sub, {active, false}]),
  {ok, Backend} = erlzmq:socket(Context, [pub, {active, false}]),
  erlzmq:connect(Frontend,"tcp://localhost:6101"),
  erlzmq:bind(Backend,"tcp://*:6102"),
  ok = erlzmq:setsockopt(Frontend, subscribe, <<>>),
  notification_connection_proxy(Frontend, Backend).

notification_connection_proxy(Frontend, Backend) ->
  {ok, Msg} = erlzmq:recv(Frontend),
  io:format("Recebi notificação: ~p~p \n", [Frontend, Msg]),
  erlzmq:send(Backend, Msg),
  notification_connection_proxy(Frontend, Backend).  
 
% Aceita clientes que se conectem ao servidor
acceptor(LSock, Counter) ->
  {ok, Sock} = gen_tcp:accept(LSock),
  io:format("Utilizador connectado~n"),
  spawn(fun() -> acceptor(LSock, Counter+1) end),
  user(Sock).


% O user só permite que o socket envie outra mensagem quando recebe a ultima do room,
% isto é, a mensagem já foi atendida (broadcasted) pelo room. 
% mensagem distrito = << <<"\distrito ">>, resto >>

% Responde aos pedidos do utilizador
user(Sock) ->
  Self = self(),
  receive
    {line, {Self, Data}} ->
      inet:setopts(Sock, [{active, once}]),
      gen_tcp:send(Sock, Data),
      user(Sock);
    {line, {_, Data}} ->
      gen_tcp:send(Sock, Data),
      user(Sock);

    {tcp, _, <<"\\register ",Query/binary>>} ->
      RegistoInfo=lists:droplast(binary_to_list(Query)),
      [NewUsername,NewPass,Distrito] = string:tokens(RegistoInfo," "),

      Resposta = authenticate:criar_conta(NewUsername,NewPass,Distrito),
      RespostaBin = atom_to_binary(Resposta,utf8), 

      io:format("Registo com dados:~p ~p ~p~n", [NewUsername, NewPass, Distrito]),    
      inet:setopts(Sock, [{active, once}]),
      gen_tcp:send(Sock, <<"--- Registo: ",RespostaBin/binary," ---\n">>),
      
      case Resposta of
        utilizador_criado -> 
          user(Sock);
        _ ->
          user(Sock)
      end;

    {tcp, _, <<"\\login ",Query/binary>>} ->
      LoginInfo=lists:droplast(binary_to_list(Query)),
      [NewUsername,NewPass] = string:tokens(LoginInfo," "),

      Resposta = authenticate:login(NewUsername, NewPass),
      RespostaBin = atom_to_binary(Resposta,utf8),

      io:format("Login com dados:~p ~p~n", [NewUsername, NewPass]),

      case Resposta of
        login_sucesso -> 
          Distrito = authenticate:distrito(NewUsername),
          DistritoBin = list_to_binary(Distrito),
          io:format("--- Logado no Servidor Distrital:~p ---~n", [Distrito]),
          inet:setopts(Sock, [{active, once}]),
          gen_tcp:send(Sock, <<DistritoBin/binary,"\n">>),
          Sockets = servidores_distritais(),
          client_handler(Sock, Sockets);
        _ ->
          inet:setopts(Sock, [{active, once}]),
          gen_tcp:send(Sock, <<"--- Erro com Login: ",RespostaBin/binary," ---\n">>),
          user(Sock)
      end
  end.

% Handler do cliente após login
client_handler(Sock, Sockets) ->
    receive
        {tcp, _, <<"\\posicao ",Query/binary>>} ->
            PosicaoInfo=lists:droplast(binary_to_list(Query)),
            [PosX,PosY,User,Distrito] = string:tokens(PosicaoInfo," "),
            io:format("Utilizador ~p está na posição:(~p,~p), servidor ~p~n",[User,PosX,PosY,Distrito]),
            
            route_to_distrital(Distrito, <<"\\posicao ",Query/binary>>, Sockets, Sock),
            client_handler(Sock, Sockets);
        
        {tcp, _, <<"\\opcao ",Query/binary>>} ->
            OpcaoInfo=lists:droplast(binary_to_list(Query)),
            [PosX,PosY,User,Opcao,Distrito] = string:tokens(OpcaoInfo," "),
            io:format("Utilizador ~p (~p,~p): envia a opção ~p ao servidor ~p~n",[User,PosX,PosY,Opcao,Distrito]),
            
            if 
              Opcao == "2" ->
                authenticate:infetado(User),
                route_to_distrital(Distrito, <<"\\opcao ",Query/binary>>, Sockets, Sock),
                user(Sock);
              true ->
                route_to_distrital(Distrito, <<"\\opcao ",Query/binary>>, Sockets, Sock),
                client_handler(Sock, Sockets)
            end;    

        {tcp, _, <<"\\logout ",Query/binary>>} ->
            LogoutInfo=lists:droplast(binary_to_list(Query)),
            [PosX,PosY,User,Distrito] = string:tokens(LogoutInfo," "),
            io:format("Logout com dados:(~p,~p) para o ~p~n", [PosX,PosY,User]),

            Resposta = authenticate:logout(User),
            
            case Resposta of
              logout_sucesso -> 
                route_to_distrital(Distrito,<<"\\logout ",Query/binary>>, Sockets, Sock),
                user(Sock);
              _ ->
                io:fwrite("Error\n")
            end;    

        Data ->
            gen_tcp:send(Sock, Data),
            client_handler(Sock, Sockets)   
    end.

% Liga aos servidores distritais
servidores_distritais() ->
    {ok, Context} = erlzmq:context(),
    {ok, Socket1} = erlzmq:socket(Context, [req, {active, false}]),
    erlzmq:connect(Socket1,"tcp://localhost:5551"),
    {ok, Socket2} = erlzmq:socket(Context, [req, {active, false}]),
    erlzmq:connect(Socket2,"tcp://localhost:5552"),
    {ok, Socket3} = erlzmq:socket(Context, [req, {active, false}]),
    erlzmq:connect(Socket3,"tcp://localhost:5553"),
    {ok, Socket4} = erlzmq:socket(Context, [req, {active, false}]),
    erlzmq:connect(Socket4,"tcp://localhost:5554"),
    {ok, Socket5} = erlzmq:socket(Context, [req, {active, false}]),
    erlzmq:connect(Socket5,"tcp://localhost:5555"),
    {ok, Socket6} = erlzmq:socket(Context, [req, {active, false}]),
    erlzmq:connect(Socket6,"tcp://localhost:5556"),
    {ok, Socket7} = erlzmq:socket(Context, [req, {active, false}]),
    erlzmq:connect(Socket7,"tcp://localhost:5557"),
    {ok, Socket8} = erlzmq:socket(Context, [req, {active, false}]),
    erlzmq:connect(Socket8,"tcp://localhost:5558"),
    {ok, Socket9} = erlzmq:socket(Context, [req, {active, false}]),
    erlzmq:connect(Socket9,"tcp://localhost:5559"),
    {ok, Socket10} = erlzmq:socket(Context, [req, {active, false}]),
    erlzmq:connect(Socket10,"tcp://localhost:5560"),
    {ok, Socket11} = erlzmq:socket(Context, [req, {active, false}]),
    erlzmq:connect(Socket11,"tcp://localhost:5561"),
    {ok, Socket12} = erlzmq:socket(Context, [req, {active, false}]),
    erlzmq:connect(Socket12,"tcp://localhost:5562"),
    {ok, Socket13} = erlzmq:socket(Context, [req, {active, false}]),
    erlzmq:connect(Socket13,"tcp://localhost:5563"),
    {ok, Socket14} = erlzmq:socket(Context, [req, {active, false}]),
    erlzmq:connect(Socket14,"tcp://localhost:5564"),
    {ok, Socket15} = erlzmq:socket(Context, [req, {active, false}]),
    erlzmq:connect(Socket15,"tcp://localhost:5565"),
    {ok, Socket16} = erlzmq:socket(Context, [req, {active, false}]),
    erlzmq:connect(Socket16,"tcp://localhost:5566"),
    {ok, Socket17} = erlzmq:socket(Context, [req, {active, false}]),
    erlzmq:connect(Socket17,"tcp://localhost:5567"),
    {ok, Socket18} = erlzmq:socket(Context, [req, {active, false}]),
    erlzmq:connect(Socket18,"tcp://localhost:5568"),
    {Socket1, Socket2, Socket3, Socket4, Socket5, Socket6, Socket7, 
     Socket8, Socket9, Socket10, Socket11, Socket12, Socket13,
     Socket14, Socket15, Socket16, Socket17, Socket18}.

% Route to servidores distritais
route_to_distrital(Distrito, Data, Sockets, Sock) ->
    {Socket1, Socket2, Socket3, Socket4, Socket5, Socket6, Socket7, 
     Socket8, Socket9, Socket10, Socket11, Socket12, Socket13,
     Socket14, Socket15, Socket16, Socket17, Socket18} = Sockets,
    case Distrito of
        "Aveiro" -> send_messages(Socket1, Data, Sock);
        "Beja" -> send_messages(Socket2, Data, Sock);
        "Braga" -> send_messages(Socket3, Data, Sock);
        "Braganca" -> send_messages(Socket4, Data, Sock);
        "Castelo_Branco" -> send_messages(Socket5, Data, Sock);
        "Coimbra" -> send_messages(Socket6, Data, Sock);
        "Evora" -> send_messages(Socket7, Data, Sock);
        "Faro" -> send_messages(Socket8, Data, Sock);
        "Guarda" -> send_messages(Socket9, Data, Sock);
        "Leiria" -> send_messages(Socket10, Data, Sock);
        "Lisboa" -> send_messages(Socket11, Data, Sock);
        "Portalegre" -> send_messages(Socket12, Data, Sock);
        "Porto" -> send_messages(Socket13, Data, Sock);
        "Santarem" -> send_messages(Socket14, Data, Sock);
        "Setubal" -> send_messages(Socket15, Data, Sock);
        "Viana_do_Castelo" -> send_messages(Socket16, Data, Sock);
        "Vila_Real" -> send_messages(Socket17, Data, Sock);
        "Viseu" -> send_messages(Socket18, Data, Sock);
        _ -> io:fwrite("Error\n")
    end.


% Envia mensagens para os servidores distritais
send_messages(Socket, Opcao, Sock) ->
    case erlzmq:send(Socket, Opcao) of
        ok ->
            io:fwrite("Enviei com sucesso\n"),
            io:format("Send message: ~p\n", [Opcao]);
        {error, Reason} ->
            io:fwrite("Enviei sem sucesso"),
            io:format("Failed to send message: ~p, reason: ~p\n", [Opcao, Reason])
    end,
    case erlzmq:recv(Socket) of
        {ok, RecvMessage} ->
            io:format("Recv message: ~p\n", [RecvMessage]),
            inet:setopts(Sock, [{active, once}]),
            gen_tcp:send(Sock, RecvMessage);
        {error, RecvReason} ->
            io:format("Failed to recv, reason: ~p\n", [RecvReason])
    end.

