package jp.developer.bbee.assemblepc.shared.presentation.screen.assembly.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import assemblepc.shared.generated.resources.Res
import assemblepc.shared.generated.resources.bd_drive
import assemblepc.shared.generated.resources.case_fan
import assemblepc.shared.generated.resources.cpu
import assemblepc.shared.generated.resources.cpu_cooler
import assemblepc.shared.generated.resources.dvd_drive
import assemblepc.shared.generated.resources.fan_controller
import assemblepc.shared.generated.resources.hdd_35inch
import assemblepc.shared.generated.resources.item_image_description
import assemblepc.shared.generated.resources.keyboard
import assemblepc.shared.generated.resources.lcd_monitor
import assemblepc.shared.generated.resources.motherboard
import assemblepc.shared.generated.resources.mouse
import assemblepc.shared.generated.resources.no_image
import assemblepc.shared.generated.resources.os_soft
import assemblepc.shared.generated.resources.pc_case
import assemblepc.shared.generated.resources.pc_memory
import assemblepc.shared.generated.resources.pc_speaker
import assemblepc.shared.generated.resources.power_supply
import assemblepc.shared.generated.resources.sound_card
import assemblepc.shared.generated.resources.ssd
import assemblepc.shared.generated.resources.video_card
import coil3.compose.AsyncImage
import jp.developer.bbee.assemblepc.shared.common.Constants
import jp.developer.bbee.assemblepc.shared.domain.model.CompositionItem
import jp.developer.bbee.assemblepc.shared.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.shared.presentation.common.BaseBGPreview
import jp.developer.bbee.assemblepc.shared.presentation.components.MultipleTotalPrice
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

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
                    placeholder = painterResource(Res.drawable.no_image),
                    modifier = Modifier
                        .padding(5.dp)
                        .height(80.dp)
                        .width(80.dp),
                    contentDescription = stringResource(Res.string.item_image_description),
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

private val DeviceType.textResId: StringResource
    get() = when (this) {
        DeviceType.PC_CASE -> Res.string.pc_case
        DeviceType.MOTHER_BOARD -> Res.string.motherboard
        DeviceType.POWER_SUPPLY -> Res.string.power_supply
        DeviceType.CPU -> Res.string.cpu
        DeviceType.CPU_COOLER -> Res.string.cpu_cooler
        DeviceType.MEMORY -> Res.string.pc_memory
        DeviceType.SSD -> Res.string.ssd
        DeviceType.HDD -> Res.string.hdd_35inch
        DeviceType.VIDEO_CARD -> Res.string.video_card
        DeviceType.OS -> Res.string.os_soft
        DeviceType.DISPLAY -> Res.string.lcd_monitor
        DeviceType.KEYBOARD -> Res.string.keyboard
        DeviceType.MOUSE -> Res.string.mouse
        DeviceType.DVD_DRIVE -> Res.string.dvd_drive
        DeviceType.BD_DRIVE -> Res.string.bd_drive
        DeviceType.SOUND_CARD -> Res.string.sound_card
        DeviceType.SPEAKER -> Res.string.pc_speaker
        DeviceType.FAN_CONTROLLER -> Res.string.fan_controller
        DeviceType.CASE_FAN -> Res.string.case_fan
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
