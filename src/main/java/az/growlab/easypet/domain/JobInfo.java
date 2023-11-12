package az.growlab.easypet.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class JobInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String company;
    private Integer duration;
    @OneToOne
    @JoinColumn(name = "veterinar_id", referencedColumnName = "id")
    private Veterinary veterinary;
}
