package io.microsamples.db.dbchachkies;


import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Date;

@AllArgsConstructor
@Value
public class Chachkie {
    Integer id;
    String name;
    Date when;
}
