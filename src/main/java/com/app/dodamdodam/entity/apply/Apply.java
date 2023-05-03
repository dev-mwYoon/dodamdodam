package com.app.dodamdodam.entity.apply;

import com.app.dodamdodam.audit.Period;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@ToString
@Table(name = "TBL_APPLY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Apply extends Period {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    private String applyStatus;

}
