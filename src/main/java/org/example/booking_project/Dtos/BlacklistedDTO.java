package org.example.booking_project.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlacklistedDTO {
    public int id;
    public String email;
    public String name;
    public String group;
    public Date created;
    public boolean ok;

}
