package CEPE.Importacao;


import CEPE.Municipio.MunicipioRepository;
import CEPE.Polo.PoloRepository;
import CEPE.Zona.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ImportacaoService importacaoService;

    @Autowired
    private MunicipioRepository municipioRepo;

    @Autowired
    private PoloRepository poloRepo;

    @Autowired
    private ZonaRepository zonaRepo;

    @Override
    public void run(String... args) {
        // Verifica se já existem dados no banco
        if (municipioRepo.count() == 0 && poloRepo.count() == 0 && zonaRepo.count() == 0) {
            importacaoService.importarPolos("src/main/resources/csv/polos-sedes.csv");
            importacaoService.importarZonasSedes("src/main/resources/csv/zonas-sedes.csv");
            importacaoService.importarSecoes("src/main/resources/csv/secoes.csv");
            System.out.println("Importação concluída com sucesso!");
        } else {
            System.out.println("Dados já existentes - pulando importação inicial");
        }
    }
}