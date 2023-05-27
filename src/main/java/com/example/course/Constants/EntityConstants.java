package com.example.course.Constants;

public class EntityConstants {
    public static final String updateCourseQuery = "UPDATE course_course SET name=IF(:name IS NULL,name,:name),description=IF(:description IS NULL,description,:description),educator=IF(:educator IS NULL,educator,:educator) where id=:id";
    public static final String deleteCourseQuery = "UPDATE course_course SET deleted=1 where id=:id";

}
