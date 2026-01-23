package jp.developer.bbee.assemblepc.shared.presentation.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import assemblepc.shared.generated.resources.Res
import assemblepc.shared.generated.resources.item_image_description
import assemblepc.shared.generated.resources.label_add
import assemblepc.shared.generated.resources.label_cancel
import assemblepc.shared.generated.resources.label_change
import assemblepc.shared.generated.resources.label_delete
import assemblepc.shared.generated.resources.label_link_to_website
import assemblepc.shared.generated.resources.title_add_assembly
import assemblepc.shared.generated.resources.title_edit_assembly
import coil3.compose.AsyncImage
import jp.developer.bbee.assemblepc.shared.common.Constants
import jp.developer.bbee.assemblepc.shared.domain.model.Device
import jp.developer.bbee.assemblepc.shared.presentation.common.BasePreview
import jp.developer.bbee.assemblepc.shared.presentation.screen.device.components.NumberEditor
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

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
        Res.string.title_edit_assembly
    } else {
        Res.string.title_add_assembly
    }.let { stringResource(it) }

    val editButtonText = if (isEdit) {
        Res.string.label_change
    } else {
        Res.string.label_add
    }.let { stringResource(it) }

    AlertDialog(
        modifier = Modifier,//.semantics { testTagsAsResourceId = Constants.DEBUG },
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
                        contentDescription = stringResource(Res.string.item_image_description),
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
                            text = stringResource(Res.string.label_link_to_website),
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
                        Text(text = stringResource(Res.string.label_cancel))
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
                            Text(text = stringResource(Res.string.label_delete))
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
