package `in`.`fun`.tazzo

import androidx.compose.ui.graphics.Color


class AngleToColorConverter(
  private val baseColor: Color,
  private val gyroReader: Int,
) {

  private fun getRadiumColor(angle: Int, color: Float): Float {
    val factor = angle.toFloat() / 360f
    val newColor = if(color * factor * 100 > 1) color else color * factor * 100
    return if (factor > 0.0) newColor else color
  }

  fun convert(): Color {
    val angle = gyroReader
    return Color(
      getRadiumColor(angle = angle, color = baseColor.red),
      getRadiumColor(angle = angle, color = baseColor.green),
      getRadiumColor(angle = angle, color = baseColor.blue)
    )
  }
}