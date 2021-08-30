package com.project.pickItUp.helper;

import com.project.pickItUp.entity.User;

import java.util.List;

public interface VolunteerPicker {
    List<User> selectVolunteers(List<User> users);
}
