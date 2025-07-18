package CEPE.Municipio;

import CEPE.Secao.Secao;
import CEPE.Secao.SecaoRepository;
import CEPE.Zona.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class MunicipioController {

    @Autowired
    private MunicipioRepository municipioRepo;

    @Autowired
    private ZonaRepository zonaRepo;

    @Autowired
    private SecaoRepository secaoRepo;

    @GetMapping("/listar-municipios")
    public String listarMunicipios(
            @RequestParam(required = false) String search,
            Model model) {

        List<Municipio> municipios;
        if (search != null && !search.isEmpty()) {
            municipios = municipioRepo.findByNomeContainingIgnoreCase(search);
        } else {
            municipios = municipioRepo.findAll();
        }

          model.addAttribute("municipios", municipios);
        model.addAttribute("zonas", zonaRepo.findAllByMunicipiosIn(municipios));
        model.addAttribute("secoes", secaoRepo.findAllByMunicipioIn(municipios));
        return "municipio/listarMunicipio";
    }



    @GetMapping("/consultar-municipios-cod")
    public String consultarPorCodigo(
            @RequestParam(required = false) Integer codTse,
            Model model) {

        if (codTse == null) {
            return "municipio/codMunicipio";
        }

        Optional<Municipio> municipioOpt = municipioRepo.findById(codTse);

        if (municipioOpt.isPresent()) {
            model.addAttribute("municipio", municipioOpt.get());
        } else {
            model.addAttribute("error", "Município não encontrado com o código TSE: " + codTse);
        }

        return "municipio/codMunicipio";
    }



    @GetMapping("/consultar-secoes-zonas")
    public String buscarPorCodTse( @RequestParam(name = "codTse", required = false) Integer codTse,
                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "size", defaultValue = "10") int size,
                                   Model model) {
        if (codTse != null) {
            Optional<Municipio> municipioOpt = municipioRepo.findById(codTse);

            if (municipioOpt.isPresent()) {
                Municipio municipio = municipioOpt.get();

                Pageable pageable = PageRequest.of(page, size);
                Page<Secao> secoesPage = secaoRepo.findByMunicipio(municipio, pageable);

                model.addAttribute("municipio", municipio.getNome());
                model.addAttribute("dados", secoesPage.getContent());
                model.addAttribute("paginaAtual", page);
                model.addAttribute("totalPaginas", secoesPage.getTotalPages());
                model.addAttribute("size", size);
                model.addAttribute("codTse", codTse);
            } else {
                model.addAttribute("error", "Município não encontrado para o código informado.");
            }
        }

        return "municipio/consultarZonaSecao";
    }



}