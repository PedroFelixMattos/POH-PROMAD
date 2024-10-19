package com.example.poh_promad.serviceTest;

import com.example.poh_promad.model.Processo;
import com.example.poh_promad.model.Reu;
import com.example.poh_promad.repository.ProcessoRepository;
import com.example.poh_promad.service.ProcessoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@Service
public class ProcessoServiceTest {

    // Mocks for dependencies
    @MockBean
    private ProcessoRepository processoRepository;
    @MockBean
    private MessageSource messageSource;

    @Autowired
    private ProcessoService processoService;

    // Test data
    private Long existingProcessId = 1L;
    private Processo existingProcesso = new Processo();
    private Reu existingReu = new Reu(existingProcesso);
    private Optional<Reu> optionalReu = Optional.of(existingReu);

    @BeforeEach
    public void setUp() {
        existingReu.setProcesso(existingProcesso);
        Mockito.when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Mensagem de erro"); // Mock message source
    }

    @Test
    public void testFindAll() {
        List<Processo> mockList = Arrays.asList(new Processo(), new Processo());

        // Mock the repository behavior
        Mockito.when(processoRepository.findAll()).thenReturn(mockList);

        // Call the service method
        List<Processo> resultList = processoService.findAll();

        // Assert that the returned list is the same as the mocked list
        assertEquals(mockList, resultList);
    }

    @Test
    public void testFindByIdExistingProcess() {
        // Mock the repository behavior
        Mockito.when(processoRepository.findById(existingProcessId)).thenReturn(Optional.of(existingProcesso));

        // Call the service method
        Optional<Processo> optionalProcesso = processoService.findById(existingProcessId);

        // Assert that the returned Optional contains the expected process
        assertTrue(optionalProcesso.isPresent());
        assertEquals(existingProcesso, optionalProcesso.get());
    }

    @Test
    public void testFindByIdNonExistentProcess() {
        Long nonExistentId = 2L;

        // Mock the repository behavior
        Mockito.when(processoRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Call the service method
        Optional<Processo> optionalProcesso = processoService.findById(nonExistentId);

        // Assert that the returned Optional is empty
        assertFalse(optionalProcesso.isPresent());
    }

    @Test
    public void testSaveExistingProcessWithReu() {
        // Mock the repository behavior for findById
        Mockito.when(processoRepository.findById(existingProcessId)).thenReturn(Optional.of(existingProcesso));

        // Call the service method (expecting exception)
        processoService.save(existingProcessId, optionalReu);
    }

    @Test
    public void testSaveExistingProcessNoReu() {
        // Mock the repository behavior for findById and save
        Mockito.when(processoRepository.findById(existingProcessId)).thenReturn(Optional.of(existingProcesso));
        Mockito.when(processoRepository.save(existingProcesso)).thenReturn(existingProcesso);

        // Call the service method
        existingProcesso.setId(existingProcessId);
        Processo savedProcesso = processoService.save(existingProcesso).getProcesso();

        // Assert that the returned process is the same as the mocked process
        assertEquals(existingProcesso, savedProcesso);
    }

    @Test
    public void testSaveNewProcess() {
        Processo newProcesso = new Processo();

        // Mock the repository behavior
        Mockito.when(processoRepository.save(newProcesso)).thenReturn(newProcesso);

        // Call the service method
        Processo savedProcesso = processoService.save(newProcesso).getProcesso();

        // Assert that the returned process is the same as the mocked process
        assertEquals(newProcesso, savedProcesso);
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;

        // Mock the repository behavior
        processoService.deleteById(id);

        // Verify that the delete method was called on the repository
        Mockito.verify(processoRepository, Mockito.times(1)).deleteById(id);
    }
}