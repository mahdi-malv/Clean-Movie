package ir.malv.cleanmovies.utils

import android.graphics.Color
import kotlin.random.Random

object ColorUtil {
    fun randomColor(): Int = Color.argb(
        255,
        Random.nextInt(from = 0, until = 255),
        Random.nextInt(from = 0, until = 255),
        Random.nextInt(from = 0, until = 255)
    )

    fun isColorDark(color: Int): Boolean = (1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255) >= 0.5
}