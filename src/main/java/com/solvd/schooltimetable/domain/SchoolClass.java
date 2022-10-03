package com.solvd.schooltimetable.domain;

import java.util.Objects;

public class SchoolClass {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        SchoolClass guest = (SchoolClass) obj;
        return Objects.equals(id, guest.id)
                && (Objects.equals(name, guest.name)
                || (name != null && name.equals(guest.getName()))
        );
    }

    @Override
    public String toString() {
        return "CalendarDay{ "
                + "Name = " + name
                + " ID = " + id
                + " }";
    }
}
