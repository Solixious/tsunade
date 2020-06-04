package com.konoha.tsunade.model.response;

import java.io.Serializable;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetailResponse implements Serializable {

	private static final long serialVersionUID = -137743848033232584L;
	private String title;
	private String description;
	private String path;
	private AliasResponse author;
	private String created;
	private Page<PostResponse> posts;
}
