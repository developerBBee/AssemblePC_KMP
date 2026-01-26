package jp.developer.bbee.assemblepc.shared.domain.model.serializer

import jp.developer.bbee.assemblepc.shared.common.toTokyoInstant
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.time.Instant

class InstantTokyoSerializer : KSerializer<Instant> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "jp.developer.bbee.assemblepc.shared.domain.model.serializer.Instant",
        kind = PrimitiveKind.STRING,
    )

    override fun deserialize(decoder: Decoder): Instant {
        val dateTimeText = decoder.decodeString()
        return dateTimeText.toTokyoInstant()
    }

    override fun serialize(
        encoder: Encoder,
        value: Instant
    ) {
        encoder.encodeString(value.toString())
    }
}
