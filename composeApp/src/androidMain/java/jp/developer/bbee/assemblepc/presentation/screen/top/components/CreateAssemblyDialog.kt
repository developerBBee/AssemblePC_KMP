package jp.developer.bbee.assemblepc.presentation.screen.top.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
fun CreateAssemblyDialog(
    onDismiss: () -> Unit,
    onCreationStart: (String) -> Unit = {},
) {
    var assemblyName: String by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        modifier = Modifier
            .semantics { testTagsAsResourceId = BuildConfig.DEBUG },
        shape = RoundedCornerShape(10.dp),
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.create_new_assembly),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
            )
        },
        text = {
            Column {
                Text(
                    text = stringResource(R.string.input_assembly_name),
                    fontWeight = FontWeight.Bold
                )
                TextField(
                    value = assemblyName,
                    onValueChange = { if (it.length <= 20) assemblyName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("assembly_name_text_field"),
                    placeholder = {
                        Text(text = stringResource(R.string.assembly_name_placeholder))
                    },
                    maxLines = 1,
                    singleLine = true,
                )

            }
        },
        buttons = {
            Row (modifier = Modifier.padding(10.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = onDismiss) {
                    Text(text = stringResource(R.string.label_cancel))
                }
                Button(
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .testTag("create_assembly_button"),
                    onClick = { onCreationStart(assemblyName) }
                ) {
                    Text(text = stringResource(R.string.create_new))
                }
            }
        }
    )
}