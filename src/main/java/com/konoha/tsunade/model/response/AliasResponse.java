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
public class AliasResponse implements Serializable {

	private static final long serialVersionUID = -3831423668412279124L;
	private String username;
	private String profilePicture;
}
