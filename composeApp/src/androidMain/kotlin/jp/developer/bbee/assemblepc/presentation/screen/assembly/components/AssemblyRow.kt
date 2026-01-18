package jp.developer.bbee.assemblepc.presentation.screen.assembly.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import jp.developer.bbee.assemblepc.R
import jp.developer.bbee.assemblepc.common.Constants
import jp.developer.bbee.assemblepc.domain.model.CompositionItem
import jp.developer.bbee.assemblepc.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.presentation.common.BaseBGPreview
import jp.developer.bbee.assemblepc.presentation.components.MultipleTotalPrice

@Composable
fun AssemblyRow(
    modifier: Modifier = Modifier,
    item: CompositionItem,
    onAssemblyClick: () -> Unit,
) {

    Box (modifier = modifier) {
        Card(
            elevation = 8.dp,
            modifier = Modifier
                .heightIn(min = 120.dp)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAssemblyClick() }
                    .testTag(item.deviceType.key),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    model = item.deviceImgUrl,
                    modifier = Modifier
                        .padding(5.dp)
                        .height(80.dp)
                        .width(80.dp),
                    contentDescription = stringResource(R.string.item_image_description),
                )

                // 製品名
                Text(
                    text = item.deviceName,
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f),
                    fontSize = 18.sp,
                    softWrap = true,
                )

                // 単価
                Text(
                    text = item.devicePriceRecent.yenOrUnknown(),
                    modifier = Modifier
                        .padding(10.dp)
                        .offset(y = (-20).dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        Card(
            modifier = Modifier
                .offset(x = (-2).dp, y = (-2).dp)
                .clip(shape = RoundedCornerShape(10.dp)),
            backgroundColor = MaterialTheme.colors.secondary
        ) {
            Text(
                text = stringResource(item.deviceType.textResId),
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 2.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        MultipleTotalPrice(quantity = item.quantity, price = item.devicePriceRecent)
    }
}

private val DeviceType.textResId
    get() = when (this) {
        DeviceType.PC_CASE -> R.string.pc_case
        DeviceType.MOTHER_BOARD -> R.string.motherboard
        DeviceType.POWER_SUPPLY -> R.string.power_supply
        DeviceType.CPU -> R.string.cpu
        DeviceType.CPU_COOLER -> R.string.cpu_cooler
        DeviceType.MEMORY -> R.string.pc_memory
        DeviceType.SSD -> R.string.ssd
        DeviceType.HDD -> R.string.hdd_35inch
        DeviceType.VIDEO_CARD -> R.string.video_card
        DeviceType.OS -> R.string.os_soft
        DeviceType.DISPLAY -> R.string.lcd_monitor
        DeviceType.KEYBOARD -> R.string.keyboard
        DeviceType.MOUSE -> R.string.mouse
        DeviceType.DVD_DRIVE -> R.string.dvd_drive
        DeviceType.BD_DRIVE -> R.string.bd_drive
        DeviceType.SOUND_CARD -> R.string.sound_card
        DeviceType.SPEAKER -> R.string.pc_speaker
        DeviceType.FAN_CONTROLLER -> R.string.fan_controller
        DeviceType.CASE_FAN -> R.string.case_fan
    }

@Preview
@Composable
private fun AssemblyRowPreview(modifier: Modifier = Modifier) {
    BaseBGPreview {
        AssemblyRow(
            modifier = modifier,
            item = Constants.COMPOSITION_SAMPLE.items.first(),
            onAssemblyClick = {}
        )
    }
}

@Preview
@Composable
private fun MultiAssemblyRowPreview(modifier: Modifier = Modifier) {
    BaseBGPreview {
        AssemblyRow(
            modifier = modifier,
            item = Constants.COMPOSITION_SAMPLE.items.first().copy(quantity = 3),
            onAssemblyClick = {}
        )
    }
}
