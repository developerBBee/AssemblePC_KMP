package jp.developer.bbee.assemblepc.data.remote.request

import jp.developer.bbee.assemblepc.domain.model.Composition
import jp.developer.bbee.assemblepc.domain.model.CompositionItem
import jp.developer.bbee.assemblepc.domain.model.Price
import jp.developer.bbee.assemblepc.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.domain.model.serializer.PriceSerializer
import kotlinx.serialization.Serializable

@Serializable
data class ReviewRequest(
    val pcCase: ReviewItem?,
    val motherboard: ReviewItem?,
    val powerSupply: ReviewItem?,
    val cpu: ReviewItem?,
    val cpuCooler: ReviewItem?,
    val memories: List<ReviewItem>,
    val storages: List<ReviewItem>,
    val videoCards: List<ReviewItem>,
) {

    companion object {

        fun from(composition: Composition): ReviewRequest = ReviewRequest(
            pcCase = composition.getItem(DeviceType.PC_CASE)?.let(ReviewItem::from),
            motherboard = composition.getItem(DeviceType.MOTHER_BOARD)?.let(ReviewItem::from),
            powerSupply = composition.getItem(DeviceType.POWER_SUPPLY)?.let(ReviewItem::from),
            cpu = composition.getItem(DeviceType.CPU)?.let(ReviewItem::from),
            cpuCooler = composition.getItem(DeviceType.CPU_COOLER)?.let(ReviewItem::from),
            memories = composition.getItem(DeviceType.MEMORY)?.let(ReviewItem::fromMany)
                ?: emptyList(),
            storages = listOfNotNull(
                composition.getItem(DeviceType.HDD),
                composition.getItem(DeviceType.SSD),
            ).let(ReviewItem::fromList),
            videoCards = composition.getItem(DeviceType.VIDEO_CARD)?.let(ReviewItem::fromMany)
                ?: emptyList(),
        )
    }
}

@Serializable
data class ReviewItem(
    val itemName: String,
    @Serializable(with = PriceSerializer::class)
    val itemPrice: Price,
) {

    companion object {

        fun from(compositionItem: CompositionItem): ReviewItem =
            ReviewItem(itemName = compositionItem.deviceName, itemPrice = compositionItem.price)

        fun fromMany(compositionItem: CompositionItem): List<ReviewItem> =
            List(size = compositionItem.quantity) {
                ReviewItem(itemName = compositionItem.deviceName, itemPrice = compositionItem.price)
            }

        fun fromList(compositionItems: List<CompositionItem>): List<ReviewItem> = compositionItems
            .flatMap { item -> List(size = item.quantity) { item } }
            .map { item -> ReviewItem(itemName = item.deviceName, itemPrice = item.price) }
    }
}