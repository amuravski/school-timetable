package com.solvd.schooltimetable.domain;

import java.util.Objects;

public class CalendarDay {

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        CalendarDay guest = (CalendarDay) obj;
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
