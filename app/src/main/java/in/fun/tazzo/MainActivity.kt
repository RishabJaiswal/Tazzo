package `in`.`fun`.tazzo

import `in`.`fun`.tazzo.ui.theme.TazzoTheme
import android.animation.ValueAnimator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.ExperimentalTime


class MainActivity : ComponentActivity() {

  private var angle = 2

  private lateinit var colorAnimation: ValueAnimator
  private var currentColor = Color(0xff28679e)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      TazzoTheme {
        val gyroState by gyro.collectAsState(initial = 0)
        val convertedColor by derivedStateOf {
          AngleToColorConverter(
            baseColor = currentColor,
            gyroReader = gyroState,
          ).convert()
        }

        Block(convertedColor)
      }
    }
  }

  @OptIn(ExperimentalTime::class)
  private val gyro = flow {
    while (true) {
      if (angle > 360) angle = 0
      emit(angle++)
      kotlinx.coroutines.delay(Duration.seconds(2))
    }
  }
}


@Composable
fun Block(
  color: Color,
) {
  val animateColor by animateColorAsState(
    targetValue = color,
    animationSpec = spring(stiffness = Spring.StiffnessLow)
  )
  Surface(
    modifier = Modifier.fillMaxSize()
  ) {
    Image(
      painter = painterResource(id = R.drawable.logo),
      contentDescription = "",
      colorFilter = ColorFilter.tint(
        animateColor
      )
    )
  }
}