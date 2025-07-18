package CEPE.Municipio;

import CEPE.Polo.Polo;
import CEPE.Zona.Zona;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Municipio {
    @Id
    private Integer codTse;

    private String nome;

    private Integer numeroPolo;

    @ManyToMany(mappedBy = "municipios")
    private List<Zona> zonas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "polo_id")
    private Polo polo;

    public Integer getCodTse() {
        return codTse;
    }

    public void setCodTse(Integer codTse) {
        this.codTse = codTse;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumeroPolo() {
        return numeroPolo;
    }

    public void setNumeroPolo(Integer numeroPolo) {
        this.numeroPolo = numeroPolo;
    }

    public List<Zona> getZonas() {
        return zonas;
    }

    public void setZonas(List<Zona> zonas) {
        this.zonas = zonas;
    }

    public Polo getPolo() {
        return polo;
    }

    public void setPolo(Polo polo) {
        this.polo = polo;
    }

}