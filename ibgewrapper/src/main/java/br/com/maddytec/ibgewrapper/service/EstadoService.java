package br.com.maddytec.ibgewrapper.service;

import br.com.maddytec.ibgewrapper.gatway.feign.EstadoClient;
import br.com.maddytec.ibgewrapper.gatway.json.EstadoJson;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    private static final String URL_BASE_IBGE = "https://servicodados.ibge.gov.br/";

    @Cacheable(value = "estado")
    public List<EstadoJson> execute(){
        EstadoClient estadoClient = Feign.builder()
                .decoder(new GsonDecoder())
                .target(EstadoClient.class, URL_BASE_IBGE);

        return estadoClient.get();
    }
}
