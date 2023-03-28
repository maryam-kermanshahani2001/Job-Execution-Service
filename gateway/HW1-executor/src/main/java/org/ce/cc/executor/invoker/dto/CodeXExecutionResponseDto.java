package org.ce.cc.executor.invoker.dto;

/**
 * @author Parham Ahmady
 * @since 3/20/2023
 */
public record CodeXExecutionResponseDto(long timeStamp, int status, String output, String error, String language,
                                        String info) {
}
