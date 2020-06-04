package com.konoha.tsunade.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {

	private String title;
	private String description;
	private String path;
	private String created;
	private AliasResponse author;
}
