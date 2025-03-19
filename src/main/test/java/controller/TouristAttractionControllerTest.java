package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import touristattraction2fork.controller.TouristAttractionController;
import touristattraction2fork.model.Tag;
import touristattraction2fork.model.TouristAttraction;
import touristattraction2fork.service.TouristAttractionService;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(MockitoExtension.class)
class TouristAttractionControllerTest {


        private MockMvc mockMvc;

        @Mock
        private TouristAttractionService touristAttractionService;

        @InjectMocks
        private TouristAttractionController touristAttractionController;

        @BeforeEach
        void setup() {
            mockMvc = MockMvcBuilders.standaloneSetup(touristAttractionController).build();
        }

        @Test
        void testGetAttractions_withSearch() throws Exception {
            // Create test attractions
            TouristAttraction attraction1 = new TouristAttraction();
            attraction1.setName("Bakken");

            TouristAttraction attraction2 = new TouristAttraction();
            attraction2.setName("Tivoli");

            List<TouristAttraction> allAttractions = Arrays.asList(attraction1, attraction2);
            List<TouristAttraction> filteredAttractions = List.of(attraction2); // Expected result when searching "Tivoli"

            // Mock service methods
            when(touristAttractionService.findAll()).thenReturn(allAttractions);

            // Perform GET request with search query "?search=Tivoli"
            mockMvc.perform(get("/attractions").param("search", "Tivoli"))
                    .andExpect(status().isOk()) // Expect HTTP 200 OK
                    .andExpect(view().name("attractionList")) // Expect the correct view
                    .andExpect(model().attributeExists("attractions")) // Check if model has attractions
                    .andExpect(model().attribute("attractions", filteredAttractions)); // Ensure only Tivoli is returned

            // Verify that findAll() is called once
            verify(touristAttractionService, times(1)).findAll();
        }


        @Test
        void testGetTags() throws Exception {
            // Arrange - Create a test attraction with tags
            String attractionName = "Bakken";
            TouristAttraction attraction = new TouristAttraction();
            attraction.setName(attractionName);
            attraction.setTags(List.of(new Tag(1, "GRATIS"), new Tag(2, "CHILD_FRIENDLY"))); // Ensure tags exist

            // Mock the service method to return the attraction
            when(touristAttractionService.findAttractionByName(attractionName)).thenReturn(attraction);

            // Act - Perform GET request and validate response
            mockMvc.perform(get("/attractions/{name}/tags", attractionName)) // Removed extra argument
                    .andExpect(status().isOk()) // Ensure HTTP 200 OK
                    .andExpect(view().name("tagsPage"))
                    .andExpect(model().attributeExists("viewAttraction")) // Ensure model contains the attribute
                    .andExpect(model().attribute("viewAttraction", attraction)); // Check that the object is correctly passed

            // Verify that the service method was called exactly once
            verify(touristAttractionService, times(1)).findAttractionByName(attractionName);
        }



        @Test
        public void testEditAttraction() throws Exception{
            Map<String, Integer> mockTags = Map.of("GRATIS", 1, "CHILD_FRIENDLY", 2);

            TouristAttraction touristAttraction = new TouristAttraction("Bakken", "Amusement Park",
                    "CPH", List.of(new Tag(1, "GRATIS"), new Tag(2, "CHILD_FRIENDLY")));

            TouristAttraction spyAttraction = Mockito.spy(touristAttraction);

            when(touristAttractionService.findAttractionByName("Bakken")).thenReturn(spyAttraction);
            when(touristAttractionService.getAvailableTags()).thenReturn(mockTags);
            doReturn(List.of("GRATIS", "CHILD_FRIENDLY")).when(spyAttraction).convertTagsToStringList();

            mockMvc.perform(get("/attractions/{name}/edit", touristAttraction.getName()))
                    .andExpect(status().isOk())
                    .andExpect(view().name("updateAttraction"))
                    .andExpect(model().attributeExists("attraction"))
                    .andExpect(model().attributeExists("availableTags"))
                    .andExpect(model().attributeExists("attractionTags"))
                    .andExpect(model().attribute("attraction", hasProperty("name", equalTo("Bakken"))))
                    .andExpect(model().attribute("attraction", hasProperty("description", equalTo("Amusement Park"))))
                    .andExpect(model().attribute("attraction", hasProperty("city", equalTo("CPH"))))
                    .andExpect(model().attribute("availableTags", containsInAnyOrder("GRATIS", "CHILD_FRIENDLY")))
                    .andExpect(model().attribute("attractionTags", containsInAnyOrder("GRATIS", "CHILD_FRIENDLY")));

            verify(touristAttractionService, times(1)).findAttractionByName("Bakken");
            verify(touristAttractionService, times(1)).getAvailableTags();

        }

        @Test
        public void testUpdateAttraction() throws Exception {
            TouristAttraction touristAttraction = new TouristAttraction();
            touristAttraction.setName("Bakken");
            touristAttraction.setDescription("Amusement park in Copenhagen");
            touristAttraction.setCity("CPH");
            touristAttraction.setTags(new ArrayList<>());

            Map<String, Integer> availableTags = new HashMap<>();
            availableTags.put("GRATIS", 1);
            availableTags.put("CHILD_FRIENDLY", 2);

            when(touristAttractionService.findAttractionByName("Bakken")).thenReturn(touristAttraction);
            when(touristAttractionService.getAvailableTags()).thenReturn(availableTags);

            mockMvc.perform(post("/attractions/update")
                            .param("name", "Bakken")
                            .param("description", "new description")
                            .param("selectedTags", "GRATIS", "CHILD_FRIENDLY"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/attractions#attractions"));

            verify(touristAttractionService, times(1)).findAttractionByName("Bakken");
            verify(touristAttractionService, times(1)).updateTouristAttraction(any());
        }

        @Test
        public void testSaveAttraction() throws Exception {
            mockMvc.perform(post("/attractions/save")
                            .param("selectedTags", "GRAITS", "CHILD_FRIENDLY")
                            .flashAttr("touristAttraction", new TouristAttraction("Bakken", "Amustment Park", "CPH")))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/attractions#attractions"));

            verify(touristAttractionService, times(1)).addTouristAttractionToList(any());
        }

        @Test
        public void testDeleteAttraction() throws Exception {
            String attractionName = "Bakken";
            TouristAttraction touristAttraction = new TouristAttraction(attractionName, "Amusement Park", "CPH");
            touristAttraction.setId(1);

            when(touristAttractionService.findAttractionByName(attractionName)).thenReturn(touristAttraction);
            doNothing().when(touristAttractionService).deleteAttraction(touristAttraction.getId());

            mockMvc.perform(post("/attractions/delete/{name}", attractionName))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/attractions#attractions"));

            verify(touristAttractionService, times(1)).findAttractionByName(attractionName);
            verify(touristAttractionService, times(1)).deleteAttraction(1);
        }

        @Test
        public void testAddAttractionPage() throws Exception {
            Map<String, Integer> mockTags = new HashMap<>();
            mockTags.put("GRATIS", 1);
            mockTags.put("CHILD_FRIENDLY", 2);

            when(touristAttractionService.getAvailableTags()).thenReturn(mockTags);

            mockMvc.perform(get("/attractions/add"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("addAttraction"))
                    .andExpect(model().attributeExists("tags"))
                    .andExpect(model().attributeExists("touristAttraction"))
                    .andExpect(model().attribute("tags", new ArrayList<>(mockTags.keySet())));

            verify(touristAttractionService, times(1)).getAvailableTags();
        }
    }
