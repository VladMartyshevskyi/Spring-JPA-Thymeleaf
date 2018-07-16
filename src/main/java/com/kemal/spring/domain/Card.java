package com.kemal.spring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Card {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true)
	@NotNull
    private String number;
	@NotNull
	private Integer cvv;
	@NotNull
	private String bank;
	@NotNull
	private Integer expiresMonth;
	@NotNull
	private Integer expiresYear;
	
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private User user;

	public Card(String number, Integer cvv, String bank, Integer expiresMonth, Integer expiresYear, User user) {
		this.number = number;
		this.cvv = cvv;
		this.bank = bank;
		this.expiresMonth = expiresMonth;
		this.expiresYear = expiresYear;
		this.user = user;
	}
	
}
