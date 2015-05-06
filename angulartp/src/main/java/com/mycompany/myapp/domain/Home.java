package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Home.
 */
@Entity
@Table(name = "HOME")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Home implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "aire")
    private Integer aire;

    @Column(name = "ip")
    private String ip;

    @ManyToOne
    private Person person;

    @OneToMany(mappedBy = "home")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Heater> heaterss = new HashSet<>();

    @OneToMany(mappedBy = "home")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Device> devicess = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getAire() {
        return aire;
    }

    public void setAire(Integer aire) {
        this.aire = aire;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Heater> getHeaterss() {
        return heaterss;
    }

    public void setHeaterss(Set<Heater> heaters) {
        this.heaterss = heaters;
    }

    public Set<Device> getDevicess() {
        return devicess;
    }

    public void setDevicess(Set<Device> devices) {
        this.devicess = devices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Home home = (Home) o;

        if ( ! Objects.equals(id, home.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Home{" +
                "id=" + id +
                ", adresse='" + adresse + "'" +
                ", aire='" + aire + "'" +
                ", ip='" + ip + "'" +
                '}';
    }
}
