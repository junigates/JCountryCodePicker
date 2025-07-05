package com.junigates.j_cc_picker.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun mySpacer(h: Int) {
    Spacer(modifier = Modifier.height(h.dp))
}

@Composable
fun myWidthSpacer(w: Int) {
    Spacer(modifier = Modifier.width(w.dp))
}


@Composable
fun DashedLine(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    dashWidth: Float = 15f,
    gapWidth: Float = 15f
) {
    Canvas(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .height(0.5.dp)
            .then(modifier)
    ) {
        drawLine(
            alpha = 0.4F,
            color = color,
            start = androidx.compose.ui.geometry.Offset(0f, size.height / 2),
            end = androidx.compose.ui.geometry.Offset(size.width, size.height / 2),
            strokeWidth = 1.dp.toPx(),
            cap = StrokeCap.Round,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidth, gapWidth), 0f)
        )
    }
}