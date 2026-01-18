package jp.developer.bbee.assemblepc.domain.model.serializer

import jp.developer.bbee.assemblepc.domain.model.enums.DeviceType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class DeviceTypeSerializer() : KSerializer<DeviceType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            serialName = "jp.developer.bbee.assemblepc.domain.model.enums.DeviceType",
            kind = PrimitiveKind.STRING,
        )

    override fun deserialize(decoder: Decoder): DeviceType {
        return DeviceType.from(decoder.decodeString())
    }

    override fun serialize(
        encoder: Encoder,
        value: DeviceType
    ) {
        encoder.encodeString(value.key)
    }
}