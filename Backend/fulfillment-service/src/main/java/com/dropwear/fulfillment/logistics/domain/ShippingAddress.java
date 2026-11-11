package com.dropwear.fulfillment.logistics.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Instantané (snapshot) de l'adresse au moment de la commande : non modifié si le client change son carnet. */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ShippingAddress {

    private String fullName;
    private String street;
    private String city;
    private String postalCode;
    private String governorate;
    private String country;
    private String phone;
}
