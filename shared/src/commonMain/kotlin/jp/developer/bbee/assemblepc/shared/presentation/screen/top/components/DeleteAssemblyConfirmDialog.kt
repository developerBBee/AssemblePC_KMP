package jp.developer.bbee.assemblepc.shared.presentation.screen.top.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import assemblepc.shared.generated.resources.Res
import assemblepc.shared.generated.resources.delete_assembly
import assemblepc.shared.generated.resources.delete_confirmation_massage
import assemblepc.shared.generated.resources.delete_confirmation_title
import assemblepc.shared.generated.resources.label_cancel
import org.jetbrains.compose.resources.stringResource

@Composable
fun DeleteAssemblyConfirmDialog(
    selectedName: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {

    AlertDialog(
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
                    modifier = Modifier.padding(start = 8.dp),
                    text = stringResource(Res.string.delete_confirmation_title),
                    fontSize = 16.sp,
                )
            }
        },
        text = {
            Column {
                Text(
                    text = stringResource(Res.string.delete_confirmation_massage),
                    fontWeight = FontWeight.Bold
                )
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
                    Text(text = stringResource(Res.string.label_cancel))
                }

                Button(onClick = onConfirm) {
                    Text(text = stringResource(Res.string.delete_assembly))
                }
            }
        }
    )
}