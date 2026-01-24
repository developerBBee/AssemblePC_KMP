package jp.developer.bbee.assemblepc.shared.presentation.screen.device.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import assemblepc.shared.generated.resources.Res
import assemblepc.shared.generated.resources.selected_icon_description
import assemblepc.shared.generated.resources.sort_menu_button_description
import jp.developer.bbee.assemblepc.shared.presentation.common.BaseBGPreview
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SortMenu(
    currentSortType: SortType,
    onSortChanged: (SortType) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd,
    ) {
        IconButton(
            modifier = Modifier.testTag("sort_menu_button"),
            onClick = { isExpanded = true },
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(Res.string.sort_menu_button_description),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 5.dp, end = 5.dp, top = 8.dp),
            )
        }

        Row (Modifier.padding(end = 16.dp)) {
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
            ) {
                SortType.entries.forEach { sortType ->
                    SortItemRow(
                        modifier = Modifier.fillMaxWidth(),
                        sortName = sortType.sortName,
                        sortTag = sortType.name,
                        isSelected = currentSortType == sortType,
                        onItemClick = {
                            isExpanded = false
                            onSortChanged(sortType)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun SortItemRow(
    modifier: Modifier = Modifier,
    sortName: String,
    sortTag: String,
    isSelected: Boolean,
    onItemClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        DropdownMenuItem(
//            modifier = Modifier
//                .semantics { testTagsAsResourceId = BuildConfig.DEBUG }
//                .testTag(sortTag),
            onClick = onItemClick,
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(Res.string.selected_icon_description),
                    modifier = Modifier.width(24.dp).padding(end = 2.dp),
                )
            } else {
                Spacer(modifier = Modifier.width(24.dp).padding(end = 2.dp))
            }
            Text(text = sortName)
        }
    }
}

@Preview
@Composable
fun SortMenuPreview() {
    BaseBGPreview {
        SortMenu(
            modifier = Modifier.height(300.dp).fillMaxWidth(),
            currentSortType = SortType.POPULARITY,
            onSortChanged = {},
        )
    }
}
