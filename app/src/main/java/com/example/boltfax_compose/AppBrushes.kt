package com.example.boltfax_compose

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.example.boltfax_compose.ui.ColorPrimary
import com.example.boltfax_compose.ui.ColorSecondary
import com.example.boltfax_compose.ui.ColorSecondaryGradient

//| XML Angle | Direction               | Vector (start to end)            |
//| --------- | ----------------------- | -------------------------------- |
//| `0`       | Left → Right            | `start = (0, 0)`, `end = (1, 0)` |
//| `90`      | Top → Bottom            | `start = (0, 0)`, `end = (0, 1)` |
//| `180`     | Right → Left            | `start = (1, 0)`, `end = (0, 0)` |
//| `270`     | Bottom → Top            | `start = (0, 1)`, `end = (0, 0)` |
//| `45`      | Top-left → Bottom-right | `start = (0, 0)`, `end = (1, 1)` |
//| `135`     | Top-right → Bottom-left | `start = (1, 0)`, `end = (0, 1)` |
//| `225`     | Bottom-right → Top-left | `start = (1, 1)`, `end = (0, 0)` |
//| `315`     | Bottom-left → Top-right | `start = (0, 1)`, `end = (1, 0)` |

object AppBrushes {


    val BackgroundGradient = Brush.linearGradient(
        colors = listOf(ColorSecondaryGradient, ColorSecondary),
        start = Offset(0f, 0f),
        end = Offset(1000f, 0f) // Or use Modifier.onSizeChanged to get width dynamically
    )
}