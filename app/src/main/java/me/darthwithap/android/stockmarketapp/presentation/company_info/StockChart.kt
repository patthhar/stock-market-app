package me.darthwithap.android.stockmarketapp.presentation.company_info

import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.darthwithap.android.stockmarketapp.domain.model.IntradayInfo
import kotlin.math.round
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StockChart(
  infoList: List<IntradayInfo> = emptyList(),
  modifier: Modifier = Modifier,
  graphColor: Color = Color.Green
) {
  val spacing = 100f
  val transparentGraphColor = remember {
    graphColor.copy(alpha = 0.55f)
  }
  val upperValue = remember(infoList) {
    // Add 1 and then round it to the floor value
    (infoList.maxOfOrNull { it.close }?.plus(1)?.roundToInt() ?: 0)
  }
  val lowerValue = remember(infoList) {
    // Round it to the floor value and not the nearest by using roundToInt()
    (infoList.minOfOrNull { it.close }?.toInt()!!)
  }
  val density = LocalDensity.current
  val textPaint = remember {
    Paint().apply {
      color = android.graphics.Color.WHITE
      textAlign = Paint.Align.CENTER
      textSize = density.run { 12.sp.toPx() }
    }
  }
  Canvas(modifier = modifier) {
    // X-axis on canvas
    val spacePerHour = (size.width - spacing) / infoList.size
    (0 until infoList.size - 1 step 2).forEach { i ->
      val info = infoList[i]
      val hour = info.date.hour
      drawContext.canvas.nativeCanvas.apply {
        drawText(
          hour.toString(),
          spacing + i * spacePerHour,
          size.height - 8,
          textPaint
        )
      }
    }
    // Y-axis draw on canvas
    val priceStep = (upperValue - lowerValue) / 4f
    (0..4).forEach { i ->
      drawContext.canvas.nativeCanvas.apply {
        drawText(
          round(lowerValue + priceStep * i).toString(),
          29f,
          size.height - spacing - i * size.height / 4f,
          textPaint
        )
      }
    }
    var lastX = 0f
    // Actual graph using a stroke path
    val strokePath = Path().apply {
      val height = size.height
      for (i in infoList.indices) {
        val info = infoList[i]
        val nextInfo = infoList.getOrNull(i + 1) ?: infoList.last()
        // in terms of total percentages
        val leftRatio = (info.close - lowerValue) / (upperValue - lowerValue)
        val rightRatio = (nextInfo.close - lowerValue) / (upperValue - lowerValue)
        // Getting x1, x2, y1, y2 to draw a line between these two
        val x1 = spacing + i * spacePerHour
        val y1 = height - spacing - (leftRatio * height).toFloat()
        val x2 = spacing + (i + 1) * spacePerHour
        val y2 = height - spacing - (rightRatio * height).toFloat()
        // if at first info then move to that point
        if (i == 0) {
          moveTo(x1, y1)
        }
        lastX = (x1 + x2) / 2f
        quadraticBezierTo(x1, y1, (x1 + x2) / 2f, (y1 + y2) / 2f)
      }
    }
    // shape gradient using a path as well
    val fillPath = android.graphics.Path(strokePath.asAndroidPath())
      .asComposePath()
      .apply {
        lineTo(lastX, size.height - spacing)
        lineTo(spacing, size.height - spacing)
        close()
      }
    drawPath(
      path = fillPath, brush = Brush.verticalGradient(
        colors = listOf(transparentGraphColor, Color.Transparent),
        endY = size.height - spacing
      )
    )
    drawPath(
      path = strokePath,
      color = graphColor,
      style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
    )
  }
}