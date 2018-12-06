package com.soapwebservices.soapcoursemanagement.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

//@SoapFault(faultCode = FaultCode.CLIENT) Either client or custom can be used
@SoapFault(faultCode = FaultCode.CUSTOM, customFaultCode = "{http://soapwebservices.com/courses}001_COURSE_NOT_FOUND")
public class CourseNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -8000590643379350607L;

	public CourseNotFoundException(String message) {
		super(message);
	}

}
