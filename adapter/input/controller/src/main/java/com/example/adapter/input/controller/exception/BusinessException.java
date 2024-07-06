package com.example.adapter.input.controller.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -5241554286656150984L;

	@JsonIgnore
	private final HttpStatus httpStatusCode;
	private final String timestamp;
	private final Integer status;
	private final String error;
	private final String message;
	private final String path;

	public BusinessExceptionBody getOnlyBody() {
		return BusinessExceptionBody.builder()
				.timestamp(this.timestamp)
				.status(this.status)
				.error(this.error)
				.message(this.message)
				.path(this.path)
				.build();
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class BusinessExceptionBody {
		private String timestamp;
		private Integer status;
		private String error;
		private String message;
		private String path;
	}
}
