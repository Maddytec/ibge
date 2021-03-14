package br.com.maddytec.ibgeservice.gateway.http;

import br.com.maddytec.ibgeservice.gateway.json.CidadeList;
import br.com.maddytec.ibgeservice.gateway.json.EstadoJson;
import br.com.maddytec.ibgeservice.gateway.json.EstadoList;
import br.com.maddytec.ibgeservice.service.CidadeService;
import br.com.maddytec.ibgeservice.service.EstadoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/estados")
public class EstadoResource {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    public EstadoList listarEstados() throws InterruptedException, ExecutionException, JsonProcessingException {
        return estadoService.execute();
    }

    @GetMapping("/{uf}/cidades")
    public CidadeList listarCidades(@PathVariable("uf") String uf) throws InterruptedException, ExecutionException, JsonProcessingException {
        return cidadeService.execute(uf);
    }

}
