package com.example.course.Constants;

public class EntityConstants {
    public static final String updateCourseQuery = "UPDATE course_course SET name=IF(:name IS NULL,name,:name),description=IF(:description IS NULL,description,:description),educator=IF(:educator IS NULL,educator,:educator) where id=:id and deleted=false";
    public static final String updateLectureQuery = "UPDATE course_lecture SET name=IF(:name IS NULL,name,:name),source=IF(:source IS NULL,source,:source),module=IF(:module IS NULL,module,:module) where id=:id and deleted=false";
    public static final String deleteLectureQuery = "UPDATE course_lecture SET deleted=1 where id=:id";
    public static final String deleteEnrollmentQuery = "UPDATE course_enrollment SET deleted=1 where id=:id";
    public static final String deleteModuleQuery = "UPDATE course_module SET deleted=1 where id=:id";
    public static final String deleteCourseQuery = "UPDATE course_course SET deleted=1 where id=:id";
    public static final String moduleExistsQuery = "SELECT CASE WHEN EXISTS (SELECT * FROM course_module WHERE id = :id and deleted=false) THEN true ELSE false END";
    public static final String lectureExistsQuery = "SELECT CASE WHEN EXISTS (SELECT * FROM course_lecture WHERE id = :id and deleted=false) THEN true ELSE false END";
    public static final String courseExistsQuery = "SELECT CASE WHEN EXISTS (SELECT * FROM course_course WHERE id = :id and deleted=false) THEN true ELSE false END";

}
