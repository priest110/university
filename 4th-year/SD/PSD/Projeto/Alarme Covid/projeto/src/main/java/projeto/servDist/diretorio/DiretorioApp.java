package projeto.servDist.diretorio;

import projeto.servDist.diretorio.resources.DiretorioRes;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DiretorioApp extends Application<DiretorioConfig>{
    public static void main(String[] args) throws Exception {
        new DiretorioApp().run(args);
    }

    @Override
    public String getName() { return "Diretorio"; }

    @Override
    public void initialize(Bootstrap<DiretorioConfig> bootstrap) { }

    @Override
    public void run(final DiretorioConfig configuration,
                    final Environment environment) {
        environment.jersey().register(
                new DiretorioRes());
    }
}
