package jp.developer.bbee.assemblepc.presentation.screen.top.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import jp.developer.bbee.assemblepc.R

@Composable
fun RenameAssemblyConfirmDialog(
    selectedName: String,
    newName: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {

    AlertDialog(
        shape = RoundedCornerShape(10.dp),
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.confirm_assembly_name_change)) },
        text = {
            Column {
                Text(text = selectedName, fontWeight = FontWeight.ExtraBold)
                Text(text = stringResource(R.string.to))
                Text(text = newName, fontWeight = FontWeight.ExtraBold)
                Text(text = stringResource(R.string.change))
            }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, bottom = 5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    modifier = Modifier.padding(end = 10.dp),
                    onClick = onDismiss
                ) {
                    Text(text = stringResource(R.string.label_cancel))
                }

                Button(onClick = onConfirm) {
                    Text(text = stringResource(R.string.label_confirm))
                }
            }
        }
    )
}