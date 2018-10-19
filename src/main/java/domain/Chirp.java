
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {@Index(columnList = "title, description")})
public class Chirp extends DomainEntity {

    private String title;
    private String description;
    private Date moment;
    // Relationships
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getMoment() {
        return this.moment;
    }

    public void setMoment(final Date moment) {
        this.moment = moment;
    }

    @NotBlank
    @SafeHtml
    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String body) {
        this.description = body;
    }

    @NotBlank
    @SafeHtml
    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @ManyToOne(optional = true)
    @Valid
    public User getUser() {
        return user;
    }

    public void setUser(User route) {
        this.user = route;
    }


}
