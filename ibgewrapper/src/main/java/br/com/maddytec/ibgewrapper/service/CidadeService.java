package br.com.maddytec.ibgewrapper.service;

import br.com.maddytec.ibgewrapper.gatway.feign.CidadeClient;
import br.com.maddytec.ibgewrapper.gatway.feign.EstadoClient;
import br.com.maddytec.ibgewrapper.gatway.json.CidadeJson;
import br.com.maddytec.ibgewrapper.gatway.json.EstadoJson;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    private static final String URL_BASE_IBGE = "https://servicodados.ibge.gov.br/";

    public List<CidadeJson> execute(String idUf){
        CidadeClient cidadeClient = Feign.builder()
                .decoder(new GsonDecoder())
                .target(CidadeClient.class, URL_BASE_IBGE);

        return cidadeClient.get(idUf);
    }
}
