package jp.developer.bbee.assemblepc.domain.model.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime

class LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            serialName = "java.time.LocalDateTime",
            kind = PrimitiveKind.STRING,
        )

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString())
    }

    override fun serialize(
        encoder: Encoder,
        value: LocalDateTime
    ) {
        encoder.encodeString(value.toString())
    }
}