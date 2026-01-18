package jp.developer.bbee.assemblepc.presentation.screen.top.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.developer.bbee.assemblepc.BuildConfig
import jp.developer.bbee.assemblepc.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditAssemblyDialog(
    selectedName: String,
    onDismiss: () -> Unit,
    onAddParts: () -> Unit,
    onShowComposition: () -> Unit,
    onRenameClick: (String) -> Unit,
    onDeleteClick: () -> Unit,
) {
    var newName: String by rememberSaveable { mutableStateOf(selectedName) }

    val changeNameButtonEnable = (newName != selectedName) && newName.isNotEmpty()

    AlertDialog(
        modifier = Modifier.semantics { testTagsAsResourceId = BuildConfig.DEBUG },
        shape = RoundedCornerShape(10.dp),
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = selectedName,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                )
                Text(
                    text = stringResource(R.string.assembly_selected),
                    fontSize = 16.sp,
                )
            }
        },
        text = {
            Column {
                Text(
                    text = stringResource(R.string.edit_name_caption),
                    fontWeight = FontWeight.Bold,
                )
                TextField(
                    value = newName,
                    onValueChange = { if (it.length <= 20) newName = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = stringResource(R.string.edit_name_placeholder)) },
                    maxLines = 1,
                    singleLine = true,
                )
                Button(
                    modifier = Modifier.align(Alignment.End),
                    enabled = changeNameButtonEnable,
                    onClick = { onRenameClick(newName) }
                ) {
                    Text(text = stringResource(R.string.label_update))
                }
            }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, bottom = 5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                // 左側ボタン（上：構成を削除　下：キャンセル）
                Column {
                    Button(
                        modifier = Modifier.testTag("delete_composition_button"),
                        onClick = onDeleteClick
                    ) {
                        Text(text = stringResource(R.string.delete_assembly))
                    }

                    Button(
                        modifier = Modifier.testTag("cancel_composition_dialog_button"),
                        onClick = onDismiss
                    ) {
                        Text(text = stringResource(R.string.label_cancel))
                    }
                }

                // 右側ボタン（上：パーツを追加　下：構成確認）
                Column {
                    Button(
                        modifier = Modifier
                            .align(Alignment.End)
                            .testTag("add_parts_button"),
                        onClick = onAddParts
                    ) {
                        Text(text = stringResource(R.string.add_parts))
                    }

                    Button(
                        modifier = Modifier
                            .align(Alignment.End)
                            .testTag("show_assembly_button"),
                        onClick = onShowComposition
                    ) {
                        Text(text = stringResource(R.string.confirm_assembly))
                    }
                }
            }
        }
    )
}