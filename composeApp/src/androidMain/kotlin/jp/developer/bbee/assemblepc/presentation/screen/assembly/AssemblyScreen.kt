package jp.developer.bbee.assemblepc.presentation.screen.assembly

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.developer.bbee.assemblepc.domain.model.CompositionItem
import jp.developer.bbee.assemblepc.presentation.components.AssemblyDialog
import jp.developer.bbee.assemblepc.presentation.screen.assembly.components.AssemblyRow

@Composable
fun AssemblyScreen(
    assemblyViewModel: AssemblyViewModel = hiltViewModel()
){

    val uiState by assemblyViewModel.uiState.collectAsStateWithLifecycle()
    val dialogUiState by assemblyViewModel.dialogUiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        AssemblyUiState.Loading -> {
            CircularProgressIndicator()
        }

        is AssemblyUiState.Error -> {
            Text(text = state.error ?: "エラーが発生しました")
        }

        is AssemblyUiState.ShowComposition -> {
            AssemblyContent(
                modifier = Modifier.fillMaxSize(),
                items = state.composition.items,
                onAssemblyClick = assemblyViewModel::showAssemblyDialog
            )
        }
    }

    dialogUiState?.let { (deviceQty) ->
        AssemblyDialog(
            isEdit = true,
            quantity = deviceQty.quantity,
            device = deviceQty.device,
            onDismiss = assemblyViewModel::clearDialog,
            onAddAssembly = { device, qty, _ -> assemblyViewModel.addAssembly(device, qty) },
            onDeleteAssembly = assemblyViewModel::deleteAssembly,
        )
    }
}

@Composable
private fun AssemblyContent(
    modifier: Modifier = Modifier,
    items: List<CompositionItem>,
    onAssemblyClick: (CompositionItem) -> Unit
) {

    Column(modifier = modifier) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items) { item ->
                AssemblyRow(
                    modifier = Modifier.padding(10.dp),
                    item = item,
                    onAssemblyClick = { onAssemblyClick(item) }
                )
            }
        }
    }
}
