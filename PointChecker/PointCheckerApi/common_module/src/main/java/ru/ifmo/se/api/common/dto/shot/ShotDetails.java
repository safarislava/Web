package ru.ifmo.se.api.common.dto.shot;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RevolverDetails.class, name = "Revolver"),
        @JsonSubTypes.Type(value = ShotgunDetails.class, name = "Shotgun")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShotDetails {
    private String type;
}
