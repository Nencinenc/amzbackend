package amzx.app.data.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity
{

  @Id
  @GeneratedValue(generator = "uuid-string")
  @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", nullable = false, unique = true, updatable = false)
  private String id;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_created", nullable = false, updatable = false)
  private Date dateCreated;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_updated", nullable = false)
  private Date dateUpdated;
}
