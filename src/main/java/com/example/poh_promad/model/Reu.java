package com.example.poh_promad.model;


import jakarta.persistence.*;

@Entity
@Table(name = "reu")
public class Reu {

    @Id
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "processo_id",nullable = false)
    private Processo processo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public Reu(Processo processo) {
        this.processo = processo;
    }

}
