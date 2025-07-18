package CEPE.Zona;

import CEPE.Municipio.Municipio;
import CEPE.Secao.Secao;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Zona {
    @Id
    private Integer numero;

    @ManyToOne
    @JoinColumn(name = "municipio_sede_id")
    private Municipio municipioSede;

    @ManyToMany
    @JoinTable(
            name = "zona_municipio",
            joinColumns = @JoinColumn(name = "zona_numero"),
            inverseJoinColumns = @JoinColumn(name = "municipio_cod_tse")
    )
    private List<Municipio> municipios = new ArrayList<>();

    @OneToMany(mappedBy = "zona")
    private List<Secao> secoes = new ArrayList<>();

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

    public List<Secao> getSecoes() {
        return secoes;
    }

    public void setSecoes(List<Secao> secoes) {
        this.secoes = secoes;
    }

}
