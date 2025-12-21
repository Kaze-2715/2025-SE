package com.agri.platform.controller.advise;

import com.agri.platform.model.Land;
import com.agri.platform.repository.LandRepository;
import com.agri.platform.service.advice.StorageService;
import com.agri.platform.service.advice.SuggestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LandControllerTest {

    @Mock
    private LandRepository landRepository;

    @Mock
    private SuggestionService suggestionService;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private LandController landController;

    private Land testLand;

    @BeforeEach
    void setUp() {
        testLand = new Land();
        testLand.setId(1L);
        testLand.setLandId("L001");
        testLand.setArea(100.5);
        testLand.setSoilType("Loam");
        testLand.setAttachmentPath("uploads/land-attachment.pdf");
    }

    @Test
    void testAll() {
        List<Land> expectedLands = Arrays.asList(testLand);
        when(landRepository.findAll()).thenReturn(expectedLands);

        List<Land> result = landController.all();

        assertEquals(expectedLands, result);
        verify(landRepository, times(1)).findAll();
    }

    @Test
    void testCreateWithoutAttachment() throws IOException {
        when(landRepository.save(any(Land.class))).thenReturn(testLand);

        ResponseEntity<Land> response = landController.create(
                "L001",
                100.5,
                "Loam",
                null
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testLand, response.getBody());
        verify(landRepository, times(1)).save(any(Land.class));
        verify(storageService, never()).store(any(MultipartFile.class));
    }

    @Test
    void testCreateWithAttachment() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile(
                "attachment",
                "test.pdf",
                "application/pdf",
                "Test PDF content".getBytes()
        );

        when(storageService.store(mockFile)).thenReturn("uploads/test.pdf");
        when(landRepository.save(any(Land.class))).thenReturn(testLand);

        ResponseEntity<Land> response = landController.create(
                "L001",
                100.5,
                "Loam",
                mockFile
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testLand, response.getBody());
        verify(storageService, times(1)).store(mockFile);
        verify(landRepository, times(1)).save(any(Land.class));
    }

    @Test
    void testUpdate() {
        when(landRepository.findById(1L)).thenReturn(Optional.of(testLand));
        when(landRepository.save(any(Land.class))).thenReturn(testLand);

        ResponseEntity<Land> response = landController.update(
                1L,
                "L002",
                200.5,
                "Clay"
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testLand, response.getBody());
        verify(landRepository, times(1)).findById(1L);
        verify(landRepository, times(1)).save(any(Land.class));
    }

    @Test
    void testUpdateNotFound() {
        when(landRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Land> response = landController.update(
                1L,
                "L002",
                200.5,
                "Clay"
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(landRepository, times(1)).findById(1L);
        verify(landRepository, never()).save(any(Land.class));
    }

    @Test
    void testGet() {
        when(landRepository.findById(1L)).thenReturn(Optional.of(testLand));

        ResponseEntity<Land> response = landController.get(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testLand, response.getBody());
        verify(landRepository, times(1)).findById(1L);
    }

    @Test
    void testGetNotFound() {
        when(landRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Land> response = landController.get(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(landRepository, times(1)).findById(1L);
    }

    @Test
    void testDelete() {
        doNothing().when(landRepository).deleteById(1L);

        ResponseEntity<Void> response = landController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(landRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSuggestion() {
        String expectedSuggestion = "建议：在这片100.5平方米的壤土上种植小麦。";
        when(landRepository.findById(1L)).thenReturn(Optional.of(testLand));
        when(suggestionService.generateSuggestion(
                testLand.getLandId(),
                testLand.getArea(),
                testLand.getSoilType()
        )).thenReturn(expectedSuggestion);

        ResponseEntity<String> response = landController.suggestion(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedSuggestion, response.getBody());
        verify(landRepository, times(1)).findById(1L);
        verify(suggestionService, times(1)).generateSuggestion(
                testLand.getLandId(),
                testLand.getArea(),
                testLand.getSoilType()
        );
    }

    @Test
    void testSuggestionNotFound() {
        when(landRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = landController.suggestion(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(landRepository, times(1)).findById(1L);
        verify(suggestionService, never()).generateSuggestion(anyString(), anyDouble(), anyString());
    }
}