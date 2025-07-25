package CEPE.Polo;

import CEPE.Municipio.Municipio;
import CEPE.Secao.Secao;
import CEPE.Zona.Zona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PoloRepository extends JpaRepository<Polo, Integer> {
}