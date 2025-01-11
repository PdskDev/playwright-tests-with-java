package me.nadetdev.playwright.domain;

import net.datafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
            getDateFormatted(),
       "P*ssword01",
        fake.internet().emailAddress());
  }

  public static User getFakeUserWithNoFistName() {
    Faker fake = new Faker();
    return new User(
            null,
            fake.name().lastName(),
            fake.address().streetAddress(),
            fake.address().city(),
            fake.address().state(),
            fake.address().country(),
            fake.address().postcode(),
            fake.phoneNumber().cellPhone(),
            getDateFormatted(),
            "P*ssword01",
            fake.internet().emailAddress());
  }

  private static String getDateFormatted() {
    Faker faker = new Faker();
    int year = faker.number().numberBetween(1950, 2007);
    int month = faker.number().numberBetween(1, 12);
    int day = faker.number().numberBetween(1,31);
    LocalDate date = LocalDate.of(year, month, day);
    return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }

  public User withPassword(String password) {
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
