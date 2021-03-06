package br.com.maddytec.ibgewrapper.gatway.feign;

import br.com.maddytec.ibgewrapper.gatway.json.EstadoJson;
import feign.RequestLine;

import java.util.List;

public interface EstadoClient {

    @RequestLine("GET /api/v1/localidades/estados")
    public List<EstadoJson> get();
}
