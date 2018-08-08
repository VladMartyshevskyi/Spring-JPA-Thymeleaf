package com.kemal.spring.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Review {
	private String name;
	private String positive;
	private String negative;
	private int usingPeriod;
	
}
