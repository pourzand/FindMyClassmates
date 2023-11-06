package com.example.findmyclassmates;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ClassModel implements Serializable, Parcelable {
    private String department;
    private String courseNumber;
    private String section;
    private String className;
    private String professor;
    private String days;
    private String time;
    private String location;
    private String units;

    public ClassModel(String department, String courseNumber, String section,
                      String className, String professor, String days,
                      String time, String location, String units) {
        this.department = department;
        this.courseNumber = courseNumber;
        this.section = section;
        this.className = className;
        this.professor = professor;
        this.days = days;
        this.time = time;
        this.location = location;
        this.units = units;
    }

    // Parcelable implementation

    protected ClassModel(Parcel in) {
        department = in.readString();
        courseNumber = in.readString();
        section = in.readString();
        className = in.readString();
        professor = in.readString();
        days = in.readString();
        time = in.readString();
        location = in.readString();
        units = in.readString();
    }

    public static final Creator<ClassModel> CREATOR = new Creator<ClassModel>() {
        @Override
        public ClassModel createFromParcel(Parcel in) {
            return new ClassModel(in);
        }

        @Override
        public ClassModel[] newArray(int size) {
            return new ClassModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(department);
        dest.writeString(courseNumber);
        dest.writeString(section);
        dest.writeString(className);
        dest.writeString(professor);
        dest.writeString(days);
        dest.writeString(time);
        dest.writeString(location);
        dest.writeString(units);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getter methods
    public String getDepartment() { return department; }
    public String getCourseNumber() { return courseNumber; }
    public String getSection() { return section; }
    public String getClassName() { return className; }
    public String getProfessor() { return professor; }
    public String getDays() { return days; }
    public String getTime() { return time; }
    public String getLocation() { return location; }
    public String getUnits() { return units; }

    // Setter methods (if needed)
    // Example:
    // public void setClassName(String className) { this.className = className; }
    // ... Add other setter methods if necessary
}
