package com.dropwear.user.web.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.dropwear.user.domain.*;

/** Contrats d'échange REST de user-service (records immuables). */
public final class UserDtos {

    private UserDtos() {
    }

    public record RegisterRequest(@NotBlank @Email String email, @NotBlank String password, @NotBlank String firstName, @NotBlank String lastName, String phone) {
    }

    public record UserProfileDto(UUID id, String email, String firstName, String lastName, String phone, Role role, UserStatus status) {
    }

    public record UpdateProfileRequest(@NotBlank String firstName, @NotBlank String lastName, String phone) {
    }

    public record AddressRequest(String label, @NotBlank String fullName, @NotBlank String street, @NotBlank String city, @NotBlank String postalCode, String governorate, @NotBlank String country, @NotBlank String phone, boolean defaultAddress) {
    }

    public record AddressDto(UUID id, String label, String fullName, String street, String city, String postalCode, String governorate, String country, String phone, boolean defaultAddress) {
    }

    public record RoleUpdateRequest(@NotNull Role role) {
    }

    public record StatusUpdateRequest(@NotNull UserStatus status) {
    }

    public record SupplierProfileRequest(@NotBlank String companyName, @NotBlank String taxId, @Email String contactEmail, String contactPhone, String country, @Min(1) int shippingDelayDays) {
    }

    public record SupplierProfileDto(UUID id, UUID userId, String companyName, String taxId, String contactEmail, String contactPhone, String country, int shippingDelayDays, boolean active) {
    }

    public record UserContactDto(UUID userId, String fullName, String email, String phone) {
    }
}
