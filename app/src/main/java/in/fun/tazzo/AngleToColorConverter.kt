package `in`.`fun`.tazzo

import android.graphics.Color
import io.reactivex.rxjava3.core.Observable

class AngleToColorConverter(
  private val baseColor: Color,
  private val gyroReader: Observable<Int>
) {

  private fun getRedRadiumColor(angle: Int): Float {
    return baseColor.red() * (angle/360f)
  }

  private fun getGreenRadiumColor(angle: Int): Float {
    return baseColor.green() * (angle/360f)
  }

  private fun getBlueRadiumColor(angle: Int): Float {
    return baseColor.blue() * (angle/360f)
  }

  fun convert(): Observable<Color> {
    return gyroReader.map { angle ->
      Color.valueOf(
        getRedRadiumColor(angle = angle),
        getGreenRadiumColor(angle = angle),
        getBlueRadiumColor(angle = angle)
      )
    }
  }
}