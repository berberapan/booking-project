package org.example.booking_project.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RoomClosed.class, name = "RoomClosed"),
        @JsonSubTypes.Type(value = RoomCleaningFinished.class, name = "RoomCleaningFinished"),
        @JsonSubTypes.Type(value = RoomCleaningStarted.class, name = "RoomCleaningStarted"),
        @JsonSubTypes.Type(value = RoomOpened.class, name = "RoomOpened")
})


@Data
@Entity(name = "EventBase")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class EventBase {
    @Id
    @GeneratedValue
    public Long id;

    @NotEmpty(message = "Type är obligatoriskt")
    @Size(min = 4,message = "Type måste vara minst 3 tecken")
    @Pattern(regexp = "^[A-Öa-ö]*$", message = "Type får endast innehålla bokstäver")
    public String type;

    @NotNull(message = "Timestamp är obligatoriskt")
    @PastOrPresent(message = "Timestamp får inte vara i framtiden")
    public LocalDateTime TimeStamp;
}

