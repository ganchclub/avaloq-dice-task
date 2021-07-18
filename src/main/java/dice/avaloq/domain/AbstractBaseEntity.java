package dice.avaloq.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    public void setTimestamp() {
        createdAt = new Date();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}