package CEPE.Polo;

import CEPE.Municipio.Municipio;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Polo {
    @Id
    private Integer numero;

    @OneToOne
    @JoinColumn(name = "municipio_sede_id")
    private Municipio municipioSede;

    @OneToMany(mappedBy = "polo")
    private List<Municipio> municipios = new ArrayList<>();



    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Municipio getMunicipioSede() {
        return municipioSede;
    }

    public void setMunicipioSede(Municipio municipioSede) {
        this.municipioSede = municipioSede;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }
}

