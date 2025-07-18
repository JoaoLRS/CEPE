package CEPE.Polo;

import CEPE.Municipio.Municipio;
import CEPE.Municipio.MunicipioRepository;
import CEPE.Secao.SecaoRepository;
import CEPE.Zona.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/polos")
public class PoloController {

    @Autowired
    private PoloRepository poloRepo;


    @Autowired
    private MunicipioRepository municipioRepo;

    @Autowired
    private ZonaRepository zonaRepo;

    @Autowired
    private SecaoRepository secaoRepo;

    @GetMapping("/consultar-polo-detalhes")
    public String consultarPoloDetalhesForm(@RequestParam(name="numeroPolo", required = false) Integer numeroPolo, Model model) {
        model.addAttribute("pageTitle", "Consultar Detalhes do Polo");
        model.addAttribute("numeroPoloConsultado", numeroPolo);

        if (numeroPolo != null) {
            Optional<Polo> poloOpt = poloRepo.findById(numeroPolo);
            if (!poloOpt.isPresent()) {
                model.addAttribute("error", "Polo não encontrado com o número: " + numeroPolo);
            }
        }
        return "polo/formConsultarPoloDetalhes";
    }


    @GetMapping("/{poloNumero}/municipios")
    public String getMunicipiosByPolo(@PathVariable Integer poloNumero, Model model) {
        Optional<Polo> poloOpt = poloRepo.findById(poloNumero);
        if (poloOpt.isPresent()) {
            Polo polo = poloOpt.get();
            List<Municipio> municipios = municipioRepo.findByPolo_Numero(poloNumero); // Use o nome completo da classe Municipio se houver ambiguidade
            model.addAttribute("polo", polo);
            model.addAttribute("municipios", municipios);
            model.addAttribute("pageTitle", "Municípios do Polo " + poloNumero);
        } else {
            model.addAttribute("error", "Polo não encontrado: " + poloNumero);
            model.addAttribute("pageTitle", "Erro ao buscar Polo");
        }
        return "polo/listarMunicipiosPorPolo";
    }


    @GetMapping("/{poloNumero}/zonas")
    public String getZonasByPolo(@PathVariable Integer poloNumero, Model model) {
        Optional<Polo> poloOpt = poloRepo.findById(poloNumero);
        if (poloOpt.isPresent()) {
            Polo polo = poloOpt.get();
            List<CEPE.Secao.Secao> secoesDoPolo = secaoRepo.findByPolo_Numero(poloNumero);
            java.util.Set<CEPE.Zona.Zona> zonasUnicas = secoesDoPolo.stream()
                    .map(CEPE.Secao.Secao::getZona)
                    .filter(java.util.Objects::nonNull)
                    .collect(java.util.stream.Collectors.toSet());

            model.addAttribute("polo", polo);
            model.addAttribute("zonas", new java.util.ArrayList<>(zonasUnicas));
            model.addAttribute("pageTitle", "Zonas do Polo " + poloNumero);
        } else {
            model.addAttribute("error", "Polo não encontrado: " + poloNumero);
            model.addAttribute("pageTitle", "Erro ao buscar Polo");
        }
        return "polo/listarZonasPorPolo";
    }


    @GetMapping("/consultar-polo-por-municipio")
    public String getPoloByMunicipio(@RequestParam(required = false) Integer codTse, Model model) {
        model.addAttribute("pageTitle", "Consultar Polo por Município");
        if (codTse != null) {
            Optional<CEPE.Municipio.Municipio> municipioOpt = municipioRepo.findById(codTse);
            if (municipioOpt.isPresent()) {
                CEPE.Municipio.Municipio municipio = municipioOpt.get();
                model.addAttribute("municipio", municipio);
                if (municipio.getPolo() != null) {
                    model.addAttribute("poloNumero", municipio.getPolo().getNumero());
                } else if (municipio.getNumeroPolo() != null) {
                    model.addAttribute("poloNumero", municipio.getNumeroPolo());
                } else {
                    model.addAttribute("poloError", "Polo não definido para este município.");
                }
            } else {
                model.addAttribute("error", "Município não encontrado com o código TSE: " + codTse);
            }
        }
        return "polo/consultarPoloPorMunicipio";
    }

    @GetMapping("/consultar-polo-por-zona")
    public String getPoloByZona(@RequestParam(required = false) Integer numeroZona, Model model) {
        model.addAttribute("pageTitle", "Consultar Polo por Zona");
        if (numeroZona != null) {
            Optional<CEPE.Zona.Zona> zonaOpt = zonaRepo.findByNumero(numeroZona);
            if (zonaOpt.isPresent()) {
                CEPE.Zona.Zona zona = zonaOpt.get();
                model.addAttribute("zona", zona);

                List<CEPE.Secao.Secao> secoesDaZona = secaoRepo.findByZona_Numero(numeroZona);
                List<Polo> polosDaZona = secoesDaZona.stream()
                        .map(CEPE.Secao.Secao::getPolo)
                        .filter(java.util.Objects::nonNull)
                        .distinct()
                        .collect(java.util.stream.Collectors.toList());

                if (polosDaZona.isEmpty()) {
                    model.addAttribute("poloError", "Nenhum polo associado às seções desta zona, ou esta zona não possui seções cadastradas com polo definido.");
                } else if (polosDaZona.size() == 1) {
                    model.addAttribute("poloNumero", polosDaZona.get(0).getNumero());
                } else {
                    String numerosPolos = polosDaZona.stream()
                            .map(Polo::getNumero)
                            .map(String::valueOf)
                            .collect(java.util.stream.Collectors.joining(", "));
                    model.addAttribute("poloError", "Múltiplos polos (" + numerosPolos + ") associados às seções desta zona. Verifique a configuração.");
                }
            } else {
                model.addAttribute("error", "Zona não encontrada com o número: " + numeroZona);
            }
        }
        return "polo/consultarPoloPorZona";
    }
}