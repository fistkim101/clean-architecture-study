package com.fistkim.saving.adapter.out.persistence.account;

import com.fistkim.saving.type.AccountType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
public class AccountJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id", nullable = false)
    @Comment(value = "예금주ID")
    private Long ownerId;

    @Column(name = "account_type", nullable = false)
    @Comment(value = "통장 유형")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "balance", nullable = false)
    @Comment(value = "잔액")
    private int balance;

    @Column(name = "created_at", nullable = false)
    @Comment(value = "생성일시")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @Comment(value = "수정일시")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void changeBalance(int balance) {
        this.balance = balance;
    }

}
