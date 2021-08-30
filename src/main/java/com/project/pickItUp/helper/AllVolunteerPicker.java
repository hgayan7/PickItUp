package com.project.pickItUp.helper;

import com.project.pickItUp.entity.User;

import java.util.List;

public class AllVolunteerPicker implements VolunteerPicker {
    @Override
    public List<User> selectVolunteers(List<User> users) {
        return users;
    }
}
