package jp.developer.bbee.assemblepc.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import jp.developer.bbee.assemblepc.BuildConfig
import jp.developer.bbee.assemblepc.R
import jp.developer.bbee.assemblepc.presentation.common.BasePreview
import jp.developer.bbee.assemblepc.common.Constants
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.presentation.screen.device.components.NumberEditor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AssemblyDialog(
    isEdit: Boolean,
    quantity: Int,
    device: Device,
    onDismiss: () -> Unit,
    onAddAssembly: (Device, Int, Boolean) -> Unit,
    onDeleteAssembly: (Device, Int) -> Unit,
) {

    val uriHandler = LocalUriHandler.current

    var editQuantity by rememberSaveable { mutableIntStateOf(quantity) }

    val diffQty = if (isEdit) {
        editQuantity - quantity
    } else {
        editQuantity
    }

    val title = if (isEdit) {
        R.string.title_edit_assembly
    } else {
        R.string.title_add_assembly
    }.let { stringResource(it) }

    val editButtonText = if (isEdit) {
        R.string.label_change
    } else {
        R.string.label_add
    }.let { stringResource(it) }

    AlertDialog(
        modifier = Modifier.semantics { testTagsAsResourceId = BuildConfig.DEBUG },
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(10.dp),
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
            )
        },
        text = {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    AsyncImage(
                        model = device.imgUrl,
                        modifier = Modifier
                            .padding(end = 10.dp, bottom = 2.dp)
                            .height(100.dp)
                            .width(100.dp),
                        contentDescription = stringResource(R.string.item_image_description),
                    )

                    Text(
                        text = device.price.yenOrUnknown(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(text = device.detail)
            }
        },
        buttons = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(
                        onClick = { uriHandler.openUri(device.url) },
                    ) {
                        Text(
                            fontWeight = FontWeight.ExtraBold,
                            text = stringResource(R.string.label_link_to_website),
                        )
                    }
                    NumberEditor(
                        value = editQuantity,
                        onValueChange = { editQuantity = it }
                    )
                }

                Row(modifier = Modifier.padding(10.dp)) {
                    Spacer(modifier = Modifier.weight(1f))

                    Button(onClick = onDismiss) {
                        Text(text = stringResource(R.string.label_cancel))
                    }

                    Button(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .testTag("edit_assembly_button"),
                        enabled = (diffQty != 0),
                        onClick = {
                            if (isEdit) {
                                if (diffQty > 0) {
                                    onAddAssembly(device, diffQty, isEdit)
                                } else if (diffQty < 0) {
                                    onDeleteAssembly(device, -diffQty)
                                }
                            } else {
                                onAddAssembly(device, editQuantity, isEdit)
                            }
                        }
                    ) {
                        Text(text = editButtonText)
                    }

                    if (isEdit) {
                        // 全削除ボタン
                        Button(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .testTag("delete_assembly_button"),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.error
                            ),
                            onClick = { onDeleteAssembly(device, quantity) }
                        ) {
                            Text(text = stringResource(R.string.label_delete))
                        }
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun AddAssemblyDialogPreview() {
    BasePreview {
        AssemblyDialog(
            isEdit = false,
            quantity = 1,
            device = Constants.DEVICE_SAMPLE,
            onDismiss = {},
            onAddAssembly = { _, _, _ -> },
            onDeleteAssembly = { _, _ -> },
        )
    }
}

@Preview
@Composable
private fun EditAssemblyDialogPreview() {
    BasePreview {
        AssemblyDialog(
            isEdit = true,
            quantity = 3,
            device = Constants.DEVICE_SAMPLE,
            onDismiss = {},
            onAddAssembly = { _, _, _ -> },
            onDeleteAssembly = { _, _ -> },
        )
    }
}
