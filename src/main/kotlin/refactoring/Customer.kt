package refactoring

class Customer(val name: String) {
    private var rentals: MutableList<Rental> = mutableListOf()

    fun addRental(rental: Rental) = rentals.add(rental)

    fun statement(): String {
        var totalAmount = 0.0
        var frequentRenterPoints = 0
        var result = StringBuilder(1024)
        result.append("Rental Record for $name\n")

        for (rental in rentals) {
            var thisAmount = 0.0
            val daysRented = rental.daysRented
            val movieType = rental.movie.type

            // determine amounts for each line
            when (movieType) {
                MovieType.REGULAR -> {
                    thisAmount += 2
                    if (daysRented > 2)
                        thisAmount += (daysRented - 2) * 1.5
                }
                MovieType.NEW_RELEASE -> thisAmount += daysRented * 3
                MovieType.CHILDREN -> {
                    thisAmount += 1.5
                    if (daysRented > 3)
                        thisAmount += (daysRented - 3) * 1.5
                }
            }

            // add frequent renter points
            frequentRenterPoints++
            // add bonus for a two day new release rental
            if (movieType == MovieType.NEW_RELEASE && daysRented > 1)
                frequentRenterPoints++

            // show figures for this rental
            result.append("\t${rental.movie.title}\t$thisAmount\n")

            totalAmount += thisAmount
        }

        // add footer lines
        result.append("Amount owed is $totalAmount\n")
            .append("You earned $frequentRenterPoints frequent renter pointers")

        return result.toString()
    }
}