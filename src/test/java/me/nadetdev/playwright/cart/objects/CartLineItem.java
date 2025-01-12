package me.nadetdev.playwright.cart.objects;

public record CartLineItem(String title, int quantity, Double price, Double total) {}
