package com.ingbank.banking.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.model.CustomerRequestModel;


@Component
public class ApplicationValidation {



	public static void validateRegisterCustomer(CustomerRequestModel customerRequestModel) throws ApplicationException {
		
		
		
		if (!StringUtils.hasText(customerRequestModel.getPanNumber())||!StringUtils.hasText(customerRequestModel.getFirstName())||!StringUtils.hasText(customerRequestModel.getEmail()))
				
			throw new ApplicationException("Please fill Mandatory Fields");

		
			
		}
	}
	
	


