package com.example.poh_promad.service;

import com.example.poh_promad.model.Processo;

public class ProcessoResponse{
    public ProcessoResponse(Processo processo, String mensagemErro) {
        this.processo = processo;
        this.mensagemErro = mensagemErro;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    private Processo processo;
    private String mensagemErro;

}
