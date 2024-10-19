package com.example.poh_promad.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "processo")
public class Processo {
    @Id
    @Column(nullable = false)
    private Long id;

    @OneToMany(mappedBy = "processo")
    private List<Reu> reus = new ArrayList<>();

    public List<Reu> getReus() {
        return reus;
    }

    public void setReus(List<Reu> reus) {
        this.reus = reus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
