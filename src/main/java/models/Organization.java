package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Organization {

    public String id;
    public String name;
    public String displayName;
    public List<Members> memberships;
}
