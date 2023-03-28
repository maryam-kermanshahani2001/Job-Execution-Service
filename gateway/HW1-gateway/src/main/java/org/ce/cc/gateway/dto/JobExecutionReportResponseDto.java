package org.ce.cc.gateway.dto;

import org.ce.cc.core.entity.Result;

import java.util.List;

/**
 * @author Maryam Kermanshahani
 * @since 07.03.23
 */
public record JobExecutionReportResponseDto(List<Result> results) {
}
