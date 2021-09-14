package com.project.pickItUp.service;

import com.project.pickItUp.controller.AddressUpdate;
import com.project.pickItUp.entity.City;
import com.project.pickItUp.entity.Organization;
import com.project.pickItUp.entity.OrganizationAddress;
import com.project.pickItUp.entity.User;
import com.project.pickItUp.exception.type.ApiRequestException;
import com.project.pickItUp.model.request.AddressUpdateRequest;
import com.project.pickItUp.model.request.OrganizationCreationRequest;
import com.project.pickItUp.repository.CityRepository;
import com.project.pickItUp.repository.OrganizationAddressRepository;
import com.project.pickItUp.repository.OrganizationRepository;
import com.project.pickItUp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrganizationService implements AddressUpdate {

    @Autowired
    private AccountService accountService;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganizationAddressRepository organizationAddressRepository;
    @Autowired
    private CityRepository cityRepository;

    public String createOrganization(OrganizationCreationRequest request) {
        Optional<Organization> registeredOrganization = this.organizationRepository.findByName(request.getName());
        if(registeredOrganization.isPresent()) {
            throw new ApiRequestException("Organization with name " + request.getName() + " already present",
                    HttpStatus.BAD_REQUEST);
        }
        Optional<User> creatorUser = this.userRepository.findById(Long.valueOf(request.getCreatorId()));
        if(!creatorUser.isPresent()) {
            throw new ApiRequestException("User not registered", HttpStatus.BAD_REQUEST);
        }
        Organization organization = new Organization();
        organization.setName(request.getName());
        organization.setWebsiteUrl(request.getWebsiteUrl());
        organization.getOrganizationMembers().add(creatorUser.get());
        creatorUser.get().getAssociatedOrganizations().add(organization);
        this.organizationRepository.save(organization);
        return "Organization created";
    }

    public String addOrganizationMember(Long orgId, String email) {
        Optional<Organization> org = this.organizationRepository.findById(orgId);
        Optional<User> user = this.userRepository.findByEmail(email);
        if(org.isPresent() && user.isPresent()) {
            if(isUserPartOfOrganization(user.get().getId(),orgId)) {
                throw new ApiRequestException("User already a part of this organization"
                        ,HttpStatus.BAD_REQUEST);
            }
            long requesterId = Long.valueOf(this.accountService.getUserId());
            if(this.isUserPartOfOrganization(requesterId,orgId)) {
                org.get().getOrganizationMembers().add(user.get());
                user.get().getAssociatedOrganizations().add(org.get());
                this.organizationRepository.save(org.get());
                return "New member added";
            } else {
                throw new ApiRequestException("Only existing organization members can add new member"
                        ,HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new ApiRequestException("Organization or User is not valid"
                    ,HttpStatus.BAD_REQUEST);
        }
    }

    public boolean isUserPartOfOrganization(long userid, long orgId) {
        Optional<User> user = this.userRepository.findById(userid);
        Optional<Organization> org = this.organizationRepository.findById(orgId);
        if(!user.isPresent() || !org.isPresent()) {
            return false;
        }
        long isUserMemberOfOrg = user.get().getAssociatedOrganizations()
                .stream().filter(item -> Objects.equals(item.getId(), orgId)).count();
        return isUserMemberOfOrg > 0;
    }

    @Override
    public String updateAddress(AddressUpdateRequest request) {
        Optional<Organization> org = organizationRepository.findById(request.getEntityId());
        Optional<City> city = this.cityRepository.findById(request.getCityId());
        Optional<User> requestingUser = this.userRepository.findById(accountService.getUserId());
        if(org.isPresent() && city.isPresent() && requestingUser.isPresent()) {
            if(!isUserPartOfOrganization(requestingUser.get().getId(), request.getEntityId())) {
                throw new ApiRequestException("You cannot access this resource", HttpStatus.FORBIDDEN);
            }
            OrganizationAddress address = new OrganizationAddress();
            address.setAddress(request.getAddress());
            address.setCity(city.get());
            address.setOrganization(org.get());
            organizationAddressRepository.save(address);
            return "Address updated";
        } else {
            throw new ApiRequestException("Organization or City not found",HttpStatus.BAD_REQUEST);
        }
    }
}
