package jp.developer.bbee.assemblepc.domain.model.enums

import jp.developer.bbee.assemblepc.domain.model.serializer.DeviceTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = DeviceTypeSerializer::class)
enum class DeviceType(val key: String) {
    PC_CASE("pccase"),
    MOTHER_BOARD("motherboard"),
    POWER_SUPPLY("powersupply"),
    CPU("cpu"),
    CPU_COOLER("cpucooler"),
    MEMORY("pcmemory"),
    SSD("ssd"),
    HDD("hdd35inch"),
    VIDEO_CARD("videocard"),
    OS("ossoft"),
    DISPLAY("lcdmonitor"),
    KEYBOARD("keyboard"),
    MOUSE("mouse"),
    DVD_DRIVE("dvddrive"),
    BD_DRIVE("bluraydrive"),
    SOUND_CARD("soundcard"),
    SPEAKER("pcspeaker"),
    FAN_CONTROLLER("fancontroller"),
    CASE_FAN("casefan")
    ;

    companion object {

        fun from(key: String): DeviceType {
            return entries.first { it.key == key }
        }
    }
}