Feature: Products catalog

  As a customer,
  I want to easily search, filter and sort products in a catalog
  So that I can find the product I am looking for.

  Sally is a onloneline customer

  Rule: Customers should be able to search for products by name
    Example: The one where Sally searchs for an Ajustable Wrench
      Given Sally is on the home page
      When Sally searchs for an "pliers"
      Then the "Combination Pliers" product should be displayed