package jp.developer.bbee.assemblepc.domain.model.serializer

import jp.developer.bbee.assemblepc.domain.model.Price
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class PriceSerializer : KSerializer<Price> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            serialName = "jp.developer.bbee.assemblepc.domain.model.Price",
            kind = PrimitiveKind.INT,
        )

    override fun deserialize(decoder: Decoder): Price {
        return Price(decoder.decodeInt())
    }

    override fun serialize(
        encoder: Encoder,
        value: Price
    ) {
        encoder.encodeInt(value.value)
    }
}
