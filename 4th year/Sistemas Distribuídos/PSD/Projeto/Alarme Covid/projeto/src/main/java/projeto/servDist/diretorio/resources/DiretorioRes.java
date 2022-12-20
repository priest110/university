package projeto.servDist.diretorio.resources;

import projeto.servDist.diretorio.Diretorio;
import projeto.servDist.diretorio.representations.DistritoRep;
import projeto.servDist.diretorio.representations.MedioRep;
import projeto.servDist.diretorio.representations.Top5Rep;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/diretorio")
@Produces(MediaType.APPLICATION_JSON)
public class DiretorioRes {
    private Diretorio diretorio;

    public DiretorioRes(){
        this.diretorio = new Diretorio();
    }

    /**
     * Consulta do nº de utilizadores e infetados de um dado distrito
     * @return da page respetiva
     */
    @GET
    @Path("/distritos/{nome}")
    public Response getDistrito(@PathParam("nome") String nome){
        int utilizadores = this.diretorio.getUtilizadores().get(nome).intValue();
        int infetados = this.diretorio.getInfetados().get(nome).intValue();

        DistritoRep dr = new DistritoRep(nome, utilizadores, infetados);
        return Response.status(200).entity(dr).build();
    }

    /**
     * Consulta do top5
     * @return da page respetiva
     */
    @GET
    @Path("/top5")
    public Response getTop5(){
        List<String> top5_racio = this.diretorio.top5_racio();
        List<String> top5_localizacoes = this.diretorio.top5_locais();
        Top5Rep tr = new Top5Rep(top5_racio, top5_localizacoes);
        return Response.status(200).entity(tr).build();
    }

    /**
     * Consulta do nº médio
     * @return da page respetiva
     */
    @GET
    @Path("/medio")
    public Response getMedio(){
        double medio = this.diretorio.possiveis_infetados();

        MedioRep mr = new MedioRep(medio);
        return Response.status(200).entity(mr).build();
    }

    /**
     * POST de um utilizador.
     */
    @POST
    @Path("/add/utilizador")
    public void putUtilizador(String query){
        this.diretorio.putUtilizadores(query);
    }

    /**
     * POST de um infetado.
     */
    @POST
    @Path("/add/infetado")
    public void putInfetado(String distrito){
        this.diretorio.putInfetados(distrito);
    }

    /**
     * POST de um possível infetado.
     */
    @POST
    @Path("/add/possivel_infetado")
    public void putPossivelInfetado(String distrito){
        this.diretorio.putPossiveisInfetados(distrito);
    }

}
