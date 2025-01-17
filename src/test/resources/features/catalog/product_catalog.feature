Feature: Products catalog

  As a customer,
  I want to easily search, filter and sort products in a catalog
  So that I can find the product I am looking for.

  Sally is a online customer

  Rule: Customers should be able to search for products by name
    Example: The one where Sally searchs for an pliers
      Given Sally is on the home page
      When Sally searches for an "pliers"
      Then the "Combination Pliers" product should be displayed

    Example: The one where Sally searchs for more general term
      Given Sally is on the home page
      When Sally searches for an "saw"
      Then the following products should be displayed:
       | Wood Saw |
       | Circular Saw |

    Example: The one where Sally searchs for more general term
      Given Sally is on the home page
      When Sally searches for an "screwdriver"
      Then the following products should be displayed with his prices:
        | Product | Price |
        | Phillips Screwdriver | $4.92 |
        | Mini Screwdriver | $13.96 |

    Example: The one where Sally searchs for more general term
      Given Sally is on the home page
      When Sally searches for an "hammer"
      Then result page should displays the following products and prices:
        | Product | Price |
        | Court Hammer | $18.63 |
        | Claw Hammer with Fiberglass Handle | $20.14 |
        | Thor Hammer | $11.14 |

    Example: The one where Sally searchs for more general term
      Given Sally is on the home page
      When Sally searches for an "product-not-exist"
      Then no product should be displayed
      And the message "There are no products found." should be displayed

  Rule: Customers should be able to narrow downs their search by category
    Example: The one where Sally only wants to see Hand Saws
      Given Sally is on the home page
      When Sally searches for an "saw"
      And she filters by "Hand Saw"
      Then the following products should be displayed with his prices:
        | Product  | Price  |
        | Wood Saw | $12.18 |
