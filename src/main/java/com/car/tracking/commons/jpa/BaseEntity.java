package com.car.tracking.commons.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Nyabinde Nyasha
 * @created 3/25/2021
 * @project todo-app
 */

@MappedSuperclass
@Data
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreationTimestamp
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(
            timezone = "Africa/Harare",
            locale = "en_ZW",
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date createdDate;

    @UpdateTimestamp
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(
            timezone = "Africa/Harare",
            locale = "en_ZW",
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date lastModifiedDate;

}
