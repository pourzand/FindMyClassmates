package com.example.findmyclassmates;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ClassModel implements Serializable, Parcelable {
    private String department;
    private String className;
    private String description;
    private String units;
    private String time;
    private String days;
    private String professor;
    private String location;
    private String classID;

    public ClassModel(String department, String className, String description,
                      String units, String time, String days,
                      String professor, String location, String classID) {
        this.department = department;
        this.className = className;
        this.description = description;
        this.units = units;
        this.time = time;
        this.days = days;
        this.professor = professor;
        this.location = location;
        this.classID = classID;
    }

    // Parcelable implementation
    protected ClassModel(Parcel in) {
        department = in.readString();
        className = in.readString();
        description = in.readString();
        units = in.readString();
        time = in.readString();
        days = in.readString();
        professor = in.readString();
        location = in.readString();
        classID = in.readString();
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
        dest.writeString(className);
        dest.writeString(description);
        dest.writeString(units);
        dest.writeString(time);
        dest.writeString(days);
        dest.writeString(professor);
        dest.writeString(location);
        dest.writeString(classID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getter methods
    public String getDepartment() { return department; }
    public String getClassName() { return className; }
    public String getDescription() { return description; }
    public String getUnits() { return units; }
    public String getTime() { return time; }
    public String getDays() { return days; }
    public String getProfessor() { return professor; }
    public String getLocation() { return location; }
    public String getClassID() { return classID; }

    // Setter methods
    public void setDepartment(String department) { this.department = department; }
    public void setClassName(String className) { this.className = className; }
    public void setDescription(String description) { this.description = description; }
    public void setUnits(String units) { this.units = units; }
    public void setTime(String time) { this.time = time; }
    public void setDays(String days) { this.days = days; }
    public void setProfessor(String professor) { this.professor = professor; }
    public void setLocation(String location) { this.location = location; }
    public void setClassID(String classID) { this.classID = classID; }
}
