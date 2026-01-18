package jp.developer.bbee.assemblepc.presentation.screen.device.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import jp.developer.bbee.assemblepc.R
import jp.developer.bbee.assemblepc.common.Constants
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.presentation.common.BaseBGPreview
import jp.developer.bbee.assemblepc.presentation.components.MultipleTotalPrice

@Composable
fun DeviceRow(
    modifier: Modifier = Modifier,
    device: Device,
    quantity: Int = 1,
    onClick: (Device) -> Unit
) {

    Box(modifier = modifier) {
        Card(
            elevation = 8.dp,
            modifier = Modifier
                .heightIn(min = 120.dp)
                .padding(vertical = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onClick(device) }
                    .testTag(device.id),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Text(
                        text = device.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.h6,
                        //fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = device.imgUrl,
                            modifier = Modifier
                                .height(80.dp)
                                .width(80.dp),
                            contentDescription = stringResource(R.string.item_image_description),
                        )
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 5.dp),
                            text = device.detail,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onSurface,
                        )
                        Text(
                            modifier = Modifier.offset(y = (-20).dp),
                            text = device.price.yenOrUnknown(),
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.ExtraBold,
                        )
                    }
                }
            }
        }

        MultipleTotalPrice(quantity = quantity, price = device.price)
    }
}

@Preview
@Composable
fun DeviceRowPreview() {
    BaseBGPreview {
        DeviceRow(device = Constants.DEVICE_SAMPLE) { }
    }
}

@Preview
@Composable
fun MultiDeviceRowPreview() {
    BaseBGPreview {
        DeviceRow(device = Constants.DEVICE_SAMPLE, quantity = 3) { }
    }
}
