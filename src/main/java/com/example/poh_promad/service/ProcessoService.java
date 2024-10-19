package com.example.poh_promad.service;

import com.example.poh_promad.model.Processo;
import com.example.poh_promad.model.Reu;
import com.example.poh_promad.repository.ProcessoRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProcessoService {

    private final ProcessoRepository processoRepository;

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public ProcessoRepository getProcessoRepository() {
        return processoRepository;
    }

    private final MessageSource messageSource;

    public ProcessoService(MessageSource messageSource, ProcessoRepository processoRepository) {
        this.messageSource = messageSource;
        this.processoRepository = processoRepository;
    }

    public boolean isExisteProcesso() {
        return existeProcesso;
    }

    public void setExisteProcesso(boolean existeProcesso) {
        this.existeProcesso = existeProcesso;
    }

    private boolean existeProcesso = false;

    public List<Processo> findAll(){
        return processoRepository.findAll();
    }

    public Optional<Processo> findById(Long id){
        Optional<Processo> repositorio = processoRepository.findById(id);
        this.setExisteProcesso(true);
        return repositorio;
    }

    public Processo save(Long id, Optional<Reu> reuOpcional){
        Optional<Processo> processoExistente = processoRepository.findById(id);
        if (processoExistente.isPresent()){
            Processo processo = processoExistente.get();
            //Processo e Reu EXISTEM
            if (reuOpcional.isPresent()) {
                Reu reu = reuOpcional.get();
                //Se Reu já cadastrado, deve apresentar erro. Processo já existe
                boolean aux = reu.getProcesso() == processo;
                if (aux) {
                    throw new ReuJaAssociadoException(messageSource.getMessage("Numero de Processo já existente", null, LocaleContextHolder.getLocale()));
                }
                //Se Reu não cadastrado, adiciona-lo ao processo
                Reu novoReu = new Reu(processo);
                processo.getReus().add(novoReu);
                return processo;
            //processo EXISTE, E REU NÃO. Adiciona-lo ao processo
            }else {
                Reu novoReu = new Reu(processo);
                processo.getReus().add(novoReu);
                return processo;
            }
        }else{
            //Reu existe e processo não
            if (reuOpcional.isPresent()) {
                Reu reu = reuOpcional.get();
                Processo processo = new Processo();
                processo.getReus().add(reu);
                Processo processoSalvo = processoRepository.save(processo);
                return processoSalvo;
            //Não existe Reu ou Processo
            }else{
                Processo processo = new Processo();
                Processo processoSalvo = processoRepository.save(processo);
                return processoSalvo;
            }
        }
    }



    public ProcessoResponse save(Processo processo){
        var aux = findById(processo.getId());
        if (this.isExisteProcesso()){
            this.setExisteProcesso(false);
            String mensagemErro = messageSource.getMessage("Numero de Processo já existente", null, LocaleContextHolder.getLocale());
            return new ProcessoResponse(null, mensagemErro);
        }else{
            this.setExisteProcesso(false);
            Processo processoSalvo = processoRepository.save(processo);
            return new ProcessoResponse(processoSalvo, null);
        }
    }

    public void deleteById(Long id){
        processoRepository.deleteById(id);
    }
}
