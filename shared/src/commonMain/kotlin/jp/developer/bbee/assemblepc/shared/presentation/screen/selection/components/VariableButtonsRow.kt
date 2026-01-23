package jp.developer.bbee.assemblepc.shared.presentation.screen.selection.components

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import assemblepc.shared.generated.resources.Res
import assemblepc.shared.generated.resources.bd_drive
import assemblepc.shared.generated.resources.case_fan
import assemblepc.shared.generated.resources.cpu
import assemblepc.shared.generated.resources.cpu_cooler
import assemblepc.shared.generated.resources.dvd_drive
import assemblepc.shared.generated.resources.fan_controller_2_line
import assemblepc.shared.generated.resources.hdd_35inch
import assemblepc.shared.generated.resources.keyboard
import assemblepc.shared.generated.resources.lcd_monitor
import assemblepc.shared.generated.resources.motherboard
import assemblepc.shared.generated.resources.mouse
import assemblepc.shared.generated.resources.os_soft
import assemblepc.shared.generated.resources.pc_case
import assemblepc.shared.generated.resources.pc_memory
import assemblepc.shared.generated.resources.pc_speaker
import assemblepc.shared.generated.resources.power_supply
import assemblepc.shared.generated.resources.sound_card
import assemblepc.shared.generated.resources.ssd
import assemblepc.shared.generated.resources.video_card_2_line
import jp.developer.bbee.assemblepc.shared.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.shared.presentation.common.getScreenWidthDp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

// TODO グリッドレイアウトに変更
@Composable
fun VariableButtonsRow (onSelected: (DeviceType) -> Unit) {
    val screenWidth = getScreenWidthDp()
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

private fun getDeviceNameId(deviceType: DeviceType): StringResource =
    when (deviceType) {
        DeviceType.PC_CASE -> Res.string.pc_case
        DeviceType.MOTHER_BOARD -> Res.string.motherboard
        DeviceType.POWER_SUPPLY -> Res.string.power_supply
        DeviceType.CPU -> Res.string.cpu
        DeviceType.CPU_COOLER -> Res.string.cpu_cooler
        DeviceType.MEMORY -> Res.string.pc_memory
        DeviceType.SSD -> Res.string.ssd
        DeviceType.HDD -> Res.string.hdd_35inch
        DeviceType.VIDEO_CARD -> Res.string.video_card_2_line
        DeviceType.OS -> Res.string.os_soft
        DeviceType.DISPLAY -> Res.string.lcd_monitor
        DeviceType.KEYBOARD -> Res.string.keyboard
        DeviceType.MOUSE -> Res.string.mouse
        DeviceType.DVD_DRIVE -> Res.string.dvd_drive
        DeviceType.BD_DRIVE -> Res.string.bd_drive
        DeviceType.SOUND_CARD -> Res.string.sound_card
        DeviceType.SPEAKER -> Res.string.pc_speaker
        DeviceType.FAN_CONTROLLER -> Res.string.fan_controller_2_line
        DeviceType.CASE_FAN -> Res.string.case_fan
    }
