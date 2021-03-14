package br.com.maddytec.ibgewrapper.gatway.feign;

import br.com.maddytec.ibgewrapper.gatway.json.CidadeJson;
import br.com.maddytec.ibgewrapper.gatway.json.EstadoJson;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface CidadeClient {

    @RequestLine("GET /api/v1/localidades/estados/{UF}/municipios")
    public List<CidadeJson> get(@Param("UF") String uf );
}
