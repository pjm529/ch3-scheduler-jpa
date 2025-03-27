package com.sparta.common.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum SystemValues {

	LOGIN_USER("loginUser"),
	;

	private final String value;
}
