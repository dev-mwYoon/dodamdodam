package com.app.dodamdodam.entity.free;

import com.app.dodamdodam.audit.Period;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_FREE_BOARD_LIKE")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreeReply extends Period {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull private String replyContent;
//    private LocalDateTime replyRegisterDate;
//    private LocalDateTime replyUpdateDate;

}
