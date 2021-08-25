package com.project.pickItUp.service;

import com.project.pickItUp.entity.Organization;
import com.project.pickItUp.entity.User;
import com.project.pickItUp.exception.InvalidUserIDException;
import com.project.pickItUp.exception.OrganizationNameRegisteredException;
import com.project.pickItUp.model.request.OrganizationCreationRequest;
import com.project.pickItUp.repository.OrganizationRepository;
import com.project.pickItUp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private UserRepository userRepository;

    public String createOrganization(OrganizationCreationRequest request) {
        Optional<Organization> registeredOrganization = this.organizationRepository.findByName(request.getName());
        if(registeredOrganization.isPresent()) {
            throw new OrganizationNameRegisteredException();
        }
        Optional<User> creatorUser = this.userRepository.findById(Long.valueOf(request.getCreatorId()));
        if(!creatorUser.isPresent()) {
            throw new InvalidUserIDException();
        }
        Organization organization = new Organization();
        organization.setName(request.getName());
        organization.setWebsiteUrl(request.getWebsiteUrl());
        organization.getOrganizationMembers().add(creatorUser.get());
        creatorUser.get().getAssociatedOrganizations().add(organization);
        this.organizationRepository.save(organization);
        return "Organization created";
    }

    public String addOrganizationMember(Long orgId, Long userId) {
        Optional<Organization> org = this.organizationRepository.findById(orgId);
        Optional<User> user = this.userRepository.findById(userId);
        if(org.isPresent() && user.isPresent()) {
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
            Optional<User> requestInitiator =
                    this.userRepository.
                            findById(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName()));
            if(requestInitiator.isPresent()) {
                //Checks if the one requesting to add new member is part of the organization or not
                Long count = this.userRepository.getOrganizationMemberCount(orgId,
                        Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName()));
                if(count == 0) {
                    return "Only existing organization members can add new member";
                } else {
                    org.get().getOrganizationMembers().add(user.get());
                    user.get().getAssociatedOrganizations().add(org.get());
                    this.organizationRepository.save(org.get());
                    return "New member added";
                }
            } else {
                return "You don't have permission to access the resource";
            }
        } else {
            return "Org Id or User Id not valid";
        }
    }
}
