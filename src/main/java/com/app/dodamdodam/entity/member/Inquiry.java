package com.app.dodamdodam.entity.member;

import com.app.dodamdodam.audit.Period;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_INQUIRY")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry extends Period {
    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @Enumerated(EnumType.STRING)
    @NotNull private InquiryType inquiryType;
    @NotNull private String inquiryEmail;
    @NotNull private String memberIdentification;
    @NotNull private String inquiryPhoneNumber;
    @NotNull private String inquiryContent;
    @NotNull private String inquiryAnswer;

    //status 필요한가요?
}
