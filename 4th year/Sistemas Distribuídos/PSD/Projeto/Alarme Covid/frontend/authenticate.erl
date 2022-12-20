-module(authenticate).
-export([start/0,stop/0,criar_conta/3,login/2,logout/1,distrito/1,infetado/1,online/0]).

% Para iniciar um processo responsável por gerir o estado dos utilizadores do sistema
start()->
    Pid = spawn(fun()->loop(#{}) end),
    register(?MODULE,Pid).

% Para parar o loop
stop()->
    ?MODULE ! stop,
    unregister(?MODULE).

% Loop responsável por registo/login/logout de utilizadores
loop(Utilizadores)->
    receive 
        {{criar_conta,Utilizador, Pass, Distrito},From} ->
            case maps:find(Utilizador,Utilizadores) of 
                {ok,_} -> 
                    From ! {utilizador_ja_existe,?MODULE},
                    loop(Utilizadores);
                _ -> 
                    From ! {utilizador_criado,?MODULE},
                    loop(maps:put(Utilizador,{Pass,false,Distrito,"saudavel"},Utilizadores))
            end;
        {{login, Utilizador, Pass},From} ->
            case maps:find(Utilizador,Utilizadores) of 
                {ok,{Pass,false,_,"saudavel"}} -> 
                    From ! {login_sucesso,?MODULE},
                    {_,_,Distrito,_} = maps:get(Utilizador,Utilizadores),
                    loop(maps:update(Utilizador,{Pass,true,Distrito,"saudavel"},Utilizadores));
                {ok,{Pass,true,_,_}} -> 
                    From ! {utilizador_ja_logado,?MODULE},
                    loop(Utilizadores);
                {ok,{Pass,_,_,"doente"}} -> 
                    From ! {utilizador_infetado,?MODULE},
                    loop(Utilizadores);    
                {ok,_} -> 
                    From ! {pass_invalida,?MODULE},
                    loop(Utilizadores);
                error -> 
                    From ! {utilizador_invalido,?MODULE},
                    loop(Utilizadores)
            end;
        {{logout,Utilizador},From} ->
            case maps:find(Utilizador,Utilizadores) of 
                {ok,{Pass,true,_,_}} -> 
                    From ! {logout_sucesso,?MODULE},
                    {Pass,_,Distrito, Estado} = maps:get(Utilizador,Utilizadores),
                    loop(maps:update(Utilizador,{Pass,false,Distrito,Estado},Utilizadores));
                error ->
                    From ! {utilizador_invalido,?MODULE},
                    loop(Utilizadores)
            end;
        {{distrito,Utilizador},From} ->
            case maps:find(Utilizador,Utilizadores) of 
                {ok,_} -> 
                    {_,_,Distrito,_} = maps:get(Utilizador,Utilizadores),
                    From ! {Distrito,?MODULE},
                    loop(Utilizadores);
                error ->
                    From ! {utilizador_invalido,?MODULE},
                    loop(Utilizadores)
            end;
        {{infetado,Utilizador},From} ->
            case maps:find(Utilizador,Utilizadores) of 
                {ok,_} -> 
                    From ! {ficou_infetado,?MODULE},
                    {Pass,_,Distrito,_} = maps:get(Utilizador,Utilizadores),
                    loop(maps:update(Utilizador,{Pass,false,Distrito,"doente"},Utilizadores));
                error ->
                    From ! {utilizador_invalido,?MODULE},
                    loop(Utilizadores)
            end;
        {{online},From} ->
            LoggedInAccNames = maps:keys(
                maps:filter(
                    fun(_,{_,LoggedIn, _, _}) -> LoggedIn end,
                    Utilizadores
                )
            ),
            From ! {LoggedInAccNames,?MODULE}, 
            loop(Utilizadores);
        stop -> true;
        _ -> error_logger:error_report("Unknown message on login_manager"),
        loop(Utilizadores)
    end.



rpc(Req)->
    ?MODULE ! {Req,self()},
    receive
        {Result, ?MODULE}->Result
    end.

criar_conta(Utilizador,Pass,Distrito) -> 
    rpc({criar_conta,Utilizador,Pass,Distrito}).

login(Utilizador, Pass) -> 
    rpc({login,Utilizador,Pass}).

logout(Utilizador) -> 
    rpc({logout,Utilizador}).

distrito(Utilizador) ->
    rpc({distrito,Utilizador}).  

infetado(Utilizador) ->
    rpc({infetado,Utilizador}).          

online() -> 
    rpc({online}).
