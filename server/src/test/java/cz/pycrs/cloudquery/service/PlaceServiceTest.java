package cz.pycrs.cloudquery.service;

import cz.pycrs.cloudquery.dto.PlacePatchRequest;
import cz.pycrs.cloudquery.entity.Place;
import cz.pycrs.cloudquery.repository.PlaceRepository;
import cz.pycrs.cloudquery.service.impl.PlaceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

    @Mock
    PlaceRepository placeRepository;

    @InjectMocks
    PlaceServiceImpl placeService;

    Place samplePlace;

    @BeforeEach
    void setUp() {
        samplePlace = Place.builder()
                .id(1)
                .cityName("Liberec")
                .countryCode("CZ")
                .zoneOffset(ZoneOffset.ofHours(2))
                .comment("Test")
                .build();
    }

    @Test
    void testGetOrCreatePlace_createsNewPlace() {
        when(placeRepository.save(any(Place.class))).thenReturn(samplePlace);

        Place result = placeService.getOrCreatePlace(1, "Liberec", "CZ", ZoneOffset.ofHours(2), "Test");

        assertThat(result).isEqualTo(samplePlace);
        verify(placeRepository).save(any(Place.class));
    }

    @Test
    void testGetPlace_found() {
        when(placeRepository.findById(1)).thenReturn(Optional.of(samplePlace));

        Place result = placeService.getPlace(1);

        assertThat(result).isEqualTo(samplePlace);
        verify(placeRepository).findById(1);
    }

    @Test
    void testGetPlace_notFound() {
        when(placeRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> placeService.getPlace(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Place not found");
    }

    @Test
    void testDeletePlace() {
        placeService.deletePlace(1);

        verify(placeRepository).deleteById(1);
    }

    @Test
    void testUpdatePlace_updatesAllFields() {
        PlacePatchRequest patch = new PlacePatchRequest("Prague", "CZ", ZoneOffset.ofHours(1), "Updated");

        when(placeRepository.findById(1)).thenReturn(Optional.of(samplePlace));
        when(placeRepository.save(any(Place.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Place result = placeService.updatePlace(1, patch);

        assertThat(result.getCityName()).isEqualTo("Prague");
        assertThat(result.getZoneOffset()).isEqualTo(ZoneOffset.ofHours(1));
        assertThat(result.getComment()).isEqualTo("Updated");

        verify(placeRepository).save(samplePlace);
    }

    @Test
    void testGetPlaces_returnsPagedResult() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Place> page = new PageImpl<>(List.of(samplePlace));

        when(placeRepository.findAll(pageable)).thenReturn(page);

        Page<Place> result = placeService.getPlaces(pageable);

        assertThat(result.getContent()).containsExactly(samplePlace);
        verify(placeRepository).findAll(pageable);
    }
}