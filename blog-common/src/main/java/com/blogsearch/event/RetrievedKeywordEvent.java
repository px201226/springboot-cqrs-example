package com.blogsearch.event;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class RetrievedKeywordEvent {

	private String keyword;

}
