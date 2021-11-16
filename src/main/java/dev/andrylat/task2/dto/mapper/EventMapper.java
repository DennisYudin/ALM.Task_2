package dev.andrylat.task2.dto.mapper;

import dev.andrylat.task2.dto.EventDTO;
import dev.andrylat.task2.entities.Event;
import dev.andrylat.task2.entities.Location;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventMapper {

    public EventDTO convertToDTO(Event event, List<String> categories, Location location) {

        return new EventDTO.Builder()
                .id(event.getId())
                .name(event.getTitle())
                .date(event.getDate())
                .price(event.getPrice())
                .eventStatus(event.getStatus())
                .eventDescription(event.getDescription())
                .eventCategories(categories)
                .locationName(location.getTitle())
                .locationWorkingHours(location.getWorkingHours())
                .locationType(location.getType())
                .locationAddress(location.getAddress())
                .locationDescription(location.getDescription())
                .capacityPeople(location.getCapacityPeople())
                .build();
    }
}

