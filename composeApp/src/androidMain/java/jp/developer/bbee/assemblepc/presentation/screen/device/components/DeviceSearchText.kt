package jp.developer.bbee.assemblepc.presentation.screen.device.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import jp.developer.bbee.assemblepc.R

@Composable
fun DeviceSearchText(
    modifier: Modifier = Modifier,
    currentSearchText: String,
    onSearchChanged: (String) -> Unit,
    currentSortType: SortType,
    onSortChanged: (SortType) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var hasFocus by remember { mutableStateOf(false) }
    val hasFocusOrText = hasFocus || currentSearchText.isNotEmpty()

    Box(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxSize()
                .onFocusChanged { hasFocus = it.isFocused }
                .testTag("search_text_field"),
            value = currentSearchText,
            label = {
                Row {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search_description)
                    )
                    if (!hasFocusOrText) {
                        Text(text = stringResource(R.string.label_search))
                    }
                }
            },
            onValueChange = { onSearchChanged(it) },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                // EnterしたらFocusを外してソフトウェアキーボードを隠す
                onDone = { focusManager.clearFocus() }
            )
        )

        SortMenu(
            modifier = Modifier.fillMaxSize(),
            currentSortType = currentSortType,
            onSortChanged = onSortChanged,
        )
    }
}
