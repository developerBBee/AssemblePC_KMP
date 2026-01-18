package jp.developer.bbee.assemblepc.presentation.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.developer.bbee.assemblepc.domain.model.Price

@Composable
fun BoxScope.MultipleTotalPrice(
    quantity: Int,
    price: Price,
) {
    if (quantity > 1) {
        val totalPrice = price * quantity
        val text = if (totalPrice.isZero()) {
            "${quantity}つ構成中"
        } else {
            "×${quantity}\n${totalPrice.yenOrUnknown()}"
        }

        Card(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-10).dp, y = (-5).dp)
                .clip(shape = RoundedCornerShape(2.dp))
                .testTag("multiple_total_price"),
            backgroundColor = MaterialTheme.colors.primaryVariant
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                text = text,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}