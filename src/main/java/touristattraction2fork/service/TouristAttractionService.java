package touristattraction2fork.service;

import touristattraction2fork.model.TouristAttraction;
import touristattraction2fork.repository.TouristAttractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TouristAttractionService {

    private final TouristAttractionRepository touristAttractionRepository;

    @Autowired
    public TouristAttractionService(TouristAttractionRepository touristAttractionRepository) {
        this.touristAttractionRepository = touristAttractionRepository;
    }

    public List<TouristAttraction> findAll() {
        return touristAttractionRepository.findAll();
    }

    public Map<String, Integer> getAvailableTags() {
        return touristAttractionRepository.getAvailableTags();
    }

    public List<TouristAttraction> getFirstAttractions() {
        return touristAttractionRepository.getFirstAttractions();
    }

    public void addTouristAttractionToList(TouristAttraction touristAttraction) {
        try {
            touristAttractionRepository.createAttraction(touristAttraction);
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Could not add tourist attraction: " + e.getMessage(), e);
        }
    }

    public void deleteAttraction(int id) {
        touristAttractionRepository.deleteAttraction(id);
    }

    public TouristAttraction findAttractionByName(String name) {
        return touristAttractionRepository.findAttractionByName(name);
    }

    public void updateTouristAttraction(TouristAttraction touristAttraction) {
        try {
            touristAttractionRepository.updateAttraction(touristAttraction);
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Could not update tourist attraction: " + e.getMessage(), e);
        }
    }
}