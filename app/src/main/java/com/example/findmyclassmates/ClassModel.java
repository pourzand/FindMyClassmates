package com.example.findmyclassmates;

import android.os.Parcel;
import android.os.Parcelable;

public class ClassModel implements Parcelable {
    private String department;
    private String className;
    private String description;
    private String units;
    private String time;
    private String days;
    private String instructor;
    private String location;
    private String classID;

    public ClassModel(String department, String className, String description, String units, String time, String days, String instructor, String location, String classID) {
        this.department = department;
        this.className = className;
        this.description = description;
        this.units = units;
        this.time = time;
        this.days = days;
        this.instructor = instructor;
        this.location = location;
        this.classID = classID;
    }

    // Getters
    public String getDepartment() {
        return department;
    }

    public String getClassName() {
        return className;
    }

    public String getDescription() {
        return description;
    }

    public String getUnits() {
        return getUnits();
    }

    public String getTime() {
        return time;
    }

    public String getDays() {
        return days;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getLocation() {
        return location;
    }

    public String getClassID() {
        return classID;
    }

    // Parcelable implementation
    protected ClassModel(Parcel in) {
        department = in.readString();
        className = in.readString();
        description = in.readString();
        units = in.readString();
        time = in.readString();
        days = in.readString();
        instructor = in.readString();
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(department);
        dest.writeString(className);
        dest.writeString(description);
        dest.writeString(units);
        dest.writeString(time);
        dest.writeString(days);
        dest.writeString(instructor);
        dest.writeString(location);
        dest.writeString(classID);
    }
}
