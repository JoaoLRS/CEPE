package CEPE.Zona;

import CEPE.Municipio.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, Integer> {
    Optional<Zona> findByNumero(Integer numero);

    List<Zona> findAllByMunicipios(List<Municipio> municipios);

    List<Zona> findAllByMunicipiosIn(List<Municipio> municipios);
}