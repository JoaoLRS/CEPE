package CEPE.Importacao;

import CEPE.Municipio.Municipio;
import CEPE.Municipio.MunicipioRepository;
import CEPE.Polo.Polo;
import CEPE.Polo.PoloRepository;
import CEPE.Secao.Secao;
import CEPE.Secao.SecaoRepository;
import CEPE.Zona.Zona;
import CEPE.Zona.ZonaRepository;
import com.opencsv.CSVReader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;

@Service
@Slf4j
@Transactional
public class ImportacaoService {

    @Autowired
    private MunicipioRepository municipioRepo;

    @Autowired
    private ZonaRepository zonaRepo;

    @Autowired
    private PoloRepository poloRepo;

    @Autowired
    private SecaoRepository secaoRepo;

    // Importar zonas-sedes.csv
    public void importarZonasSedes(String caminho) {
        try (CSVReader reader = new CSVReader(new FileReader(caminho))) {
            String[] linha;
            boolean header = true;

            while ((linha = reader.readNext()) != null) {
                if (header) {
                    header = false;
                    continue;
                }

                if (linha.length < 3) {
                    continue;
                }

                Integer numeroZona = Integer.parseInt(linha[0].trim());
                Integer codMunicipioSede = Integer.parseInt(linha[1].trim());
                String nomeMunicipioSede = linha[2].trim();

                Municipio municipio = municipioRepo.findById(codMunicipioSede)
                        .orElseGet(() -> {
                            Municipio m = new Municipio();
                            m.setCodTse(codMunicipioSede);
                            m.setNome(nomeMunicipioSede);
                            return municipioRepo.save(m);
                        });

                Zona zona = zonaRepo.findById(numeroZona)
                        .orElseGet(() -> {
                            Zona z = new Zona();
                            z.setNumero(numeroZona);
                            z.setMunicipioSede(municipio);
                            return zonaRepo.save(z);
                        });

                zona.setMunicipioSede(municipio);
                zonaRepo.save(zona);
            }

            log.info("Importação de zonas-sedes concluída com sucesso!");

        } catch (Exception e) {
            log.error("Erro lendo zonas-sedes.csv", e);
            throw new RuntimeException("Erro lendo zonas-sedes.csv", e);
        }
    }

    // Importar polo-sedes.csv
    public void importarPolos(String caminho) {
        try (CSVReader reader = new CSVReader(new FileReader(caminho))) {
            String[] linha;
            boolean header = true;

            while ((linha = reader.readNext()) != null) {
                if (header) {
                    header = false;
                    continue;
                }

                if (linha.length < 3) {
                    continue;
                }

                Integer numeroPolo = Integer.parseInt(linha[0].trim());
                Integer codMunicipioSede = Integer.parseInt(linha[1].trim());
                String nomeMunicipioSede = linha[2].trim();

                Municipio municipio = municipioRepo.findById(codMunicipioSede)
                        .orElseGet(() -> {
                            Municipio m = new Municipio();
                            m.setCodTse(codMunicipioSede);
                            m.setNome(nomeMunicipioSede);
                            return municipioRepo.save(m);
                        });

                Polo polo = poloRepo.findById(numeroPolo)
                        .orElseGet(() -> {
                            Polo p = new Polo();
                            p.setNumero(numeroPolo);
                            p.setMunicipioSede(municipio);
                            return poloRepo.save(p);
                        });

                polo.setMunicipioSede(municipio);
                poloRepo.save(polo);
            }

            log.info("Importação de polos concluída com sucesso!");

        } catch (Exception e) {
            log.error("Erro lendo polo-sedes.csv", e);
            throw new RuntimeException("Erro lendo polo-sedes.csv", e);
        }
    }

    // Importar secoes.csv
    public void importarSecoes(String caminho) {
        try (CSVReader reader = new CSVReader(new FileReader(caminho))) {
            String[] linha;
            boolean header = true;

            while ((linha = reader.readNext()) != null) {
                if (header) {
                    header = false;
                    continue;
                }

                if (linha.length < 5) {
                    continue;
                }

                Integer codMunicipio = Integer.parseInt(linha[0].trim());
                String nomeMunicipio = linha[1].trim();
                Integer numeroSecao = Integer.parseInt(linha[2].trim());
                Integer numeroZona = Integer.parseInt(linha[3].trim());
                Integer numeroPolo = Integer.parseInt(linha[4].trim());

                // Municipio
                Municipio municipio = municipioRepo.findById(codMunicipio)
                        .orElseGet(() -> {
                            Municipio m = new Municipio();
                            m.setCodTse(codMunicipio);
                            m.setNome(nomeMunicipio);
                            m.setNumeroPolo(numeroPolo);
                            return municipioRepo.save(m);
                        });

                // Polo
                Polo polo = poloRepo.findById(numeroPolo)
                        .orElseGet(() -> {
                            Polo p = new Polo();
                            p.setNumero(numeroPolo);
                            p.setMunicipioSede(municipio);
                            return poloRepo.save(p);
                        });

                if (municipio.getPolo() == null) {
                    municipio.setPolo(polo);
                    municipioRepo.save(municipio);
                }

                // Zona
                Zona zona = zonaRepo.findById(numeroZona)
                        .orElseGet(() -> {
                            Zona z = new Zona();
                            z.setNumero(numeroZona);
                            z.setMunicipioSede(municipio);
                            return zonaRepo.save(z);
                        });

                if (!zona.getMunicipios().contains(municipio)) {
                    zona.getMunicipios().add(municipio);
                    zonaRepo.save(zona);
                }

                if (!municipio.getZonas().contains(zona)) {
                    municipio.getZonas().add(zona);
                    municipioRepo.save(municipio);
                }

                // Secao
                Secao secao = new Secao();
                secao.setNumero(numeroSecao);
                secao.setMunicipio(municipio);
                secao.setZona(zona);
                secao.setPolo(polo);

                secaoRepo.save(secao);
            }

            log.info("Importação de seções concluída com sucesso!");

        } catch (Exception e) {
            log.error("Erro lendo secoes.csv", e);
            throw new RuntimeException("Erro lendo secoes.csv", e);
        }
    }
}
