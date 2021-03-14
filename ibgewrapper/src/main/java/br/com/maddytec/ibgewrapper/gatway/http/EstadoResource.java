package br.com.maddytec.ibgewrapper.gatway.http;

import br.com.maddytec.ibgewrapper.gatway.json.CidadeJson;
import br.com.maddytec.ibgewrapper.gatway.json.EstadoJson;
import br.com.maddytec.ibgewrapper.service.CidadeService;
import br.com.maddytec.ibgewrapper.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoResource {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    public List<EstadoJson> listarEstados(){
        return estadoService.execute();
    }

    @GetMapping("/{UF}/cidades")
    public List<CidadeJson> listarCidades(@PathVariable("UF") String UF){
        return cidadeService.execute(UF);
    }

}
