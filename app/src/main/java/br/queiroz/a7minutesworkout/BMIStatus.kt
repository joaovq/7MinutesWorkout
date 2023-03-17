package br.queiroz.a7minutesworkout

enum class BMIStatus(
    val label: String,
    val description: String,
) {
    VERY_SEVERE_UNDERWEIGHT(
        "Very severely underweight",
        "Oops! You really need to take better care of yourself!" +
            " Eat more!",
    ),
    SEVERELY_UNDERWEIGHT(
        "Severely underweight",
        "Oops!You really need to take better care of yourself!" +
            " Eat more!",
    ),
    UNDERWEIGHT(
        "Underweight",
        "Oops! You really need to take better care of yourself!" +
            " Eat more!",
    ),
    NORMAL("Normal", "Congratulations! You are in a good shape!"),
    OVERWEIGHT(
        "Overweight",
        "Oops! You really need to take care of your yourself!" +
            " Workout maybe!",
    ),
    MODERATELY_OBESE(
        "Obese Class | (Moderately obese)",
        "Oops! You really need to take care of your yourself!" +
            " Workout maybe!",
    ),
    SEVERELY_OBESE(
        "Obese Class || (Severely obese)",
        "OMG! You are in a very dangerous condition! Act now!",
    ),
    VERY_SEVERELY_OBESE(
        "Obese Class ||| (Very Severely obese)",
        "OMG! You are in a very dangerous condition! Act now!",
        ),
    
    ;

    companion object {
        fun getMetricsFromBMI(bmi: Float): BMIStatus = when {
            bmi.compareTo(15f) <= 0 -> {
                VERY_SEVERE_UNDERWEIGHT
            }
            bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0 -> {
                SEVERELY_UNDERWEIGHT
            }
            bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0 -> {
                UNDERWEIGHT
            }
            bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0 -> {
                NORMAL
            }
            bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0 -> {
                OVERWEIGHT
            }
            bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0 -> {
                MODERATELY_OBESE
            }
            bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0 -> {
                SEVERELY_OBESE
            }
            else -> {
                VERY_SEVERELY_OBESE
            }
        }
    }
}
