package CEPE.Municipio;

import CEPE.Secao.Secao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {
    Optional<Municipio> findByNome(String nome);
    List<Municipio> findByNomeContainingIgnoreCase(String nome);

    List<Municipio> findByPolo_Numero(Integer poloNumero);
}

