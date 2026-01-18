package jp.developer.bbee.assemblepc.presentation.screen.device.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExposureNeg1
import androidx.compose.material.icons.filled.ExposurePlus1
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import jp.developer.bbee.assemblepc.R
import jp.developer.bbee.assemblepc.presentation.common.BaseBGPreview

@Composable
fun NumberEditor(
    width: Dp = 64.dp,
    value: Int = 0,
    onValueChange: (Int) -> Unit = {},
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        IconButton(
            onClick = { onValueChange((value - 1).coerceAtLeast(0)) }
        ) {
            Icon(
                modifier = Modifier.testTag("minus_button"),
                imageVector = Icons.Default.ExposureNeg1,
                contentDescription = stringResource(R.string.minus_button_description),
                tint = Color.Blue,
            )
        }

        OutlinedTextField(
            modifier = Modifier.width(width),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            value = "$value",
            onValueChange = { onValueChange(it.toIntOrNull()?.validate(previous = value) ?: 0) }
        )

        IconButton(
            modifier = Modifier,
            onClick = { onValueChange((value + 1).coerceAtMost(99)) }
        ) {
            Icon(
                modifier = Modifier.testTag("plus_button"),
                imageVector = Icons.Default.ExposurePlus1,
                contentDescription = stringResource(R.string.plus_button_description),
                tint = Color.Red,
            )
        }
    }
}

private fun Int.validate(previous: Int): Int = if (this in (0..99)) {
    this
} else {
    previous
}

@Preview
@Composable
private fun NumberPickerPreview() {
    var value by remember { mutableIntStateOf(0) }
    BaseBGPreview {
        NumberEditor(
            value = value,
            onValueChange = { value = it }
        )
    }
}
