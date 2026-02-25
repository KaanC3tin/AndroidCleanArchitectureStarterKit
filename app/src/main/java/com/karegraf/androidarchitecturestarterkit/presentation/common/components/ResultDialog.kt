package com.karegraf.androidarchitecturestarterkit.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.karegraf.androidarchitecturestarterkit.presentation.common.model.DialogType
import com.karegraf.androidarchitecturestarterkit.presentation.common.model.DialogUiState

@Composable
fun ResultDialog(
    state: DialogUiState,
    tintFor: ((DialogType) -> Color)? = null
) {
    val tint = tintFor?.invoke(state.type) ?: when (state.type) {
        DialogType.ERROR -> MaterialTheme.colorScheme.error
        DialogType.SUCCESS -> MaterialTheme.colorScheme.primary
    }
    AlertDialog(
        icon = {
            Icon(
                imageVector = state.type.icon,
                modifier = Modifier.size(32.dp),
                contentDescription = state.message, tint = tint
            )
        },
        title = {
            Text(text = state.type.label)
        },
        text = {
            Text(text = state.message)
        },
        onDismissRequest = {
            state.onDismiss()
        },
        confirmButton = {
            TextButton(
                modifier = Modifier.padding(horizontal = 8.dp)
                    .background(MaterialTheme.colorScheme.primary,
                        MaterialTheme.shapes.large),
                onClick = {
                    state.onConfirm()
                }
            ) {
                Text(state.confirmLabel,
                    color = (MaterialTheme.colorScheme.background))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    state.onDismiss()
                }
            ) {
                Text(state.dismissLabel)
            }
        }
    )

}

@Composable
@Preview(showBackground = true)
fun ResultDialogPreview() {
    ResultDialog(
        state = DialogUiState(
            type = DialogType.SUCCESS,
            message = "İşlem başarılı"
        )
    )
}