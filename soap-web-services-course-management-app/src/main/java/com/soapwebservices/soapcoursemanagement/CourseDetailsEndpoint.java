package com.soapwebservices.soapcoursemanagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.soapwebservices.courses.CourseDetails;
import com.soapwebservices.courses.DeleteCourseDetailsRequest;
import com.soapwebservices.courses.DeleteCourseDetailsResponse;
import com.soapwebservices.courses.GetAllCourseDetailsRequest;
import com.soapwebservices.courses.GetAllCourseDetailsResponse;
import com.soapwebservices.courses.GetCourseDetailsRequest;
import com.soapwebservices.courses.GetCourseDetailsResponse;
import com.soapwebservices.soapcoursemanagement.exception.CourseNotFoundException;
import com.soapwebservices.soapcoursemanagement.service.CourseDetailsService;
import com.soapwebservices.soapcoursemanagement.service.CourseDetailsService.Status;
import com.soapwebservices.soapcoursemanagement.soap.bean.Course;

@Endpoint
public class CourseDetailsEndpoint {
	@Autowired
	CourseDetailsService courseDetailsService;

	@PayloadRoot(namespace = "http://soapwebservices.com/courses", localPart = "GetCourseDetailsRequest")
	@ResponsePayload
	public GetCourseDetailsResponse ProcessCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {

		Course course = courseDetailsService.findById(request.getId());
		if (course == null)
			throw new CourseNotFoundException("Invalid Course Id " + request.getId());
		return mapCourseDetails(course);
	}

	private GetCourseDetailsResponse mapCourseDetails(Course course) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();
		response.setCourseDetails(mapCourse(course));
		return response;
	}

	@PayloadRoot(namespace = "http://soapwebservices.com/courses", localPart = "GetAllCourseDetailsRequest")
	@ResponsePayload
	public GetAllCourseDetailsResponse ProcessAllCourseDetailsRequest(
			@RequestPayload GetAllCourseDetailsRequest request) {
		List<Course> courses = courseDetailsService.getAllCourses();
		return mapAllCourseDetails(courses);

	}

	private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
		GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
		for (Course course : courses) {
			CourseDetails mapCourse = mapCourse(course);
			response.getCourseDetails().add(mapCourse);
		}
		return response;
	}

	private CourseDetails mapCourse(Course course) {

		CourseDetails courseDetails = new CourseDetails();
		courseDetails.setId(course.getId());
		courseDetails.setName(course.getName());
		courseDetails.setDescription(course.getDescription());

		return courseDetails;
	}

	@PayloadRoot(namespace = "http://soapwebservices.com/courses", localPart = "DeleteCourseDetailsRequest")
	@ResponsePayload
	public DeleteCourseDetailsResponse deleteCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request) {
		Status status = courseDetailsService.deleteById(request.getId());
		return mapDeleteCourse(status);
	}

	private DeleteCourseDetailsResponse mapDeleteCourse(Status status) {
		DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
		if (status == Status.SUCCESS)
			response.setStatus(com.soapwebservices.courses.Status.SUCCESS);
		response.setStatus(com.soapwebservices.courses.Status.FAILURE);
		return response;
	}

}
