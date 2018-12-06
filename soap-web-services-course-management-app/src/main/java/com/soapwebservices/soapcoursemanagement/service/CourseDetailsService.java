package com.soapwebservices.soapcoursemanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.stereotype.Component;

import com.soapwebservices.soapcoursemanagement.soap.bean.Course;

@Component
public class CourseDetailsService {
	public enum Status {
		SUCCESS, FAILURE;
	}

	private static List<Course> courses = new ArrayList<>();
	static {
		Course course1 = new Course(1, "MAV", "by Programming Knowledge");
		courses.add(course1);
		Course course2 = new Course(2, "Junit", "the easy way");
		courses.add(course2);
		Course course3 = new Course(3, "Mockito", "Basics only");
		courses.add(course3);
		Course course4 = new Course(4, "Master Spring Framework", "Including Spring Boot");
		courses.add(course4);
		Course course5 = new Course(5, "Rest Web Services", "SOAP included");
		courses.add(course5);

	}

	public Course findById(int id) {
		for (Course course : courses) {
			if (id == course.getId())
				return course;
		}
		return null;
	}

	public List<Course> getAllCourses() {
		return courses;
	}

	public Status deleteById(int id) {
		ListIterator<Course> listIterator = courses.listIterator();
		while (listIterator.hasNext()) {
			Course course = listIterator.next();
			if (id == course.getId()) {
				listIterator.remove();
				return Status.SUCCESS;
			}
		}
		return Status.FAILURE;
	}

}
