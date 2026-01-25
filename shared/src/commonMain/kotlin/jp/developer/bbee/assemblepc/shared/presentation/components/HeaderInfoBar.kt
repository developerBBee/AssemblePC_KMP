package jp.developer.bbee.assemblepc.shared.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import assemblepc.shared.generated.resources.Res
import assemblepc.shared.generated.resources.assembly_name
import assemblepc.shared.generated.resources.assembly_total_price
import jp.developer.bbee.assemblepc.shared.domain.model.Composition
import jp.developer.bbee.assemblepc.shared.domain.model.sumYen
import jp.developer.bbee.assemblepc.shared.presentation.common.getScreenWidthDp
import org.jetbrains.compose.resources.stringResource

@Composable
fun HeaderInfoBar(
    composition: Composition?,
) {
    composition?.let {
        HeaderContent(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 1.dp),
            composition = composition
        )
    }
}

@Composable
private fun HeaderContent(
    modifier: Modifier = Modifier,
    composition: Composition,
) {
    val assemblyName = composition.assemblyName
    val totalPriceText = composition.items.map { it.devicePriceRecent * it.quantity }.sumYen()

    val screenWidth = getScreenWidthDp()
    val fontSize = if (screenWidth < 600) 18.sp else 24.sp

    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .width(0.dp)
                    .weight(1f),
                text = stringResource(Res.string.assembly_name, assemblyName),
                fontSize = fontSize,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )

            // TODO: 価格のインクリメントアニメーションを追加したい
            Text(
                text = stringResource(Res.string.assembly_total_price, totalPriceText),
                fontSize = fontSize,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Visible,
                textAlign = TextAlign.End
            )
        }
    }
}
