package com.example.homework2_tbc_it_academy

private val ones =
    arrayOf("", "ერთი", "ორი", "სამი", "ოთხი", "ხუთი", "ექვსი", "შვიდი", "რვა", "ცხრა")

private val fromTenToNineteen =
    arrayOf("ათი", "თერთმეტი", "თორმეტი", "ცამეტი", "თოთხმეტი", "თხუთმეტი", "თექვსმეტი", "ჩვიდმეტი", "თვრამეტი", "ცხრამეტი")

private val tens =
    arrayOf("", "", "ოცი", "ოცდაათი", "ორმოცი", "ორმოცდაათი", "სამოცი", "სამოცდაათი", "ოთხმოცი", "ოთხმოცდაათი")

private val hundreds =
    arrayOf("", "ასი", "ორასი", "სამასი", "ოთხასი", "ხუთასი", "ექვსასი", "შვიდასი", "რვაასი", "ცხრაასი")

enum class Suffixes(val value: String) {
    GEORGIAN_I("ი"),
    GEORGIAN_TEN("ათი")
}

fun numberToWord(number: Int): String {
    if (number == 0) return "ნული"
    if (number == 1000) return "ათასი"

    return when (number) {
        in 1..9 -> convertFrom1To9ToString(number)
        in 10..99 -> convertFrom10To99ToString(number)
        in 100..999 -> convertFrom100To999ToString(number)
        else -> ""
    }
}

private fun convertFrom1To9ToString(number: Int): String {
    return ones[number]
}

private fun convertFrom10To99ToString(number: Int): String {
    return if (number.toString().first().toString().toInt() == 1)
        fromTenToNineteen[number % 100 - 10]
    else convertFrom20To99ToString(number)
}

private fun convertFrom20To99ToString(num: Int): String {
    return when (num) {
        in 20..99 -> {
            when {
                /*
                 * If number is divisible by 10 without remainder,
                 * it means number ends with 0, so the function will easily find corresponding string in array.
                 */
                num % 10 == 0 -> tens[num % 100 / 10]

                /*
                 * If the first digit is divisible by 2 without remainder, then it means we need to find a number in 20s, 40s, 60s, and 80s.
                 * Such numbers in Georgian are created using tens with removing "ი" letter, adding "და" in the middle and finally a number from ones.
                 */
                num.toString().first().toString().toInt() % 2 == 0 -> "${tens[num % 100 / 10].removeSuffix(Suffixes.GEORGIAN_I.value)}და${ones[num % 10]}"

                /*
                 * If the first number is not divisible by 2, then its suffix has to be "ათი".
                 * Therefore, function removes this suffix because such numbers do not include them, and adds a corresponding string from numbers from
                 * ten to nineteen.
                 */
                else -> "${tens[num % 100 / 10].removeSuffix(Suffixes.GEORGIAN_TEN.value)}${fromTenToNineteen[num % 10]}"
            }
        }
        else -> ""
    }
}

private fun convertFrom100To999ToString(number: Int): String {
    val firstDigit = number / 100
    val lastTwoDigits = number % 100

    // The variable below is used to find the hundreds value of the number, and then it's used several times.
    val firstDigitConvertedToString = hundreds[firstDigit].removeSuffix(Suffixes.GEORGIAN_I.value)

    if (number % 100 == 0)
        return hundreds[firstDigit]

    /*
     * If we have 3-digit number and last 2 digits are less than 10, it means number is in the range of 100..109, 200..209 and so on.
     * Therefore, to find the last two digits we re-use the function that was created to find such numbers.
     */
    if (lastTwoDigits < 10)
        return "$firstDigitConvertedToString ${convertFrom1To9ToString(lastTwoDigits % 10)}"

    /*
     * If we have 3-digit number and last 2 digits are less than 20 and more than 9, it means number is in the range of 110..119, 210..219 and so on.
     * Therefore, to find the last two digits we re-use the solution which finds the corresponding strings from ten to nineteen.
     */
    if (lastTwoDigits < 20)
        return "$firstDigitConvertedToString ${fromTenToNineteen[lastTwoDigits % 100 - 10]}"

    // To convert last two digits to a string for the rest of the cases, we re-use [convertFrom20To99ToString] function.
    return "$firstDigitConvertedToString ${convertFrom20To99ToString(lastTwoDigits)}"
}