package com.konoha.tsunade.model.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemParameterResponse implements Serializable {

	private static final long serialVersionUID = 6021222221200670391L;
	private String variable;
	private String value;
}
