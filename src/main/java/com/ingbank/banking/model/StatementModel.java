package com.ingbank.banking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
public class StatementModel {
	private Long customerId;
	private Double totalIncoming;
	private Double totalOutgoing;
	private Double closingBalance;

}
