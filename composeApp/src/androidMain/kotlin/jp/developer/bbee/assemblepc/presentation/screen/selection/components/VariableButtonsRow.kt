package jp.developer.bbee.assemblepc.presentation.screen.selection.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.developer.bbee.assemblepc.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.R

// TODO グリッドレイアウトに変更
@Composable
fun VariableButtonsRow (onSelected: (DeviceType) -> Unit) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    // 画面幅340dpごとに列数を増やすレスポンシブレイアウト
    val rows = (screenWidth / 340) + 1
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        for (i in 0 ..DeviceType.entries.size / rows) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                for (j in 0 until rows) {
                    // (i+1)行(j+1)列目のデバイスのボタンを生成、存在しなければnull->Spacer
                    val type = DeviceType.entries.getOrNull(i * rows + j)
                    if (type != null) {
                        Button(
                            modifier = Modifier
                                .width(160.dp)
                                .height(120.dp)
                                .clip(shape = RoundedCornerShape(20.dp))
                                .testTag(type.key),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary,
                            ),
                            onClick = { onSelected(type) }
                        ) {
                            val text = stringResource(getDeviceNameId(type))
                            val textSize = if (text.replace("\n","").length > 5) 16.sp else 20.sp
                            Text(
                                text = text,
                                fontSize = textSize,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.width(160.dp))
                    }
                }
            }
        }
    }
}

@StringRes
private fun getDeviceNameId(deviceType: DeviceType): Int =
    when (deviceType) {
        DeviceType.PC_CASE -> R.string.pc_case
        DeviceType.MOTHER_BOARD -> R.string.motherboard
        DeviceType.POWER_SUPPLY -> R.string.power_supply
        DeviceType.CPU -> R.string.cpu
        DeviceType.CPU_COOLER -> R.string.cpu_cooler
        DeviceType.MEMORY -> R.string.pc_memory
        DeviceType.SSD -> R.string.ssd
        DeviceType.HDD -> R.string.hdd_35inch
        DeviceType.VIDEO_CARD -> R.string.video_card_2_line
        DeviceType.OS -> R.string.os_soft
        DeviceType.DISPLAY -> R.string.lcd_monitor
        DeviceType.KEYBOARD -> R.string.keyboard
        DeviceType.MOUSE -> R.string.mouse
        DeviceType.DVD_DRIVE -> R.string.dvd_drive
        DeviceType.BD_DRIVE -> R.string.bd_drive
        DeviceType.SOUND_CARD -> R.string.sound_card
        DeviceType.SPEAKER -> R.string.pc_speaker
        DeviceType.FAN_CONTROLLER -> R.string.fan_controller_2_line
        DeviceType.CASE_FAN -> R.string.case_fan
    }
