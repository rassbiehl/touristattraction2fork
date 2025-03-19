import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import touristattraction2fork.model.TouristAttraction;
import touristattraction2fork.repository.TouristAttractionRepository;
import touristattraction2fork.service.TouristAttractionService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class) // Enable Mockito support
class TouristAttractionServiceTest {

    @Mock
    private TouristAttractionRepository repository; // Mocked repository (no real DB)

    @InjectMocks
    private TouristAttractionService service; // Service using the mocked repository

    private TouristAttraction tivoli;

    @BeforeEach
    void setUp() {
        tivoli = new TouristAttraction(3, "Tivoli", "Forlystelsespark", "København", null);
    }


    @Test
    void deleteTouristAttraction_Success() {
        // Arrange: Create a mock attraction
        TouristAttraction tivoliAttraction = new TouristAttraction(1, "Tivoli", "Forlystelsespark", "København", null);

        // Mock repository behavior
        Mockito.when(repository.findAttractionByName("Tivoli")).thenReturn(tivoliAttraction);
        Mockito.when(repository.deleteAttraction(1)).thenReturn(true);

        // Act: Find and delete attraction
        TouristAttraction attraction = service.findAttractionByName("Tivoli");
        assertNotNull(attraction, "Tivoli should have been added");

        service.deleteAttraction(attraction.getId());

        // Assert: Verify the repository delete method was called once
        verify(repository, times(1)).deleteAttraction(tivoliAttraction.getId());

        // Ensure the attraction is no longer found
        Mockito.when(repository.findAttractionByName("Tivoli")).thenReturn(null);
        assertNull(service.findAttractionByName("Tivoli"), "Tivoli should no longer exist");
    }

    @Test
    void deleteTouristAttraction_NotFound() {
        TouristAttraction attraction = service.findAttractionByName("NonExisting");
        assertNull(attraction, "The attraction shouldn't exist");
    }

    @Test
    void testFindAll() {
        // Arrange: Create a mock attraction
        List<TouristAttraction> attractions = new ArrayList<>(Arrays.asList(
                new TouristAttraction(1, "Rundetårn", "Stort tårn med flot udsigt", "København", null),
                new TouristAttraction(2, "Tivoli", "Historisk forlystelsespark", "København", null),
                new TouristAttraction(3, "Den Lille Havfrue", "Ikonisk statue af en havfrue", "København", null),
                new TouristAttraction(4, "Legoland", "Forlystelsespark med Lego-tema", "Billund", null),
                new TouristAttraction(5, "Aros", "Kunstmuseum med den berømte regnbue", "Aarhus", null)
        ));

        // Mock behavior
        Mockito.when(repository.findAll()).thenReturn(attractions);

        // Act: Find all attractions
        List<TouristAttraction> result = service.findAll();

        //assert
        assertEquals(5, result.size());
        assertEquals("Rundetårn", result.get(0).getName());

        //verify
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    void getAvailableTags() {
        // Arrange: Create a mock attraction
        Map<String, Integer> tags = new HashMap<>() {{
            put("CHILD_FRIENDLY", 1);
            put("HISTORY", 2);
            put("MUSEUM", 3);
            put("NATURE", 4);
            put("FREE", 5);
        }};

        // Arrange: Create a mock attraction
        Mockito.when(repository.getAvailableTags()).thenReturn(tags);

        // Act: Find all attractions
        Map<String, Integer> result = service.getAvailableTags();

        // assert
        assertEquals(tags, result);
        assertEquals(5, tags.size());

        //verify
        Mockito.verify(repository, Mockito.times(1)).getAvailableTags();

    }

    @Test
    void testFindAttractionByName() {
        // Arrange: Create a mock attraction
        List<TouristAttraction> attractions = new ArrayList<>(Arrays.asList(
                new TouristAttraction(1, "Rundetårn", "Stort tårn med flot udsigt", "København", null),
                new TouristAttraction(2, "Tivoli", "Historisk forlystelsespark", "København", null),
                new TouristAttraction(3, "Den Lille Havfrue", "Ikonisk statue af en havfrue", "København", null),
                new TouristAttraction(4, "Legoland", "Forlystelsespark med Lego-tema", "Billund", null),
                new TouristAttraction(5, "Aros", "Kunstmuseum med den berømte regnbue", "Aarhus", null)
        ));

        // Mock behavior
        Mockito.when(repository.findAttractionByName("Tivoli")).thenReturn(attractions.get(1));

        // Act:
        TouristAttraction attraction = service.findAttractionByName("Tivoli");

        //assert
        assertEquals(attraction, attractions.get(1));

        //verify
        Mockito.verify(repository, Mockito.times(1)).findAttractionByName("Tivoli");
    }

    @Test
    void testAddTouristAttraction() {
        // Arrange
        List<TouristAttraction> attractions = new ArrayList<>(Arrays.asList(
                new TouristAttraction(1, "Rundetårn", "Stort tårn med flot udsigt", "København", null),
                new TouristAttraction(2, "Tivoli", "Historisk forlystelsespark", "København", null),
                new TouristAttraction(4, "Legoland", "Forlystelsespark med Lego-tema", "Billund", null),
                new TouristAttraction(5, "Aros", "Kunstmuseum med den berømte regnbue", "Aarhus", null)
        ));

        TouristAttraction touristAttraction = new TouristAttraction(3, "Den Lille Havfrue", "Ikonisk statue af en havfrue", "København", null);

        // Tell Mockito what to do when createAttraction is called
        doAnswer(call -> {
            attractions.add(touristAttraction); // Add the attraction to the list
            return null; // Because it's a void method
        }).when(repository).createAttraction(any(TouristAttraction.class));

        // Act: Call the service method
        service.addTouristAttractionToList(touristAttraction);

        // Assert: Check if it was added
        assertEquals(5, attractions.size());
        assertEquals("Den Lille Havfrue", attractions.get(4).getName());

        // Verify that createAttraction was called once
        verify(repository, times(1)).createAttraction(any(TouristAttraction.class));
    }

    @Test
    void testUpdateTouristAttraction() {
        TouristAttraction attraction = new TouristAttraction(1, "Rundetårn", "Updated description", "København", null);

        doNothing().when(repository).updateAttraction(any(TouristAttraction.class));

        assertDoesNotThrow(() -> service.updateTouristAttraction(attraction));

        verify(repository, times(1)).updateAttraction(attraction);
    }

    // Arrange: Create a mock attraction
    // Mock behavior
    // Act: Find all attractions
    //assert
    //verify



}