package CEPE.Secao;

import CEPE.Municipio.Municipio;
import CEPE.Polo.Polo;
import CEPE.Zona.Zona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SecaoRepository extends JpaRepository<Secao, Long> {
    Optional<Secao> findByNumeroAndMunicipio(Integer numero, Municipio municipio);
    List<Secao> findByMunicipio(Municipio municipio);
    Page<Secao> findByMunicipio(Municipio municipio, Pageable pageable);
    Page<Secao> findAllByZona(Zona zona, Pageable pageable);
    List<Secao> findAllByMunicipioIn(List<Municipio> municipios);
    List<Secao> findByPolo_Numero(Integer poloNumero);
    List<Secao> findByZona_Numero(Integer numeroZona);
}