package com.senzecit.iitiimshaadi.model.commons;

/**
 * Created by senzec on 8/2/18.
 */

public class CourseModel {

    public CourseModel(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    String courseId;
    String courseName;
}
