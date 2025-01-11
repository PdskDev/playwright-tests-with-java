package me.nadetdev.playwright.domain;

import net.datafaker.Faker;

public record User(
    String first_name,
    String last_name,
    String address,
    String city,
    String state,
    String country,
    String postcode,
    String phone,
    String dob,
    String password,
    String email) {

  public static User getFakeUser() {
    Faker fake = new Faker();
    return new User(
        fake.name().firstName(),
        fake.name().lastName(),
        fake.address().streetAddress(),
        fake.address().city(),
        fake.address().state(),
        fake.address().country(),
        fake.address().postcode(),
        fake.phoneNumber().cellPhone(),
        "1970-01-01",
       "P*ssword01",
        fake.internet().emailAddress());
  }

  public Object withPassord(String password) {
    return new User(
            first_name,
            last_name,
            address,
            city,
            state,
            country,
            postcode,
            phone,
            dob,
            password,
            email
    );
  }
}
