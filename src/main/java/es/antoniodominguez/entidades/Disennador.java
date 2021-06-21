/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.antoniodominguez.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author anton
 */
@Entity
@Table(name = "DISENNADOR")
@NamedQueries({
    @NamedQuery(name = "Disennador.findAll", query = "SELECT d FROM Disennador d"),
    @NamedQuery(name = "Disennador.findById", query = "SELECT d FROM Disennador d WHERE d.id = :id"),
    @NamedQuery(name = "Disennador.findByDni", query = "SELECT d FROM Disennador d WHERE d.dni = :dni"),
    @NamedQuery(name = "Disennador.findByNombre", query = "SELECT d FROM Disennador d WHERE d.nombre = :nombre"),
    @NamedQuery(name = "Disennador.findByApellidos", query = "SELECT d FROM Disennador d WHERE d.apellidos = :apellidos"),
    @NamedQuery(name = "Disennador.findByTelefono", query = "SELECT d FROM Disennador d WHERE d.telefono = :telefono"),
    @NamedQuery(name = "Disennador.findByEmail", query = "SELECT d FROM Disennador d WHERE d.email = :email"),
    @NamedQuery(name = "Disennador.findByProyectos", query = "SELECT d FROM Disennador d WHERE d.proyectos = :proyectos"),
    @NamedQuery(name = "Disennador.findByFechaNac", query = "SELECT d FROM Disennador d WHERE d.fechaNac = :fechaNac"),
    @NamedQuery(name = "Disennador.findByFoto", query = "SELECT d FROM Disennador d WHERE d.foto = :foto"),
    @NamedQuery(name = "Disennador.findByLider", query = "SELECT d FROM Disennador d WHERE d.lider = :lider")})

public class Disennador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DNI")
    private String dni;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PROYECTOS")
    private Short proyectos;
    @Column(name = "FECHA_NAC")
    @Temporal(TemporalType.DATE)
    private Date fechaNac;
    @Column(name = "FOTO")
    private String foto;
    @Column(name = "LIDER")
    private Boolean lider;
    @JoinColumn(name = "GRUPO", referencedColumnName = "ID")
    @ManyToOne
    private Grupodisenno grupo;

    public Disennador() {
    }

    public Disennador(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Short getProyectos() {
        return proyectos;
    }

    public void setProyectos(Short proyectos) {
        this.proyectos = proyectos;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Boolean getLider() {
        return lider;
    }

    public void setLider(Boolean lider) {
        this.lider = lider;
    }

    public Grupodisenno getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupodisenno grupo) {
        this.grupo = grupo;
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
        if (!(object instanceof Disennador)) {
            return false;
        }
        Disennador other = (Disennador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.antoniodominguez.entidades.Disennador[ id=" + id + " ]";
    }
    
}
