/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.antoniodominguez.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author anton
 */
@Entity
@Table(name = "GRUPODISENNO")
@NamedQueries({
    @NamedQuery(name = "Grupodisenno.findAll", query = "SELECT g FROM Grupodisenno g"),
    @NamedQuery(name = "Grupodisenno.findById", query = "SELECT g FROM Grupodisenno g WHERE g.id = :id"),
    @NamedQuery(name = "Grupodisenno.findByEstudio", query = "SELECT g FROM Grupodisenno g WHERE g.estudio = :estudio"),
    @NamedQuery(name = "Grupodisenno.findByEmpresa", query = "SELECT g FROM Grupodisenno g WHERE g.empresa = :empresa")})
public class Grupodisenno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "ESTUDIO")
    private String estudio;
    @Column(name = "EMPRESA")
    private String empresa;
    @OneToMany(mappedBy = "grupo")
    private Collection<Disennador> disennadorCollection;

    public Grupodisenno() {
    }

    public Grupodisenno(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Collection<Disennador> getDisennadorCollection() {
        return disennadorCollection;
    }

    public void setDisennadorCollection(Collection<Disennador> disennadorCollection) {
        this.disennadorCollection = disennadorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupodisenno)) {
            return false;
        }
        Grupodisenno other = (Grupodisenno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.antoniodominguez.entidades.Grupodisenno[ id=" + id + " ]";
    }
    
}
