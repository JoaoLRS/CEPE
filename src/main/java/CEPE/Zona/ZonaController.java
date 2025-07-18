package CEPE.Zona;


import CEPE.Secao.Secao;
import CEPE.Secao.SecaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ZonaController {

    @Autowired
    private ZonaRepository zonaRepo;

    @Autowired
    private SecaoRepository secaoRepo;


    @GetMapping("/listar-zonas")
    public String consultarZonaPorNumero(
            @RequestParam(name = "numeroZona", required = false) Integer numeroZona,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model
    ) {
        if (numeroZona == null) {
            return "zona/consultarZona";
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("numero").ascending());

        Optional<Zona> zonaOptional = zonaRepo.findByNumero(numeroZona);

        if (zonaOptional.isEmpty()) {
            model.addAttribute("error", "Zona não encontrada.");
            return "zona/consultarZona";
        }

        Zona zona = zonaOptional.get();

        Page<Zona> pageZona = new PageImpl<>(
                List.of(zona),
                pageable,
                1
        );

        model.addAttribute("dados", pageZona.getContent());
        model.addAttribute("paginaAtual", pageZona.getNumber());
        model.addAttribute("totalPaginas", pageZona.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("numeroZona", numeroZona);

        return "zona/consultarZona";
    }


    @GetMapping("/listar-secoes-por-zona")
    public String listarSecoesPorZona(
            @RequestParam(name = "numeroZona", required = false) Integer numeroZona,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model
    ) {
        if (numeroZona == null) {
            return "zona/consultarSecao";
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("numero").ascending());

        Optional<Zona> zonaOptional = zonaRepo.findByNumero(numeroZona);

        if (zonaOptional.isEmpty()) {
            model.addAttribute("error", "Zona não encontrada.");
            return "zona/consultarSecao";
        }

        Zona zona = zonaOptional.get();

        Page<Secao> secoesPage = secaoRepo.findAllByZona(zona, pageable);

        model.addAttribute("secoesPage", secoesPage); // 👈 Aqui vai o Page inteiro
        model.addAttribute("paginaAtual", secoesPage.getNumber());
        model.addAttribute("totalPaginas", secoesPage.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("numeroZona", numeroZona);

        return "zona/consultarSecao"; // Verificar se o HTML está correto
    }

}